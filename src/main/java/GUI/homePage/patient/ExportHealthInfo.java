package GUI.homePage.patient;

import Exceptions.PasswordException;
import GUI.Login;
import appointments.appointment.AppointmentDAOImp;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.io.FilenameUtils;
import user.Patient.Patient;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * this class represents the page in the GUI where a patient can export their health info
 * @author Ahmed Agdmoun
 */
public class ExportHealthInfo extends JFrame {

    /** represents the logged in patient in the actual session */
    Patient patient;

    /** to save the imported health info from the database as a text file */
    File textFile = null;
    private JPanel mainPanel;
    private JButton exportAsAPDFButton;
    private JButton goBackToHomepageButton;
    private JButton exportAsATEXTButton;
    private JButton logOutButton;

    /**
     * an instance which create a frame where the user gets the Option of Exporting as PDF or TXTs File
     * @param patient patient that wants to export the healthinfo
     */
    public ExportHealthInfo(Patient patient) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.patient = patient;
        setContentPane(mainPanel);
        setSize(800,500);

        /**
         * actions taking place after clicking the exportAsTEXTButton
         */
        exportAsATEXTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(exportAsATEXTButton) == JFileChooser.APPROVE_OPTION) {
                    textFile = fileChooser.getSelectedFile();
                    if (FilenameUtils.getExtension(textFile.getName()).equalsIgnoreCase("txt")) {
                        // filename is OK as-is
                    } else {
                        textFile = new File(textFile.toString() + ".txt");  // append .txt
                    }
                }
                try{
                    FileOutputStream out = new FileOutputStream(textFile);
                    AppointmentDAOImp appointmentDAOImp = new AppointmentDAOImp();
                    out.write(appointmentDAOImp.getHealthInfo(patient.getId()));
                    out.close();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        /**
         * actions taking place after clicking the exportAsPDFButton
         */
        exportAsAPDFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(exportAsAPDFButton) == JFileChooser.APPROVE_OPTION) {
                    textFile = fileChooser.getSelectedFile();
                }
                try{
                    FileOutputStream out = new FileOutputStream(textFile);
                    AppointmentDAOImp appointmentDAOImp = new AppointmentDAOImp();
                    out.write(appointmentDAOImp.getHealthInfo(patient.getId()));
                    out.close();
                    Document pdfDoc = new Document(PageSize.A4);
                    PdfWriter.getInstance(pdfDoc, new FileOutputStream(textFile+".pdf"))
                            .setPdfVersion(PdfWriter.PDF_VERSION_1_7);
                    pdfDoc.open();
                    Font myFont = new Font();
                    myFont.setStyle(Font.NORMAL);
                    myFont.setSize(11);
                    pdfDoc.add(new Paragraph("\n"));
                    BufferedReader br = new BufferedReader(new FileReader(textFile));
                    String strLine;
                    while ((strLine = br.readLine()) != null) {
                        Paragraph para = new Paragraph(strLine + "\n", myFont);
                        para.setAlignment(Element.ALIGN_JUSTIFIED);
                        pdfDoc.add(para);
                    }
                    pdfDoc.close();
                    br.close();
                } catch (DocumentException ex) {
                    ex.printStackTrace();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        /**
         * actions taking place after clicking the goBackToHomepageButton
         */
        goBackToHomepageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PatientHomePage patientHomePage = new PatientHomePage(patient);
                setVisible(false);
                patientHomePage.setVisible(true);
            }
        });

        /**
         * actions taking place in case of clicking the logOutButton
         */
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login mainPage = null;
                try {
                    mainPage = new Login();
                } catch (PasswordException ex) {
                    ex.printStackTrace();
                }
                setVisible(false);
                mainPage.setVisible(true);
            }
        });
    }
}

