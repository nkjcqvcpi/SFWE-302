package edu.baylor.cs;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;


public class MySetDemo5 extends URLLoader {
    // Task 6
    protected Set<String> resultSet = new HashSet<>();
    // Task 8
    protected List<Product> list = new ArrayList<>();
    protected Set<String> all = new HashSet<>();
    protected Set<String> duplicates = new HashSet<>();
    protected Set<String> oneOccurence = null;
    // Task 10
//    protected List<Product> list = new ArrayList<>();
    protected Map<String, Integer> map = new HashMap<>();
    // Task 12
//    protected List<Product> list = new ArrayList<>();
//    protected Map<String, List<Product>> map = null;

    public static void main(String[] args) {
        MySetDemo5 myListDemo = new MySetDemo5();
        myListDemo.loadData();

        myListDemo.applySearch();

        System.out.println(myListDemo.list.size());
        myListDemo.createXLS();
        System.out.println(myListDemo.resultSet.size());

        // Task 6
//        System.out.println("Size all: " + myListDemo.list.size());
//        System.out.println("Size unique: " + myListDemo.all.size());
//        System.out.println("Size duplicates: " + myListDemo.duplicates.size());
//        System.out.println("Size one occurence: " + myListDemo.oneOccurence.size());
    }

    @Override
    protected void processLine(String[] tokens) {
        if (tokens[7].equalsIgnoreCase("British Columbia")) {
            Product product = new Product();
            product.setId(Long.parseLong(tokens[0]));
            product.setName(tokens[1]);
            product.setAgentName(tokens[2]);
            product.setAgentId(Long.parseLong(tokens[3]));
            product.setPrice(Double.parseDouble(tokens[5]));
            product.setTerritory(tokens[7]);
            product.setCategory(tokens[8]);
            list.add(product);
        }
    }

    private void applySearch() {
        // Task 8
        for (Product product : list) {
            String name = product.getName();
            if (!all.add(name)) { // Check documentation on what this means
                duplicates.add(name);
            }
        }
        oneOccurence = new HashSet<>(all);
        oneOccurence.removeAll(duplicates);
        // Task 9
        oneOccurence = new TreeSet<>(all);
        oneOccurence.removeAll(duplicates);

        List<String> sorted = new ArrayList<>(oneOccurence);
        Collections.sort(sorted);
        // Task 10
//        for (Product product : list) {
//            String name = product.getName();
//            Integer count = map.get(name);
//            if (count == null) {
//                count = 0;
//            }
//            map.put(name, ++count);
//        }
//
//        for (Map.Entry<String, Integer> entry : map.entrySet()) {
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        }
        // Task 12
//        map = list.stream().collect(Collectors.groupingBy(Product::getTerritory));
        // list.stream().sorted((o1, o2)->o1.setName().compareTo(o2.setName()));
//        map = list.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName()))
//                .collect(Collectors.groupingBy(Product::getTerritory));
        // Task 13
//        map = list.stream()
//                .sorted((o1, o2) -> o1.getTerritory().compareTo(o2.getTerritory()))
//                .collect(Collectors.groupingBy(Product::getTerritory, Collectors.summingDouble(Product::getPrice)));
    }

    protected void createXLS() {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("sheet1");  // creating a blank sheet
            int rownum = 0;
            // Task 8
            for (String result : oneOccurence) {
                Row row = sheet.createRow(rownum++);
                Cell cell = row.createCell(0);
                cell.setCellValue(result);
            }
            // Task 11
//            LinkedHashMap<String, Integer> sortedMap = sortByValue(map);
//            for (Map.Entry<String, Integer> result : sortedMap.entrySet()) {
//                Row row = sheet.createRow(rownum++);
//                Cell cell = row.createCell(0);
//                cell.setCellValue(result.getKey());
//                cell = row.createCell(1);
//                cell.setCellValue(result.getValue());
//            }

            // Task 12
//            for (Map.Entry<String, List<Product>> result : map.entrySet()) {
//                Row row = sheet.createRow(rownum++);
//                Cell cell = row.createCell(0);
//                cell.setCellValue(result.getKey());
//                boolean skipLine = true;
//                for (Product product : result.getValue()) {
//                    if (skipLine) {
//                        skipLine = false;
//                    } else {
//                        row = sheet.createRow(rownum++);
//                    }
//                    cell = row.createCell(1);
//                    cell.setCellValue(product.getName());
//                    cell = row.createCell(2);
//                    cell.setCellValue(product.getTerritory());
//                }
//            }
            // Task 13
//            for (Map.Entry<String, Double> result : map.entrySet()) {
//                Row row = sheet.createRow(rownum++);
//                Cell cell = row.createCell(0);
//                cell.setCellValue(result.getKey());
//                cell = row.createCell(1);
//                cell.setCellValue(result.getValue());
//            }
            FileOutputStream out = new FileOutputStream(new File("NewFile3.xlsx"));  // file name with path
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createList(Product product, Row row) {
        Cell cell = row.createCell(0);
        cell.setCellValue(product.getId());
        cell = row.createCell(1);
        cell.setCellValue(product.getName());
        cell = row.createCell(2);
        cell.setCellValue(product.getAgentName());
        cell = row.createCell(3);
        cell.setCellValue(product.getAgentId());
        cell = row.createCell(4);
        cell.setCellValue(product.getTerritory());
        cell = row.createCell(5);
        cell.setCellValue(product.getCategory());
    }

    // Task 11
    public static LinkedHashMap<String, Integer> sortByValue1(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static LinkedHashMap<String, Integer> sortByValue(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if (o2.getValue().equals(o1.getValue())) {
                    return o1.getKey().compareTo(o2.getKey());
                } else {
                    return o2.getValue().compareTo(o1.getValue());
                }
            }
        });
        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    // Task 12
//    public static <K extends Comparable<? super K>, V extends Comparable<? super V>> LinkedHashMap<K, V> sortByValue(Map<K, V> map) {
//        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
////        list.sort(Entry.comparingByValue());
//        list.sort(new Comparator<Map.Entry<K, V>>() {
//            @Override
//            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
//                if (o2.getValue().equals(o1.getValue())) {
//                    return o1.getKey().compareTo(o2.getKey());
//                } else {
//                    return o2.getValue().compareTo(o1.getValue());
//                }
//            }
//        });
//        LinkedHashMap<K, V> result = new LinkedHashMap<>();
//        for (Map.Entry<K, V> entry : list) {
//            result.put(entry.getKey(), entry.getValue());
//        }
//        return result;
//    }
}
