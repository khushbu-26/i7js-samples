package com.itextpdf.samples;

import com.itextpdf.basics.PdfException;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfDocumentInfo;
import com.itextpdf.core.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Listing_12_01_MetadataPdf {

    static public final String DEST = "./target/test/resources/Listing_12_01_MetadataPdf.pdf";

    public static void main(String args[]) throws IOException, PdfException {
        new Listing_12_01_MetadataPdf().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws FileNotFoundException, PdfException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document and add page
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.addNewPage();

        PdfDocumentInfo info = pdfDoc.getInfo();
        info.setTitle("Hello World example").setAuthor("Bruno Lowagie").
                setSubject("This example shows how to add metadata").
                setKeywords("Metadata, iText, PDF").
                setCreator("My program using iText");

        //Close document
        pdfDoc.close();
    }

}
