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

public class ScheduleDAOImp implements ScheduleDAO {

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
            JOptionPane.showMessageDialog(null, e);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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
                //System.out.println(rs.getLong("scheduleId"));
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


    @Override
    public void updateStatusToAvailable(long scheduleId) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "Update schedule set  status='available' where scheduleId=" + scheduleId;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Updated!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
    }

    @Override
    public void updateStatusToBooked(long scheduleId) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "Update schedule set  status='booked' where scheduleId=" + scheduleId;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Updated!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
    }

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

    @Override
    public boolean existsSchedule(Schedule schedule) {
        try {
            Connection con = DBConnection.getConnection();
            String sql2 = "Select  `doctorId`, `date`, `start` from schedule where  `doctorId`=" + schedule.getDoctorId() + " and `date`='" + Date.valueOf(schedule.getDate()) + "'and `start`='" + Time.valueOf(schedule.getStart()) + "'";
            PreparedStatement ps = con.prepareStatement(sql2);
            //ps.setLong(1, schedule.getDoctorId());
            //ps.setDate(2, Date.valueOf(schedule.getDate()));
            //ps.setTime(3, Time.valueOf(schedule.getStart()));
            System.out.println(sql2);
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery(sql2);
            System.out.println("nach ResultSet");
            if (rs.next()) return true;
            else return false;
        } catch (Exception e) {
            e.printStackTrace();

        }

        return false;
    }

    @Override
    public void deleteById(long scheduleId) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "delete from schedule where scheduleId=" + scheduleId;
            System.out.println(sql);
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Schedulr deleted");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
    }

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
            JOptionPane.showMessageDialog(null, "Error");
        }
        return null;
    }

}
