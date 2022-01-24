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

                try {
                    if (userNameText.getText().isEmpty() | emailText.getText().isEmpty() |
                            passwordText.getText().isEmpty() | firstNameText.getText().isEmpty() |
                            lastNameText.getText().isEmpty() | addressText.getText().isEmpty() | dateChooser.getDate() == null
                    ) throw new Exception("please fill out completely! ");

                    boolean registered = false;
                    insuranceTypeBox.setModel(new DefaultComboBoxModel(InsuranceType.values()));
                    PatientDAOImp patientDAOImp = new PatientDAOImp();
                    Patient patient = new Patient(userNameText.getText(), emailText.getText(), passwordText.getText(),
                            firstNameText.getText(), lastNameText.getText(), addressText.getText(),
                            LocalDate.ofInstant(dateChooser.getDate().toInstant(), ZoneId.systemDefault()),
                            insuranceTypeBox.getItemAt(insuranceTypeBox.getSelectedIndex()),
                            insuranceNameText.getText());

                        registered = patientDAOImp.save(patient);

                    if (registered) {
                            Login login = new Login();
                            setVisible(false);
                            login.setVisible(true);

                    }
                }catch (PasswordException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                } catch (EmailException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                } catch (Exception ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,ex.getMessage());
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
