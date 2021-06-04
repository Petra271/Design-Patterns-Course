#include <iostream>
#include <assert.h>
#include <stdlib.h>

struct Point {
    int x;
    int y;
};

struct Shape {
    enum EType {circle, square, rhomb};
    EType type_;
};

struct Circle {
    Shape::EType type_;
    double radius_;
    Point center_;
};

struct Square {
    Shape::EType type_;
    double side_;
    Point center_;
};

struct Rhomb {
    Shape::EType type_;
    double side_;
    double angle_;
    Point center_;
};

void drawSquare(struct Square *) {
    std::cerr << "in drawSquare\n";
}

void drawCircle(struct Circle *) {
    std::cerr << "in drawCircle\n";
}

void drawRhomb(struct Rhomb *) {
    std::cerr << "in drawRhomb\n";
}

void translate(int *x, int *y, int t_x, int t_y){
    (*x) +=t_x;
    (*y) +=t_y;
}

void moveShapes(Shape **shapes, int n, int x, int y) {
    for (int i = 0; i < n; ++i) {
        struct Shape *s = shapes[i];
        switch (s->type_) {
        case Shape::square: {
            struct Square *s1 = (struct Square *)s;
            translate(&(s1->center_.x), &(s1->center_.y), x, y);
            break;
        } case Shape::circle: {
            struct Circle *s2 = (struct Circle *)s;
            translate(&(s2->center_.x), &(s2->center_.y), x, y);
            break;
        } case Shape::rhomb: {
            struct Rhomb *s3 = (struct Rhomb *)s;
            translate(&(s3->center_.x), &(s3->center_.y), x, y);
            break;
        } default:
            assert(0);
            exit(0);
        }
    }
}

void drawShapes(Shape **shapes, int n) {
    for (int i = 0; i < n; ++i) {
        struct Shape *s = shapes[i];
        switch (s->type_) {
        case Shape::square:
            drawSquare((struct Square *)s);
            break;
        case Shape::circle:
            drawCircle((struct Circle *)s);
            break;
        case Shape::rhomb:
            drawRhomb((struct Rhomb *)s);
            break;
        default:
            assert(0);
            exit(0);
        }
    }
}

int main() {
    struct Point p1 = {12, 6};
    struct Point p2 = {2, 3};
    struct Point p3 = {7, 9};

    Shape *shapes[4];

    shapes[0] = (Shape *)new Circle;
    shapes[0]->type_ = Shape::circle;
    ((Circle *)shapes[0])->center_ = p1;

    shapes[1] = (Shape *)new Square;
    shapes[1]->type_ = Shape::square;
    ((Square *)shapes[1])->center_ = p2;

    shapes[2] = (Shape *)new Square;
    shapes[2]->type_ = Shape::square;

    shapes[3] = (Shape *)new Circle;
    shapes[3]->type_ = Shape::circle;

    shapes[4] = (Shape *)new Rhomb;
    shapes[4]->type_ = Shape::rhomb;
    ((Rhomb *)shapes[4])->center_ = p3;

    drawShapes(shapes, 5);
    moveShapes(shapes, 5, 3, 4);
    std::cout << ((Circle *)shapes[0])->center_.x << ", " << ((Circle *)shapes[0])->center_.y << "\n";
    std::cout << ((Square *)shapes[1])->center_.x << ", " << ((Circle *)shapes[1])->center_.y << "\n";
    std::cout << ((Rhomb *)shapes[4])->center_.x  << ", " << ((Rhomb *)shapes[4])->center_.y;
}