package application.model;


import java.util.ArrayList;
import java.util.List;

public class Produkt {

    private String navn;
    private ProduktGruppe produktGruppe;
    private int liter;

    public Produkt(String navn, ProduktGruppe produktGruppe, int liter) {
        this.navn = navn;
        this.setProduktGruppe(produktGruppe);
        this.liter = liter;
    }

    public Produkt(String navn, ProduktGruppe produktGruppe) {
        this.navn = navn;
        this.setProduktGruppe(produktGruppe);
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public ProduktGruppe getProduktGruppe() {
        return produktGruppe;
    }

    public void setProduktGruppe(ProduktGruppe produktGruppe) {
        if (this.produktGruppe != produktGruppe) {
            ProduktGruppe oldProduktGruppe = this.produktGruppe;
            if (oldProduktGruppe != null) {
                oldProduktGruppe.removeProdukt(this);
            }
            this.produktGruppe = produktGruppe;
            if (produktGruppe != null) {
                produktGruppe.addProdukt(this);
            }
        }
    }

    public int getLiter() {
        return liter;
    }

    public void setLiter(int liter) {
        this.liter = liter;
    }

    @Override
    public String toString() {
        return navn + " ";
    }
}
