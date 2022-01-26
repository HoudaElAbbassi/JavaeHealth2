package Security;

import Exceptions.EmailException;
/**
 * this class verify if the given email fulfill the required conditions.
 *
 * @author
 */

public class EmailVerification {
    /**
     *this methode verify if the email valid or invalid based on The following restrictions by using regex
     * @param email the email given by the user
     * @throws EmailException if the Email-format is invalid
     * */
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
