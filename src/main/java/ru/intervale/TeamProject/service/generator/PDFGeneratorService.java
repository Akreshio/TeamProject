/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "18.03.2022, 23:58"
 * @version V 1.0.0
 */

/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "18.03.2022, 23:56"
 * @version V 1.0.0
 */

/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "18.03.2022, 23:39"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.generator;

import com.itextpdf.text.DocumentException;
import ru.intervale.TeamProject.model.book.BookEntity;

import java.util.List;



public interface PDFGeneratorService {

    byte[] getPdf(List<BookEntity> bookEntities) throws DocumentException;
}