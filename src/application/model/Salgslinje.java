package application.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Salgslinje {

    private PerProduktPris perProduktPris;
    private int antal;


    public Salgslinje(PerProduktPris perProduktPris, int antal) {
        this.perProduktPris = perProduktPris;
        this.antal = antal;
    }

    public PerProduktPris getPerProduktPris() {
        return perProduktPris;
    }

    public void setPerProduktPris(PerProduktPris perProduktPris) {
        this.perProduktPris = perProduktPris;
    }

    public int getAntal() {
        return antal;
    }

    public void setAntal(int antal) {
        this.antal = antal;
    }


    public double pris(){
        return perProduktPris.getPris()*antal;
    }

    public int prisKlip(){
        return perProduktPris.getKlip()*antal;
    }

    public double getSamletPant() {
        return this.perProduktPris.getProdukt().getProduktGruppe().getPant().getPris() * this.antal;
    }

    @Override
    public String toString() {
        return perProduktPris.getProdukt().getNavn();
    }
}























/*
    public double samletPrisRabat(Salgssituation salgssituation) {
        double sum = 0;
        for (PerProduktPris p: salgssituation.getPerProduktPriser()) {
            sum += p.getPris() * antal;
        }
        return sum - rabat.rabatBeloeb(sum) ;
    }

    public double samletKoeb(Salgssituation salgssituation) {
        double sum = 0;
        for (PerProduktPris p: salgssituation.getPerProduktPriser()) {
            sum += p.getPris() * antal;
        }
        return sum;
    }*/
