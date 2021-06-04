class Parrot:
    def __init__(self, name):
        self.p_name = name

    def name(self):
        return self.p_name

    def greet(self):
        return "ciao"

    def menu(self):
        return "orahe"


def create(name):
    return Parrot(name)
