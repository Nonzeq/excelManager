package com.kobylchak.core.exelservice;

import com.kobylchak.core.exelservice.reporters.ExcelReport;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ExcelPriceWorker  implements ExcelWorker{
    int priceRow = 8;




    @Override
    public void work(
            String pathToStockFile, Workbook workbook, ReportManager reportManager) throws FileNotFoundException {
            Sheet sheet = workbook.getSheetAt(0);
            Map<String,String> mapWithStockFileData;
            mapWithStockFileData = this.getHashMapOfExcel(pathToStockFile);
            for(int i=1; i<sheet.getLastRowNum();i++){
                Row row = sheet.getRow(i);
                Cell cell = row.getCell(0);
                if(cell != null){
                    if(mapWithStockFileData.get(row.getCell(0).toString().trim()) != null){
                        String newPrice = this.getNormalPriceWithComma(mapWithStockFileData
                                .get(row.getCell(0).toString()));
                        double numFromStock = Double.parseDouble(newPrice.replace(",", "."));
                        double numFromProm = Double.parseDouble(row
                                .getCell(priceRow).toString()
                                .replace(",","."));
                        if(numFromProm != numFromStock){
                            reportManager.addExcelReport(new ExcelReport(
                                    row.getCell(0).toString(),
                                    row.getCell(2).toString(),
                                    row.getCell(priceRow).toString(),
                                    newPrice
                            ));
                        }
                        row.getCell(priceRow).setCellValue(newPrice);
                    }
                }
            }


    }


    private Map<String, String> getHashMapOfExcel(String path) throws FileNotFoundException {

        Map<String, String> map = new HashMap<>();
        try(FileInputStream  inputStream = new FileInputStream(path)){
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            for(Row row: sheet){
                if(row.getCell(0)!= null){
                    map.put(row.getCell(0).toString().trim(), row.getCell(2).toString().trim());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    private String getNormalPriceWithComma(String str){
       if(str.charAt(str.indexOf(',')+1) == '0'){
            return str.substring(0, str.indexOf(','));
        }else{
            return str.substring(0, str.indexOf(',')+2);
        }

    }


}
