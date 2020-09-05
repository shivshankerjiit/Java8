package com.java8.poiOld;

import com.google.common.collect.ImmutableMap;
import org.apache.poi.POIXMLException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestExcel {
    enum POSITION_TYPE {HA,VA_LEFT,VA_RIGHT}

    public static void main(String[] args)  {
        final String FILE_NAME = "crosstable_c254.xlsx";
        TestExcel example = new TestExcel();
        example.readExcelFile(FILE_NAME);
    }

    void readExcelFile(String FILE_NAME) {
        try {
            Sheet sheet;
            Workbook workbook;
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FILE_NAME);
            //InputStream inputStream = new FileInputStream(new File(FILE_NAME));
            String fileName = FILE_NAME;
            if (fileName.toLowerCase().endsWith("xls")) {
                workbook = new HSSFWorkbook(inputStream);
            } else {
                workbook = new XSSFWorkbook(inputStream);
            }
            Integer numberOfSheets = workbook.getNumberOfSheets();
            if (numberOfSheets > 1) {
                Map<Integer, String> sheets = new HashMap<>();
                for (Integer i = 0; i < numberOfSheets; i++) {
                    sheets.put(i, workbook.getSheetAt(i).getSheetName());
                }
            } else {
                sheet = workbook.getSheetAt(0);
                Cell cell = sheet.getRow(0).getCell(0);
                System.out.println("......"+cell.getStringCellValue());
                prepareOrder2(sheet);
                //processCrossTable(sheet);
            }
        } catch (POIXMLException poie) {
            System.out.println("POIXMLException: something wrong by ready XML file: " + poie.getMessage());
        } catch (Exception e) {
            System.out.println("++++ Something goes wrong +++++ " + e.getMessage());
        }
    }

    private void processCrossTable2(Sheet sheet) {
        int startOfVehicleColumn = 12;
        int totalRows = sheet.getPhysicalNumberOfRows();
        int totalColumns = sheet.getRow(0).getLastCellNum();
        DataFormatter formatter = new DataFormatter();
        Row row0 = sheet.getRow(0);
        Cell cell = row0.getCell(startOfVehicleColumn);
        String fahrzeugNr = formatter.formatCellValue(cell);
        System.out.println("fahrzeugNr->" + fahrzeugNr);


        List<String> vehicleList =
                IntStream.range(startOfVehicleColumn, totalColumns)
                        .boxed()
                        .map(col -> formatter.formatCellValue(row0.getCell(col)))
                        .collect(Collectors.toList());
        Predicate<CrossTableVehicleRow> crossTableVehicleRowFilter = crossTableRow -> (
                crossTableRow.getAmount() > 0
                        && (crossTableRow.getModul().startsWith("80") || crossTableRow.getModul().startsWith("90"))
                        && (crossTableRow.getSt().equals("02") || crossTableRow.getSt().equals("03")));

        List<CrossTableVehicleRow> rowDataList = IntStream.range(1, totalRows)
                .boxed()
                .map(rowNum -> {
                    Row row = sheet.getRow(rowNum);
                    CrossTableVehicleRow rowData = new CrossTableVehicleRow();
                    rowData.setModul(formatter.formatCellValue(row.getCell(1)));
                    rowData.setSt(formatter.formatCellValue(row.getCell(4)));
                    rowData.setSachnummer(formatter.formatCellValue(row.getCell(5)));
                    rowData.setBenennung(formatter.formatCellValue(row.getCell(6)));
                    rowData.setRowNum(rowNum);

                    return vehicleList.stream()
                            .map(vehicleNo -> {
                                CrossTableVehicleRow vehicleRow = (CrossTableVehicleRow) rowData.clone();
                                vehicleRow.setVehicleNo(vehicleNo);
                                vehicleRow.setAmount(row.getCell(6).getNumericCellValue());
                                return vehicleRow;
                            })
                            .collect(Collectors.toList());
                })
                .flatMap(List::stream)
                .filter(crossTableVehicleRowFilter)
                .collect(Collectors.toList());

        rowDataList.stream()
                .map(rowData -> {
                    CrossTableVehicle crossTableVehicle = new CrossTableVehicle();
                    crossTableVehicle.setPositionType(findPartType(rowData.getSachnummer()));
                    crossTableVehicle.setAmount(rowData.getAmount());
                    crossTableVehicle.setSnr(rowData.getSachnummer());
                    crossTableVehicle.setVehicleNo(rowData.getVehicleNo());
                    crossTableVehicle.setModule(rowData.getModul());
                    return crossTableVehicle;
                })
                .collect(Collectors.groupingBy(CrossTableVehicle::getVehicleNo,
                        Collectors.groupingBy(CrossTableVehicle::getModule,
                                Collectors.groupingBy(CrossTableVehicle::getSnr,
                                    Collectors.summingDouble(CrossTableVehicle::getAmount)))));




    }

    /*private void processCrossTable2(Sheet sheet) {
        int startOfVehicleColumn = 12;
        int totalRows = sheet.getPhysicalNumberOfRows();
        int totalColumns = sheet.getRow(0).getLastCellNum();
        DataFormatter formatter = new DataFormatter();
        Row row0 = sheet.getRow(0);
        Cell cell = row0.getCell(startOfVehicleColumn);
        String fahrzeugNr = formatter.formatCellValue(cell);
        System.out.println("fahrzeugNr->"+fahrzeugNr);


        List<String> vehicleList =
                IntStream.range(startOfVehicleColumn, totalColumns)
                        .boxed()
                        .map(col -> formatter.formatCellValue(row0.getCell(col)))
                        .collect(Collectors.toList());

        List<ImmutableMap<String, String>> vehicleMap = IntStream.range(startOfVehicleColumn, totalColumns)
                .boxed()
                .map(col -> ImmutableMap.of(vehicleList.get(col), formatter.formatCellValue(sheet.getRow(1).getCell(col))))
                .collect(Collectors.toList());



        Predicate<CrossTableRow> crossTableRowFilter = crossTableRow -> (
                Integer.valueOf(crossTableRow.getAmount()) > 0
                        && (crossTableRow.getSt().equals("02") || crossTableRow.getSt().equals("02")));





        Map<String, Map<String, Integer>> moduleMap
                = IntStream.range(1, totalRows)
                .boxed()
                .map(rowNum -> {
                    String vehicleNo = "2540002";
                    Row row = sheet.getRow(rowNum);
                    CrossTableRow rowData = new CrossTableRow();
                    rowData.setModul(formatter.formatCellValue(row.getCell(1)));
                    rowData.setSt(formatter.formatCellValue(row.getCell(4)));
                    rowData.setSachnummer(formatter.formatCellValue(row.getCell(5)));
                    rowData.setBenennung(formatter.formatCellValue(row.getCell(6)));
                    rowData.setAmount(formatter.formatCellValue(row.getCell(12)));
                    rowData.setVehicleNo(vehicleNo);
                    rowData.setRowNum(rowNum);
                    List<ImmutableMap<String, String>> vehicleMap = IntStream.range(startOfVehicleColumn, totalColumns)
                            .boxed()
                            .map(col -> ImmutableMap.of(vehicleList.get(col), formatter.formatCellValue(row.getCell(col))))
                            .collect(Collectors.toList());
                    //rowData.setVehicleMap(vehicleMap);

                    return rowData;
                })
                .peek(System.out::println)
                .filter(rowData -> (Integer.valueOf(rowData.getAmount()) > 0
                        && (rowData.getSt().equals("02") || rowData.getSt().equals("02"))))
                .peek(System.out::println)
                .collect(Collectors.groupingBy(CrossTableRow::getModul,
                        Collectors.groupingBy(CrossTableRow::getSachnummer,
                                //Collectors.mapping(CrossTableRow::getRowNum, Collectors.toList()))));
                                Collectors.summingInt(CrossTableRow::getRowNum)
                                //     Collectors.mapping(Collectors.summingInt(CrossTableRow::getRowNum),Collectors.toMap(c->"amount",Function.identity()))
                                //Collectors.mapping(CrossTableRow::getSt,Collectors.summingInt(CrossTableRow::getRowNum))
                        )));


    }*/





   /* private void processCrossTable(Sheet sheet) {
        Map<String, List<String>> orderData = loadData();
        int startOfVehicleColumn = 12;
        int totalRows = sheet.getPhysicalNumberOfRows();
        int totalColumns = sheet.getRow(0).getLastCellNum();
        DataFormatter formatter = new DataFormatter();
        Row row0 = sheet.getRow(0);
        Cell cell = row0.getCell(startOfVehicleColumn);
        String fahrzeugNr = formatter.formatCellValue(cell);
        System.out.println("fahrzeugNr->"+fahrzeugNr);


        //Map<String, Map<String, List<Integer>>> moduleMap
        Map<String, Map<String, Integer>> moduleMap
                = IntStream.range(1, totalRows)
                .boxed()
                .map(rowNum -> {
                    String vehicleNo = "2540002";
                    Row row = sheet.getRow(rowNum);
                    CrossTableRow rowData = new CrossTableRow();
                    rowData.setModul(formatter.formatCellValue(row.getCell(1)));
                    rowData.setSt(formatter.formatCellValue(row.getCell(4)));
                    rowData.setSachnummer(formatter.formatCellValue(row.getCell(5)));
                    rowData.setBenennung(formatter.formatCellValue(row.getCell(6)));
                    rowData.setAmount(formatter.formatCellValue(row.getCell(12)));
                    rowData.setVehicleNo(vehicleNo);
                    rowData.setRowNum(rowNum);

                    return rowData;
                })
                .filter(rowData -> (Integer.valueOf(rowData.getAmount()) > 0
                        && (rowData.getSt().equals("02") || rowData.getSt().equals("02"))))
                .collect(Collectors.groupingBy(CrossTableRow::getModul,
                                Collectors.groupingBy(CrossTableRow::getSachnummer,
                                        //Collectors.mapping(CrossTableRow::getRowNum, Collectors.toList()))));
                                        Collectors.summingInt(CrossTableRow::getRowNum)
                                       //     Collectors.mapping(Collectors.summingInt(CrossTableRow::getRowNum),Collectors.toMap(c->"amount",Function.identity()))
                                        //Collectors.mapping(CrossTableRow::getSt,Collectors.summingInt(CrossTableRow::getRowNum))
                                        )));

        System.out.println("----------------------processCrossTable----------------->");
        //System.out.println("moduleMap->"+moduleMap);

    }

    void prepareOrder(Sheet sheet){
        Map<String, List<String>> orderData = loadData();
        int startOfVehicleColumn = 12;
        int totalColumns = sheet.getRow(0).getLastCellNum();
        DataFormatter formatter = new DataFormatter();
        Row row = sheet.getRow(0);
        Cell cell = row.getCell(startOfVehicleColumn);

        Function<String,Map<String,Map<String,String>>> orderMap = vehicle -> {
            return orderData.get(vehicle)
                    .stream()
                    .collect(Collectors.toMap(Function.identity()
                            ,c->ImmutableMap.of("canImport","true","override","true")
                            ,(oldValue, newValue) -> oldValue));
        };
        try{
            Map<String,Map<String,Map<String,String>>> collect =
                    IntStream.range(startOfVehicleColumn, totalColumns)
                        .boxed()
                        .map(col -> formatter.formatCellValue(row.getCell(col)))
                        .collect(Collectors.toMap(Function.identity(), orderMap));
            //System.out.println("collect->"+collect);
        }catch(IllegalStateException e){

        }
    }
    */

    void prepareOrder2(Sheet sheet){
        Map<String, List<String>> orderData = loadData();
        int startOfVehicleColumn = 12;
        int totalColumns = sheet.getRow(0).getLastCellNum();
        DataFormatter formatter = new DataFormatter();
        Row row = sheet.getRow(0);
        Cell cell = row.getCell(startOfVehicleColumn);

        Function<String, Map<POSITION_TYPE, Map<String, ImmutableMap<String, String>>>> orderMap = vehicleNo -> {
            return orderData.get(vehicleNo).stream()
                    .map(order -> {
                        POSITION_TYPE type = findOrderType(order);
                        CrossTableOrder crossTableOrder = new CrossTableOrder();
                        crossTableOrder.setVehicleNo(vehicleNo);
                        crossTableOrder.setOrderNo(order);
                        crossTableOrder.setPositionType(type);
                        crossTableOrder.setAction(ImmutableMap.of("canImport", "true", "override", "true"));
                        return crossTableOrder;
                    })
                    .collect(Collectors.groupingBy(
                            CrossTableOrder::getPositionType,
                            Collectors.toMap(
                                    CrossTableOrder::getOrderNo,
                                    CrossTableOrder::getAction
                            )));

        };

        try{
            Map<String, Map<POSITION_TYPE, Map<String, ImmutableMap<String, String>>>> collect =
                    IntStream.range(startOfVehicleColumn, totalColumns)
                        .boxed()
                        .map(col -> formatter.formatCellValue(row.getCell(col)))
                        .collect(Collectors.toMap(Function.identity(), orderMap));
            System.out.println("--------------prepareOrder2-----------");
            System.out.println("collect->"+collect);
        }catch(IllegalStateException e){

        }
    }

    POSITION_TYPE findOrderType(String order){
        if (order.startsWith("B0")){
            return POSITION_TYPE.HA;
        } else if (order.startsWith("A0")){
            if(order.startsWith("A00")){
                return POSITION_TYPE.VA_LEFT;
            } else if (order.startsWith("A01")){
                return POSITION_TYPE.VA_RIGHT;
            }else {
                //TODO grouping
                return POSITION_TYPE.HA;
            }
        }else{
            //TODO grouping
            return POSITION_TYPE.HA;
        }
    }

    POSITION_TYPE findPartType(String snr){
        if (snr.startsWith("P0")){
            return POSITION_TYPE.HA;
        } else if (snr.startsWith("P0")){
            if(snr.startsWith("P00")){
                return POSITION_TYPE.VA_LEFT;
            } else if (snr.startsWith("P01")){
                return POSITION_TYPE.VA_RIGHT;
            }else {
                //TODO grouping
                return POSITION_TYPE.HA;
            }
        }else{
            //TODO grouping
            return POSITION_TYPE.HA;
        }
    }

    Map<String, List<String>> loadData(){
        Map<String, List<String>> orderData = new HashMap<>();
        orderData.put("2540002", Arrays.asList("A0001","A0002","A0103","A0104"));
        orderData.put("2540083", Arrays.asList("A0005","A0106","B0001","B0002","B0003","B0004"));
        return orderData;
    }

}
class CrossTableVehicleRow extends CrossTableRow implements Cloneable{
    private String vehicleNo;
    private Double amount;

