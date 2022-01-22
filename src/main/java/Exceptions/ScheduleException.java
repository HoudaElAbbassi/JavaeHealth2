package Exceptions;

public class ScheduleException extends Exception {


    public ScheduleException(){ super("Error");}

    public ScheduleException(String errorMessage){
        super(errorMessage);
    }

}
