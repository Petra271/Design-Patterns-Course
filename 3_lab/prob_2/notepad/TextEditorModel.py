from LocationRange import LocationRange
from Location import Location
from CursorObserver import CursorObserver
from TextObserver import TextObserver
from tkinter import Canvas, Tk, font
import UndoManager
from EditAction import *
from SelectionObserver import SelectionObserver


class TextEditorModel:
    def __init__(self, text):
        self.__selection_range = None
        self.pad = 2
        self.__cursor_loc = Location(self.pad, self.pad)
        self.__lines = text.split("\n")
        self.__cursor_observers = []
        self.__text_observers = []
        self.__selection_observers = []

    def all_lines(self):
        return iter(self.__lines)

    def lines_range(self, index_1, index_2):
        return iter(self.__lines[index_1:index_2])

    def set_selection_range(self, r: LocationRange):
        if r is None or self.__selection_range is None:
            self.notify_sel()
        self.__selection_range = r
        self.notify_text()

    def set_cursor_loc(self, location: Location):
        self.__cursor_loc = location
        self.notify_cursor()

    def get_selection_range(self):
        return self.__selection_range

    def attach(self, observer):
        if isinstance(observer, CursorObserver):
            self.__cursor_observers.append(observer)
        if isinstance(observer, TextObserver):
            self.__text_observers.append(observer)
        if isinstance(observer, SelectionObserver):
            self.__selection_observers.append(observer)

    def detach(self, observer):
        if isinstance(observer, CursorObserver):
            self.__cursor_observers.remove(observer)
        if isinstance(observer, TextObserver):
            self.__text_observers.remove(observer)
        if isinstance(observer, SelectionObserver):
            self.__selection_observers.remove(observer)

    def notify_text(self):
        [o.update_text() for o in self.__text_observers]

    def notify_cursor(self):
        [o.update_cursor_loc(self.__cursor_loc) for o in self.__cursor_observers]

    def notify_sel(self):
        [o.update_selection() for o in self.__selection_observers]

    def move_up(self):
        self.move(y=(-1))
        self.set_selection_range(None)

    def move_down(self):
        self.move(y=1)
        self.set_selection_range(None)

    def move_left(self):
        self.move(x=(-1))

    def move_right(self):
        self.move(x=1)

    def move(self, x=0, y=0):
        self.__cursor_loc = Location(self.__cursor_loc.x + x, self.__cursor_loc.y + y)
        self.notify_cursor()

    def delete_before(self):
        if self.__cursor_loc.x - self.pad > 0:
            deleted = self.delete(x1=(-1))
            self.move_left()
            action = DeleteBeforeAction(deleted, self)
            UndoManager.push(action)

    def delete_after(self):
        deleted = self.delete(x2=1)
        action = DeleteAfterAction(deleted, self)
        UndoManager.push(action)

    def delete_range(self, r: LocationRange):
        start = r.start
        end = r.end
        start_y = start.y - self.pad
        end_y = end.y - self.pad
        start_x = start.x - self.pad
        end_x = end.x - self.pad

        if start.y == end.y:
            line = self.__lines[start_y]
            if start.x < end.x:
                self.__lines[start_y] = line[0:start_x] + line[end_x:]
                deleted = line[start_x:end_x]
            else:
                self.__lines[start_y] = line[0:end_x] + line[start_x:]
                deleted = line[end_x:start_x]
        self.set_selection_range(None)
        self.notify_sel()
        self.__cursor_loc = start

        action = DeleteSelectionAction(deleted, r, self)
        UndoManager.push(action)

        self.notify_cursor()
        self.notify_text()

    def delete(self, x1=0, x2=0):
        x, y = self.get_position()
        deleted = ""
        if y < len(self.__lines):
            line = self.__lines[y]
            index = x + x1
            if index < len(line):
                deleted = line[index]
            self.__lines[y] = line[: x + x1] + line[x + x2 :]
            self.notify_text()
            return deleted

    def clear(self):
        self.__lines.clear()
        self.notify_text()

    def add(self, new):
        self.__lines = new
        self.notify_text()

    def insert_char(self, c: chr):
        x, y = self.get_position()
        if y >= len(self.__lines):
            self.insert_empty(y, str(c))
            if c != "\r":
                self.move_right()
        else:
            line = self.__lines[y]
            split_line = [line[:x], line[x:]]
            if c == "\r":
                first_part = self.__lines[:y]
                first_part.extend(split_line)
                first_part.extend(self.__lines[y + 1 :])
                self.__lines = first_part
                self.__cursor_loc = Location(self.pad, y + self.pad + 1)
                self.notify_cursor()
            else:
                self.__lines[y] = str(line[:x]) + c + str(line[x:])
                self.move_right()

        action = InsertCharAction(c, self)
        UndoManager.push(action)
        self.notify_text()

    def insert_string(self, text_str: str):
        position = self.__cursor_loc
        x, y = self.get_position()
        text = text_str.split("\n")
        if y >= len(self.__lines):
            self.insert_empty(y, text_str)
            print(self.__lines)
        else:
            line = self.__lines[y]
            self.__lines[y] = line[:x] + text[0]
            if len(text) > 1:
                self.__lines.extend(text[1:])
                self.__lines[y + len(text) - 1] += line[x:]
            else:
                first = self.__lines[0:y]
                for line in text:
                    first.append(line)
                first[len(first) - 1] += line[x:]
                self.__lines = first.split("\n")
                self.__cursor_loc = Location(
                    len(line[:x]) + self.pad + len(text[0]), y + self.pad
                )
                self.notify_cursor()

        range = LocationRange(position, self.__cursor_loc)
        action = InsertStringAction(text_str, range, self)
        UndoManager.push(action)
        self.notify_text()

    def get_position(self):
        x = self.__cursor_loc.x - self.pad
        y = self.__cursor_loc.y - self.pad
        return x, y

    def insert_empty(self, y, text):
        [self.__lines.append("") for _ in range(y + 1 - len(self.__lines))]
        self.__lines[y] = text
