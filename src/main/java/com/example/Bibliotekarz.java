package com.example;

import java.util.List;

/**
 * Klasa sterujaca logika biznesowa aplikacji bibliotecznej.
 *
 * <p>Zasady GRASP i SOLID:
 * - Controller (Kontroler): Odbiera zdarzenia systemowe z warstwy interfejsu
 *   uzytkownika (Main) i koordynuje ich wykonanie (walidacja, delegacja do
 *   obiektow domenowych oraz zapis w repozytorium).
 * - Creator (Tworca): Odpowiada za tworzenie instancji klas Ksiazka, Egzemplarz
 *   oraz Czytelnik na podstawie surowych danych z warstwy UI, poniewaz posiada
 *   dane inicjalizacyjne i bezposrednio zleca ich rejestracje w systemie.
 * - Low Coupling (Niskie sprzezenie) oraz Dependency Inversion: Klasa nie zna
 *   konkretnej implementacji bazy danych (BibliotekaRepoArray), lecz operuje
 *   wylacznie na interfejsie BibliotekaRepository wstrzykiwanym przez konstruktor.
 * - High Cohesion (Wysoka spojnosc): Zawiera wylacznie reguly przeplywu pracy
 *   w bibliotece; nie zawiera kodu wyswietlania (Scanner/System.out) ani
 *   szczegolow technicznych składowania danych.
 */
public class Bibliotekarz {

    private final BibliotekaRepository magazyn;

    public Bibliotekarz(BibliotekaRepository magazyn) {
        if (magazyn == null) {
            throw new IllegalArgumentException("Magazyn danych nie moze byc null!");
        }
        this.magazyn = magazyn;
    }

    /**
     * Tworzy i zapisuje nowa ksiazke (GRASP: Creator + Controller).
     */
    public void dodajKsiazke(
            int idKsiazki, String tytul, String imieAutora, String nazwiskoAutora) {
        if (magazyn.czyMamyKsiazke(idKsiazki)) {
            throw new IllegalArgumentException("Ta ksiazka jest juz dodana!");
        }
        Ksiazka ksiazka = new Ksiazka(idKsiazki, tytul, imieAutora, nazwiskoAutora);
        magazyn.dodajKsiazke(ksiazka);
    }

    /**
     * Tworzy i zapisuje nowy egzemplarz istniejacej ksiazki (GRASP: Creator + Controller).
     */
    public void dodajEgzemplarz(int idEgzemplarza, int idKsiazki) {
        if (magazyn.czyMamyEgzemplarz(idEgzemplarza)) {
            throw new IllegalArgumentException("Ten egzemplarz jest juz dodany!");
        }
        Ksiazka ksiazka = magazyn.zwrocKsiazkePoId(idKsiazki);
        if (ksiazka == null) {
            throw new IllegalArgumentException(
                "Brak ksiazki o podanym ID w bazie. Najpierw dodaj ksiazke.");
        }
        Egzemplarz egzemplarz = new Egzemplarz(idEgzemplarza, ksiazka);
        magazyn.dodajEgzemplarz(egzemplarz);
    }

    /**
     * Tworzy i zapisuje nowego czytelnika (GRASP: Creator + Controller).
     */
    public void dodajCzytelnika(
            int idCzytelnika, String imieCzytelnika, String nazwiskoCzytelnika) {
        if (magazyn.czyMamyCzytelnika(idCzytelnika)) {
            throw new IllegalArgumentException("Ten czytelnik jest juz dodany!");
        }
        Czytelnik czytelnik = new Czytelnik(idCzytelnika, imieCzytelnika, nazwiskoCzytelnika);
        magazyn.dodajCzytelnika(czytelnik);
    }

    /**
     * Realizuje zwrot egzemplarza, delegujac zmiane stanu do Eksperta (Egzemplarz).
     */
    public void oddajEgzemplarz(int idEgzemplarza) {
        Egzemplarz egzemplarz = magazyn.zwrocEgzemplarzPoId(idEgzemplarza);
        if (egzemplarz == null) {
            throw new IllegalArgumentException("Nie ma takiego egzemplarza!");
        }
        egzemplarz.zwrotEgzemplarza();
        magazyn.zaktualizujEgzemplarz(egzemplarz);
    }

    /**
     * Wypozycza konkretny egzemplarz po jego ID dla wskazanego czytelnika.
     */
    public void wypozyczEgzemplarz(int idEgzemplarza, int idCzytelnika) {
        Egzemplarz egzemplarz = magazyn.zwrocEgzemplarzPoId(idEgzemplarza);
        if (egzemplarz == null) {
            throw new IllegalArgumentException("W bazie nie ma tego egzemplarza!");
        }
        Czytelnik czytelnik = magazyn.zwrocCzytelnikaPoId(idCzytelnika);
        if (czytelnik == null) {
            throw new IllegalArgumentException("W bazie nie ma takiego czytelnika!");
        }
        egzemplarz.wypozycz(czytelnik);
        magazyn.zaktualizujEgzemplarz(egzemplarz);
    }

    /**
     * Wypozycza pierwszy wolny egzemplarz danej ksiazki (po ID ksiazki) dla czytelnika.
     */
    public void wypozyczKsiazkePoId(int idKsiazki, int idCzytelnika) {
        Ksiazka ksiazka = magazyn.zwrocKsiazkePoId(idKsiazki);
        if (ksiazka == null) {
            throw new IllegalArgumentException("W bazie nie ma takiej ksiazki!");
        }
        Czytelnik czytelnik = magazyn.zwrocCzytelnikaPoId(idCzytelnika);
        if (czytelnik == null) {
            throw new IllegalArgumentException("W bazie nie ma takiego czytelnika!");
        }
        Egzemplarz wolnyEgzemplarz = magazyn.znajdzWolnyEgzemplarzKsiazki(ksiazka);
        if (wolnyEgzemplarz == null) {
            throw new IllegalStateException("Brak wolnych egzemplarzy tej ksiazki!");
        }
        wolnyEgzemplarz.wypozycz(czytelnik);
        magazyn.zaktualizujEgzemplarz(wolnyEgzemplarz);
    }

    public boolean czyKsiazkaIstnieje(int idKsiazki) {
        return magazyn.czyMamyKsiazke(idKsiazki);
    }

    public List<Czytelnik> przekazWszystkichCzytelnikow() {
        return magazyn.pobierzWszystkichCzytelnikow();
    }

    public List<Egzemplarz> przekazWszystkieEgzemplarze() {
        return magazyn.pobierzWszystkieEgzemplarze();
    }

    public List<Ksiazka> przekazWszystkieKsiazki() {
        return magazyn.pobierzWszystkieKsiazki();
    }
}