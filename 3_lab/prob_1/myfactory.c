#include <windows.h>
#include <stdio.h> 

typedef void*(__cdecl *MYPROC)(char const*);

void *myfactory(char const *libname, char const *ctorarg) {
    char name[64];
    sprintf(name, "%s%s%s", "./", libname, ".dll");

    HINSTANCE lib = LoadLibrary(name);

    if (lib != NULL) {
        MYPROC createProc = (MYPROC)GetProcAddress(lib, "create");
        if (createProc != NULL) {
            return (createProc)(ctorarg);
        }
    }

    return 0;
}
