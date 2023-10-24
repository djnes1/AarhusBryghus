package application.controller;


import application.model.*;
import storage.Storage;
import storage.StorageInterface;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Controller {


    private static StorageInterface storage;
    private static Controller uniqueInstance;
    private Salg aktueltSalg;

    public void setAktueltSalg(Salg aktueltSalg) {
        this.aktueltSalg = aktueltSalg;
    }


    public Salg getAktueltSalg() {
        return aktueltSalg;
    }
//------------------------------------------------------

    public Controller(StorageInterface storage) {
        this.storage = storage;
    }

    public void setStorage(Storage storage) {
        Controller.storage = storage;
    }

    public static Controller getUniqueInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Controller(Storage.getUniqueInstance());
        }
        return uniqueInstance;
    }


//------------------------------------------------------


    /**
     * Opret produkt
     *
     * @param navn
     * @param produktGruppe not null
     * @return Produkt
     * @throws IllegalArgumentException hvis produkt allerede findes i produktgruppen
     */

    public Produkt createProdukt(String navn, ProduktGruppe produktGruppe) {
        for (Produkt produkt : produktGruppe.getProdukter()) {
            if (produkt.getNavn().equals(navn)) {
                throw new IllegalArgumentException("Produkt findes allerede i produktgruppe.");
            }
        }
        Produkt produkt = new Produkt(navn, produktGruppe);
        storage.addProdukt(produkt);
        return produkt;
    }


    /**
     * @param navn
     * @param produktGruppe not null
     * @param liter
     * @return Produkt med mængde
     * @throws IllegalArgumentException hvis produkt allerede findes i produktgruppen
     */

    public Produkt createProdukt(String navn, ProduktGruppe produktGruppe, int liter) {
        for (Produkt produkt : produktGruppe.getProdukter()) {
            if (produkt.getNavn().equals(navn)) {
                throw new IllegalArgumentException("Produkt findes allerede i produktgruppe.");
            }
        }
        Produkt produkt = new Produkt(navn, produktGruppe, liter);
        storage.addProdukt(produkt);
        return produkt;
    }

    /**
     * @param navn
     * @param produktGruppe not null
     * @return Klippekort
     */
    public Klippekort createKlippekort(String navn, ProduktGruppe produktGruppe) {
        Klippekort klippekort = new Klippekort(navn, produktGruppe);
        klippekort.setAntalKlip(4);
        storage.addProdukt(klippekort);
        return klippekort;
    }


    public ArrayList<Produkt> getProdukter() {
        return storage.getProdukter();
    }


    // -------------------------------------------------

    /**
     * Opret produkgruppe
     *
     * @param navn på produktgruppe
     * @param pant
     * @return produkgruppe
     * @throws IllegalArgumentException hvis produktgruppe allerede findes i systemet
     */

    public ProduktGruppe createProduktGruppe(String navn, Pant pant) {
        for (ProduktGruppe p : this.getProduktGrupper()) {
            if (p.getNavn().equals(navn)) {
                throw new IllegalArgumentException("Produktgruppe eksisterer allerede");
            }
        }
        ProduktGruppe produktGruppe = new ProduktGruppe(navn, pant);
        storage.addProduktGruppe(produktGruppe);
        return produktGruppe;
    }


    public ArrayList<ProduktGruppe> getProduktGrupper() {
        return storage.getProduktGrupper();
    }


    // -----------------------------

    /**
     * @param navn
     * @return salgssituation
     * @throws IllegalArgumentException hvis salgssituation allerede findes i systemet
     */

    public Salgssituation createSalgssituation(String navn) {
        for (Salgssituation p : this.getSalgssituationer()) {
            if (p.getNavn().equals(navn)) {
                throw new IllegalArgumentException("Salgssituation er allerede oprettet");
            }
        }
        Salgssituation salgssituation = new Salgssituation(navn);
        storage.addSalgssituation(salgssituation);
        return salgssituation;
    }

    public ArrayList<Salgssituation> getSalgssituationer() {
        return storage.getSalgssituationer();
    }


    //------------------------------------------------------


    //------------------------------------------------------

    /**
     * Opret administrator
     *
     * @param navn
     * @param adresse
     * @param tlfnr
     * @param cpr
     * @param email
     * @param brugernavn
     * @param password
     * @param befoejelser hvad en administrator har adgang til at gøre
     * @return Ny administrator
     * @throws IllegalArgumentException hvis administrator allerede findes i systemet
     */

    public Person createAdministrator(String navn, String adresse, String tlfnr, String cpr, String email, String brugernavn, String password, String befoejelser) {
        if (this.findMedarbejder(brugernavn, password, true)) {
            throw new IllegalArgumentException("Administrator eksisterer allerede");
        }
        Person admin = new Administrator(navn, adresse, tlfnr, cpr, email, brugernavn, password, befoejelser);
        storage.addPerson(admin);
        return admin;
    }

    public ArrayList<Rundvisning> getRundvisninger() {
        return storage.getRundvisningArrayList();
    }
    // -----------------------------------------------------

    /**
     * Opret medarbejder
     *
     * @param navn
     * @param adresse
     * @param tlfnr
     * @param cpr
     * @param email
     * @param brugernavn
     * @param password
     * @return Ny medarbejder
     * @throws IllegalArgumentException hvis medarbejder allerede findes i systemet
     */

    public Person createMedarbejder(String navn, String adresse, String tlfnr, String cpr, String email, String brugernavn, String password) {
        if (this.findMedarbejder(brugernavn, password, false)) {
            throw new IllegalArgumentException("Medarbejder eksisterer allerede");
        }
        Person medarbejder = new Medarbejder(navn, adresse, tlfnr, cpr, email, brugernavn, password);
        storage.addPerson(medarbejder);
        return medarbejder;
    }

    /**
     * @param navn
     * @param adresse
     * @param tlfnr
     * @param cpr
     * @param email
     * @throws IllegalArgumentException hvis kunde allerede findes i systemet
     */
    public Person createKunde(String navn, String adresse, String tlfnr, String cpr, String email) {
        Person kunde = new Kunde(navn, adresse, tlfnr, cpr, email);
        storage.addPerson(kunde);
        return kunde;
    }

    public ArrayList<Person> getPersoner() {
        return storage.getPersoner();
    }


    /**
     * @param person          not null
     * @param betalingsmetode not null
     * @param dato
     * @return Salg
     */

    public Salg createSalg(Person person, Betalingsmetode betalingsmetode, LocalDate dato) {
        Salg salg = new Salg(person, betalingsmetode, dato);
        storage.addSalg(salg);
        return salg;
    }


    public ArrayList<Salg> getSalg() {
        return storage.getSalgArrayList();
    }


    //--------------------------------------------  -----------------------------------------------------------


    /**
     * @param person
     * @return
     */
    public Udlejning createUdlejning(Person person) {
        Udlejning udlejning = new Udlejning(person);
        storage.addUdlejning(udlejning);
        return udlejning;
    }

    public ArrayList<Udlejning> getUdlejninger() {
        return storage.getUdlejninger();
    }
    //-----


    /**
     * @param person
     * @return
     */
    public Rundvisning createRundvisning(Person person, LocalDate rundvisningsDato, LocalTime tidspunkt, int antalPersoner) {
        Rundvisning rundvisning = new Rundvisning(person, rundvisningsDato, tidspunkt, antalPersoner);
        storage.addRundvisning(rundvisning);
        return rundvisning;
    }

    /**
     * Finder medarbejder i system
     *
     * @param brugernavn
     * @param password
     * @param admin
     * @return true/false
     * @pre brugernavn, password NOT NULL
     */

    public boolean findMedarbejder(String brugernavn, String password, boolean admin) {
        for (Person m : this.getPersoner()) {
            if (m instanceof Medarbejder) {
                if (((Medarbejder) m).getBrugernavn().equals(brugernavn)) {
                    if (((Medarbejder) m).getPassword().equals(password)) {
                        if (admin) {
                            if (m instanceof Administrator) {
                                return true;
                            }
                        } else {
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }


    //------------------------  -------------------------------

    /**
     * @param dato
     * @return Returnerer en liste af produkter, som er blevet solgt på den angivne dato.
     * Listen indholder også, for hvert produkt, navn, antal, produktpris,
     * salgslinjens samlede køb og den anvendte betalingsmetode ved køb
     */
    public ArrayList<String> dagensSalg(LocalDate dato) {
        double sum = 0;
        ArrayList<String> result = new ArrayList<>();
        for (Salg s : getSalg()) {
            if (s.getDato().equals(dato)) {
                for (Salgslinje sa : s.getSalgslinjer()) {
                    result.add(sa.getAntal() + "   x   " + sa.getPerProduktPris().getProdukt() +
                            "  " + sa.getPerProduktPris().getPris() + " " + sa.pris() + " " + s.getBetalingsmetode());
                    sum += sa.pris();
                }
            }
        }
        result.add("\nSamlet salg:\n" + sum + "\n");
        return result;
    }

    /**
     * @param start
     * @param slut
     * @return returnerer antallet af solgte klippekort og returnerer sum af kortenes klip
     * @throws IllegalArgumentException hvis startdato er efter slutdato
     */
    public int antalSolgteKlip(LocalDate start, LocalDate slut) {
        int sum = 0;
        if (start.isAfter(slut)) {
            throw new IllegalArgumentException("Startdato kan ikke være efter slutdato.");
        } else {
            for (Salg salg : storage.getSalgArrayList()) {
                if (!start.isAfter(salg.getDato()) && !slut.isBefore(salg.getDato())) {
                    for (Salgslinje salgslinje : salg.getSalgslinjer()) {
                        if (salgslinje.getPerProduktPris().getProdukt() instanceof Klippekort) {
                            sum += salgslinje.getAntal()
                                    * ((Klippekort) salgslinje.getPerProduktPris().getProdukt()).getAntalKlip();
                        }
                    }
                }
            }
        }

        return sum;
    }


    /**
     * @param start
     * @param slut
     * @return Antal brugte klip, hvor betalingsmetode har været KLIPPEKORT
     * @throws IllegalArgumentException hvis startdato er efter slutdato
     */
    public int antalBrugteKlip(LocalDate start, LocalDate slut) {
        int sum = 0;
        for (Salg salg : getSalg()) {
            if (slut.isAfter(salg.getDato()) && start.isBefore(salg.getDato())) {
                throw new IllegalArgumentException("Start skal være før slut");
            }
            if (salg.getBetalingsmetode().equals(Betalingsmetode.KLIPPEKORT)) {
                for (Salgslinje salgslinje : salg.getSalgslinjer()) {
                    sum += salgslinje.getAntal() * salgslinje.getPerProduktPris().getKlip();
                }
            }
        }
        return sum;
    }

    /**
     * @return en liste over udlejninger som er blevet afhentet, men ikke afleveret
     */

    public ArrayList<Udlejning> ikkeAfleveredeUdlejninger() {
        ArrayList<Udlejning> result = new ArrayList<>();
        for (Udlejning udlejning : this.getUdlejninger()) {
            if (udlejning.isAfhentet() && !udlejning.isAfleveret()) {
                result.add(udlejning);
            }
        }

        return result;
    }

    public void writeReceiptToFile(Salg salg) {
        String dato = String.format("------------- %s --------------", salg.getDato());
        String tak = "---------------------------------------" +
                "\nTak fordi du handlede hos Aarhus Bryghus";
        try (BufferedWriter bw = new BufferedWriter(new PrintWriter("kvittering.txt"))) {
            String aarhus = "\t\t\tAARHUS BRYGHUS";
            bw.write(dato);
            bw.newLine();
            bw.write(aarhus);
            bw.newLine();
            for (Salgslinje s : salg.getSalgslinjer()) {
                String produkt = String.format("%s%n%3d x %s", s.getPerProduktPris().getProdukt().getNavn().toUpperCase(),
                        s.getAntal(), s.getPerProduktPris().getProdukt());
                bw.newLine();
                String rabat = String.format("RABAT: %31s ", salg.getRabat() + "-");
                if (salg.getRabat() == null) {
                    bw.write(produkt + "\n");
                } else {
                    bw.write(produkt + "\n" + rabat);
                }
            }
            bw.newLine();
            String pris = String.format("\n\t\t\tTOTAL: %1.2f\n", salg.samletPris());
        bw.write(pris);
        bw.newLine();
        bw.write("\t\tBetalt med:  " + salg.getBetalingsmetode());
        bw.newLine();
        bw.write(tak);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public ArrayList<PerProduktPris> getProdukterFraProduktGruppe(ProduktGruppe produktGruppe) {
        ArrayList<PerProduktPris> result = new ArrayList<>();
        Salgssituation webshop = null;
        // Finder først webshop-objektet
        for (Salgssituation s : storage.getSalgssituationer()) {
            if (s.getNavn().equalsIgnoreCase("Webshop")) {
                webshop = s;
            }
        }

        for (PerProduktPris ppp : webshop.getPerProduktPriser()) {
            if (ppp.getProdukt().getProduktGruppe() == produktGruppe) {
                result.add(ppp);
            }
        }
        return result;
    }

    public ArrayList<ProduktGruppe> getProduktGrupperFraWebshop() {
        Salgssituation butik = null;
        // Finder først webshop-objektet
        for (Salgssituation s : storage.getSalgssituationer()) {
            if (s.getNavn().equalsIgnoreCase("Webshop")) {
                butik = s;
            }
        }
        ArrayList<ProduktGruppe> result = new ArrayList<>();

        // Og finder derefter alle produktgrupper som hører til
        for (PerProduktPris ppp : butik.getPerProduktPriser()) {
            if (!result.contains(ppp.getProdukt().getProduktGruppe())) {
                result.add(ppp.getProdukt().getProduktGruppe());
            }
        }
        return result;
    }

    public Salg addSalg(Salg salg) {
        storage.addSalg(salg);
        return salg;
    }

    // ---- MedarbejderGUI
    public PerProduktPris createPerProdukt(double pris, Produkt produkt, Salgssituation salgssituation) {
        PerProduktPris perProduktPris = new PerProduktPris(pris, produkt, salgssituation);
        storage.addPerProduktPris(perProduktPris);
        return perProduktPris;
    }

    public PerProduktPris createPerProdukt(double pris, int klip, Produkt produkt, Salgssituation salgssituation) {
        PerProduktPris perProduktPris = new PerProduktPris(pris, klip, produkt, salgssituation);
        storage.addPerProduktPris(perProduktPris);
        return perProduktPris;
    }

    public void initStorage() {


        /*********** ADMIN ***************/
        Person administrator = this.createAdministrator("Jones", "Nordre Ringgade 47",
                "20202020", "", "admin@gmail.com", "admin",
                "admin", "Opret produkt, produktgruppe og adgang til statistik");


//        /*********** MEDARBEJDER ***************/
        Person medarbejder = this.createMedarbejder("Jack Black",
                "Vestergade 58", "23456789", "121290-1212", "jackblack@gmail.com",
                "john", "10");

        /************* PRODUKTGRUPPE *************/
        ProduktGruppe flaske = this.createProduktGruppe("Flaske", Pant.A);
        ProduktGruppe fadoel = this.createProduktGruppe("Fadøl - 40cl", Pant.INGENPANT);
        ProduktGruppe spiritus = this.createProduktGruppe("Spiritus", Pant.B);
        ProduktGruppe fustage = this.createProduktGruppe("Fustage", Pant.FUSTAGE);
        ProduktGruppe kulsyre = this.createProduktGruppe("Kulsyre", Pant.INGENPANT);
        ProduktGruppe malt = this.createProduktGruppe("Malt", Pant.INGENPANT);
        ProduktGruppe beklaedning = this.createProduktGruppe("Beklædning", Pant.INGENPANT);
        ProduktGruppe anlaeg = this.createProduktGruppe("Anlæg", Pant.INGENPANT);
        ProduktGruppe glas = this.createProduktGruppe("Glas", Pant.INGENPANT);
        ProduktGruppe sampakning = this.createProduktGruppe("Sampakninger", Pant.INGENPANT);


//        /************** Salgssituation ***************/
        Salgssituation fredagsbar = this.createSalgssituation("Fredagsbar");
        Salgssituation webshop = this.createSalgssituation("Webshop");


//        /************** KLIPPEKORT  ********** */
        Produkt klippekort = this.createKlippekort("Klippekort", flaske);


        /************** FLASKE ***************/

        Produkt klosterbrygFlaske = this.createProdukt("Klosterbryg", flaske);
        Produkt sweetGeorgiaFlaske = this.createProdukt("Sweet Georgia Brown", flaske);
        Produkt extraPilsnerFlaske = this.createProdukt("Extra Pilsner", flaske);
        Produkt celebration = this.createProdukt("Celebration", flaske);
        Produkt blondie = this.createProdukt("Blondie", flaske);
        Produkt foraarsbryg = this.createProdukt("Forårsbryg", flaske);
        Produkt ipa = this.createProdukt("India Pale Ale", flaske);
        Produkt julebryg = this.createProdukt("Julebryg", flaske);
        Produkt juletoende = this.createProdukt("Juletønde", flaske);
        Produkt oldstrongale = this.createProdukt("Old Strong Ale", flaske);
        Produkt fregatten = this.createProdukt("Fregatten Jylland", flaske);
        Produkt impstout = this.createProdukt("Imperial Stout", flaske);
        Produkt tribute = this.createProdukt("Tribute", flaske);
        Produkt blackmonster = this.createProdukt("Black Monster", flaske);
        webshop.createPerProduktPris(40, 4, extraPilsnerFlaske);
        fredagsbar.createPerProduktPris(70, 2, klosterbrygFlaske);
        webshop.createPerProduktPris(36, klosterbrygFlaske);
        fredagsbar.createPerProduktPris(70, 2, sweetGeorgiaFlaske);
        webshop.createPerProduktPris(36, sweetGeorgiaFlaske);
        fredagsbar.createPerProduktPris(70, 2, extraPilsnerFlaske);
        webshop.createPerProduktPris(36, extraPilsnerFlaske);
        fredagsbar.createPerProduktPris(70, 2, celebration);
        webshop.createPerProduktPris(36, celebration);
        fredagsbar.createPerProduktPris(70, 2, blondie);
        webshop.createPerProduktPris(36, blondie);
        fredagsbar.createPerProduktPris(70, 2, foraarsbryg);
        webshop.createPerProduktPris(36, foraarsbryg);
        fredagsbar.createPerProduktPris(70, 2, ipa);
        webshop.createPerProduktPris(36, ipa);
        fredagsbar.createPerProduktPris(70, 2, julebryg);
        webshop.createPerProduktPris(36, julebryg);
        fredagsbar.createPerProduktPris(70, 2, juletoende);
        webshop.createPerProduktPris(36, juletoende);
        fredagsbar.createPerProduktPris(70, 2, oldstrongale);
        webshop.createPerProduktPris(36, oldstrongale);
        fredagsbar.createPerProduktPris(70, 2, fregatten);
        webshop.createPerProduktPris(36, fregatten);
        fredagsbar.createPerProduktPris(70, 2, impstout);
        webshop.createPerProduktPris(36, impstout);
        fredagsbar.createPerProduktPris(70, 2, tribute);
        webshop.createPerProduktPris(36, tribute);
        fredagsbar.createPerProduktPris(100, 3, blackmonster);
        webshop.createPerProduktPris(60, blackmonster);


        /*************** FADOEL **************/

        Produkt klosterbrygFad = this.createProdukt("Klosterbryg", fadoel);
        Produkt jazzclassicFad = this.createProdukt("Jazz Classic", fadoel);
        Produkt extrapilsFad = this.createProdukt("Extra Pilsner", fadoel);
        Produkt celebFad = this.createProdukt("Celebration", fadoel);
        Produkt blondieFad = this.createProdukt("Blondie", fadoel);
        Produkt foraarFad = this.createProdukt("Forårsbryg", fadoel);
        Produkt ipaFad = this.createProdukt("IPA", fadoel);
        Produkt julFad = this.createProdukt("Julebryg", fadoel);
        Produkt impstoutFad = this.createProdukt("Imperial Stout", fadoel);
        Produkt special = this.createProdukt("Special", fadoel);
        fredagsbar.createPerProduktPris(38, 1, klosterbrygFad);
        fredagsbar.createPerProduktPris(38, 1, jazzclassicFad);
        fredagsbar.createPerProduktPris(38, 1, extrapilsFad);
        fredagsbar.createPerProduktPris(38, 1, celebFad);
        fredagsbar.createPerProduktPris(38, 1, blondieFad);
        fredagsbar.createPerProduktPris(38, 1, foraarFad);
        fredagsbar.createPerProduktPris(38, 1, ipaFad);
        fredagsbar.createPerProduktPris(38, 1, julFad);
        fredagsbar.createPerProduktPris(38, 1, impstoutFad);
        fredagsbar.createPerProduktPris(38, 1, special);
        Produkt aeblebrus = this.createProdukt("Æblebrus", fadoel);
        Produkt chips = this.createProdukt("Chips", fadoel);
        Produkt peanuts = this.createProdukt("Peanuts", fadoel);
        Produkt cola = this.createProdukt("Cola", fadoel);
        Produkt nikoline = this.createProdukt("Nikoline", fadoel);
        Produkt sevenup = this.createProdukt("7-Up", fadoel);
        Produkt vand = this.createProdukt("Vand", fadoel);
        Produkt oelpoelser = this.createProdukt("Ølpølser", fadoel);
        fredagsbar.createPerProduktPris(15, aeblebrus);
        fredagsbar.createPerProduktPris(15, peanuts);
        fredagsbar.createPerProduktPris(15, chips);
        fredagsbar.createPerProduktPris(15, cola);
        fredagsbar.createPerProduktPris(15, nikoline);
        fredagsbar.createPerProduktPris(15, sevenup);
        fredagsbar.createPerProduktPris(10, chips);
        fredagsbar.createPerProduktPris(10, vand);
        fredagsbar.createPerProduktPris(30, 1, oelpoelser);

        /**************************** SPIRITUS ******************/

        Produkt whiskey45 = this.createProdukt("Whiskey 45%, 50 cl. rør", spiritus);
        Produkt whiskey4cl = this.createProdukt("Whiskey 4 cl.", spiritus);
        Produkt whiskey43 = this.createProdukt("Whiskey 43%, 50 cl. rør", spiritus);
        Produkt uEgesplint = this.createProdukt("u/ egesplint", spiritus);
        Produkt mEgesplint = this.createProdukt("m/ egesplint", spiritus);
        Produkt whiskeyGlas_2_gange = this.createProdukt("2 * Whiskey glas + brikker", spiritus);
        Produkt liquorOfAarhus = this.createProdukt("Liquor of Aarhus", spiritus);
        Produkt lyngGin_50_cl = this.createProdukt("Lyng Gin, 50 cl", spiritus);
        Produkt lyngGin_4_cl = this.createProdukt("Lyng Gin, 4 cl", spiritus);
        fredagsbar.createPerProduktPris(599, whiskey45);
        webshop.createPerProduktPris(599, whiskey45);
        fredagsbar.createPerProduktPris(50, whiskey4cl);
        fredagsbar.createPerProduktPris(499, whiskey43);
        webshop.createPerProduktPris(499, whiskey43);
        fredagsbar.createPerProduktPris(300, uEgesplint);
        webshop.createPerProduktPris(300, uEgesplint);
        fredagsbar.createPerProduktPris(350, mEgesplint);
        webshop.createPerProduktPris(350, mEgesplint);
        fredagsbar.createPerProduktPris(80, whiskeyGlas_2_gange);
        webshop.createPerProduktPris(80, whiskeyGlas_2_gange);
        fredagsbar.createPerProduktPris(175, liquorOfAarhus);
        webshop.createPerProduktPris(175, liquorOfAarhus);
        fredagsbar.createPerProduktPris(350, lyngGin_50_cl);
        webshop.createPerProduktPris(350, lyngGin_50_cl);
        fredagsbar.createPerProduktPris(40, lyngGin_4_cl);


        /*********************** FUSTAGE  ************/

        Produkt kloster20F = this.createProdukt("Klosterbryg ", fustage, 20);
        Produkt jazzClassicF = this.createProdukt("Jazz Classic, 25l", fustage, 25);
        Produkt extrapilsF = this.createProdukt("Extra Pilsner, 25l", fustage, 25);
        Produkt celebF = this.createProdukt("Celebration, 20l", fustage, 20);
        Produkt blondieF = this.createProdukt("Blondie, 25l", fustage, 25);
        Produkt foraarF = this.createProdukt("Forårsbryg, 20l", fustage, 20);
        Produkt ipaF = this.createProdukt("India Pale Ale, 20l", fustage, 20);
        Produkt juleF = this.createProdukt("Julebryg, 20l", fustage, 20);
        Produkt impStoutFustage = this.createProdukt("Imperial Stout, 20l", fustage, 20);
        webshop.createPerProduktPris(777, kloster20F);
        webshop.createPerProduktPris(625, jazzClassicF);
        webshop.createPerProduktPris(575, extrapilsF);
        webshop.createPerProduktPris(775, celebF);
        webshop.createPerProduktPris(700, blondieF);
        webshop.createPerProduktPris(775, foraarF);
        webshop.createPerProduktPris(775, ipaF);
        webshop.createPerProduktPris(775, juleF);
        webshop.createPerProduktPris(775, impStoutFustage);


        /****** KULSYRE *****/


        Produkt kul_6kg = this.createProdukt("6 kg", kulsyre);
        Produkt pantKul = this.createProdukt("Pant", kulsyre);
        Produkt kul_4kg = this.createProdukt("4 kg", kulsyre);
        Produkt kul_10kg = this.createProdukt("10 kg", kulsyre);
        fredagsbar.createPerProduktPris(400, kul_6kg);
        webshop.createPerProduktPris(400, kul_6kg);
        fredagsbar.createPerProduktPris(1000, pantKul);
        webshop.createPerProduktPris(1000, pantKul);
        webshop.createPerProduktPris(450, kul_4kg);
        webshop.createPerProduktPris(1000, kul_10kg);


        /************* MALT ************/


        Produkt maltProdukt = this.createProdukt("25 kg sæk", malt);
        webshop.createPerProduktPris(300, maltProdukt);


        /************** BEKLÆDNING ****************/


        Produkt t_shirt = this.createProdukt("T-shirt", beklaedning);
        Produkt polo = this.createProdukt("Polo", beklaedning);
        Produkt cap = this.createProdukt("Cap", beklaedning);
        fredagsbar.createPerProduktPris(70, t_shirt);
        webshop.createPerProduktPris(70, t_shirt);
        fredagsbar.createPerProduktPris(100, polo);
        webshop.createPerProduktPris(100, polo);
        fredagsbar.createPerProduktPris(30, cap);
        webshop.createPerProduktPris(30, cap);


        /************** ANLÆG ****************/

        Produkt hane_1 = this.createProdukt("1-hane", anlaeg);
        Produkt hane_2 = this.createProdukt("2-haner", anlaeg);
        Produkt barMedFlereHaner = this.createProdukt("Bar med flere haner", anlaeg);
        Produkt levering = this.createProdukt("Levering", anlaeg);
        Produkt krus = this.createProdukt("Krus", anlaeg);
        webshop.createPerProduktPris(250, hane_1);
        webshop.createPerProduktPris(400, hane_2);
        webshop.createPerProduktPris(500, barMedFlereHaner);
        webshop.createPerProduktPris(500, levering);
        webshop.createPerProduktPris(60, krus);


        /************** GLAS ****************/

        Produkt glas_uansetStoerrelse = this.createProdukt("Uanset størrelse", glas);
        webshop.createPerProduktPris(15, glas_uansetStoerrelse);


        /************** SAMPAKNINGER ****************/

        Produkt gave_2_Oel = this.createProdukt("Gaveæske : 2 øl, 2 glas", sampakning);
        Produkt gave_4_Oel = this.createProdukt("Gaveæske : 4 øl", sampakning);
        Produkt traekasse_6_Oel = this.createProdukt("Trækasse : 6 øl", sampakning);
        Produkt gavekurv_6_Oel = this.createProdukt("Gavekurv : 6 øl, 2 glas", sampakning);
        Produkt traekasse_6_Glas = this.createProdukt("Trækasse : 6 øl, 6 glas", sampakning);
        Produkt traekasse_12_Oel = this.createProdukt("Trækasse : 12 øl", sampakning);
        Produkt papkasse = this.createProdukt("Papkasse : 12 øl", sampakning);
        fredagsbar.createPerProduktPris(110, gave_2_Oel);
        webshop.createPerProduktPris(110, gave_2_Oel);
        fredagsbar.createPerProduktPris(140, gave_4_Oel);
        webshop.createPerProduktPris(140, gave_4_Oel);
        fredagsbar.createPerProduktPris(260, traekasse_6_Oel);
        webshop.createPerProduktPris(260, traekasse_6_Oel);
        fredagsbar.createPerProduktPris(260, gavekurv_6_Oel);
        webshop.createPerProduktPris(260, gavekurv_6_Oel);
        fredagsbar.createPerProduktPris(350, traekasse_6_Glas);
        webshop.createPerProduktPris(350, traekasse_6_Glas);
        fredagsbar.createPerProduktPris(410, traekasse_12_Oel);
        webshop.createPerProduktPris(410, traekasse_12_Oel);
        fredagsbar.createPerProduktPris(370, papkasse);
        webshop.createPerProduktPris(370, papkasse);


        /************ SALG ************/


        Salg salg = this.createSalg(administrator, Betalingsmetode.DANKORT, LocalDate.now());

        PerProduktPris initKoeb1 = fredagsbar.createPerProduktPris(60, klosterbrygFad);
        PerProduktPris initKoeb2 = fredagsbar.createPerProduktPris(50, extraPilsnerFlaske);
        salg.createSalgslinje(initKoeb1, 10);
        salg.createSalgslinje(initKoeb2, 2);


    }

    public void init() {

        initStorage();
    }


}

