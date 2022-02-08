package GUI.homePage.patient;

import appointments.Appointment;
import appointments.AppointmentDAOImp;
import appointments.schedule.Schedule;
import appointments.schedule.ScheduleDAOImp;
import org.apache.commons.lang3.time.DateUtils;
import user.Doctor.Doctor;
import user.Doctor.DoctorDAOImp;
import user.Doctor.Specialization;
import user.Patient.HealthProblem;
import user.Patient.Patient;
import utilities.EnumMapping;
import utilities.Mailer;
import utilities.Reminder;
import utilities.distanceOfSearch.DistanceOfSearch;
import utilities.distanceOfSearch.MapUtils;

import javax.mail.MessagingException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;
import java.text.ParseException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

/**
 * This class represents the page where a patient can make an appointment in the GUI
 * @author Ahmed Agdmoun
 */

public class MakeAppointment extends JFrame implements ActionListener {
    /** represents the actual patient logged in */
    Patient patient;
    /** to save the health info file that will be uploaded by the patient */
    File file = null;
    /** represents a list of all the doctors with a specialization corresponding to the selected health problem */
    private JList<Doctor> doctorsList;
    /** represents a filtered list of all available appointments according to the patient filters */
    private JList<Schedule> availableAppointments;
    /** represents the selected doctor from the list that will be displayed */
    Doctor selectedDoctor = null;
    /** represents the selected schedule from the list of available appointments */
    Schedule selectedSchedule = null;
    private JButton healthInfoButton;
    private JComboBox healthProblemBox;
    private JTextField distanceOfSearch;
    private JButton showDoctorsButton;
    DefaultListModel<Doctor> model = new DefaultListModel<>();
    private JPanel mainPanel;
    private JButton makeAppointmentButton;
    DefaultListModel<Schedule> model2 = new DefaultListModel<>();
    private JButton showAvailableAppointmentsButton;
    private JComboBox reminderBox;
    private JButton goBackToHomepageButton;
    private JFileChooser fileChooser = new JFileChooser();

    @Override
    public void actionPerformed(ActionEvent e) {
        int response = fileChooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            file = new File(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    /**
     * This is the main constructor responsible for creating a new MakeAppointment page
     * @param patient represents the actual logged in patient
     */
    public MakeAppointment(Patient patient){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.patient = patient;
        setContentPane(mainPanel);
        setSize(500,500);
        healthInfoButton.addActionListener(this);
        healthProblemBox.setModel(new DefaultComboBoxModel<>(HealthProblem.values()));

        /**
         * actions taking place in case of clicking the showDoctorsButton
         */
        showDoctorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.clear(); // to actualize the list every time the user clicks the button
                doctorsList.setModel(model); // sets a model for the doctors list
                // gets the coordinates of the patient using his address
                Map<String, Double> patientCoords =  MapUtils.getInstance().getCoordinates(patient.getAddress());
                DoctorDAOImp doctorDAOImp = new DoctorDAOImp();
                EnumMap<HealthProblem, Specialization> map = EnumMapping.mapEnums();
                // list contains all doctors with the specialization corresponding to the health problem selected by the patient
                List<Doctor> list = doctorDAOImp.getAllBySpecialization(map.get(healthProblemBox.getSelectedItem()));
                /*
                    the foreach loop runs through the whole list and gets the coordinates of every doctor using their address
                    then calculates the distance between the patient coordinates above and the coordinates of each doctor
                    only the doctors with a distance less than the entered distance of search will be added to the model of
                    the doctors list to be shown
                */
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

        /**
         * actions taking place in case of clicking the showAvailableAppointmentsButton
         */
        showAvailableAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    model2.clear();
                    availableAppointments.setModel(model2);
                    ScheduleDAOImp scheduleDAOImp = new ScheduleDAOImp();
                    List<Schedule> schedules = scheduleDAOImp.getAllAvailable(selectedDoctor.getId());
                    // only available schedules will be added to the model
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

        /**
         * actions taking place in case of clicking the makeAppointmentButton
         */
        makeAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean appointmentSaved = false;
                try{
                    Appointment appointment = new Appointment(selectedDoctor.getId(),
                        patient.getId(),
                        selectedSchedule.getScheduleId(),
                        HealthProblem.valueOf(healthProblemBox.getItemAt(healthProblemBox.getSelectedIndex()).toString()),
                        file);
                    try{
                        AppointmentDAOImp appointmentDAOImp = new AppointmentDAOImp();
                        appointmentSaved = appointmentDAOImp.addAppointment(appointment);
                    } catch (NullPointerException pe){
                        JOptionPane.showMessageDialog(null, "Please upload your health info file!");
                    }
                    if (appointmentSaved == true){ // only if appointment was saved successfully
                        String message = Mailer.bookingMessage(patient, selectedDoctor, selectedSchedule);
                        Mailer.sendMail(patient.getEmail(), message, "Appointment Reservation");
                        Timer timer=new Timer();
                        Reminder reminder = new Reminder(patient.getEmail(),
                                "Hello " + patient.getFirstName()+"!\n\nDon't forget! You have an appointment with the following information:\n"+
                                "-Doctor Name: "+selectedDoctor.getLastName()+" "+selectedDoctor.getFirstName()+".\n"+
                                "-Address: "+selectedDoctor.getAddress()+".\n"+
                                "-Date and Time: "+selectedSchedule.getDate()+" at "+selectedSchedule.getStart()+
                                "\n\nBest regards\n\neHealth Consulting", "Appointment Reminder");
                        ScheduleDAOImp scheduleDAOImp = new ScheduleDAOImp();
                        Date date = Reminder.convert(scheduleDAOImp.getDateTimeByScheduleId(selectedSchedule.getScheduleId()));
                        if(reminderBox.getSelectedIndex() == 0){
                            date = DateUtils.addWeeks(date, 1); // add 1 week to the actual date
                        }
                        else if(reminderBox.getSelectedIndex() == 1){
                            date = DateUtils.addDays(date, 3); // add 3 days to the actual date
                        }
                        else if(reminderBox.getSelectedIndex() == 2){
                            date = DateUtils.addHours(date, 1); // add 1 hour to the actual date
                        }
                        else if(reminderBox.getSelectedIndex() == 3){
                            date = DateUtils.addMinutes(date, 10); // add 10 minutes to the actual date
                        }
                        System.out.println("Reminder will be sent on: "+ date);
                        timer.schedule(reminder, date);
                    }
                } catch (NullPointerException npe2){
                    JOptionPane.showMessageDialog(null, "Please select an appointment!");
                }
                catch (MessagingException ex) {
                    ex.printStackTrace();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
        });

        /**
         * actions taking place in case of clicking the goBackToHomepageButton
         */
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
