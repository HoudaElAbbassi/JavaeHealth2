package appointments;

import Connection.DBConnection;
import appointments.schedule.ScheduleDAOImp;
import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import org.apache.poi.hwpf.usermodel.DateAndTime;
import user.Patient.HealthProblem;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppointmentDAOImp implements AppointmentDAO{

    @Override
    public boolean addAppointment(Appointment appointment) {
            try{
                Connection con= DBConnection.getConnection();
                String sql = "INSERT INTO `appointments`( `doctorId`, `patientId`,`scheduleId`,`healthproblem`, `healthinfo`) VALUES (?,?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setLong(1, appointment.getDoctorId());
                ps.setLong(2, appointment.getPatientId());
                ps.setLong(3,appointment.getScheduleId());
                ps.setString(4,appointment.getHealthProblem().toString());
                ps.setCharacterStream(5, new FileReader(appointment.getHealthInfo()));
                ps.executeUpdate();
                ScheduleDAOImp scheduleDAOImp = new ScheduleDAOImp();
                scheduleDAOImp.updateStatusToBooked(appointment.getScheduleId());
                JOptionPane.showMessageDialog(null, "Appointment booked!");
                return true;
            }catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error");
            }
            catch(FileNotFoundException e) {
                e.printStackTrace();
            }
            return false;

    }

    @Override
    public void shiftAppointment(Appointment appointment, long newScheduleId) {
        try{ScheduleDAOImp scheduleDAOImp = new ScheduleDAOImp();
            scheduleDAOImp.updateStatusToAvailable(appointment.getScheduleId());
            appointment.setScheduleId(newScheduleId);
            scheduleDAOImp.updateStatusToBooked(newScheduleId);
            JOptionPane.showMessageDialog(null, "Appointment shifted!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
    }

    @Override
    public void cancel(Appointment appointment) {
        try{
            ScheduleDAOImp scheduleDAOImp = new ScheduleDAOImp();
            scheduleDAOImp.updateStatusToAvailable(appointment.getScheduleId());
            Connection con= DBConnection.getConnection();
            String sql = "delete from appointments where doctorId=? , patientId=?, scheduleId=?" +
                    "healthproblem=?, healthinfo=? ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, appointment.getDoctorId());
            ps.setLong(2, appointment.getPatientId());
            ps.setLong(3,appointment.getScheduleId());
            ps.setString(4,appointment.getHealthProblem().toString());
            ps.setCharacterStream(5, new FileReader(appointment.getHealthInfo()));

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Appointment deleted");
        }catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void cancelById(long id) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "delete from appointments where id=" + id;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Appointment deleted");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
    }


        @Override
    public Appointment getAppointmentByPatientId(long patientId) {
            Appointment appointment = new Appointment();
            try {
                Connection con = DBConnection.getConnection();
                String sql = "SELECT * from appointments where patientId="+patientId;
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs=ps.executeQuery();
                if(rs.next()){
                    appointment.setId(rs.getLong("id"));
                    appointment.setDoctorId(rs.getLong("doctorId"));
                    appointment.setPatientId(rs.getLong("patientId"));
                    appointment.setScheduleId(rs.getLong("scheduleId"));
                    appointment.setHealthProblem(HealthProblem.valueOf(rs.getString("healthProblem")));
                    File file=new File("/output.txt");
                    Files.write(CharStreams.toString(rs.getCharacterStream("healthinfo")).getBytes(StandardCharsets.UTF_8),file);
                    appointment.setHealthInfo(file);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error");
            }
            return appointment;
    }

    @Override
    public Appointment getAppointmentByDoctorId(long doctorId) {
        Appointment appointment = new Appointment();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * from appointments where doctorId="+doctorId;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                appointment.setId(rs.getLong("id"));
                appointment.setDoctorId(rs.getLong("doctorId"));
                appointment.setPatientId(rs.getLong("patientId"));
                appointment.setScheduleId(rs.getLong("scheduleId"));
                appointment.setHealthInfo(new File(String.valueOf(rs.getCharacterStream("healthinfo"))));
                //appointment.setHealthProblem(HealthProblem.valueOf(rs.getString("HealthProblem")));
                File file=new File("C:\\Users\\houda\\Desktop\\JAVAProject\\output.txt");
                Files.write(CharStreams.toString(rs.getCharacterStream("healthinfo")).getBytes(StandardCharsets.UTF_8),file);
                appointment.setHealthInfo(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        return appointment;
    }

    @Override
    public Appointment getById(int id) {
        Appointment appointment = new Appointment();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * from appointments where id="+id;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                appointment.setId(rs.getLong("id"));
                appointment.setDoctorId(rs.getLong("doctorId"));
                appointment.setPatientId(rs.getLong("patientId"));
                appointment.setScheduleId(rs.getLong("scheduleId"));
                appointment.setHealthProblem(HealthProblem.valueOf(rs.getString("HealthProblem")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        return appointment;
    }

    @Override
    public List<Appointment> getAll() {
        List<Appointment> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM appointments ";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Appointment appointment = new Appointment();
                appointment.setId(rs.getLong("id"));
                appointment.setDoctorId(rs.getLong("doctorId"));
                appointment.setPatientId(rs.getLong("patientId"));
                appointment.setScheduleId(rs.getLong("scheduleId"));
                appointment.setHealthProblem(HealthProblem.valueOf(rs.getString("HealthProblem")));
                list.add(appointment);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        return list;
    }

    @Override
    public List<Appointment> getAllByDoctorId(long doctorId) {
        List<Appointment> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM appointments WHERE doctorId="+doctorId;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Appointment appointment = new Appointment();
                appointment.setId(rs.getLong("id"));
                appointment.setDoctorId(rs.getLong("doctorId"));
                appointment.setPatientId(rs.getLong("patientId"));
                appointment.setScheduleId(rs.getLong("scheduleId"));
                appointment.setHealthProblem(HealthProblem.valueOf(rs.getString("HealthProblem")));
                list.add(appointment);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        return list;
    }

    @Override
    public List<Appointment> getAllByPatientId(long patientId) {
        List<Appointment> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM appointments WHERE patientId="+patientId;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Appointment appointment = new Appointment();
                appointment.setId(rs.getLong("id"));
                appointment.setDoctorId(rs.getLong("doctorId"));
                appointment.setPatientId(rs.getLong("patientId"));
                appointment.setScheduleId(rs.getLong("scheduleId"));
                appointment.setHealthProblem(HealthProblem.valueOf(rs.getString("healthProblem")));
                list.add(appointment);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        return list;
    }

    @Override
    public long getScheduleId(long appointmentId) {
        long scheduleId = 0;
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM appointments WHERE id="+appointmentId;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                scheduleId = rs.getLong("scheduleId");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        return scheduleId;
    }

    @Override
    public HealthProblem getHealthProblemById(long Id) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT healthproblem FROM appointments WHERE Id="+Id;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
                return HealthProblem.valueOf(rs.getString("healthproblem"));

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        return null;
    }

    @Override
    public void updateHealthinfoById(long id,File healthproblemfile) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "Update appointments set  healthinfo=? where id=" + id;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setCharacterStream(1, new FileReader(healthproblemfile));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Updated!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
    }

    @Override
    public byte[] getHealthInfo(long patientId) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * from appointments where patientId=" + patientId;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBytes("healthInfo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public byte[] getHealthInfoById(long appointmentid) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * from appointments where Id=" + appointmentid;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBytes("healthInfo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getPatientIdById(long appointmentId) {
        long patientId = 0;
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT patientId FROM appointments WHERE id="+appointmentId;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) patientId = rs.getLong("patientId");


        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        return patientId;
    }
}