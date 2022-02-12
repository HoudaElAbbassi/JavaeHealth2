package appointments.schedule;
import Connection.DBConnection;

import Exceptions.ScheduleException;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to connect to the database and access and edit schedules
 */
public class ScheduleDAOImp implements ScheduleDAO {

    /**
     * This method adds schedules to the database
     * @param schedule
     * @throws SQLException when access error or other error occurs
     * @throws Exception
     * @throws ScheduleException when the schedule already exists or date lies in the past
     */
    @Override
    public void addSchedule(Schedule schedule) throws SQLException, Exception, ScheduleException {
        try {
            Connection con = DBConnection.getConnection();

            Statement stmt = con.createStatement();

            //schedule already exists
            if (existsSchedule(schedule)) throw new ScheduleException("Already exists");

            //date is in the past
            if (schedule.getDate().isBefore(LocalDate.now())) throw new ScheduleException("Date lies in the Past");

            String sql = "INSERT INTO `schedule`(`date`, `doctorId`,`start`, `end`, `status`) VALUES ('" +
                    Date.valueOf(schedule.getDate()) + "', '" +
                    schedule.getDoctorId() + "', '" +
                    Time.valueOf(schedule.getStart()) + "', '" +
                    Time.valueOf(schedule.getEnd()) + "','" + schedule.getStatus().toString() + "')";
            stmt.execute(sql);

        } catch (ScheduleException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method gets all the available time slots and dates of a doctor from the database
     * @param doctorId
     * @return List<Schedule>
     */
    @Override
    public List<Schedule> getAllAvailable(long doctorId) {
        List<Schedule> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM schedule WHERE doctorId=? AND status='available'";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, doctorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Schedule schedule = new Schedule();
                schedule.setScheduleId(rs.getLong("scheduleId"));
                schedule.setDoctorId(rs.getLong("doctorId"));
                schedule.setDate(rs.getDate("date").toLocalDate());
                schedule.setStart(rs.getTime("start").toLocalTime());
                schedule.setEnd(rs.getTime("end").toLocalTime());
                schedule.setStatus(Status.available);
                list.add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * This method gets all the booked timesolts of a doctor from the database
     * @param doctorId
     * @return List<Schedule>
     */
    @Override
    public List<Schedule> getAllBooked(long doctorId) {

        List<Schedule> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM schedule WHERE doctorId=? AND status='booked'";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, doctorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Schedule schedule = new Schedule();
                schedule.setDoctorId(rs.getLong("doctorId"));
                schedule.setDate(rs.getDate("date").toLocalDate());
                schedule.setStart(rs.getTime("start").toLocalTime());
                schedule.setEnd(rs.getTime("end").toLocalTime());
                schedule.setStatus(Status.booked);
                list.add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * This method gets all the timeslots of a doctor, booked and available
     * @param doctorId
     * @return ArrayList<Schedule>
     * @throws SQLException when access error or other error occurs
     */
    @Override
    public ArrayList<Schedule> getAll(long doctorId) throws SQLException {
        ArrayList<Schedule> list = new ArrayList<>();
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            String sql = "SELECT * FROM schedule WHERE doctorId=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, doctorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Schedule schedule = new Schedule();
                schedule.setScheduleId(rs.getLong("scheduleId"));
                schedule.setDoctorId(rs.getLong("doctorId"));
                schedule.setDate(rs.getDate("date").toLocalDate());
                schedule.setStart(rs.getTime("start").toLocalTime());
                schedule.setEnd(rs.getTime("end").toLocalTime());
                schedule.setStatus(Status.valueOf(rs.getString("status")));
                list.add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return list;
    }

    /**
     * This method updates the status of a timeslot to available
     * @param scheduleId
     */
    @Override
    public void updateStatusToAvailable(long scheduleId) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "Update schedule set  status='available' where scheduleId=" + scheduleId;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method changes the status of a timeslot to booked
     * @param scheduleId
     */
    @Override
    public void updateStatusToBooked(long scheduleId) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "Update schedule set  status='booked' where scheduleId=" + scheduleId;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method gets the ID of a doctor using the ID of their schedule
     * @param scheduleId
     * @return doctorId
     */
    @Override
    public long getDoctorId(long scheduleId) {
        long doctorId = 0;
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM schedule WHERE scheduleId=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, scheduleId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                doctorId = rs.getLong("doctorId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctorId;
    }

    /**
     * This method checks if a schedule exists or not
     * @param schedule
     * @return boolean, true if the schedule exist, false if not
     */
    @Override
    public boolean existsSchedule(Schedule schedule) {
        try {
            Connection con = DBConnection.getConnection();
            String sql2 = "Select  `doctorId`, `date`, `start` from schedule where  `doctorId`=" + schedule.getDoctorId() + " and `date`='" + Date.valueOf(schedule.getDate()) + "'and `start`='" + Time.valueOf(schedule.getStart()) + "'";
            PreparedStatement ps = con.prepareStatement(sql2);
            ResultSet rs = ps.executeQuery(sql2);
            if (rs.next()) return true;
            else return false;
        } catch (Exception e) {
            e.printStackTrace();

        }

        return false;
    }

    /**
     * This method deletes timeslots from the schedule from the database
     * @param scheduleId
     */
    @Override
    public void deleteById(long scheduleId) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "delete from schedule where scheduleId=" + scheduleId;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Solt successfully deleted from schedule");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method gets the whole schedule from the database though the ID of the schedule
     * @param scheduleId
     * @return Schedule
     */
    @Override
    public Schedule getById(long scheduleId) {
        Schedule schedule = new Schedule();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM schedule WHERE scheduleId=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, scheduleId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                schedule.setDate(rs.getDate("date").toLocalDate());
                schedule.setStart(rs.getTime("start").toLocalTime());
                schedule.setEnd(rs.getTime("end").toLocalTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedule;
    }

    /**
     * This method gets the status of a timeslot trough scheduleId from the database
     * @param scheduleId
     * @return Status
     */
    @Override
    public Status getStatusById(long scheduleId) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "Select  status from schedule where  `scheduleId`=" + scheduleId;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return Status.valueOf(rs.getString("Status"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method gets the dates from the schedule from a specific doctor through th doctors ID
     * @param doctorid
     * @return LocalDate
     */
    @Override
    public LocalDate getDateByDoctorId(long doctorid) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "Select  date from schedule where  `doctorId`=" + doctorid;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getDate("date").toLocalDate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method gets the times from the schedule for a specific doctor through th doctors ID
     * @param doctorId
     * @return LocalTime
     */
    @Override
    public LocalTime getTimeByDoctorId(long doctorId) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "Select  start from schedule where  `doctorId`=" + doctorId;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getTime("start").toLocalTime();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method gets the dates and times from the schedule through the ID of the schedule
     * @param scheduleId
     * @return LocalDateTime
     */
    @Override
    public LocalDateTime getDateTimeByScheduleId(long scheduleId) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT date, start FROM schedule WHERE scheduleId="+scheduleId;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                LocalDate date=rs.getDate("date").toLocalDate();
                LocalTime time=rs.getTime("start").toLocalTime();

                return LocalDateTime.of(date,time);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
