import GUI.MainPage;
import GUI.homePage.patient.PatientHomePage;
import appointments.Appointment;
import appointments.AppointmentDAOImp;
import user.Patient.PatientDAOImp;


public class Testing {

    public static void main(String[] args){
        //MainPage mainPage = new MainPage();
        //mainPage.setVisible(true);

        AppointmentDAOImp appointmentDAOImp=new AppointmentDAOImp();
        Appointment a=appointmentDAOImp.getAppointmentByDoctorId(2);
        System.out.println(a.getHealthInfo());
    }

}