package Exceptions;
/**
 * this class extends from Exception,
 * and it would be thrown when a checked error occurs in commons-email-format.
 *
 * @author @author Ahmed,Houda,Amine,Parabal,Daniel
 */
public class EmailException extends Exception {
    /**
     * Constructs an instance of EmailException with
     * an error message.
     */
    public EmailException(){ super("Error");}
    /**
     * Constructs an instance of EmailException with the
     * specified detail message.
     *
     * @param errorMessage
     */
    public EmailException(String errorMessage){
        super(errorMessage);
    }
}
