package ru.intervale.TeamProject.service.generator.impl;

import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.svg.SVGGraphics2D;
import org.springframework.stereotype.Service;
import ru.intervale.TeamProject.model.book.BookEntity;
import ru.intervale.TeamProject.service.generator.SvgGeneratorService;
import ru.intervale.TeamProject.service.rate.Currency;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@Slf4j
public class SvgGeneratorServiceImpl implements SvgGeneratorService {

    @Override
    public byte[] generateSvg (List<BookEntity> bookEntityList, Currency currency) {

        log.debug("Get Svg by list of bookEntities = {}, currency = {}", bookEntityList, currency);
        //Создаем график с заданными размерами и записываем в файл
        SVGGraphics2D graphics2D = new SVGGraphics2D(600, 400);
        Rectangle rectangle = new Rectangle(0, 0, 600, 400);
        createChart(bookEntityList, currency).draw(graphics2D, rectangle);
        return graphics2D.getSVGElement().getBytes();
    }

    private XYDataset createDataset(List<BookEntity> bookEntityList) {

        // Создаем коллекцию значений для каждой книги
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        for (BookEntity bookEntity : bookEntityList) {
        TimeSeries timeSeries = new TimeSeries("Автор: " + bookEntity.getWriter());
        bookEntity.getChangePrice().forEach((k, v) -> {
                timeSeries.add(new Day(
                    k.getDayOfMonth(),
                    k.getMonthValue(),
                    k.getYear()),
                    v.doubleValue());
        });
        dataset.addSeries(timeSeries);
            log.debug("Get dataset = {}", dataset);
    }
        return dataset;
    }

    private JFreeChart createChart(List<BookEntity> bookEntityList, Currency currency) {

        //Создаем общий график и добавляем коллекции значений
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Изменение стоимости книги \"" + bookEntityList.get(0).getTitle() + "\"" ,      // title
                "",                                                                             // x-axis label
                "Стоимость в " + currency,                                                      // y-axis label
                createDataset(bookEntityList),                                                  // data
                true,                                                                           // create legend
                true,                                                                           // generate tooltips
                false                                                                           // generate URLs
        );

        // Настройки отрисовки (цвета фона, линий; размеры точек)
        chart.setBackgroundPaint(Color.white);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.cyan);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint (Color.lightGray);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        //Настройки отображения
        XYItemRenderer itemRenderer = plot.getRenderer();
        if (itemRenderer instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) itemRenderer;
            renderer.setDefaultShapesVisible   (true);
            renderer.setDefaultShapesFilled    (true);
            renderer.setDrawSeriesLineAsPath(true);
        }
        // Указываем формат даты
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("dd.MM.yyyy"));

        return chart;
    }

}
