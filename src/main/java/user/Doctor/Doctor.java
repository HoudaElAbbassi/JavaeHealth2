package user.Doctor;

import appointments.appointment.Appointment;
import user.User;
import java.time.LocalDate;
import java.util.List;

/**
 *This class is the Patient class ,it extends the user class ,
 *The Patient is the main user of the application and recieves alot of functionality.
 * @author Ahmed,Amin,Daniel,Houda,Prabal
 */
public class Doctor extends User {
    private Specialization specialization;
    private List<Appointment> appointments;

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    /**
     * the constructor creates a Doctor with all relevant information
     * @param userName Doctor Username
     * @param email Doctor Email Address
     * @param password Doctor Password
     * @param firstName Doctor Firstname
     * @param lastName Doctor Lastname
     * @param address Doctor Address
     * @param birthDate Doctor Birthdate
     * @param specialization Doctor Specialization
     */
    public Doctor(
            String userName,
            String email,
            String password,
            String firstName,
            String lastName,
            String address,
            LocalDate birthDate,
            Specialization specialization) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthDate = birthDate;
        this.specialization=specialization;

    }

    /**
     * default constructor
     */
    public Doctor() {
    }

    /**
     * the Methode is used to return the most relevant Information of a doctor in a String
     * @return a String with the first-, lastname and adresse of the doctor
     */
    @Override
    public String toString() {
        return "Doctor:   "+firstName+" "+lastName+",   "+
                "Address:   "+address +", Specialization: "+specialization.toString();
    }
    }

