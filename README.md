UC1: Dodaj książkę do katalogu
Warunki początkowe: Program musi być uruchomiony.
Scenariusz główny: Użytkownik wybiera opcję 1 z menu, a następnie podaje ID książki, tytuł, imię autora oraz nazwisko autora. Program sprawdza, czy takie ID nie jest już zajęte i czy tytuł nie jest pusty, po czym zapisuje książkę w bazie i wyświetla komunikat o dodaniu książki.
Scenariusze alternatywne: Jeśli użytkownik jako ID nie poda liczby całkowitej, wyświetli się błąd formatu i program wróci do menu. Jeśli książka o podanym ID jest już w bazie albo tytuł jest pusty, program wyświetli odpowiedni komunikat błędu i nie doda książki.

UC2: Dodaj egzemplarz książki
Warunki początkowe: Program musi być uruchomiony.
Scenariusz główny: Użytkownik wybiera opcję 2 z menu, a następnie podaje ID nowego egzemplarza oraz ID książki, której ten egzemplarz dotyczy. Jeśli książka o podanym ID jest już zapisana w bazie, a ID egzemplarza jest wolne, system dodaje nowy egzemplarz (ze statusem wolny) i wyświetla potwierdzenie.
Scenariusze alternatywne: Jeśli książki o podanym ID nie ma jeszcze w bazie (rozszerzenie <<extend>> do UC1), program informuje o braku tytułu i prosi użytkownika o podanie tytułu, imienia autora oraz nazwiska autora, dodaje książkę, a następnie zapisuje egzemplarz. Jeśli użytkownik jako ID nie poda liczby, pojawi się informacja o błędzie i wyświetli się menu. Jeśli użytkownik poda ID egzemplarza, które już istnieje w bazie (albo zostawi pusty tytuł przy dodawaniu nowej książki), program wyświetli informację o błędzie i nie doda egzemplarza.

UC3: Dodaj czytelnika
Warunki początkowe: Program musi być uruchomiony.
Scenariusz główny: Użytkownik wybiera opcję 3 z menu, a następnie wpisuje ID czytelnika, jego imię oraz nazwisko. Program sprawdza, czy ID jest unikalne oraz czy imię i nazwisko nie są puste, po czym zapisuje czytelnika w bazie i wyświetla potwierdzenie.
Scenariusze alternatywne: Jeśli ID nie jest liczbą całkowitą, pojawi się błąd formatu i powrót do menu. Jeśli czytelnik o podanym ID już istnieje w bazie albo imię/nazwisko zostało puste, program wyświetli komunikat błędu i nie doda czytelnika.

UC4: Wypożycz egzemplarz książki
Warunki początkowe: Program musi być uruchomiony, a egzemplarz oraz czytelnik o podanych przez użytkownika ID muszą być wcześniej dodani do bazy
Scenariusz główny: Użytkownik wybiera opcję 4 z menu, podaje ID egzemplarza, a następnie ID czytelnika. Program sprawdza, czy oba obiekty istnieją i czy egzemplarz nie jest aktualnie wypożyczony, po czym przypisuje czytelnika do egzemplarza i informuje o wypożyczeniu.
Scenariusze alternatywne: Jeśli któreś ID nie jest liczbą całkowitą albo brakuje podanego ID egzemplarza lub czytelnika w bazie, program informuje o błędzie i nie wypożycza egzemplarza. Jeśli podany egzemplarz jest już obecnie wypożyczony przez kogoś innego, program również wyświetla komunikat o błędzie i blokuje wypożyczenie.

UC5: Zwróć egzemplarz książki
Warunki początkowe: Program musi być uruchomiony, a zwracany egzemplarz musi istnieć w bazie i być aktualnie wypożyczony.
Scenariusz główny: Użytkownik wybiera opcję 5 z menu i podaje ID zwracanego egzemplarza. Program odnajduje egzemplarz, zwalnia przypisanego do niego czytelnika (ustawia status na wolny) i wyświetla komunikat o zwrocie.
Scenariusze alternatywne: Jeśli ID nie jest liczbą całkowitą lub w bazie nie ma egzemplarza o takim ID, program wyświetla błąd. Jeśli egzemplarz leży w bibliotece i nie był wypożyczony, program informuje o tym i nie wykonuje zwrotu.

UC6: Wyświetl listę obiektów (książki / egzemplarze / czytelnicy)
Warunki początkowe: Program musi być uruchomiony.
Scenariusz główny: Użytkownik wybiera w menu opcję 6 (wypisz książki), 7 (wypisz egzemplarze) lub 8 (wypisz czytelników). Program pobiera odpowiednią listę z repozytorium i wypisuje w konsoli dane wszystkich zapisanych obiektów danego typu (w przypadku egzemplarzy pokazuje też, czy są wolne, czy wypożyczone i przez kogo).
Scenariusze alternatywne: Jeśli lista wybranego typu obiektów jest pusta, program wyświetla w konsoli informację o braku zapisanych pozycji (np. Brak ksiazek w systemie.).

Kwestia dodania obsługi bazy danych:

Ponieważ klasa Bibliotekarz nie zalezy od BibliotekaRepoArray, tylko od dowolnej implementacji interfejsu
BlibliotekaRepository, dzieki temu nie musi wiedziec w jaki sposob metody sa realizowane, wystarczy ze instnieja,
to umozliwia latwe przejscie na baze danych, wystrczy zaimplementowac interfejs obslugujacy baze danych i w Main 
przekazac do konstruktora klasy Bibliotekarz instancje nowej implementacji.
