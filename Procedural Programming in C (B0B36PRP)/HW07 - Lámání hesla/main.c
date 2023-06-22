#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "lock.h"
int krok = 0;
int found = 0;
/* The main program */
void swap(char * first, char * second){
	char temp;
    temp = *first;
    *first = *second;
    *second = temp;
}

int compare (const void * a, const void * b) {
   return ( *(char*)a - *(char*)b );
}
 
void permutation(char *array, int index, int length) {
	
	if (index == length) {
   		printf("%s\n", array);
   	}
	else
	{
		for (int i = index; i < length; i++)
        {
       		swap((array+index), (array+i));
        	char copy[length];
        	for(int j = 0; j < length; j++) {
        		copy[j] = array[j];
        	}
        	copy[length] = '\0';

        	qsort(copy+index+1, length-index-1, sizeof(char), compare);
			permutation(copy, index+1, length);
          	swap((array+index), (array+i));
        }
    }
} 

void permutationWhenOPT(char *array, int index, int length) {

	if (index == length) {
		krok++;
		if (unlock(array)){
			found = 1;
			printf("%s - %dx\n", array, krok);
   		}
   	}
	else
	{
		for (int i = index; i < length; i++)
        {
       		swap((array+index), (array+i));
        	char copy[length];
        	for(int j = 0; j < length; j++) {
        		copy[j] = array[j];
        	}
        	copy[length] = '\0';

        	qsort(copy+index+1, length-index-1, sizeof(char), compare);
			permutationWhenOPT(copy, index+1, length);
          	swap((array+index), (array+i));
        }
    }
} 

int main(int argc, char * argv[])
{
       
    int limit = 0;
    char passwordString[9];

    while(1) {
    	passwordString[limit] = getchar();

    	if (passwordString[limit] != '\n' && passwordString[limit] != '\0' && passwordString[limit] != EOF) {
			if (passwordString[limit] < 65 || passwordString[limit] > 90) { 
				if (passwordString[limit] < 97 || passwordString[limit] > 122)  {
					if (passwordString[limit] < 48 || passwordString[limit] > 57) {
						fprintf(stderr, "Error: Chybny vstup!\n");
						return 100;
					}
				}
			}
		}
		else if (limit == 0 && (passwordString[limit] == '\n' || passwordString[limit] == EOF || passwordString[limit] == '\0')) {
			fprintf(stderr, "Error: Chybny vstup!\n");
			return 100;
		}


    	if (passwordString[limit] == '\n' || passwordString[limit] == '\0' || passwordString[limit] == EOF) {
			passwordString[limit] = '\0';
			break;
		}

		limit++;
 	}
  
  	if (argc > 1) {
  			permutationWhenOPT(passwordString, 0, strlen(passwordString));
  			if (found == 1) {
  				return 0; 
  			}
  			else {
  				fprintf(stderr, "Error: Heslo nebylo nalezeno!\n");
  				return 101;
  			}
  			
  	}
  	else {
	 	permutation(passwordString, 0, strlen(passwordString));
   	}

    return 0;
}