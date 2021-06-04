#include <stdio.h>
#include <stdlib.h>

typedef char const *(*PTRFUN)();

typedef struct Tiger {
    PTRFUN* vtable;
    char const* name;
} Tiger;

char const* greet(void) {
  return "roar!";
}

char const* menu(void){
  return "zebre";
}

char const* name(void* this) {
  return ((Tiger *)(this))->name; 
}

PTRFUN vTable[3] = {
  (PTRFUN)name,
	(PTRFUN)greet,
	(PTRFUN)menu,
};


void* create(char const* name) {
  Tiger* tiger = (Tiger *)malloc(sizeof(Tiger));
  tiger->vtable = vTable;
  tiger->name = name;
}