package application.model;

import java.util.ArrayList;
import java.util.List;

public class Salgssituation {

    private String navn;
    private List<PerProduktPris> perProduktPriser = new ArrayList<>();

    public Salgssituation(String navn) {
        this.navn = navn;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }


    public void addPerProdukterPris(PerProduktPris perProduktPris) {
        if (!perProduktPriser.contains(perProduktPris)) {
            perProduktPriser.add(perProduktPris);
            perProduktPris.setSalgssituation(this);

        }
    }

    public void removePerProdukterPris(PerProduktPris perProduktPris) {
        if (perProduktPriser.contains(perProduktPris)) {
            perProduktPriser.remove(perProduktPris);
            perProduktPris.setSalgssituation(null);
        }
    }

    public PerProduktPris createPerProduktPris(double pris, int klip, Produkt produkt) {
        PerProduktPris perProduktPris = new PerProduktPris(pris,klip,produkt,this);
        perProduktPriser.add(perProduktPris);
        return perProduktPris;
    }

    public PerProduktPris createPerProduktPris(double pris, Produkt produkt) {
        PerProduktPris perProduktPris = new PerProduktPris(pris,produkt,this);
        perProduktPriser.add(perProduktPris);
        return perProduktPris;
    }


    public List<PerProduktPris> getPerProduktPriser() {
        return new ArrayList<>(perProduktPriser);
    }


    @Override
    public String toString() {
        return navn;
    }



}
