package application.model;



public class Administrator extends Medarbejder {

    private String befoejelser;

    public Administrator(String navn, String adresse, String tlfnr, String cpr, String email, String brugernavn, String password, String befoejelser) {
        super(navn, adresse, tlfnr, cpr, email, brugernavn, password);
        this.befoejelser = befoejelser;
    }


    public String getBefoejelser() {
        return befoejelser;
    }

    public void setBefoejelser(String befoejelser) {
        this.befoejelser = befoejelser;
    }

    @Override
    public String toString() {
        return super.toString()
                + String.format("\n%s", befoejelser);
    }
}
