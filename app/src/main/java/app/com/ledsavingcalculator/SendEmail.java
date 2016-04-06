package app.com.ledsavingcalculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
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
import app.com.ledsavingcalculator.util.Calculations;
import app.com.ledsavingcalculator.util.Mail;


public class SendEmail extends Activity {

    EditText emailadd, contactname, companyname, facilitysize, designation, address;
    Button nextBtn, backBtn;
    String email,Contactname, Companyname, fsize, Designation, Address;
    private  static BaseColor HorizonColor = new BaseColor(34, 78, 48);
    private  static BaseColor HorizonRedColor = new BaseColor(192, 0, 0);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);
        emailadd = (EditText)findViewById(R.id.emailadd);
        contactname = (EditText)findViewById(R.id.contactname);
        companyname = (EditText)findViewById(R.id.companyname);
        facilitysize = (EditText)findViewById(R.id.facilitysize);
        designation = (EditText)findViewById(R.id.designation);
        address = (EditText)findViewById(R.id.address);
        nextBtn = (Button)findViewById(R.id.nextBtn);
        backBtn = (Button)findViewById(R.id.backBtn);
        setupUI(findViewById(R.id.parent));

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
                        Borders event = new Borders();
                        document.setPageSize(PageSize.A4);
                        PdfWriter writer = PdfWriter.getInstance(document, openFileOutput("project-proposal-ILS.pdf", MODE_PRIVATE));
                        writer.setPageEvent(event);
                        document.open();
                        addTitlePage(document);
                        addContent(document);
                        addContentSecond(document);
                        addContentThird(document);
                        document.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String from = "horizonsolutionsltd@gmail.com"; // email
                    final Mail mail = new Mail(from, "Hesolutions123");
                    final Mail mail1 = new Mail(from, "Hesolutions123");
                    String to = emailadd.getText().toString();
                    String to1 = "contact@hesolutions.ca";
                    String subject = "Project Proposal-ILS";
                    String message = "Hello,\nThank you for considering Horizon Engineering Solutions. Please find the complete project " +
                            "proposal attached to this email. \n" + "\n" + "\n" + "\n"+ "Regards, \n" + "Hariharan Krithivasan \n"
                            + "Chief Executive Officer \n" + "Horizon Engineering Solutions \n" + "Email:hari@hesolutions.ca \n"+
                            "Mobile:(+1)226-792-1506 \n" + "Work:(+1)519-749-3373\n" + "Website: www.hesolutions.ca\n";
                    //String attachements = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PdfTest/Test.pdf";
                    File attachementsfile = new File(getFilesDir(),"project-proposal-ILS.pdf");
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
                        protected void onPostExecute(Void res) {
                            emailadd.setText("");
                            contactname.setText("");
                            companyname.setText("");
                            facilitysize.setText("");
                            designation.setText("");
                            address.setText("");
                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendEmail.this);
                            alertDialog.setTitle("Email sent successfully");
                            alertDialog.setCancelable(false);
                            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    Intent intent = new Intent(SendEmail.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            });

                            alertDialog.show();
                        }
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
            image.scaleAbsolute(550f, 100f);
            image.setAlignment(Element.ALIGN_CENTER);
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

        try {
            Drawable d = getResources().getDrawable(R.drawable.smalllogo);
            BitmapDrawable bitDw = ((BitmapDrawable) d);
            Bitmap bmp = bitDw.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image image = Image.getInstance(stream.toByteArray());
            image.scaleAbsolute(150f, 35f);
            image.setAlignment(Element.ALIGN_RIGHT);
            document.add(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Paragraph preface = new Paragraph();
        // Second parameter is the number of the chapter
        preface.add(new Paragraph("Section 1: Existing Lighting system analysis", subFontUnderline));
        preface.setAlignment(Element.ALIGN_LEFT);
        addEmptyLine(preface, 3);
        document.add(preface);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getBaseContext());

        Dao<ExistingBulb, Integer> existingBulbDao = null;
        try {
            existingBulbDao = dataBaseHelper.getExistingBulbDao();
            Dao<Results, Integer> resultDao = dataBaseHelper.getResultDao();
            List<Results> resultses = resultDao.queryForAll();
            List<ExistingBulb> existingBulbs = existingBulbDao.queryForAll();
            int number = 0;
            for (ExistingBulb existingBulb : existingBulbs) {
                Results results1 = resultses.get(number);
                double totalWeeklyHour = results1.getWeeklyActiveHour();
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
                DecimalFormat f = new DecimalFormat("##.00");
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
                cell = new PdfPCell(new Phrase((f.format(totalWeeklyHour)), smallNormal));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
                document.add(preface1);
                document.add(table);
            }
        } catch (SQLException e) {
        e.printStackTrace();
        }
        /*
        PdfPTable table = new PdfPTable(2);
        table.setTotalWidth(document.getPageSize().getWidth() - 80);
        table.setLockedWidth(true);
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("Average Monthly Cost on lighting system (Hydro Cost)", smallBoldHorizon));
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("$" + fsize + "(CAD)", smallBoldHorizon));
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
        */

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

        try {
            Drawable d = getResources().getDrawable(R.drawable.smalllogo);
            BitmapDrawable bitDw = ((BitmapDrawable) d);
            Bitmap bmp = bitDw.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image image = Image.getInstance(stream.toByteArray());
            image.scaleAbsolute(150f, 35f);
            image.setAlignment(Element.ALIGN_RIGHT);
            document.add(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                typenew = resultses.getReplacementLight();
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
            image.scaleAbsolute(400f, 200f);
            image.setAlignment(Element.ALIGN_CENTER);
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
            image2.scaleAbsolute(400f, 200f);
            image2.setAlignment(Element.ALIGN_CENTER);
            document.add(emptypreface);
            document.add(image2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Paragraph horizonaxis = new Paragraph("Jan   Feb   Mar   Apr   May   Jun   Jul   Aug   Sep   Oct   Nov   Dec", smallNormal);
        horizonaxis.setAlignment(Element.ALIGN_CENTER);
        document.add(horizonaxis);
        // Start a new page
        document.newPage();
    }
    private void addContentThird(Document document) throws DocumentException {
        Paragraph emptypreface = new Paragraph();
        addEmptyLine(emptypreface, 2);
        // Second parameter is the number of the chapter

        try {
            Drawable d = getResources().getDrawable(R.drawable.smalllogo);
            BitmapDrawable bitDw = ((BitmapDrawable) d);
            Bitmap bmp = bitDw.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image image = Image.getInstance(stream.toByteArray());
            image.scaleAbsolute(150f, 35f);
            image.setAlignment(Element.ALIGN_RIGHT);
            document.add(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            /// Image image2 = Image.getInstance(Environment.getExternalStorageDirectory().getAbsolutePath() + "/PdfTest/save.png");
            String yourFilePath = getFilesDir() + "/" + "costyearly.png";
            Image image2 = Image.getInstance(yourFilePath);
            image2.scaleAbsolute(400f, 200f);
            image2.setAlignment(Element.ALIGN_CENTER);
            String yourFilePath1 = getFilesDir() + "/" + "saveyearly.png";
            Image image1 = Image.getInstance(yourFilePath1);
            image1.scaleAbsolute(400f, 200f);
            image1.setAlignment(Element.ALIGN_CENTER);
            document.add(emptypreface);
            document.add(image2);
            document.add(emptypreface);
            document.add(image1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Start a new page
        document.newPage();
    }

    public class Borders extends PdfPageEventHelper
    {
        @Override
        public  void onEndPage(PdfWriter writer, Document document){
            PdfContentByte canvas = writer.getDirectContent();
            Rectangle rect = document.getPageSize();
            rect.setBorder(Rectangle.BOX);
            rect.setBorderWidth(10);
            rect.setBorderColor(HorizonColor);
            rect.setUseVariableBorders(true);
            canvas.rectangle(rect);
        }
    }
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    public void setupUI(View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    Calculations.hideSoftKeyboard(SendEmail.this);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

}
