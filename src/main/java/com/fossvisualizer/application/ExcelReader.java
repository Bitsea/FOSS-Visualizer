/*
 * FOSS Visualizer
 * Copyright (C) 2025 Bitsea GmbH
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

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

