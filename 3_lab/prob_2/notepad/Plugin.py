from TextEditorModel import TextEditorModel
import UndoManager
from ClipboardStack import ClipboardStack


class Plugin:
    def get_name(self):
        pass

    def get_desc(self):
        pass

    def execute(
        self, model: TextEditorModel, undo_man: UndoManager, cb_stack: ClipboardStack
    ):
    pass