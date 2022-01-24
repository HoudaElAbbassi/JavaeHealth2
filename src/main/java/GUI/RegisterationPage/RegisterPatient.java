package GUI.RegisterationPage;

import Exceptions.EmailException;
import Exceptions.PasswordException;
import GUI.Login;
import GUI.MainPage;
import com.toedter.calendar.JDateChooser;
import user.Patient.HealthProblem;
import user.Patient.InsuranceType;
import user.Patient.Patient;
import user.Patient.PatientDAOImp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;

public class RegisterPatient extends  JFrame{

    private JPanel mainPanel;
    private JTextField userNameText;
    private JTextField firstNameText;
    private JTextField lastNameText;
    private JTextField emailText;
    private JPasswordField passwordText;
    private JTextField addressText;
    private JComboBox<InsuranceType> insuranceTypeBox;
    private JTextField insuranceNameText;
    private JPanel BirthdatePanel;
    private JButton registerButton;
    private JButton goBackToMainpageButton;
    private JDateChooser dateChooser=new JDateChooser();

    public RegisterPatient() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(mainPanel);
        setSize(500,500);

        insuranceTypeBox.setModel(new DefaultComboBoxModel(InsuranceType.values()));

        BirthdatePanel.add(dateChooser);
        dateChooser.setDateFormatString("dd/MM/yyyy");

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean registered = false;
                insuranceTypeBox.setModel(new DefaultComboBoxModel(InsuranceType.values()));
                PatientDAOImp patientDAOImp = new PatientDAOImp();
                Patient patient = new Patient(userNameText.getText(), emailText.getText(), passwordText.getText(),
                        firstNameText.getText(), lastNameText.getText(), addressText.getText(),
                        LocalDate.ofInstant(dateChooser.getDate().toInstant(), ZoneId.systemDefault()),
                        insuranceTypeBox.getItemAt(insuranceTypeBox.getSelectedIndex()),
                        insuranceNameText.getText());//,DateofBirthText.getText(),SpecializationText.getText());

                try {
                    registered = patientDAOImp.save(patient);
                } catch (PasswordException ex) {
                    ex.printStackTrace();
                } catch (EmailException ex) {
                    ex.printStackTrace();
                }
                if (registered){
                    try {
                        Login login = new Login();
                        setVisible(false);
                        login.setVisible(true);
                    } catch (PasswordException ex) {
                        ex.printStackTrace();
                    }
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
