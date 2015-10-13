package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.basics.io.ByteArrayOutputStream;
import com.itextpdf.basics.io.RandomAccessSourceFactory;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.model.Document;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class ReadOnlyField extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/acroforms/read_only_field.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ReadOnlyField().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new RandomAccessSourceFactory().createSource(createForm()),
                null, null, null, null, null), new PdfWriter(new FileOutputStream(DEST)));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        form.getField("text").setReadOnly(true).setValue("A B C D E F G H I J K L M N O P Q R S T U V W X Y Z");
        pdfDoc.close();
    }

    public byte[] createForm() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);
        Rectangle rect = new Rectangle(36, 770, 144 - 36, 806 - 770);
        PdfTextFormField tf = PdfFormField.createText(pdfDoc, rect, PdfFont.getDefaultFont(pdfDoc), 20, "text", "text");
        tf.setMultiline(true);
        PdfAcroForm.getAcroForm(pdfDoc, true).addField(tf);
        doc.close();
        return baos.toByteArray();
    }
}