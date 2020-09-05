package com.java8.poi.controller;

import com.google.common.collect.ImmutableMap;
import com.java8.fp1.Course;
import com.java8.poi.bean.KreuztabelleAuftrag;
import com.java8.poi.bean.KreuztabelleDaten;
import com.java8.poi.bean.KreuztabelleTeile;
import com.java8.poi.bean.POSITION;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class test {
    public static void main(String[] args) {
        System.out.println("hi");
        test t = new test();
        //t.listToMap();
        double a = 2.0;
        double b = ((a % 2) == 0) ? a/2 : 200;
        double amount = 4;
        amount = ((amount % 2) == 0) ? amount/2 : 1;
        System.out.println("b->"+b+","+amount);

    }

    private  void listToMap() {
        List<Course> courses1 =
                Arrays.asList(
                        new Course("Spring MVC","Spring",100,125),
                        new Course("Spring Boot","Spring",200,225)
                );
        List<Course> courses2 =
                Arrays.asList(
                        new Course("Dance","Art",60,215),
                        new Course("Spring Fundamental","Spring",300,325)
                );
        List<Course> courses3 =
                Arrays.asList(
                        new Course("Music","Art",150,115),
                        new Course("Dance","Art",60,215)
                );
        List<Course> courses4 =
                Arrays.asList(
                        new Course("Spring Boot","Spring",200,225),
                        new Course("Music","Art",150,115)
                );
        List<List<Course>> cc = Arrays.asList(courses1,courses2,courses3,courses4);

        Map<Integer, List<Course>> collect = IntStream.range(0, 4)
                .mapToObj(i -> {
                    AbstractMap.SimpleEntry<Integer, List<Course>> e = new AbstractMap.SimpleEntry<>(Integer.valueOf(i), cc.get(i));
                    return e;
                })
                /*.collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(
                                Map.Entry::getValue,
                                Collectors.toList())));*/
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

        System.out.println("collect->"+collect);


    }

    public void uploadSaveItems(){
        List<Map<String,String>> filterList = getFilterList();
        filterList.stream()
                .map(item -> item.entrySet())
                .flatMap(Set::stream)
                //.map(entries -> {
                  //  String fahrzeugNr = entries;
                //})
                .collect(Collectors.toList());

        Map<String, List<KreuztabelleAuftrag>> prepareAuftragInfo = null;
        Map<String, List<KreuztabelleTeile>> prepareModulInfo = null;

        Map<String,List<KreuztabelleAuftrag>> auftragInfo = getAuftragInfo2();
        filterList.stream()
                .map(itemMap -> {
                    String fahrzeugNr = itemMap.get("fahrzeugNr");
                    String auftragNr = itemMap.get("auftragNr");

                    List<KreuztabelleAuftrag> auftragList = prepareAuftragInfo.get(fahrzeugNr);
                    auftragList.stream()
                            .filter(auftrag -> auftrag.getAuftragNr().equals(auftragNr))
                            .collect(Collectors.toList());

                    List<KreuztabelleTeile> teileList = prepareModulInfo.get(fahrzeugNr);
                    return teileList;
                }).collect(Collectors.toList());
        //*********************************************/
        String fahrzeugNr1 = "";
        List<KreuztabelleTeile> teileList = prepareModulInfo.get(fahrzeugNr1);
        teileList.stream()
                .forEach(teile -> {
                    List<KreuztabelleAuftrag> auftragList = prepareAuftragInfo.get(fahrzeugNr1);
                    List<KreuztabelleAuftrag> auftragList2 =
                            auftragList.stream()
                                .filter(auftrag -> {
                                    return filterList.stream()
                                        .anyMatch(itemMap ->{
                                            return itemMap.get("fahrzeugNr").equals(fahrzeugNr1) && itemMap.get("auftragNr").equals(auftrag.getAuftragNr());
                                        });
                                }).filter(auftrag -> auftrag.getPosition().equals(teile.getPosition()))
                                    .collect(Collectors.toList());
                    process(teile,auftragList2);
                });

    }

    private void process(KreuztabelleTeile teile, List<KreuztabelleAuftrag> auftragList2) {
        auftragList2.stream()
                .forEach(auftrag ->
                                System.out.println(auftrag)
                        //add(teile,auftrag)
                );
    }

    public List<Map<String,String>> getFilterList(){
        Map<String,String> items = new HashMap<>();
        items.put("fahrzeugNr","2540002");
        items.put("auftragNr","AC0000000176");
        items.put("canImport","true");
        items.put("overwrite","true");
        Map<String,String> items2 = new HashMap<>();
        items2.put("fahrzeugNr","2540083");
        items2.put("auftragNr","AC0000000117");
        items2.put("canImport","true");
        items2.put("overwrite","false");
        Map<String,String> items3 = new HashMap<>();
        items2.put("fahrzeugNr","2540083");
        items2.put("auftragNr","AC0000000116");
        items2.put("canImport","true");
        items2.put("overwrite","false");
        Map<String,String> items4 = new HashMap<>();
        items2.put("fahrzeugNr","2540083");
        items2.put("auftragNr","AC0000000185");
        items2.put("canImport","true");
        items2.put("overwrite","true");
        List<Map<String,String>> l = Arrays.asList(items,items2,items3,items4);
        return l;
    }
    public Map<String,List<KreuztabelleAuftrag>> getAuftragInfo2(){
        KreuztabelleAuftrag auftrag11 = new KreuztabelleAuftrag();
        auftrag11.setFahrzeugNr("2540002");
        auftrag11.setAuftragNr("AC0000000176");
        auftrag11.setPosition(POSITION.HA);
        auftrag11.setAction(ImmutableMap.of("canImport","true", "overwrite","true"));
        KreuztabelleAuftrag auftrag21 = new KreuztabelleAuftrag();
        auftrag21.setFahrzeugNr("2540083");
        auftrag21.setAuftragNr("AC0000000117");
        auftrag21.setPosition(POSITION.VA_RIGHT);
        auftrag21.setAction(ImmutableMap.of("canImport","true", "overwrite","false"));
        KreuztabelleAuftrag auftrag22 = new KreuztabelleAuftrag();
        auftrag22.setFahrzeugNr("2540083");
        auftrag22.setAuftragNr("AC0000000116");
        auftrag22.setPosition(POSITION.VA_LEFT);
        auftrag22.setAction(ImmutableMap.of("canImport","true", "overwrite","false"));
        KreuztabelleAuftrag auftrag23 = new KreuztabelleAuftrag();
        auftrag23.setFahrzeugNr("2540083");
        auftrag23.setAuftragNr("AC0000000185");
        auftrag23.setPosition(POSITION.HA);
        auftrag23.setAction(ImmutableMap.of("canImport","true", "overwrite","true"));
        Map<String,List<KreuztabelleAuftrag>> vehicle1 = new HashMap<>();
        vehicle1.put("2540002",Arrays.asList(auftrag11));
        vehicle1.put("2540083",Arrays.asList(auftrag21,auftrag22,auftrag23));
        return vehicle1;
        /*[{2540002={
                HA=[KreuztabelleAuftrag{fahrzeugNr='2540002', auftragNr='AC0000000176', modul='null', varianteSnr='null', position=HA, action={canImport=true, overwrite=true}}]}},
        {2540083=
                {VA_RIGHT=[KreuztabelleAuftrag{fahrzeugNr='2540083', auftragNr='AC0000000117', modul='null', varianteSnr='null', position=VA_RIGHT, action={canImport=true, overwrite=false}}],
            VA_LEFT=[KreuztabelleAuftrag{fahrzeugNr='2540083', auftragNr='AC0000000116', modul='null', varianteSnr='null', position=VA_LEFT, action={canImport=true, overwrite=false}}],
            HA=[KreuztabelleAuftrag{fahrzeugNr='2540083', auftragNr='AC0000000185', modul='null', varianteSnr='null', position=HA, action={canImport=true, overwrite=true}}]}}]
    */
    }
    public Map<String,Map<POSITION, KreuztabelleAuftrag>> getAuftragInfo(){
        Map<POSITION, KreuztabelleAuftrag> map1 = new HashMap<>();
        KreuztabelleAuftrag auftrag11 = new KreuztabelleAuftrag();
        auftrag11.setFahrzeugNr("2540002");
        auftrag11.setAuftragNr("AC0000000176");
        auftrag11.setPosition(POSITION.HA);
        auftrag11.setAction(ImmutableMap.of("canImport","true", "overwrite","true"));
        map1.put(POSITION.HA,auftrag11);
        Map<POSITION, KreuztabelleAuftrag> map2 = new HashMap<>();
        KreuztabelleAuftrag auftrag21 = new KreuztabelleAuftrag();
        auftrag21.setFahrzeugNr("2540083");
        auftrag21.setAuftragNr("AC0000000117");
        auftrag21.setPosition(POSITION.VA_RIGHT);
        auftrag21.setAction(ImmutableMap.of("canImport","true", "overwrite","false"));
        map2.put(POSITION.VA_RIGHT,auftrag21);
        KreuztabelleAuftrag auftrag22 = new KreuztabelleAuftrag();
        auftrag22.setFahrzeugNr("2540083");
        auftrag22.setAuftragNr("AC0000000116");
        auftrag22.setPosition(POSITION.VA_LEFT);
        auftrag22.setAction(ImmutableMap.of("canImport","true", "overwrite","false"));
        map2.put(POSITION.VA_LEFT,auftrag21);
        KreuztabelleAuftrag auftrag23 = new KreuztabelleAuftrag();
        auftrag23.setFahrzeugNr("2540083");
        auftrag23.setAuftragNr("AC0000000185");
        auftrag23.setPosition(POSITION.HA);
        auftrag23.setAction(ImmutableMap.of("canImport","true", "overwrite","true"));
        map2.put(POSITION.HA,auftrag21);
        Map<String,Map<POSITION, KreuztabelleAuftrag>> vehicle1 = new HashMap<>();
        vehicle1.put("2540002",map1);
        vehicle1.put("2540083",map2);
        return vehicle1;
        /*[{2540002={
                HA=[KreuztabelleAuftrag{fahrzeugNr='2540002', auftragNr='AC0000000176', modul='null', varianteSnr='null', position=HA, action={canImport=true, overwrite=true}}]}},
        {2540083=
                {VA_RIGHT=[KreuztabelleAuftrag{fahrzeugNr='2540083', auftragNr='AC0000000117', modul='null', varianteSnr='null', position=VA_RIGHT, action={canImport=true, overwrite=false}}],
            VA_LEFT=[KreuztabelleAuftrag{fahrzeugNr='2540083', auftragNr='AC0000000116', modul='null', varianteSnr='null', position=VA_LEFT, action={canImport=true, overwrite=false}}],
            HA=[KreuztabelleAuftrag{fahrzeugNr='2540083', auftragNr='AC0000000185', modul='null', varianteSnr='null', position=HA, action={canImport=true, overwrite=true}}]}}]
    */
    }



}
