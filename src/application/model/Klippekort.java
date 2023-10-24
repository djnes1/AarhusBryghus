package application.model;

public class Klippekort extends Produkt {

    private int antalKlip = 4;

    public Klippekort(String navn, ProduktGruppe produktGruppe) {
        super(navn, produktGruppe);
    }

    public int getAntalKlip() {
        return antalKlip;
    }

    public void setAntalKlip(int antalKlip) {
        this.antalKlip = antalKlip;
    }

    public void benytKlippekort(int antal) {
        if (this.antalKlip < antal) {
            throw new IllegalArgumentException("Du har ikke nok klip på kortet");
        } else {
            this.antalKlip -= antal;
        }
    }

    @Override
    public String toString() {
        return super.toString() + String.format("%d ", antalKlip);
    }
}
