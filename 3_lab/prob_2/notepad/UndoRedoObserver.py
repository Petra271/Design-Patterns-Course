from tkinter import Button, NORMAL, DISABLED
from ButtonObserver import ButtonObserver


class UndoManagerObserver:
    def update_undo_mng(self):
        pass


class ButtonUndoObserver(ButtonObserver):
    def update_undo_mng(self):
        self.update()


class ButtonRedoObserver(ButtonObserver):
    def update_undo_mng(self):
        self.update()
