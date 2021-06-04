import re
import ast
from abc import abstractmethod
from time import sleep


class Cell:
    def __init__(self, exp, value) -> None:
        self.exp = exp
        self.value = value

    def set_exp(self, exp):
        self.exp = exp

    def set_value(self, value):
        self.value = value

    def get_value(self):
        return self.value

    def get_exp(self):
        return self.exp


class Observer:
    def update(self, sheet) -> None: pass


class EvaluateObserver(Observer):
    def update(self, sheet) -> None:
        for i in range(sheet.rows):
            for j in range(sheet.columns):
                refs = sheet.getrefs(sheet.get_table()[j][i])
                if refs is None:
                    continue
                if sheet.get_current() in refs:
                    sheet.get_table()[j][i].set_value(
                        sheet.evaluate(sheet.get_table()[j][i]))


class Subject:
    @abstractmethod
    def attach(self, observer) -> None: pass

    @abstractmethod
    def detach(self, observer) -> None: pass

    @abstractmethod
    def notify(self) -> None: pass


class Sheet(Subject):
    def __init__(self, rows, columns) -> None:
        self.table = [[Cell(None, None) for _ in range(rows)]
                      for _ in range(columns)]
        self.rows = rows
        self.columns = columns
        self.observers = []
        self.current = None
        self.observers.append(EvaluateObserver())

    def get_table(self):
        return self.table

    def get_current(self):
        return self.current

    def cell(self, ref):
        try:
            return self.table[int(ref[1:])-1][ord(ref[0])-ord('A')]
        except IndexError:
            print(f'Ref {ref} out of range.')
            exit(1)

    def set(self, ref, content) -> None:
        try:
            cell = self.table[int(ref[1:])-1][ord(ref[0])-ord('A')]
            if not content.isdecimal():
                refs = re.findall(r'[A-Z][0-9]*', content)
                for r in refs:
                    if ref in self.getrefs(self.cell(r)):
                        raise RuntimeError('Circular dependency')
            cell.set_exp(content)
            cell.set_value(self.evaluate(cell))
            self.current = ref
            self.notify()
        except IndexError:
            print(f'Ref {ref} out of range.')

    @staticmethod
    def getrefs(cell):
        if cell.get_exp() is None:
            return None
        return re.findall(r'[A-Z][0-9]*', cell.get_exp())

    def evaluate(self, cell) -> None:
        values = dict()
        [values.update({ref: self.cell(ref).get_value()})
         for ref in self.getrefs(cell)]
        if None in values.values():
            return
        return eval_expression(cell.get_exp(), values)

    def print(self):
        print([[self.table[i][j].get_exp() for i in range(self.rows)]
               for j in range(self.columns)])
        print([[self.table[i][j].get_value() for i in range(self.rows)]
               for j in range(self.columns)])

    def attach(self, observer):
        self.observers.append(observer)

    def detach(self, observer):
        if observer in self.observers:
            self.observers.remove(observer)

    def notify(self) -> None:
        for o in self.observers:
            o.update(self)


def eval_expression(exp, variables=None):
    if variables is None:
        variables = {}

    def _eval(node):
        if isinstance(node, ast.Num):
            return node.n
        elif isinstance(node, ast.Name):
            return variables[node.id]
        elif isinstance(node, ast.BinOp):
            return _eval(node.left) + _eval(node.right)
        else:
            raise Exception('Unsupported type {}'.format(node))

    node = ast.parse(exp, mode='eval')
    return _eval(node.body)


if __name__ == "__main__":
    s = Sheet(5, 5)
    print()

    s.set('A1', '2')
    s.set('A2', '5')
    s.set('A3', 'A1+A2')
    s.print()
    print()

    s.set('A1', '4')
    s.set('A4', 'A1+A3')
    s.print()
    print()

    try:
        s.set('A1', 'A3')
    except RuntimeError as e:
        print("Caught exception:", e)
    s.print()
    print()
