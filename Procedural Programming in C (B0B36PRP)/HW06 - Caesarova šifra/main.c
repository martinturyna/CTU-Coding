#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <limits.h>

#define MIN(a, b, c) ((a) < (b) ? ((a) < (c) ? (a) : (c)) : ((b) < (c) ? (b) : (c)))

int shift(int character, int shift_lenght) { // Posun

	for (int i = 0; i < shift_lenght; i++) {
		character--;
		if (character == 96) {
			character = 90;
		}
		else if (character == 64) {
			character = 122;
		}
	}
	return character;
}

int levensthein_distance (char * coded, char * result) { // Levenstheinuv algoritmus podle Wagner-fischera
    int i, j, codedlen, resultlen;
    codedlen = strlen(coded);
 	resultlen = strlen(result);

 	// int distance[resultlen+1][codedlen+1];
 	int **distance = (int **)malloc((resultlen + 1) * sizeof(int *));
    for (i = 0; i < resultlen + 1; i++)
         distance[i] = (int *)malloc((codedlen + 1) * sizeof(int));
 	distance[0][0] = 0;
	for (i = 1; i <= resultlen ; i++) {
	    distance[i][0] = distance[i-1][0] + 1;
	}
	for (j = 1; j <= codedlen; j++) {
	    distance[0][j] = distance[0][j-1] + 1;
	}
	for (i = 1; i <= resultlen; i++) {
	    for (j = 1; j <= codedlen; j++) {
	        distance[i][j] = MIN(distance[i-1][j] + 1, distance[i][j-1] + 1, distance[i-1][j-1] + (coded[j-1] == result[i-1] ? 0 : 1));
	    }
	}

	int vysledek = distance[resultlen][codedlen];

	for (i = 0; i < resultlen + 1; i++)
        free(distance[i]);
    free(distance);

    return vysledek;
}

	
int main(int argc, char * argv[])
{
  // TODO - insert your code here

	int limit = 0;
	int counter = 10;
	char * codedString = malloc(counter * sizeof(char));
	int shiftList[52];

	for (int i = 0; i < 52; i++) {
		shiftList[i] = 0;
	}
	
	while(1) {
		codedString[limit] = getchar();

		if (codedString[limit] != '\n' && codedString[limit] != '\0' && codedString[limit] != EOF) {
			if (codedString[limit] < 65 || codedString[limit] > 90) { 
				if (codedString[limit] < 97 || codedString[limit] > 122)  {
					fprintf(stderr, "Error: Chybny vstup!\n");
					free(codedString);
					return 100;
				}
			}
		}

		if (codedString[limit] == '\n' || codedString[limit] == '\0' || codedString[limit] == EOF) {
			codedString[limit] = '\0';
			break;
		}
		else if(limit + 1 == counter) {
			
			counter = 2*counter;
			if (!(codedString = realloc(codedString, counter * sizeof(char)))) {
				fprintf(stderr, "Realloc failure\n");
				free(codedString);
				return 102;
			}
		}
		limit++;
	}

	limit = 0;
	counter = 10;
	char * resultString = malloc(counter * sizeof(char));
	while(1) {
		resultString[limit] = getchar();

		if (resultString[limit] != '\n' && resultString[limit] != '\0' && resultString[limit] != EOF) {
			if (resultString[limit] < 65 || resultString[limit] > 90) { 
				if (resultString[limit] < 97 || resultString[limit] > 122)  {
					fprintf(stderr, "Error: Chybny vstup!\n");
					free(codedString);
					free(resultString);
					return 100;
				}
			}
		}

		if (resultString[limit] == '\n' || resultString[limit] == '\0' || resultString[limit] == EOF) {
			resultString[limit] = '\0';
			break;
		}
		else if(limit + 1 == counter) {
			
			counter = 2*counter;
			
			if (!(resultString = realloc(resultString, counter * sizeof(char)))) {
				fprintf(stderr, "Realloc failure\n");
				free(codedString);
				free(codedString);
				return 102;
			} 

		}
		limit++;
	}

	if (!(argc > 1)) {
		if (strlen(codedString) != strlen(resultString)) {
			fprintf(stderr, "Error: Chybna delka vstupu!\n");
			free(codedString);
			free(resultString);
			return 101;
		}

		for (int i = 0; i < strlen(codedString); i++) {
			for (int j = 0; j < 52; j++) {
				if (shift(codedString[i], j) == resultString[i]) {
					shiftList[j] += 1;
				}
			}
		}

		int mostEq = 0;
		int mostEqIdx = 0; // Shift list je pole o delce 52 a kazdy index obsahuje udaj, kolik se na danem idx posunu shoduje pismen.
		for (int i = 0; i < 52; i++) {
			if (shiftList[i] > mostEq) {
				mostEq = shiftList[i];
				mostEqIdx = i;
			}
		}

		for (int i = 0; i < strlen(codedString); i++) {
			printf("%c", shift(codedString[i], mostEqIdx));
		}
		printf("\n");
		
		free(codedString);
	  	free(resultString); 

	  	return 0;
	}
	else {
		
		// Kopie CodedStringu
		char * shiftStringForLeven = malloc((strlen(codedString) + 1) * sizeof(char));

		for (int i = 0; i < strlen(codedString); i++) {
			shiftStringForLeven[i] = 'A';
		}
		shiftStringForLeven[strlen(codedString)] = '\0';

		int MinLevenDistance = INT_MAX;
		int ShiftIdx = 0;
        for (int i = 1; i < 52 ; i++) {
            for (int j = 0; j < strlen(shiftStringForLeven); j++) {
               shiftStringForLeven[j] = shift(codedString[j], i);
            }
            
            if (MinLevenDistance > levensthein_distance(shiftStringForLeven, resultString)) {
            	MinLevenDistance = levensthein_distance(shiftStringForLeven, resultString);
            	ShiftIdx = i;
            }
        }

        for (int i = 0; i < strlen(codedString); i++) {
			printf("%c", shift(codedString[i], ShiftIdx));
		}
		printf("\n");

		free(codedString);
	  	free(resultString); 
	  	free(shiftStringForLeven);
		return 0;
	}
	
	free(codedString);
	free(resultString);
	return 0;
}