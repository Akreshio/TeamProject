package ru.intervale.TeamProject.service;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;
import org.jfree.ui.RectangleInsets;
import org.springframework.stereotype.Service;
import ru.intervale.TeamProject.model.book.BookEntity;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ServiceGenerateSvgImpl implements ServiceGenerateSvg{

    private XYDataset createDataset(List<BookEntity> bookEntityList) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        for (BookEntity bookEntity : bookEntityList) {
        TimeSeries timeSeries = new TimeSeries("Автор: " + bookEntity.getWriter());
        bookEntity.getChangePrice().forEach((k, v) -> {
            LocalDate localDate = LocalDate.parse(k, formatter);
            timeSeries.add(new Day(
                    localDate.getDayOfMonth(),
                    localDate.getMonthValue(),
                    localDate.getYear()),
                    v.doubleValue());
        });
        dataset.addSeries(timeSeries);
    }
        return dataset;
    }

    private JFreeChart createChart(List<BookEntity> bookEntityList) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Изменение стоимости книги \"" + bookEntityList.get(0).getTitle() + "\"" ,      // title
                "",                                                                             // x-axis label
                "Стоимость",                                                                    // y-axis label
                createDataset(bookEntityList),                                                  // data
                true,                                                                           // create legend
                true,                                                                           // generate tooltips
                false                                                                           // generate URLs
        );

        chart.setBackgroundPaint(Color.white);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.cyan);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint (Color.lightGray);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        XYItemRenderer itemRenderer = plot.getRenderer();
        if (itemRenderer instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) itemRenderer;
            renderer.setBaseShapesVisible   (true);
            renderer.setBaseShapesFilled    (true);
            renderer.setDrawSeriesLineAsPath(true);
        }

        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("dd.MM.yyyy"));

        return chart;
    }

    public void generateSvg (List<BookEntity> bookEntityList) throws IOException {

        SVGGraphics2D graphics2D = new SVGGraphics2D(600, 400);
        Rectangle rectangle = new Rectangle(0, 0, 600, 400);
        createChart(bookEntityList).draw(graphics2D, rectangle);
        File file = new File("SVGChart.svg");
        SVGUtils.writeToSVG(file, graphics2D.getSVGElement());
    }
}
