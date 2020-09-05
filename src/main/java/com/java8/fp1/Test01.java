package com.java8.fp1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test01 {
    public static void main(String[] args){
        List<String> orderListWithModul = Arrays.asList("1","2","3","4","5");
        Map<String, List<Map<String,String>>> c = orderListWithModul.stream()
                .collect(Collectors.groupingBy(auftragData -> auftragData, Collectors.mapping(
                        auftragData -> {
                            List<String> findAllByVarAuftragName = Arrays.asList("11", "22", "33", "44", "55");
                            Map<String,String> map = new HashMap<>();
                            if (findAllByVarAuftragName.size() == 0) {
                                map.put("overwrite", "false");

                            } else {
                                map.put("overwrite", "true");
                            }
                            return map;
                        }, Collectors.toList()
                )));

        System.out.println(c);
        Map<String, Map<String,String>> c2 = new HashMap<>();
                c.entrySet().stream().forEach(entry->c2.put(entry.getKey(),entry.getValue().get(0)));
        System.out.println(c2);


        //
        Map<String, Map<String,String>> auftragListToMap2 = new HashMap<>();
        orderListWithModul.stream()
                .forEach(auftragData->{
                            List<String> findAllByVarAuftragName = Arrays.asList("11", "22", "33", "44", "55");
                            Map<String,String> map = new HashMap<>();
                            if (findAllByVarAuftragName.size() == 0) {
                                map.put("overwrite", "false");
                            } else {
                                map.put("overwrite", "true");
                            }
                            auftragListToMap2.put(auftragData,map);
                        }
                );
        System.out.println(auftragListToMap2);

    }
}
