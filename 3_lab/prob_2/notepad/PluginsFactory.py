from importlib import import_module


def plugin_factory(folder, module_name):
    module = import_module(folder + module_name)
    return getattr(module, module_name)
