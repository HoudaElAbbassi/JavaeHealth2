package GUI.homePage.admin;


import GUI.MainPage;
import user.Patient.InsuranceType;
import user.Patient.Patient;
import user.Patient.PatientDAOImp;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * this class displays to the admin a list of all patients profiles furthermore it enables him to edit or delete them
 *
 * @author Mohamed Amine Belrhazi
 */
public class AccessPat {
    private JPanel MainPanel;
    private JList ListPatient;
    private JTextField UserNameText;
    private JTextField FirstNameText;
    private JTextField LastNameText;
    private JTextField EmailText;
    private JTextField PasswordField;
    private JTextField AdressText;
    private JButton EditButton;
    private JButton DeleteButton;
    private JTextField DoBText;
    private JTextField IdText;
    private JPanel PanelTop;
    private JButton DoctorsButton;
    private JButton logoutButton;
    private JPanel PanelLeft;
    private JPanel PanelRight;
    private JLabel Patients;
    private JTextField InsText;
    private JComboBox<InsuranceType> comboBox2;
    private JTextField InsuranceNameText;
    private JLabel InsuranceName;
    private List<Patient> patients;
    private PatientDAOImp patientDAOImp;
    private DefaultListModel listPatientModel;

    public AccessPat(){//// The beginning of the constructor
        /*setContentPane(MainPanel);
        setSize(500,500);*/
        JFrame frame=new JFrame(); ////// some configuration of the MainPanel
        /**
         *This method changes the size of the frames according to the given size
         * */
        frame.setSize(800,600);
        /** This method is used to set the top-level visual element inside a Window
         * @param MainPanel which stores our group of components
         * */
        frame.setContentPane(MainPanel);
        /**
         *This method is used to display the frame to the user
         * */
        frame.setVisible(true);
        /**
         * This method is used to determine one of several options for the close button.
         * @param Frame.DISPOSE_ON_CLOSE which discards The frame object ,but the application continues to run.
         * */
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        MainPanel.setVisible(true);
        /**
         * This method doesn't enable DeleteButtons until choosing an item from patientList
         * */
        DeleteButton.setEnabled(false);
        /**
         * This method doesn't enable EditButtons until choosing an item from patientList
         * */
        EditButton.setEnabled(false);
        /**
         * setModel bind the enum "InsuranceType" with our comboBox1 to provide limited choices
         * */
        comboBox2.setModel(new DefaultComboBoxModel<>(InsuranceType.values()));
        patients=new ArrayList<>();///Instantiate a list of patients
        patientDAOImp=new PatientDAOImp();////// Instantiate the data access object implementation patient
        listPatientModel=new DefaultListModel();//
        ListPatient.setModel(listPatientModel);////instantiate the ListPatient in order to display the data to the admin.
        patients=patientDAOImp.getAll();////// get the database
        listPatientModel.removeAllElements();//// remove everything if the list isn't empty
        for(Patient p:patients){
            listPatientModel.addElement("Id: "+p.getId()+" Date of birth "+p.getBirthDate());////Populate our JList "listPatient"
        }

        EditButton.addActionListener(new ActionListener() {
            /**
             * Using this methode, admin have the access to edit patient's info
             * @param e is generated when the admin has selected that menu item
             * */
            @Override
            public void actionPerformed(ActionEvent e) {
                int PatNbr=ListPatient.getSelectedIndex();
                if(PatNbr>=0){/// if our JList is empty, it returns -1 and no data selection would appear.
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");//// format the Date.
                    LocalDate date = LocalDate.parse(DoBText.getText(),formatter);///// switch from a String to LocalDate
                    Patient patient= new Patient(UserNameText.getText(),//// instantiate an object from class patient with a parametrized constructor.
                            EmailText.getText(),
                            PasswordField.getText(),
                            FirstNameText.getText(),
                            LastNameText.getText(),
                            AdressText.getText(),
                            date,
                            comboBox2.getItemAt(comboBox2.getSelectedIndex()),
                            InsuranceName.getText());
                    patient.setId(Long.valueOf(IdText.getText()));
                    patientDAOImp.editByAdmin(patient);////call the methode edit from the class patientDAOImp
                    refreshListPatient();/// to display the new changed data
                }
            }
        });
        DeleteButton.addActionListener(new ActionListener() {
            /**
             * Using this methode, admin have the access to delete patients from the database
             * @param e is generated when the admin has selected that menu item
             * */
            @Override
            public void actionPerformed(ActionEvent e) {
                int PatNbr=ListPatient.getSelectedIndex();
                if(PatNbr>=0){
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate date = LocalDate.parse(DoBText.getText(),formatter);
                    ///Patient patient=new Patient();
                    patientDAOImp.deleteByID(Long.valueOf( IdText.getText()));////call the methode delete from the class patientDAOImp
                    refreshListPatient();
                }


            }
        });
        ListPatient.addListSelectionListener(new ListSelectionListener() {
            /**
             * This methode allows the admin to replace the whole data to the text field just on clicking and choosing it from the JList.
             * @param e is generated when the user has selected that menu item
             * */
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int PatNbr=ListPatient.getSelectedIndex();
                if(PatNbr>=0){/// if our JList is empty, it returns -1 and no data selection would appear.
                    Patient p=patients.get(PatNbr);
                    IdText.setText(String.valueOf(p.getId()));
                    UserNameText.setText(p.getUserName());
                    FirstNameText.setText(p.getFirstName());
                    LastNameText.setText(p.getLastName());
                    EmailText.setText(p.getEmail());
                    PasswordField.setText("*********");
                    AdressText.setText(p.getAddress());
                    DoBText.setText(p.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    comboBox2.setSelectedItem(p.getInsuranceType());
                    InsuranceNameText.setText(p.getInsuranceName());
                    DeleteButton.setEnabled(true);////Button would be enabled after selecting an item from the JList
                    EditButton.setEnabled(true);////Button would be enabled after selecting an item from the JList
                }else{
                    DeleteButton.setEnabled(false);
                    EditButton.setEnabled(false);
                }

            }
        });
        DoctorsButton.addActionListener(new ActionListener() {
            /**
             * By clicking on patient button the admin switches to AccessDoctor page
             * @param e  is generated when the user has selected that menu item
             * */
            @Override
            public void actionPerformed(ActionEvent e) {
                AccessDoc accessDoc=new AccessDoc();
                frame.dispose();
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            /**
             * By clicking on LogoutButton the admin would be redirected to MainPage
             * @param e  is generated when the user has selected that menu item
             * */
            @Override
            public void actionPerformed(ActionEvent e) {
                MainPage mainPage=new MainPage();
                frame.dispose();
                mainPage.setVisible(true);
            }
        });
        DeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
    public void refreshListPatient(){
        /**
         * This method refreshes the patient selectionList after every change which the admin makes
         * */
        listPatientModel.removeAllElements();
        patients=patientDAOImp.getAll();/////get every patient from the database
        for(Patient p: patients){
            listPatientModel.addElement("Id: "+p.getId()+" Date of birth "+p.getBirthDate());
        }
    }
}
