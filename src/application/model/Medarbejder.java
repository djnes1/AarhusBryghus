package application.model;

public class Medarbejder extends Person  {

    private String brugernavn;
    private String password;

    public Medarbejder(String navn, String adresse, String tlfnr, String cpr, String email,String brugernavn, String password) {
        super(navn, adresse, tlfnr, cpr, email);
        this.brugernavn = brugernavn;
        this.password = password;
    }


    public String getBrugernavn() {
        return brugernavn;
    }

    public String getPassword() {
        return password;
    }

}
