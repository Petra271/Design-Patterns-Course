#include <stdio.h>
#include <stdlib.h>
#include <string.h>

const void* mymax(
    const void *base, size_t nmemb, size_t size,
    int (*compar)(const void *, const void *)) {

    unsigned char *ptr = (unsigned char *)base;

    for (int i = 1; i < nmemb; i++){
        int c = compar(ptr + i * size, ptr);
        if (c == 1)
            *ptr = *(ptr + i * size);
    }

    return (void *)(ptr);
}

int gt_int(const void *x, const void *y){
    int *n1 = (int *)x;
    int *n2 = (int *)y;
    return *n1 > *n2;
}

int gt_char(const void *x, const void *y){
    char *c1 = (char *)x;
    char *c2 = (char *)y;
    return *c1 > *c2;
}

int gt_str(const void *x, const void *y){
    char *s1 = *(char **)x;
    char *s2 = *(char **)y;
    int res = strcmp(s1, s2);
    return res <= 0 ? 0 : 1;
};

int main() {
    int arr_int[] = {1, 3, 5, 7, 4, 6, 9, 2, 0};
    int max1 = *(int *)mymax(&arr_int, sizeof(arr_int) / sizeof(*arr_int), sizeof(*arr_int), gt_int);
    printf("%d\n", max1);

    char arr_char[] = "Suncana strana ulice";
    char max2 = *(char *)mymax(&arr_char, strlen(arr_char), sizeof(*arr_char), gt_char);
    printf("%c\n", max2);

    const char *arr_str[] = {
        "Gle", "malu", "vocku", "poslije", "kise",
        "Puna", "je", "kapi", "pa", "ih", "njise"};
    char *max3 = *(char **)mymax(&arr_str, sizeof(arr_str) / sizeof(*arr_str), sizeof(*arr_str), gt_str);
    printf("%s", max3);

}