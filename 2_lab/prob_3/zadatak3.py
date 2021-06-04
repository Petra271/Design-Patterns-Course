
def mymax(iterable, key=lambda x:x):
	max_x=max_key=None

	for x in iterable:
		if max_x is None or key(x) > max_key:
			max_key = key(x)
			max_x = x

	return max_x


def main():
	maxint = mymax([1, 3, 5, 7, 4, 6, 9, 2, 0])
	maxchar = mymax("Suncana strana ulice")
	print(maxint)
	print(maxchar)

	f = lambda x: len(x)
	str_list = ["Gle", "malu", "vocku", "poslije", "kise",
            "Puna", "je", "kapi", "pa", "ih", "njise"]
	maxstring1 = mymax(str_list, f)
	maxstring2 = mymax(str_list)

	print(maxstring1)
	print(maxstring2)

	D = {'burek':8, 'buhtla':5}
	maxitem = mymax(D, D.get)
	print(maxitem)

	people = [('Ana', 'Zric'), ('Ana', 'Anic'), ('Zina', 'Horvat'), 
	('Zina', 'Zanic'), ('Luka', 'Klaric')]
	max_person = mymax(people)
	print(max_person)

if __name__ == '__main__':
	main()

# metoda je slicna slobodnoj funkciji, ali je vezana uz objekt
# ima defaultni argument self koji se odnosi na instancu