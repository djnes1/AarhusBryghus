package application.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Salg {

    private ArrayList<Salgslinje> salgslinjer = new ArrayList<>();
    private Betalingsmetode betalingsmetode;
    private Person person;
    private Medarbejder medarbejder;
    private Kunde kunde;
    private LocalDate dato;
    private Rabat rabat;
    private Udlejning udlejning;
    private Rundvisning rundvisning;


    public Salg() {

    }

    public Salg(Person person, Betalingsmetode betalingsmetode, LocalDate dato) {
        this.betalingsmetode = betalingsmetode;
        this.person = person;
        this.dato = dato;
    }

    public Salg(Medarbejder medarbejder, Kunde kunde, Betalingsmetode betalingsmetode, LocalDate dato) {
        this.betalingsmetode = betalingsmetode;
        this.medarbejder = medarbejder;
        this.kunde = kunde;
        this.dato = dato;
    }


    public Salgslinje createSalgslinje(PerProduktPris perProduktPris, int antal) {
        Salgslinje salgslinje = new Salgslinje(perProduktPris, antal);
        salgslinjer.add(salgslinje);
        return salgslinje;
    }

    public void removeSalgslinje(Salgslinje salgslinje) {
        if (salgslinjer.contains(salgslinje)) {
            salgslinjer.remove(salgslinje);
        }
    }

    public ArrayList<Salgslinje> getSalgslinjer() {
        return new ArrayList<>(salgslinjer);
    }

    public void addSalgslinje(Salgslinje salgslinje) {
        if (!salgslinjer.contains(salgslinje)) {
            salgslinjer.add(salgslinje);
        }
    }

    public Person getPerson() {
        return person;
    }

    public Person getMedarbejder() {
        return medarbejder;
    }

    public void setMedarbejder(Medarbejder medarbejder) {
        this.medarbejder = medarbejder;
    }


    public Betalingsmetode getBetalingsmetode() {
        return betalingsmetode;
    }

    public void setBetalingsmetode(Betalingsmetode betalingsmetode) {
        this.betalingsmetode = betalingsmetode;
    }


    public LocalDate getDato() {
        return dato;
    }

    public void setDato(LocalDate dato) {
        this.dato = dato;
    }

    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public void betal(Betalingsmetode betalingsmetode) {
        this.betalingsmetode = betalingsmetode;
        this.dato = LocalDate.now();
    }

    public double samletPris() {
        double sum = 0;
        for (Salgslinje s : salgslinjer) {
            sum += s.pris();
        }
        if (rabat != null) {
            return sum * rabat.getProcent() - rabat.getBeløb();
        }
        return sum;
    }

    public int samletPrisKlip() {
        int sum = 0;
        for (Salgslinje s : salgslinjer) {
            sum += s.prisKlip();
        }
        return sum;
    }

    public double getSamletPant() {
        double sum = 0;
        for (Salgslinje salgslinje : this.salgslinjer) {
            sum += salgslinje.getSamletPant();
        }
        return sum;
    }


    public void setPerson(Person person) {
        this.person = person;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public Rabat getRabat() {
        return rabat;
    }

    public void setRabat(Rabat rabat) {
        this.rabat = rabat;
    }

    public Udlejning getUdlejning() {
        return udlejning;
    }

    public void setUdlejning(Udlejning udlejning) {
        this.udlejning = udlejning;
    }

    public Rundvisning getRundvisning() {
        return rundvisning;
    }

    public void setRundvisning(Rundvisning rundvisning) {
        this.rundvisning = rundvisning;
    }

    @Override
    public String toString() {
        return String.format("\n%s\n%s\n%s%n", medarbejder, betalingsmetode, dato);
    }
}

