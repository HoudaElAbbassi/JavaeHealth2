package user.Admin;

import Connection.DBConnection;
import Exceptions.EmailException;
import Exceptions.PasswordException;
import Security.EmailVerification;
import Security.PasswordManager;
import user.UserDAO;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAOImp implements UserDAO<Admin> {

    @Override
    public boolean save(Admin admin) throws PasswordException, EmailException {
        try {

            PasswordManager.passwordVerification(admin.getPassword());
            EmailVerification.verifyEmail(admin.getEmail());
            Connection con= DBConnection.getConnection();
            String sql = "INSERT INTO admins(userName, email, password, firstName, lastName, address, birthDate ) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, admin.getUserName());
            ps.setString(2, admin.getEmail());
            ps.setString(3, PasswordManager.encode(admin.getPassword()));
            ps.setString(4,admin.getFirstName());
            ps.setString(5,admin.getLastName());
            ps.setString(6,admin.getAddress());
            ps.setDate(7, Date.valueOf(admin.getBirthDate()));
            
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "You have successfully registered! Now you can login.");
            return true;
        }
        catch (PasswordException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        catch (EmailException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        catch (SQLIntegrityConstraintViolationException e){
            JOptionPane.showMessageDialog(null, "User already exists! Please Login");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
    }

    @Override
    public void edit(Admin admin) throws PasswordException, EmailException {
        try{
            PasswordManager.passwordVerification(admin.getPassword());
            EmailVerification.verifyEmail(admin.getEmail());
            Connection con= DBConnection.getConnection();
            String sql = "Update admins set  userName=?, email=?, password=?, firstName=?, lastName=?, address=?, birthDate=? where id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, admin.getUserName());
            ps.setString(2, admin.getEmail());
            ps.setString(3, admin.getPassword());
            ps.setString(4,admin.getFirstName());
            ps.setString(5,admin.getLastName());
            ps.setString(6,admin.getAddress());
            ps.setDate(7, Date.valueOf(admin.getBirthDate()));
            ps.setLong(8,admin.getId());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Updated!");
        }
        catch (PasswordException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        catch (EmailException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        return false;
    }

    @Override
    public void delete(Admin admin) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "delete from admins  WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, admin.getId());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Deleted!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
    }
    public void deleteByID(long id){ try {

        Connection con = DBConnection.getConnection();
        String sql = "delete from admins  WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setLong(1, id);
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "Deleted!");
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error");
    }}

    @Override
    public boolean existEmail(String userEmail) {
        try{
            Connection con = DBConnection.getConnection();
            String sql = "select email from admins WHERE email='" + userEmail + "'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();

            if(rs.next()){
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }

        return false;
    }

    @Override
    public Admin getByEmail(String userEmail) {
        Admin admin=new Admin();
        try {

            Connection con = DBConnection.getConnection();
            String sql = "SELECT userName, email, password, firstName, lastName, address, birthDate, insuranceType, insuranceName from admins where email="+userEmail;
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs=ps.executeQuery();

            if(rs.next()){
                admin.setAddress(rs.getString("address"));
                admin.setBirthDate(rs.getDate("birthDate").toLocalDate());
                admin.setEmail(rs.getString("email"));
                admin.setFirstName(rs.getString("firstName"));
                admin.setLastName(rs.getString("lastName"));
                admin.setPassword(rs.getString("password"));
                //admin.setEmail(email);
            }
            JOptionPane.showMessageDialog(null, "Deleted!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        return admin;
    }

    @Override
    public Admin getByID(long id) {
        return null;
    }



    @Override
    public List<Admin> getAll() {
        List<Admin> list = new ArrayList<Admin>();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM admins ";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Admin admin = new Admin();
                admin.setAddress(rs.getString("address"));
                admin.setBirthDate(rs.getDate("birthDate").toLocalDate());
                admin.setEmail(rs.getString("email"));
                admin.setFirstName(rs.getString("firstName"));
                admin.setLastName(rs.getString("lastName"));
                admin.setPassword(rs.getString("password"));
                admin.setId(rs.getInt("ID"));
                list.add(admin);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        return list;
    }

    @Override
    public String getPassword(String userEmail) {
        try{
            Connection con = DBConnection.getConnection();
            String sql = "select password from admins WHERE email='" + userEmail + "'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            rs.next();
            return rs.getString("password");
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"User doesn't exist");
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }

        return null;
    }

    @Override
    public String getFirstNameByID(long id) {
        try{
            Connection con = DBConnection.getConnection();
            String sql = "select firstName from admins WHERE id="+id;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            rs.next();
            return rs.getString("firstName");
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"User doesn't exist");
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }

        return null;
    }

    @Override
    public String getLastNameByID(long id) {
        try{
            Connection con = DBConnection.getConnection();
            String sql = "select lastName from admins WHERE id="+id;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            rs.next();
            return rs.getString("lastName");
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"User doesn't exist");
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        return null;
    }

    @Override
    public String getEmailById(long id) {
        return null;
    }
}
