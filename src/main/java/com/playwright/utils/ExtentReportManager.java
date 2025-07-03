package com.playwright.utils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static ExtentTest test;

    public static void initReport() {
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/PlaywrightReport.html");
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
