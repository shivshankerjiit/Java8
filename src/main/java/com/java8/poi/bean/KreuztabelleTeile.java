package com.java8.poi.bean;

public class KreuztabelleTeile {
    private String fahrzeugNr;
    private String snr;
    private String modul;
    private POSITION position;
    private Double amount;
    private boolean kommiRelevant;

    public KreuztabelleTeile() {
    }

    public KreuztabelleTeile(String fahrzeugNr, String snr, String modul, POSITION position, Double amount, boolean kommiRelevant) {
        this.fahrzeugNr = fahrzeugNr;
        this.snr = snr;
        this.modul = modul;
        this.position = position;
        this.amount = amount;
        this.kommiRelevant = kommiRelevant;
    }

    public String getFahrzeugNr() {
        return fahrzeugNr;
    }

    public void setFahrzeugNr(String fahrzeugNr) {
        this.fahrzeugNr = fahrzeugNr;
    }

    public String getSnr() {
        return snr;
    }

    public void setSnr(String snr) {
        this.snr = snr;
    }

    public String getModul() {
        return modul;
    }

    public void setModul(String modul) {
        this.modul = modul;
    }

    public POSITION getPosition() {
        return position;
    }

    public void setPosition(POSITION position) {
        this.position = position;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public boolean isKommiRelevant() {
        return kommiRelevant;
    }

    public void setKommiRelevant(boolean kommiRelevant) {
        this.kommiRelevant = kommiRelevant;
    }

    @Override
    public String toString() {
        return "KreuztabelleTeile{" +
                "fahrzeugNr='" + fahrzeugNr + '\'' +
                ", snr='" + snr + '\'' +
                ", modul='" + modul + '\'' +
                ", position=" + position +
                ", amount=" + amount +
                ", kommiRelevant=" + kommiRelevant +
                '}';
    }
}
