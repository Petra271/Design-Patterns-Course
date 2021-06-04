#include <stdio.h>
#include <stdlib.h>

typedef char const* (*PTRFUN)();

typedef struct Animal {
    char const* name;
    PTRFUN* vtable;
} Animal;

void animalPrintGreeting(Animal* animal) {
    printf("%s pozdravlja: %s\n", animal->name, animal->vtable[0]());
}

void animalPrintMenu(Animal* animal){
    printf("%s voli %s\n", animal->name, animal->vtable[1]());
}

PTRFUN animalTable[2] = {
	(PTRFUN)NULL,
	(PTRFUN)NULL,
};

Animal* initalizeMem(){
    Animal* animal = (Animal *)malloc(sizeof(Animal));
    animal->vtable = animalTable;
    return animal;
}

char const* dogGreet(void){
  return "vau!";
}

char const* dogMenu(void){
  return "kuhanu govedinu";
}

PTRFUN dogTable[2] = {
	(PTRFUN)dogGreet,
	(PTRFUN)dogMenu,
};

void constructDog(Animal* animal, char const* name){
    animal->vtable = dogTable;
    animal->name = name;
}

Animal* createDog(char const* name){
    constructDog(initalizeMem(), name);
}

char const* catGreet(void){
  return "mijau!";
}

char const* catMenu(void){
  return "konzerviranu tunjevinu";
}

PTRFUN catTable[2] = {
	(PTRFUN)catGreet,
	(PTRFUN)catMenu,
};

void constructCat(Animal* animal, char const* name){
    animal->vtable = catTable;
    animal->name = name;
}

Animal* createCat(char const* name){
    constructCat(initalizeMem(), name);
}

Animal* createDoggos(int n) {
    Animal *array = (Animal *)malloc(n * sizeof(Animal));
    Animal *p = NULL;

    for(p = array; p < array + n; p++) {
        constructDog(p, "doggo");
    }

    return array;
}

Animal stackCats(char const* name){
    Animal cat;
    cat.name = name;
    cat.vtable = catTable;
    return cat;
}

Animal *heapCats(char const* name){
    Animal *cat = (Animal *)malloc(sizeof(Animal));
    cat->name = name;
    cat->vtable = catTable;
    return cat;
}

void testAnimals(void){
  struct Animal* p1=createDog("Hamlet");
  struct Animal* p2=createCat("Ofelija");
  struct Animal* p3=createDog("Polonije");

  animalPrintGreeting(p1);
  animalPrintGreeting(p2);
  animalPrintGreeting(p3);

  animalPrintMenu(p1);
  animalPrintMenu(p2);
  animalPrintMenu(p3);

  struct Animal p4 = stackCats("Mimi");
  animalPrintGreeting(&p4);

  struct Animal* p5 = heapCats("Lili");
  animalPrintMenu(p5);

  Animal *p6 = createDoggos(5);

  printf(p6[2].name);
  // printf(p6[5].name);
 
  free(p1); free(p2); free(p3); free(p5); free(p6);
}

int main(){
    testAnimals();
}


