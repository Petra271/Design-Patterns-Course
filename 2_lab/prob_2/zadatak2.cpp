#include <iostream>
#include <stdlib.h>
#include <string.h>
#include <vector>
#include <set>

template <typename Iterator, typename Predicate>
Iterator mymax(Iterator first, Iterator last, Predicate pred) {

    for (Iterator iter = first; iter != last; iter++) {
        int c = pred(*iter, *first);
        if (c == 1) first = iter;
    }

    return first;
}

int gt_int(const int x, const int y){
    return x > y;
}

int gt_char(const char x, const char y){
    return x > y;
}

int gt_str(const char *x, const char *y){
    int res = strcmp(x, y);
    return res <= 0 ? 0 : 1;
};

int main(){
    int arr_int[] = {1, 3, 5, 7, 4, 6, 9, 2, 0};
    const int *max1 = mymax(&arr_int[0],
            &arr_int[sizeof(arr_int) / sizeof(*arr_int)], gt_int);
    std::cout << *max1 << "\n";

    char arr_char[] = "Suncana strana ulice";
    const char *max2 = mymax(&arr_char[0],
            &arr_char[sizeof(arr_char) / sizeof(*arr_char)], gt_char);
    std::cout << *max2 << "\n";

    const char *arr_str[] = {
        "Gle", "malu", "vocku", "poslije", "kise",
        "Puna", "je", "kapi", "pa", "ih", "njise"};
    const char **max3 = mymax(&arr_str[0],
        &arr_str[sizeof(arr_str) / sizeof(*arr_str)], gt_str);
    std::cout << *max3 << "\n";

    std::vector<char> vect{ 'a', 'b', 'z', 'S', 'm' };
    char max4 = *(mymax(vect.begin(), vect.end(), gt_char));
    std::cout << max4 << "\n";

    std::set<const char*> set{ "sunce", "mjesec", "zemlja", "svemir" };
    const char *max5 = *(mymax(set.begin(), set.end(), gt_str));
    std::cout << max5 << "\n";

}

// prednosti: komplementarna primjenjivost, nadogradivost s obzirom na različite vrste
// spremnika, mogucnost primjene fje. mymax primijeniti na sve podatke nad kojima je definiran poredak
// nedostatak je nuznost ponovnog prevodenja ako zelimo promijeniti parametar predloska