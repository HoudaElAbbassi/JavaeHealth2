package GUI.homePage.patient;

import Exceptions.ScheduleException;
import appointments.Appointment;
import appointments.AppointmentDAOImp;
import appointments.schedule.Status;
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
 * This class represents the page where a patient can cancel an appointment in the GUI
 * @author Ahmed Agdmoun
 */
public class CancelAppointment extends JFrame {
    /** represents the logged in patient */
    private Patient patient;

    /** represents the selected appointment to be canceled */
    private Appointment selectedAppointment = null;

    /** represents a list of all the booked appointments by the logged in patient */
    private JList<Appointment> myAppointments;
    DefaultListModel<Appointment> model = new DefaultListModel<>();
    private JButton cancelButton;
    private JButton showMyAppointmentsButton;
    private JButton goBackToHomepageButton;
    private JPanel mainPanel;

    /**
     * this is the main constructor responsible for creating the CancelAppointment page
     * @param patient represents to the actual patient logged in.
     */
    public CancelAppointment(Patient patient){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.patient = patient;
        setContentPane(mainPanel);
        setSize(800,500);

        /**
         * Actions taking place after clicking the showMyAppointmentButton
         */
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
         * Actions taking place after clicking the cancelButton
         */
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel your appointment?", "choose", JOptionPane.YES_NO_OPTION);

                    if (input == 0) {

                        AppointmentDAOImp appointmentDAOImp = new AppointmentDAOImp();
                        appointmentDAOImp.cancel(selectedAppointment);
                        String message = Mailer.cancellationMessage(patient);
                        Mailer.sendMail(patient.getEmail(), message, "Appointment Cancellation");
                        showMyAppointmentsButton.doClick();
                    }


                } catch (MessagingException ex) {
                    ex.printStackTrace();
                } catch (NullPointerException npe1) {
                    JOptionPane.showMessageDialog(null, "please select an Appointment!");
                }
            }
        });

        /**
         * Actions taking place after clicking the goBackToHomeButton
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
