package GUI.homePage.Doctor;

import Exceptions.EmailException;
import Exceptions.PasswordException;
import Exceptions.ScheduleException;
import GUI.MainPage;
import appointments.AppointmentDAOImp;
import appointments.schedule.Schedule;
import appointments.schedule.ScheduleDAOImp;
import appointments.schedule.Status;
import com.github.lgooddatepicker.components.*;
import user.Doctor.Doctor;
import user.Doctor.DoctorDAOImp;
import user.Patient.PatientDAOImp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class DoctorHomePage extends JFrame{
    /**
     * This class is the Homepage of the doctor. The doctor has the possibility
     * to add or delete slots to the Schedule and can add multiple dates at the time.
     * The doctor also has an overview of his booked appointments and can cancel if necessary.
     */
    private JPanel mainPanel;
    private JTabbedPane Appointments;
    private JPanel AppointmentsPanel;
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
    private JTabbedPane tabbedPane1;
    private JButton logoutButton;
    private JLabel UsernameTextField;
    private JPanel addmultipledates;
    private JPanel addOneDate;
    private JPanel TimePanelEnd;


    TimePickerSettings timePickerSettings=new TimePickerSettings();


    private DatePicker datePicker=new DatePicker();
    private TimePicker timePicker=new TimePicker(timePickerSettings);

    private DatePicker datePicker2=new DatePicker();
    private TimePicker timePicker2=new TimePicker(timePickerSettings);

    private DatePicker datePicker3=new DatePicker();
    private TimePicker timePicker3=new TimePicker(timePickerSettings);

    private DatePicker datePicker4=new DatePicker();
    private TimePicker timePicker4=new TimePicker(timePickerSettings);



    public DoctorHomePage(Doctor doctor) {
        /**
         * The HomePage of the Doctor receives as the attribute the logged in doctor.
         * All the Actions of GUI(ButtonClicks) are implemented in the Constructor.
         */

        setContentPane(mainPanel);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        viewAppointmentsButton.addActionListener(new ActionListener() {
            /**
             *This Methode is for the View Appointment Button.
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                AppointmentDAOImp appointmentDAOImp=new AppointmentDAOImp();
                PatientDAOImp patientDAOImp=new PatientDAOImp();
                DefaultTableModel tbModel = (DefaultTableModel) AppointmentTable.getModel();

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
                    //Appointment nicht aktuell!!
                    //rowData[3]=0;
                }
                    /*tbModel.setRowCount(0);
                    Connection con = DBConnection.getConnection();

                    String query = "Select id,patientId,healthproblem,healthinfo,distanceofsearch,scheduleId from appointments";

                    Statement stmt = con.prepareStatement(query);
                    ResultSet res = stmt.executeQuery(query);


                    while (res.next()) {
                        long appointmentid=res.getLong("id");
                        long patientid = res.getInt("patientId");
                        String healthproblem = res.getString("healthproblem");


                        //InputStream healthinfo = res.getBinaryStream("healthinfo");
                        File HealthInfoFile = new File("healthinfo");
                        FileOutputStream healthinfoFile = new FileOutputStream(HealthInfoFile);
                        byte[] buffer = new byte[1024];
                        while (healthinfo.read(buffer) > 0) {
                            healthinfoFile.write(buffer);
                        }

                        int distanceOfSearch = res.getInt("distanceofsearch");
                        long scheduleId = res.getInt("scheduleId");

                        String tbData[] = {"appointmentID","patientID", "healthproblem", "healthInfo", "distance of search", "schedukeID"};
                        String appInfo[] = {appointmentid+"",patientid + "", healthproblem, HealthInfoFile.toString(), distanceOfSearch + "", scheduleId + ""};


                        tbModel.setColumnIdentifiers(tbData);
                        tbModel.addRow(appInfo);
                        AppointmentTable.setModel(tbModel);




                    }
                    */

            }
        });

        //add to schedule
        addToScheduleButton.addActionListener(new ActionListener() {
            /**
             * When the "add to schedule"-button is pressed the Methode initializes a schedule-Object.This Object is being
             * saved in the Database. This class uses the class ScheduleDAO to manipulate the database.
             * If a Exception is being thrown by a Methode of ScheduleDAO, the Exception is being handled.
             * @param e
             */
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
        viewScheduleButton.addActionListener(new ActionListener() {
            /**
             * This Methode gets the Schedule from the Database and displays it in a Table. Each row represents the
             * relevant datasets from the table schedule.
             * @param e
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
        deleteFromScheduleButton.addActionListener(new ActionListener() {
            /**
             * the doctor has the possibility to delete a slot from the schedule by selecting the row.
             * If the selected slot is already booked the deletion is not possible, if otherwise the slot gets deleted.
             * The deletion has to get confirmed before the slot is deleted from the table schedule in the database.
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ScheduleDAOImp scheduleDAOImp = new ScheduleDAOImp();

                    //get selected row and confirm to delete
                    int i = Integer.parseInt( ScheduleTable.getValueAt(ScheduleTable.getSelectedRow(), 0).toString());

                    int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "choose", JOptionPane.YES_NO_OPTION);
                    System.out.println(i);

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
        addMultipleDatesToButton.addActionListener(new ActionListener() {
            /**
             * This Methode adds multiple dates to the schedule. The doctor chooses a starting date,time and end date,time(
             * (if chosen wrong the Exception handels the Error) and creates slots.
             *
             * @param e
             */
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
        cancelAppointmentButton.addActionListener(new ActionListener() {
            /**
             * The doctor selects an appointment that has to be canceled. After the confirmation of cancelation
             * the class AppointmentDAO is deleting the appointment in the database.
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                AppointmentDAOImp appointmentDAOImp=new AppointmentDAOImp();

                //get selected row and confirm to delete
                int i = Integer.parseInt((String) AppointmentTable.getValueAt(AppointmentTable.getSelectedRow(), 0));

                int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel the appointment?", "choose", JOptionPane.YES_NO_OPTION);

                //if delete is confirmed
                if (input == 0) {

                    appointmentDAOImp.cancelById(i);
                }

                //refreshing the table
                viewAppointmentsButton.doClick();

            }
        });
        logoutButton.addActionListener(new ActionListener() {
            /**
             * This methode of the logout button logs the doctor out and refers to the MainPage
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MainPage mainPage=new MainPage();
                mainPage.setVisible(true);


            }
        });
    }


    private static String convert(InputStream is) {
        BufferedInputStream bis = new BufferedInputStream(is);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int result;
        String str = null;
        try {
            result = bis.read();

            while (result != -1) {
                buf.write((byte) result);
                result = bis.read();
            }
            str = buf.toString("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }


    public static void main(String[] args) throws PasswordException, EmailException, SQLException {
        //Doctor doctor=new Doctor("patient2","email2@doc.de","Password1234#","firstname",
          //    "lastname","address", LocalDate.of(2000,12,12), Specialization.Allergist);
        DoctorDAOImp doctorDAOImp=new DoctorDAOImp();
        Doctor doctor= doctorDAOImp.getByID(2);
        //doctorDAOImp.save(doctor);
        DoctorHomePage doctorHomePage=new DoctorHomePage(doctor);
        doctorHomePage.setVisible(true);
    }


}

