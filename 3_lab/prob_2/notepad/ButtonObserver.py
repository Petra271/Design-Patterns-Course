from tkinter import Button, NORMAL, DISABLED


class ButtonObserver(Button):
    def update(self):
        if self["state"] == NORMAL:
            self["state"] = DISABLED
        else:
            self["state"] = NORMAL
