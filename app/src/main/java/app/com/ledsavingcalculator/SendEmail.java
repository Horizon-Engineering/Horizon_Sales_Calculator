package app.com.ledsavingcalculator;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.j256.ormlite.dao.Dao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

import app.com.ledsavingcalculator.database.DataBaseHelper;
import app.com.ledsavingcalculator.database.dao.ExistingBulb;
import app.com.ledsavingcalculator.database.dao.ReplacementBulb;
import app.com.ledsavingcalculator.database.dao.Results;
import app.com.ledsavingcalculator.util.Mail;


public class SendEmail extends AppCompatActivity {

    EditText emailadd, contactname, companyname, facilitysize, designation, address;
    Button nextBtn;
    String email,Contactname, Companyname, fsize, Designation, Address;
    private  static BaseColor HorizonColor = new BaseColor(34, 78, 48);
    private  static BaseColor HorizonRedColor = new BaseColor(192, 0, 0);
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD, HorizonColor);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD, HorizonColor);
    private static Font subFontUnderline = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD|Font.UNDERLINE, HorizonColor);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    private static Font smallBoldHorizon = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD, HorizonColor);
    private static Font smallNormal = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL);
    private static Font smallNormalWhite = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.WHITE);
    private static Font smallNormalHorizon = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, HorizonColor);
    private double totalWeeklyHour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);
        emailadd = (EditText)findViewById(R.id.emailadd);
        contactname = (EditText)findViewById(R.id.contactname);
        companyname = (EditText)findViewById(R.id.companyname);
        facilitysize = (EditText)findViewById(R.id.facilitysize);
        designation = (EditText)findViewById(R.id.designation);
        address = (EditText)findViewById(R.id.address);
        nextBtn = (Button)findViewById(R.id.nextBtn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailadd.getText().toString();
                Contactname = contactname.getText().toString();
                Companyname = companyname.getText().toString();
                fsize = facilitysize.getText().toString();
                Designation = designation.getText().toString();
                Address = address.getText().toString();
                if (!email.equals("") && !Contactname.equals("")&&!Companyname.equals("")&&!fsize.equals("")
                        &&!Designation.equals("")&&!Address.equals("")) {
                    /*
                    try {

                        Document document = new Document();
                        File root = Environment.getExternalStorageDirectory();
                        File dir = new File(root.getAbsolutePath() + "/PdfTest");
                        File file = new File(dir, "Test.pdf");
                        PdfWriter.getInstance(document, new FileOutputStream(file));
                        document.open();

                        addTitlePage(document);
                        addContent(document);
                        addContentSecond(document);
                        document.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    */
                    FileOutputStream outputStream;
                    try {
                        Document document = new Document();
                        PdfWriter.getInstance(document, openFileOutput("Test.pdf", MODE_PRIVATE));
                        document.open();
                        addTitlePage(document);
                        addContent(document);
                        addContentSecond(document);
                        document.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String from = "horizonsolutionsltd@gmail.com"; // email
                    final Mail mail = new Mail(from, "Hesolutions123");
                    final Mail mail1 = new Mail(from, "Hesolutions123");
                    String to = emailadd.getText().toString();
                    String to1 = "contact@hesolutions.ca";
                    String subject = "Test Send";
                    String message = "Just the attachment";
                    //String attachements = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PdfTest/Test.pdf";
                    File attachementsfile = new File(getFilesDir(),"Test.pdf");
                    String attachements = attachementsfile.toString();
                    if (subject != null && subject.length() > 0) {
                        mail.setSubject(subject);
                        mail1.setSubject(subject);
                    } else {
                        mail.setSubject("Subject");
                        mail1.setSubject("Subject");
                    }

                    if (message != null && message.length() > 0) {
                        mail.setBody(message);
                        mail1.setBody(message);
                    } else {
                        mail.setBody("Message");
                        mail1.setBody("Message");
                    }

                    mail.setFrom(from);
                    mail.setTo(new String[]{to});
                    mail1.setFrom(from);
                    mail1.setTo(new String[]{to1});

                    if (attachements != null) {
                        try {
                            mail.addAttachment(attachements);
                            mail1.addAttachment(attachements);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    new AsyncTask<Void, Void, Void>() {

                        @Override
                        protected void onPreExecute() {
                            emailadd.setText("");
                            contactname.setText("");
                            companyname.setText("");
                            facilitysize.setText("");
                            designation.setText("");
                            address.setText("");
                            Toast.makeText(SendEmail.this, "Send successfully", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        protected Void doInBackground(Void... params)
                        {
                            try {
                                mail.send();
                                mail1.send();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                        @Override
                        protected void onPostExecute(Void res) {}
                    }.execute();
                }else
                {
                    Toast.makeText(SendEmail.this, "Missing information", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void addTitlePage(Document document)
            throws DocumentException {
        Paragraph emptypreface = new Paragraph();
        addEmptyLine(emptypreface, 6);
        try {
            Drawable d = getResources().getDrawable(R.drawable.horizonlogo);
            BitmapDrawable bitDw = ((BitmapDrawable) d);
            Bitmap bmp = bitDw.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image image = Image.getInstance(stream.toByteArray());
            image.scalePercent(18f);
            document.add(emptypreface);
            document.add(image);
            document.add(emptypreface);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Paragraph preface = new Paragraph("Project Proposal – HORIZON-ILS™", subFont);
        preface.setAlignment(Element.ALIGN_CENTER);
        document.add(preface);

        PdfPTable table = new PdfPTable(2);
        table.setTotalWidth(document.getPageSize().getWidth() - 80);
        table.setLockedWidth(true);
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("Product Information", smallBold));
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("HORIZON-ILS™ Base configuration ", smallBoldHorizon));
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Client Details", smallBold));
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(" ", smallNormal));
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Company Name", smallBold));
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase( Companyname, smallNormal));
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Contact Person", smallBold));
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(Contactname, smallNormal));
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Designation", smallBold));
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(Designation, smallNormal));
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Address", smallBold));
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(Address, smallNormal));
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Email", smallBold));
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(email, smallNormal));
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
        document.add(emptypreface);
        document.add(table);
        // Start a new page
        document.newPage();
    }
    private void addContent(Document document) throws DocumentException {
        Paragraph preface = new Paragraph();
        // Second parameter is the number of the chapter
        preface.add(new Paragraph("Section 1: Existing Lighting system analysis", subFontUnderline));
        preface.setAlignment(Element.ALIGN_LEFT);
        addEmptyLine(preface, 3);
        document.add(preface);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getBaseContext());

        try {
            Dao<Results, Integer> resultDao = dataBaseHelper.getResultDao();
            List<Results> resultses = resultDao.queryForAll();
            for(Results results : resultses){
                totalWeeklyHour = results.getWeeklyActiveHour();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Dao<ExistingBulb, Integer> existingBulbDao = null;
        try {
            existingBulbDao = dataBaseHelper.getExistingBulbDao();
            List<ExistingBulb> existingBulbs = existingBulbDao.queryForAll();
            int number = 0;
            for (ExistingBulb existingBulb : existingBulbs) {
                number++;
                Paragraph preface1 = new Paragraph();
                preface1.add(new Paragraph("Light " +number, smallBoldHorizon));
                addEmptyLine(preface1, 1);

                String typeoflight = existingBulb.getTypeOfLight();
                int wattageperbulb = existingBulb.getWattageOfBulb();
                int numberoffixture = existingBulb.getNoOfFixtures();
                int bulbperfixture = existingBulb.getNoOfBulbsPerFixture();
                int totalbulb = numberoffixture*bulbperfixture;
                int totalwattage = totalbulb * wattageperbulb;

                PdfPTable table = new PdfPTable(2);
                table.setTotalWidth(document.getPageSize().getWidth() - 80);
                table.setLockedWidth(true);
                PdfPCell cell;
                cell = new PdfPCell(new Phrase("Facility Size – Approx", smallBold));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(fsize + "sq.ft", smallNormal));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Lighting System", smallBold));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(typeoflight, smallNormal));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Number of Fixtures", smallBold));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(Integer.toString(numberoffixture), smallNormal));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Number of light bulbs per fixture", smallBold));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(Integer.toString(bulbperfixture), smallNormal));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Total number of light bulbs", smallBold));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(Integer.toString(totalbulb), smallNormal));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Wattage - Per light bulb", smallBold));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(Integer.toString(wattageperbulb) + "Watts", smallNormal));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Total Wattage – lighting system", smallBold));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(Integer.toString(totalwattage) + "Watts", smallNormal));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Operational Hours", smallBold));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(Double.toString(totalWeeklyHour), smallNormal));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
                document.add(preface1);
                document.add(table);
            }
        } catch (SQLException e) {
        e.printStackTrace();
        }

        // Start a new page
        document.newPage();
    }
    private void addContentSecond(Document document) throws DocumentException {
        Paragraph preface = new Paragraph();
        Paragraph emptypreface = new Paragraph();
        addEmptyLine(emptypreface, 2);
        // Second parameter is the number of the chapter
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getBaseContext());
        String typenew = "";

        preface.add(new Paragraph("Section 2: HORIZON-ILS™ ", subFontUnderline));
        preface.setAlignment(Element.ALIGN_LEFT);
        document.add(preface);
        PdfPTable table = new PdfPTable(2);
        table.setTotalWidth(document.getPageSize().getWidth() - 80);
        table.setLockedWidth(true);
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("Proposed System", smallBold));
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("HORIZON-ILS™", smallNormalHorizon));
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
        try {
            Dao<ReplacementBulb, Integer> replacementBulbs = dataBaseHelper.getReplacementBulbDao();
            List<ReplacementBulb> replacementBulbs1 = replacementBulbs.queryForAll();
            for (ReplacementBulb resultses : replacementBulbs1) {
                typenew = resultses.getTypeOfReplacement();
                cell = new PdfPCell(new Phrase("Proposed light fixture type", smallNormal));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(typenew, smallNormal));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        double monthlyCostSavingsForSummer = 0, monthlyCostSavingsForWinter = 0, averageCostSavings = 0,
                mothlyCostForLEDForSummer = 0, mothlyCostForLEDForWinter = 0, averageCostForLED = 0;
        try {
            Dao<Results, Integer> resultDao = dataBaseHelper.getResultDao();
            List<Results> resultses = resultDao.queryForAll();
            for (Results results : resultses) {
                monthlyCostSavingsForSummer += results.getMonthlyElectricityOfExistingBulbForSummer();
                monthlyCostSavingsForWinter += results.getMonthlyCostForExistingBulbsForWinter();

                mothlyCostForLEDForSummer += results.getMonthlyElecticityCostForLEDForSummer();
                mothlyCostForLEDForWinter += results.getMonthlyElecticityCostForLEDForWinter();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        double totalSavingsForExistings = (monthlyCostSavingsForSummer + monthlyCostSavingsForWinter)/2.0;
        double totalSavingsForLED = (mothlyCostForLEDForSummer + mothlyCostForLEDForWinter)/2.0;
        DecimalFormat f = new DecimalFormat("##.00");

        totalSavingsForExistings = Double.parseDouble(f.format(totalSavingsForExistings));
        totalSavingsForLED = Double.parseDouble(f.format(totalSavingsForLED));
        averageCostSavings = totalSavingsForExistings / 12.0;
        averageCostForLED = totalSavingsForLED / 12.0;
        averageCostSavings = Double.parseDouble(f.format(averageCostSavings));
        averageCostForLED = Double.parseDouble(f.format(averageCostForLED));
        PdfPTable table2 = new PdfPTable(2);
        table2.setTotalWidth(document.getPageSize().getWidth() - 80);
        table2.setLockedWidth(true);
        cell = new PdfPCell(new Phrase("Existing System", smallBold));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorder(PdfPCell.NO_BORDER);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("HORIZON-ILS™", smallBold));
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("Hydro expense per year      \t$" + totalSavingsForExistings, smallNormalWhite));
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setBackgroundColor(HorizonRedColor);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("Hydro expense per year      \t$" + totalSavingsForLED, smallNormalWhite));
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setBackgroundColor(HorizonColor);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("Hydro expense per month     \t$" + averageCostSavings, smallNormalWhite));
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setBackgroundColor(HorizonRedColor);
        table2.addCell(cell);
        cell = new PdfPCell(new Phrase("Hydro expense per month     \t$" + averageCostForLED, smallNormalWhite));
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setBackgroundColor(HorizonColor);
        table2.addCell(cell);

        document.add(emptypreface);
        document.add(table);
        try {
            Drawable d = getResources().getDrawable(R.drawable.lights);
            BitmapDrawable bitDw = ((BitmapDrawable) d);
            Bitmap bmp = bitDw.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image image = Image.getInstance(stream.toByteArray());
            image.scalePercent(12f);
            document.add(emptypreface);
            document.add(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        document.add(emptypreface);
        document.add(table2);
        try {
           /// Image image2 = Image.getInstance(Environment.getExternalStorageDirectory().getAbsolutePath() + "/PdfTest/save.png");
            String yourFilePath = getFilesDir() + "/" + "cost.png";
            Image image2 = Image.getInstance(yourFilePath);
            image2.scalePercent(30f);
            document.add(emptypreface);
            document.add(image2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Paragraph horizonaxis = new Paragraph("                 Jan Feb Mar Apr May Jun Jul Aug Sep Oct Nov Dec", smallNormal);
        document.add(horizonaxis);
        // Start a new page
        document.newPage();
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

}
