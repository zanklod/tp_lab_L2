package com.example;

import java.util.Objects;

/**
 * Klasa dziedzinowa reprezentujaca abstrakcyjna pozycje katalogowa (tytul).
 *
 * <p>Zasady GRASP:
 * - Information Expert (Ekspert Informacyjny): Klasa przechowuje podstawowe
 *   dane o ksiazce (id, tytul, autor) i odpowiada za ich udostepnianie.
 *   Posiada rowniez metody equals i hashCode oparte na idKsiazki, poniewaz
 *   sama najlepiej wie, kiedy dwie instancje reprezentuja ten sam tytul.
 * - High Cohesion (Wysoka spojnosc): Klasa jest maksymalnie prosta i skupiona
 *   wylacznie na opisie pozycji katalogowej. Nie zawiera logiki bazodanowej,
 *   interfejsu uzytkownika ani zarzadzania fizycznymi egzemplarzami.
 * - Low Coupling (Niskie sprzezenie): Klasa nie zalezy od zadnej innej klasy
 *   w systemie (zero referencji do Czytelnika, Egzemplarza czy Repozytorium).
 */
public class Ksiazka {

    private final int idKsiazki;
    private final String tytul;
    private final String imieAutora;
    private final String nazwiskoAutora;

    public Ksiazka(int idKsiazki, String tytul, String imieAutora, String nazwiskoAutora) {
        if (tytul == null || tytul.isBlank()) {
            throw new IllegalArgumentException("Tytul ksiazki nie moze byc pusty!");
        }
        this.idKsiazki = idKsiazki;
        this.tytul = tytul;
        this.imieAutora = imieAutora;
        this.nazwiskoAutora = nazwiskoAutora;
    }

    public int getIdKsiazki() {
        return idKsiazki;
    }

    public String getImieAutora() {
        return imieAutora;
    }

    public String getNazwiskoAutora() {
        return nazwiskoAutora;
    }

    public String getTytul() {
        return tytul;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ksiazka ksiazka = (Ksiazka) o;
        return idKsiazki == ksiazka.idKsiazki;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idKsiazki);
    }
}