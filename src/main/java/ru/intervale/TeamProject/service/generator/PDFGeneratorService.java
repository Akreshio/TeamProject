/*
 * @author Игорь Прохорченко
 * @since "19.03.2022, 11:55"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.generator;

import com.itextpdf.text.DocumentException;
import ru.intervale.TeamProject.model.book.BookEntity;

import java.util.List;

/**
 * The interface Pdf generator service.
 */
public interface PDFGeneratorService {

    /**
     * Get pdf byte [ ].
     *
     * @param bookEntities the book entities
     * @return the byte [ ]
     */
    byte[] getPdf(List<BookEntity> bookEntities);
}