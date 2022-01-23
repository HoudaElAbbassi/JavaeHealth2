package GUI;

import Exceptions.PasswordException;
import GUI.homePage.admin.AdminHomePage;
import GUI.homePage.patient.PatientHomePage;
import Security.PasswordManager;
import user.Admin.AdminDAOImp;
import user.Doctor.DoctorDAOImp;
import user.Patient.PatientDAOImp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JTextField emailText;
    private JPasswordField passwordField;
    private JComboBox comboBox;
    private JPanel mainPanel;
    private JButton logInButton;
    private JButton goBackToMainpageButton;
    private JButton resetPasswordButton;

    public Login() throws PasswordException {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 600);
        setContentPane(mainPanel);

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String encodedPassword = PasswordManager.encode(passwordField.getText());
                System.out.println(encodedPassword);
                //login patient
                if (comboBox.getSelectedItem().toString().equals("Patient")){
                    PatientDAOImp patientDAOImp = new PatientDAOImp();
                    if(patientDAOImp.existEmail(emailText.getText())){
                        if(patientDAOImp.getPassword(emailText.getText()).equals(encodedPassword)){
                            dispose();
                            PatientHomePage patientHomePage = new PatientHomePage(patientDAOImp.getByEmail(emailText.getText()));
                            System.out.println(patientDAOImp.getByEmail(emailText.getText()).getId());
                            setVisible(false);
                            patientHomePage.setVisible(true);
                        }
                        else   JOptionPane.showMessageDialog(null,"Incorrect password!");

                    }
                    else   JOptionPane.showMessageDialog(null,"Incorrect email/password");

                }
               else if (comboBox.getSelectedItem().toString().equals("Doctor")){
                    DoctorDAOImp doctorDAOImp = new DoctorDAOImp();
                    if(doctorDAOImp.existEmail(emailText.getText())){
                        if(doctorDAOImp.getPassword(emailText.getText()).equals(encodedPassword)){
                            dispose();
                            GUI.homePage.doctor.DoctorHomePage doctorHomePage = new GUI.homePage.doctor.DoctorHomePage(doctorDAOImp.getByEmail(emailText.getText()));
                            setVisible(false);
                            //doctorHomePage.setVisible(true);
                        }
                        else   JOptionPane.showMessageDialog(null,"Incorrect password!");

                    }
                    else   JOptionPane.showMessageDialog(null,"Incorrect email/password");

                }

               else {
                    AdminDAOImp adminDAOImp = new AdminDAOImp();
                    if(adminDAOImp.existEmail(emailText.getText())){
                        if(adminDAOImp.getPassword(emailText.getText()).equals(passwordField.getText())){
                            dispose();
                            AdminHomePage adminHomePage = new AdminHomePage();
                            setVisible(false);
                            //adminHomePage.setVisible(true);
                        }
                        else   JOptionPane.showMessageDialog(null,"Incorrect password!");

                    }
                    else   JOptionPane.showMessageDialog(null,"Incorrect email/password");
                }
            }
        });

        goBackToMainpageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainPage mainPage = new MainPage();
                setVisible(false);
                mainPage.setVisible(true);
            }
        });
        resetPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ResetPasswordPage resetPasswordPage=new ResetPasswordPage();
                resetPasswordPage.setVisible(true);
            }
        });
    }
}
