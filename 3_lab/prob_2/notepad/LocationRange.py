from Location import Location


class LocationRange:
    def __init__(self, start: Location, end: Location):
        self.__start = start
        self.__end = end

    @property
    def start(self):
        return self.__start

    @property
    def end(self):
        return self.__end
