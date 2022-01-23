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

    /*
    public static void main(String[] args) throws ParseException {
        Timer timer=new Timer();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String year = String.valueOf(LocalDate.now().getYear());
        String month = String.valueOf(LocalDate.now().getMonthValue());
        String day = String.valueOf(LocalDate.now().getDayOfMonth());
        String hour=String.valueOf(LocalTime.of(23,37,1));
        String hour2=String.valueOf(LocalTime.of(23,38,1));
        Date date = dateFormatter.parse(year + "-" + month + "-" + day +" "+ hour);
        Date date2 = dateFormatter.parse(year + "-" + month + "-" + day +" "+ hour2);
        /// LocalDateTime ldt= LocalDateTime.of(LocalDate.of(2022, 1, 10), LocalTime.of(20, 36, 1));
        timer.schedule(new Reminder("agdmouna@gmail.com", "Appointment reminder", "test test test"),date);
        timer.schedule(new Reminder("agdmouna@gmail.com", "Appointment reminder2", "2test test test"),date2);
    }

     */



}