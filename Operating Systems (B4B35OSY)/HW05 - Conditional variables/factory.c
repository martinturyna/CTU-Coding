#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>

#define NUZKY 0
#define VRTACKA 1
#define OHYBACKA 2
#define SVARECKA 3
#define LAKOVNA 4
#define SROUBOVAK 5
#define FREZA 6

#define A 0
#define B 1
#define C 2


int stock[3][6] = {
	{0, 0, 0, 0, 0, 0},
	{0, 0, 0, 0, 0, 0},
	{0, 0, 0, 0, 0, 0}
};

int tools[7] = {0, 0, 0, 0, 0, 0, 0};

int tools_in_stock[3][6] = {
	{NUZKY, VRTACKA, OHYBACKA, SVARECKA, VRTACKA, LAKOVNA},
	{VRTACKA, NUZKY, FREZA, VRTACKA, LAKOVNA, SROUBOVAK},
	{FREZA, VRTACKA, SROUBOVAK, VRTACKA, FREZA, LAKOVNA}
};

// >>>>> GOBAL <<<<<
typedef struct {
	pthread_t tid;
	char* name;
	int tool;
	int is_working; // bool
	int shall_go_home; // bool
} Worker;

Worker** workers_array; 
int workers_array_size = 0;

typedef struct {
	pthread_mutex_t lock;
	pthread_cond_t something_changed;	
} Factory;

Factory* factory;

void sleep_for_work(int tool) {

	if(tool == NUZKY) {
		usleep(100000);
	}
	else if (tool == VRTACKA) {
		usleep(200000);
	} 
	else if (tool == OHYBACKA) {
		usleep(150000);
	}
	else if (tool == SVARECKA) {
		usleep(300000);
	}
	else if (tool == LAKOVNA) {
		usleep(400000);
	}
	else if (tool == SROUBOVAK) {
		usleep(250000);
	}
	else if (tool == FREZA) {
		usleep(500000);
	}





	/*switch (tool) {
		case NUZKY:
			usleep(100000);
		case VRTACKA:
			usleep(200000);
		case OHYBACKA:
			usleep(150000);
		case SVARECKA:
			usleep(300000);
		case LAKOVNA:
			usleep(400000);
		case SROUBOVAK:
			usleep(250000);
		case FREZA:
			usleep(500000);
	}*/
}

void remove_tool(char* tool) {

	pthread_mutex_lock(&factory->lock);
	if (strcmp(tool, "nuzky") == 0) {
		tools[NUZKY]--;
	}
	else if (strcmp(tool, "vrtacka") == 0) {
		tools[VRTACKA]--;
	}
	else if (strcmp(tool, "ohybacka") == 0) {
		tools[OHYBACKA]--;
	}
	else if (strcmp(tool, "svarecka") == 0) {
		tools[SVARECKA]--;
	}
	else if (strcmp(tool, "lakovna") == 0) {
		tools[LAKOVNA]--;
	}
	else if (strcmp(tool, "sroubovak") == 0) {
		tools[SROUBOVAK]--;
	}
	else if (strcmp(tool, "freza") == 0) {
		tools[FREZA]--;
	}
	pthread_mutex_unlock(&factory->lock);
	pthread_cond_broadcast(&factory->something_changed);
	// else do nothing
}


void add_tool(char* tool) {

	pthread_mutex_lock(&factory->lock);
	if (strcmp(tool, "nuzky") == 0) {
		tools[NUZKY]++;
	}
	else if (strcmp(tool, "vrtacka") == 0) {
		tools[VRTACKA]++;
	}
	else if (strcmp(tool, "ohybacka") == 0) {
		tools[OHYBACKA]++;
	}
	else if (strcmp(tool, "svarecka") == 0) {
		tools[SVARECKA]++;
	}
	else if (strcmp(tool, "lakovna") == 0) {
		tools[LAKOVNA]++;
	}
	else if (strcmp(tool, "sroubovak") == 0) {
		tools[SROUBOVAK]++;
	}
	else if (strcmp(tool, "freza") == 0) {
		tools[FREZA]++;
	}
	pthread_mutex_unlock(&factory->lock);
	pthread_cond_broadcast(&factory->something_changed);
	// else do nothing
}

int get_tool(char* tool) {

	if (strcmp(tool, "nuzky") == 0) {
		return NUZKY;
	}
	else if (strcmp(tool, "vrtacka") == 0) {
		return VRTACKA;
	}
	else if (strcmp(tool, "ohybacka") == 0) {
		return OHYBACKA;
	}
	else if (strcmp(tool, "svarecka") == 0) {
		return SVARECKA;
	}
	else if (strcmp(tool, "lakovna") == 0) {
		return LAKOVNA;
	}
	else if (strcmp(tool, "sroubovak") == 0) {
		return SROUBOVAK;
	}
	else if (strcmp(tool, "freza") == 0) {
		return FREZA;
	}
	return -1;
	// else do nothing
}

char* tool_to_string(int tool) {
	if(tool == NUZKY) {
		return "nuzky";
	}
	else if (tool == VRTACKA) {
		return "vrtacka";
	}
	else if (tool == OHYBACKA) {
		return "ohybacka";
	}
	else if (tool == SVARECKA) {
		return "svarecka";
	}
	else if (tool == LAKOVNA) {
		return "lakovna";
	}
	else if (tool == SROUBOVAK) {
		return "sroubovak";
	}
	else {
		return "freza";
	}
}

