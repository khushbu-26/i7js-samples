package com.itextpdf.samples.book.chapter03;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.FontFactory;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Div;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.layout.LayoutArea;
import com.itextpdf.model.renderer.DocumentRenderer;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.samples.book.chapter02.StarSeparator;

import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.PojoFactory;
import com.lowagie.filmfestival.PojoToElementFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_03_20_ColumnMovies2 extends GenericTest {

    public static final String DEST = "./target/test/resources/Listing_03_20_ColumnMovies2/Listing_03_20_ColumnMovies2.pdf";

    public static final Rectangle[] COLUMNS = {
            new Rectangle(36, 36, 188, 543), new Rectangle(230, 36, 188, 543),
            new Rectangle(424, 36, 188, 543) , new Rectangle(618, 36, 188, 543)
    };

    public static void main(String[] args) throws Exception {
        new Listing_03_20_ColumnMovies2().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, PageSize.A4.rotate());

        doc.setRenderer(new DocumentRenderer(doc) {
            int nextAreaNumber = 0;
            int currentPageNumber;

            @Override
            public LayoutArea getNextArea() {
                if (nextAreaNumber % 4 == 0) {
                    currentPageNumber = super.getNextArea().getPageNumber();
                }
                return (currentArea = new LayoutArea(currentPageNumber, COLUMNS[nextAreaNumber++ % 4].clone()));
            }
        });

        // Create a database connection
        DatabaseConnection connection = new HsqldbConnection("filmfestival");

        List<Movie> movies = PojoFactory.getMovies(connection);
        for (Movie movie : movies) {
            addContent(doc, movie);
        }

        doc.close();
    }

    public void addContent(Document doc, Movie movie) throws IOException {
        PdfFont bold = new PdfType1Font(doc.getPdfDocument(), (Type1Font) FontFactory.createFont(FontConstants.HELVETICA_BOLD, ""));
        PdfFont italic = new PdfType1Font(doc.getPdfDocument(), (Type1Font) FontFactory.createFont(FontConstants.HELVETICA_OBLIQUE, ""));
        PdfFont normal = new PdfType1Font(doc.getPdfDocument(), (Type1Font) FontFactory.createFont(FontConstants.HELVETICA, ""));

        Div div = new Div().setKeepTogether(true);
        Paragraph p = new Paragraph(movie.getTitle()).setFont(bold).
            setHorizontalAlignment(Property.HorizontalAlignment.CENTER).
            setMargins(0, 0, 0, 0);
        div.add(p);
        if (movie.getOriginalTitle() != null) {
            p = new Paragraph(movie.getOriginalTitle()).setFont(italic).
                    setHorizontalAlignment(Property.HorizontalAlignment.RIGHT).
                    setMargins(0, 0, 0, 0);
            div.add(p);
        }
        p = new Paragraph().
                setMargins(0, 0, 0, 0).
                addAll(PojoToElementFactory.getYearPhrase(movie, bold, normal)).
                add(" ").
                addAll(PojoToElementFactory.getDurationPhrase(movie, bold, normal)).
                setHorizontalAlignment(Property.HorizontalAlignment.JUSTIFIED_ALL);
        div.add(p);
        div.add(new StarSeparator());

        doc.add(div);
    }
}
