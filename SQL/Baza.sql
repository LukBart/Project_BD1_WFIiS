
CREATE TABLE public.Wlasciciel (
                                   id_wlasciciel SERIAL NOT NULL,
                                   Imie VARCHAR(50) NOT NULL,
                                   Nazwisko VARCHAR(50) NOT NULL,
                                   CONSTRAINT wlasciciel_pk PRIMARY KEY (id_wlasciciel)
);


CREATE TABLE public.Ksiegarnia (
                                   id_ksiegarnia SERIAL NOT NULL,
                                   Nazwa VARCHAR(50) NOT NULL,
                                   Ulica VARCHAR(50) NOT NULL,
                                   Numer VARCHAR(4) NOT NULL,
                                   Kod VARCHAR(6) NOT NULL,
                                   Miejscowosc VARCHAR(50) NOT NULL,
                                   id_wlasciciel SERIAL NOT NULL,
                                   CONSTRAINT ksiegarnia_pk PRIMARY KEY (id_ksiegarnia)
);


CREATE TABLE public.Kadra_Pracownicza (
                                          id_kadra_pracownicza SERIAL NOT NULL,
                                          id_ksiegarnia SERIAL NOT NULL,
                                          Opis VARCHAR(50) NOT NULL,
                                          CONSTRAINT kadra_pracownicza_pk PRIMARY KEY (id_kadra_pracownicza)
);


CREATE TABLE public.Pracownik (
                                  id_pracownik SERIAL NOT NULL,
                                  Nazwisko VARCHAR(50) NOT NULL,
                                  Imie VARCHAR(50) NOT NULL,
                                  Plec VARCHAR(1),
                                  Telefon VARCHAR(9),
                                  Email VARCHAR(50),
                                  id_kadra_pracownicza SERIAL NOT NULL,
                                  CONSTRAINT pracownik_pk PRIMARY KEY (id_pracownik)
);


CREATE TABLE public.Dzial (
                              id_dzial SERIAL NOT NULL,
                              id_ksiegarnia SERIAL NOT NULL,
                              id_kadra_pracownicza SERIAL NOT NULL,
                              Nazwa VARCHAR(50) NOT NULL,
                              CONSTRAINT dzial_pk PRIMARY KEY (id_dzial)
);


CREATE TABLE public.Kasjer (
                               id_kasjer SERIAL NOT NULL,
                               id_pracownik SERIAL NOT NULL,
                               Stanowisko INTEGER NOT NULL,
                               CONSTRAINT kasjer_pk PRIMARY KEY (id_kasjer)
);


CREATE TABLE public.Transakcja (
                                   id_transakcja SERIAL NOT NULL,
                                   id_kasjer SERIAL,
                                   Data TIMESTAMP NOT NULL,
                                   CONSTRAINT transakcja_pk PRIMARY KEY (id_transakcja)
);


CREATE TABLE public.Menedzer (
                                 id_menedzer SERIAL NOT NULL,
                                 id_pracownik SERIAL NOT NULL,
                                 Biuro VARCHAR(50) NOT NULL,
                                 CONSTRAINT menedzer_pk PRIMARY KEY (id_menedzer)
);


CREATE TABLE public.Sprzatacz (
                                  id_sprzatacz SERIAL NOT NULL,
                                  id_pracownik SERIAL NOT NULL,
                                  Sprzet VARCHAR(100) NOT NULL,
                                  CONSTRAINT sprzatacz_pk PRIMARY KEY (id_sprzatacz)
);


CREATE TABLE public.Gatunek (
                                id_gatunek SERIAL NOT NULL,
                                id_dzial SERIAL NOT NULL,
                                Opis VARCHAR(50) NOT NULL,
                                CONSTRAINT gatunek_pk PRIMARY KEY (id_gatunek)
);


CREATE TABLE public.Ksiazka (
                                id_ksiazka SERIAL NOT NULL,
                                id_gatunek SERIAL NOT NULL,
                                Autor VARCHAR(50) NOT NULL,
                                Tytul VARCHAR(50) NOT NULL,
                                Cena DOUBLE PRECISION NOT NULL,
                                CONSTRAINT ksiazka_pk PRIMARY KEY (id_ksiazka)
);


CREATE TABLE public.Kopia_Ksiazki (
                                      id_kopia SERIAL NOT NULL,
                                      id_transakcja SERIAL NOT NULL,
                                      id_ksiazka SERIAL NOT NULL,
                                      CONSTRAINT kopia_ksiazki_pk PRIMARY KEY (id_kopia)
);


ALTER TABLE public.Ksiegarnia ADD CONSTRAINT wlasciciel_ksiegarnia_fk
    FOREIGN KEY (id_wlasciciel)
        REFERENCES public.Wlasciciel (id_wlasciciel)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE public.Dzial ADD CONSTRAINT ksiegarnia_dzial_fk
    FOREIGN KEY (id_ksiegarnia)
        REFERENCES public.Ksiegarnia (id_ksiegarnia)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE public.Kadra_Pracownicza ADD CONSTRAINT ksiegarnia_kadra_pracownicza_fk
    FOREIGN KEY (id_ksiegarnia)
        REFERENCES public.Ksiegarnia (id_ksiegarnia)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE public.Pracownik ADD CONSTRAINT kadra_pracownicza_pracownik_fk
    FOREIGN KEY (id_kadra_pracownicza)
        REFERENCES public.Kadra_Pracownicza (id_kadra_pracownicza)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE public.Dzial ADD CONSTRAINT kadra_pracownicza_dzial_fk
    FOREIGN KEY (id_kadra_pracownicza)
        REFERENCES public.Kadra_Pracownicza (id_kadra_pracownicza)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE public.Kasjer ADD CONSTRAINT pracownik_kasjer_fk
    FOREIGN KEY (id_pracownik)
        REFERENCES public.Pracownik (id_pracownik)
        ON DELETE CASCADE
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE public.Sprzatacz ADD CONSTRAINT pracownik_sprzatacz_fk
    FOREIGN KEY (id_pracownik)
        REFERENCES public.Pracownik (id_pracownik)
        ON DELETE CASCADE
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE public.Menedzer ADD CONSTRAINT pracownik_menedzer_fk
    FOREIGN KEY (id_pracownik)
        REFERENCES public.Pracownik (id_pracownik)
        ON DELETE CASCADE
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE public.Gatunek ADD CONSTRAINT dzial_gatunek_fk
    FOREIGN KEY (id_dzial)
        REFERENCES public.Dzial (id_dzial)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE public.Transakcja ADD CONSTRAINT kasjer_transakcja_fk
    FOREIGN KEY (id_kasjer)
        REFERENCES public.Kasjer (id_kasjer)
        ON DELETE SET NULL
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE public.Kopia_Ksiazki ADD CONSTRAINT transakcja_kopia_ksiazki_fk
    FOREIGN KEY (id_transakcja)
        REFERENCES public.Transakcja (id_transakcja)
        ON DELETE CASCADE
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE public.Ksiazka ADD CONSTRAINT gatunek_ksiazka_fk
    FOREIGN KEY (id_gatunek)
        REFERENCES public.Gatunek (id_gatunek)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE public.Kopia_Ksiazki ADD CONSTRAINT ksiazka_kopia_ksiazki_fk
    FOREIGN KEY (id_ksiazka)
        REFERENCES public.Ksiazka (id_ksiazka)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
        NOT DEFERRABLE;