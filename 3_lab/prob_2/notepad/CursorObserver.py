from Location import Location
from tkinter import Label, SUNKEN, E, BOTTOM


class CursorObserver:
    def update_cursor_loc(self, loc: Location):
        pass


class StatusBar(CursorObserver, Label):
    def __init__(self, editor, line, col, pad):
        Label.__init__(
            self,
            master=editor,
            text=f"Ln: {line}, Col: {col}",
            bd=1,
            relief=SUNKEN,
            anchor=E,
        )
        self.line = line
        self.col = col
        self.pad = pad

    def update_cursor_loc(self, loc: Location):
        self.config(text=f"Ln: {loc.x-self.pad}, Col: {loc.y-self.pad}")
