package com.java8.poi.bean;

public class KreuztabelleDaten {
    //MG , PosE , PV , BZA Stamm ,	Empfänger ,	Anz. Fhrz. , Summe , BME
    private String modul;
    private String st;
    private String sachnummer;
    private String benennung;
    private int rowNum;
    private POSITION position;
    private String fahrzeugNr;
    private Double amount;

    public KreuztabelleDaten() {
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

    public POSITION getPosition() {
        return position;
    }

    public void setPosition(POSITION position) {
        this.position = position;
    }

    public String getFahrzeugNr() {
        return fahrzeugNr;
    }

    public void setFahrzeugNr(String fahrzeugNr) {
        this.fahrzeugNr = fahrzeugNr;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "KreuztabelleDaten{" +
                "modul='" + modul + '\'' +
                ", st='" + st + '\'' +
                ", sachnummer='" + sachnummer + '\'' +
                ", benennung='" + benennung + '\'' +
                ", rowNum=" + rowNum +
                ", position=" + position +
                ", fahrzeugNr='" + fahrzeugNr + '\'' +
                ", amount=" + amount +
                '}';
    }
}
