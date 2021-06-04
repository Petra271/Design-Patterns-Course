#include <iostream>

class CoolClass {
public:
    virtual void set(int x) { x_ = x; };
    virtual int get() { return x_; };

private:
    int x_;
};

class PlainOldClass {
public:
    void set(int x) { x_ = x; };
    int get() { return x_; };

private:
    int x_;
};

int main() {
    std::cout << sizeof(PlainOldClass) << "\n";
    std::cout << sizeof(CoolClass);

}

/* prevoditelj nadopunjava objekte kako bi poravnao mem. adrese i smanjio broj CPU ciklusa
   procesor čita 8 po 8 bajtova (ako je 64-bitni), 
   pointer na virtualnu tablicu zauzima 8 bajtova, a x je tipa int i zauzima 4 bajta
   prevoditelj dodaje padding od 4 bajta da bi početne adrese bile djeljive s 8 */

