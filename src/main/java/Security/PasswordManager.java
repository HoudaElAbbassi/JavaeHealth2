package Security;

import Exceptions.EmailException;
import Exceptions.PasswordException;


import java.util.Base64;
/**
 * this class is used to manage passwords in the app, through verifying,encryption and decryption
 *
 * @author
 */
public class PasswordManager {
/**
 * this methode is used to encode the given password with help of the public class Base64
 * @param password which is given by user
 * @return encodedPassword which will be stored
 */
    public static String encode(String password){
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
        return encodedPassword;
    }
    /**
     * this methode is used to decode the given password with help of the public class Base64
     * @param password which is given by user or stored in database
     * @return decodedPassword which will be compared with the Password given by the user in Login
     */
    public static String decode(String password){
        byte[] decodedBytes = Base64.getDecoder().decode(password);
        String decodedPassword = new String(decodedBytes);
        return decodedPassword;
    }
    /**
     *this methode verify if the email valid or invalid based on The following restrictions by using regex
     * @param password which is given by the user
     * @throws PasswordException if the password-format is invalid
     * @return boolean true if the password-format is valid
     * */
    public static boolean passwordVerification(String password) throws PasswordException {
        if((password.length()<7)){
            throw new PasswordException("Password is too short. Password must contain at least 8 characters");
        }
        String pattern="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        if(!password.matches(pattern)) {throw new PasswordException(" Password must:\n" +
                "\n" +
                "    At least 8 chars\n" +
                "\n" +
                "    Contains at least one digit\n" +
                "\n" +
                "    Contains at least one lower alpha char and one upper alpha char\n" +
                "\n" +
                "    Contains at least one char within a set of special chars (@#$%^&+=)\n" +
                "\n" +
                "    Does not contain space, tab, etc.\n");}
        return true;

    }
}
