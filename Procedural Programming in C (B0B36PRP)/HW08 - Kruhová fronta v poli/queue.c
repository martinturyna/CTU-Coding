#include "queue.h"

/* creates a new queue with a given size */
queue_t* create_queue(int cap) {
	queue_t *NewQueue;
	NewQueue = malloc(sizeof(queue_t));
	NewQueue->start_index = 0;
	NewQueue->end_index = 0;
	NewQueue->capacity = cap;
	NewQueue->size = 0;
	NewQueue->array = malloc(NewQueue->capacity * sizeof(void*));
	for (int i = 0; i < NewQueue->capacity; i++) {
		NewQueue->array[i] = NULL;
	}
	return NewQueue;
}
/* deletes the queue and all allocated memory */
void delete_queue(queue_t *queue) {
	free(queue->array);
	free(queue);
}
/* 
 * inserts a reference to the element into the queue
 * returns: true on success; false otherwise
 */
bool push_to_queue(queue_t *queue, void *data) {

	if (queue->size == queue->capacity) {
		int copyStartIndex = queue->start_index;
		void** HelpArray = malloc(queue->size * sizeof(void*));
		for (int i = 0; i < queue->size; i++) {
			HelpArray[i] = queue->array[copyStartIndex + i];
			if (copyStartIndex + i == queue->capacity - 1) {
				copyStartIndex = -i - 1;
			}
		}

		queue->capacity *= 2;
		queue->array = realloc(queue->array, queue->capacity * sizeof(void*));

		for (int i = 0; i < queue->size; i++) {
			queue->array[i] = HelpArray[i];
		}
		free(HelpArray);
		queue->start_index = 0;
		queue->end_index = get_queue_size(queue) - 1;
	}
		
	if(queue->array[queue->start_index] == NULL) {
		queue->array[queue->start_index] = data;
		queue->start_index = queue->end_index = 0;
		queue->size++;
		return true;
	} else {
		if (queue->end_index == queue->capacity - 1) {
			if (queue->start_index == 0) {			
				return false;
			}
			else {
				queue->end_index = 0;
				queue->array[queue->end_index] = data;
				queue->size++;
				return true;
			}
		}
		else if (queue->end_index == queue->start_index - 1) {
			return false;
		}
		else {
			queue->end_index++;
			queue->array[queue->end_index] = data;
			queue->size++;
			return true;		
		}		
	}
}
/* 
 * gets the first element from the queue and removes it from the queue
 * returns: the first element on success; NULL otherwise
 */
void* pop_from_queue(queue_t *queue) {


	if (queue->size == queue->capacity / 3) {
		int copyStartIndex = queue->start_index;
		void** HelpArray = malloc(queue->size * sizeof(void*));
		for (int i = 0; i < queue->size; i++) {
			HelpArray[i] = queue->array[copyStartIndex + i];
			if (copyStartIndex + i == queue->capacity - 1) {
				copyStartIndex = -i - 1;
			}
		}

		queue->capacity = (queue->capacity / 3) + 1;
		queue->array = realloc(queue->array, queue->capacity * sizeof(void*));

		for (int i = 0; i < queue->size; i++) {
			queue->array[i] = HelpArray[i];
		}
		free(HelpArray);
		queue->start_index = 0;
		queue->end_index = get_queue_size(queue) - 1;
	}



	if (queue->array[queue->start_index] == NULL) {
		return NULL;
	}
	else {
		void* element = queue->array[queue->start_index];
		queue->array[queue->start_index] = NULL;
		queue->size--;
		
		if (queue->start_index + 1 == queue->capacity) {
			if (queue->start_index != queue->end_index){
				queue->start_index = 0;
			}
		}
		else {
			if (queue->start_index != queue->end_index) {
				queue->start_index++;
			}
		}

		return element;
	}
}
/* 
 * gets idx-th element from the queue 
 * returns: the idx-th element on success; NULL otherwise
 */
void* get_from_queue(queue_t *queue, int idx) {
	
	if (idx < 0 || idx > queue->size - 1) {
		return NULL;
	}
	else {

		if (queue->start_index + idx == queue->capacity - 1 ) {
			int counter = 0;
			for (int i = 0; i <= idx; i++) {
				if (queue->start_index + i > queue->capacity - 1) {
					counter++;
				}
			}
			return queue->array[counter];
		}
		else {
			return queue->array[queue->start_index + idx];
		}
	}
}

/* gets number of stored elements */
int get_queue_size(queue_t *q) {
	return q->size;
}