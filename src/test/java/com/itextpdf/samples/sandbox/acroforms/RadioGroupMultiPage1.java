/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/30895930/issue-with-itext-radiocheckfield-when-displayed-on-multiple-pages
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.FontFactory;
import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfButtonFormField;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class RadioGroupMultiPage1 extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/acroforms/radio_group_multi_page1.pdf";
    /**
     * Possible values of a Choice field.
     */
    public static final String[] LANGUAGES = {"English", "German", "French", "Spanish", "Dutch"};

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new RadioGroupMultiPage1().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);
        PdfFont font = new PdfType1Font(pdfDoc,
                (Type1Font) FontFactory.createFont(FontConstants.HELVETICA, PdfEncodings.WINANSI), false);
        Rectangle rect = new Rectangle(40, 806, 60 - 40, 788 - 806);
        PdfButtonFormField radioGroup = PdfFormField.createRadioGroup(pdfDoc, "Language", "");
        for (int page = 1; page <= LANGUAGES.length; page++) {
            pdfDoc.addNewPage();
            PdfFormField field = PdfFormField.createRadioButton(pdfDoc, rect, radioGroup, LANGUAGES[page - 1]);
            field.setPage(page);
            doc.showTextAligned(new Paragraph(LANGUAGES[page - 1]).setFont(font).setFontSize(18),
                    70, 787, page, Property.HorizontalAlignment.LEFT, Property.VerticalAlignment.BOTTOM, 0);
        }
        PdfAcroForm.getAcroForm(pdfDoc, true).addField(radioGroup);
        pdfDoc.close();
    }
}