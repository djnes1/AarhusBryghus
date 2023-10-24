package application.model;

import java.util.ArrayList;
import java.util.List;

public class ProduktGruppe {

    private String navn;
    private Pant pant;
    private List<Produkt> produkter = new ArrayList<>();



    public ProduktGruppe(String navn, Pant pant) {
        this.navn = navn;
        this.pant = pant;


    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public Pant getPant() {
        return pant;
    }


    public double getSamledePant() {
        double sum = 0;
        for (Produkt p: getProdukter()) {
            if (p.getProduktGruppe().equals(this)) {
                sum = this.pant.getPris() * getProdukter().size();
            }
        }
        return sum;
    }

    public List<Produkt> getProdukter() {
        return new ArrayList<>(produkter);
    }



    @Override
    public String toString() {
        return navn;
    }

    public Produkt createProdukt(String navn, int liter) {
        Produkt produkt = new Produkt(navn,this,liter);
        produkter.add(produkt);
        return produkt;
    }

    public Produkt createProdukt(String navn) {
        Produkt produkt = new Produkt(navn,this);
        produkter.add(produkt);
        return produkt;
    }


    public void addProdukt(Produkt produkt) {
        if (!produkter.contains(produkt)) {
            produkter.add(produkt);
            produkt.setProduktGruppe(this);
        }
    }

    public void removeProdukt(Produkt produkt) {
        if (produkter.contains(produkt)) {
            produkter.remove(produkt);
            produkt.setProduktGruppe(null);
        }
    }


}
