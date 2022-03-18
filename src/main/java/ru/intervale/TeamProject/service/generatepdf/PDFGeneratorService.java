package ru.intervale.TeamProject.service.generatepdf;

import com.itextpdf.text.DocumentException;
import ru.intervale.TeamProject.model.book.BookEntity;

import java.util.List;



public interface PDFGeneratorService {

    byte[] getPdf(List<BookEntity> bookEntities) throws DocumentException;
}