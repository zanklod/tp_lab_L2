package com.example;

/**
 * Klasa dziedzinowa reprezentujaca fizyczny egzemplarz konkretnej ksiazki.
 *
 * <p>Zasady GRASP:
 * - Information Expert (Ekspert Informacyjny): Poniewaz Egzemplarz przechowuje
 *   referencje do aktualnego Czytelnika (pole czytelnik), sam najlepiej wie,
 *   czy jest wolny (metoda czyDostepny) oraz sam pilnuje poprawnosci zmiany
 *   swojego stanu podczas wypozyczenia (metoda wypozycz) i zwrotu (zwrotEgzemplarza).
 * - High Cohesion (Wysoka spojnosc): Skupia sie wylacznie na powiazaniu fizycznej
 *   kopii z tytulem (Ksiazka) oraz jej aktualnym statusem wypozyczenia.
 * - Low Coupling (Niskie sprzezenie): Zna jedynie klasy Ksiazka i Czytelnik,
 *   nie zalezy od warstwy repozytorium ani warstwy prezentacji.
 */
public class Egzemplarz {

    private final int idEgzemplarza;
    private final Ksiazka ksiazka;
    private Czytelnik czytelnik;

    public Egzemplarz(int idEgzemplarza, Ksiazka ksiazka) {
        if (ksiazka == null) {
            throw new IllegalArgumentException("Egzemplarz musi byc przypisany do ksiazki!");
        }
        this.idEgzemplarza = idEgzemplarza;
        this.ksiazka = ksiazka;
        this.czytelnik = null;
    }

    /**
     * Przypisuje czytelnika do egzemplarza (realizacja Information Expert).
     *
     * @param nowyCzytelnik obiekt czytelnika wypozyczajacego egzemplarz
     */
    public void wypozycz(Czytelnik nowyCzytelnik) {
        if (nowyCzytelnik == null) {
            throw new IllegalArgumentException("Czytelnik nie moze byc null!");
        }
        if (this.czytelnik != null) {
            throw new IllegalStateException("Ten egzemplarz jest juz wypozyczony!");
        }
        this.czytelnik = nowyCzytelnik;
    }

    /**
     * Zwalnia egzemplarz (ustawia czytelnika na null).
     */
    public void zwrotEgzemplarza() {
        if (this.czytelnik == null) {
            throw new IllegalStateException("Ten egzemplarz nie jest aktualnie wypozyczony!");
        }
        this.czytelnik = null;
    }

    public boolean czyDostepny() {
        return this.czytelnik == null;
    }

    public int getIdEgzemplarza() {
        return idEgzemplarza;
    }

    public Czytelnik getCzytelnik() {
        return czytelnik;
    }

    public Ksiazka getKsiazka() {
        return ksiazka;
    }
}