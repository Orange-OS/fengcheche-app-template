package com.zsy.module.chart.generate.service.impl;

import com.zsy.module.chart.generate.domain.dto.TemperatureData;
import com.zsy.module.chart.generate.service.GenerateChartService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.LookupPaintScale;
import org.jfree.chart.renderer.xy.XYBlockRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GenerateChartServiceImpl implements GenerateChartService {

    @Override
    public Object generate() {


        return null;
    }

    public static void generateGradientHeatmap(double[][] temperatureData,
                                               String outputPath) throws IOException {

        int width = temperatureData[0].length;
        int height = temperatureData.length;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // 创建温度梯度
        GradientPaint gradient = new GradientPaint(
                0, 0, Color.BLUE,
                width, height, Color.RED
        );

        // 找到温度范围
        double minTemp = findMin(temperatureData);
        double maxTemp = findMax(temperatureData);

        // 绘制每个像素
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double normalized = (temperatureData[y][x] - minTemp) / (maxTemp - minTemp);
                Color color = getGradientColor(normalized);
                image.setRGB(x, y, color.getRGB());
            }
        }

        // 添加温度刻度
        addColorBar(g2d, width, height, minTemp, maxTemp);

        g2d.dispose();

        // 保存图像
        ImageIO.write(image, "PNG", new File(outputPath));
        System.out.println("梯度温度色斑图已保存至: " + outputPath);
    }

    private static Color getGradientColor(double normalized) {
        // HSL颜色空间插值：从蓝色(240°)到红色(0°)
        float hue = (float) (240 * (1 - normalized)) / 360f;
        return Color.getHSBColor(hue, 0.8f, 0.9f);
    }

    private static void addColorBar(Graphics2D g2d, int width, int height,
                                    double minTemp, double maxTemp) {
        int barWidth = 20;
        int barHeight = height - 40;
        int barX = width - barWidth - 10;
        int barY = 20;

        // 绘制颜色条
        for (int y = 0; y < barHeight; y++) {
            double normalized = 1 - (double) y / barHeight;
            Color color = getGradientColor(normalized);
            g2d.setColor(color);
            g2d.fillRect(barX, barY + y, barWidth, 1);
        }

        // 绘制边框和刻度
        g2d.setColor(Color.BLACK);
        g2d.drawRect(barX, barY, barWidth, barHeight);

        g2d.setFont(new Font("Arial", Font.PLAIN, 10));
        String minText = String.format("%.1f°C", minTemp);
        String maxText = String.format("%.1f°C", maxTemp);

        g2d.drawString(maxText, barX + barWidth + 5, barY + 10);
        g2d.drawString(minText, barX + barWidth + 5, barY + barHeight - 5);
    }

    private static double findMin(double[][] data) {
        double min = Double.MAX_VALUE;
        for (double[] row : data) {
            for (double value : row) {
                min = Math.min(min, value);
            }
        }
        return min;
    }

    private static double findMax(double[][] data) {
        double max = Double.MIN_VALUE;
        for (double[] row : data) {
            for (double value : row) {
                max = Math.max(max, value);
            }
        }
        return max;
    }

    public static void main(String[] args) {
        try {
            // 生成测试数据
            int width = 500;
            int height = 400;
            double[][] temperatureData = new double[height][width];

            // 创建更复杂的温度分布
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // 多个热源和冷源
                    double temp = 20; // 基础温度

                    // 热源
                    temp += 30 * Math.exp(-((x-100)*(x-100) + (y-100)*(y-100)) / 2000);
                    temp += 25 * Math.exp(-((x-350)*(x-350) + (y-250)*(y-250)) / 3000);

                    // 冷源
                    temp -= 15 * Math.exp(-((x-200)*(x-200) + (y-300)*(y-300)) / 2500);

                    temperatureData[y][x] = temp;
                }
            }

            generateGradientHeatmap(temperatureData, "gradient_temperature_map.png");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
