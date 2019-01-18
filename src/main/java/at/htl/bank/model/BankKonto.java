package at.htl.bank.model;

public abstract class BankKonto {
    private String name;
    protected double kontoStand;

    public BankKonto(String name) {
        this.name = name;
        this.kontoStand = 0;
    }

    public BankKonto(String name, double anfangsbestand) {
        this.name = name;
        this.kontoStand = anfangsbestand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getKontoStand() {
        return kontoStand;
    }

   public void einzahlen(double betrag)
   {
        this.kontoStand = this.kontoStand + betrag;
   }

   public void abheben(double betrag)
   {
        this.kontoStand = this.kontoStand - betrag;
   }

}
