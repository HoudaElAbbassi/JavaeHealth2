package utilities;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;
public class Mailer {

    public static void sendMail(String recipient, String msg, String subject) throws MessagingException {
        System.out.println("Preparing...");
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        //get Session
        String myAccountMail = "smart.ehealth22@gmail.com";
        String password = "eHealth2022";
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountMail, password);
            }
        });

        Message message = prepareMessage(session, myAccountMail, recipient, subject, msg);
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
        catch(Exception e){
            Logger.getLogger(Mailer.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }


}

