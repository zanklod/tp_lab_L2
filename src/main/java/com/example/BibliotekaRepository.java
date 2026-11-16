package com.example;

import java.util.List;

/**
 * Interfejs warstwy dostepu do danych (Data Access Layer).
 *
 * <p>Zasady GRASP i SOLID:
 * - Pure Fabrication (Czysty wymysl): Interfejs oraz jego implementacje nie
 *   reprezentuja bytu ze swiata rzeczywistego biblioteki, lecz zostaly powolane
 *   w celu odciazenia klas domenowych i kontrolera z odpowiedzialnosci za
 *   przechowywanie danych.
 * - Dependency Inversion (Odwrocenie zaleznosci) i Indirection (Posrednictwo):
 *   Klasa Bibliotekarz zalezy od tej abstrakcji, a nie od konkretnej bazy danych.
 * - Polymorphism (Polimorfizm) i Protected Variations (Ochrona przed zmianami):
 *   Pozwala na bezbolesna podmiane sposobu zapisu (np. z pamieci RAM na baze
 *   SQL lub NoSQL) poprzez utworzenie nowej klasy implementujacej ten interfejs,
 *   bez modyfikacji kodu logiki biznesowej.
 */
public interface BibliotekaRepository {

    void dodajCzytelnika(Czytelnik czytelnik);

    void dodajKsiazke(Ksiazka ksiazka);
    
    void dodajEgzemplarz(Egzemplarz egzemplarz);


    void zaktualizujEgzemplarz(Egzemplarz egzemplarz);


    Egzemplarz znajdzWolnyEgzemplarzKsiazki(Ksiazka ksiazka);


    boolean czyMamyKsiazke(int idKsiazki);
    
    boolean czyMamyEgzemplarz(int idEgzemplarza);
    
    boolean czyMamyCzytelnika(int idCzytelnika);


    Ksiazka zwrocKsiazkePoId(int idKsiazki);
    
    Egzemplarz zwrocEgzemplarzPoId(int idEgzemplarza);
    
    Czytelnik zwrocCzytelnikaPoId(int idCzytelnika);



    List<Ksiazka> pobierzWszystkieKsiazki();
    
    List<Egzemplarz> pobierzWszystkieEgzemplarze();
    
    List<Czytelnik> pobierzWszystkichCzytelnikow();
}