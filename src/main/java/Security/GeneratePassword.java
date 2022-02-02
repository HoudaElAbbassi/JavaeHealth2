package Security;

import java.security.SecureRandom;

/***
 * This class generates a random password from given requirements. The length of the generated
 * password is fifteen characters long and the symbols,letters and numbers that has to be used are pre
 * determent at the beginning
 */

public class GeneratePassword {



        private static final String CHAR_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
        private static final String CHAR_UPPERCASE = CHAR_LOWERCASE.toUpperCase();
        private static final String DIGIT = "0123456789";
        private static final String OTHER_PUNCTUATION = "@#$%^&+=";
        private static final String OTHER_SYMBOL = "@#$%^&+=";
        private static final String OTHER_SPECIAL = OTHER_PUNCTUATION + OTHER_SYMBOL;
        private static final int PASSWORD_LENGTH = 15;

        private static final String PASSWORD_ALLOW =
                CHAR_LOWERCASE + CHAR_UPPERCASE + DIGIT + OTHER_SPECIAL;

        private static SecureRandom random = new SecureRandom();

    /**
     * This methode generates a String password by creating random Strings of each category of symbols and brings
     * them together to create a password.
     * @return String=password
     */
    public static String generateStrongPassword() {

            StringBuilder result = new StringBuilder(PASSWORD_LENGTH);

            // at least 2 chars (lowercase)
            String strLowerCase = generateRandomString(CHAR_LOWERCASE, 2);
            result.append(strLowerCase);

            // at least 2 chars (uppercase)
            String strUppercaseCase = generateRandomString(CHAR_UPPERCASE, 2);
            result.append(strUppercaseCase);

            // at least 2 digits
            String strDigit = generateRandomString(DIGIT, 2);
            result.append(strDigit);

            // at least 2 special characters (punctuation + symbols)
            String strSpecialChar = generateRandomString(OTHER_SPECIAL, 2);
            result.append(strSpecialChar);

            // remaining, just random
            String strOther = generateRandomString(PASSWORD_ALLOW, PASSWORD_LENGTH - 8);
            result.append(strOther);

            String password = result.toString();

            return password;
        }

        // generate a random char[], based on `input`

    /**
     * This methode generates a random password
     * @param input String that has to be randomized
     * @param size size of the String that has to be randomized
     * @return returns a randomized String
     * @exception IllegalArgumentException has be thrown when the size or string is 0 or less
     */
        private static String generateRandomString(String input, int size) {

            if (input == null || input.length() <= 0)
                throw new IllegalArgumentException("Invalid input.");
            if (size < 1) throw new IllegalArgumentException("Invalid size.");

            StringBuilder result = new StringBuilder(size);
            for (int i = 0; i < size; i++) {
                // produce a random order
                int index = random.nextInt(input.length());
                result.append(input.charAt(index));
            }
            return result.toString();
        }
}
