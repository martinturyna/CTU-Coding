#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <semaphore.h>

typedef struct _list_item {
	unsigned int x;
	char * word;
	struct _list_item *prev;
	struct _list_item *next;
} list_item;

typedef struct {
	list_item *head;
	list_item *tail;	
} list;

// >>> GLOBAL VARIABLES <<<
pthread_mutex_t stdout_mutex;
pthread_mutex_t mutex;
sem_t sm;
list *global_list;
int DESTROY = 0;
// >>> GLOBAL VARIABLES <<<


list* list_create() {
	list *l = (list*) malloc(sizeof(list));
	l->head = NULL;
	l->tail = NULL;

	return l;
}

void list_free(list* l) {
	list_item *li, *tmp;

	pthread_mutex_lock(&mutex);

	if (l != NULL) {
		
		li = l->head;
		
		while(li != NULL) {
			tmp = li->next;
			free(li);
			li = tmp;
		}
	}

	pthread_mutex_unlock(&mutex);
	sem_destroy(&sm);
	pthread_mutex_destroy(&stdout_mutex);
	pthread_mutex_destroy(&mutex);
	free(l);
}

void list_add_element(list *l, unsigned int x, char *word) {
	list_item *li;
	li = (list_item *) malloc(sizeof(list_item));
	li->x = x;
	li->word = word;
	li->next = NULL;
	li->prev = NULL;

	if(l->head == NULL) {
		l->head = l->tail = li;
	}
	else {
		l->tail->next = li;
		l->tail = li;
	}
}

list_item* list_remove_head(list *l) {
	list_item *li = l->head;

	if(li == NULL) {

		free(li);
		return NULL;
	
	}
	else {

		l->head = li->next;
		return li;
	
	}
}

void *consumer_todo(void *threadid) {
	
	long pid;
	pid = (long)threadid;

	while(global_list->head != NULL || DESTROY == 0) {	

	    sem_wait(&sm);
	    pthread_mutex_lock(&mutex);
	   // printf("#%ld Thread - Before if\n", pid+1);
	    if (global_list->head != NULL) {
	    	//printf("#%ld Thread - In if\n", pid+1);
			list_item *ret = list_remove_head(global_list);
			pthread_mutex_unlock(&mutex);
			pthread_mutex_lock(&stdout_mutex);
	
			printf("Thread %ld:", pid+1);

			for(int i = 0; i < ret->x; i++) {
				printf(" %s", ret->word);
			}
			printf("\n");
			fflush(stdout);

			pthread_mutex_unlock(&stdout_mutex);
			free(ret->word);
			free(ret);
		}
		else {
			//printf("#%ld Thread - ELSE\n", pid+1);
			pthread_mutex_unlock(&mutex);
		}
	}
	//printf("#%ld Thread - Before EXIT\n", pid+1);
	pthread_exit(NULL);
}

void *producer_todo(void *threadid) {
	
	
	while(1) {
		unsigned int x;
		char *word;	
		int ret;
		
		ret = scanf("%u %ms", &x, &word);
		
		if (ret == 2) {
			
			pthread_mutex_lock(&mutex);
			list_add_element(global_list, x, word);
			sem_post(&sm);
			pthread_mutex_unlock(&mutex);
			
		} 
		else if (ret == -1) {
			DESTROY = 1;
			pthread_exit(NULL);
		}
		else {
			DESTROY = 2;
			pthread_exit(NULL);
		}
	}
}

int main(int argc, char const *argv[]) {

	int N = 1;

	if(argv[1] != '\0') {

		N = strtol(argv[1], NULL, 10);
	
	}
	if(N > sysconf(_SC_NPROCESSORS_ONLN) || N < 1) {

		return 1;
	
	}

	pthread_mutex_init(&mutex, NULL);
	pthread_mutex_init(&stdout_mutex, NULL);
	sem_init(&sm, 0, 0);
	global_list = list_create();

	pthread_t producer_thread;
	
	int ret = pthread_create(&producer_thread, NULL, producer_todo, NULL);
	
	if(ret != 0) {

		printf("Creating pthread error\n");
		return 1;

	}

	pthread_t *consumer_threads = malloc(sizeof(pthread_t) * N);


	for(long i = 0; i < N; i++) {
		
		ret = pthread_create(&consumer_threads[i], NULL, consumer_todo, (void *)i);

		if(ret != 0) {
			
			printf("Creating pthread error\n");
			return 1;
		
		}

	}

	pthread_join(producer_thread, NULL);

	
	for(int i = 0; i < N; i++) {
		sem_post(&sm);
	}

	for(int i = 0; i < N; i++) {
		pthread_join(consumer_threads[i], NULL);
	}

	free(consumer_threads);
	list_free(global_list);
	if (DESTROY == 2) {
		return 1;
	}
	return 0;
}