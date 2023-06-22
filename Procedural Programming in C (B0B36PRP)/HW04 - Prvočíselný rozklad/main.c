#include <stdio.h>
#include <stdlib.h>
#include <limits.h>

/* The main program */




int main()
{
	int power = 0;
	long number;

// Sieve of Eratosthenes

	int array[1000000];
	int prime[80000];
	int prime_count = 0;

	
	array[0] = 0;
	array[1] = 0;

	for (int i = 2; i < 1000000; i++) {
      		array[i]= i;
   	}
		
	
	for (int i = 0; i < 1000000; i++) {
		if (array[i] != 0) { 
         		for (int j = 2; j < 1000000; j++) {
				if (array[i] * j > 1000000) {
            		      		break;    
            			}
				array[j * array[i]] = 0;
        		}
      		}
   	}
	
	for (int i = 0; i < 1000000; i++) {
		if (array[i] != 0 ) {
			prime[prime_count] = array[i];
			prime_count += 1;		
      		}
	}
	

		
	while (scanf("%ld", &number) == 1) {
		
		if (number < 0) {
			fprintf(stderr, "Error: Chybny vstup!\n");
			return 100;		
		}
		else if (number == 0) {
			return 0;			
		}
		else if (number == 1) {
			printf("Prvociselny rozklad cisla %ld je:\n", number);
			printf("%ld\n", number);
		}
		else {
			printf("Prvociselny rozklad cisla %ld je:\n", number);
			power = 0;
			for (int i = 0; i <= 78498; i++) {
				if (number == 1) {
					printf("\n");
					break;
				}
				else if (number % prime[i] == 0) {
					while (number % prime[i] == 0) {
						number = number / prime[i];
						power += 1;
					}
					if (power == 1){
						printf("%d", prime[i]);
					}
					else {	
						printf("%d^%d", prime[i], power);
					}					
					power = 0;
					if (number != 1) {
						printf(" x ");
					}
				}							
			}				
		}
	}

	fprintf(stderr, "Error: Chybny vstup!\n");
	return 100;		

}

