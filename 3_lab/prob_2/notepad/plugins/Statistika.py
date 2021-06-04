import sys
from tkinter import messagebox

sys.path.append("..")
from Plugin import Plugin


class Statistika(Plugin):
    def get_name(self):
        return "Statistika"

    def get_desc(self):
        return "Broji koliko ima redaka, riječi i slova u dokumentu"

    def execute(self, model, undo_man, cb_stack):
        data = list(model.all_lines())
        num_lines = len(data)
        words = []
        [words.extend(line.split()) for line in data]
        num_words = len(words)
        letters = [len(list(word)) for word in words]
        num_letters = sum(letters)
        messagebox.showinfo(
            title="Statistics",
            message=f"File has {num_lines} lines, {num_words} words and {num_letters} letters.",
        )
