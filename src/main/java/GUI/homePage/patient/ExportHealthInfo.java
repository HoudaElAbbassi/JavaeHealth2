package GUI.homePage.patient;

import appointments.AppointmentDAOImp;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.io.FilenameUtils;
import user.Patient.Patient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class ExportHealthInfo extends JFrame {

    Patient patient;
    private JPanel mainPanel;
    private JButton exportAsAPDFButton;
    private JButton goBackToHomepageButton;

    File textFile = null;
    private JButton exportAsATEXTButton;

    public ExportHealthInfo(Patient patient) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.patient = patient;
        setContentPane(mainPanel);
        setSize(500,500);


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

        goBackToHomepageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PatientHomePage patientHomePage = new PatientHomePage(patient);
                setVisible(false);
                patientHomePage.setVisible(true);
            }
        });
    }
}

