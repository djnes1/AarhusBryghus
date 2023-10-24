package application.model;


import java.util.ArrayList;

public abstract class Person {

    private String navn;
    private String adresse;
    private String tlfnr;
    private String cpr;
    private String email;
    private ArrayList<Udlejning> udlejninger = new ArrayList<>();
    private ArrayList<Rundvisning> rundvisninger = new ArrayList<>();
    private ArrayList<Salg> salgArrayList = new ArrayList<>();


    public Person(String navn, String adresse, String tlfnr, String cpr, String email) {
        this.navn = navn;
        this.adresse = adresse;
        this.tlfnr = tlfnr;
        this.cpr = cpr;
        this.email = email;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTlfnr() {
        return tlfnr;
    }

    public void setTlfnr(String tlfnr) {
        this.tlfnr = tlfnr;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public ArrayList<Udlejning> getUdlejninger() {
        return new ArrayList<>(udlejninger);
    }

    public ArrayList<Rundvisning> getRundvisninger() {
        return new ArrayList<>(rundvisninger);
    }

    public ArrayList<Salg> getSalgArrayList() {
        return new ArrayList<>(salgArrayList);
    }

    public void addSalg(Salg salg) {
        if (!salgArrayList.contains(salg)) {
            salgArrayList.add(salg);
        }
    }

    public void removeSalg(Salg salg) {
        if (salgArrayList.contains(salg)) {
            salgArrayList.remove(salg);
        }
    }

    public void addUdlejning(Udlejning udlejning) {
        if (!udlejninger.contains(udlejning)) {
            udlejninger.add(udlejning);
        }
    }

    public void removeUdlejning(Udlejning udlejning) {
        if (udlejninger.contains(udlejning)) {
            udlejninger.remove(udlejning);
        }
    }

    public void addRundvisning(Rundvisning rundvisning) {
        if (!rundvisninger.contains(rundvisning)) {
            rundvisninger.add(rundvisning);
        }
    }

    public void removeRundvisning(Rundvisning rundvisning) {
        if (rundvisninger.contains(rundvisning));
        rundvisninger.remove(rundvisning);
    }

    @Override
    public String toString() {
        return String.format("%s \n%s \n%s \n%s \n%s",navn,adresse,tlfnr,cpr,email);
    }
}
