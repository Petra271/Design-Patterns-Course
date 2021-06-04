#include <iostream>

class B{
public:
  virtual int __cdecl prva()=0;
  virtual int __cdecl druga(int)=0;
};

class D: public B{
public:
  virtual int __cdecl prva(){return 42;}
  virtual int __cdecl druga(int x){return prva()+x;}
};

void print(B *pb){
  typedef int (*pfun1)(B *);
  typedef int (*pfun2)(B *, int);
  
  std::cout << (*(pfun1**)(pb))[0](pb) << ", " << (*(pfun2**)(pb))[1](pb, 7);
}

int main(){
    B *pb = new D();
    print(pb);
    delete(pb);
}