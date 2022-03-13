package ru.intervale.TeamProject.service.generatepdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.intervale.TeamProject.service.bank.Currency;

import java.io.*;

import java.util.Map;
import java.util.stream.Stream;


@Service
public class PDFGenerateServiceImpl implements PDFGeneratorService{
    private static Logger logger = LoggerFactory.getLogger(PDFGenerateServiceImpl.class);
    private  ru.intervale.TeamProject.model.book.BookEntity book;

    @Override
    public ByteArrayInputStream getPdf(String name, Currency currency, Map<String, String> term){

//    byte[] data = new byte[0];
//    File filePdf = new File("demo-file.pdf");
//        if (filePdf.exists()) {
//        try(InputStream inputStream = new FileInputStream(filePdf)) {
//            data = new byte[(int) filePdf.length()];
//            data = inputStream.readAllBytes();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("demo-file.pdf"));
            document.open();

            //добавляем текст в PDF файл
            Font font = FontFactory.getFont(FontFactory.COURIER_BOLD,14, BaseColor.BLUE);
            Paragraph para = new Paragraph("Book - dynamic price", font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(6);
            //создаем header таблицы-->
            Stream.of("Page","Price","Weight", "Isbn", "Writer", "Title")
                    .forEach(headerTitle -> {
                        PdfPCell header = new PdfPCell();
                        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                        header.setBackgroundColor(BaseColor.ORANGE);
                        header.setHorizontalAlignment(Element.ALIGN_CENTER);
                        header.setBorder(2);
                        header.setPhrase(new Phrase(headerTitle, headFont));
                        table.addCell(header);
                    });


            String page = String.valueOf(book.getPage());
            PdfPCell pageCell = new PdfPCell(new Phrase(page));
            pageCell.setPadding(4);
            pageCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            pageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(pageCell);

            PdfPCell priceCell = new PdfPCell(new Phrase(String.valueOf(book.getPrice())));
            priceCell.setPaddingLeft(4);
            priceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            priceCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            //добавление цвета в цену-->
            priceCell.setBorderColor(BaseColor.RED);
            table.addCell(priceCell);

            PdfPCell weightCell = new PdfPCell(new Phrase(book.getWeight()));
            weightCell.setPaddingLeft(4);
            weightCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            weightCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(weightCell);

            PdfPCell isbnCell = new PdfPCell(new Phrase(book.getIsbn()));
            isbnCell.setPaddingLeft(4);
            isbnCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            isbnCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(isbnCell);

            PdfPCell writerCell = new PdfPCell(new Phrase(book.getWriter()));
            writerCell.setPaddingLeft(4);
            writerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            writerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(writerCell);

            PdfPCell titleCell = new PdfPCell(new Phrase(book.getTitle()));
            titleCell.setPaddingLeft(4);
            titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(titleCell);

//            for (BookEntityList bookEntityList: book){
//
//            }

            document.add(table);
            document.close();
        }
        catch(DocumentException | FileNotFoundException e) {
            logger.error(e.toString());
        }


       return new ByteArrayInputStream(out.toByteArray());
    }
}