package utilities;

import javax.mail.MessagingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimerTask;

/**
 * Thi class represents the reminder functionality. It is responsible for sending a reminder via Email to a patient according to their chosen time.
 * @author Ahmed, Amine, Houda
 */
public class Reminder extends TimerTask implements Runnable {

    private boolean exit = false;
    /** represents the email address of the patient */
    private String receiverEmailAddress;
    /** represents the subject of reminder email */
    private String subject;
    /** represents the body of reminder email */
    private String message;

    /** A thread is used here to run the process of sending the reminder parallel to other processes running by the app.
     * it ensures that other processes run from the app do not stop from running while waiting for the reminder to be sent
     */
    Thread t;

    /**
     * this is the main constructor that is responsible for creating a reminder. it has as parameters:
     * @param receiverEmailAddress email address of the patient
     * @param subject subject of the reminder email
     * @param message body of the reminder email
     */
    public Reminder(String receiverEmailAddress, String subject, String message) {
        this.receiverEmailAddress = receiverEmailAddress;
        this.subject = subject;
        this.message = message;
        t = new Thread(this); // instatiates the thread
        t.start(); // starts the thread
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

    /**
     * used to stop the thread started from the constructor
     */
    public void stop() {
        exit = true;
    }

    /**
     * this is a helper method to convert the time from the LocalDateTime format to a Date format.
     * it's mainly used while making an appointment and setting a reminder to convert the time gotten from the schedule table in the database
     * @param localDateTime the time and date in LocalDateTime format
     * @return the same time and date given as a parameter converted in the Date Format
     * @throws ParseException in case the date parsing fails
     */
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
