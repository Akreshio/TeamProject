package ru.intervale.TeamProject.service.generatepdf;

import com.itextpdf.text.DocumentException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public interface PDFGeneratorService {

    void export(HttpServletResponse response) throws DocumentException, IOException;
}
