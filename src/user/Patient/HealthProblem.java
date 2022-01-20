package user.Patient;

public enum HealthProblem {
    //Allergist
    Asthma("Asthma"),
    Eczema("Eczema"),
    Food_allergies("Food Allergies"),
    Insect_sting_allergies("Insect Sting Allergies"),

    //Cardiologist
    Heart_Problem("Heart Problem"),
    Heart_Failure("Heart Failure"),
    Heart_Attack("Heart Attack"),
    High_blood_pressure("High Blood Pressure"),
    Irregular_heartbeat("Irregular Heartbeat"),

    //Surgeon
    Colon_cancer("Colon Cancer"),
    Hemorrhoids("Hemorrhoids"),
    Inflammatory_bowel("Inflammatory Bowel"),

    //Dermatologist
    Moles("Moles"),
    Scars("Scars"),
    Acne("Acne"),
    Skin_allergies("Skin Allergies"),

    //Endocrinologist
    Diabetes("Diabetes"),
    Thyroid_problems("Thyroid Problems"),
    Infertility("Infertility"),
    Bone_disorder("Bone Disorder"),

    //Family_Physician
    Routine_checkup("Routine Checkup"),

    //Gastroenterologist
    Abdominal_pain("Abdominal Pain"),
    Ulcers("Ulcers"),
    Diarrhea("Diarrhea"),
    Jaundice("Jaundice"),

    //Hematologist
    Sickle_cell("Sickle Cell"),
    Anemia("Anemia"),
    Hemophilia("Hemophilia"),
    Leukemia("Leukemia"),

    //Infectious_Disease_Specialist
    Fevers("Fevers"),
    Lyme("Lyme"),
    Pneumonia("Pneumonia"),
    Tuberculosis("Tuberculosis"),
    HIV("HIV"),
    AIDS("AIDS"),

    //Nephrologist
    Kidney_disease("Kidney Disease"),

    //Neurologist
    Strokes("Strokes"),
    Brain_problem("Brain Problem"),
    Spinal_tumors("Spinal Tumors"),
    Epilepsy("Epilepsy"),
    Parkinson("Parkinson"),
    Alzheimer("Alzheimer"),

    //Ophthalmologist
    Glaucoma("Glaucoma"),
    Eye_problem("Eye Problem"),

    //Otolaryngologist
    Ears_problem("Ears Problem"),
    Nose_problem("Nose Problem"),
    Throat__problem("Throat Problem"),
    Sinuses_problem("Sinuses Problem"),
    Head_problem("Head Problem"),
    Neck_problem("Neck Problem"),
    Respiratory_system_problem("Respiratory System Problem"),

    //Psychiatrist
    Depression("Depression"),
    Schizophrenia("Schizophrenia"),
    Substance_abuse("Substance Abuse"),
    Anxiety("Anxiety"),

    //Pulmonologist
    Lung_cancer("Lung Cancer"),
    Emphysema("Emphysema"),

    //Rheumatologist
    Osteoporosis("Osteoporosis"),
    Back_pain("Back Pain"),
    Gout("Gout"),
    Tendinitis("Tendinitis"),
    Fibromyalgia("Fibromyalgia"),


    // General Practitioner
    Immune_System_Problems("Immune System Problems"),
    Allergies("Allergies"),
    Flu("Flu"),
    Cold("Cold"),
    Conjunctivitis("Conjunctivitis"),
    Mononucleosis("Mononucleosis"),
    Intestinal_Gas("Intestinal_Gas"),
    Nausea_and_Vomiting("Nausea And/Or Vomiting"),
    Headache("Headache");

    private String value;
    private HealthProblem(String value)
    {
        this.value = value;
    }

    public String toString()
    {
        return this.value;
    }
}