int can_work(Worker* w) {
	if(tools[w->tool] > 0) {
		for (int i = 0; i < 3; i++) {
			for(int j = 0; j < 6; j++) {
				if((stock[i][j] > 0) && (tools_in_stock[i][j] == w->tool)) {
					return 1;
				}
			}
		}
	}
	return 0;
}

int finish() {
	for (int i = 0; i < workers_array_size; i++) {
		if(!workers_array[i]->shall_go_home && (workers_array[i]->is_working || can_work(workers_array[i]))) {
			return 0;
		}
	}
	return 1;
}



void *worker_to_do(void* worker) {
	Worker* w = (Worker*)worker;
	pthread_mutex_lock(&factory->lock);
	while(1) {
		while(!(can_work(w) || w->shall_go_home)) {
			//fprintf(stderr, "%s\n", w->name);
			pthread_cond_wait(&factory->something_changed, &factory->lock);
		}

		if(w->shall_go_home) {
			break;
		}

		// Is ready to work
		int product = 0;
		int step = -1;
		//int go_for_break = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 6; j++) {
				if((stock[i][j] > 0) && (tools_in_stock[i][j] == w->tool) && j > step) {
					product = i;
					step = j;
				}
			}
		}

		stock[product][step]--;
		tools[w->tool]--;
		printf("%s %s %c %d\n", w->name, tool_to_string(w->tool), product+65, step+1);
		w->is_working = 1;
		pthread_mutex_unlock(&factory->lock);
		sleep_for_work(w->tool);
		pthread_mutex_lock(&factory->lock);
		w->is_working = 0;
		if (step == 5) {
			printf("done %c\n", product+65);
		}
		else {
			stock[product][step + 1]++;
		}
		tools[w->tool]++;
		fprintf(stderr, "konec\n");
		pthread_cond_broadcast(&factory->something_changed);
	}
	pthread_mutex_unlock(&factory->lock);
	return 0;
}

void create_worker(char* name, char* tool) { 
	Worker* w = malloc(sizeof(Worker));
	w->tool = get_tool(tool);
	w->name = name;
	w->shall_go_home = 0;
	w->is_working = 0;
	pthread_create(&w->tid, NULL, worker_to_do, w);
	workers_array[workers_array_size] = w;
	workers_array_size++;
}

void start_making_product(char* product_type) {
	pthread_mutex_lock(&factory->lock);
	if(strcmp(product_type, "A") == 0) {
		stock[A][0]++;
	}
	else if (strcmp(product_type, "B") == 0) {
		stock[B][0]++;
	}
	else if (strcmp(product_type, "C") == 0) {
		stock[C][0]++;
	}
	// else do nothing
	pthread_mutex_unlock(&factory->lock);
	pthread_cond_broadcast(&factory->something_changed);
}

void end_worker(char* name) {
	for(int i = 0; i < workers_array_size; i++) {
		if(strcmp(workers_array[i]->name, name) == 0) {
			workers_array[i]->shall_go_home = 1;
			pthread_cond_broadcast(&factory->something_changed);
		}
	}
}

void read_input() {
	char* command;
	while (scanf("%ms", &command) == 1) {
		fprintf(stderr, "%s ", command);
		if (strcmp(command, "make") == 0) {
			char* product_type;
			if(scanf("%ms", &product_type) == 1) {
				fprintf(stderr, "%s\n", product_type);
				start_making_product(product_type);
			}
			free(product_type);
		}
		else if (strcmp(command, "start") == 0) {
			char* tool;
			char* name;
			if (scanf("%ms %ms", &name, &tool)) {
				fprintf(stderr, "%s %s\n", name, tool);
				create_worker(name, tool);
			}
			free(tool);
			//free(name);
		}
		else if (strcmp(command, "end") == 0) {
			char* name;
			if(scanf("%ms", &name) == 1) {
				fprintf(stderr, "%s\n", name);
				end_worker(name);
			}
			free(name);
		}
		else if (strcmp(command, "add") == 0) {
			char* tool;
			if (scanf("%ms", &tool) == 1) {
				fprintf(stderr, "%s\n", tool);
				add_tool(tool);
			}
			free(tool);
		}
		else if (strcmp(command, "remove") == 0) {
			char* tool;
			if (scanf("%ms", &tool) == 1) {
				fprintf(stderr, "%s\n", tool);
				remove_tool(tool);
			}
			free(tool);
		}
		free(command);
	}
	fprintf(stderr, "EOF\n");
}



int main(int argc, char * argv[]) {

	//Should be enough
	workers_array = malloc(200*sizeof(Worker*));	
	factory = malloc(sizeof(Factory));
	pthread_mutex_init(&factory->lock, NULL);
	pthread_cond_init(&factory->something_changed, NULL);
	read_input();

	pthread_mutex_lock(&factory->lock);
	while(!(finish())) {
		pthread_cond_wait(&factory->something_changed, &factory->lock);
	}
	pthread_mutex_unlock(&factory->lock);

	for(int i = 0; i < workers_array_size; i++) {
		workers_array[i]->shall_go_home = 1;
	}
	pthread_cond_broadcast(&factory->something_changed);
	for(int i = 0; i < workers_array_size; i++) {
		pthread_join(workers_array[i]->tid, 0);
	}

	for(int i = 0; i < workers_array_size; i++) {
		free(workers_array[i]->name);
		free(workers_array[i]);
	}
	free(workers_array);
	free(factory);



	return 0;
}