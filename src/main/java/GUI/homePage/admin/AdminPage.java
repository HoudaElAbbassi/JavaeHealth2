package GUI.homePage.admin;

import Exceptions.EmailException;
import Exceptions.PasswordException;
import GUI.Login;
import user.Doctor.Doctor;
import user.Doctor.DoctorDAOImp;
import user.Doctor.Specialization;
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

public class AdminPage extends JFrame{
    private JTabbedPane tabbedPane1;
    private JButton logoutButton;
    private JList ListDoctor;
    private JComboBox<Specialization> comboBoxDoctor;
    private JPanel MainPanel;
    private JPanel PanelLeft;
    private JList ListPatient;
    private JPanel PanelRight;
    private JTextField UserNamePatient;
    private JTextField FirstNamePatient;
    private JTextField LastNamePatient;
    private JTextField EmailPatient;
    private JTextField PasswordPatient;
    private JTextField AdressPatient;
    private JButton editPatient;
    private JButton deletePatient;
    private JTextField DoBPatient;
    private JTextField IdPatient;
    private JTextField InsuranceNameText;
    private JComboBox<InsuranceType> comboBoxPatient;
    private JLabel InsuranceName;
    private JButton editDoctor;
    private JButton deleteDoctor;
    private JTextField IdDoctor;
    private JTextField UserNameDoctor;
    private JTextField FirstNameDoctor;
    private JTextField LastNameDoctor;
    private JTextField EmailDoctor;
    private JTextField PasswordDoctor;
    private JTextField AdressDoctor;
    private JTextField DoBDoctor;

    private List<Doctor> doctors;
    private DoctorDAOImp doctorDAOImp;
    private DefaultListModel listDoctorModel;

    private List<Patient> patients;
    private PatientDAOImp patientDAOImp;
    private DefaultListModel listPatientModel;

