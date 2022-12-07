package com.kobylchak.core.exelservice;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelAmountWorker implements ExcelWorker {
    @Override
    public void work(String pathToStockFile, Workbook workbook, ReportManager reportManager) throws FileNotFoundException {

        Sheet sheet = workbook.getSheetAt(0);
        Map<String,String> mapWithStockFileData;
        mapWithStockFileData = this.getHashMapOfExcel(pathToStockFile);
        for(int i=1; i<sheet.getLastRowNum();i++){
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(0);
            if(cell != null){
                if(mapWithStockFileData.get(row.getCell(0).toString().trim())!=null){
                    String amount = mapWithStockFileData.get(row.getCell(0).toString());
                    double amountLong = Double.parseDouble(amount);
                    if(amountLong > 0){
                        row.getCell(15).setCellValue("+");
                    }else{
                        row.getCell(15).setCellValue("-");
                    }
                }
            }
        }

    }

    private Map<String, String> getHashMapOfExcel(String path) throws FileNotFoundException {

        Map<String, String> map = new HashMap<>();
        try (FileInputStream inputStream = new FileInputStream(path)) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getCell(0) != null) {
                    map.put(row.getCell(0).toString().trim(), row.getCell(3).toString().trim());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return map;
    }
}
