package utilities;

import appointments.schedule.Schedule;
import user.Doctor.Doctor;
import user.Patient.Patient;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * a utility class to send an email using the SMTP protocol and the Google Gmail services
 * @author Ahmed, Amine
 */
public class Mailer {

    /**
     * a simple method to create the message to send to the patient after booking an appointment
     * @param patient the patient who has booked the appointment
     * @param doctor the doctor that has been booked from the patient
     * @param schedule the schedule chosen from the patient
     * @return the message to sent after booking a new appointment
     */
    public static String bookingMessage(Patient patient, Doctor doctor, Schedule schedule){
        return "Hello " + patient.getFirstName()+"!\n\n" +
                "You have successfully booked an appointment with the following information:\n"+
                "-Doctor Name: "+doctor.getLastName()+" "+doctor.getFirstName()+".\n"+
                "-Address: "+doctor.getAddress()+".\n"+
                "-Date and Time: "+schedule.getDate()+" at "+schedule.getStart()+
                "\n\nBest regards\n\n" +
                "eHealth Consulting";
    }

    /**
     * a simple method to create the message to send to the patient after cancelling an appointment
     * @param patient the patient who has cancelled the appointment
     * @return the message to sent after cancelling a new appointment
     */
    public static String cancellationMessage(Patient patient){
        return "Hello " + patient.getFirstName()+"!\n\n" +
                "Your Appointment has been successfully canceled!\n\n" +
                "Best regards\n\n" +
                "eHealth Consulting";
    }

    /**
     * a simple method to create the message to send to the patient after rescheduling an appointment
     * @param patient the patient to send the email to
     * @param schedule the schedule chosen from the patient
     * @return the message to sent after rescheduling a new appointment
     */
    public static String reschedulingMessage(Patient patient, Schedule schedule){
        return "Hello " + patient.getFirstName()+"!\n\n" +
                "You have successfully shifted an appointment!\n"+
                "Your new Appointment has the following new information:\n"+
                "-Date and Time: "+schedule.getDate()+" at "+schedule.getStart()+
                "\n\nBest regards\n\n" +
                "eHealth Consulting";
    }

    /**
     * Utility method to send an email
     * @param recipient represents the email of the person receiving the email
     * @param msg represents the body of the email
     * @param subject represents the subject of the email
     * @throws MessagingException in case sending the message fails
     */
    public static void sendMail(String recipient, String msg, String subject) throws MessagingException {
        System.out.println("Preparing...");
        // configuration of the email host
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        // gmail account and password for the configuration
        String myAccountMail = "smart.ehealth22@gmail.com";
        String password = "eHealth2022";
        //get Session
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountMail, password);
            }
        });
        Message message = prepareMessage(session, myAccountMail, recipient, subject, msg);
        // Send message
        Transport.send(message);
        System.out.println("Message sent successfully");
    }

    /**
     * util method to prepare the message before sending it
     * @param session the created session
     * @param myAccountEmail the email of the sender
     * @param recipient the email of the recipient
     * @param subject the subject of the email
     * @param msg the body of the email
     * @return the created mime message
     */
    private static Message prepareMessage(Session session, String myAccountEmail, String recipient, String subject, String msg) {
        try {
            // Create a default MimeMessage object
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(msg);
            return message;
        }
        catch(Exception e){
            Logger.getLogger(Mailer.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }


}

