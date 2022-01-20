package appointments.schedule;
import Connection.DBConnection;
import appointments.Reminder;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAOImp implements ScheduleDAO {

    @Override
    public void addSchedule(Schedule schedule) throws SQLException, Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ehealth", "root", "MySQL640182--");
            Statement stmt = conn.createStatement();


            String sql = "INSERT INTO `ehealth`.`schedule` (`date`, `doctorId`,`start`, `end`, `status`) VALUES ('" +
                    Date.valueOf(schedule.getDate()) + "', '" +
                    schedule.getDoctorId() + "', '" +
                    Time.valueOf(schedule.getStart()) + "', '" +
                    Time.valueOf(schedule.getEnd()) + "','" + schedule.getStatus().toString() + "')";

            stmt.execute(sql);
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
            String sql = "SELECT * FROM `ehealth`.`schedule` WHERE doctorId=? AND status='available'";
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
            String sql = "SELECT * FROM `ehealth`.`schedule` WHERE doctorId=? AND status='booked'";
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
    public void setReminder(long scheduleId, Reminder reminder) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "Update schedule set reminder=? where scheduleId=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, reminder.toString());
            ps.setLong(2, scheduleId);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Reminder set!");
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
            String sql = "SELECT * FROM `ehealth`.`schedule` WHERE scheduleId=?";
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
    public Schedule getById(long scheduleId) {
        Schedule schedule = new Schedule();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM `ehealth`.`schedule` WHERE scheduleId=?";
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
}
