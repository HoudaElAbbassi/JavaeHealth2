package GUI.homePage.doctor;


import Exceptions.PasswordException;
import Exceptions.ScheduleException;
import GUI.Login;
import GUI.MainPage;
import appointments.AppointmentDAOImp;
import appointments.schedule.Schedule;
import appointments.schedule.ScheduleDAOImp;
import appointments.schedule.Status;
import com.github.lgooddatepicker.components.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import user.Doctor.Doctor;
import user.Patient.PatientDAOImp;
import utilities.Mailer;

import javax.mail.MessagingException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.io.File;

/**
 * This class is the Homepage of the doctor. The doctor has the possibility
 * to add or delete slots to the Schedule and can add multiple dates.
 * The doctor also has an overview of his booked appointments and can cancel if necessary.
 * @author Houda El Abbassi
 */
public class DoctorHomePage extends JFrame{

    private JPanel mainPanel;
    private JTable AppointmentTable;
    private JButton addToScheduleButton;
    private JButton viewAppointmentsButton;
    private JPanel DatePanel;
    private JPanel TimePanelStart;
    private JPanel StartDate;
    private JPanel EndDate;
    private JPanel startTime;
    private JPanel EndTime;
    private JTable ScheduleTable;
    private JButton viewScheduleButton;
    private JButton deleteFromScheduleButton;
    private JButton addMultipleDatesToButton;
    private JButton cancelAppointmentButton;
    private JButton logoutButton;
    private JLabel UsernameTextField;
    private JButton exportHealthinfoButton;
    private JButton importHealthinfoButton;
    //private JPanel TimePanelEnd;
    private JFileChooser fileChooser = new JFileChooser();
    File file = null;


    TimePickerSettings timePickerSettings=new TimePickerSettings();


    private DatePicker datePicker=new DatePicker();
    private TimePicker timePicker=new TimePicker(timePickerSettings);

    private DatePicker datePicker2=new DatePicker();
    private TimePicker timePicker2=new TimePicker(timePickerSettings);

    private DatePicker datePicker3=new DatePicker();
    private TimePicker timePicker3=new TimePicker(timePickerSettings);

    private DatePicker datePicker4=new DatePicker();
    private TimePicker timePicker4=new TimePicker(timePickerSettings);


    /**
     * All the Actions of GUI(ButtonClicks) are implemented in the Constructor.
     * @param doctor The doctor that is currently logged in
     */

    public DoctorHomePage(Doctor doctor) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(mainPanel);
        setSize(800, 500);



        UsernameTextField.setText(doctor.getFirstName()+" "+doctor.getLastName());

        //load the datePicker to the Panels..

        //..for add one date to the schedule
        DatePanel.add(datePicker);
        TimePanelStart.add(timePicker);
        //..add multiple dates to the schedule
        StartDate.add(datePicker2);
        EndDate.add(datePicker3);
        startTime.add(timePicker2);
        EndTime.add(timePicker3);

        //setting for the time slots
        TimePickerSettings.TimeIncrement time_slot= TimePickerSettings.TimeIncrement.FifteenMinutes;
        timePickerSettings.generatePotentialMenuTimes(time_slot, LocalTime.of(10, 00), LocalTime.of(12, 00));
        new TimePicker(timePickerSettings);

        //settings for cancel/delete from the table=> one of a time
            ScheduleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            AppointmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        //Button Action

        /**
         * The viewAppointments Button is activated with a click on it.
         * a table is created that fetches all the needed data from the database
         */
        viewAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppointmentDAOImp appointmentDAOImp=new AppointmentDAOImp();
                PatientDAOImp patientDAOImp=new PatientDAOImp();
                ScheduleDAOImp scheduleDAOImp=new ScheduleDAOImp();
                DefaultTableModel tbModel = (DefaultTableModel) AppointmentTable.getModel();

                //resets the table
                tbModel.setRowCount(0);

                Object[] columnsName=new Object[7];

                columnsName[0]="ID";
                columnsName[1]="First name";
                columnsName[2]="surname";
                columnsName[3]="Date";
                columnsName[4]="Time";
                columnsName[5]="Healthproblem";
                columnsName[6]="Healthinfo";


                tbModel.setColumnIdentifiers(columnsName);

                Object[] rowData=new Object[7];

