package GUI.homePage.admin;

import Exceptions.EmailException;
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
/**
 * this class displays to the admin a list of all doctors profiles furthermore it enables him to edit or delete them
 *
 * @author Mohamed Amine Belrhazi
 */
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

    /**
     * Constructs an instance which create a frame where the admin access to all doctors and can edit or delete their profiles.
     * */
    public AccessDoc(){
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
        DeleteButton.setEnabled(false);
        /**
         * This method doesn't enable EditButtons until choosing an item from doctorList
         * */
        EditButton.setEnabled(false);
        /**
         * setModel bind the enum "Specialization" with our comboBox1 to provide limited choices
         * */
        comboBox1.setModel(new DefaultComboBoxModel<>(Specialization.values()));
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
            /**
             * Using this methode, admin have the access to edit doctor's info
             * @param e is generated when the admin has selected that menu item
             * */
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
                    try {
                        doctorDAOImp.editByAdmin(dc);////call the methode edit from the class doctorDAOImp
                    } catch (EmailException ex) {
                        ex.printStackTrace();
                    }
                    refreshList();//// to display the new changed data
                }
            }
        });
        DeleteButton.addActionListener(new ActionListener() {
            /**
             * Using this methode, admin have the access to delete doctors from the database
             * @param e is generated when the admin has selected that menu item
             * */
            @Override
            public void actionPerformed(ActionEvent e) {
                int DocNbr=ListDoctor.getSelectedIndex();
                if(DocNbr>=0){/// if our JList is empty, it returns -1 and no data selection would appear.
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //// format the Date.
                    LocalDate date = LocalDate.parse(DoBText.getText(),formatter); ///// switch from a String to LocalDate
                    Doctor doctor= new Doctor(/*UserNameText.getText(), //// instantiate an object from class doctor with a parametrized constructor.
                            EmailText.getText(),
                            PasswordField.getText(),
                            FirstNameText.getText(),
                            LastNameText.getText(),
                            AdressText.getText(),
                            date,
                            comboBox1.getItemAt(comboBox1.getSelectedIndex())*/);
                    doctorDAOImp.deleteByID(Long.valueOf( IdText.getText())); ////call the methode delete from the class doctorDAOImp
                    refreshList(); //// to display the new changed data
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
                   IdText.setText(String.valueOf(d.getId()));
                   UserNameText.setText(d.getUserName());
                   FirstNameText.setText(d.getFirstName());
                   LastNameText.setText(d.getLastName());
                   EmailText.setText(d.getEmail());
                   PasswordField.setText("********");
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
        logoutButton.addActionListener(new ActionListener() {
            /**
             * By clicking on LogoutButton the admin would be redirected to MainPage
             * @param e  is generated when the user has selected that menu item
             * */
            @Override
            public void actionPerformed(ActionEvent e) {
               MainPage mainPage=new MainPage();
               dispose();
               mainPage.setVisible(true);
            }
        });
        patientsButton.addActionListener(new ActionListener() {////Button to switch to patient access page
            /**
             * By clicking on patient button the admin switches to AccessPatient page
             * @param e  is generated when the user has selected that menu item
             * */
            @Override
            public void actionPerformed(ActionEvent e) {
                AccessPat accessPat=new AccessPat();
                dispose();
            }
        });
    }
    /**
     * This method refreshes the doctor selectionList after every change which the admin makes
     * */
    public void refreshList(){
        listDoctorModel.removeAllElements();
        doctors=doctorDAOImp.getAll();///// get the whole doctors from the database
        for(Doctor d:doctors){
            listDoctorModel.addElement("Id: "+d.getId()+" Date of birth "+d.getBirthDate().toString());///display the Id and the Birthdate of every doctor in the data base.
        }
    }

}
