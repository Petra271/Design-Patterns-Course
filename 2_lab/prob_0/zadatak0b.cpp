#include <iostream>
#include <stdlib.h>
#include <list>
#include <iterator>

struct Point {
    int x;
    int y;
};

class Shape {
private:
    Point center;
public:
    Shape(int x, int y) { center.x = x; center.y = y; };
    virtual void draw() = 0;
    virtual void translate(int t_x, int t_y) {
        center.x += t_x;
        center.y += t_y;
    }
    void getCenter() {
        std::cout << center.x << ", " << center.y << "\n";
    }
};

class Circle: public Shape {
private:
    double radius;
public:
    Circle(int x, int y, int r) : Shape(x, y), radius(r) {};
    virtual void draw(){
        std::cerr << "in drawCircle\n";
    };
};

class Square: public Shape {
private:
    double side;
public:
    Square(int x, int y, int s) : Shape(x, y), side(s) {};
    virtual void draw(){
        std::cerr << "in drawSquare\n";
    };
};

class Rhomb: public Shape {
private:
    double side;
    double angle;
public:
    Rhomb(int x, int y, double s, double a) : Shape(x, y), side(s), angle(a) {};
    virtual void draw(){
        std::cerr << "in drawRhomb\n";
    };
};

void drawShapes(const std::list<Shape*>& fig) {
    std::list<Shape*>::const_iterator it;
    for (it = fig.begin(); it != fig.end(); ++it ){
        (*it) -> draw();
    }
}

void moveShapes(const std::list<Shape*>& fig, int x, int y) {
    std::list<Shape*>::const_iterator it;
    for (it = fig.begin(); it != fig.end(); ++it ){
        (*it) -> translate(x, y);
    }
}

int main() {
    std::list<Shape*> shapes;
    shapes.push_back((Shape *)new Circle(2,6,3));
    shapes.push_back((Shape *)new Square(3,8,5));
    shapes.push_back((Shape *)new Square(2,4,4));
    shapes.push_back((Shape *)new Circle(1,3,2));
    shapes.push_back((Shape *)new Rhomb(5,11,30,8));

    drawShapes(shapes);
    moveShapes(shapes, 7, 8);
    std::list<Shape*>::const_iterator it;
    for (it = shapes.begin(); it != shapes.end(); ++it ){
        (*it) -> getCenter();
    }

}

// ova implementacija omogucava drawShapes i moveShapes apstrahiranje
// konkretnih objekata
// rjesenje su polimorfni pozivi preko zajednickog sucelja
// ovakvo rjesenje omogucava NBP i fleksibilnost
// komponente su otvorene za nadogradnju, a zatvorene za promjene