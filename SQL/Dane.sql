insert into public.Wlasciciel (id_wlasciciel, Imie, Nazwisko)
values (DEFAULT, 'Jerzy', 'Wróblewski');

insert into public.Ksiegarnia (id_ksiegarnia, Nazwa, Ulica, Numer, Kod, Miejscowosc, id_wlasciciel)
values (DEFAULT, 'Sowa', 'Mickiewicza', '12b', '23-152', 'Krakow', 1);

insert into public.Kadra_Pracownicza (id_kadra_pracownicza, id_ksiegarnia, Opis)
values (DEFAULT, 1, 'Kadra Pierwsza'),
       (DEFAULT, 1, 'Kadra Druga');

insert into public.Pracownik (id_pracownik, Nazwisko, Imie, Plec, Telefon, Email, id_kadra_pracownicza)
values (DEFAULT, 'Marcin', 'Szymczak', 'm', '654658967', 'mszym@asf.com', 1),
       (DEFAULT, 'Joanna', 'Baranowska', 'k', '619667459', 'joanna@asf.com', 2),
       (DEFAULT, 'Maciej', 'Szczepanski', 'm', NULL, NULL, 1),
       (DEFAULT, 'Czesław', 'Wróbel', 'm', '969422852', NULL, 1),
       (DEFAULT, 'Alex', 'Górska', NULL, '646218954', 'alex@fks.com', 1),
       (DEFAULT, 'Wanda', 'Krawczyk', 'k', '213813068', NULL, 1),
       (DEFAULT, 'Renata', 'Urbanska', 'k', '785110574', NULL, 1),
       (DEFAULT, 'Wiesława', 'Tomaszewska', 'k', NULL, 'wtomaszewska@gkame.com', 2),
       (DEFAULT, 'Bożena', 'Baranowska', 'k', '125658571', NULL, 2),
       (DEFAULT, 'Ewelina', 'Malinowska', 'k', '961826606', NULL, 2),
       (DEFAULT, 'Anna', 'Krajewska', 'k', '411529543', 'akrajweska@gaiej.com', 2);
-- (DEFAULT, 'Mieczyslaw','Zajac','m','891231842',),
-- (DEFAULT, 'Wieslaw','Przybylski','m','957340068',),
-- (DEFAULT, 'Dorota','Tomaszewska','k','457692334',);

insert into public.Menedzer (id_menedzer, id_pracownik, Biuro)
values (DEFAULT, 1, 'Na piętrze'),
       (DEFAULT, 2, 'Na parterze');

insert into public.Kasjer (id_kasjer, id_pracownik, Stanowisko)
values (DEFAULT, 3, 1),
       (DEFAULT, 4, 2),
       (DEFAULT, 5, 2),
       (DEFAULT, 8, 3),
       (DEFAULT, 9, 4),
       (DEFAULT, 10, 4);

insert into public.Sprzatacz (id_sprzatacz, id_pracownik, Sprzet)
values (DEFAULT, 6, 'Zestaw do sprzątania regałów'),
       (DEFAULT, 7, 'Zestaw do sprzątania podłogi'),
       (DEFAULT, 11, 'Zestaw do sprzątania regałów i podłogi');

insert into public.Dzial (id_dzial, id_ksiegarnia, id_kadra_pracownicza, Nazwa)
values (DEFAULT, 1, 1, 'Pierwsza część na parterze'),
       (DEFAULT, 1, 1, 'Druga część na parterze'),
       (DEFAULT, 1, 2, 'Piętro');

insert into public.Gatunek (id_gatunek, Opis, id_dzial)
values (DEFAULT, 'fantasy', 1),
       (DEFAULT, 'przygodowa', 1),
       (DEFAULT, 'romans', 2),
       (DEFAULT, 'kryminał', 3),
       (DEFAULT, 'dramat', 3);

insert into public.Ksiazka (id_ksiazka, Autor, Tytul, id_gatunek, Cena)
values (DEFAULT, 'Amanda Piotrowska', 'Ksiażka o Smokach', 1, 37.99),
       (DEFAULT, 'Żaneta Borkowska', 'Szaman Bez Skaz', 1, 36.99),
       (DEFAULT, 'Gabriela Sikorska', 'Wstęga Rzeczywistości', 2, 25.99),
       (DEFAULT, 'Konrad Mazurek', 'Żony i Czarownice', 2, 42.99),
       (DEFAULT, 'Asia Maciejewska', 'Tajemniczy Wielbiciel', 3, 47.99),
       (DEFAULT, 'Alan Maciejewski', 'Przyjaciel o Ciemnych Włosach', 3, 25.99),
       (DEFAULT, 'Natalia Borkowska', 'Miłosc Północy', 3, 40.99),
       (DEFAULT, 'Arkadiusz Mróz', 'Węże Piekła', 4, 41.99),
       (DEFAULT, 'Lidia Krajewska', 'Rycerz Zwierząt', 4, 34.99),
       (DEFAULT, 'Ksawery Szymański', 'Zdrajca Oceanu', 5, 41.99),
       (DEFAULT, 'Bartosz Szymczak', 'Przygotowany na Mgle', 5, 46.99);