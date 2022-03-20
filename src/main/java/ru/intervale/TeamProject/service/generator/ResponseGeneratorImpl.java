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
import ru.intervale.TeamProject.service.RateCurrencyChanging.Currency;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ResponseGeneratorImpl implements ResponseGenerator{

    private PDFGeneratorService pdfGenerator;
    private CsvGeneratorService csvGenerator;
    private SvgGeneratorService serviceGenerateSvg;


    @Override
    public String generationCsv(List<BookEntity> books) {
        return csvGenerator.getCsv(books);
    }

    @Override
    public byte[] generationSvg(List<BookEntity> books, Currency currency) {
        return serviceGenerateSvg.generateSvg(books, currency);
    }

    @Override
    public byte[] generationPdf(List<BookEntity> books) {
        try {
            return pdfGenerator.getPdf(books);
        } catch (DocumentException e) {
            log.error("No generation pdf file");
        }
        return new byte[0];
    }
}
