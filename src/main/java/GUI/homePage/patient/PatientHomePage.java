package GUI.homePage.patient;

import GUI.MainPage;
import user.Patient.Patient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatientHomePage extends JFrame{
    Patient patient;
    private JPanel mainPanel;
    private JButton makeAppointmentButton;
    private JButton cancelAppointmentButton;
    private JButton shiftAppointmentButton;
    private JButton logOutButton;
    private JButton exportMyHealthInfoButton;


    public PatientHomePage(Patient patient) {
        this.patient = patient;
        setContentPane(mainPanel);
        setSize(500,500);
        makeAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MakeAppointment makeAppointment = new MakeAppointment(patient);
                setVisible(false);
                makeAppointment.setVisible(true);
            }
        });

        shiftAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShiftAppointment shiftAppointment = new ShiftAppointment(patient);
                setVisible(false);
                shiftAppointment.setVisible(true);
            }
        });

        cancelAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CancelAppointment cancelAppointment = new CancelAppointment(patient);
                setVisible(false);
                cancelAppointment.setVisible(true);
            }
        });

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainPage mainPage = new MainPage();
                setVisible(false);
                mainPage.setVisible(true);
            }
        });

        exportMyHealthInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExportHealthInfo exportInfo = new ExportHealthInfo(patient);
                setVisible(false);
                exportInfo.setVisible(true);
            }
        });
    }
}
