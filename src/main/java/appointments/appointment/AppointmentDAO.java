package appointments.appointment;

import user.Patient.HealthProblem;

import java.io.File;
import java.util.List;

/**
 * This class has all the Methods that can be used to access the Database table of Appointment
 * They are implemented in the class AppointmentDAOImp
 */
public interface AppointmentDAO {
    boolean addAppointment(Appointment appointment);
    void shiftAppointment(Appointment appointment, long newScheduleId);
    void cancel(Appointment appointment);
    void cancelById(long id);
    Appointment getAppointmentByPatientId(long patientId);
    Appointment getAppointmentByDoctorId(long doctorId);
    Appointment getById(int id);
    List<Appointment> getAll();
    List<Appointment> getAllByDoctorId(long doctorId);
    List<Appointment> getAllByPatientId(long patientId);
    long getScheduleId(long appointmentId);
    HealthProblem getHealthProblemById(long id);
    void updateHealthinfoById(long id, File healthproblemfile);
    byte[] getHealthInfo(long patientId);
    byte[] getHealthInfoById(long appointmentid);
    long getPatientIdById(long appointmentId);
}
