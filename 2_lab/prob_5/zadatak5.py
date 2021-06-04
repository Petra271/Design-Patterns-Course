from abc import abstractmethod
import time
import statistics
from datetime import datetime


class Izvor:
    @abstractmethod
    def citaj(self) -> None: pass


class TipkovnickIzvor(Izvor):
    def citaj(self):
        try:
            return int(input())
        except ValueError:
            print('Moguce unositi samo nenegativne brojeve.')
            return None
        except KeyboardInterrupt:
            return 'kraj'


class DatotecniIzvor(Izvor):
    def __init__(self, datoteka) -> None:
        self.datoteka = datoteka

    def citaj(self):
        try:
            broj = self.datoteka.readline()
            if broj != '':
                return int(broj)
            else:
                return 'kraj'
        except ValueError:
            return None


class Promatrac:
    def azuriraj(self, kolekcija) -> None: pass


class Subjekt:
    @abstractmethod
    def obavijesti_promatrace(self) -> None: pass

    @abstractmethod
    def dodaj_promatraca(self, promatrac) -> None: pass

    @abstractmethod
    def odjavi_promatraca(self, promatrac) -> None: pass


class SlijedBrojeva(Subjekt):
    def __init__(self, izvor: Izvor) -> None:
        self.list = []
        self.promatraci = []
        self.izvor = izvor

    def kreni(self):
        while True:
            broj = self.izvor.citaj()
            if broj == 'kraj':
                print('Izvor iscrpljen.')
                exit()
            if broj is None or broj < 0:
                continue
            self.list.append(broj)
            self.obavijesti_promatrace()
            try:
                time.sleep(1)
            except KeyboardInterrupt:
                exit()

    def obavijesti_promatrace(self):
        for p in self.promatraci:
            p.azuriraj(self.dohvati())

    def dodaj_promatraca(self, promatrac):
        self.promatraci.append(promatrac)

    def odjavi_promatraca(self, promatrac):
        if promatrac in self.promatraci:
            self.promatraci.remove(promatrac)

    def dohvati(self):
        return self.list


class SumaPromatrac(Promatrac):
    def azuriraj(self, kolekcija) -> None:
        print(f'Suma: {sum(kolekcija)}')


class MedijanPromatrac(Promatrac):
    def azuriraj(self, kolekcija) -> None:
        print(f'Medijan: {statistics.median(kolekcija)}')


class ProsjekPromatrac(Promatrac):
    def azuriraj(self, kolekcija) -> None:
        print(f'Prosjek: {statistics.mean(kolekcija)}')


class DatotekaPromatrac(Promatrac):
    def __init__(self, datoteka) -> None:
        self.datoteka = datoteka

    def azuriraj(self, kolekcija) -> None:
        with open(self.datoteka, 'a') as d:
            d.write(', '.join([str(n) for n in kolekcija]))
            date_time = datetime.now()
            d.write(date_time.strftime('\n%d/%m/%Y %H:%M:%S\n\n'))


if __name__ == '__main__':
    promatrac1 = SumaPromatrac()
    promatrac2 = ProsjekPromatrac()
    promatrac3 = MedijanPromatrac()
    promatrac4 = DatotekaPromatrac('datoteka.txt')

    # izvor = TipkovnickIzvor()
    d = open('brojevi.txt')
    izvor = DatotecniIzvor(d)

    slijed_brojeva = SlijedBrojeva(izvor)

    slijed_brojeva.dodaj_promatraca(promatrac1)
    slijed_brojeva.dodaj_promatraca(promatrac2)
    slijed_brojeva.dodaj_promatraca(promatrac3)
    slijed_brojeva.dodaj_promatraca(promatrac4)

    slijed_brojeva.kreni()
    d.close()
