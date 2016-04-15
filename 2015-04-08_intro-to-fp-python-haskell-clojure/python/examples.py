"hello"

a = "hello"

a

if True:
  "hello"
else:
  "goodbye"


def inc(x):
  return x + 1

inc(5)

inc = lambda x: x + 1

inc(6)

inc


1 + 1

def add(x, y):
  return x + y

add(4, 5)



def applyOneOne (f):
  return f(1, 1)

applyOneOne(add)



def apply (f, x, y):
  return f(x, y)

apply(add, 2, 3)


def makeIncrementer (n):
  return lambda m: m + n

add6 = makeIncrementer(6)

add6(4)



def part (f, *args):
  return lambda *more: f(*(args + more))

add1 = part(add, 1)
add1(2)

useless = part(add, 1, 2)
useless()

addbis = part(add)

addbis(1,2)




map(lambda x: x + 1, [1,2,3])



def isEven(number):
    return number % 2 == 0

def isOdd(number):
    return number % 2 == 1

filter(isEven, [1,2,3,4])


def add(x,y):
    return x + y

reduce(add, [1, 2, 3], 1)

a = 1
for x in [1,2,3]:
  a = a + x

1 + 1 + 2 + 3

a


def mult(x,y):
    return x * y

reduce(mult, [1, 2, 3])

[x * y for x in range(1, 10)
       for y in range(1, 10)
       if isEven(x) and isOdd(y)]
