undo_stack = []
redo_stack = []
undo_observers = []
redo_observers = []


def undo(event=None):
    if len(undo_stack) > 0:
        command = undo_stack.pop()
        redo_stack.append(command)
        command.execute_undo()
        undo_stack.pop()
    if len(redo_stack) == 1:
        notify_redo()


def push(command):
    if len(undo_stack) == 0:
        notify_undo()
    undo_stack.append(command)


def redo(event=None):
    if len(redo_stack) > 0:
        command = redo_stack.pop()
        command = command.execute_do()
    if len(redo_stack) == 0:
        notify_redo()


def attach_redo(observer):
    redo_observers.append(observer)


def detach_redo(observer):
    redo_observers.remove(observer)


def attach_undo(observer):
    undo_observers.append(observer)


def detach_undo(observer):
    undo_observers.remove(observer)


def notify_undo():
    [o.update_undo_mng() for o in undo_observers]


def notify_redo():
    [o.update_undo_mng() for o in redo_observers]
