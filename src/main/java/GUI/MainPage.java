package GUI;

import Exceptions.PasswordException;
import GUI.RegisterationPage.RegisterDoc;
import GUI.RegisterationPage.RegisterPatient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * this class extends from JFrame, and it's used to display to the user the option
 * to register as a doctor/patient or login
 * @author
 */
public class MainPage extends JFrame{
    private JButton newDoctorButton;
    private JButton newPatientButton;
    private JButton loginButton;
    private JPanel mainPanel;
    /**
     * Constructs an instance which create a frame where the user can choose to register or login.
     */
    public MainPage() {
        /**
         * This method is used to determine one of several options for the close button.
         * @param Frame.DISPOSE_ON_CLOSE which discards The frame object ,but the application continues to run.
         * */
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        /**
         * This method is used to set the top-level visual element inside a Window
         * @param mainPanel which stores our group of components
         * */
        setContentPane(mainPanel);
        /**
         *This methode changea the size of the frames according to the given size
         * */
        setSize(500,500);
        newDoctorButton.addActionListener(new ActionListener() {
            @Override
            /**
             * By clicking on newDoctor button the user would be redirected to RegisterDoc page
             * @param e  is generated when the user has selected that menu item
             * */
            public void actionPerformed(ActionEvent e) {
                RegisterDoc registerDoc= new RegisterDoc();
                setVisible(false);
                registerDoc.setVisible(true);
            }
        });

        newPatientButton.addActionListener(new ActionListener() {
            @Override
            /**
             * By clicking on newPatient button the user would be redirected to RegisterPatient page
             * @param e  is generated when the user has selected that menu item
             * */
            public void actionPerformed(ActionEvent e) {
                RegisterPatient registerPatient =  new RegisterPatient();
                setVisible(false);
                registerPatient.setVisible(true);
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            /**
             * By clicking on login button the user would be redirected to login page
             * @param e  is generated when the user has selected that menu item
             * */
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
