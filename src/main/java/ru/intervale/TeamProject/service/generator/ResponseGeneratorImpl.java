/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "19.03.2022, 10:51"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.generator;

import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.intervale.TeamProject.model.book.BookEntity;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ResponseGeneratorImpl implements ResponseGenerator{

    private PDFGeneratorService pdfGenerator;
    private CsvGeneratorService csvGenerator;


    @Override
    public String generationCsv(List<BookEntity> books) {
        return csvGenerator.getCsv(books);
    }

    @Override
    public byte[] generationSvg(List<BookEntity> books) {
        return new byte[0];
    }

    @Override
    public byte[] generationPdf(List<BookEntity> books) {
            return pdfGenerator.getPdf(books);

    }
}
