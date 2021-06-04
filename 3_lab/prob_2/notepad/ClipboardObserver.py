from tkinter import Button, NORMAL, DISABLED
from ButtonObserver import ButtonObserver


class ClipboardObserver:
    def update_clipboard(self):
        pass


class ButtonCPObserver(ButtonObserver, ClipboardObserver):
    def update_clipboard(self):
        self.update()
