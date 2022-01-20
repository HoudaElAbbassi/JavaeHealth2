package user.Admin;


import user.User;

import java.sql.Date;
import java.time.LocalDate;

public class Admin extends User {

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

    //public void editUserProfil()
    //public deleteUser()
    //public accessUserProfile()
}
