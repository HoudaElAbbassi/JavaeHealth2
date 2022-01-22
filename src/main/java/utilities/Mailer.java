package utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import javax.mail.*;
import javax.mail.internet.*;
public class Mailer {

    public static void sendMail(String to,String sub,String msg){
        //Get properties object
        String from = "smart.ehealth22@gmail.com";
        String password = "eHealth2022";
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        //get Session
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });
        //compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject(sub);
            message.setText(msg);
            //send message
            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {throw new RuntimeException(e);}

    }
    /*public static void main(String[] args) throws ParseException {
        Timer timer=new Timer();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String year = String.valueOf(LocalDate.now().getYear());
        String month = String.valueOf(LocalDate.now().getMonthValue());
        String day = String.valueOf(LocalDate.now().getDayOfMonth());
        String hour=String.valueOf(LocalTime.of(16,19,1));
        Date date = dateFormatter.parse(year + "-" + month + "-" + day +" "+ hour);
        /// LocalDateTime ldt= LocalDateTime.of(LocalDate.of(2022, 1, 10), LocalTime.of(20, 36, 1));
        timer.schedule(new MailApp("clinic.uas2022@gmail.com", "Java2022", "belghazi1998@gmail.com", "Appointment reminder", "test test test"),date);
        Mailer.send("clinic.uas2022@gmail.com", "Java2022", "belghazi1998@gmail.com", "Appointment reminder", "test test test");
    }*/

}
