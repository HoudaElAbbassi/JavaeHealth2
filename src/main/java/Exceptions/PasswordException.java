package Exceptions;
/**
 * this class extends from Exception,
 * and it would be thrown when a checked error occurs in Password forms.
 *
 * @author Ahmed,Houda,Amine,Parabal,Daniel
 */
public class PasswordException extends Exception {
    /**
     * Constructs an instance of PasswordException with
     * an error message.
     */
    public PasswordException(){ super("Error");}
    /**
     * Constructs an instance of PasswordException with
     * specified detail message.
     * @param errorMessage password error
     */
    public PasswordException(String errorMessage){
        super(errorMessage);
    }

}
