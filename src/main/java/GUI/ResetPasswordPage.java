package GUI;

import Exceptions.EmailException;
import Exceptions.PasswordException;
import Security.GeneratePassword;
import Security.PasswordManager;
import user.Admin.Admin;
import user.Admin.AdminDAOImp;
import user.Doctor.Doctor;
import user.Doctor.DoctorDAOImp;
import user.Patient.Patient;
import user.Patient.PatientDAOImp;
import utilities.Mailer;

import javax.mail.MessagingException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResetPasswordPage extends JFrame{
    private JComboBox userBox;
    private JTextField email;
    private JButton sendNewPasswordButton;
    private JTextField firstname;
    private JTextField lastname;
    private JPanel mainPanel;

    public ResetPasswordPage() {

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 600);
        setContentPane(mainPanel);

        PatientDAOImp patientDAOImp=new PatientDAOImp();
        DoctorDAOImp doctorDAOImp=new DoctorDAOImp();
        AdminDAOImp adminDAOImp=new AdminDAOImp();

        sendNewPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(userBox.getSelectedItem().toString().equals("Patient")){
                    //compare Date
                    Patient patient=patientDAOImp.getByEmail(email.getText());
                    if(patient.getFirstName().equals(firstname.getText()) && patient.getLastName().equals(lastname.getText())){
                        String p=GeneratePassword.generateStrongPassword();
                        patient.setPassword(p);
                        patientDAOImp.edit(patient);


                        try {
                            Mailer.sendMail(patient.getEmail(),"new Password: "+p,"Your new Password");
                        } catch (MessagingException ex) {
                            ex.printStackTrace();
                        }

                        dispose();
                        setNewPassword setNewPassword=new setNewPassword();
                        setNewPassword.setVisible(true);
                    }
                    else{
                    JOptionPane.showMessageDialog(null,"E-Mail/First-/Lastname wrong");
                }}
                else if(userBox.getSelectedItem().toString().equals("Doctor")){
                    Doctor doctor=doctorDAOImp.getByEmail(email.getText());
                    if(doctor.getFirstName().equals(firstname.getText()) && doctor.getLastName().equals(lastname.getText())){
                        String p=GeneratePassword.generateStrongPassword();
                        doctor.setPassword(p);
                        doctorDAOImp.edit(doctor);
                        try {
                            Mailer.sendMail(doctor.getEmail(),"new Password:"+p,"Your new Password");
                        } catch (MessagingException ex) {
                            ex.printStackTrace();
                        }
                        dispose();
                        setNewPassword setNewPassword=new setNewPassword();
                        setNewPassword.setVisible(true);
                    }

                    else{
                        JOptionPane.showMessageDialog(null,"E-Mail/First-/Lastname wrong");
                    }
                }
                else{
                    Admin admin=adminDAOImp.getByEmail(email.getText());
                    if(admin.getFirstName().equals(firstname.getText()) && admin.getLastName().equals(lastname.getText())){
                        String p=GeneratePassword.generateStrongPassword();
                        admin.setPassword(p);
                        try {
                            adminDAOImp.edit(admin);
                        } catch (PasswordException ex) {
                            ex.printStackTrace();
                        } catch (EmailException ex) {
                            ex.printStackTrace();
                        }
                        try {
                            Mailer.sendMail(admin.getEmail(),"new Password:"+PasswordManager.decode(p),"Your new Password");
                        } catch (MessagingException ex) {
                            ex.printStackTrace();
                        }
                        dispose();
                        setNewPassword setNewPassword=new setNewPassword();
                        setNewPassword.setVisible(true);
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"E-Mail/First-/Lastname wrong");
                    }

                }
            }
        });
    }
}