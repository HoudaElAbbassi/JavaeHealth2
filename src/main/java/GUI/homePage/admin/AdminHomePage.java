package GUI.homePage.admin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AdminHomePage extends JFrame{
    private JPanel PanelMain;
    private JPanel AdminPanel;
    private JButton PatientButton;
    private JButton DoctorButton;
    private JPanel Admin;

    public AdminHomePage() {
        super("AdminHomePage");
        this.setVisible(true);
        this.setSize(500,600);
        this.setContentPane(this.PanelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ///this.pack();
        DoctorButton.addActionListener(new ActionListener() { /////The doctorButton to access to the page which the admin can delete or edit the data of a doctor.
            @Override
            public void actionPerformed(ActionEvent e) {
                AccessDoc accessDoc=new AccessDoc();
                setVisible(false);
            }
        });
        PatientButton.addActionListener(new ActionListener() {/////The patientButton to access to the page which the admin can delete or edit the data of a patient.
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
