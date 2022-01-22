package Security;

import Exceptions.EmailException;

public class EmailVerification {

    public static boolean verifyEmail(String email) throws EmailException {
        String pattern = "^(.+)@(.+)$";
        if (email.matches(pattern)){
            return true;
        }
        else {
            throw new EmailException("invalid email!");
        }
    }
}
