from tkinter import *
import time


class canvasComp(Canvas):
    def __init__(self, master):
        Canvas.__init__(self, master=master)
        self.draw()
        self.master.bind("<Return>", self.close)

    def draw(self):
        self.create_line(20, 20, 500, 20, fill="red")
        self.create_line(20, 20, 20, 500, fill="red")
        self.create_text(
            30,
            30,
            anchor="nw",
            text="Gle malu voćku poslije kiše:\nPuna je kapi pa ih njiše",
        )
        self.pack()

    def close(self, event):
        self.master.destroy()
