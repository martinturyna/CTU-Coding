#include <stdlib.h>
#include <stdio.h>

#include "graph.h"

/* This is a testing file just to be able to compile the 
 * examples of binary files to convert graph from txt->bin
 * and bin -> txt
 */

// - function -----------------------------------------------------------------
graph_t* allocate_graph() 
{
	graph_t *NewGraph;
	NewGraph = malloc(sizeof(graph_t));
	NewGraph->capacity = 50;
	NewGraph->num_edges = 0;
	NewGraph->from = malloc(NewGraph->capacity * sizeof(int));
	NewGraph->to = malloc(NewGraph->capacity * sizeof(int));
	NewGraph->cost = malloc(NewGraph->capacity * sizeof(int));
	
	return NewGraph;
}

// - function -----------------------------------------------------------------
void free_graph(graph_t **graph)
{
	free((*graph)->from);
	free((*graph)->to);
	free((*graph)->cost);
	free(*graph);
}

// - function -----------------------------------------------------------------
void load_txt(const char *fname, graph_t *graph)
{

	FILE* file = fopen(fname, "r");

	int counter = 0;
	while(fscanf(file, "%i %i %i", &graph->from[counter], &graph->to[counter], &graph->cost[counter]) != EOF) {
		
		counter++;
		graph->num_edges = counter;
		if (graph->num_edges == graph->capacity - 1) {
			graph->capacity *= 2;
			graph->from = realloc(graph->from, graph->capacity * sizeof(int));
			graph->to = realloc(graph->to, graph->capacity * sizeof(int));
			graph->cost = realloc(graph->cost, graph->capacity * sizeof(int));
		}

	}
	fclose(file);
}

// - function -----------------------------------------------------------------
void load_bin(const char *fname, graph_t *graph)
{
	FILE* file = fopen(fname, "rb");

	int counter = 0;
	while(1) {
		
		if (fread(graph->from + counter, sizeof(int), 1, file) == 0) {
			break;
		}
		if (fread(graph->to + counter, sizeof(int), 1, file) == 0) {
			break;
		}
		if (fread(graph->cost + counter, sizeof(int), 1, file) == 0) {
			break;
		}
		counter++;
		graph->num_edges = counter;

		if (graph->num_edges == graph->capacity - 1) {
			graph->capacity *= 2;
			graph->from = realloc(graph->from, graph->capacity * sizeof(int));
			graph->to = realloc(graph->to, graph->capacity * sizeof(int));
			graph->cost = realloc(graph->cost, graph->capacity * sizeof(int));
		}
	}
	fclose(file);
}

// - function -----------------------------------------------------------------
void save_txt(const graph_t * const graph, const char *fname)
{

	FILE* file = fopen(fname, "w");
	for (int i = 0; i < graph->num_edges; i++) {
		fprintf(file, "%i %i %i\n", graph->from[i], graph->to[i], graph->cost[i]);
	}
	fclose(file);
}

// - function -----------------------------------------------------------------
void save_bin(const graph_t * const graph, const char *fname)
{
	
	FILE* file = fopen(fname, "wb");
	
	for (int i = 0; i < graph->num_edges; i++) {
		
		fwrite(graph->from + i, sizeof(int), 1, file);
		fwrite(graph->to + i, sizeof(int), 1, file);
		fwrite(graph->cost + i, sizeof(int), 1, file);
	}
	fclose(file);
}
