package com.java8.poi.controller;

import com.java8.poi.bean.KreuztabelleTeile;
import com.java8.poi.bean.POSITION;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class TestGrouping {
    public static void main(String[] args) {
        TestGrouping t = new TestGrouping();
        List<KreuztabelleTeile> list = t.getData();
        Map<String, Map<String, List<KreuztabelleTeile>>> teileMap = list.stream()
                .collect(
                        Collectors.groupingBy(
                                KreuztabelleTeile::getModul,
                                Collectors.groupingBy(
                                        KreuztabelleTeile::getSnr,
                                        Collectors.toList()

                                )
                        ));
        System.out.println("teileMap->"+teileMap);

        Set<String> teielKeySet = teileMap.keySet();
        List<KreuztabelleTeile> collect = teielKeySet.stream()
                .map(modulNr -> {
                    Map<String, List<KreuztabelleTeile>> snrMap = teileMap.get(modulNr);
                    Set<String> snrKeySet = snrMap.keySet();
                    return snrKeySet.stream()
                            .map(snrNr -> {
                                List<KreuztabelleTeile> teileList = snrMap.get(snrNr);
                                KreuztabelleTeile teile = teileList.get(0);
                                POSITION position = teile.getPosition();
                                List<KreuztabelleTeile> resultList = new ArrayList<>();
                                double amount = teileList.stream()
                                        .mapToDouble(KreuztabelleTeile::getAmount)
                                        .sum();
                                switch (position) {
                                    case VA_LEFT:
                                    case VA_RIGHT:
                                    case HA:
                                        if (teileList.size() == 1) {
                                            resultList.add(teile);
                                            break;

                                        } else {
                                            teile.setAmount(amount);
                                            resultList.add(teile);
                                            break;
                                        }
                                    case VA_LEFT_RIGHT:
                                        if ((teileList.size() % 2) == 0) {
                                            amount = ((amount % 2) == 0) ? amount / 2 : 1;
                                            KreuztabelleTeile teileLeft = teileList.get(0);
                                            KreuztabelleTeile teileRight = teileList.get(1);
                                            teileLeft.setAmount(amount);
                                            teileLeft.setPosition(POSITION.VA_LEFT);
                                            teileRight.setAmount(amount);
                                            teileRight.setPosition(POSITION.VA_RIGHT);
                                            resultList.add(teileLeft);
                                            resultList.add(teileRight);
                                            break;
                                        } else {
                                            //TODO
                                            break;
                                        }
                                }
                                return resultList;
                            })
                            .flatMap(List::stream)
                            .collect(Collectors.toList());
                })
                .flatMap(List::stream)
                .collect(Collectors.toList());
        System.out.println("collect->"+collect);
    }
    public List<KreuztabelleTeile> getData(){
        KreuztabelleTeile t1 = new KreuztabelleTeile("2540002", "A0009909200", "801200", POSITION.VA_LEFT, 1.0, true);
        KreuztabelleTeile t2 = new KreuztabelleTeile("2540002", "A0009909201", "801200", POSITION.VA_RIGHT, 1.0, true);

        KreuztabelleTeile t3 = new KreuztabelleTeile("2540002", "A0009909202", "801200", POSITION.VA_LEFT, 1.0, true);
        KreuztabelleTeile t4 = new KreuztabelleTeile("2540002", "A0009909203", "801200", POSITION.VA_RIGHT, 1.0, true);

        KreuztabelleTeile t5 = new KreuztabelleTeile("2540002", "A0009909205", "801200", POSITION.VA_LEFT, 1.0, true);
        KreuztabelleTeile t6 = new KreuztabelleTeile("2540002", "A0009909205", "801200", POSITION.VA_LEFT, 1.0, true);
        KreuztabelleTeile t7 = new KreuztabelleTeile("2540002", "A0009909205", "801200", POSITION.VA_LEFT, 1.0, true);
        KreuztabelleTeile t8 = new KreuztabelleTeile("2540002", "A0009909205", "801200", POSITION.VA_LEFT, 1.0, true);

        KreuztabelleTeile t9 = new KreuztabelleTeile("2540002", "A0009909204", "801200", POSITION.VA_RIGHT, 1.0, true);
        KreuztabelleTeile t10 = new KreuztabelleTeile("2540002", "A0009909204", "801200", POSITION.VA_RIGHT, 1.0, true);

        KreuztabelleTeile t11 = new KreuztabelleTeile("2540002", "A0009909300", "801300", POSITION.HA, 1.0, true);
        KreuztabelleTeile t12 = new KreuztabelleTeile("2540002", "A0009909300", "801300", POSITION.HA, 1.0, true);
        KreuztabelleTeile t13 = new KreuztabelleTeile("2540002", "A0009909300", "801300", POSITION.HA, 1.0, true);
        KreuztabelleTeile t14 = new KreuztabelleTeile("2540002", "A0009909300", "801300", POSITION.HA, 1.0, true);


        KreuztabelleTeile t15 = new KreuztabelleTeile("2540002", "A0009909301", "801301", POSITION.HA, 1.0, true);
        KreuztabelleTeile t16 = new KreuztabelleTeile("2540002", "A0009909302", "801301", POSITION.HA, 1.0, true);

        KreuztabelleTeile t17 = new KreuztabelleTeile("2540002", "A0009909400", "801400", POSITION.VA_LEFT_RIGHT, 1.0, true);
        KreuztabelleTeile t18 = new KreuztabelleTeile("2540002", "A0009909400", "801400", POSITION.VA_LEFT_RIGHT, 1.0, true);
        KreuztabelleTeile t19 = new KreuztabelleTeile("2540002", "A0009909400", "801400", POSITION.VA_LEFT_RIGHT, 1.0, true);
        KreuztabelleTeile t20 = new KreuztabelleTeile("2540002", "A0009909400", "801400", POSITION.VA_LEFT_RIGHT, 1.0, true);

        KreuztabelleTeile t21 = new KreuztabelleTeile("2540002", "A0009909401", "801400", POSITION.VA_LEFT_RIGHT, 1.0, true);
        KreuztabelleTeile t22 = new KreuztabelleTeile("2540002", "A0009909401", "801400", POSITION.VA_LEFT_RIGHT, 2.0, true);

        KreuztabelleTeile t23 = new KreuztabelleTeile("2540002", "A0009909402", "801400", POSITION.VA_LEFT_RIGHT, 2.0, true);
        KreuztabelleTeile t24 = new KreuztabelleTeile("2540002", "A0009909402", "801400", POSITION.VA_LEFT_RIGHT, 2.0, true);
        KreuztabelleTeile t25 = new KreuztabelleTeile("2540002", "A0009909402", "801400", POSITION.VA_LEFT_RIGHT, 2.0, true);

        List<KreuztabelleTeile> teileList = Arrays.asList(t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18,t19,t20,t21,t22,t23,t24,t25);
        return teileList;
    }
}
