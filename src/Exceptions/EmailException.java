package Exceptions;

public class EmailException extends Exception {
    public EmailException(){ super("Error");}

    public EmailException(String errorMessage){
        super(errorMessage);
    }
}
