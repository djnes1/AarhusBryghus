package storage;

import application.model.*;

import java.util.ArrayList;

public class Storage implements StorageInterface {


    private ArrayList<ProduktGruppe> produktGrupper = new ArrayList<>();
    private ArrayList<Produkt> produkter = new ArrayList<>();
    private ArrayList<Salgssituation> salgssituationer = new ArrayList<>();
    private ArrayList<PerProduktPris> perProduktPriser = new ArrayList<>();
    private ArrayList<Person> personer = new ArrayList<>();
    private ArrayList<Udlejning> udlejninger = new ArrayList<>();
    private ArrayList<Salg> salgArrayList = new ArrayList<>();
    private ArrayList<Salgslinje> salgslinjes = new ArrayList<>();
    private ArrayList<Rundvisning> rundvisningArrayList = new ArrayList<>();

    private static Storage uniqueInstance;


    private Storage() {
    }

    public static Storage getUniqueInstance() {
        if (Storage.uniqueInstance == null) {
            Storage.uniqueInstance = new Storage();
        }
        return Storage.uniqueInstance;
    }

    public ArrayList<Udlejning> getUdlejninger() {
        return new ArrayList<>(udlejninger);
    }

    public ArrayList<Rundvisning> getRundvisningArrayList() {
        return new ArrayList<>(rundvisningArrayList);
    }

    @Override
    public void addRundvisning(Rundvisning rundvisning) {
        rundvisningArrayList.add(rundvisning);
    }

    @Override
    public void removeRundvisning(Rundvisning rundvisning) {
        rundvisningArrayList.remove(rundvisning);
    }


    @Override
    public ArrayList<Salgslinje> getSalgslinjes() {
        return new ArrayList<>(salgslinjes);
    }

    @Override
    public ArrayList<ProduktGruppe> getProduktGrupper() {
        return new ArrayList<ProduktGruppe>(produktGrupper);
    }

    @Override
    public ArrayList<Produkt> getProdukter() {
        return new ArrayList<Produkt>(produkter);
    }

    @Override
    public ArrayList<Salgssituation> getSalgssituationer() {
        return new ArrayList<Salgssituation>(salgssituationer);
    }

    @Override
    public ArrayList<PerProduktPris> getPerProduktPriser() {
        return new ArrayList<PerProduktPris>(perProduktPriser);
    }

    @Override
    public ArrayList<Person> getPersoner() {
        return new ArrayList<Person>(personer);
    }


    @Override
    public ArrayList<Salg> getSalgArrayList() {
        return new ArrayList<>(salgArrayList);
    }

    @Override
    public void addSalg(Salg salg) {
        salgArrayList.add(salg);
    }

    @Override
    public void removeSalg(Salg salg) {
        salgArrayList.remove(salg);
    }

    @Override
    public void addProduktGruppe(ProduktGruppe produktGruppe) {
        produktGrupper.add(produktGruppe);
    }

    @Override
    public void addProdukt(Produkt produkt) {
        produkter.add(produkt);
    }

    @Override
    public void addSalgssituation(Salgssituation salgssituation) {
        salgssituationer.add(salgssituation);
    }

    @Override
    public void addPerProduktPris(PerProduktPris perProduktPris) {
        perProduktPriser.add(perProduktPris);
    }

    @Override
    public void addUdlejning(Udlejning udlejning) {
        udlejninger.add(udlejning);
    }

    @Override
    public void addPerson(Person person) {
        personer.add(person);
    }


    @Override
    public void removeProdukt(Produkt produkt) {
        produkter.remove(produkt);
    }

    @Override
    public void removeUdlejning(Udlejning udlejning) {
        udlejninger.remove(udlejning);
    }

    @Override
    public void removePerProduktPris(PerProduktPris perProduktPris) {
        perProduktPriser.remove(perProduktPris);
    }

    @Override
    public void removeSalgssituation(Salgssituation salgssituation) {
        salgssituationer.remove(salgssituation);
    }

    @Override
    public void removePerson(Person person) {
        personer.remove(person);
    }
}