    @Override
    public Object clone() {
        try {
            return (CrossTableVehicleRow) super.clone();
        } catch (CloneNotSupportedException e) {
            return new CrossTableVehicleRow(super.getModul(), super.getSt(), super.getSachnummer(), super.getBenennung(), super.getRowNum(), super.getVehicleMap());
        }
    }

    public CrossTableVehicleRow() {
    }

    public CrossTableVehicleRow(String modul, String st, String sachnummer, String benennung, int rowNum, Map<String, Double> vehicleMap) {
        super(modul, st, sachnummer, benennung, rowNum, vehicleMap);
        this.vehicleNo = vehicleNo;
        this.amount = amount;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CrossTableVehicleRow{" +
                "vehicleNo='" + vehicleNo + '\'' +
                ", amount=" + amount +
                '}';
    }
}
class CrossTableRow {
    //MG , PosE , PV , BZA Stamm ,	Empfänger ,	Anz. Fhrz. , Summe , BME
    private String modul;
    private String st;
    private String sachnummer;
    private String benennung;
    private int rowNum;
    private Map<String,Double> vehicleMap;

    public CrossTableRow() {
    }

    public CrossTableRow(String modul, String st, String sachnummer, String benennung, int rowNum, Map<String, Double> vehicleMap) {
        this.modul = modul;
        this.st = st;
        this.sachnummer = sachnummer;
        this.benennung = benennung;
        this.rowNum = rowNum;
        this.vehicleMap = vehicleMap;
    }

