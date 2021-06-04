#include <stdio.h>

class Base{
public:
  Base() {
    metoda();
  }

  virtual void virtualnaMetoda() {
    printf("ja sam bazna implementacija!\n");
  }

  void metoda() {
    printf("Metoda kaze: ");
    virtualnaMetoda();
  }
};

class Derived: public Base{
public:
  Derived(): Base() {
    metoda();
  }
  virtual void virtualnaMetoda() {
    printf("ja sam izvedena implementacija!\n");
  }
};

int main(){
  Derived* pd=new Derived();
  pd->metoda();
}

// budući da se konstruktor nadklase poziva prije konstruktora podklase,
// metoda koja se poziva je metoda iz klase Base jer objekt klase Derived još ne postoji
// nakon što je objekt klase Derived stvoren i ima svoju virtualnu tablice koja pokazuje na 
// ispravne implementacije metoda, dobivamo očekivani rezultat