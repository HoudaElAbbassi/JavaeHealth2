package user.Doctor;

import java.util.HashMap;

public enum Specialization {

    Allergist("Allergist/Immunologist"),

    Cardiologist("Cardiologist"),

    Surgeon("Surgeon"),

    Dermatologist("Dermatologist"),

    Endocrinologist("Endocrinologist"),

    Family_Physician("Family Physician"),

    Gastroenterologist("Gastroenterologist"),

    Hematologist("Hematologist"),

    Infectious_Disease_Specialist("Infectious Disease Specialist"),

    Nephrologist("Nephrologist"),

    Neurologist("Neurologist"),

    Ophthalmologist("Ophthalmologist"),

    Otolaryngologist("Otolaryngologist"),

    Psychiatrist("Psychiatrist"),

    Pulmonologist("Pulmonologist"),

    Rheumatologist("Rheumatologist"),

    General_practitioner("General Practitioner");

    private String value;
    private Specialization(String value)
    {
        this.value = value;
    }

    public String toString()
    {
        return this.value;
    }

}
