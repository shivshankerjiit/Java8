package com.java8.poi.controller;

import com.google.common.collect.ImmutableMap;
import com.java8.poi.bean.KreuztabelleAuftrag;
import com.java8.poi.bean.KreuztabelleDaten;
import com.java8.poi.bean.KreuztabelleTeile;
import com.java8.poi.bean.POSITION;
import org.apache.poi.POIXMLException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StucklistController {

    public int startOfFahrzeugColumn = 12;

    public static void main(String[] args)  {
        final String FILE_NAME = "crosstable_c254.xlsx";
        StucklistController example = new StucklistController();
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
                prepareData(sheet);
            }
        } catch (POIXMLException poie) {
            System.out.println("POIXMLException: something wrong by ready XML file: " + poie.getMessage());
        } catch (Exception e) {
            System.out.println("++++ Something goes wrong +++++ " + e.getMessage());
        }
    }

    private List<String>  getFahrzeugList(Row row0) {
        DataFormatter formatter = new DataFormatter();
        int totalColumns = row0.getLastCellNum();

        return IntStream.range(startOfFahrzeugColumn, totalColumns)
                .mapToObj(col -> formatter.formatCellValue(row0.getCell(col)))
                .collect(Collectors.toList());
    }

    void prepareData(Sheet sheet) {
        List<Map<String, Map<POSITION, List<KreuztabelleAuftrag>>>> auftragInfo = prepareAuftragInfoMap(sheet);
        List<Map<String, Map<String, Map<String, List<KreuztabelleTeile>>>>> modulMap = prepareModulMap(sheet);
        //processKreuztablleProcess(sheet,auftragInfo,modulMap);
    }

    /*void processKreuztablleProcess(
            Sheet sheet,
            List<Map<String, Map<String, Map<String, List<KreuztabelleTeile>>>>> auftragInfo,
            List<Map<String, Map<String, Map<String, List<KreuztabelleTeile>>>>> modulMap
    ){
        Row row0 = sheet.getRow(0);
        List<String> fahrzeugList = getFahrzeugList(row0);

        fahrzeugList.stream()
                .map(fahrzeugNr -> {
                    modulMap.stream()
                            .map(modulMapElement -> {
                                return modulMapElement.get(fahrzeugNr).values();
                            })
                            .flatMap(x->x.stream())
                            .map(m -> m.values())
                            .filter(m2 -> m2.size()>0)
                            .flatMap(m-> m.stream())
                            .filter(tList -> true)
                            .map(tList ->{
                                tList.stream()
                                        .map(t - {

                                        })
                                        .forEach(System.out::println);
                            })
                                t.
                                    }
                                m.
                            );
                                teile
                            })
                            .forEach(System.out::println);
                })
                .collect(Collectors.toList());

    }*/
    List<Map<String, Map<POSITION, List<KreuztabelleAuftrag>>>> prepareAuftragInfoMap(Sheet sheet) {
        Map<String, List<String>> orderData = loadData();
        int totalColumns = sheet.getRow(0).getLastCellNum();
        DataFormatter formatter = new DataFormatter();
        Row row0 = sheet.getRow(0);
        Cell cell = row0.getCell(startOfFahrzeugColumn);

        List<String> fahrzeugList = getFahrzeugList(row0);

        List<Map<String,Map<POSITION, List<KreuztabelleAuftrag>>>> auftragInfo = fahrzeugList.stream()
                .map(fahrzeugNr -> {
                    Set<String> aufteagSet = orderData.get(fahrzeugNr).stream().collect(Collectors.toSet());

                    return aufteagSet.stream()
                            .map(auftrag -> {
                                POSITION position = findOrderType(auftrag);
                                ImmutableMap<String, String> action = findAction(auftrag);
                                KreuztabelleAuftrag kreuztabelleAuftrag = new KreuztabelleAuftrag();
                                kreuztabelleAuftrag.setAuftragNr(auftrag);
                                kreuztabelleAuftrag.setFahrzeugNr(fahrzeugNr);
                                kreuztabelleAuftrag.setPosition(position);
                                kreuztabelleAuftrag.setAction(action);
                                return kreuztabelleAuftrag;
                            })
                            .collect(Collectors.groupingBy(
                                    KreuztabelleAuftrag::getFahrzeugNr,
                                    Collectors.groupingBy(
                                            KreuztabelleAuftrag::getPosition,
                                                    Collectors.toList())));
                })
                .collect(Collectors.toList());
        System.out.println("------auftraginfo->");
        System.out.println(auftragInfo);
        return auftragInfo;
    }


    List<Map<String, Map<String, Map<String, List<KreuztabelleTeile>>>>> prepareModulMap(Sheet sheet) {
        Map<String, List<String>> orderData = loadData();
        int totalColumns = sheet.getRow(0).getLastCellNum();
        int totalRows = sheet.getPhysicalNumberOfRows();
        DataFormatter formatter = new DataFormatter();
        Row row0 = sheet.getRow(0);
        Cell cell = row0.getCell(startOfFahrzeugColumn);

        List<String> fahrzeugList = getFahrzeugList(row0);

        String fahrzeugNr = fahrzeugList.get(1);
        List<Map<String, Map<String, Map<String, List<KreuztabelleTeile>>>>> modulMap = IntStream.range(12, totalColumns)
                .mapToObj(col -> {
                    return IntStream.range(1, totalRows)
                            .mapToObj(rowNr -> {
                                Row row = sheet.getRow(rowNr);
                                KreuztabelleDaten rowData = new KreuztabelleDaten();
                                rowData.setModul(formatter.formatCellValue(row.getCell(1)));
                                rowData.setSt(formatter.formatCellValue(row.getCell(4)));
                                rowData.setSachnummer(formatter.formatCellValue(row.getCell(5)));
                                rowData.setBenennung(formatter.formatCellValue(row.getCell(6)));
                                rowData.setAmount(row.getCell(col).getNumericCellValue());
                                rowData.setFahrzeugNr(fahrzeugList.get((col-12)));
                                rowData.setRowNum(rowNr);
                                return rowData;
                            })
                            .filter(rowData -> (rowData.getAmount()) > 0
                                    && (rowData.getSt().equals("02") || rowData.getSt().equals("02")))
                            .map(rowData -> {
                                String snr = rowData.getSachnummer();
                                POSITION position = findOrderType(snr);
                                KreuztabelleTeile kreuztabelleTeile = new KreuztabelleTeile();
                                kreuztabelleTeile.setAmount(rowData.getAmount());
                                kreuztabelleTeile.setFahrzeugNr(rowData.getFahrzeugNr());
                                kreuztabelleTeile.setKommiRelevant(true);
                                kreuztabelleTeile.setModul(rowData.getModul());
                                kreuztabelleTeile.setPosition(position);
                                kreuztabelleTeile.setSnr(rowData.getSachnummer());
                                return kreuztabelleTeile;
                            })
                            .collect(Collectors.groupingBy(
                                    KreuztabelleTeile::getFahrzeugNr,
                                    Collectors.groupingBy(
                                            KreuztabelleTeile::getModul,
                                            Collectors.groupingBy(
                                                    KreuztabelleTeile::getSnr,
                                                    Collectors.toList()
                                            )
                                    )
                            ));
                })
                .collect(Collectors.toList());


        System.out.println("------modulMap->");
        System.out.println(modulMap);
        return modulMap;
    }

    public ImmutableMap<String, String> findAction(String auftrag) {
        return ImmutableMap.of("canImport", "true", "override", "true");
    }

    public POSITION findOrderType(String auftrag) {
        String suffix = auftrag.substring(auftrag.length()-2);
        POSITION p ;
        switch (suffix){
            case "00" :
                p = POSITION.VA_LEFT_RIGHT; break;
            case "01" :
            case "02" :
                p = POSITION.VA_LEFT; break;
            case "03" :
            case "04" :
                p = POSITION.VA_RIGHT; break;
            default: p = POSITION.HA;
        }
        return p;
    }
    private Map<String, List<String>> loadData() {
        Map<String, List<String>> orderData = new HashMap<>();
        //orderData.put("2540002", Arrays.asList("A020001","A020002","A020003","A020004"));
        //orderData.put("2540003", Arrays.asList("A030001","A030002","A030003","A030004","A030005","A030006"));
        orderData.put("2540002", Arrays.asList("A020001","A020002"));
        orderData.put("2540003", Arrays.asList("A030001","A030002","A030006"));
        return orderData;
    }

    ;

        /*try{
            Map<String, Map<TestExcel.POSITION_TYPE, Map<String, ImmutableMap<String, String>>>> collect =
                    IntStream.range(startOfVehicleColumn, totalColumns)
                            .boxed()
                            .map(col -> formatter.formatCellValue(row.getCell(col)))
                            .collect(Collectors.toMap(Function.identity(), orderMap));
            System.out.println("--------------prepareOrder2-----------");
            System.out.println("collect->"+collect);
        }catch(IllegalStateException e){

        }*/
    }



