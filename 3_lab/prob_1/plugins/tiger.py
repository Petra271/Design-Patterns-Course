class Tiger:
    def __init__(self, name):
        self.t_name = name

    def name(self):
        return self.t_name

    def greet(self):
        return "roar"

    def menu(self):
        return "zebre"


def create(name):
    return Tiger(name)