                for(int i=0; i<appointmentDAOImp.getAllByDoctorId(doctor.getId()).size();i++){
                    rowData[0]=appointmentDAOImp.getAllByDoctorId(doctor.getId()).get(i).getId();
                    rowData[1]=patientDAOImp.getFirstNameByID(appointmentDAOImp.getAllByDoctorId(doctor.getId()).get(i).getPatientId());
                    rowData[2]=patientDAOImp.getLastNameByID(appointmentDAOImp.getAllByDoctorId(doctor.getId()).get(i).getPatientId());
                    rowData[3]=scheduleDAOImp.getDateByDoctorId(appointmentDAOImp.getAllByDoctorId(doctor.getId()).get(i).getDoctorId());
                    rowData[4]=scheduleDAOImp.getTimeByDoctorId(appointmentDAOImp.getAllByDoctorId(doctor.getId()).get(i).getDoctorId());
                    rowData[5]=appointmentDAOImp.getHealthProblemById(appointmentDAOImp.getAllByDoctorId(doctor.getId()).get(i).getId()).toString();
                    rowData[6]=appointmentDAOImp.getAllByDoctorId(doctor.getId()).get(i).getHealthInfo();

                    tbModel.addRow(rowData);

                }
                AppointmentTable.setModel(tbModel);

            }
        });

        /**
         *  When the "add to schedule"-button is pressed, the Methode initializes a schedule-Object.This Object is being
         * saved in the Database. This class uses the class ScheduleDAO to manipulate the database.
         *  If a Exception is being thrown by a Methode of ScheduleDAO, the Exception is being handled.
         */
        addToScheduleButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                LocalTime start = timePicker.getTime();
                LocalDate date = datePicker.getDate();

                //check if schedule already in the DB
                Schedule schedule = new Schedule(doctor.getId(), date, start,
                        start.plusMinutes(TimePickerSettings.TimeIncrement.FifteenMinutes.minutes), Status.available);

                ScheduleDAOImp scheduleDAOImp = new ScheduleDAOImp();

                try {
                    scheduleDAOImp.addSchedule(schedule);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            }
        });

        /**
         * the "view Schedule"-Button is activated with a click on it.
         * the schedule Table is created that fetches all needed data from the database
         */
        viewScheduleButton.addActionListener(new ActionListener() {
            /**
             * This Methode gets the Schedule from the Database and displays it in a Table. Each row represents the
             * relevant datasets from the table schedule.
             * @exception SQLException is thrown when the fetching from the databse is failed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                    try {
                        ScheduleDAOImp scheduleDAOImp = new ScheduleDAOImp();
                        DefaultTableModel tbModel = (DefaultTableModel) ScheduleTable.getModel();

                        //resets the table
                        tbModel.setRowCount(0);

                        //set column names
                        Object[] columnsName = new Object[4];
                        columnsName[0] = "ID";
                        columnsName[1] = "Date";
                        columnsName[2] = "Start";
                        columnsName[3] = "End";
                        tbModel.setColumnIdentifiers(columnsName);

                        //fills in the rows
                        Object[] rowData = new Object[4];

                        for (int i = 0; i < scheduleDAOImp.getAll(doctor.getId()).size(); i++) {

                            rowData[0] = scheduleDAOImp.getAll(doctor.getId()).get(i).getScheduleId();
                            rowData[1] = scheduleDAOImp.getAll(doctor.getId()).get(i).getDate();
                            rowData[2] = scheduleDAOImp.getAll(doctor.getId()).get(i).getStart();
                            rowData[3] = scheduleDAOImp.getAll(doctor.getId()).get(i).getEnd();

                            tbModel.addRow(rowData);

                        }
                        ScheduleTable.setModel(tbModel);
                    }catch (SQLException exception){
                        exception.printStackTrace();
                    }


            }
        });
        /**
         * the doctor has the possibility to delete a slot from the schedule by selecting the row.
         * If the selected slot is already booked the deletion is not possible, if otherwise the slot gets deleted.
         * The deletion has to get confirmed before the slot is deleted from the table schedule in the database.
         */
        deleteFromScheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ScheduleDAOImp scheduleDAOImp = new ScheduleDAOImp();

                    //get selected row and confirm to delete
                    int i = Integer.parseInt( ScheduleTable.getValueAt(ScheduleTable.getSelectedRow(), 0).toString());

                    int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "choose", JOptionPane.YES_NO_OPTION);


                    //if schedule that has to deleted is already booked, than cancel appointment first
                    if (scheduleDAOImp.getStatusById(i) == Status.booked)
                        throw new ScheduleException("Schedule already booked, please cancel Appointment");
                    //if delete is confirmed
                    if (input == 0) {

                        scheduleDAOImp.deleteById(i);
                    }

                    //refreshing the table
                    viewScheduleButton.doClick();
                }catch (ScheduleException exception){
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(null, exception.getMessage());

                }
            }
        });

        /**
         * This Methode adds multiple dates to the schedule. The doctor chooses a starting date,time and end date,time(
         * (if chosen wrong the Exception handles the Error) and creates slots.
         */
        addMultipleDatesToButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ScheduleDAOImp scheduleDAOImp = new ScheduleDAOImp();
                //start after end
                if(timePicker2.getTime().isAfter(timePicker3.getTime()))
                    throw new ScheduleException("Starttime after Endtime");

                if(datePicker2.getDate().isAfter(datePicker3.getDate()))
                    throw new ScheduleException("Startdate after Enddate");

                //start date before the current date
                if(datePicker2.getDate().isBefore(LocalDate.now()))
                    throw new ScheduleException("Start Date is in the past");

                for(LocalDate i=datePicker2.getDate(); i.isBefore(datePicker3.getDate().plusDays(1));i=i.plusDays(1)){
                    for(LocalTime j=timePicker2.getTime(); j.isBefore(timePicker3.getTime().plusMinutes(time_slot.minutes)); j=j.plusMinutes(time_slot.minutes)){
                        Schedule schedule= new Schedule(doctor.getId(),i,j,j.plusMinutes(time_slot.minutes),Status.available);
                        scheduleDAOImp.addSchedule(schedule);

                    }
                }

                } catch (ScheduleException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex);

                } catch (Exception ex) {
                    ex.printStackTrace();

                }
            }
        });

        /**
         * The doctor selects an appointment that has to be canceled. After the confirmation of cancelation
         * the class AppointmentDAO is deleting the appointment in the database.
         */
        cancelAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppointmentDAOImp appointmentDAOImp=new AppointmentDAOImp();
                PatientDAOImp patientDAOImp=new PatientDAOImp();

                //get selected row and confirm to delete
                int i = Integer.parseInt((String) AppointmentTable.getValueAt(AppointmentTable.getSelectedRow(), 0).toString());


                int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel the appointment?", "choose", JOptionPane.YES_NO_OPTION);

                if (input == 0) {

                    try {
                        Mailer.sendMail(doctor.getEmail(),
                                "Hello " + doctor.getFirstName() + "!\n\nYour Appointment has been successfully canceled!\n\n" +
                                        "Best regards\n\neHealth Consulting",
                                "Appointment Cancellation"
                        );
                    } catch (MessagingException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        Mailer.sendMail(patientDAOImp.getEmailById(appointmentDAOImp.getPatientIdById(i)),
                                "Hello " + patientDAOImp.getLastNameByID(appointmentDAOImp.getPatientIdById(i)) + "!\n\nYour Appointment has been successfully canceled! The Patient has been informed\n\n" +
                                        "Best regards\n\neHealth Consulting",
                                "Appointment Cancellation"
                        );
                    } catch (MessagingException ex) {
                        ex.printStackTrace();
                    }

                    appointmentDAOImp.cancelById(i);


                    //send Mail to doctor and to patient


                    }


                //refreshing the table
                viewAppointmentsButton.doClick();
                }
        });

        /**
        * This methode of the logout button logs the doctor out and refers to the MainPage
         */
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Login mainPage= null;
                try {
                    mainPage = new Login();
                } catch (PasswordException ex) {
                    ex.printStackTrace();
                }
                mainPage.setVisible(true);


            }
        });

        /**
         * The Doctor chooses an appointment and can import the healthinfo. This healthinfo is importet to the database
         * and the patient can view it on the patients' homepage.
         */
        importHealthinfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //setting for upload file
                int response = fileChooser.showOpenDialog(null);
                if (response == JFileChooser.APPROVE_OPTION) {
                    file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                }
                AppointmentDAOImp appointmentDAOImp=new AppointmentDAOImp();
                int i = Integer.parseInt( AppointmentTable.getValueAt(AppointmentTable.getSelectedRow(), 0).toString());
                appointmentDAOImp.updateHealthinfoById(i,file);

            }
        });

        /**
         * The doctor can view the healthinfo of the patient that booked an appointment. The file is exported as a
         * .txt and .pdf file.
         */
        exportHealthinfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                File textFile = null;
                int i = Integer.parseInt((String) AppointmentTable.getValueAt(AppointmentTable.getSelectedRow(), 0).toString());

                if (fileChooser.showSaveDialog(exportHealthinfoButton) == JFileChooser.APPROVE_OPTION) {
                    textFile = fileChooser.getSelectedFile();
                }
                try{
                    FileOutputStream out = new FileOutputStream(textFile);
                    AppointmentDAOImp appointmentDAOImp = new AppointmentDAOImp();
                    out.write(appointmentDAOImp.getHealthInfoById(i));
                    out.close();
                    Document pdfDoc = new Document(PageSize.A4);
                    PdfWriter.getInstance(pdfDoc, new FileOutputStream(textFile+".pdf"))
                            .setPdfVersion(PdfWriter.PDF_VERSION_1_7);
                    pdfDoc.open();
                    Font myFont = new Font();
                    myFont.setStyle(Font.NORMAL);
                    myFont.setSize(11);
                    pdfDoc.add(new Paragraph("\n"));
                    BufferedReader br = new BufferedReader(new FileReader(textFile));
                    String strLine;
                    while ((strLine = br.readLine()) != null) {
                        Paragraph para = new Paragraph(strLine + "\n", myFont);
                        para.setAlignment(Element.ALIGN_JUSTIFIED);
                        pdfDoc.add(para);
                    }
                    pdfDoc.close();
                    br.close();
                } catch (DocumentException | IOException ex) {
                    ex.printStackTrace();
                }

            }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Doctor doctor=new Doctor();
        DoctorHomePage doctorHomePage=new DoctorHomePage(doctor);
        doctorHomePage.setVisible(true);
    }
}

