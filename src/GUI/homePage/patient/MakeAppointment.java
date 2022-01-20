package GUI.homePage.patient;

import appointments.*;
import appointments.schedule.Schedule;
import appointments.schedule.ScheduleDAOImp;
import user.Doctor.Doctor;
import user.Doctor.DoctorDAOImp;
import user.Doctor.Specialization;
import user.Patient.HealthProblem;
import user.Patient.Patient;
import utilities.EnumMapping;
import utilities.Mailer;
import utilities.distanceOfSearch.DistanceOfSearch;
import utilities.distanceOfSearch.MapUtils;

import javax.mail.MessagingException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class MakeAppointment extends JFrame implements ActionListener {
    Patient patient;
    private JButton healthInfoButton;
    private JComboBox healthProblemBox;
    private JTextField distanceOfSearch;
    private JButton showDoctorsButton;
    private JList<Doctor> doctorsList;
    DefaultListModel<Doctor> model = new DefaultListModel<>();
    private JPanel mainPanel;
    private JButton makeAppointmentButton;
    DefaultListModel<Schedule> model2 = new DefaultListModel<>();
    private JButton showAvailableAppointmentsButton;
    private JList<Schedule> availableAppointments;
    private JComboBox reminderBox;
    private JButton goBackToHomepageButton;
    private JFileChooser fileChooser = new JFileChooser();
    File file = null;
    Doctor selectedDoctor = null;
    Schedule selectedSchedule = null;

    @Override
    public void actionPerformed(ActionEvent e) {
        int response = fileChooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            file = new File(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    public MakeAppointment(Patient patient){
        this.patient = patient;
        setContentPane(mainPanel);
        setSize(500,500);
        healthInfoButton.addActionListener(this);
        healthProblemBox.setModel(new DefaultComboBoxModel<>(HealthProblem.values()));
        reminderBox.setModel(new DefaultComboBoxModel<>(Reminder.values()));
        showDoctorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.clear();
                doctorsList.setModel(model);
                Map<String, Double> patientCoords =  MapUtils.getInstance().getCoordinates(patient.getAddress());
                DoctorDAOImp doctorDAOImp = new DoctorDAOImp();
                EnumMap<HealthProblem, Specialization> map = EnumMapping.mapEnums();
                List<Doctor> list = doctorDAOImp.getAllBySpecialization(map.get(healthProblemBox.getSelectedItem()));
                for (Doctor d: list) {
                    Map<String, Double> doctorCoords =  MapUtils.getInstance().
                            getCoordinates(d.getAddress());
                    double distanceFromPatient = DistanceOfSearch.distance(patientCoords.get("lat"),
                            patientCoords.get("lon"), doctorCoords.get("lat"), doctorCoords.get("lon"));
                    try {
                        if (distanceFromPatient <= Integer.valueOf(distanceOfSearch.getText()) * 1000) {
                            model.addElement(d);
                        }
                    } catch (NumberFormatException ne){
                        JOptionPane.showMessageDialog(null, "please enter a valid distance!");
                    }
                }
            }
        });
        doctorsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        doctorsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedDoctor = doctorsList.getSelectedValue();
            }
        });

        showAvailableAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    model2.clear();
                    availableAppointments.setModel(model2);
                    ScheduleDAOImp scheduleDAOImp = new ScheduleDAOImp();
                    List<Schedule> schedules = scheduleDAOImp.getAllAvailable(selectedDoctor.getId());
                    for (Schedule schedule : schedules) {
                        model2.addElement(schedule);
                    }
                } catch (NullPointerException npe1){
                    JOptionPane.showMessageDialog(null, "please select a doctor!");
                }
            }
        });
        availableAppointments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        availableAppointments.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedSchedule = availableAppointments.getSelectedValue();
            }
        });

        makeAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean s = false;
                try{
                    reminderBox.setModel(new DefaultComboBoxModel<>(Reminder.values()));
                    Appointment appointment = new Appointment(selectedDoctor.getId(),
                        patient.getId(),
                        selectedSchedule.getScheduleId(),
                        HealthProblem.valueOf(healthProblemBox.getSelectedItem().toString()),
                        file,
                        Reminder.valueOf(reminderBox.getSelectedItem().toString()),
                        Integer.parseInt(distanceOfSearch.getText()));
                    try{
                        AppointmentDAOImp appointmentDAOImp = new AppointmentDAOImp();
                        System.out.println(patient.getId());
                        s = appointmentDAOImp.addAppointment(appointment);
                    } catch (NullPointerException pe){
                        JOptionPane.showMessageDialog(null, "Please upload your health info file!");
                    }
                    if (s == true){
                        Mailer.sendMail(patient.getEmail(),
                                "Hello " + patient.getFirstName()+"!\n\nYou have successfully booked an appointment with the following information:\n"+
                                        "-Doctor Name: "+selectedDoctor.getLastName()+" "+selectedDoctor.getFirstName()+".\n"+
                                        "-Address: "+selectedDoctor.getAddress()+".\n"+
                                        "-Date and Time: "+selectedSchedule.getDate()+" at "+selectedSchedule.getStart()+
                                        "\n\nBest regards\n\neHealth Consulting",
                                "Appointment Reservation"
                        );
                    }
                } catch (NullPointerException npe2){
                    JOptionPane.showMessageDialog(null, "Please select an appointment!");
                }
                catch (MessagingException ex) {
                    ex.printStackTrace();
                }
            }
        });

        goBackToHomepageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PatientHomePage patientHomePage = new PatientHomePage(patient);
                setVisible(false);
                patientHomePage.setVisible(true);
            }
        });
    }

}
