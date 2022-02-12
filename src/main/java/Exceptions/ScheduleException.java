package Exceptions;
/**
 * this class extends from Exception,
 * and it would be thrown when an already exists schedule would be taken.
 *
 * @author Ahmed,Houda,Amine,Parabal,Daniel
 */
public class ScheduleException extends Exception {
    /**
     * Constructs an instance of ScheduleException with
     * an error message.
     */
    public ScheduleException(){ super("Error");}
    /**
     * Constructs an instance of ScheduleException with the
     * specified detail message.
     * @param errorMessage schedule error
     */
    public ScheduleException(String errorMessage){
        super(errorMessage);
    }

}
