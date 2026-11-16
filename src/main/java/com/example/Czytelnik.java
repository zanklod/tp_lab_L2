package com.example;

/**
 * Klasa dziedzinowa reprezentujaca czytelnika biblioteki.
 *
 * <p>Zasady GRASP:
 * - Information Expert (Ekspert Informacyjny): Przechowuje i udostepnia dane
 *   identyfikujace czytelnika (id, imie, nazwisko).
 * - High Cohesion (Wysoka spojnosc): Odpowiada wylacznie za reprezentacje danych
 *   osobowych czytelnika.
 * - Low Coupling (Niskie sprzezenie): Klasa jest calkowicie niezalezna od innych
 *   klas domenowych (nie przechowuje listy Egzemplarzy, co ulatwia mapowanie
 *   relacyjne do bazy danych i eliminuje cykliczne zaleznosci).
 */
public class Czytelnik {

    private final int idCzytelnika;
    private final String imieCzytelnika;
    private final String nazwiskoCzytelnika;

    public Czytelnik(int idCzytelnika, String imie, String nazwisko) {
        if (imie == null || imie.isBlank() || nazwisko == null || nazwisko.isBlank()) {
            throw new IllegalArgumentException("Imie i nazwisko nie moga byc puste!");
        }
        this.idCzytelnika = idCzytelnika;
        this.imieCzytelnika = imie;
        this.nazwiskoCzytelnika = nazwisko;
    }

    public int getIdCzytelnika() {
        return idCzytelnika;
    }

    public String getImie() {
        return imieCzytelnika;
    }

    public String getNazwisko() {
        return nazwiskoCzytelnika;
    }
}