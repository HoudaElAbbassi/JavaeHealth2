package utilities;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailer {

    public static void sendMail(String recipient, String msg, String subject) throws MessagingException {
        System.out.println("Preparing...");
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");


        //get Session
        String myAccountMail = "clinic.uas2022@gmail.com";
        String password = "Java2022";
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountMail, password);
            }
        });
        
        // Used to debug SMTP issues
        session.setDebug(true);

        Message message = prepareMessage(session, myAccountMail, recipient, subject, msg);
        System.out.println("sending...");
            // Send message
        Transport.send(message);
        System.out.println("Message sent successfully");
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recipient, String subject, String msg) throws MessagingException {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(msg);
            return message;
        }
        catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws MessagingException {
        Mailer.sendMail("agdmouna@gmail.com","hallo:)","TEST");
    }

}

