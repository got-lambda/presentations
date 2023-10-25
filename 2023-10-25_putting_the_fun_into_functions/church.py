######################### Maybe #########################
def nothing():
    def c_nothing(with_nothing, with_just):
        return with_nothing()

    return c_nothing

def just(a):
    def c_just(with_nothing, with_just):
        return with_just(a)

    return c_just

## Example usage ##
nothing()(lambda: print('got nothing'),
          lambda x: print(f'got just: {x}'))

just(42)(lambda: print('got nothing'),
         lambda x: print(f'got just: {x}'))



######################### Maths Expression #########################
def num(n):
    def c_num(with_num, with_add):
        return with_num(n)

    return c_num

def add(a, b):
    def c_add(with_num, with_add):
        return with_add(a(with_num, with_add),
                        b(with_num, with_add))

    return c_add

a = add(num(10), num(2))


## Calculate an expression ##
def calc_num(n):
    return n

def calc_add(a, b):
    return a + b

a(calc_num, calc_add)


## Print an expression ##
def str_num(n):
    return str(n)

def str_add(a, b):
    return a + ' + ' + b

a(str_num, str_add)


######################### List #########################
def nil():
    def c_nil(f_nil, f_cons):
        return f_nil()
    return c_nil

def cons(hd, tl):
    def c_cons(f_nil, f_cons):
        return f_cons(hd, tl(f_nil, f_cons))
    return c_cons


## Print a list ##
def str_nil():
    return 'nil'

def str_cons(hd, tl):
    return str(hd) + ',' + tl

lst = cons(1, cons(2, nil()))
lst(str_nil, str_cons)


## Functions on lists ##
def lappend(l0, l1):
    def app_nil():
        return l1

    def app_cons(hd, tl):
        return cons(hd, tl)

    return l0(app_nil, app_cons)

def lmap(f, l):
    def map_nil():
        return nil()

    def map_cons(hd, tl):
        return cons(f(hd), tl)

    return l(map_nil, map_cons)


## Using the list functions ##
lst0 = cons(1, cons(2, nil()))
lst1 = cons(3, cons(4, nil()))

lappend(lst0, lst1)(str_nil, str_cons)

lmap(lambda x: x * x, lappend(lst0, lst1))(str_nil, str_cons)
