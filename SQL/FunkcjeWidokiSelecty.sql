DROP VIEW IF EXISTS KsiazkiView;
CREATE OR REPLACE VIEW KsiazkiView AS
SELECT tytul,cena, opis, autor,id_ksiazka, g.id_gatunek, d.id_dzial, d.nazwa, d.id_ksiegarnia
FROM ksiazka
         JOIN gatunek g on g.id_gatunek = ksiazka.id_gatunek
         JOIN dzial d on d.id_dzial = g.id_dzial;

-------------------------

DROP FUNCTION  IF EXISTS pracownikmaxid();
CREATE OR REPLACE FUNCTION pracownikmaxid()
    RETURNS INTEGER AS
$$
SELECT max(id_pracownik) FROM pracownik;
$$ language sql;

-------------------------
DROP FUNCTION IF EXISTS liczbaPracownikow(id int);
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
DROP FUNCTION IF EXISTS liczbaksiazek();
CREATE OR REPLACE FUNCTION public.liczbaksiazek ()
    RETURNS bigint AS
$$
SELECT count(id_ksiazka)
FROM public.ksiegarnia
         JOIN dzial d on ksiegarnia.id_ksiegarnia = d.id_ksiegarnia
         JOIN gatunek g on d.id_dzial = g.id_dzial
         JOIN ksiazka k on g.id_gatunek = k.id_gatunek;
$$LANGUAGE sql;

-------------------------
DROP FUNCTION IF EXISTS daneKsiegarni(id int);
CREATE OR REPLACE FUNCTION public.daneKsiegarni (id int)
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

DROP VIEW IF EXISTS ksiazkapracownik;
CREATE OR REPLACE VIEW ksiazkapracownik AS
SELECT k.tytul, k.autor,k.cena, k.id_ksiazka, id_kasjer, g.opis
FROM kasjer
         JOIN pracownik p on p.id_pracownik = kasjer.id_pracownik
         JOIN kadra_pracownicza kp on kp.id_kadra_pracownicza = p.id_kadra_pracownicza
         JOIN dzial d on kp.id_kadra_pracownicza = d.id_kadra_pracownicza
         JOIN gatunek g on d.id_dzial = g.id_dzial
         JOIN ksiazka k on g.id_gatunek = k.id_gatunek;
SELECT * FROM ksiazkapracownik;

-------------------------
DROP FUNCTION IF EXISTS countksiazki(id int);
CREATE OR REPLACE FUNCTION countksiazki (id int)
    RETURNS bigint AS
$$
SELECT count(id_ksiazka) FROM ksiazkapracownik WHERE id_kasjer = id;
$$ LANGUAGE sql;

-------------------------

DROP VIEW IF EXISTS raportobrot;
CREATE OR REPLACE VIEW raportobrot AS
    SELECT ks.nazwa, w.nazwisko, w.imie, o.obrot
        FROM (
            SELECT sum(c.obrotksiazki) as obrot
            FROM (SELECT count(id_kopia) * cena as obrotksiazki
                FROM ksiazka k
                JOIN kopia_ksiazki kk on k.id_ksiazka = kk.id_ksiazka
            GROUP BY k.tytul, k.cena)c)
        o, ksiegarnia ks
        JOIN dzial d on ks.id_ksiegarnia = d.id_ksiegarnia
        JOIN gatunek g on d.id_dzial = g.id_dzial
        JOIN ksiazka k on g.id_gatunek = k.id_gatunek
        JOIN kopia_ksiazki kk on k.id_ksiazka = kk.id_ksiazka
        JOIN wlasciciel w on w.id_wlasciciel = ks.id_wlasciciel
        GROUP BY  ks.id_ksiegarnia, o.obrot, ks.id_ksiegarnia, w.nazwisko, w.imie;

-------------------------

DROP VIEW IF EXISTS raportPracownikow;
CREATE OR REPLACE VIEW raportPracownikow AS
SELECT ks.nazwa, count(id_kasjer) as kasjerzy, count(id_sprzatacz) AS sprzatacze, count(id_menedzer) menedzerowie
FROM ksiegarnia ks
         JOIN kadra_pracownicza kp on ks.id_ksiegarnia = kp.id_ksiegarnia
         JOIN pracownik p on kp.id_kadra_pracownicza = p.id_kadra_pracownicza
         LEFT JOIN kasjer k on p.id_pracownik = k.id_pracownik
         LEFT JOIN menedzer m on p.id_pracownik = m.id_pracownik
         LEFT JOIN sprzatacz s on p.id_pracownik = s.id_pracownik
GROUP BY ks.nazwa;

SELECT * FROM raportPracownikow;

-------------------------

DROP VIEW IF EXISTS raportkasjer;
CREATE OR REPLACE VIEW raportkasjer AS
SELECT k2.nazwa, kasjer.id_kasjer,imie, nazwisko, count(t.id_transakcja), sum(cena)
FROM kasjer
         LEFT JOIN transakcja t ON kasjer.id_kasjer = t.id_kasjer
         LEFT JOIN kopia_ksiazki kk on t.id_transakcja = kk.id_transakcja
         LEFT JOIN ksiazka k on k.id_ksiazka = kk.id_ksiazka
         JOIN pracownik p on p.id_pracownik = kasjer.id_pracownik
         JOIN kadra_pracownicza kp on kp.id_kadra_pracownicza = p.id_kadra_pracownicza
         JOIN ksiegarnia k2 on k2.id_ksiegarnia = kp.id_ksiegarnia
GROUP BY kasjer.id_kasjer, imie, nazwisko, k2.id_ksiegarnia
ORDER BY kasjer.id_kasjer;

-- SELECT * FROM raportkasjer;
-------------------------

DROP VIEW IF EXISTS raporttransakcja;
CREATE OR REPLACE VIEW raportTransakcja AS
SELECT p.imie, p.nazwisko, k2.stanowisko, t.id_transakcja, t.data, sum(cena)
FROM transakcja t
         JOIN kopia_ksiazki kk on t.id_transakcja = kk.id_transakcja
         JOIN ksiazka k on k.id_ksiazka = kk.id_ksiazka
         JOIN kasjer k2 on k2.id_kasjer = t.id_kasjer
         JOIN pracownik p on p.id_pracownik = k2.id_pracownik
GROUP BY p.imie, p.nazwisko, k2.stanowisko, t.id_transakcja, t.data
ORDER BY data;



