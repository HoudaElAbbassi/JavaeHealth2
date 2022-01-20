package user.Doctor;

import appointments.Appointment;
import user.User;


import java.time.LocalDate;
import java.util.List;

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

    public Doctor() {
    }

    @Override
    public String toString() {
        return "Doctor:   "+firstName+" "+lastName+",   "+
                "Address:   "+address;
    }

    //public List<Appointment> addAppointment(Appointment appointment)
    //public void cancelAppointment(Appointment appointment)

    }

