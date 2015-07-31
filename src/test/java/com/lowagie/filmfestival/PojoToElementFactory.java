package com.lowagie.filmfestival;

import com.itextpdf.core.font.PdfFont;
import com.itextpdf.model.element.Text;

import java.util.ArrayList;
import java.util.List;

public class PojoToElementFactory {

    public static com.itextpdf.model.element.List getDirectorList(Movie movie) {
        com.itextpdf.model.element.List list = new com.itextpdf.model.element.List();
        for (Director director : movie.getDirectors()) {
            list.add(String.format("%s, %s", director.getName(), director.getGivenName()));
        }
        return list;
    }

    public static List<Text> getYearPhrase(Movie movie, PdfFont bold, PdfFont normal) {
        List<Text> year = new ArrayList<>();
        year.add(new Text("Year: ").setFont(bold));
        year.add(new Text(String.valueOf(movie.getYear())).setFont(normal));
        return year;
    }

    public static List<Text> getDurationPhrase(Movie movie, PdfFont bold, PdfFont normal) {
        List<Text> duration = new ArrayList<>();
        duration.add(new Text("Duration: ").setFont(bold));
        duration.add(new Text(String.valueOf(movie.getDuration())).setFont(normal));
        return duration;
    }

    public static com.itextpdf.model.element.List getCountryList(Movie movie) {
        com.itextpdf.model.element.List list = new com.itextpdf.model.element.List();
        for (Country country : movie.getCountries()) {
            list.add(country.getCountry());
        }
        return list;
    }

}