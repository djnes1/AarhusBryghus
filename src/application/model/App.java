package application.model;

public class App {

    public static void main(String[] args) {

//        Controller controller = new Controller();
        ProduktGruppe produktGruppe = new ProduktGruppe("Fadøl", Pant.INGENPANT);
//        Kunde donnie = new Kunde("Donnie Darko", "Ringgade 45\n8200 Aarhus\n", "23232323", "2345323", "hej@gmail.com");
        Medarbejder judi = new Medarbejder("Judi Dench", "Ringgade 45\n8200 Aarhus\n", "23232323", "2345323", "hej@gmail.com","bom","23");
////        System.out.println(Pant.INGENPANT.getPris());
//        Rabat rabat = new Rabat(10);
//        Produkt produkt = controller.createProdukt("Ale", produktGruppe);
////        Produkt produkt1 = controller.createProdukt("IPA",produktGruppe);
//        Salgssituation salgssituation1 = new Salgssituation("Fredag");
//        Salgssituation salgssituation = controller.createSalgssituation("Lørdag");
//       PerProduktPris perProduktPris = controller.createPerProdukt(salgssituation,20,produkt);
//        salgssituation1.createPerProduktPris(34, produkt);
//        salgssituation1.createPerProduktPris(20,produkt);

////        Salgslinje salgslinje =new Salgslinje(produkt,5);
//////        System.out.println(salgslinje.getProdukt().getProduktGruppe());
////
////        salgslinje1.setRabat(rabat);
//        System.out.println(salgssituation1.getPerProduktPriser());
//        Salg salg = controller.createSalg(judi,Betalingsmetode.KLIPPEKORT, LocalDate.of(2022, 1, 10));
//        Salgslinje salgslinje1 = salg.createSalgslinje(perProduktPris,3);
//////        Salg salg1 = controller.createSalg(donnie, Pant.pe
// , Betalingsmetode.KLIPPEKORT, salgssituation1, LocalDate.of(2022, 1, 10));
////
////        salg.addSalgslinje(salgslinje);
////        salg.addSalgslinje(salgslinje1);
////        salg1.addSalgslinje(salgslinje);
//
//        controller.writeReceiptToFile(salg);
//        System.out.println(salgslinje1.samletKoeb());
////////        System.out.println(salg);
//////        System.out.println();
//        System.out.println(salg.samletPris());
//////        System.out.println(controller.dagensSalg(LocalDate.of(2022, 1, 10)));
//////        System.out.println();
////        afregning1.setRabat(rabat);
////        Produkt udlejning = controller.createUdlejning("Hej", produktGruppe);
////        System.out.println(controller.antalIkkeAfleveredeUdlejninger());

    }
}
