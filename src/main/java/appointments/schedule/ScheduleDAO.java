package appointments.schedule;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public interface ScheduleDAO {

    public void addSchedule(Schedule schedule) throws SQLException, Exception;
    public List<Schedule> getAllAvailable(long doctorId);
    public List<Schedule> getAllBooked(long doctorId);

    public ArrayList<Schedule> getAll(long doctorId) throws SQLException;

    public void updateStatusToAvailable(long scheduleId);
    public void updateStatusToBooked(long scheduleId);
    public long getDoctorId(long scheduleId);
    public Schedule getById(long scheduleId);
    public boolean existsSchedule(Schedule schedule);
    public void deleteById(long scheduleId);
    public Status getStatusById(long scheduleId);
    public LocalDate getDateByDoctorId(long doctorId);
    public LocalTime getTimeByDoctorId(long doctorId);

    LocalDateTime getDateTimeByScheduleId(long scheduleId);
}
