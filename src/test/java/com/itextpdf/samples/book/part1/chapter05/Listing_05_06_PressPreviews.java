package com.itextpdf.samples.book.part1.chapter05;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.border.Border;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.renderer.CellRenderer;
import com.itextpdf.model.renderer.TableRenderer;
import com.itextpdf.samples.GenericTest;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.PojoFactory;
import com.lowagie.filmfestival.Screening;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_05_06_PressPreviews extends GenericTest {
    static public final String DEST =
            "./target/test/resources/book/part1/chapter05/Listing_05_06_PressPreviews.pdf";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_05_06_PressPreviews().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        // create the database connection
        DatabaseConnection connection = new HsqldbConnection("filmfestival");

        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(PageSize.A4).rotate());

        Table table = getTable(connection);

        doc.add(table);

        doc.close();
        connection.close();
    }

    public Table getTable(DatabaseConnection connection) throws UnsupportedEncodingException, SQLException {
        Table table = new Table(new float[]{1, 2, 2, 5, 1});
        // TODO setWidth(0) do not render well with headers/footers
        table.setWidthPercent(100);
        // TODO No facility to set default-cell properties
        // table.getDefaultCell().setPadding(5);
        // table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        // table.getDefaultCell().setCellEvent(new PressPreviews());
        // TODO One may not know the row range limits
        table.setNextRenderer(new PressPreviewTableRenderer(table, new Table.RowRange(0, 30)));
        table.setBorder(null);
        Cell cell;
        // TODO Setting cell renderer on headers/footers do not lasts more them one page
        cell = new Cell()
                .add(new Paragraph("Location"))
                .setPadding(5)
                .setBorder(Border.NO_BORDER);
        cell.setNextRenderer(new PressPreviewsCellRenderer(cell));
        table.addHeaderCell(cell);
        cell = new Cell()
                .add(new Paragraph("Date/Time"))
                .setPadding(5)
                .setBorder(Border.NO_BORDER);
        cell.setNextRenderer(new PressPreviewsCellRenderer(cell));
        table.addHeaderCell(cell);
        cell = new Cell()
                .add(new Paragraph("Run Length"))
                .setPadding(5)
                .setBorder(Border.NO_BORDER);
        cell.setNextRenderer(new PressPreviewsCellRenderer(cell));
        table.addHeaderCell(cell);
        cell = new Cell()
                .add(new Paragraph("Title"))
                .setPadding(5)
                .setBorder(Border.NO_BORDER);
        cell.setNextRenderer(new PressPreviewsCellRenderer(cell));
        table.addHeaderCell(cell);
        cell = new Cell()
                .add(new Paragraph("Year")
                ).setPadding(5)
                .setBorder(Border.NO_BORDER);
        cell.setNextRenderer(new PressPreviewsCellRenderer(cell));
        table.addHeaderCell(cell);
        cell = new Cell()
                .add(new Paragraph("Location"))
                .setPadding(5)
                .setBorder(Border.NO_BORDER);
        cell.setNextRenderer(new PressPreviewsCellRenderer(cell));
        table.addFooterCell(cell);
        cell = new Cell()
                .add(new Paragraph("Date/Time"))
                .setPadding(5)
                .setBorder(Border.NO_BORDER);
        cell.setNextRenderer(new PressPreviewsCellRenderer(cell));
        table.addFooterCell(cell);
        cell = new Cell()
                .add(new Paragraph("Run Length"))
                .setPadding(5)
                .setBorder(Border.NO_BORDER);
        cell.setNextRenderer(new PressPreviewsCellRenderer(cell));
        table.addFooterCell(cell);
        cell = new Cell()
                .add(new Paragraph("Title"))
                .setPadding(5)
                .setBorder(Border.NO_BORDER);
        cell.setNextRenderer(new PressPreviewsCellRenderer(cell));
        table.addFooterCell(cell);
        cell = new Cell()
                .add(new Paragraph("Year"))
                .setPadding(5)
                .setBorder(Border.NO_BORDER);
        cell.setNextRenderer(new PressPreviewsCellRenderer(cell));
        table.addFooterCell(cell);

        List<Screening> screenings = PojoFactory.getPressPreviews(connection);
        Movie movie;
        for (Screening screening : screenings) {
            movie = screening.getMovie();
            cell = new Cell()
                    .add(new Paragraph(screening.getLocation()))
                    .setPadding(5)
                    .setBorder(Border.NO_BORDER);
            cell.setNextRenderer(new PressPreviewsCellRenderer(cell));
            table.addCell(cell);
            cell = new Cell()
                    .add(new Paragraph(String.format("%s   %2$tH:%2$tM",
                            screening.getDate().toString(), screening.getTime())))
                    .setPadding(5)
                    .setBorder(Border.NO_BORDER);
            cell.setNextRenderer(new PressPreviewsCellRenderer(cell));
            table.addCell(cell);
            cell = new Cell()
                    .add(new Paragraph(String.format("%d '", movie.getDuration())))
                    .setPadding(5)
                    .setBorder(Border.NO_BORDER);
            cell.setNextRenderer(new PressPreviewsCellRenderer(cell));
            table.addCell(cell);
            cell = new Cell()
                    .add(new Paragraph(movie.getMovieTitle()))
                    .setPadding(5)
                    .setBorder(Border.NO_BORDER);
            cell.setNextRenderer(new PressPreviewsCellRenderer(cell));
            table.addCell(cell);
            cell = new Cell()
                    .add(new Paragraph(String.valueOf(movie.getYear())))
                    .setPadding(5)
                    .setBorder(Border.NO_BORDER);
            cell.setNextRenderer(new PressPreviewsCellRenderer(cell));
            table.addCell(cell);
        }
        return table;
    }


    private class PressPreviewsCellRenderer extends CellRenderer {
        public PressPreviewsCellRenderer(Cell modelElement) {
            super(modelElement);
        }

        @Override
        public void draw(PdfDocument document, PdfCanvas canvas) {
            super.draw(document, canvas);
            Rectangle rect = getOccupiedAreaBBox();
            canvas
                    .rectangle(rect.getLeft() + 2, rect.getBottom() + 2, rect.getWidth() - 4, rect.getHeight() - 4)
                    .stroke();
        }

        @Override
        protected PressPreviewsCellRenderer createOverflowRenderer(int layoutResult) {
            PressPreviewsCellRenderer overflowRenderer = new PressPreviewsCellRenderer(getModelElement());
            overflowRenderer.parent = parent;
            overflowRenderer.modelElement = modelElement;
            overflowRenderer.addAllProperties(getOwnProperties());
            return overflowRenderer;
        }

        @Override
        protected CellRenderer createSplitRenderer(int layoutResult) {
            PressPreviewsCellRenderer splitRenderer = new PressPreviewsCellRenderer(getModelElement());
            splitRenderer.parent = parent;
            splitRenderer.modelElement = modelElement;
            splitRenderer.occupiedArea = occupiedArea;
            splitRenderer.addAllProperties(getOwnProperties());
            return splitRenderer;
        }
    }


    private class PressPreviewTableRenderer extends TableRenderer {
        public PressPreviewTableRenderer(Table modelElement, Table.RowRange rowRange) {
            super(modelElement, rowRange);
        }

        @Override
        public void drawBorder(PdfDocument document, PdfCanvas canvas) {
            Rectangle rect = getOccupiedAreaBBox();
            canvas.rectangle(rect.getLeft(), rect.getBottom(), rect.getWidth(), rect.getHeight()).stroke();
        }

        @Override
        protected PressPreviewTableRenderer makeOverflowRenderer(Table.RowRange rowRange) {
            return new PressPreviewTableRenderer((Table) modelElement, rowRange);
        }

        @Override
        protected PressPreviewTableRenderer makeSplitRenderer(Table.RowRange rowRange) {
            return new PressPreviewTableRenderer((Table) modelElement, rowRange);
        }
    }
}