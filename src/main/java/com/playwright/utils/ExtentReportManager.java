package com.playwright.utils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static ExtentTest test;

    public static void initReport() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/Result-" + timestamp + "-report.html");
        spark.config().setReportName("Playwright Automation Report");
        spark.config().setDocumentTitle("Test Results");

        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    public static ExtentReports getExtent() {
        return extent;
    }

    public static ExtentTest createTest(String name) {
        test = extent.createTest(name);
        return test;
    }

    public static void flushReport() {
        extent.flush();
    }

    public static ExtentTest getTest() {
        return test;
    }
}
