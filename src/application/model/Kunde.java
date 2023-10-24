package application.model;

import java.util.ArrayList;

public class Kunde extends Person {

    private String ean;
    private ArrayList<Salg> salgArrayList = new ArrayList<>();


    public Kunde(String navn, String adresse, String tlfnr, String cpr, String email) {
        super(navn, adresse, tlfnr, cpr, email);
        this.ean = ean;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public ArrayList<Salg> getSalgArrayList() {
        return new ArrayList<>(salgArrayList);
    }


    public ArrayList<Salg> getOrdreArrayList() {
        return new ArrayList<>(salgArrayList);
    }



    @Override
    public String toString() {
        return "Kunde{" +
                "cvr='" + ean + '\'' +
                '}';
    }


}
