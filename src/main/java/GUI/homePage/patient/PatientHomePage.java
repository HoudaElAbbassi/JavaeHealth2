package GUI.homePage.patient;

import Exceptions.PasswordException;
import GUI.Login;
import user.Patient.InsuranceType;
import user.Patient.Patient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

/**
 * This class represents the homepage of the patient in the GUI, in other words
 * the page where a patient lands after being successfully logged in
 * @author Ahmed Agdmoun
 */
public class PatientHomePage extends JFrame{
    /** represents the patient logged in the actual session */
    Patient patient;
    private JPanel mainPanel;
    private JButton makeAppointmentButton;
    private JButton cancelAppointmentButton;
    private JButton shiftAppointmentButton;
    private JButton logOutButton;
    private JButton exportMyHealthInfoButton;
    private JLabel patienticon;
    private JLabel patientName;
    private JLabel patientlastname;

    /**
     * This is the main constructor of the class which constructs the GUI page
     * @param patient represents the logged in patient
     */
    public PatientHomePage(Patient patient) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.patient = patient;
        setContentPane(mainPanel);
        setSize(800,500);

        ImageIcon imageIcon= new ImageIcon("medical_chiken2.png");
       patienticon.setIcon(imageIcon);

        patientName.setText(patient.getFirstName());
        patientlastname.setText(patient.getLastName());

        makeAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MakeAppointment makeAppointment = new MakeAppointment(patient); // creates a new makeAppointment page
                setVisible(false);
                makeAppointment.setVisible(true);
            }
        });
        /**
         * actions taking place in case of clicking the shiftAppointmentButton
         */
        shiftAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShiftAppointment shiftAppointment = new ShiftAppointment(patient);
                setVisible(false);
                shiftAppointment.setVisible(true);
            }
        });

        /**
         * actions taking place in case of clicking the cancelAppointmentButton
         */
        cancelAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CancelAppointment cancelAppointment = new CancelAppointment(patient);
                setVisible(false);
                cancelAppointment.setVisible(true);
            }
        });

        /**
         * actions taking place in case of clicking the exportMyHealthInfoButton
         */
        exportMyHealthInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExportHealthInfo exportInfo = new ExportHealthInfo(patient);
                setVisible(false);
                exportInfo.setVisible(true);
            }
        });

        /**
         * actions taking place in case of clicking the logOutButton
         */
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login mainPage = null;
                try {
                    mainPage = new Login();
                } catch (PasswordException ex) {
                    ex.printStackTrace();
                }
                setVisible(false);
                mainPage.setVisible(true);
            }
        });
    }

}
