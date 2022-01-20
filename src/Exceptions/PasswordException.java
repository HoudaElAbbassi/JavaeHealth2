package Exceptions;

public class PasswordException extends Exception {


    public PasswordException(){ super("Error");}

    public PasswordException(String errorMessage){
        super(errorMessage);
    }

}
