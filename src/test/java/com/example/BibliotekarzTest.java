package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Testy jednostkowe weryfikujace logike biznesowa oraz klasy domenowe.
 */
class BibliotekarzTest {

    private BibliotekaRepository repozytorium;
    private Bibliotekarz bibliotekarz;

    @BeforeEach
    void setUp() {
        // Przed kazdym testem tworzymy swieze repozytorium w pamieci i nowego Bibliotekarza
        repozytorium = new BibliotekaRepoArray();
        bibliotekarz = new Bibliotekarz(repozytorium);
    }

    @Test
    @DisplayName("Powinien poprawnie dodac ksiazke i kilka jej egzemplarzy")
    void powinienDodacKsiazkerazWieleEgzemplarzy() {
        // Given
        bibliotekarz.dodajKsiazke(1, "Solaris", "Stanislaw", "Lem");

        // When
        bibliotekarz.dodajEgzemplarz(101, 1);
        bibliotekarz.dodajEgzemplarz(102, 1);

        // Then
        List<Ksiazka> ksiazki = bibliotekarz.przekazWszystkieKsiazki();
        List<Egzemplarz> egzemplarze = bibliotekarz.przekazWszystkieEgzemplarze();

        assertEquals(1, ksiazki.size());
        assertEquals(2, egzemplarze.size());
        assertEquals("Solaris", egzemplarze.get(0).getKsiazka().getTytul());
        assertEquals("Solaris", egzemplarze.get(1).getKsiazka().getTytul());
    }

    @Test
    @DisplayName("Powinien rzucic wyjatek przy probie dodania duplikatu ID ksiazki")
    void powinienRzucicWyjatekPrzyDuplikacieIdKsiazki() {
        // Given
        bibliotekarz.dodajKsiazke(1, "Wiedzmin", "Andrzej", "Sapkowski");

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            bibliotekarz.dodajKsiazke(1, "Inny Tytul", "Jan", "Kowalski");
        });
    }

    @Test
    @DisplayName("Powinien rzucic wyjatek przy dodawaniu egzemplarza do nieistniejacej ksiazki")
    void powinienRzucicWyjatekGdyKsiazkaNieIstnieje() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            bibliotekarz.dodajEgzemplarz(101, 999);
        });
    }

    @Test
    @DisplayName("Czytelnik powinien moc wypozyczyc kilka egzemplarzy ksiazek")
    void czytelnikPowinienMocWypozyczycKilkaKsiazek() {
        // Given
        bibliotekarz.dodajKsiazke(1, "Diuna", "Frank", "Herbert");
        bibliotekarz.dodajKsiazke(2, "Lód", "Jacek", "Dukaj");
        bibliotekarz.dodajEgzemplarz(101, 1);
        bibliotekarz.dodajEgzemplarz(102, 2);
        bibliotekarz.dodajCzytelnika(10, "Adam", "Nowak");

        // When
        bibliotekarz.wypozyczEgzemplarz(101, 10);
        bibliotekarz.wypozyczEgzemplarz(102, 10);

        // Then
        Egzemplarz egz1 = repozytorium.zwrocEgzemplarzPoId(101);
        Egzemplarz egz2 = repozytorium.zwrocEgzemplarzPoId(102);

        assertFalse(egz1.czyDostepny());
        assertFalse(egz2.czyDostepny());
        assertNotNull(egz1.getCzytelnik());
        assertEquals(10, egz1.getCzytelnik().getIdCzytelnika());
        assertEquals(10, egz2.getCzytelnik().getIdCzytelnika());
    }

    @Test
    @DisplayName("Powinien rzucic wyjatek przy probie wypozyczenia zajetego egzemplarza")
    void niePowinienPozwolicWypozyczycZajetegoEgzemplarza() {
        // Given
        bibliotekarz.dodajKsiazke(1, "Solaris", "Stanislaw", "Lem");
        bibliotekarz.dodajEgzemplarz(101, 1);
        bibliotekarz.dodajCzytelnika(10, "Adam", "Nowak");
        bibliotekarz.dodajCzytelnika(20, "Ewa", "Kowalska");

        bibliotekarz.wypozyczEgzemplarz(101, 10);

        // When & Then (drugi czytelnik probuje wypozyczyc ten sam egzemplarz)
        assertThrows(IllegalStateException.class, () -> {
            bibliotekarz.wypozyczEgzemplarz(101, 20);
        });
    }

    @Test
    @DisplayName("Powinien poprawnie obsluzyc zwrot egzemplarza i pozwolic na ponowne wypozyczenie")
    void powinienObsluzycZwrotEgzemplarza() {
        // Given
        bibliotekarz.dodajKsiazke(1, "Solaris", "Stanislaw", "Lem");
        bibliotekarz.dodajEgzemplarz(101, 1);
        bibliotekarz.dodajCzytelnika(10, "Adam", "Nowak");
        bibliotekarz.wypozyczEgzemplarz(101, 10);

        // When
        bibliotekarz.oddajEgzemplarz(101);

        // Then
        Egzemplarz egz = repozytorium.zwrocEgzemplarzPoId(101);
        assertTrue(egz.czyDostepny());
        assertNull(egz.getCzytelnik());
    }

    @Test
    @DisplayName("Powinien automatycznie znalezc i wypozyczyc wolny egzemplarz danej ksiazki")
    void powinienWypozyczycWolnyEgzemplarzPoIdKsiazki() {
        // Given
        bibliotekarz.dodajKsiazke(1, "Solaris", "Stanislaw", "Lem");
        bibliotekarz.dodajEgzemplarz(101, 1);
        bibliotekarz.dodajEgzemplarz(102, 1);
        bibliotekarz.dodajCzytelnika(10, "Adam", "Nowak");
        bibliotekarz.dodajCzytelnika(20, "Ewa", "Kowalska");

        // Pierwszy czytelnik zajmuje egzemplarz 101
        bibliotekarz.wypozyczEgzemplarz(101, 10);

        // When - drugi czytelnik prosi o dowolny wolny egzemplarz ksiazki o ID 1
        bibliotekarz.wypozyczKsiazkePoId(1, 20);

        // Then - system powinien automatycznie przydzielic wolny egzemplarz 102
        Egzemplarz egz102 = repozytorium.zwrocEgzemplarzPoId(102);
        assertFalse(egz102.czyDostepny());
        assertEquals(20, egz102.getCzytelnik().getIdCzytelnika());
    }
}