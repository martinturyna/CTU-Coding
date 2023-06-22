CFLAGS+=-Wall -g -O2
EXTRA_CFLAGS?=-fsanitize=address -fno-omit-frame-pointer

all: factory

factory: factory.c 
	gcc factory.c -o factory -pthread $(CFLAGS) $(EXTRA_CFLAGS)

tar:
	tar czf osy06.tgz Makefile *.[ch]
