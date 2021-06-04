import sys

sys.path.append("..")
from Plugin import Plugin


class VelikoSlovo(Plugin):
    def get_name(self):
        return "VelikoSlovo"

    def get_desc(self):
        return "Pretvara početna slova svih riječi u velika slova"

    def execute(self, model, undo_man, cb_stack):
        data = list(model.all_lines())
        data = [line.title() for line in data]
        model.add(data)
