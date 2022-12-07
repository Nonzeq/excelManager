package com.kobylchak.core.exelservice;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public interface ExcelWorker {

    static String getPromFilePath(){
        return Objects.requireNonNull(ExcelWorker.class.getClassLoader()
                .getResource("xml/prom.xlsx")).toString();
    }

    static String  getStockFilePath(){

        return Objects.requireNonNull(ExcelWorker.class.getClassLoader()
                .getResource("xml/stock.xlsx")).toString();
    }
    void work(String pathToStockFile, Workbook workbook, ReportManager reportManager) throws FileNotFoundException;


}
