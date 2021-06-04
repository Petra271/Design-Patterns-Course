from TextEditorModel import TextEditorModel
from tkinter import *
from Location import Location
from CursorObserver import *
from TextObserver import TextObserver
from LocationRange import LocationRange
from ClipboardStack import ClipboardStack
from EditAction import *
import UndoManager
from ClipboardObserver import *
from SelectionObserver import *
from UndoRedoObserver import *
import os
from PluginsFactory import plugin_factory
from functools import partial


class TextEditor(
    Tk,
    CursorObserver,
    TextObserver,
    SelectionObserver,
    ClipboardObserver,
    UndoManagerObserver,
):
    pressed = 0

    def __init__(
        self,
        model: TextEditorModel,
    ):
        Tk.__init__(self)
        self.geometry("700x500")
        self.title("Fake Notepad")
        self.__plugins = self.find_plugins()
        self.__model = model
        self.__model.attach(self)
        UndoManager.attach_redo(self)
        UndoManager.attach_undo(self)
        self.__clipboard = ClipboardStack()
        self.__clipboard.attach(self)
        self._gui()
        self.bind_keys()
        self.write()

    def _gui(self):
        self.create_menus()
        self.create_toolbar()

        self.__canvas = Canvas(self)
        self.__pad = 2
        self.__cursor = Location(self.__pad, self.__pad)
        self.__ft = font.Font(family="Consolas", size=10, weight="normal")
        self.__letter_w = self.__ft.measure("\0")
        self.__letter_h = self.__ft.metrics("linespace")
        self.__cursor_line = self.__canvas.create_line(
            self.__pad,
            self.__pad,
            self.__pad,
            self.__pad + self.__letter_h,
        )
        self.__canvas.pack(side=TOP, fill=BOTH, expand=True)
        self.__text = []
        self.__col = None
        self.__selection = ""
        self.add_status_bar()

    def write(self):
        [self.__canvas.delete(line) for line in self.__text]
        y = self.__pad
        lines = self.__model.all_lines()
        range = self.__model.get_selection_range()

        for i, line in enumerate(lines):
            self.__text.append(
                self.__canvas.create_text(
                    self.__pad,
                    y,
                    anchor="nw",
                    text=line,
                    font=self.__ft,
                )
            )

            y += self.__letter_h
            if range is not None and range.end != range.start:
                start_y = range.start.y - self.__pad
                if i == start_y:
                    start_x = range.start.x - self.__pad
                    end_x = range.end.x - self.__pad
                    if end_x > start_x:
                        self.__selection = line[start_x:end_x]
                    else:
                        self.__selection = line[end_x:start_x]

                    self.__canvas.delete(self.__col)
                    self.__col = self.__canvas.create_rectangle(
                        start_x * self.__letter_w,
                        (range.start.y + self.__pad) + i * self.__letter_h,
                        end_x * self.__letter_w,
                        range.end.y + (i + 1) * self.__letter_h,
                        fill="SkyBlue1",
                        outline="",
                    )
                    self.__canvas.lower(self.__col)

    def update_cursor_loc(self, loc: Location):
        x = self.__cursor.x
        y = self.__cursor.y
        self.__cursor = loc

        move_x = (self.__cursor.x - x) * self.__letter_w
        move_y = (self.__cursor.y - y) * self.__letter_h

        self.__canvas.move(
            self.__cursor_line,
            move_x,
            move_y,
        )

    def update_text(self):
        self.write()

    def select_text(self, event):
        if not TextEditor.pressed:
            self.__canvas.delete(self.__col)
            TextEditor.pressed = 1
            self.__model.set_selection_range(
                LocationRange(self.__cursor, self.__cursor)
            )
        else:
            range = self.__model.get_selection_range()
            current = self.__cursor
            self.__model.set_selection_range(LocationRange(range.start, current))

    def release(self, event):
        TextEditor.pressed = 0

    def select_arrow_keys(self, event):
        if TextEditor.pressed:
            self.select_text(event)

    def clear_selection(self):
        self.release(None)
        self.__model.set_selection_range(None)
        self.__canvas.delete(self.__col)

    def delete(self, event=None):
        range = self.__model.get_selection_range()
        if range is not None and range.start != range.end:
            self.__model.delete_range(range)
            self.clear_selection()
        elif event is not None and event.keysym == "Delete":
            self.__model.delete_before()
        else:
            self.__model.delete_after()

    def insert_char(self, event):
        range = self.__model.get_selection_range()
        if not event.keysym.startswith("Control"):
            if range is not None:
                self.__model.delete_range(range)
                self.clear_selection()
            self.__model.insert_char(event.char)

    def up(self, event):
        if self.__cursor.y > self.__pad:
            self.__model.move_up()
            self.clear_selection()

    def down(self, event):
        if self.__cursor.y * self.__letter_h < self.winfo_height() - self.__pad:
            self.__model.move_down()
            self.clear_selection()

    def left(self, event):
        if self.__cursor.x > self.__pad:
            self.__model.move_left()

    def right(self, event):
        if (self.__cursor.x - 1) * self.__letter_w < self.winfo_width() - self.__pad:
            self.__model.move_right()

    def copy(self, event=None):
        if self.__selection != "":
            self.__clipboard.push(self.__selection)
            self.clear_selection()

    def cut(self, event=None):
        if self.__selection != "":
            self.__clipboard.push(self.__selection)
            self.delete(None)
            self.clear_selection()

    def seek_paste(self, event=None):
        self.paste(self.__clipboard.seek)

    def pop_paste(self, event=None):
        self.paste(self.__clipboard.pop)

    def paste(self, fun):
        if not self.__clipboard.is_empty():
            text = fun()
            self.__model.insert_string(text)

    def bind_keys(self):
        self.bind("<Up>", self.up)
        self.bind("<Down>", self.down)
        self.bind("<Left>", self.left)
        self.bind("<Right>", self.right)
        self.bind("<Delete>", self.delete)
        self.bind("<BackSpace>", self.delete)
        self.bind("<KeyPress-Shift_R>", self.select_text)
        self.bind("<KeyRelease-Shift_L>", self.release)
        self.bind("<KeyRelease-Right>", self.select_arrow_keys)
        self.bind("<KeyRelease-Left>", self.select_arrow_keys)
        self.bind("<Control-c>", self.copy)
        self.bind("<Control-x>", self.cut)
        self.bind("<Control-v>", self.seek_paste)
        self.bind("<Control-Shift-V>", self.pop_paste)
        self.bind("<Control-z>", UndoManager.undo)
        self.bind("<Control-y>", UndoManager.redo)
        self.bind("<Key>", self.insert_char)

    def open_file(self):
        file = filedialog.askopenfilename()
        with open(file, "r") as f:
            text = f.read()
        self.__model = TextEditorModel(text)
        self.__model.attach(self)
        self.write()
        self.reset_state()

    def reset_state(self):
        self.__clipboard = ClipboardStack()
        self.__cursor = Location(self.__pad, self.__pad)
        self.__col = None
        self.__selection = ""

    def save_file(self):
        file = filedialog.asksaveasfilename()
        with open(file, "w") as f:
            text = f.write("\n".join(list(self.__model.all_lines())))

    def move_start(self):
        x = self.__pad - self.__cursor.x
        y = self.__pad - self.__cursor.y
        self.__model.move(x, y)
        return Location(x, y)

    def move_end(self):
        lines = list(self.__model.all_lines())
        x = len(lines[-1]) - self.__cursor.x + self.__pad
        y = len(lines) - self.__cursor.y + 1
        self.__model.move(x, y)
        return Location(x, y)

    def clear_document(self):
        self.__model.clear()
        self.move_start()

    def create_menus(self):
        menubar = Menu(self)
        # file menu
        file = Menu(menubar, tearoff=0)

        file.add_command(label="Open", command=self.open_file)
        file.add_command(label="Save", command=self.save_file)
        file.add_command(label="Exit", command=self.quit)
        # edit menu
        self.edit = Menu(menubar, tearoff=0)

        self.edit.add_command(label="Undo", command=UndoManager.undo)
        self.edit.add_command(label="Redo", command=UndoManager.redo)

        self.edit.add_separator()

        self.edit.add_command(label="Cut", command=self.cut, state=DISABLED)
        self.edit.add_command(label="Copy", command=self.copy, state=DISABLED)
        self.edit.add_command(label="Paste", command=self.seek_paste, state=DISABLED)
        self.edit.add_command(
            label="Paste and Take", command=self.pop_paste, state=DISABLED
        )
        self.edit.add_command(label="Delete selection", command=self.delete)
        self.edit.add_command(label="Clear document", command=self.clear_document)

        # move menu
        move = Menu(menubar, tearoff=0)
        move.add_command(label="Cursor to document start", command=self.move_start)
        move.add_command(label="Cursor to document end", command=self.move_end)

        # plugins menu
        plugins = Menu(menubar, tearoff=0)
        for plugin in self.__plugins:

            plugins.add_command(
                label=plugin.get_name(self),
                command=partial(
                    plugin.execute, self, self.__model, UndoManager, self.__clipboard
                ),
            )

        menubar.add_cascade(label="File", menu=file)
        menubar.add_cascade(label="Edit", menu=self.edit)
        menubar.add_cascade(label="Move", menu=move)
        menubar.add_cascade(label="Plugins", menu=plugins)

        self.config(menu=menubar)

    def create_toolbar(self):
        tlbar = Frame(self.master, bd=1, relief=RAISED)
        buttons = []
        buttons.append(
            undo := ButtonUndoObserver(
                tlbar, text="Undo", command=UndoManager.undo, state=DISABLED
            )
        )
        buttons.append(
            redo := ButtonRedoObserver(
                tlbar, text="Redo", command=UndoManager.redo, state=DISABLED
            )
        )
        UndoManager.attach_undo(undo)
        UndoManager.attach_redo(redo)

        buttons.append(
            cut := BtnSelObserver(tlbar, text="Cut", command=self.cut, state=DISABLED)
        )
        buttons.append(
            copy := BtnSelObserver(
                tlbar, text="Copy", command=self.copy, state=DISABLED
            )
        )
        self.__model.attach(cut)
        self.__model.attach(copy)
        buttons.append(
            paste := ButtonCPObserver(
                tlbar, text="Paste", command=self.seek_paste, state=DISABLED
            )
        )
        self.__clipboard.attach(paste)

        for b in buttons:
            b.pack(side=LEFT)
        tlbar.pack(side=TOP, fill=X)

    def update_selection(self):
        self.update_item(3, 4)

    def update_clipboard(self):
        self.update_item(5, 6)

    def update_item(self, x, y):
        state = NORMAL
        if self.edit.entrycget(x, "state") == NORMAL:
            state = DISABLED

        self.edit.entryconfigure(x, state=state)
        self.edit.entryconfigure(y, state=state)

    def add_status_bar(self):
        statusbar = StatusBar(self, 1, 1, self.__pad - 1)
        statusbar.pack(side=BOTTOM, fill=X)
        self.__model.attach(statusbar)

    def find_plugins(self):
        plugins = []
        for mymodule in os.listdir("plugins"):
            moduleName, moduleExt = os.path.splitext(mymodule)
            if moduleExt == ".py":
                plugin = plugin_factory("plugins.", moduleName)
                plugins.append(plugin)
        return plugins
