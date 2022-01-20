package GUI;

import Exceptions.PasswordException;
import GUI.RegisterationPage.RegisterDoc;
import GUI.RegisterationPage.RegisterPatient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JFrame{
    private JButton newDoctorButton;
    private JButton newPatientButton;
    private JButton loginButton;
    private JPanel mainPanel;

    public MainPage() {
        setContentPane(mainPanel);
        setSize(500,500);
        newDoctorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterDoc registerDoc= new RegisterDoc();
                setVisible(false);
                registerDoc.setVisible(true);
            }
        });

        newPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterPatient registerPatient =  new RegisterPatient();
                setVisible(false);
                registerPatient.setVisible(true);
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Login login = new Login();
                    setVisible(false);
                    login.setVisible(true);
                } catch (PasswordException ex) {
                    ex.printStackTrace();
                }
            }
        });


    }



}
