package GUI;

import Exceptions.PasswordException;
import GUI.RegisterationPage.RegisterDoc;
import GUI.RegisterationPage.RegisterPatient;
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
 * @author Ahmed,Houda,Amine,Parabal,Daniel
 */
public class Login extends JFrame {
    private JTextField emailText;
    private JPasswordField passwordField;
    private JComboBox comboBox;
    private JPanel mainPanel;
    private JButton logInButton;
    private JButton goBackToMainpageButton;
    private JButton resetPasswordButton;
    private JButton signUpAsAButton1;
    private JButton signUpAsAButton;
    private JLabel labelpicture;

    /**
     * an instance which create a frame where the user select the type of his profile and log in with his own email and password.
     * */
    public Login() throws PasswordException {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //This method is used to determine one of several options for the close button.
        //Frame.DISPOSE_ON_CLOSE which discards The frame object ,but the application continues to run.
        setSize(800, 500);
        setContentPane(mainPanel);//This method is used to set the top-level visual element inside a Window

        ImageIcon imageIcon= new ImageIcon("C:\\Users\\houda\\Desktop\\JAVAProject\\docPic.jpeg");
        labelpicture.setIcon(imageIcon);


        /**This method enable the user to select his type of profile and input his registered email and password to login
         * @param e is generated when the user has selected that menu item
         * */


        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String encodedPassword = PasswordManager.encode(passwordField.getText());/// encode the given password given
                System.out.println(encodedPassword);
                //login patient
                if (comboBox.getSelectedItem().toString().equals("Patient")){///// if the user selects to log in as a patient
                    PatientDAOImp patientDAOImp = new PatientDAOImp();/// instantiate the class of data access object patient
                    if(patientDAOImp.existEmail(emailText.getText())){/////check if the email exist in patient database
                        if(patientDAOImp.getPassword(emailText.getText()).equals(encodedPassword)){////check if the encoded given password matches the password's email
                            dispose();
                            PatientHomePage patientHomePage = new PatientHomePage(patientDAOImp.getByEmail(emailText.getText()));/// Instantiate the class patientHomePage
                            System.out.println(patientDAOImp.getByEmail(emailText.getText()).getId());
                            setVisible(false);/// Close the login frame
                            patientHomePage.setVisible(true);/// Display the PatientHomePage
                        }
                        else   JOptionPane.showMessageDialog(null,"Incorrect password!");

                    }
                    else   JOptionPane.showMessageDialog(null,"Incorrect email/password");

                }
               else if (comboBox.getSelectedItem().toString().equals("Doctor")){///// if the user selects to log in as a doctor
                    DoctorDAOImp doctorDAOImp = new DoctorDAOImp();/// instantiate the class of data access object doctor
                    if(doctorDAOImp.existEmail(emailText.getText())){/////check if the email exist in doctor database
                        if(doctorDAOImp.getPassword(emailText.getText()).equals(encodedPassword)){////check if the encoded given password matches the password's email
                            dispose();
                            GUI.homePage.doctor.DoctorHomePage doctorHomePage = new GUI.homePage.doctor.DoctorHomePage(doctorDAOImp.getByEmail(emailText.getText()));/// Instantiate the class doctorHomePage
                            System.out.println(doctorDAOImp.getByEmail(emailText.getText()).getId());
                            doctorHomePage.setVisible(true);///display the DoctorHomePage
                        }
                        else   JOptionPane.showMessageDialog(null,"Incorrect password!");

                    }
                    else   JOptionPane.showMessageDialog(null,"Incorrect email/password");

                }

               else {/////// the same operation occurs with the admin
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
        /**
         * By clicking on goBackToMainPage button the user would be redirected to MainPage
         * @param e  is generated when the user has selected that menu item
         * */
        goBackToMainpageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainPage mainPage = new MainPage();
                setVisible(false);
                mainPage.setVisible(true);
            }
        });
        /**
         * this methode enable the user to reset his previous password
         * @param e  is generated when the user has selected that menu item
         * */
        resetPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ResetPasswordPage resetPasswordPage=new ResetPasswordPage();
                resetPasswordPage.setVisible(true);
            }
        });
        signUpAsAButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterDoc registerDoc= new RegisterDoc();
                setVisible(false);
                registerDoc.setVisible(true);
            }
        });
        signUpAsAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterPatient registerPatient =  new RegisterPatient();
                setVisible(false);
                registerPatient.setVisible(true);
            }
        });
    }
}
