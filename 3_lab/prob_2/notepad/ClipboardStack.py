from ClipboardObserver import ClipboardObserver


class ClipboardStack:
    def __init__(self):
        self.__texts = []
        self.__clip_observers = []

    def attach(self, o: ClipboardObserver):
        self.__clip_observers.append(o)

    def detach(self, o: ClipboardObserver):
        self.__clip_observers.remove(o)

    def notify(self):
        [o.update_clipboard() for o in self.__clip_observers]

    def push(self, text):
        self.__texts.append(text)
        self.notify()

    def pop(self):
        if self.is_empty:
            self.notify()
        return self.__texts.pop()

    def seek(self):
        return self.__texts[-1]

    def is_empty(self):
        return len(self.__texts) == 0

    def delete(self):
        self.__texts.clear()
