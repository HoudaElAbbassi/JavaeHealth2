import Exceptions.PasswordException;
import GUI.Login;

import javax.swing.*;

/**
 * Our Application starts with this class.
 */
public class Main {

    /**
     * This method starts the Application by instantiating the Login Page
     * @param args
     * @throws PasswordException
     */
    public static void main(String[] args) throws PasswordException {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        Login login = new Login();
        login.setVisible(true);
    }

}