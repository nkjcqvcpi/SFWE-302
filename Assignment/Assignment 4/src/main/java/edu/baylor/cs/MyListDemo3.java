package edu.baylor.cs;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MyListDemo3 extends URLLoader {

    protected List<List<String>> list = new ArrayList<>();

    protected void createXLS() {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("sheet1");  // creating a blank sheet
            int rownum = 0;
            for (List<String> line : list) {
                Row row = sheet.createRow(rownum++);
                createList(line, row);
            }
            FileOutputStream out = new FileOutputStream(new File("NewFile3.xlsx"));  // file name with path
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createList(List<String> line, Row row) {
        Cell cell = row.createCell(0);
        cell.setCellValue(line.get(0));

        cell = row.createCell(1);
        cell.setCellValue(line.get(1));

        cell = row.createCell(2);
        cell.setCellValue(line.get(2));

        cell = row.createCell(3);
        cell.setCellValue(line.get(3));

        cell = row.createCell(4);
        cell.setCellValue(line.get(5));

        cell = row.createCell(5);
        cell.setCellValue(line.get(7));

        cell = row.createCell(6);
        cell.setCellValue(line.get(8));
    }

    public static void main(String[] args) {
        MyListDemo3 myListDemo = new MyListDemo3();
        myListDemo.loadData();

        System.out.println(myListDemo.list.size());
        myListDemo.createXLS();
    }

    @Override
    protected void processLine(String[] tokens) {
        if (tokens[7].equalsIgnoreCase("British Columbia")) {
            list.add(Arrays.asList(tokens));
        }
    }
}
