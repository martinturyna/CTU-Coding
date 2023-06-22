CFLAGS+=--std=gnu99
CFLAGS+=-O3 -Wall -Werror -pedantic

all: forkpipe nsd

forkpipe: forkpipe.c 
	${CC} ${CFLAGS} forkpipe.c -o forkpipe

nsd.o: nsd.c nsd.h
	${CC} ${CFLAGS} -c nsd.c -o nsd.o

nd.o: nd.c nd.h
	${CC} ${CFLAGS} -c nd.c -o nd.o

nsd: nsd_main.c nsd.o nd.o
	${CC} ${CFLAGS} nsd_main.c nsd.o nd.o -o nsd

tar:
	tar czf osy04.tgz Makefile *.[ch]
