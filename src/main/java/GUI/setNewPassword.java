package GUI;

import Exceptions.EmailException;
import Exceptions.PasswordException;
import Security.PasswordManager;
import user.Admin.Admin;
import user.Admin.AdminDAOImp;
import user.Doctor.Doctor;
import user.Doctor.DoctorDAOImp;
import user.Patient.Patient;
import user.Patient.PatientDAOImp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class setNewPassword extends JFrame {
    private JButton setNewPasswordButton;
    private JComboBox userBox;
    private JTextField emailtxt;
    private JPanel mainPanel;
    private JPasswordField newPassword;
    private JPasswordField newPassword2;
    private JPasswordField tempPassword;

    public setNewPassword(){

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 600);
        setContentPane(mainPanel);

        setNewPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!newPassword.getText().equals(newPassword2.getText())) {
                        throw new PasswordException("The passwords do not match");
                    }


                    if (userBox.getSelectedItem().toString().equals("Patient")) {
                        PatientDAOImp patientDAOImp = new PatientDAOImp();
                        Patient patient = patientDAOImp.getByEmail(emailtxt.getText());


                        if (!patient.getPassword().equals(PasswordManager.encode(tempPassword.getText()))) {
                            throw new PasswordException("The passwords do not match");
                        }


                        patient.setPassword(newPassword.getText());
                        patientDAOImp.edit(patient);

                        JOptionPane.showMessageDialog(null, "reset Password successful");
                        dispose();
                        MainPage mainPage = new MainPage();
                        mainPage.setVisible(true);

                    } else if (userBox.getSelectedItem().toString().equals("Doctor")) {
                        DoctorDAOImp doctorDAOImp = new DoctorDAOImp();
                        Doctor doctor = doctorDAOImp.getByEmail(emailtxt.getText());

                        if (!doctor.getPassword().equals(PasswordManager.encode(tempPassword.getText())))
                           throw new PasswordException("Please check the password we sent you!");

                        doctor.setPassword(newPassword.getText());
                        doctorDAOImp.edit(doctor);

                        JOptionPane.showMessageDialog(null, "reset Password successful");
                        dispose();
                        MainPage mainPage = new MainPage();
                        mainPage.setVisible(true);

                    } else if (userBox.getSelectedItem().toString().equals("Admin")) {
                        AdminDAOImp adminDAOImp = new AdminDAOImp();
                        Admin admin = adminDAOImp.getByEmail(emailtxt.getText());

                        if (admin.getPassword().equals(PasswordManager.encode(tempPassword.getText())))
                           throw new PasswordException( "Please check the password we sent you!");

                        admin.setPassword(newPassword.getText());
                        try {
                            adminDAOImp.edit(admin);
                        } catch (PasswordException ex) {
                            ex.printStackTrace();
                        } catch (EmailException ex) {
                            ex.printStackTrace();
                        }

                        JOptionPane.showMessageDialog(null, "reset Password successful");
                        dispose();
                        MainPage mainPage = new MainPage();
                        mainPage.setVisible(true);
                    }
                }
                catch(PasswordException exception){
                    exception.printStackTrace();
                    JOptionPane.showConfirmDialog(null,exception.getMessage());
                    }


            }
        });
    }
}
