package application.model;

class Rabat {

    private int beløb = 0;
    private String beskrivelse;
    private double procent = 1;

    public Rabat(int sum) {
        this.beløb = sum;
    }

    public Rabat(double procent, String beskrivelse) {
        this.procent = procent;
        this.beskrivelse = beskrivelse;
    }
    

    public double getBeløb() {
        return beløb;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public double getProcent() {
        return procent;
    }

}
