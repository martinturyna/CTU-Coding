#include <stdio.h>
/*
	inspiration from:
	http://arjunsreedharan.org/post/148675821737/write-a-simple-memory-allocator
*/

extern "C" {
	void *my_malloc(unsigned int size); 
	int my_free(void *address);
 } 

unsigned syscall2(unsigned w0, unsigned w1) {

	asm volatile(
		" mov %%esp, %%ecx ;"
	    " mov $1f, %%edx ;"
	    " sysenter ;"
		"1: ;"
		: "+a"(w0)
	    : "S"(w1)
		: "ecx", "edx", "memory");
    return w0;
}

void *brk(void *address) {
	return (void *)syscall2(3, (unsigned)address);
} 

struct header_t {
	size_t size;
	unsigned is_free;
	struct header_t *next;
};

struct header_t *head, *tail;

struct header_t *get_free_block(size_t size)
{
	struct header_t *curr = head;
	while(curr) {
		if (curr->is_free && curr->size >= size) {
			return curr;
		}
		curr = curr->next;
	}
	return NULL;
}


void *my_malloc(unsigned int size) {
	/*if (start_brk == NULL) {
		start_brk = brk(0);
		current_brk = start_brk;
	}*/
	printf("my_malloc(%u)\n", size);
	size_t total_size;
	void *block;
	struct header_t *header = 0;
	if (size == 0) {
		printf("return %d\n", 0);
		return 0;
	}
	header = get_free_block(size);
	if (header) {
		header->is_free = 0;
		printf("return %p\n", (void*)(header + 1));
		return (void*)(header + 1);
	}
	total_size = sizeof(struct header_t) + size;
	mword current_brk = (mword)brk(0);
	mword next_brk = current_brk + total_size;
	block = brk((void*)next_brk);
	if (block == 0) {
		printf("return %d\n", 0);
		return 0;
	}
	header = (struct header_t*)block;
	header->size = size;
	header->is_free = 0;
	header->next = NULL;
	if (!head) {
		head = header;
	}

	if (tail) {
		tail->next = header;
	}
	tail = header;
	return (void*)(header + 1);
}

int my_free(void *block) {
	struct header_t *header, *tmp = head;
	//void *programbreak;
	if (!block) {
		return 42; // 42 is answer for everything
	}
	header = (struct header_t*)block - 1;
	while(tmp != NULL) {
		if (tmp == header) {
			if (header->is_free == 0) {
				header->is_free = 1;
				return 0;
			}
		}
		tmp = tmp->next;
	}
	return 41;
}