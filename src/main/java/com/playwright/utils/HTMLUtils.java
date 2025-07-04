package com.playwright.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLUtils {

    public static String extractWatermark(String htmlContent) {
        if (htmlContent == null || htmlContent.isEmpty()) {
            return "";
        }
        Document doc = Jsoup.parse(htmlContent);

        for (Element td : doc.select("td")) {
            if (td.text().toLowerCase().contains("watermark:")) {
                Matcher matcher = Pattern.compile(
                        "\\b[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-\\b[0-9a-fA-F]{12}\\b"
                ).matcher(td.text());
                if (matcher.find()) {
                    return matcher.group();
                }
            }
        }
        return "";
    }
}