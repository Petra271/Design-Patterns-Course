class DeleteSelectionAction:
    def __init__(self, text, range, model):
        self.__range = range
        self.__text = text
        self.__model = model

    def execute_do(self):
        self.__model.delete_range(self.__range)

    def execute_undo(self):
        self.__model.insert_string(self.__text)


class DeleteBeforeAction:
    def __init__(self, text, model):
        self.__text = text
        self.__model = model

    def execute_do(self):
        self.__model.delete_before()

    def execute_undo(self):
        self.__model.insert_char(self.__text)


class DeleteAfterAction:
    def __init__(self, text, model):
        self.__text = text
        self.__model = model

    def execute_do(self):
        self.__model.delete_after()

    def execute_undo(self):
        self.__model.insert_char(self.__text)


class InsertCharAction:
    def __init__(self, c, model):
        self.__c = c
        self.__model = model

    def execute_do(self):
        self.__model.insert_char(self.__c)

    def execute_undo(self):
        self.__model.delete_before()


class InsertStringAction:
    def __init__(self, text, r, model):
        self.__text = text
        self.__model = model
        self.__r = r

    def execute_do(self):
        self.model.insert_string(self.__text)

    def execute_undo(self):
        self.model.delete_range(self.__r)
