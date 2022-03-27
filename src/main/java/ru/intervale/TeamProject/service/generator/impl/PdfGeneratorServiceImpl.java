/*
 * @author Игорь Прохорченко
 * E-mail: akreshios@gmail.com
 * @since "19.03.2022, 11:55"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.generator.impl;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import ru.intervale.TeamProject.model.book.BookEntity;
import ru.intervale.TeamProject.service.generator.PdfGeneratorService;


@Slf4j
@Service
public class PdfGeneratorServiceImpl implements PdfGeneratorService {

    public byte[] getPdf(List<BookEntity> bookEntities){

        log.debug("Get Pdf by list of bookEntities = {}", bookEntities);

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // верстка документа ->
            for (BookEntity book : bookEntities) {
                // верстка таблиц
                header(document, book);
                body(document, book);
            }
        } catch(DocumentException e) {
            log.error("DocumentException: PDF file generation error");
        } finally {
            document.close();
        }
        return out.toByteArray();
    }

    private void header(Document document, BookEntity book) throws DocumentException {
        //создание заголовка над таблицей с названием книги
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, BaseColor.BLACK);
        Paragraph paragraph = new Paragraph(book.getTitle(), font);
        paragraph.setAlignment(Element.ALIGN_CENTER);

        Paragraph paragraph1 = new Paragraph(" page: " + book.getPage(), font);
        paragraph1.setAlignment(Element.ALIGN_LEFT);
        Paragraph paragraph2 = new Paragraph(" isbn: " + book.getIsbn(), font);
        paragraph2.setAlignment(Element.ALIGN_LEFT);
        Paragraph paragraph3 = new Paragraph(" price: " + book.getPrice(), font);
        paragraph3.setAlignment(Element.ALIGN_LEFT);
        Paragraph paragraph4 = new Paragraph(" weight: " + book.getWeight(), font);
        paragraph4.setAlignment(Element.ALIGN_LEFT);



        document.add(paragraph);
        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(paragraph4);
        document.add(Chunk.NEWLINE);
    }

    private void body(Document document, BookEntity book) throws DocumentException {

        // создание таблицы ->
        PdfPTable table = new PdfPTable(2);

        // добавление шапки таблицы  ->
        Stream.of("Date", "Price in currency")
                .forEach(headerTitle -> {
                    PdfPCell header = new PdfPCell();
                    Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(headerTitle, headFont));
                    table.addCell(header);
                });

        // вывод значений изменения цены
        if ((book.getChangePrice() != null) && (!book.getChangePrice().isEmpty())){

            for (Map.Entry<LocalDateTime, BigDecimal> price : book.getChangePrice().entrySet()) {
                createCell(table, formatDate(price.getKey()));
                createCell(table, formatPrice(price.getValue()));
            }
            document.add(table);
        }
    }

    //формат времени 01.01.2021 00:00
    private String formatDate (LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return  formatter.format(date);
    }

    //формат цены 99.99
    private String formatPrice(BigDecimal price) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(price);
    }

    //создание ячеек таблицы
    private void createCell (PdfPTable table, String str){
        PdfPCell cell = new PdfPCell(new Phrase(str));
        cell.setPaddingLeft(4);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }
}