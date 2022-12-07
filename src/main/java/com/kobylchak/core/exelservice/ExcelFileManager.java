package com.kobylchak.core.exelservice;

import com.kobylchak.core.exelservice.reporters.ExcelReport;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;


public class ExcelFileManager {

    private List<ExcelWorker> excelWorkers;
    private FileInputStream dataFile;
    private String pathToStockFile;
    private Workbook workbook;

    private ReportManager reportManager;


    public ExcelFileManager(){
        this.reportManager = new ReportManager();
    }


    public ExcelFileManager(List<ExcelWorker> excelWorkers, FileInputStream dataFile) throws IOException {
        this.excelWorkers = excelWorkers;
        this.dataFile = dataFile;

    }



    public List<ExcelWorker> getExcelWorkers() {
        return excelWorkers;
    }

    public void setExcelWorker(List<ExcelWorker> excelWorkers) {
        this.excelWorkers = excelWorkers;
    }

    public FileInputStream getDataFile() {
        return dataFile;
    }

    public void setDataFile(FileInputStream dataFile) {
        this.dataFile = dataFile;
        try {
            this.workbook = new XSSFWorkbook(this.dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPathToStockFile() {
        return pathToStockFile;
    }

    public void setPathToStockFile(String pathToStockFile) {
        this.pathToStockFile = pathToStockFile;
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public void runService() throws FileNotFoundException {
        for(ExcelWorker worker:this.excelWorkers){
            worker.work(this.pathToStockFile, workbook, reportManager);
        }
    }
    public void saveFile(String path){
        try {
            FileOutputStream outputStream = new FileOutputStream(path);
            workbook.write(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public List<ExcelReport> getExcelReports(){
        return this.reportManager.getExcelReports();
    }
}
