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

public class Reminder extends TimerTask implements Runnable{

    private String receiverEmailAddress;
    private String subject;
    private String message;
    private LocalDateTime localDateTime= LocalDateTime.of(2022,1,22,20,44,1);


    public Reminder( String receiverEmailAddress, String subject, String message) throws ParseException {
        this.receiverEmailAddress = receiverEmailAddress;
        this.subject = subject;
        this.message = message;

        Timer timer=new Timer();
        timer.schedule(this,this.convert(this.getLocalDateTime()));


    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public LocalDateTime getLocalDateTime(){
        return this.localDateTime;
    }

    public Date convert(LocalDateTime localDateTime) throws ParseException {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String year = String.valueOf(localDateTime.getYear());
        String month = String.valueOf(localDateTime.getMonthValue());
        String day = String.valueOf(localDateTime.getDayOfMonth());
        String hour=String.valueOf(localDateTime.getHour());
        String minute=String.valueOf(localDateTime.getMinute());
        String second=String.valueOf(localDateTime.getSecond());
        Date date = dateFormatter.parse(year + "-" + month + "-" + day +" "+ hour+":"+minute+":"+second);
        return date;
    }










    public static void main(String[] args) throws ParseException, InterruptedException {
     Reminder reminder=new Reminder("houdael@outlook.de","hallo","HALLO");
    Thread thread=new Thread(reminder);

     //reminder.setLocalDateTime();

    }


    @Override
    public void run() {
        Thread thread=new Thread(this);
        thread.start();

        try {
            Mailer.sendMail(this.receiverEmailAddress,this.message,this.subject);
            cancel();
            thread.stop();

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}