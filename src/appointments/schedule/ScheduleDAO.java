package appointments.schedule;

import appointments.Reminder;

import java.sql.SQLException;
import java.util.List;

public interface ScheduleDAO {

    public void addSchedule(Schedule schedule) throws SQLException, Exception;
    public List<Schedule> getAllAvailable(long doctorId);
    public List<Schedule> getAllBooked(long doctorId);
    public void updateStatusToAvailable(long scheduleId);
    public void updateStatusToBooked(long scheduleId);
    public void setReminder(long scheduleId, Reminder reminder);
    public long getDoctorId(long scheduleId);
    public Schedule getById(long scheduleId);

}
