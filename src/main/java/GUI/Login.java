package GUI;

import Exceptions.EmailException;
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


/**
 * this class displays to the user "Patient/Doctor/Admin" the Login page to access to the app
 * @author
 */
public class Login extends JFrame {
    private JTextField emailText;
    private JPasswordField passwordField;
    private JComboBox comboBox;
    private JPanel mainPanel;
    private JButton logInButton;
    private JButton goBackToMainpageButton;
    private JButton resetPasswordButton;
    /**
     * Constructs an instance which create a frame where the user select the type of his profile and log in with his own email and password.
     * */
    public Login() throws PasswordException {
        /**
         * This method is used to determine one of several options for the close button.
         * @param Frame.DISPOSE_ON_CLOSE which discards The frame object ,but the application continues to run.
         * */
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        /**
         *This methode changes the size of the frames according to the given size
         * */
        setSize(400, 600);
        /**
         * This method is used to set the top-level visual element inside a Window
         * @param mainPanel which stores our group of components
         * */
        setContentPane(mainPanel);

        logInButton.addActionListener(new ActionListener() {
            /**This method enable the user to select his type of profile and input his registered email and password to login
             * @param e is generated when the user has selected that menu item
            * */
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
                            System.out.println(doctorDAOImp.getByEmail(emailText.getText()).getId());
                            doctorHomePage.setVisible(true);
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
            /**
             * By clicking on goBackToMainPage button the user would be redirected to MainPage
             * @param e  is generated when the user has selected that menu item
             * */
            @Override
            public void actionPerformed(ActionEvent e) {
                MainPage mainPage = new MainPage();
                setVisible(false);
                mainPage.setVisible(true);
            }
        });
        resetPasswordButton.addActionListener(new ActionListener() {
            /**
             * this methode enable the user to reset his previous password
             * @param e  is generated when the user has selected that menu item
             * */
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ResetPasswordPage resetPasswordPage=new ResetPasswordPage();
                resetPasswordPage.setVisible(true);
            }
        });
    }
}
