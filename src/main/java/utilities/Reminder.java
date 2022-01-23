package utilities;

import javax.mail.MessagingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Reminder extends TimerTask implements Runnable {

    private boolean exit = false;

    private String senderEmailAddress;
    private String senderPassword;
    private String receiverEmailAddress;
    private String subject;
    private String message;

    Thread t;

    public Reminder(String receiverEmailAddress, String subject, String message) {

        this.receiverEmailAddress = receiverEmailAddress;
        this.subject = subject;
        this.message = message;

        t = new Thread(this);
        t.start();
    }

    @SuppressWarnings("deprecation")
    public void run() {
        t.stop();
        try {
            Mailer.sendMail(receiverEmailAddress, subject, message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        System.out.println("Mail sent");
        System.exit(0);
    }

    public void stop() {
        exit = true;
    }

    public static Date convert(LocalDateTime localDateTime) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String year = String.valueOf(localDateTime.getYear());
        String month = String.valueOf(localDateTime.getMonthValue());
        String day = String.valueOf(localDateTime.getDayOfMonth());
        String hour = String.valueOf(localDateTime.getHour());
        String minute = String.valueOf(localDateTime.getMinute());
        String second = String.valueOf(localDateTime.getSecond());
        Date date = dateFormat.parse(year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second);
        return date;
    }

}