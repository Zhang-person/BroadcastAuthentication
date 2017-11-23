/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import javax.swing.*;
/**
 *
 * @author Bobby
 */
public class SimulationResult extends JFrame {
 public SimulationResult(final String title) {

        super(title);

        final CategoryDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);

    }

    /**
     * Returns a sample dataset.
     * 
     * @return The dataset.
     */
    private CategoryDataset createDataset() {
        
        // row keys...
        final String series1 = "Sign";
        final String series2 = "Verify";
        

        // column keys...
        final String category1 = "HMAC";
        final String category2 = "RSA";
        final String category3 = "ECDSA";
       final String category4 = "Enhancement";

        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(SimulationStatitics.getHMACSignMilliSeconds() , series1, category1);
        dataset.addValue(SimulationStatitics.getRSASignMilliSeconds(), series1, category2);
        
        if(SimulationStatitics.getEnhancedSignMilliSeconds()<SimulationStatitics.getECDSASignMilliSeconds())
        {
            dataset.addValue(SimulationStatitics.getECDSASignMilliSeconds(), series1, category3);
            dataset.addValue(SimulationStatitics.getEnhancedSignMilliSeconds(), series1, category4);
        }
        else
        {
            
            dataset.addValue(SimulationStatitics.getEnhancedSignMilliSeconds(), series1, category3);
            dataset.addValue(SimulationStatitics.getECDSASignMilliSeconds(), series1, category4);
        }

            dataset.addValue(SimulationStatitics.getHMACVerifyMilliSeconds(), series2, category1);
            dataset.addValue(SimulationStatitics.getRSAVerifyMilliSeconds(), series2, category2);
        
        if(SimulationStatitics.getEnhancedVerifyMilliSeconds()<SimulationStatitics.getECDSAVerifyMilliSeconds())
        {
            dataset.addValue(SimulationStatitics.getECDSAVerifyMilliSeconds(), series2, category3);
            dataset.addValue(SimulationStatitics.getEnhancedVerifyMilliSeconds(), series2, category4);
        }
        else
        {
            
            dataset.addValue(SimulationStatitics.getEnhancedVerifyMilliSeconds(), series2, category3);
            dataset.addValue(SimulationStatitics.getECDSAVerifyMilliSeconds(), series2, category4);
        }

       
        
        return dataset;
        
    }
    
    /**
     * Creates a sample chart.
     * 
     * @param dataset  the dataset.
     * 
     * @return The chart.
     */
    private JFreeChart createChart(final CategoryDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
            "Time Consumption for Multicast Authentication",         // chart title
            "Signature",               // domain axis label
            "Milliseconds",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        
        // set up gradient paints for series...
        final GradientPaint gp0 = new GradientPaint(
            0.0f, 0.0f, Color.blue, 
            0.0f, 0.0f, Color.lightGray
        );
        final GradientPaint gp1 = new GradientPaint(
            0.0f, 0.0f, Color.green, 
            0.0f, 0.0f, Color.lightGray
        );
        final GradientPaint gp2 = new GradientPaint(
            0.0f, 0.0f, Color.red, 
            0.0f, 0.0f, Color.lightGray
        );
        renderer.setSeriesPaint(0, gp0);
        renderer.setSeriesPaint(1, gp1);
        renderer.setSeriesPaint(2, gp2);

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
        // OPTIONAL CUSTOMISATION COMPLETED.
        
        return chart;
        
    }
    
    
    public static void main(final String[] args) {

        final SimulationResult demo = new SimulationResult("Simulation Result");
        demo.pack();
       RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }   
}
