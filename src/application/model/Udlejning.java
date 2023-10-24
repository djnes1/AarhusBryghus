
package application.model;

import java.time.LocalDate;

public class Udlejning {


    private Salg afhentningsSalg;
    private Salg afleveringsSalg;
    private Person person;
    private boolean afhentet;
    private boolean afleveret;

    public Udlejning(Person person) {
        this.person = person;
    }

    public Salg getAfhentningsSalg() {
        return this.afhentningsSalg;
    }

    public Salg getAfleveringsSalg() {
        return this.afleveringsSalg;
    }

    public Person getPerson() {
        return this.person;
    }


    public boolean isAfhentet() {
        return this.afhentet;
    }

    public void afhent(Betalingsmetode betalingsmetode, LocalDate dato) {
        if (this.afhentningsSalg == null) {
            throw new NullPointerException("Et afhentningssalg skal være oprettet for at kunne afhente.");
        }

        this.afhentningsSalg.betal(betalingsmetode);
        this.afhentet = true;
        this.afhentningsSalg.setDato(dato);
        this.createAfleveringsSalg();
    }

    public boolean isAfleveret() {
        return this.afleveret;
    }

    public void aflever(Betalingsmetode betalingsmetode) {
        if (this.afleveringsSalg == null) {
            throw new NullPointerException("Et afleveringssalg skal være oprettet for at kunne aflevere.");
        }
        this.afleveringsSalg.betal(betalingsmetode);
        this.afleveret = true;
    }

    public Salg createAfhentningsSalg() {
        this.afhentningsSalg = new Salg();
        return this.afhentningsSalg;
    }

    public Salg createAfleveringsSalg() {
        if (this.afhentningsSalg == null) {
            throw new NullPointerException("Der kan ikke oprettes afleveringssalg, når afhentningssalget endnu ikke er oprettet.");
        } else if (!this.afhentet) {
            throw new NullPointerException("Der kan ikke laves et afleveringssalg, da udlejning endnu ikke er blevet afhentet");
        }
        this.afleveringsSalg = new Salg();
        return this.afleveringsSalg;
    }

    public double getTilbagebetaling() {
        return this.afleveringsSalg.samletPris();
    }

    public double getBetalingVedAflevering() {
        return this.getSamletPris() - this.getTilbagebetaling();
    }

    public double getSamletPant() {
        return this.afhentningsSalg.getSamletPant();
    }


    public double getSamletPris() {
        return this.afhentningsSalg.samletPris();
    }

    @Override
    public String toString() {

        return String.format("Lejet af: %s\n", getPerson().getNavn()) ;
    }
}