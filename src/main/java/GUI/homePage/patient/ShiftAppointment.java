package GUI.homePage.patient;

import appointments.Appointment;
import appointments.AppointmentDAOImp;
import appointments.schedule.Schedule;
import appointments.schedule.ScheduleDAOImp;
import user.Patient.Patient;
import utilities.Mailer;

import javax.mail.MessagingException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * This class represents the page where a patient can shift an appointment in the GUI
 * @author Ahmed Agdmoun
 */
public class ShiftAppointment extends JFrame {

    /**
     * represents the actual logged in patient
     */
    Patient patient;

    /**
     * a list of all the already booked appointments by the patient
     */
    private JList<Appointment> myAppointments;

    /**
     * a list of available alternative appointments by the same doctor by whom the original appointment was booked
     */
    private JList<Schedule> availableAppointments;

    /**
     * represents the appointment selected from the appointments list by the patient to be shifted
     */
    Appointment selectedAppointment = null;

    /**
     * represents the new schedule selected by the patient from the list of available appointments
     */
    Schedule selectedSchedule = null;
    private JPanel mainPanel;
    private JButton showMyAppointmentsButton;
    DefaultListModel<Appointment> model = new DefaultListModel<>();
    private JButton shiftButton;
    DefaultListModel<Schedule> model2 = new DefaultListModel<>();
    private JButton showAvailableAppointmentsButton;
    private JButton goBackToHomepageButton;

    /**
     * this is the main constructor responsible for creating a ShiftAppointment page
     * @param patient
     */
    public ShiftAppointment(Patient patient){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.patient = patient;
        setContentPane(mainPanel);
        setSize(500,500);
        showMyAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.clear();
                myAppointments.setModel(model);
                AppointmentDAOImp appointmentDAOImp = new AppointmentDAOImp();
                List<Appointment> appointmentList = appointmentDAOImp.getAllByPatientId(patient.getId());
                System.out.println(patient.getId());
                for (Appointment app: appointmentList) {
                    model.addElement(app);
                }
            }
        });

        myAppointments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        myAppointments.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedAppointment = myAppointments.getSelectedValue();
            }
        });

        /**
         * actions taking place in case of clicking the showAvailableAppointmentsButton
         */
        showAvailableAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model2.clear();
                availableAppointments.setModel(model2);
                try{
                    ScheduleDAOImp scheduleDAOImp = new ScheduleDAOImp();
                    AppointmentDAOImp appointmentDAOImp = new AppointmentDAOImp();
                    long scheduleId = appointmentDAOImp.getScheduleId(selectedAppointment.getId());
                    System.out.println(scheduleId);
                    long doctorId = scheduleDAOImp.getDoctorId(scheduleId);
                    System.out.println(doctorId);
                    List<Schedule> schedules = scheduleDAOImp.getAllAvailable(doctorId);
                    for (Schedule schedule: schedules) {
                        model2.addElement(schedule);
                    }
                }catch (NullPointerException npe1) {
                    JOptionPane.showMessageDialog(null, "please select an Appointment!");
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
         * actions taking place in case of clicking the shiftButton
         */
        shiftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AppointmentDAOImp appointmentDAOImp = new AppointmentDAOImp();
                    appointmentDAOImp.shiftAppointment(selectedAppointment, selectedSchedule.getScheduleId());
                    System.out.println(selectedSchedule.getScheduleId());
                    String message = Mailer.reschedulingMessage(patient, selectedSchedule);
                    Mailer.sendMail(patient.getEmail(), message, "Appointment Rescheduling");
                } catch (MessagingException ex) {
                    ex.printStackTrace();
                } catch (NullPointerException npe1) {
                    JOptionPane.showMessageDialog(null, "please select a new Appointment!");
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
