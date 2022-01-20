package utilities;

import user.Doctor.Specialization;
import user.Patient.HealthProblem;

import java.util.EnumMap;

public class EnumMapping {

    public static EnumMap<HealthProblem, Specialization> mapEnums(){
        EnumMap<HealthProblem, Specialization> activityMap = new EnumMap<>(HealthProblem.class);

        //Allergist
        activityMap.put(HealthProblem.values()[0], Specialization.values()[0]);
        activityMap.put(HealthProblem.values()[1], Specialization.values()[0]);
        activityMap.put(HealthProblem.values()[2], Specialization.values()[0]);
        activityMap.put(HealthProblem.values()[3], Specialization.values()[0]);

        //Cardiologist
        activityMap.put(HealthProblem.values()[4], Specialization.values()[1]);
        activityMap.put(HealthProblem.values()[5], Specialization.values()[1]);
        activityMap.put(HealthProblem.values()[6], Specialization.values()[1]);
        activityMap.put(HealthProblem.values()[7], Specialization.values()[1]);
        activityMap.put(HealthProblem.values()[8], Specialization.values()[1]);

        //Surgeon
        activityMap.put(HealthProblem.values()[9], Specialization.values()[2]);
        activityMap.put(HealthProblem.values()[10], Specialization.values()[2]);
        activityMap.put(HealthProblem.values()[11], Specialization.values()[2]);

        //Dermatologist
        activityMap.put(HealthProblem.values()[12], Specialization.values()[3]);
        activityMap.put(HealthProblem.values()[13], Specialization.values()[3]);
        activityMap.put(HealthProblem.values()[14], Specialization.values()[3]);
        activityMap.put(HealthProblem.values()[15], Specialization.values()[3]);

        //Endocrinologist
        activityMap.put(HealthProblem.values()[16], Specialization.values()[4]);
        activityMap.put(HealthProblem.values()[17], Specialization.values()[4]);
        activityMap.put(HealthProblem.values()[18], Specialization.values()[4]);
        activityMap.put(HealthProblem.values()[19], Specialization.values()[4]);

        //Family_Physician
        activityMap.put(HealthProblem.values()[20], Specialization.values()[5]);

        //Gastroenterologist
        activityMap.put(HealthProblem.values()[21], Specialization.values()[6]);
        activityMap.put(HealthProblem.values()[22], Specialization.values()[6]);
        activityMap.put(HealthProblem.values()[23], Specialization.values()[6]);
        activityMap.put(HealthProblem.values()[24], Specialization.values()[6]);

        //Hematologist
        activityMap.put(HealthProblem.values()[25], Specialization.values()[7]);
        activityMap.put(HealthProblem.values()[26], Specialization.values()[7]);
        activityMap.put(HealthProblem.values()[27], Specialization.values()[7]);
        activityMap.put(HealthProblem.values()[28], Specialization.values()[7]);

        //Infectious_Disease_Specialist
        activityMap.put(HealthProblem.values()[29], Specialization.values()[8]);
        activityMap.put(HealthProblem.values()[30], Specialization.values()[8]);
        activityMap.put(HealthProblem.values()[31], Specialization.values()[8]);
        activityMap.put(HealthProblem.values()[32], Specialization.values()[8]);
        activityMap.put(HealthProblem.values()[33], Specialization.values()[8]);
        activityMap.put(HealthProblem.values()[34], Specialization.values()[8]);

        //Nephrologist
        activityMap.put(HealthProblem.values()[35], Specialization.values()[9]);

        //Neurologist
        activityMap.put(HealthProblem.values()[36], Specialization.values()[10]);
        activityMap.put(HealthProblem.values()[37], Specialization.values()[10]);
        activityMap.put(HealthProblem.values()[38], Specialization.values()[10]);
        activityMap.put(HealthProblem.values()[39], Specialization.values()[10]);
        activityMap.put(HealthProblem.values()[40], Specialization.values()[10]);
        activityMap.put(HealthProblem.values()[41], Specialization.values()[10]);

        //Ophthalmologist
        activityMap.put(HealthProblem.values()[42], Specialization.values()[11]);
        activityMap.put(HealthProblem.values()[43], Specialization.values()[11]);

        //Otolaryngologist
        activityMap.put(HealthProblem.values()[44], Specialization.values()[12]);
        activityMap.put(HealthProblem.values()[45], Specialization.values()[12]);
        activityMap.put(HealthProblem.values()[46], Specialization.values()[12]);
        activityMap.put(HealthProblem.values()[47], Specialization.values()[12]);
        activityMap.put(HealthProblem.values()[48], Specialization.values()[12]);
        activityMap.put(HealthProblem.values()[49], Specialization.values()[12]);
        activityMap.put(HealthProblem.values()[50], Specialization.values()[12]);

        //Psychiatrist
        activityMap.put(HealthProblem.values()[51], Specialization.values()[13]);
        activityMap.put(HealthProblem.values()[52], Specialization.values()[13]);
        activityMap.put(HealthProblem.values()[53], Specialization.values()[13]);
        activityMap.put(HealthProblem.values()[54], Specialization.values()[13]);

        //Pulmonologist
        activityMap.put(HealthProblem.values()[55], Specialization.values()[14]);
        activityMap.put(HealthProblem.values()[56], Specialization.values()[14]);

        //Rheumatologist
        activityMap.put(HealthProblem.values()[57], Specialization.values()[15]);
        activityMap.put(HealthProblem.values()[58], Specialization.values()[15]);
        activityMap.put(HealthProblem.values()[59], Specialization.values()[15]);
        activityMap.put(HealthProblem.values()[60], Specialization.values()[15]);
        activityMap.put(HealthProblem.values()[61], Specialization.values()[15]);

        // General_practitioner
        activityMap.put(HealthProblem.values()[62], Specialization.values()[16]);
        activityMap.put(HealthProblem.values()[63], Specialization.values()[16]);
        activityMap.put(HealthProblem.values()[64], Specialization.values()[16]);
        activityMap.put(HealthProblem.values()[65], Specialization.values()[16]);
        activityMap.put(HealthProblem.values()[66], Specialization.values()[16]);
        activityMap.put(HealthProblem.values()[67], Specialization.values()[16]);
        activityMap.put(HealthProblem.values()[68], Specialization.values()[16]);
        activityMap.put(HealthProblem.values()[69], Specialization.values()[16]);
        activityMap.put(HealthProblem.values()[70], Specialization.values()[16]);

        return activityMap;

    }
}
