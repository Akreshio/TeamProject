package ru.intervale.TeamProject.service.generatepdf;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class PDFGenerateServiceImpl implements PDFGeneratorService{

    @Override
    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 35, BaseColor.GREEN);
        fontTitle.setSize(18);


        Paragraph paragraph = new Paragraph("Book change price", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        // Creating a table
        PdfPTable pdfPTable = new PdfPTable(7);

        //Create cells
        PdfPCell pdfPCell1 = new PdfPCell(new Paragraph("id"));
        PdfPCell pdfPCell2 = new PdfPCell(new Paragraph("page"));
        PdfPCell pdfPCell3 = new PdfPCell(new Paragraph("price"));
        PdfPCell pdfPCell4 = new PdfPCell(new Paragraph("weight"));
        PdfPCell pdfPCell5 = new PdfPCell(new Paragraph("isbn"));
        PdfPCell pdfPCell6 = new PdfPCell(new Paragraph("writer"));
        PdfPCell pdfPCell7 = new PdfPCell(new Paragraph("title"));

        //Add cells to table
        pdfPTable.addCell(pdfPCell1);
        pdfPTable.addCell(pdfPCell2);
        pdfPTable.addCell(pdfPCell3);
        pdfPTable.addCell(pdfPCell4);
        pdfPTable.addCell(pdfPCell5);
        pdfPTable.addCell(pdfPCell6);
        pdfPTable.addCell(pdfPCell7);


        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);

//        Paragraph paragraph1 = new Paragraph(String.valueOf(pdfPTable), fontParagraph);
//        paragraph1.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(paragraph);

        document.add(pdfPTable);
        document.close();

    }
}
