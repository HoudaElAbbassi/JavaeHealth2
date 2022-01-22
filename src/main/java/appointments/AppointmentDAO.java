package appointments;

import user.Patient.HealthProblem;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface AppointmentDAO {
    boolean addAppointment(Appointment appointment);
    void shiftAppointment(Appointment appointment, long newScheduleId);
    void cancel(Appointment appointment);
    void cancelById(long id);
    //public boolean existsAppointment(long AppointmentId);
    Appointment getAppointmentByPatientId(long patientId);
    Appointment getAppointmentByDoctorId(long doctorId);
    Appointment getById(int id);
    List<Appointment> getAll();
    List<Appointment> getAllByDoctorId(long doctorId);
    List<Appointment> getAllByPatientId(long patientId);
    long getScheduleId(long appointmentId);
    HealthProblem getHealthProblemById(long id);
    void updateHealthinfoById(long id, File healthproblemfile);

    public byte[] getHealthInfo(long patientId);

    public LocalDateTime getDateTimeByScheduleId(long scheduleId);
}
