package storage;

import application.model.*;

import java.util.ArrayList;

public interface StorageInterface {
    //Singleton

    ArrayList<Salgslinje> getSalgslinjes();


    ArrayList<ProduktGruppe> getProduktGrupper();

    ArrayList<Produkt> getProdukter();

    ArrayList<Salgssituation> getSalgssituationer();

    ArrayList<PerProduktPris> getPerProduktPriser();

    ArrayList<Person> getPersoner();

    ArrayList<Salg> getSalgArrayList();

    void addRundvisning(Rundvisning rundvisning);

    void removeRundvisning(Rundvisning rundvisning);

    public ArrayList<Rundvisning> getRundvisningArrayList();
    ArrayList<Udlejning> getUdlejninger();



    void addSalg(Salg salg);

    void removeSalg(Salg salg);

    void addProduktGruppe(ProduktGruppe produktGruppe);

    void addProdukt(Produkt produkt);

    void addSalgssituation(Salgssituation salgssituation);

    void addPerProduktPris(PerProduktPris perProduktPris);

    void addPerson(Person person);

    void addUdlejning(Udlejning udlejning);


    void removeUdlejning(Udlejning udlejning);
    void removeProdukt(Produkt produkt);



    void removePerProduktPris(PerProduktPris perProduktPris);

    void removeSalgssituation(Salgssituation salgssituation);

    void removePerson(Person person);
}