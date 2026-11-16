=========================================
UC1
=========================================
Nazwa; Dodaj książkę do katalogu
Identyfikator (ID); UC1
Aktor; Użytkownik systemu
Opis; Dodanie nowej książki do katalogu biblioteki na podstawie wprowadzonych danych.
Warunki wstępne; Program musi być uruchomiony.
Warunki końcowe; Książka zostaje zapisana w bazie danych, a system wyświetla komunikat o sukcesie.
Sekwencja akcji; 
1. Użytkownik wybiera opcję 1 z menu.
2. Użytkownik podaje ID książki, tytuł, imię autora oraz nazwisko autora.
3. Program sprawdza, czy ID nie jest już zajęte i czy tytuł nie jest pusty.
4. Program zapisuje książkę w bazie i wyświetla komunikat o dodaniu książki.
[Alternatywnie]: Jeśli ID nie jest liczbą całkowitą, wyświetla się błąd formatu i program wraca do menu. Jeśli książka o podanym ID jest już w bazie albo tytuł jest pusty, program wyświetla komunikat błędu i nie dodaje książki.

=========================================
UC2
=========================================
Nazwa; Dodaj egzemplarz książki
Identyfikator (ID); UC2
Aktor; Użytkownik systemu
Opis; Dodanie nowego egzemplarza powiązanego z konkretną książką w bazie.
Warunki wstępne; Program musi być uruchomiony.
Warunki końcowe; Nowy egzemplarz zostaje zapisany w systemie ze statusem "wolny", wyświetla się potwierdzenie.
Sekwencja akcji; 
1. Użytkownik wybiera opcję 2 z menu.
2. Użytkownik podaje ID nowego egzemplarza oraz ID książki.
3. Program sprawdza, czy książka o podanym ID istnieje, a ID egzemplarza jest wolne.
4. System dodaje nowy egzemplarz (ze statusem wolny) i wyświetla potwierdzenie.
[Alternatywnie - rozszerzenie <<extend>> do UC1]: Jeśli książki nie ma w bazie, program prosi o tytuł, imię i nazwisko autora, dodaje książkę, a następnie zapisuje egzemplarz. Jeśli ID nie jest liczbą, ID egzemplarza już istnieje lub tytuł nowej książki jest pusty, program wyświetla błąd i nie dodaje egzemplarza.

=========================================
UC3
=========================================
Nazwa; Dodaj czytelnika
Identyfikator (ID); UC3
Aktor; Użytkownik systemu
Opis; Zarejestrowanie nowego czytelnika w systemie bibliotecznym.
Warunki wstępne; Program musi być uruchomiony.
Warunki końcowe; Czytelnik zostaje zapisany w bazie, wyświetlany jest komunikat potwierdzający.
Sekwencja akcji; 
1. Użytkownik wybiera opcję 3 z menu.
2. Użytkownik wpisuje ID czytelnika, jego imię oraz nazwisko.
3. Program sprawdza, czy ID jest unikalne oraz czy imię i nazwisko nie są puste.
4. Program zapisuje czytelnika w bazie i wyświetla potwierdzenie.
[Alternatywnie]: Jeśli ID nie jest liczbą całkowitą, pojawia się błąd formatu i powrót do menu. Jeśli czytelnik o podanym ID już istnieje lub imię/nazwisko jest puste, program wyświetla komunikat błędu i nie dodaje czytelnika.

=========================================
UC4
=========================================
Nazwa; Wypożycz egzemplarz książki
Identyfikator (ID); UC4
Aktor; Użytkownik systemu
Opis; Przypisanie wolnego egzemplarza książki do wybranego czytelnika.
Warunki wstępne; Program musi być uruchomiony, a egzemplarz oraz czytelnik o podanych ID muszą znajdować się w bazie.
Warunki końcowe; Egzemplarz zmienia status na wypożyczony i zostaje przypisany do czytelnika; wyświetla się komunikat o wypożyczeniu.
Sekwencja akcji; 
1. Użytkownik wybiera opcję 4 z menu.
2. Użytkownik podaje ID egzemplarza, a następnie ID czytelnika.
3. Program sprawdza, czy oba obiekty istnieją i czy egzemplarz nie jest aktualnie wypożyczony.
4. Program przypisuje czytelnika do egzemplarza i informuje o wypożyczeniu.
[Alternatywnie]: Jeśli któreś ID nie jest liczbą całkowitą, brakuje ID w bazie lub egzemplarz jest już wypożyczony przez kogoś innego, program wyświetla komunikat o błędzie i blokuje wypożyczenie.

=========================================
UC5
=========================================
Nazwa; Zwróć egzemplarz książki
Identyfikator (ID); UC5
Aktor; Użytkownik systemu
Opis; Zwrot wypożyczonego wcześniej egzemplarza książki do biblioteki.
Warunki wstępne; Program musi być uruchomiony, a zwracany egzemplarz musi istnieć w bazie i być aktualnie wypożyczony.
Warunki końcowe; Egzemplarz odzyskuje status wolny, przypisanie czytelnika zostaje usunięte, wyświetla się komunikat o zwrocie.
Sekwencja akcji; 
1. Użytkownik wybiera opcję 5 z menu.
2. Użytkownik podaje ID zwracanego egzemplarza.
3. Program odnajduje egzemplarz i zwalnia przypisanego do niego czytelnika (ustawia status na wolny).
4. Program wyświetla komunikat o zwrocie.
[Alternatywnie]: Jeśli ID nie jest liczbą całkowitą, brak egzemplarza o takim ID w bazie lub egzemplarz nie był wypożyczony, program wyświetla błąd i nie wykonuje zwrotu.

=========================================
UC6
=========================================
Nazwa; Wyświetl listę obiektów (książki / egzemplarze / czytelnicy)
Identyfikator (ID); UC6
Aktor; Użytkownik systemu
Opis; Wypisanie w konsoli danych wszystkich zapisanych obiektów wybranego typu.
Warunki wstępne; Program musi być uruchomiony.
Warunki końcowe; Lista obiektów wraz ze statusami (dla egzemplarzy) zostaje wyświetlona w konsoli lub wyświetla się komunikat o braku danych.
Sekwencja akcji; 
1. Użytkownik wybiera w menu opcję 6 (książki), 7 (egzemplarze) lub 8 (czytelnicy).
2. Program pobiera odpowiednią listę z repozytorium.
3. Program wypisuje w konsoli dane wszystkich zapisanych obiektów danego typu (dla egzemplarzy pokazuje status wolny/wypożyczony i przez kogo).
[Alternatywnie]: Jeśli lista wybranego typu obiektów jest pusta, program wyświetla w konsoli informację o braku zapisanych pozycji (np. Brak ksiazek w systemie.).