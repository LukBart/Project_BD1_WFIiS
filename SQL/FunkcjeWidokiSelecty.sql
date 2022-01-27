-- DROP VIEW KsiazkiView;
CREATE OR REPLACE VIEW KsiazkiView AS
SELECT tytul,cena, opis, autor,id_ksiazka, g.id_gatunek, d.id_dzial, d.nazwa, d.id_ksiegarnia
FROM ksiazka
         JOIN gatunek g on g.id_gatunek = ksiazka.id_gatunek
         JOIN dzial d on d.id_dzial = g.id_dzial;

-------------------------
-- DROP FUNction liczbaPracownikow(id int);
CREATE OR REPLACE FUNCTION public.liczbaPracownikow ( id int)
    RETURNS bigint AS
$$
SELECT COUNT(id_pracownik)
FROM public.ksiegarnia
         JOIN kadra_pracownicza kp on ksiegarnia.id_ksiegarnia = kp.id_ksiegarnia
         JOIN pracownik p on kp.id_kadra_pracownicza = p.id_kadra_pracownicza
WHERE ksiegarnia.id_ksiegarnia = id;
$$LANGUAGE SQL;

-------------------------
-- DROP FUNCTION liczbaKsiazek(id int);
CREATE OR REPLACE FUNCTION public.liczbaKsiazek (id int)
    RETURNS bigint AS
$$
SELECT count(id_ksiazka)
FROM public.ksiegarnia
         JOIN dzial d on ksiegarnia.id_ksiegarnia = d.id_ksiegarnia
         JOIN gatunek g on d.id_dzial = g.id_dzial
         JOIN ksiazka k on g.id_gatunek = k.id_gatunek;
$$LANGUAGE sql;

-------------------------
-- DROP FUNCTION daneKsiegarni(id int);
CREATE or replace FUNCTION public.daneKsiegarni (id int)
    RETURNS TABLE (id_ksiegarnia int,nazwa varchar,ulica varchar,numer varchar,kod varchar,miejscowosc varchar,
                   id_wlasciciel int, imie varchar, nazwisko varchar, liczbapracownikow bigint, liczbaksiazek bigint)
AS
$$
SELECT id_ksiegarnia,nazwa,ulica,numer,kod,miejscowosc,ksiegarnia.id_wlasciciel,imie,nazwisko, public.liczbapracownikow(id), public.liczbaksiazek(id)
FROM ksiegarnia
         JOIN wlasciciel w on ksiegarnia.id_wlasciciel = w.id_wlasciciel
WHERE ksiegarnia.id_ksiegarnia = id;
$$LANGUAGE sql;

-------------------------

-- DROP VIEW ksiazkapracownik;
CREATE OR REPLACE VIEW ksiazkapracownik AS
SELECT k.tytul, k.autor,k.cena, k.id_ksiazka, id_kasjer, g.opis
FROM kasjer
         JOIN pracownik p on p.id_pracownik = kasjer.id_pracownik
         JOIN kadra_pracownicza kp on kp.id_kadra_pracownicza = p.id_kadra_pracownicza
         JOIN dzial d on kp.id_kadra_pracownicza = d.id_kadra_pracownicza
         JOIN gatunek g on d.id_dzial = g.id_dzial
         JOIN ksiazka k on g.id_gatunek = k.id_gatunek;
-- SELECT * FROM ksiazkapracownik;

-------------------------
CREATE OR REPLACE FUNCTION countksiazki (id int)
    RETURNS bigint AS
$$
SELECT count(id_ksiazka) FROM ksiazkapracownik WHERE id_kasjer = id;
$$ LANGUAGE sql;

-------------------------


