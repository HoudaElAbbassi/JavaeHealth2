package user.Admin;


import user.User;

import java.time.LocalDate;

/**
 * This class is the Admin class ,it extends the user class ,
 *the admin can addionally edit , access and delete profiles of other users.
 *@author Prabal, Daniel, Houda , Amine , Ahmed
 */
public class Admin extends User {
    /**
     * the constructor creates an Admin with all relevant information
     * @param userName username of the Admin
     * @param email email address of the Admin
     * @param password password of the Admin
     * @param firstName Firstname of the Admin
     * @param lastName Lastname of the Admin
     * @param address Address of the Admin
     * @param birthDate Birthdate of the Admin
     */
    public Admin(
            String userName,
            String email,
            String password,
            String firstName,
            String lastName,
            String address,
            LocalDate birthDate) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthDate = birthDate;
    }

    /**
     * default constructor
     */
    Admin(){}
}
