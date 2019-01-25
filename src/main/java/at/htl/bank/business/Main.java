package at.htl.bank.business;

import at.htl.bank.model.BankKonto;
import at.htl.bank.model.GiroKonto;
import at.htl.bank.model.SparKonto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Legen Sie eine statische Liste "konten" an, in der Sie die einzelnen Konten speichern
 *
 */
public class Main {

  // die Konstanten sind package-scoped wegen der Unit-Tests
  static final double GEBUEHR = 0.02;
  static final double ZINSSATZ = 3.0;

  static final String KONTENDATEI = "erstellung.csv";
  static final String BUCHUNGSDATEI = "buchungen.csv";
  static final String ERGEBNISDATEI = "ergebnis.csv";

  static ArrayList <BankKonto> konten = new ArrayList<BankKonto>();

  /**
   * Führen Sie die drei Methoden erstelleKonten, fuehreBuchungenDurch und
   * findKontoPerName aus
   *
   * @param args
   */
  public static void main(String[] args) {
    erstelleKonten(KONTENDATEI);
    fuehreBuchungenDurch(BUCHUNGSDATEI);
    schreibeKontostandInDatei(ERGEBNISDATEI);
  }

  /**
   * Lesen Sie aus der Datei (erstellung.csv) die Konten ein.
   * Je nach Kontentyp erstellen Sie ein Spar- oder Girokonto.
   * Gebühr und Zinsen sind als Konstanten angegeben.
   *
   * Nach dem Anlegen der Konten wird auf der Konsole folgendes ausgegeben:
   * Erstellung der Konten beendet
   *
   * @param datei KONTENDATEI
   */
  private static void erstelleKonten(String datei) {

        try (Scanner scanner = new Scanner(new FileReader(datei))) {
          scanner.nextLine();
          while (scanner.hasNextLine())
          {
            String [] helper = scanner.nextLine().split(";");

            if (helper[0].equals("Sparkonto"))
            {
              konten.add(new SparKonto(helper[1], ZINSSATZ, Double.valueOf(helper[2])));
            }
            else
            {
              konten.add(new GiroKonto(helper[1], Double.valueOf(helper[2]), GEBUEHR));
            }
          }
        }
        catch (FileNotFoundException e) {
          System.out.println(e.getMessage());
        }
    System.out.println("Erstellung der Konten beendet");
  }

  /**
   * Die einzelnen Buchungen werden aus der Datei eingelesen.
   * Es wird aus der Liste "konten" jeweils das Bankkonto für
   * kontoVon und kontoNach gesucht.
   * Anschließend wird der Betrag vom kontoVon abgebucht und
   * der Betrag auf das kontoNach eingezahlt
   *
   * Nach dem Durchführen der Buchungen wird auf der Konsole folgendes ausgegeben:
   * Buchung der Beträge beendet
   *
   * Tipp: Verwenden Sie hier die Methode 'findeKontoPerName(String name)'
   *
   * @param datei BUCHUNGSDATEI
   */
  private static void fuehreBuchungenDurch(String datei) {
    try (Scanner scanner = new Scanner(new FileReader(datei))) {
      scanner.nextLine();
      while (scanner.hasNextLine())
      {
        String [] zeile = scanner.nextLine().split(";");
        BankKonto person1 = findeKontoPerName(zeile[0]);
        BankKonto person2 = findeKontoPerName(zeile[1]);

        person1.abheben(Double.parseDouble(zeile[2]));
        person2.einzahlen(Double.parseDouble(zeile[2]));
      }
    }
    catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }

        System.out.println("Buchung der Beträge beendet");
  }

  /**
   * Es werden die Kontostände sämtlicher Konten in die ERGEBNISDATEI
   * geschrieben. Davor werden bei Sparkonten noch die Zinsen dem Konto
   * gutgeschrieben
   *
   * Die Datei sieht so aus:
   *
   * name;kontotyp;kontostand
   * Susi;SparKonto;875.5
   * Mimi;GiroKonto;949.96
   * Hans;GiroKonto;1199.96
   *
   * Vergessen Sie nicht die Überschriftenzeile
   *
   * Nach dem Schreiben der Datei wird auf der Konsole folgendes ausgegeben:
   * Ausgabe in Ergebnisdatei beendet
   *
   * @param datei ERGEBNISDATEI
   */
  private static void schreibeKontostandInDatei(String datei) {
        try(PrintWriter writer = new PrintWriter(new FileWriter(datei))) {
          writer.println("name;kontotyp;kontostand");
          for (int i = 0; i < konten.size(); i++) {
            if (konten.get(i) instanceof SparKonto)
            {
              ((SparKonto) konten.get(i)).zinsenAnrechnen();
              writer.println(konten.get(i).getName() + ";SparKonto;" + konten.get(i).getKontoStand());
            }
            else
            {
              writer.println(konten.get(i).getName() + ";GiroKonto;" + konten.get(i).getKontoStand());
            }
          }
        }
        catch (IOException e) {
          System.err.println(e.getMessage());
        }
    System.out.println("Ausgabe in Ergebnisdatei beendet");
  }

  /**
   */
  /**
   * Durchsuchen Sie die Liste "konten" nach dem ersten Konto mit dem als Parameter
   * übergebenen Namen
   * @param name
   * @return Bankkonto mit dem gewünschten Namen oder NULL, falls der Namen
   *         nicht gefunden wird
   */
  public static BankKonto findeKontoPerName(String name) {
    for (BankKonto p : konten)
    {
      if (p.getName().equals(name))
      {
        return p;
      }
    }
    return null;
  }

}
