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

/**
 * this class displays to the patient the registration Page
 * @author
 */
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
    /**
     * Constructs an instance which create a frame where the doctor input his data to register.
     */
    public RegisterPatient() {
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
         *This methode changes the size of the frames according to the given size
         * */
        setSize(500,500);
        /**
         * ComboBox1 is component that combines the button and the drop-down InsuranceType list.
         * The patient can select a InsuranceType from the drop-down list, which appears at the patient's request.
         * @param InsuranceType several types, declared as enum
         * */
        insuranceTypeBox.setModel(new DefaultComboBoxModel(InsuranceType.values()));
        /**
         * It's the simplest container which provides space with a component
         * @param dateChooser is a calendar where the patient can choose his date of birth
         * */
        BirthdatePanel.add(dateChooser);
        /**
         * it's used for selecting date, and facilitate type date in "DD/MM/YYYY" format.
         * */
        dateChooser.setDateFormatString("dd/MM/yyyy");

        registerButton.addActionListener(new ActionListener() {
            /**This method enable the patient to register by filling his data completely
             * @param e is generated when the user has selected that menu item
             * @throws PasswordException ex if the password format is invalid
             * @throws EmailException ex if the email format is invalid
             * @throws Exception ex if the required data have been not completely filled
             * */
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
            /**
             * By clicking on goBackToMainPage button the patient would be redirected to MainPage
             * @param e  is generated when the patient has selected that menu item
             * */
            public void actionPerformed(ActionEvent e) {
                MainPage mainPage = new MainPage();
                setVisible(false);
                mainPage.setVisible(true);
            }
        });
    }

}
