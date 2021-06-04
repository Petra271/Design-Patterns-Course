from tkinter import Button, NORMAL, DISABLED
from ButtonObserver import ButtonObserver


class SelectionObserver:
    def update_selection(self):
        pass


class BtnSelObserver(ButtonObserver, SelectionObserver):
    def update_selection(self):
        self.update()
