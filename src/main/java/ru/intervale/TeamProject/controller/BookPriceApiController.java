/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 20:40"
 * @version V 1.0.0
 */

/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 20:40"
 * @version V 1.0.0
 */

/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:39"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.controller;

import com.itextpdf.text.DocumentException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.intervale.TeamProject.service.ServiceChangePriceImpl;
import ru.intervale.TeamProject.service.generatepdf.PDFGenerateServiceImpl;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * The type Book price api controller.
 */
@Slf4j
@RestController
@NoArgsConstructor
public class BookPriceApiController implements BookPriceApi {

    @Autowired
    ServiceChangePriceImpl service;

    @Autowired
    PDFGenerateServiceImpl pdfGenerateService;

    @Override
    public List<?> getJson(String name, int currency) {
        return service.get(name, currency);
    }



    @Override
    public void getPdf(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";

        response.setHeader(headerKey, headerValue);

        this.pdfGenerateService.export(response);

    }
}
