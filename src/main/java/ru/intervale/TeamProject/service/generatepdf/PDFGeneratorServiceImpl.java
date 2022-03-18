package ru.intervale.TeamProject.service.generatepdf;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
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



@Service
@Slf4j
public class PDFGeneratorServiceImpl implements PDFGeneratorService{

    public byte[] getPdf(List<BookEntity> bookEntities) {

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
        }catch(DocumentException e) {
            log.error(e.toString());
        }finally {
            document.close();
        }

        return out.toByteArray();
    }

    private  void header(Document document, BookEntity book) throws DocumentException {
        //создание заголовка над таблицей с названием книги
        Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
        Paragraph paragraph = new Paragraph(" Book " + book.getTitle(), font);
        paragraph.setAlignment(Element.ALIGN_CENTER);

        document.add(paragraph);
        document.add(Chunk.NEWLINE);
    }

    private  void  body(Document document, BookEntity book) throws DocumentException {
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
        if ((book.getChangePrice() != null) && (!book.getChangePrice().isEmpty())) {

            for (Map.Entry<LocalDateTime, BigDecimal> price : book.getChangePrice().entrySet()) {

                PdfPCell titleCell = new PdfPCell(new Phrase(formatDate(price.getKey())));
                titleCell.setPaddingLeft(4);
                titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(titleCell);

                PdfPCell priceCell = new PdfPCell(new Phrase(String.valueOf(price.getValue())));
                priceCell.setPaddingLeft(4);
                priceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                priceCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(priceCell);

            }
            document.add(table);
        }
    }

    private  String formatDate(LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return  formatter.format(date);
    }
}