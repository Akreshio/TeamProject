package ru.intervale.TeamProject.service.generatepdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class PDFGenerator implements PDFGeneratorService{

    private static Logger logger = LoggerFactory.getLogger(PDFGenerator.class);

    public ByteArrayInputStream getPdf(List<BookEntity> bookEntities) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfWriter.getInstance(document, out);
            document.open();

            // Add Text to PDF file ->
            Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            Paragraph para = new Paragraph( " Table", font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(2);
            // Add PDF Table Header ->
            Stream.of("Title", "Price")
                    .forEach(headerTitle -> {
                        PdfPCell header = new PdfPCell();
                        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setHorizontalAlignment(Element.ALIGN_CENTER);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(headerTitle, headFont));
                        table.addCell(header);
                    });

            for (BookEntity book : bookEntities) {
                PdfPCell titleCell = new PdfPCell(new Phrase(book.getTitle()));
                titleCell.setPaddingLeft(4);
                titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(titleCell);

                PdfPCell priceCell = new PdfPCell(new Phrase(String.valueOf(book.getPrice())));
                priceCell.setPaddingLeft(4);
                priceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                priceCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(priceCell);


//                    for (Map.Entry<String, BigDecimal> price : book.getChangePrice().entrySet()) {
//                        priceCell = new PdfPCell(new Phrase(String.valueOf(price)));
//                        priceCell.setPaddingLeft(4);
//                        priceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                        priceCell.setHorizontalAlignment(Element.ALIGN_LEFT);
//                        table.addCell(priceCell);
//
//                }
            }

            document.add(table);

            document.close();
        }catch(DocumentException e) {
            logger.error(e.toString());
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

}