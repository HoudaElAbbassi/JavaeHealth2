package GUI.RegisterationPage;

import Exceptions.EmailException;
import Exceptions.PasswordException;
import GUI.Login;
import com.toedter.calendar.JDateChooser;
import user.Doctor.Doctor;
import user.Doctor.DoctorDAOImp;
import user.Doctor.Specialization;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * this class displays to the doctor the registration Page
 * @author Ahmed,Houda,Amine,Parabal,Daniel
 */
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
    /**
     * Constructs an instance which create a frame where the doctor input his data to register.
     */
    public RegisterDoc(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//This method is used to determine one of several options for the close button.
        setContentPane(mainPanel);//This method is used to set the top-level visual element inside a Window
        setSize(800, 500);;//This methode changes the size of the frames according to the given size
        /**
         * ComboBox1 is component that combines the button and the drop-down specialization list.
         * The doctor can select a Specialization from the drop-down list, which appears at the doctor's request.
         * @param Specialization several types, declared as enum
         * */
        comboBox1.setModel(new DefaultComboBoxModel<>(Specialization.values()));
        /**
         * It's the simplest container which provides space with a component
         * @param dateChooser is a calendar where the doctor can choose his date of birth
         * */
        BirthdatePanel.add(dateChooser);
        /**
         * it's used for selecting date, and facilitate type date in "DD/MM/YYYY" format.
         * */
        dateChooser.setDateFormatString("dd/MM/yyyy");

        /**This method enable the doctor to register by filling his data completely
         * @param e is generated when the user has selected that menu item
         * @throws PasswordException ex if the password format is invalid
         * @throws EmailException ex if the email format is invalid
         * @throws Exception ex if the required data have been not completely filled
         * */
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //form has to be completely filled
                try {
                    if (UsernameText.getText().isEmpty() | FirstNameText.getText().isEmpty() |
                            LastnameText.getText().isEmpty() | addressText.getText().isEmpty() | dateChooser.getDate()==null)
                            throw new Exception("please fill out completely! ");

                    boolean registered = false;
                    DoctorDAOImp doctorDAOImp = new DoctorDAOImp();
                    Doctor doctor = new Doctor(UsernameText.getText(), EMailText.getText(), passwordField1.getText(),
                            FirstNameText.getText(), LastnameText.getText(), addressText.getText(),
                            LocalDate.ofInstant(dateChooser.getDate().toInstant(), ZoneId.systemDefault())
                            , comboBox1.getItemAt(comboBox1.getSelectedIndex()));


                    registered = doctorDAOImp.save(doctor);

                    if (registered) {

                        Login login = new Login();
                        setVisible(false);
                        login.setVisible(true);

                    }
                }
                catch (PasswordException ex) {ex.printStackTrace();}
                    catch (EmailException ex) {ex.printStackTrace();} catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        /**
         * By clicking on goBackToMainPage button the doctor would be redirected to MainPage
         * @param e  is generated when the doctor has selected that menu item
         * */
        goBackToMainpageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login mainPage = null;
                try {
                    mainPage = new Login();
                } catch (PasswordException ex) {
                    ex.printStackTrace();
                }
                setVisible(false);
                mainPage.setVisible(true);
            }
        });
   }


}
