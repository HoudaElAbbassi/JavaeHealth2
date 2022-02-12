package appointments.schedule;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This class sets up the schedule for doctors using IDs for doctors and schedules, imported attributes for
 * date and time and a status for booked appointments.
 */
public class Schedule {

    private long scheduleId;
    private long doctorId;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private Status status = Status.available;

    public Schedule( long doctorId, LocalDate date, LocalTime start, LocalTime end, Status status) {
        this.doctorId = doctorId;
        this.date = date;
        this.start = start;
        this.end = end;
        this.status = status;
    }

    public Schedule() {
    }

    public long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * This method turns the Schedule information into a String with date and time
     * @return String
     */
    @Override
    public String toString() {
        return "Day: "+date.getDayOfWeek()+" "+date.getDayOfMonth()+". "+date.getYear()+",   "+
                "Time: "+start+ " - "+end;
    }
}
