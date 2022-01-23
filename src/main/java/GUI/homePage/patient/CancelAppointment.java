package GUI.homePage.patient;

import appointments.Appointment;
import appointments.AppointmentDAOImp;
import user.Patient.Patient;
import utilities.Mailer;
import javax.mail.MessagingException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


/** Represents the layout allowing a patient to cancel an appointment in the GUI
 * @author Ahmed Agdmoun
 */
public class CancelAppointment extends JFrame {
    /**
     * represents the logged in patient
     */
    private Patient patient;
    /**
     * represents the selected appointment to be canceled
     */
    private Appointment selectedAppointment = null;
    private JButton showMyAppointmentsButton; // this button must be clicked to display the appointments already booked by the logged in patient
    DefaultListModel<Appointment> model = new DefaultListModel<>();
    /**
     * the List contains all the booked appointments by the logged in patient
     */
    private JList<Appointment> myAppointments; // this list will contain the appointments booked from the patient and will be displayed in the GUI after clicking the showMyAppointments button
    private JButton cancelButton;
    private JButton goBackToHomepageButton;
    private JPanel mainPanel;

    /**
     * Creates the cancellation
     * @param patient corresponds to the actual patient logged in.
     */
    public CancelAppointment(Patient patient){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.patient = patient;
        setContentPane(mainPanel);
        setSize(500,500);

        /**
         * Action after clicking the showMyAppointmentButton
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

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AppointmentDAOImp appointmentDAOImp = new AppointmentDAOImp();
                    appointmentDAOImp.cancelById(selectedAppointment.getId());
                    Mailer.sendMail(patient.getEmail(),
                            "Hello " + patient.getFirstName()+"!\n\nYour Appointment has been successfully canceled!\n\n" +
                                    "Best regards\n\neHealth Consulting",
                            "Appointment Cancellation"
                    );
                } catch (MessagingException ex) {
                    ex.printStackTrace();
                } catch (NullPointerException npe1) {
                    JOptionPane.showMessageDialog(null, "please select an Appointment!");
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
