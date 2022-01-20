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
        frame.setSize(800,600);
        frame.setContentPane(MainPanel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainPanel.setVisible(true);


        DeleteButton.setEnabled(false);////// Buttons shouldn't be enabled without choosing an item
        EditButton.setEnabled(false);////// Buttons shouldn't be enabled without choosing an item
        comboBox2.setModel(new DefaultComboBoxModel<>(InsuranceType.values()));//// bind the enum "InsuranceType" with our comboBox1 to provide limited choices.
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
                    patientDAOImp.edit(patient);////call the methode edit from the class patientDAOImp
                    refreshListPatient();/// to display the new changed data
                }
            }
        });
        DeleteButton.addActionListener(new ActionListener() {
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
        ListPatient.addListSelectionListener(new ListSelectionListener() {///it allows the admin to replace the whole data to the text field just on clicking and choosing it from the JList.
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
                    PasswordField.setText(p.getPassword());
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
        DoctorsButton.addActionListener(new ActionListener() {////Button to switch to doctor access page
            @Override
            public void actionPerformed(ActionEvent e) {
                AccessDoc accessDoc=new AccessDoc();
                frame.dispose();
            }
        });
        logoutButton.addActionListener(new ActionListener() {//// Button to logout and return to Homepage
            @Override
            public void actionPerformed(ActionEvent e) {
                MainPage mainPage=new MainPage();
                frame.dispose();
            }
        });
    }
    public void refreshListPatient(){/// refresh the doctor selectionList after every change which the admin make.
        listPatientModel.removeAllElements();
        patients=patientDAOImp.getAll();/////get every patient from the database
        for(Patient p: patients){
            listPatientModel.addElement("Id: "+p.getId()+" Date of birth "+p.getBirthDate());
        }
    }
}
