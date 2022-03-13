package ru.intervale.TeamProject.service.generatepdf;

import ru.intervale.TeamProject.service.bank.Currency;

import java.io.ByteArrayInputStream;
import java.util.Map;


public interface PDFGeneratorService {

    ByteArrayInputStream getPdf(String name, Currency currency, Map<String, String> term);
}