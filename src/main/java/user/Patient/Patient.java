package user.Patient;

import user.User;

import java.io.File;
import java.time.LocalDate;

public class Patient extends User {

    private HealthProblem healthProblem;
    private File healthInfo;
    private InsuranceType insuranceType;
    private String insuranceName;

    public HealthProblem getHealthProblem() {
        return healthProblem;
    }

    public void setHealthProblem(HealthProblem healthProblem) {
        this.healthProblem = healthProblem;
    }

    public File getHealthInfo() {
        return healthInfo;
    }


    public void setHealthInfo(File healthInfo) {

        this.healthInfo = healthInfo;
    }

    public InsuranceType getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(InsuranceType insuranceType) {
        this.insuranceType = insuranceType;
    }

    public String getInsuranceName() {
        return insuranceName;
    }

    public void setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName;
    }

    public Patient(String userName,
                   String email,
                   String password,
                   String firstName,
                   String lastName,
                   String address,
                   LocalDate birthDate,
                   InsuranceType insuranceType,
                   String insuranceName
                   ) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthDate = birthDate;
        this.insuranceType = insuranceType;
        this.insuranceName = insuranceName;
    }

    public Patient(){

    }



}
