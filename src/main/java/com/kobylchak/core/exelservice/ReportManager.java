package com.kobylchak.core.exelservice;

import com.kobylchak.core.exelservice.reporters.ExcelReport;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.List;

public class ReportManager {

    private List<ExcelReport> excelReports;

    public ReportManager(){
        this.excelReports = new ArrayList<ExcelReport>();
    }

    public List<ExcelReport> getExcelReports() {
        return excelReports;
    }

    public void addExcelReport(ExcelReport excelReport) {
        this.excelReports.add(excelReport);
    }

    public void saveReport(String path){
        Workbook workbook = new XSSFWorkbook();
    }
}
