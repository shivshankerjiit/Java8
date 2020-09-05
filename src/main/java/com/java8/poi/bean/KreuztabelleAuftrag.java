package com.java8.poi.bean;

import com.google.common.collect.ImmutableMap;

public class KreuztabelleAuftrag {
    private String fahrzeugNr;
    private String auftragNr;
    private String modul;
    private String varianteSnr;
    private POSITION position;
    private ImmutableMap<String,String> action;

    public KreuztabelleAuftrag() {
    }

    public String getFahrzeugNr() {
        return fahrzeugNr;
    }

    public void setFahrzeugNr(String fahrzeugNr) {
        this.fahrzeugNr = fahrzeugNr;
    }

    public String getAuftragNr() {
        return auftragNr;
    }

    public void setAuftragNr(String auftragNr) {
        this.auftragNr = auftragNr;
    }

    public String getModul() {
        return modul;
    }

    public void setModul(String modul) {
        this.modul = modul;
    }

    public String getVarianteSnr() {
        return varianteSnr;
    }

    public void setVarianteSnr(String varianteSnr) {
        this.varianteSnr = varianteSnr;
    }

    public POSITION getPosition() {
        return position;
    }

    public void setPosition(POSITION position) {
        this.position = position;
    }

    public ImmutableMap<String, String> getAction() {
        return action;
    }

    public void setAction(ImmutableMap<String, String> action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "KreuztabelleAuftrag{" +
                "fahrzeugNr='" + fahrzeugNr + '\'' +
                ", auftragNr='" + auftragNr + '\'' +
                ", modul='" + modul + '\'' +
                ", varianteSnr='" + varianteSnr + '\'' +
                ", position=" + position +
                ", action=" + action +
                '}';
    }
}
