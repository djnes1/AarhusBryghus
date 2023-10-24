package application.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Rundvisning  {

    private LocalDate rundvisningsDato;
    private LocalTime tidspunkt;
    private int antalPersoner;
    private Person person;
    private Salg salg;

    public Rundvisning(Person person, LocalDate rundvisningsDato, LocalTime tidspunkt, int antalPersoner) {
        this.person = person;
        this.rundvisningsDato = rundvisningsDato;
        this.tidspunkt = tidspunkt;
        this.antalPersoner = antalPersoner;
    }

    public LocalDate getDato() {
        return rundvisningsDato;
    }

    public void setDato(LocalDate rundvisningsDato) {
        this.rundvisningsDato = rundvisningsDato;
    }

    public LocalTime getTidspunkt() {
        return tidspunkt;
    }

    public void setTidspunkt(LocalTime tidspunkt) {
        this.tidspunkt = tidspunkt;
    }

    public int getAntalPersoner() {
        return antalPersoner;
    }

    public void setAntalPersoner(int antalPersoner) {
        if (antalPersoner < 1) {
            throw new IllegalArgumentException("Der skal minimum være én deltager på rundvisningen");
        }
        this.antalPersoner = antalPersoner;
    }

    public void addAntalPersoner(int antalPersoner) {
        if (antalPersoner < 1) {
            throw new IllegalArgumentException("Man skal minimum tilføje én person til rundvisninger");
        }
        this.antalPersoner += antalPersoner;

    }
    public Person getPerson() {
        return this.person;
    }


    public Salg createSalg() {
        return salg = new Salg();
    }

    public Salg getSalg() {
        return this.salg;
    }




    @Override
    public String toString() {
        return String.format("%d",antalPersoner);

    }
}
