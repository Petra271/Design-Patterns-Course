from TextEditor import TextEditor
from TextEditorModel import TextEditorModel
from tkinter import Tk


if __name__ == "__main__":
    model = TextEditorModel("")
    editor = TextEditor(model)
    editor.mainloop()
