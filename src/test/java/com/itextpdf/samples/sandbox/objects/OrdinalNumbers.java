/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/30322665/how-to-access-the-sup-tag-in-itextsharp
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Text;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class OrdinalNumbers extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/ordinal_numbers.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new OrdinalNumbers().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);
        PdfFont font = PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA);
        Text st = new Text("st").setFont(font).setFontSize(6);
        st.setTextRise(7);
        Text nd = new Text("nd").setFont(font);
        nd.setTextRise(7);
        Text rd = new Text("rd").setFont(font);
        rd.setTextRise(7);
        Text th = new Text("th").setFont(font);
        th.setTextRise(7);
        Paragraph first = new Paragraph();
        first.add("The 1");
        first.add(st);
        first.add(" of May");
        doc.add(first);
        Paragraph second = new Paragraph();
        second.add("The 2");
        second.add(nd);
        second.add(" and the 3");
        second.add(rd);
        second.add(" of June");
        doc.add(second);
        Paragraph fourth = new Paragraph();
        fourth.add("The 4");
        fourth.add(rd);
        fourth.add(" of July");
        doc.add(fourth);
        doc.close();
    }
}