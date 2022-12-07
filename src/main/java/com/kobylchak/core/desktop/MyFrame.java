package com.kobylchak.core.desktop;

import com.kobylchak.core.desktop.validators.ChooseFileValidator;
import com.kobylchak.core.exelservice.ExcelAmountWorker;
import com.kobylchak.core.exelservice.ExcelFileManager;
import com.kobylchak.core.exelservice.ExcelPriceWorker;
import com.kobylchak.core.exelservice.ExcelWorker;
import com.kobylchak.core.exelservice.reporters.ExcelReport;
import com.kobylchak.core.exelservice.validators.ExcelFIleFromPromValidator;
import com.kobylchak.core.exelservice.validators.ExcelFileFromStockValidator;
import com.kobylchak.core.exelservice.validators.Validator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyFrame extends JFrame implements ActionListener {

    private ExcelFileManager excelFileManager;
    private final JButton promButton;
    private final JButton stockButton;
    private JButton workButton;
    private JButton reportButton;
    private JButton cleanButton;
    private JButton showExempleImgButton;


    JLabel jLabelProm;
    JLabel jLabelStock;
    String pathToSave;
    private JCheckBox priceCheckBox;
    private JCheckBox amountCheckBox;

    public final static int WIDTH = 800;
    public final static int HEIGTH = 700;

    private final JCheckBox[] allCheckBoxes = new JCheckBox[2];


    public MyFrame(){
        this.excelFileManager = new ExcelFileManager();
        //this.setIconImage(new ImageIcon("src/main/resources/icon/Logo_Site.jpg").getImage());
        this.setTitle("Ваша Грядка");
        this.getContentPane().setBackground(Color.WHITE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH,HEIGTH);
        this.setLayout(null);
        JLabel icon = new JLabel(
                new ImageIcon(Objects.requireNonNull(ClassLoader
                        .getSystemClassLoader()
                        .getResource("icon/Logo_Site.jpg"))));

        icon.setBounds(WIDTH/2-150,450,300,300);
        add(icon);
        this.setLocationRelativeTo(null);


        this.promButton = new JButton("Файл з прому");
        this.stockButton = new JButton("Файл з бази данних");
        this.workButton = new JButton("Обробити");
        this.reportButton = new JButton("Отримати звіт");
        this.cleanButton = new JButton("Очистити все");
        this.showExempleImgButton = new JButton("Приклад");





        this.priceCheckBox = new JCheckBox("Оновити ціни");
        this.amountCheckBox = new JCheckBox("Оновити кількість");



        this.jLabelProm= new JLabel();
        this.jLabelStock = new JLabel();



        //btns area
        promButton.setBounds(10,20,200,40);
        stockButton.setBounds(10,70,200,40);
        workButton.setBounds(WIDTH/2-75,HEIGTH/2-20, 150,40);
        reportButton.setBounds(10,250,200, 40);
        cleanButton.setBounds(WIDTH-210,HEIGTH-80,200, 40);
        showExempleImgButton.setBounds(10, HEIGTH-80, 200,40);

        this.reportButton.setVisible(false);


        //label area

        this.jLabelProm.setBounds(220,20,500,40);
        this.jLabelStock.setBounds(220,70,500,40);

        //checkBoxArea

        this.priceCheckBox.setBounds(10,130,250,40);
        this.priceCheckBox.setBackground(Color.WHITE);
        this.amountCheckBox.setBounds(10,170,250,40);
        this.amountCheckBox.setBackground(Color.WHITE);


        promButton.addActionListener(this);
        stockButton.addActionListener(this);
        workButton.addActionListener(this);
        reportButton.addActionListener(this);
        cleanButton.addActionListener(this);
        showExempleImgButton.addActionListener(this);
        this.add(showExempleImgButton);
        this.add(stockButton);
        this.add(jLabelProm);
        this.add(jLabelStock);
        this.add(promButton);
        this.add(workButton);
        this.add(reportButton);
        this.add(cleanButton);
        this.add(priceCheckBox);
        this.add(amountCheckBox);
        this.allCheckBoxes[0] = this.priceCheckBox;
        this.allCheckBoxes[1] = this.amountCheckBox;

        this.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == this.promButton){
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showOpenDialog(null);

            if(jFileChooser.getSelectedFile() != null){
                String path = jFileChooser.getSelectedFile().getAbsolutePath();
                this.jLabelProm.setText(path);
                try {
                    this.excelFileManager.setDataFile(new FileInputStream(this.jLabelProm.getText()));
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }


        }else if(e.getSource() == this.stockButton){
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showOpenDialog(null);
            if(jFileChooser.getSelectedFile() != null){
                String path = jFileChooser.getSelectedFile().getAbsolutePath();
                this.jLabelStock.setText(path);
                this.excelFileManager.setPathToStockFile(this.jLabelStock.getText());
            }
        }else if(e.getSource() == this.workButton &&
                ChooseFileValidator.validate(new JLabel[]{this.jLabelProm,this.jLabelStock}, this)){
            Validator stockValidator = new ExcelFileFromStockValidator();
            Validator promValidator = new ExcelFIleFromPromValidator();
            stockValidator.validate(new File(this.jLabelStock.getText()), this);
            promValidator.validate(new File(this.jLabelProm.getText()), this);

            if(stockValidator.fileIsValid() && promValidator.fileIsValid()){
                this.clickWorkButton();
            }




        } else if (e.getSource() == this.reportButton) {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File("report.xlsx"));
            int option = fileChooser.showSaveDialog(this);

            if(option == JFileChooser.APPROVE_OPTION){
                this.makeExcelReport(fileChooser.getSelectedFile().getAbsolutePath());
            }
        } else if (e.getSource() == this.cleanButton) {
            this.jLabelProm.setText(null);
            this.jLabelStock.setText(null);
            this.priceCheckBox.setSelected(false);
            this.amountCheckBox.setSelected(false);
            this.excelFileManager = new ExcelFileManager();
            this.cleanButton.setVisible(false);
        } else if (e.getSource() == this.showExempleImgButton) {
            ImageFrame frame = new ImageFrame();

        }
    }

    private void runExelService(List<ExcelWorker> workers){
        try {
            this.excelFileManager.runService();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void makeExcelReport(String path) {
        List<ExcelReport> excelReports = this.excelFileManager.getExcelReports();


        try (Workbook workbook = new XSSFWorkbook()){

            Sheet sheet = workbook.createSheet("Sheet1");
            Row rowHeaders = sheet.createRow(0);
            rowHeaders.createCell(0).setCellValue("код");
            rowHeaders.createCell(1).setCellValue("назва_товару");
            rowHeaders.createCell(2).setCellValue("стара_ціна");
            rowHeaders.createCell(3).setCellValue("нова_ціна");
            int rowCount = 1;
            for(ExcelReport excelReport: excelReports){
                Row row = sheet.createRow(rowCount);
                row.createCell(0).setCellValue(excelReport.getSku());
                row.createCell(1).setCellValue(excelReport.getProductName());
                row.createCell(2).setCellValue(excelReport.getOldPrice());
                row.createCell(3).setCellValue(excelReport.getNewPrice());
                rowCount++;
            }

            FileOutputStream outputStream = new FileOutputStream(path);
            workbook.write(outputStream);

        } catch (IOException e) {
            e.printStackTrace();

        }

    }
        private List<JCheckBox> getSelectedCheckBoxes(){
            List<JCheckBox> checkBoxes = new ArrayList<>();
            for (JCheckBox box : this.allCheckBoxes) {
                if (box.isSelected()) {
                    checkBoxes.add(box);
                }
            }
            return checkBoxes;
        }

        private void clickWorkButton(){
            List<JCheckBox> checkBoxes = this.getSelectedCheckBoxes();
            List<ExcelWorker> excelWorkers = new ArrayList<>();

            for(JCheckBox box:checkBoxes){
                if(box.equals(this.priceCheckBox)){
                    excelWorkers.add(new ExcelPriceWorker());
                }else if(box.equals(this.amountCheckBox)){
                    excelWorkers.add(new ExcelAmountWorker());
                }
            }
            if(!excelWorkers.isEmpty()){
                this.excelFileManager.setExcelWorker(excelWorkers);
                this.runExelService(excelWorkers);
                JOptionPane.showMessageDialog(this,"Готово!");
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setSelectedFile(new File("data.xlsx"));
                int option = fileChooser.showSaveDialog(this);

                if(option == JFileChooser.APPROVE_OPTION){
                    this.excelFileManager.saveFile(fileChooser.getSelectedFile().getAbsolutePath());
                }

                if(!this.excelFileManager.getExcelWorkers().isEmpty()){
                    this.reportButton.setVisible(true);
                }
            }else {
                JOptionPane.showMessageDialog(this,"Потрібно вибрати мінімум одну опцію",
                        "Помилка",JOptionPane.ERROR_MESSAGE);
            }
        }


}
