package sfwe302.assignment2;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTable;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumn;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumns;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableStyleInfo;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;


public class Main {
    public static void main(String[] args) throws Exception {

        String outputType = args[0];
        String fileName = args[1];

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            List<List<String>> rows = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                List<String> row1 = new ArrayList<>(List.of(line.split(",")));
                rows.add(row1);
            }
            switch(outputType) {
                case "PDF":
                    CreatePDF(rows, fileName);
                    break;
                case "XLS":
                    CreateExcel(rows, fileName);
                    break;
                default:
                    System.out.println("Invalid output type. Please specify 'PDF' or 'XLS'.");
            }
        }
    }

    public static void CreatePDF(List<List<String>> rows, String filename) throws Exception {
        String file = filename.replace(".csv", ".pdf");

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));

        Document doc = new Document(pdfDoc);

        Table table = new Table(rows.getFirst().size());
        for (List<String> row : rows) {
            for (String cell : row) {
                table.addCell(new Cell().add(new Paragraph(cell)));
            }
        }
        doc.add(table);
        doc.close();
    }

    public static void CreateExcel(List<List<String>> rows, String filename) throws IOException {
        filename = filename.replace(".csv", ".xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(filename.split("\\.")[0]);

        XSSFTable table = sheet.createTable(null);
        CTTableColumns columns = getCtTableColumns(rows, table);
        for (int i = 0; i < rows.getFirst().size(); i++) {
            CTTableColumn column = columns.addNewTableColumn();
            column.setId(i + 1);
            column.setName("Column" + (i + 1));
        }

        for (int r = 0; r < rows.size(); r++) {
            XSSFRow row = sheet.createRow(r);
            List<String> rowData = rows.get(r);
            for (int c = 0; c < rowData.size(); c++) {
                XSSFCell cell = row.createCell(c);
                cell.setCellValue(rowData.get(c));
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream(filename)) {
            workbook.write(outputStream);
        }
        workbook.close();
    }

    private static CTTableColumns getCtTableColumns(List<List<String>> rows, XSSFTable table) {
        CTTable cttable = table.getCTTable();

        cttable.setDisplayName("Table1");
        cttable.setId(1);
        cttable.setName("Test");
        cttable.setRef("A1:" + (char)('A' + rows.getFirst().size() - 1) + rows.size());
        cttable.setTotalsRowShown(false);

        CTTableStyleInfo styleInfo = cttable.addNewTableStyleInfo();
        styleInfo.setName("TableStyleMedium2");
        styleInfo.setShowColumnStripes(false);
        styleInfo.setShowRowStripes(true);

        CTTableColumns columns = cttable.addNewTableColumns();
        columns.setCount(rows.getFirst().size());
        return columns;
    }
}
