package at.htl.bank.model;

public class SparKonto extends BankKonto{
    private double zinssatz;

    public SparKonto(String name, double zinssatz) {
        super(name);
        this.zinssatz = zinssatz;
    }

    public SparKonto(String name, double zinssatz, double anfangsBestand) {
        super(name, anfangsBestand);
        this.zinssatz = zinssatz;
    }

    public void zinsenAnrechnen()
    {
        this.kontoStand = kontoStand + kontoStand * (zinssatz / 100);
    }
}
