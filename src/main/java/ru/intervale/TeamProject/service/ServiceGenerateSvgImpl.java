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
import ru.intervale.TeamProject.util.DateParsing;
import ru.intervale.TeamProject.util.model.DayDateModel;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Map;

public class ServiceGenerateSvgImpl implements ServiceGenerateSvg{

    private XYDataset createDataset(Map<String, BigDecimal> changeMap, String currency) {

        TimeSeries timeSeries = new TimeSeries("Цены указаны в " + currency);
        changeMap.forEach((k, v) -> {
            DayDateModel dayDateModel = DateParsing.dayDateParsing(k);
            timeSeries.add(new Day(
                    dayDateModel.getDay(),
                    dayDateModel.getMonth(),
                    dayDateModel.getYear()),
                    v.doubleValue());
        });

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(timeSeries);

        return dataset;
    }

    private JFreeChart createChart(Map<String, BigDecimal> changeMap, String name, String currency) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Изменение стоимости книги \"" + name + "\"" ,      // title
                "",                                                 // x-axis label
                "Стоимость",                                        // y-axis label
                createDataset(changeMap, currency),                 // data
                true,                                               // create legend
                true,                                               // generate tooltips
                false                                               // generate URLs
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
        axis.setDateFormatOverride(new SimpleDateFormat("dd.MM.yy"));

        return chart;
    }

    public void generateSvg (Map<String, BigDecimal> changeMap, String name, String currency) throws IOException {

        SVGGraphics2D graphics2D = new SVGGraphics2D(600, 400);
        Rectangle rectangle = new Rectangle(0, 0, 600, 400);
        createChart(changeMap, name, currency).draw(graphics2D, rectangle);
        File file = new File("SVGChart.svg");
        SVGUtils.writeToSVG(file, graphics2D.getSVGElement());
    }
}
