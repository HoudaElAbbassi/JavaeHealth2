package Security;

import Exceptions.PasswordException;


import java.util.Base64;

public class PasswordManager {

    public static String encode(String password){
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
        return encodedPassword;
    }

    public static String decode(String password){
        byte[] decodedBytes = Base64.getDecoder().decode(password);
        String decodedPassword = new String(decodedBytes);
        return decodedPassword;
    }

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
