package com.playwright.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelReader {

    public static Map<String, String> getMailBoxDetails(String instanceName) {
        List<Map<String, String>> matchedRows = new ArrayList<>();

        String filePath = "src/test/resources/InstanceDetails.xlsx";
        String sheetName = "InstanceDetails";

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) throw new IllegalArgumentException("Sheet not found: " + sheetName);

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) throw new IllegalStateException("No header row found.");

            // Map of column indexes to column names
            Map<Integer, String> columnIndexToName = new LinkedHashMap<>();
            for (Cell cell : headerRow) {
                columnIndexToName.put(cell.getColumnIndex(), cell.getStringCellValue().trim());
            }

            int instanceColumnIndex = -1;
            for (Map.Entry<Integer, String> entry : columnIndexToName.entrySet()) {
                if (entry.getValue().equalsIgnoreCase("Instance Name")) {
                    instanceColumnIndex = entry.getKey();
                    break;
                }
            }

            if (instanceColumnIndex == -1)
                throw new IllegalStateException("Column 'instance name' not found.");

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell instanceCell = row.getCell(instanceColumnIndex);
                if (instanceCell != null && instanceName.equalsIgnoreCase(getCellValue(instanceCell))) {
                    Map<String, String> rowData = new LinkedHashMap<>();
                    for (Map.Entry<Integer, String> col : columnIndexToName.entrySet()) {
                        Cell cell = row.getCell(col.getKey());
                        rowData.put(col.getValue(), getCellValue(cell));
                    }
                    matchedRows.add(rowData);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file: " + filePath + " - " + e.getMessage(), e);
        }

        return matchedRows.get(0);
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "";
        };
    }
}
