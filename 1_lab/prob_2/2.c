#include <stdio.h>
#include <stdlib.h>

struct unary_function uf;

typedef double (*PTRFUN)(struct unary_function * unary_function, double x);

typedef enum {false, true} bool;

/* UNARY_FUNCTION */

typedef struct unary_function {
    PTRFUN* vtable;
    int lower_bound;
    int upper_bound; 
} Unary_function;

double value_at(Unary_function * uf, double x) {
    return uf->vtable[0](uf, x);
}

double neg_value_at(Unary_function * uf, double x){
    return -(uf->vtable[0](uf, x));
}

PTRFUN vtable[2] = {
	(PTRFUN)NULL,
	(PTRFUN)NULL
};

void construct_ufun(Unary_function* uf, int lb, int ub){
    uf->lower_bound = lb;
    uf->upper_bound = ub;
    uf->vtable = vtable;
}

Unary_function* create_ufun(int lb, int ub){
    Unary_function* unary_function = (Unary_function *)malloc(sizeof(Unary_function));
    construct_ufun(unary_function, lb, ub);
 
}

/* LINEAR FUNCTION */

typedef struct linear_function {
    Unary_function;
    double a;
    double b;
} Linear_function;

double linear_value_at(Unary_function * uf, double x){
    Linear_function *lf = (Linear_function *)uf;
    return lf->a * x + lf->b;
}

PTRFUN linear_vtable[2] = {
	(PTRFUN)linear_value_at,
	(PTRFUN)neg_value_at
};

void construct_linear(Linear_function* lf, int lb, int ub, double a, double b){
    construct_ufun((Unary_function *)lf, lb, ub);
    lf->a = a;
    lf->b = b;
    lf->vtable = linear_vtable;
}

Unary_function* create_linear(int lb, int ub, double a, double b){
    Linear_function* linear_function = (Linear_function *)malloc(sizeof(Linear_function));
    construct_linear(linear_function, lb, ub, a, b);
}

/* SQUARE FUNCTION */

typedef struct square_function {
    Unary_function;
} Square_function;

double square_value_at(Square_function * sf, double x){
    return x*x;
}

PTRFUN square_vtable[2] = {
	(PTRFUN)square_value_at,
	(PTRFUN)neg_value_at
};

void construct_square(Square_function* sf, int lb, int ub){
    construct_ufun((Unary_function *)sf, lb, ub);
    sf->vtable = square_vtable;
}

Unary_function* create_square(int lb, int ub){
    Square_function* square_function = (Square_function *)malloc(sizeof(Square_function));
    construct_square(square_function, lb, ub);
}


void tabulate(Unary_function * uf) {
    for(int x = uf->lower_bound; x <= uf->upper_bound; x++) {
        printf("f(%d)=%lf\n", x, value_at(uf, x));
    }
};

static bool same_functions_for_ints(Unary_function * f1, Unary_function * f2, double tolerance) {
    if (f1->lower_bound != f2->lower_bound)
        return false;
    if (f1->upper_bound != f2->upper_bound)
        return false;
    for (int x = f1->lower_bound; x <= f1->upper_bound; x++)
    {
        double delta = value_at(f1, x) - value_at(f2, x);
        if (delta < 0)
            delta = -delta;
        if (delta > tolerance)
            return false;
    }
    return true;
};

int main(){
    Unary_function *f1 = create_square(-2, 2);
    tabulate(f1);
    Unary_function *f2 = create_linear(-2, 2, 5, -2);
    tabulate(f2);

    printf("f1==f2: %s\n", same_functions_for_ints(f1, f2, 1E-6) ? "DA" : "NE");
    printf("neg_val f2(1) = %lf\n", neg_value_at(f2, 1.0));
    
    free(f1);
    free(f2);
    return 0;
}
