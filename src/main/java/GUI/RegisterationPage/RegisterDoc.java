package GUI.RegisterationPage;

import Exceptions.EmailException;
import Exceptions.PasswordException;
import GUI.Login;
import GUI.MainPage;
import com.toedter.calendar.JDateChooser;
import user.Doctor.Doctor;
import user.Doctor.DoctorDAOImp;
import user.Doctor.Specialization;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;

public class RegisterDoc extends JFrame{
    private JPanel mainPanel;
    private JTextField UsernameText;
    private JTextField FirstNameText;
    private JTextField LastnameText;
    private JPasswordField passwordField1;
    private JTextField EMailText;
    private JTextField addressText;
    private JButton registerButton;
    private JComboBox<Specialization> comboBox1;
    private JPanel BirthdatePanel;
    private JButton goBackToMainpageButton;

    private JDateChooser dateChooser=new JDateChooser();


    public RegisterDoc(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(mainPanel);
        setSize(500,500);
        /*
        JFrame frame=new JFrame();
        frame.setSize(300,300);
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
        mainPanel.setVisible(true);

         */
        comboBox1.setModel(new DefaultComboBoxModel<>(Specialization.values()));

        BirthdatePanel.add(dateChooser);
        dateChooser.setDateFormatString("dd/MM/yyyy");


        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DoctorDAOImp doctorDAOImp=new DoctorDAOImp();
                Doctor doctor= new Doctor(UsernameText.getText(),EMailText.getText(),passwordField1.getText(),
                        FirstNameText.getText(),LastnameText.getText(),addressText.getText(),
                LocalDate.ofInstant(dateChooser.getDate().toInstant(), ZoneId.systemDefault())
                ,comboBox1.getItemAt(comboBox1.getSelectedIndex()));

                try {
                    doctorDAOImp.save(doctor);
                    Login login = new Login();
                    setVisible(false);
                    login.setVisible(true);
                } catch (PasswordException ex) {
                    ex.printStackTrace();
                } catch (EmailException ex) {
                    ex.printStackTrace();
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
   }


}
