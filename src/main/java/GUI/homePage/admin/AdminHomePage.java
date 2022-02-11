package GUI.homePage.admin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * this class displays to the admin and enables him to access to patient or doctor profiles
 *
 * @author Mohamed Amine Belrhazi
 */
public class AdminHomePage extends JFrame{
    private JPanel PanelMain;
    private JPanel AdminPanel;
    private JButton PatientButton;
    private JButton DoctorButton;
    private JButton logoutButton;
    private JPanel Admin;
    /**
     * Constructs an instance which create a frame where the admin select to access to patient or doctor profiles.
     * */
    public AdminHomePage() {
        /**
         * it's used to set a title to the frame
         * */
        super("AdminHomePage");
        /**
         * This method is used to display the frame to the user
         * */
        this.setVisible(true);
        /**
         *This methode changes the size of the frames according to the given size
         * */
        this.setSize(500,600);
        /**
         * This method is used to set the top-level visual element inside a Window
         * @param PanelMain which stores our group of components
         * */
        this.setContentPane(this.PanelMain);
        /**
         * This method is used to determine one of several options for the close button.
         * @param Frame.DISPOSE_ON_CLOSE which discards The frame object ,but the application continues to run.
         * */
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ///this.pack();
        DoctorButton.addActionListener(new ActionListener() {
            /**
             * Using The doctorButton, the admin can access to the list of doctors where he's authorized to delete or edit the data of a doctor.
             * @param e is generated when the user has selected that menu item
             * */
            @Override
            public void actionPerformed(ActionEvent e) {
                AccessDoc accessDoc=new AccessDoc();
                setVisible(false);
            }
        });
        PatientButton.addActionListener(new ActionListener() {
            /**
             * Using The PatientButton, the admin can access to the list of patients where he's authorized to delete or edit the data of a patient.
             * @param e is generated when the user has selected that menu item
             * */
            @Override
            public void actionPerformed(ActionEvent e) {
                AccessPat accessPat=new AccessPat();
                setVisible(false);
            }
        });
    }

    /*public static void main(String[] args) {
        AdminHomePage adminHomePage=new AdminHomePage();
        adminHomePage.setVisible(true);
        adminHomePage.setSize(400,500);
    }*/
}
