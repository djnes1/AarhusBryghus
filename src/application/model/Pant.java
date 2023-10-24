package application.model;

public enum Pant {
    A(1), B(1.5),C(3),INGENPANT(0),FUSTAGE(200);

   private final double pris;

    Pant(double pris) {
        this.pris = pris;
    }

    public double getPris() {
        return pris;
    }
}
