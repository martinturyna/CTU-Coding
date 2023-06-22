CFLAGS+=--std=c99
CFLAGS+=-Wall -g

all: prod-cons

prod-cons: prod-cons.c 
	${CC} ${CFLAGS} prod-cons.c -o prod-cons -lpthread

tar:
	tar czf osy05.tgz Makefile *.[ch]