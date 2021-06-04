from importlib import import_module


def myfactory(module_name):
    module = import_module(module_name)
    return getattr(module, "create")
