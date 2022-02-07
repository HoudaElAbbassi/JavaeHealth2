import GUI.MainPage;
import GUI.homePage.patient.PatientHomePage;
import appointments.Appointment;
import appointments.AppointmentDAOImp;
import user.Patient.PatientDAOImp;

import javax.swing.*;


public class Testing {

    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        MainPage mainPage = new MainPage();
        mainPage.setVisible(true);

        //AppointmentDAOImp appointmentDAOImp=new AppointmentDAOImp();
        //Appointment a=appointmentDAOImp.getAppointmentByDoctorId(2);
        //System.out.println(a.getHealthInfo());
    }

}