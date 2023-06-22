#include <stdio.h>
#include <string.h>

char * division(char[],long);
int zbytek = 0;

int main() {

	char number[100], *quotient;

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
	
	// Main looop
	while(scanf("%s", number) != 0) {
		if (number[0] == '0') {
			return 0;
		}
		else{
			for (int i = 0; i < strlen(number); i++) {
				if (number[i] > 57 || number[i] < 48) {
					fprintf(stderr, "Error: Chybny vstup!\n");
					return 100;
				}

			}

		}
		
		

		printf("Prvociselny rozklad cisla %s je:\n", number);	

		if (strlen(number) == 1 && number[0] == 49) {
			printf("1\n");
			continue;
		}		

		char number_copy[100];

		for (int j = 0; j < strlen(number); j++){
			number_copy[j] = number[j];
		}
		number_copy[strlen(number)] = '\0';
		

		int prime_count = 0;
		for (int i = 0; i < 78498; i++) {
			int power = 0;
			zbytek = 0;
			for (int j = 0; j < strlen(number_copy); j++) {
			number[j] = number_copy[j];
			}
			number[strlen(number_copy)] = '\0';

			while (zbytek == 0) {		
				quotient = division(number, prime[i]);
	
				while(*quotient) { 
					if(*quotient == 48) { 
						quotient++; 
					} 
         				else {
	             				break;
					}
				}

				if (zbytek == 0) {				
					power++;
					
					for (int j = 0; j < strlen(quotient); j++){
						number[j] = quotient[j];
					}
					number[strlen(quotient)] = '\0';			
				}
				
				if (zbytek == 0 && power == 1) {
					prime_count++;
					if (prime_count == 1) {
						printf("%d", prime[i]);
					}
					else {
						printf(" x %d", prime[i]);
					}
				}
				else if (zbytek != 0 && power > 1) {
					printf("^%d", power);
					power = 0;
				}
				else {
					continue;
				}
			}
		}
				
		printf("\n");
	}


				
	return 0;
}

char * division(char dividend[],long divisor){
   
    static char quotient[100];
    long temp=0;
    int i=0,j=0;

    while(dividend[i]){
        
         temp = temp*10 + (dividend[i] -48);
         if(temp<divisor){
            
             quotient[j++] = 48;
            
         }
         else{
             quotient[j++] = (temp / divisor) + 48;;
             temp = temp % divisor;

         }
         i++;
    }

    quotient[j] = '\0';
    zbytek = temp;
    return quotient;
}


