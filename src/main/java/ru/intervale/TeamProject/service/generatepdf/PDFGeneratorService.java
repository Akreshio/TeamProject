package ru.intervale.TeamProject.service.generatepdf;

import com.itextpdf.text.DocumentException;
import org.springframework.stereotype.Service;
import ru.intervale.TeamProject.model.book.BookEntity;
import java.io.ByteArrayInputStream;
import java.util.List;


@Service
public interface PDFGeneratorService {

    ByteArrayInputStream getPdf(List<BookEntity> bookEntities) throws DocumentException;
}