package application.model;


import java.util.ArrayList;

public class PerProduktPris {

    private double pris;
    private int klip;
    private Salgssituation salgssituation;
    private Produkt produkt;

    public PerProduktPris(double pris, Produkt produkt, Salgssituation salgssituation) {
        this.pris = pris;
        this.produkt = produkt;
        this.salgssituation = salgssituation;

    }

    public PerProduktPris(double pris, int klip, Produkt produkt, Salgssituation salgssituation) {
        this.pris = pris;
        this.klip = klip;
        this.produkt = produkt;
        this.salgssituation = salgssituation;
    }


    public double getPris() {
        return pris;
    }

    public void setPris(double pris) {
        if (pris < 0) {
            throw new IllegalArgumentException("Pris kan ikke have negativ værdi.");
        }
        this.pris = pris;
    }

    public int getKlip() {
        return klip;
    }

    public void setKlip(int klip) {
        if (klip < 0) {
            throw new IllegalArgumentException("Antal klip kan ikke være af negativ værdi.");
        }
        this.klip = klip;
    }

    public Produkt getProdukt() {
        return produkt;
    }

    public Salgssituation getSalgssituation() {
        return salgssituation;
    }

    public void setSalgssituation(Salgssituation salgssituation) {
        if (this.salgssituation != salgssituation) {
            Salgssituation oldSalgssituation = this.salgssituation;
            if (oldSalgssituation != null) {
                oldSalgssituation.removePerProdukterPris(this);
            }
            this.salgssituation = salgssituation;
            if (salgssituation != null) {
                salgssituation.addPerProdukterPris(this);
            }
        }
    }

    public void setProdukt(Produkt produkt) {
        if (this.produkt != produkt) {
            this.produkt = produkt;
        }
    }

    @Override
    public String toString() {
        return String.format("%s  %s %s", pris, produkt, salgssituation);
    }

}
