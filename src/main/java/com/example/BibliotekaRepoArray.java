package com.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementacja repozytorium przechowujaca dane w pamieci RAM (na listach).
 *
 * <p>Zasady GRASP:
 * - Pure Fabrication (Czysty wymysl): Sztuczna klasa techniczna grupujaca
 *   operacje składowania i wyszukiwania obiektow w kolekcjach.
 * - High Cohesion (Wysoka spojnosc): Zajmuje sie wylacznie operacjami CRUD
 *   na kolekcjach w pamieci, nie zawiera logiki biznesowej ani walidacji.
 * - Information Expert (w zakresie kolekcji): Posiada bezposredni dostep do
 *   list obiektow, dlatego odpowiada za ich przeszukiwanie po identyfikatorach.
 */
public class BibliotekaRepoArray implements BibliotekaRepository {

    private final List<Egzemplarz> egzemplarze = new ArrayList<>();
    private final List<Czytelnik> czytelnicy = new ArrayList<>();
    private final List<Ksiazka> ksiazki = new ArrayList<>();

    @Override
    public void dodajCzytelnika(Czytelnik czytelnik) {
        czytelnicy.add(czytelnik);
    }

    @Override
    public void dodajEgzemplarz(Egzemplarz egzemplarz) {
        egzemplarze.add(egzemplarz);
    }

    @Override
    public void dodajKsiazke(Ksiazka ksiazka) {
        ksiazki.add(ksiazka); 
    }

    @Override
    public void zaktualizujEgzemplarz(Egzemplarz egzemplarz) {
        if (egzemplarz == null) {
            throw new IllegalArgumentException("Aktualizowany egzemplarz nie moze byc null!");
        }
        // W implementacji opartej o pamiec RAM obiekt jest modyfikowany przez referencje.
        // W SqlBibliotekaRepository w tym miejscu zostanie wykonane zapytanie UPDATE.
    }

    @Override
    public boolean czyMamyKsiazke(int idKsiazki) {
        return zwrocKsiazkePoId(idKsiazki) != null;
    }

    @Override
    public boolean czyMamyEgzemplarz(int idEgzemplarza) {
        return zwrocEgzemplarzPoId(idEgzemplarza) != null;
    }

    @Override
    public boolean czyMamyCzytelnika(int idCzytelnika) {
        return zwrocCzytelnikaPoId(idCzytelnika) != null;
    }

    @Override
    public Ksiazka zwrocKsiazkePoId(int idKsiazki) {
        for (Ksiazka kandydat : ksiazki) {
            if (kandydat.getIdKsiazki() == idKsiazki) {
                return kandydat;
            }
        }
        return null;
    }

    @Override
    public Egzemplarz zwrocEgzemplarzPoId(int idEgzemplarza) {
        for (Egzemplarz kandydat : egzemplarze) {
            if (kandydat.getIdEgzemplarza() == idEgzemplarza) {
                return kandydat;
            }
        }
        return null;
    }

    @Override
    public Czytelnik zwrocCzytelnikaPoId(int idCzytelnika) {
        for (Czytelnik kandydat : czytelnicy) {
            if (kandydat.getIdCzytelnika() == idCzytelnika) {
                return kandydat;
            }
        }
        return null;
    }

    @Override
    public Egzemplarz znajdzWolnyEgzemplarzKsiazki(Ksiazka ksiazka) {
        for (Egzemplarz kandydat : egzemplarze) {
            if (kandydat.getKsiazka().equals(ksiazka) && kandydat.czyDostepny()) {
                return kandydat;
            }
        }
        return null;
    }

    @Override
    public List<Czytelnik> pobierzWszystkichCzytelnikow() {
        return new ArrayList<>(czytelnicy);
    }

    @Override
    public List<Egzemplarz> pobierzWszystkieEgzemplarze() {
        return new ArrayList<>(egzemplarze);
    }

    @Override
    public List<Ksiazka> pobierzWszystkieKsiazki() {
        return new ArrayList<>(ksiazki);
    }
}