package user.Admin;


import user.User;

import java.time.LocalDate;

public class Admin extends User {
    /** 
    
    *This class is the Admin class ,it extends the user class , 
    *the admin can addionally edit , access and delete profiles of other users.
    *@author Prabal, Daniel, Houda , Amine , Ahmed
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

    public Admin() {

    }
}
