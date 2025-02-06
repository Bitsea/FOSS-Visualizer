package com.fossvisualizer.application;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelReader {

    public static Map<Integer, List<String>> getDataFromExcel(File filename) throws IOException {
        InputStream file = new FileInputStream(filename);
        Workbook workbook = new XSSFWorkbook(file);
        Map<Integer, List<String>> data = new HashMap<>();
        Sheet sheet = workbook.getSheetAt(0);
        int i = 0;
        for (Row row : sheet) {
            if (row.getRowNum()!=0) { //skip header
                data.put(i, new ArrayList<>());
                int lastColumn = row.getLastCellNum();
                for (int cellNumber = 0; cellNumber < lastColumn; cellNumber++) {
                    Cell c = row.getCell(cellNumber);
                    if (c == null) {
                        // empty cell
                        data.get(i).add(" ");
                    } else {
                        data.get(i).add(c.toString());
                    }
                }
                i++;
            }
        }
        // filter out all completely empty rows
        data.values().removeIf(list -> list.stream().allMatch(s -> s.trim().isEmpty()));
        // remove the hidden colum from the sheet
        data.forEach((key, list) -> list.remove(2));
        file.close();
        return data;
    }
}

