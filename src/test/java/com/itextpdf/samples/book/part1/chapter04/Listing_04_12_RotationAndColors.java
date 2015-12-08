package com.itextpdf.samples.book.part1.chapter04;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.color.DeviceGray;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.border.Border;
import com.itextpdf.model.border.SolidBorder;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_04_12_RotationAndColors extends GenericTest {
    static public final String DEST =
            "./target/test/resources/book/part1/chapter04/Listing_04_12_RotationAndColors.pdf";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_04_12_RotationAndColors().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(PageSize.A4).rotate());

        Table table = new Table(new float[]{1, 3, 3, 3});
        table.setWidthPercent(100);
        Cell cell;
        // row 1, cell 1
        cell = new Cell().add(new Paragraph("COLOR"));
        cell.setRotationAngle(Math.toRadians(90));
        cell.setVerticalAlignment(Property.VerticalAlignment.TOP);
        table.addCell(cell);
        // row 1, cell 2
        cell = new Cell().add(new Paragraph("red / no borders"));
        cell.setBorder(Border.NO_BORDER);
        cell.setBackgroundColor(Color.RED);
        table.addCell(cell);
        // row 1, cell 3
        cell = new Cell().add(new Paragraph("green / black bottom border"));
        cell.setBorderBottom(new SolidBorder(Color.BLACK, 10f));
        cell.setBackgroundColor(Color.GREEN);
        table.addCell(cell);
        // row 1, cell 4
        cell = new Cell().add(new Paragraph(
                "cyan / blue top border + padding"));
        cell.setBorderTop(new SolidBorder(Color.BLUE, 5f));
        // TODO No setUseBorderPadding
        // cell.setUseBorderPadding(true);
        cell.setBackgroundColor(Color.CYAN);
        table.addCell(cell);
        // row 2, cell 1
        cell = new Cell().add(new Paragraph("GRAY"));
        cell.setRotationAngle(Math.toRadians(90));
        cell.setVerticalAlignment(Property.VerticalAlignment.MIDDLE);
        table.addCell(cell);
        // row 2, cell 2
        cell = new Cell().add(new Paragraph("0.6"));
        cell.setBorder(Border.NO_BORDER);
        cell.setBackgroundColor(new DeviceGray(0.6f));
        table.addCell(cell);
        // row 2, cell 3
        cell = new Cell().add(new Paragraph("0.75"));
        cell.setBorder(Border.NO_BORDER);
        // cell.setGrayFill(0.75f);
        cell.setBackgroundColor(new DeviceGray(0.75f));
        table.addCell(cell);
        // row 2, cell 4
        cell = new Cell().add(new Paragraph("0.9"));
        cell.setBorder(Border.NO_BORDER);
        cell.setBackgroundColor(new DeviceGray(0.9f));
        table.addCell(cell);
        // row 3, cell 1
        cell = new Cell().add(new Paragraph("BORDERS"));
        cell.setRotationAngle(Math.toRadians(90));
        cell.setVerticalAlignment(Property.VerticalAlignment.BOTTOM);
        table.addCell(cell);
        // row 3, cell 2
        cell = new Cell().add(new Paragraph("different borders"));
        // TODO Look at the result to find the difference with itext5
        cell.setBorderLeft(new SolidBorder(Color.RED, 16f));
        cell.setBorderBottom(new SolidBorder(Color.ORANGE, 12f));
        cell.setBorderRight(new SolidBorder(Color.YELLOW, 8f));
        cell.setBorderTop(new SolidBorder(Color.GREEN, 4f));
        table.addCell(cell);
        // row 3, cell 3
        cell = new Cell().add(new Paragraph("with correct padding"));
        // TODO No setUseBorderPadding(boolean)
        // cell.setUseBorderPadding(true);
        cell.setBorderLeft(new SolidBorder(Color.RED, 16f));
        cell.setBorderBottom(new SolidBorder(Color.ORANGE, 12f));
        cell.setBorderRight(new SolidBorder(Color.YELLOW, 8f));
        cell.setBorderTop(new SolidBorder(Color.GREEN, 4f));
        table.addCell(cell);
        // row 3, cell 4
        cell = new Cell().add(new Paragraph("red border"));
        cell.setBorder(new SolidBorder(Color.RED, 8f));
        table.addCell(cell);
        doc.add(table);
        doc.close();
    }
}
