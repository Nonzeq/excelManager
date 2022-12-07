package com.kobylchak.core.exelservice.validators;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExcelFIleFromPromValidator implements Validator{

    private boolean isValid;
    @Override
    public void validate(File file, JFrame frame) {
        try(Workbook workbook = new XSSFWorkbook(new FileInputStream(file))){
            Sheet sheet = workbook.getSheetAt(0);
            validateHeaders(sheet,frame);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void validateHeaders(Sheet sheet, JFrame frame){
        Row headers = sheet.getRow(0);
        Cell price = headers.getCell(8);

        if(price == null){
            JOptionPane.showMessageDialog(
                    frame,
                    "Помилка в дев'ятому стовбці: " +
                            "Очікується:{Цена}",
                    "Помилка",JOptionPane.ERROR_MESSAGE);
        }else {
            this.isValid = true;
        }
    }

    public boolean fileIsValid() {
        return isValid;
    }
}
