#include <stdio.h>
#include <stdlib.h>

typedef char const *(*PTRFUN)();

typedef struct Parrot {
    PTRFUN* vtable;
    char const* name;
} Parrot;


char const* greet(void) {
  return "ciao!";
}

char const* menu(void){
  return "orahe";
}

char const* name(void* this) {
  return ((Parrot *)(this))->name; 
}

PTRFUN vTable[3] = {
    (PTRFUN)name,
	(PTRFUN)greet,
	(PTRFUN)menu,
};

void* create(char const* name) {
    Parrot* parrot = (Parrot *)malloc(sizeof(Parrot));
    parrot->vtable = vTable;
    parrot->name = name;
}