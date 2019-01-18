package at.htl.bank.model;

public class GiroKonto extends BankKonto{
    private double gebuehr;

    public GiroKonto(String name, double gebuehr) {
        super(name);
        this.gebuehr = gebuehr;
    }

    public GiroKonto(String name, double anfangsbestand, double gebuehr) {
        super(name, anfangsbestand);
        this.gebuehr = gebuehr;
    }

    @Override
    public void einzahlen(double betrag)
    {
        this.kontoStand = this.kontoStand + betrag - gebuehr;
    }

    @Override
    public void abheben(double betrag)
    {
        this.kontoStand = this.kontoStand - betrag - gebuehr;
    }
}
