package appointments;

import appointments.schedule.Schedule;
import appointments.schedule.ScheduleDAOImp;
import user.Doctor.Doctor;
import user.Doctor.DoctorDAOImp;
import user.Patient.HealthProblem;

import java.io.File;

public class Appointment {

    private long id;
    private long doctorId;
    private long patientId;
    private long scheduleId;
    private HealthProblem healthProblem;
    private File healthInfo;
    private Reminder reminder = Reminder.three_days;

    public Appointment() {
    }

    public Appointment(
                       long doctorId,
                       long patientId,
                       long scheduleId,
                       HealthProblem healthProblem,
                       File healthInfo,
                       Reminder reminder) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.scheduleId = scheduleId;
        this.healthProblem = healthProblem;
        this.healthInfo = healthInfo;
        this.reminder = reminder;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public HealthProblem getHealthProblem() {
        return healthProblem;
    }

    public void setHealthProblem(HealthProblem healthProblem) {
        this.healthProblem = healthProblem;
    }

    public File getHealthInfo() {
        return healthInfo;
    }

    public void setHealthInfo(File healthInfo) {
        this.healthInfo = healthInfo;
    }



    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    @Override
    public String toString() {
        DoctorDAOImp doctorDAOImp = new DoctorDAOImp();
        Doctor doctor = doctorDAOImp.getByID(doctorId);
        ScheduleDAOImp scheduleDAOImp = new ScheduleDAOImp();
        Schedule schedule = scheduleDAOImp.getById(scheduleId);
        return "Appointment by Dr. " +
                 doctor.getFirstName()+" "+doctor.getLastName()+
                ", on: " + schedule.getDate() +
                ", from " + schedule.getStart() +
                " till " + schedule.getEnd();
    }
}
