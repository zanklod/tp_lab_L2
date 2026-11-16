package com.example;

import java.util.List;
import java.util.Scanner;

/**
 * Klasa warstwy prezentacji (interfejs konsolowy uzytkownika).
 *
 * <p>Zasady GRASP:
 * - High Cohesion i Low Coupling: Klasa odpowiada wylacznie za interakcje z uzytkownikiem
 *   (pobieranie tekstu z konsoli i wyswietlanie wynikow). Cala logike biznesowa
 *   deleguje do Kontrolera (Bibliotekarz). Pelni rowniez role Composition Root,
 *   wstrzykujac implementacje BibliotekaRepoArray do klasy Bibliotekarz.
 */
public final class Main {

    private Main() {
        // Prywatny konstruktor wymagany przez Checkstyle/PMD dla klas narzedziowych
    }

    public static void main(String[] args) {
        BibliotekaRepository magazyn = new BibliotekaRepoArray();
        Bibliotekarz bibliotekarz = new Bibliotekarz(magazyn);
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== SYSTEM BIBLIOTECZNY ===");
        boolean czyDziala = true;

        while (czyDziala) {
            wyswietlMenu();
            String wybor = scanner.nextLine().trim();

            try {
                czyDziala = wykonajAkcjeMenu(wybor, scanner, bibliotekarz);
            } catch (NumberFormatException e) {
                System.out.println("Blad: ID musi byc liczba calkowita!");
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Blad operacji: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static boolean wykonajAkcjeMenu(
            String wybor, Scanner scanner, Bibliotekarz bibliotekarz) {
        switch (wybor) {
            case "1":
                obsluzDodawanieKsiazki(scanner, bibliotekarz);
                break;
            case "2":
                obsluzDodawanieEgzemplarza(scanner, bibliotekarz);
                break;
            case "3":
                obsluzDodawanieCzytelnika(scanner, bibliotekarz);
                break;
            case "4":
                obsluzWypozyczenie(scanner, bibliotekarz);
                break;
            case "5":
                obsluzZwrot(scanner, bibliotekarz);
                break;
            case "6":
                wypiszKsiazki(bibliotekarz);
                break;
            case "7":
                wypiszEgzemplarze(bibliotekarz);
                break;
            case "8":
                wypiszCzytelnikow(bibliotekarz);
                break;
            case "q":
                System.out.println("Zamykanie programu...");
                return false;
            default:
                System.out.println("Nieznana opcja, sprobuj ponownie.");
                break;
        }
        return true;
    }

    private static void wyswietlMenu() {
        System.out.println("\nWybierz opcje:");
        System.out.println("1 - Dodaj ksiazke");
        System.out.println("2 - Dodaj egzemplarz (jesli brak tytulu, pozwoli go wprowadzic)");
        System.out.println("3 - Dodaj czytelnika");
        System.out.println("4 - Wypozycz egzemplarz");
        System.out.println("5 - Oddaj egzemplarz");
        System.out.println("6 - Wypisz ksiazki");
        System.out.println("7 - Wypisz egzemplarze");
        System.out.println("8 - Wypisz czytelnikow");
        System.out.println("q - Wyjscie");
        System.out.print("> ");
    }

    private static void obsluzDodawanieKsiazki(Scanner scanner, Bibliotekarz bibliotekarz) {
        System.out.print("Podaj ID ksiazki: ");
        int idKsiazki = Integer.parseInt(scanner.nextLine().trim());
        wprowadzDaneNowejKsiazki(scanner, bibliotekarz, idKsiazki);
    }

    private static void wprowadzDaneNowejKsiazki(
            Scanner scanner, Bibliotekarz bibliotekarz, int idKsiazki) {
        System.out.print("Podaj tytul: ");
        String tytul = scanner.nextLine().trim();
        System.out.print("Podaj imie autora: ");
        String imieAutora = scanner.nextLine().trim();
        System.out.print("Podaj nazwisko autora: ");
        String nazwiskoAutora = scanner.nextLine().trim();

        bibliotekarz.dodajKsiazke(idKsiazki, tytul, imieAutora, nazwiskoAutora);
        System.out.println("Dodano ksiazke do katalogu.");
    }

    private static void obsluzDodawanieEgzemplarza(
            Scanner scanner, Bibliotekarz bibliotekarz) {
        System.out.print("Podaj ID nowego egzemplarza: ");
        int idEgzemplarza = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Podaj ID ksiazki, ktorej dotyczy egzemplarz: ");
        int idKsiazki = Integer.parseInt(scanner.nextLine().trim());

        if (!bibliotekarz.czyKsiazkaIstnieje(idKsiazki)) {
            System.out.println("Tytulu o tym ID nie ma jeszcze w systemie. Wprowadz go teraz:");
            wprowadzDaneNowejKsiazki(scanner, bibliotekarz, idKsiazki);
        }

        bibliotekarz.dodajEgzemplarz(idEgzemplarza, idKsiazki);
        System.out.println("Dodano egzemplarz.");
    }

    private static void obsluzDodawanieCzytelnika(Scanner scanner, Bibliotekarz bibliotekarz) {
        System.out.print("Podaj ID czytelnika: ");
        int idCzytelnika = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Podaj imie czytelnika: ");
        String imie = scanner.nextLine().trim();
        System.out.print("Podaj nazwisko czytelnika: ");
        String nazwisko = scanner.nextLine().trim();

        bibliotekarz.dodajCzytelnika(idCzytelnika, imie, nazwisko);
        System.out.println("Dodano czytelnika.");
    }

    private static void obsluzWypozyczenie(Scanner scanner, Bibliotekarz bibliotekarz) {
        System.out.print("Podaj ID egzemplarza do wypozyczenia: ");
        int idEgzemplarza = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Podaj ID czytelnika: ");
        int idCzytelnika = Integer.parseInt(scanner.nextLine().trim());

        bibliotekarz.wypozyczEgzemplarz(idEgzemplarza, idCzytelnika);
        System.out.println("Wypozyczono egzemplarz.");
    }

    private static void obsluzZwrot(Scanner scanner, Bibliotekarz bibliotekarz) {
        System.out.print("Podaj ID zwracanego egzemplarza: ");
        int idEgzemplarza = Integer.parseInt(scanner.nextLine().trim());

        bibliotekarz.oddajEgzemplarz(idEgzemplarza);
        System.out.println("Zwrocono egzemplarz.");
    }

    private static void wypiszKsiazki(Bibliotekarz bibliotekarz) {
        List<Ksiazka> ksiazki = bibliotekarz.przekazWszystkieKsiazki();
        if (ksiazki.isEmpty()) {
            System.out.println("Brak ksiazek w systemie.");
            return;
        }
        for (Ksiazka ksiazka : ksiazki) {
            System.out.println(
                "ID: " + ksiazka.getIdKsiazki() + " | Tytul: " + ksiazka.getTytul()
                + " | Autor: " + ksiazka.getImieAutora() + " " + ksiazka.getNazwiskoAutora());
        }
    }

    private static void wypiszEgzemplarze(Bibliotekarz bibliotekarz) {
        List<Egzemplarz> egzemplarze = bibliotekarz.przekazWszystkieEgzemplarze();
        if (egzemplarze.isEmpty()) {
            System.out.println("Brak egzemplarzy w systemie.");
            return;
        }
        for (Egzemplarz egzemplarz : egzemplarze) {
            Czytelnik czytelnik = egzemplarz.getCzytelnik();
            String status = (czytelnik == null)
                ? "WOLNY"
                : "Wypozyczony (ID czytelnika: " + czytelnik.getIdCzytelnika() + ")";

            System.out.println(
                "ID egz.: " + egzemplarz.getIdEgzemplarza()
                + " | Ksiazka: " + egzemplarz.getKsiazka().getTytul()
                + " (ID: " + egzemplarz.getKsiazka().getIdKsiazki() + ")"
                + " | Status: " + status);
        }
    }

    private static void wypiszCzytelnikow(Bibliotekarz bibliotekarz) {
        List<Czytelnik> czytelnicy = bibliotekarz.przekazWszystkichCzytelnikow();
        if (czytelnicy.isEmpty()) {
            System.out.println("Brak czytelnikow w systemie.");
            return;
        }
        for (Czytelnik czytelnik : czytelnicy) {
            System.out.println(
                "ID: " + czytelnik.getIdCzytelnika() + " | "
                + czytelnik.getImie() + " " + czytelnik.getNazwisko());
        }
    }
}