    public AdminPage() {

        /**
         * it's used to set a title to the frame
         * */
        super("AdminHomePage");
        /** This method is used to set the top-level visual element inside a Window
         * @param MainPanel which stores our group of components
         * */
        this.setContentPane(this.MainPanel);
        /**
         * This method is used to determine one of several options for the close button.
         * @param Frame.DISPOSE_ON_CLOSE which discards The frame object ,but the application continues to run.
         * */
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        /**
         *  This method sizes the frame so that all its contents are at or above their preferred sizes
         * */
        this.pack();
        /**
         *This method changes the size of the frames according to the given size
         * */
        setSize(800,600);
        /**
         *This method is used to display the frame to the user
         * */
        setVisible(true);
        /**
         * This method doesn't enable DeleteButtons until choosing an item from doctorList
         * */
        deleteDoctor.setEnabled(false);
        deletePatient.setEnabled(false);
        /**
         * This method doesn't enable EditButtons until choosing an item from doctorList
         * */
        editPatient.setEnabled(false);
        editDoctor.setEnabled(false);
        /**
         * setModel bind the enum "Specialization" with our comboBox1 to provide limited choices
         * */
        comboBoxDoctor.setModel(new DefaultComboBoxModel<>(Specialization.values()));
        doctors=new ArrayList<>(); ///Instantiate a list of doctors
        doctorDAOImp=new DoctorDAOImp();////// Instantiate the data access object implementation doctor
        listDoctorModel=new DefaultListModel();
        ListDoctor.setModel(listDoctorModel);////instantiate the ListDoctor in order to display the data to the admin.
        doctors=doctorDAOImp.getAll();////// get the database
        listDoctorModel.removeAllElements();///// remove everything if the list isn't empty
        for(Doctor d:doctors) {
            listDoctorModel.addElement("Id: " + d.getId() + " Date of birth " + d.getBirthDate());////Populate our JList "listDocotr"
        }


        /**
         * setModel bind the enum "InsuranceType" with our comboBox1 to provide limited choices
         * */
        comboBoxPatient.setModel(new DefaultComboBoxModel<>(InsuranceType.values()));
        patients=new ArrayList<>();///Instantiate a list of patients
        patientDAOImp=new PatientDAOImp();////// Instantiate the data access object implementation patient
        listPatientModel=new DefaultListModel();//
        ListPatient.setModel(listPatientModel);////instantiate the ListPatient in order to display the data to the admin.
        patients=patientDAOImp.getAll();////// get the database
        listPatientModel.removeAllElements();//// remove everything if the list isn't empty
        for(Patient p:patients){
            listPatientModel.addElement("Id: "+p.getId()+" Date of birth "+p.getBirthDate());////Populate our JList "listPatient"
        }


            editDoctor.addActionListener(new ActionListener() {
                @Override
                /**
                 * Using this methode, admin have the access to edit doctor's info
                 * @param e is generated when the admin has selected that menu item
                 * */
                public void actionPerformed(ActionEvent e) {
                    int DocNbr=ListDoctor.getSelectedIndex();
                    if(DocNbr>=0){/// if our JList is empty, it returns -1 and no data selection would appear.
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");//// format the Date.
                        LocalDate date = LocalDate.parse(DoBDoctor.getText(),formatter);///// switch from a String to LocalDate
                        Doctor dc= new Doctor(UserNameDoctor.getText(), //// instantiate an object from class doctor with a parametrized constructor.
                                EmailDoctor.getText(),
                                PasswordDoctor.getText(),
                                FirstNameDoctor.getText(),
                                LastNameDoctor.getText(),
                                AdressDoctor.getText(),
                                date,
                                comboBoxDoctor.getItemAt(comboBoxDoctor.getSelectedIndex()));
                        dc.setId(Long.valueOf(IdDoctor.getText()));
                        try {
                            doctorDAOImp.editByAdmin(dc);////call the methode edit from the class doctorDAOImp
                        } catch (EmailException ex) {
                            ex.printStackTrace();
                        }
                        refreshListDoctor();//// to display the new changed data
                    }
                }
            });


        deleteDoctor.addActionListener(new ActionListener() {
            /**
             * Using this methode, admin have the access to delete doctors from the database
             * @param e is generated when the admin has selected that menu item
             * */
            @Override
            public void actionPerformed(ActionEvent e) {
                int DocNbr=ListDoctor.getSelectedIndex();
                if(DocNbr>=0){/// if our JList is empty, it returns -1 and no data selection would appear.
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //// format the Date.
                    LocalDate date = LocalDate.parse(DoBDoctor.getText(),formatter); ///// switch from a String to LocalDate
                    Doctor doctor= new Doctor(/*UserNamePatient.getText(), //// instantiate an object from class doctor with a parametrized constructor.
                            EmailPatient.getText(),
                            PasswordPatient.getText(),
                            FirstNamePatient.getText(),
                            LastNamePatient.getText(),
                            AdressText.getText(),
                            date,
                            comboBox1.getItemAt(comboBox1.getSelectedIndex())*/);
                    doctorDAOImp.deleteByID(Long.valueOf( IdDoctor.getText())); ////call the methode delete from the class doctorDAOImp
                    refreshListDoctor(); //// to display the new changed data
                }

            }
        });


        editPatient.addActionListener(new ActionListener() {
            /**
             * Using this methode, admin have the access to edit patient's info
             * @param e is generated when the admin has selected that menu item
             * */
            @Override
            public void actionPerformed(ActionEvent e) {
                int PatNbr=ListPatient.getSelectedIndex();
                if(PatNbr>=0){/// if our JList is empty, it returns -1 and no data selection would appear.
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");//// format the Date.
                    LocalDate date = LocalDate.parse(DoBPatient.getText(),formatter);///// switch from a String to LocalDate
                    Patient patient= new Patient(UserNamePatient.getText(),//// instantiate an object from class patient with a parametrized constructor.
                            EmailPatient.getText(),
                            PasswordPatient.getText(),
                            FirstNamePatient.getText(),
                            LastNamePatient.getText(),
                            AdressPatient.getText(),
                            date,
                            comboBoxPatient.getItemAt(comboBoxPatient.getSelectedIndex()),
                            InsuranceName.getText());
                    patient.setId(Long.valueOf(IdPatient.getText()));
                    patientDAOImp.editByAdmin(patient);////call the methode edit from the class patientDAOImp
                    refreshListPatient();/// to display the new changed data
                }
            }
        });
        deletePatient.addActionListener(new ActionListener() {
            /**
             * Using this methode, admin have the access to delete patients from the database
             * @param e is generated when the admin has selected that menu item
             * */
            @Override
            public void actionPerformed(ActionEvent e) {
                int PatNbr=ListPatient.getSelectedIndex();
                if(PatNbr>=0){
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate date = LocalDate.parse(DoBPatient.getText(),formatter);
                    ///Patient patient=new Patient();
                    patientDAOImp.deleteByID(Long.valueOf( IdPatient.getText()));////call the methode delete from the class patientDAOImp
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
                    IdPatient.setText(String.valueOf(p.getId()));
                    UserNamePatient.setText(p.getUserName());
                    FirstNamePatient.setText(p.getFirstName());
                    LastNamePatient.setText(p.getLastName());
                    EmailPatient.setText(p.getEmail());
                    PasswordPatient.setText("*********");
                    AdressPatient.setText(p.getAddress());
                    DoBPatient.setText(p.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    comboBoxPatient.setSelectedItem(p.getInsuranceType());
                    InsuranceNameText.setText(p.getInsuranceName());
                    deletePatient.setEnabled(true);////Button would be enabled after selecting an item from the JList
                    editPatient.setEnabled(true);////Button would be enabled after selecting an item from the JList
                }else{
                    deletePatient.setEnabled(false);
                    editPatient.setEnabled(false);
                }

            }
        });
        ListDoctor.addListSelectionListener(new ListSelectionListener() {
            /**
             * This methode allows the admin to replace the whole data to the text field just on clicking and choosing it from the JList.
             * @param e is generated when the user has selected that menu item
             * */
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int DocNbr=ListDoctor.getSelectedIndex(); /// if our JList is empty, it returns -1 and no data selection would appear.
                if(DocNbr>=0){
                    Doctor d=doctors.get(DocNbr);
                    IdDoctor.setText(String.valueOf(d.getId()));
                    UserNameDoctor.setText(d.getUserName());
                    FirstNameDoctor.setText(d.getFirstName());
                    LastNameDoctor.setText(d.getLastName());
                    EmailDoctor.setText(d.getEmail());
                    PasswordDoctor.setText("********");
                    AdressDoctor.setText(d.getAddress());
                    DoBDoctor.setText(d.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    comboBoxDoctor.setSelectedItem(d.getSpecialization());
                    deleteDoctor.setEnabled(true);/////Button would be enabled after selecting an item from the JList
                    editDoctor.setEnabled(true);/////Button would be enabled after selecting an item from the JList
                }else{
                    deleteDoctor.setEnabled(false);////else the Button would be still disabled.
                    editDoctor.setEnabled(false);
                }
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login= null;
                try {
                    login = new Login();
                } catch (PasswordException ex) {
                    ex.printStackTrace();
                }
                setVisible(false);
                login.setVisible(true);
            }
        });
    }
    public void refreshListDoctor(){
        listDoctorModel.removeAllElements();
        doctors=doctorDAOImp.getAll();///// get the whole doctors from the database
        for(Doctor d:doctors){
            listDoctorModel.addElement("Id: "+d.getId()+" Date of birth "+d.getBirthDate().toString());///display the Id and the Birthdate of every doctor in the data base.
        }
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

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        AdminPage adminPage=new AdminPage();
        adminPage.setVisible(true);
    }

}


