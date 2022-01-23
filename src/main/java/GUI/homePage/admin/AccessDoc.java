package GUI.homePage.admin;

import GUI.MainPage;
import user.Doctor.Doctor;
import user.Doctor.DoctorDAOImp;
import user.Doctor.Specialization;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AccessDoc extends JFrame {
    private JPanel MainPanel;
    private JPanel PanelTop;
    private JPanel PanelLeft;
    private JPanel PanelRight;
    private JList ListDoctor;
    private JTextField UserNameText;
    private JTextField FirstNameText;
    private JTextField LastNameText;
    private JTextField EmailText;
    private JTextField PasswordField;
    private JTextField AdressText;
    private JButton EditButton;
    private JButton DeleteButton;
    private JComboBox<Specialization> comboBox1;
    private JTextField DoBText;
    private JTextField IdText;
    private JButton patientsButton;
    private JButton logoutButton;
    private List<Doctor> doctors;
    private DoctorDAOImp doctorDAOImp;
    private DefaultListModel listDoctorModel;


    public AccessDoc(){///// The beginning of the constructor
        super("AdminHomePage"); ////// some configuration of the MainPanel
        this.setContentPane(this.MainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        setSize(800,600);
        setVisible(true);
        DeleteButton.setEnabled(false);  ////// Buttons shouldn't be enabled without choosing an item
        EditButton.setEnabled(false);    ////// Buttons shouldn't be enabled without choosing an item
        comboBox1.setModel(new DefaultComboBoxModel<>(Specialization.values())); //// bind the enum "Specialization" with our comboBox1 to provide limited choices.
        doctors=new ArrayList<>(); ///Instantiate a list of doctors
        doctorDAOImp=new DoctorDAOImp();////// Instantiate the data access object implementation doctor
        listDoctorModel=new DefaultListModel();
        ListDoctor.setModel(listDoctorModel);////instantiate the ListDoctor in order to display the data to the admin.
        doctors=doctorDAOImp.getAll();////// get the database
        listDoctorModel.removeAllElements();///// remove everything if the list isn't empty
        for(Doctor d:doctors){
            listDoctorModel.addElement("Id: "+d.getId()+" Date of birth "+d.getBirthDate());////Populate our JList "listDocotr"
        }
        EditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             int DocNbr=ListDoctor.getSelectedIndex();
                if(DocNbr>=0){/// if our JList is empty, it returns -1 and no data selection would appear.
                   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");//// format the Date.
                    LocalDate date = LocalDate.parse(DoBText.getText(),formatter);///// switch from a String to LocalDate
                    Doctor dc= new Doctor(UserNameText.getText(), //// instantiate an object from class doctor with a parametrized constructor.
                            EmailText.getText(),
                            PasswordField.getText(),
                            FirstNameText.getText(),
                            LastNameText.getText(),
                            AdressText.getText(),
                            date,
                            comboBox1.getItemAt(comboBox1.getSelectedIndex()));
                    dc.setId(Long.valueOf(IdText.getText()));
                    doctorDAOImp.edit(dc);////call the methode edit from the class doctorDAOImp
                    refreshList();//// to display the new changed data
                }
            }
        });
        DeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int DocNbr=ListDoctor.getSelectedIndex();
                if(DocNbr>=0){/// if our JList is empty, it returns -1 and no data selection would appear.
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //// format the Date.
                    LocalDate date = LocalDate.parse(DoBText.getText(),formatter); ///// switch from a String to LocalDate
                    Doctor doctor= new Doctor(UserNameText.getText(), //// instantiate an object from class doctor with a parametrized constructor.
                            EmailText.getText(),
                            PasswordField.getText(),
                            FirstNameText.getText(),
                            LastNameText.getText(),
                            AdressText.getText(),
                            date,
                            comboBox1.getItemAt(comboBox1.getSelectedIndex()));
                    doctorDAOImp.deleteByID(Long.valueOf( IdText.getText())); ////call the methode delete from the class doctorDAOImp
                    refreshList(); //// to display the new changed data
                }

            }
        });
        ListDoctor.addListSelectionListener(new ListSelectionListener() {///it allows the admin to replace the whole data to the text field just on clicking and choosing it from the JList.
            @Override
            public void valueChanged(ListSelectionEvent e) {
               int DocNbr=ListDoctor.getSelectedIndex(); /// if our JList is empty, it returns -1 and no data selection would appear.
               if(DocNbr>=0){
                   Doctor d=doctors.get(DocNbr);
                   IdText.setText(String.valueOf(d.getId()));
                   UserNameText.setText(d.getUserName());
                   FirstNameText.setText(d.getFirstName());
                   LastNameText.setText(d.getLastName());
                   EmailText.setText(d.getEmail());
                   PasswordField.setText(d.getPassword());
                   AdressText.setText(d.getAddress());
                   DoBText.setText(d.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                   comboBox1.setSelectedItem(d.getSpecialization());
                   DeleteButton.setEnabled(true);/////Button would be enabled after selecting an item from the JList
                   EditButton.setEnabled(true);/////Button would be enabled after selecting an item from the JList
               }else{
                   DeleteButton.setEnabled(false);////else the Button would be still disabled.
                   EditButton.setEnabled(false);
               }
            }
        });
        logoutButton.addActionListener(new ActionListener() {///// Button to logout and return to Homepage
            @Override
            public void actionPerformed(ActionEvent e) {
               MainPage mainPage=new MainPage();
               dispose();
            }
        });
        patientsButton.addActionListener(new ActionListener() {////Button to switch to patient access page
            @Override
            public void actionPerformed(ActionEvent e) {
                AccessPat accessPat=new AccessPat();
                dispose();
            }
        });
    }
    public void refreshList(){ ///// refresh the doctor selectionList after every change which the admin make.
        listDoctorModel.removeAllElements();
        doctors=doctorDAOImp.getAll();///// get the whole doctors from the database
        for(Doctor d:doctors){
            listDoctorModel.addElement("Id: "+d.getId()+" Date of birth "+d.getBirthDate().toString());///display the Id and the Birthdate of every doctor in the data base.
        }
    }

}