    public String getModul() {
        return modul;
    }

    public void setModul(String modul) {
        this.modul = modul;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getSachnummer() {
        return sachnummer;
    }

    public void setSachnummer(String sachnummer) {
        this.sachnummer = sachnummer;
    }

    public String getBenennung() {
        return benennung;
    }

    public void setBenennung(String benennung) {
        this.benennung = benennung;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public Map<String, Double> getVehicleMap() {
        return vehicleMap;
    }

    public void setVehicleMap(Map<String, Double> vehicleMap) {
        this.vehicleMap = vehicleMap;
    }

    @Override
    public String toString() {
        return "CrossTableRow{" +
                "modul='" + modul + '\'' +
                ", st='" + st + '\'' +
                ", sachnummer='" + sachnummer + '\'' +
                ", benennung='" + benennung + '\'' +
                ", rowNum=" + rowNum +
                ", vehicleMap=" + vehicleMap +
                '}';
    }
}
class CrossTableOrder {
    String vehicleNo;
    TestExcel.POSITION_TYPE positionType;
    String orderNo;
    ImmutableMap<String,String> action;

    public CrossTableOrder() { }

    public CrossTableOrder(String vehicleNo, TestExcel.POSITION_TYPE positionType, String orderNo, ImmutableMap<String, String> action) {
        this.vehicleNo = vehicleNo;
        this.positionType = positionType;
        this.orderNo = orderNo;
        this.action = action;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public TestExcel.POSITION_TYPE getPositionType() {
        return positionType;
    }

    public void setPositionType(TestExcel.POSITION_TYPE orderType) {
        this.positionType = positionType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public ImmutableMap<String, String> getAction() {
        return action;
    }

    public void setAction(ImmutableMap<String, String> action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "CrossTableOrder{" +
                "vehicleNo='" + vehicleNo + '\'' +
                ", positionType=" + positionType +
                ", orderNo='" + orderNo + '\'' +
                ", action=" + action +
                '}';
    }
}
class CrossTableVehicle {
    String vehicleNo;
    TestExcel.POSITION_TYPE positionType;
    String snr;
    Double amount;
    String module;
    List<Map<String,String>> vehicleMap;

    public CrossTableVehicle() {
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public TestExcel.POSITION_TYPE getPositionType() {
        return positionType;
    }

    public void setPositionType(TestExcel.POSITION_TYPE positionType) {
        this.positionType = positionType;
    }

    public String getSnr() {
        return snr;
    }

    public void setSnr(String snr) {
        this.snr = snr;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public List<Map<String, String>> getVehicleMap() {
        return vehicleMap;
    }

    public void setVehicleMap(List<Map<String, String>> vehicleMap) {
        this.vehicleMap = vehicleMap;
    }

    @Override
    public String toString() {
        return "CrossTableVehicle{" +
                "vehicleNo='" + vehicleNo + '\'' +
                ", positionType=" + positionType +
                ", snr='" + snr + '\'' +
                ", amount='" + amount + '\'' +
                ", vehicleMap=" + vehicleMap +
                '}';
    }
}