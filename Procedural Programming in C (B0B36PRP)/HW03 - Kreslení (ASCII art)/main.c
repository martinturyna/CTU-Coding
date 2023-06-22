#include <stdio.h>
 
 
int main()
{

	int sirka, vyska, plot;
	     
	// Kontrola vstupu
  
	if (scanf("%d%d", &sirka, &vyska) != 2) { // klasicka kontrola vstupu int
	        fprintf(stderr, "Error: Chybny vstup!\n");
	        return 100;
	}
 
    	if ((sirka > 63 || sirka < 3) || (vyska > 63 || vyska < 3)) { // kontrola intervalu
        	fprintf(stderr, "Error: Vstup mimo interval!\n");
        	return 101;
    	}
   
    	if (sirka % 2 != 1) { // Sirka vzdy licha
        	fprintf(stderr, "Error: Sirka neni liche cislo!\n");
        	return 102;
    	}
    	

	// Domecek s plotem
	if (sirka == vyska) {
		if (scanf("%d", &plot) != 1) {
			fprintf(stderr, "Error: Chybny vstup!\n");
			return 100;
		}
		
		if (plot >= vyska || plot <= 0) {
			fprintf(stderr, "Error: Neplatna velikost plotu!\n");
			return 103;
		}		

		int help_plot = plot;				


		// Strecha
		int k = (sirka / 2); // Pomocna promenna pro vypocet mezer mezi zacatkem radku a prvnim x-kem
		int n = 1; // Pomocna promenna pro vypocet mezer mezi x-kama

		for (int i = 0; i < (sirka / 2) ; i++) {
			if (i == 0) { // Prvni radek je vzdy spicka - jedno x
				for (int j = 0; j < (sirka / 2); j++) {
					printf(" ");
				}		
				printf("X\n");
			}	
			else {
			
				for (int m = k; m > 0; m--) {
					printf(" ");
				}
				printf("X");
				for (int l = 0; l < n ; l++) {
					printf(" ");
				}
				printf("X\n");
				n = n + 2; // Vypocet mezer mezi prvnim a druhym x-kem (pouze v pripade 2x = vynechano pro spicku domecku)
			}
			k--; // Vypocet meze mezi zacatkem radku a prvnim x-kem
		}




		for (int i = 0; i < vyska; i++) {
			if (i == 0) {
	       		     	for (int j = 0; j < sirka; j++) {
		        		printf("X");
		    		}
		    		printf("\n");
			}
	       	      	else if (i == vyska - 1) {
	 
		    		for (int j = 0; j < sirka; j++) {
		        		printf("X");
		    		}

				if (help_plot == (vyska - i)) { // kod plotu
						if (plot % 2 == 0) { // -|
							for (int l = 0; l < (plot / 2); l++) {
								printf("-|");
							}
							printf("\n");
						}
						else if (plot % 2 == 1) {
							printf("|");
							for (int l = 0; l < (plot / 2); l++) {
								printf("-|");
							}
							printf("\n");
						}
				help_plot = help_plot - 1;
				}
			}
			else {
				if (i == 1 || i % 2 == 1) {			
					printf("X");
					for (int k = 0; k < (sirka - 2) / 2; k++) {
			    			printf("o*");
					}
					printf("o"); 
				} 
				else {
					printf("X*");
					for (int k = 0; k < (sirka - 2) / 2; k++) {
			    			printf("o*");
					}
				}

				if (help_plot == (vyska - i)) { // kod plotu
						printf("X");

						if (help_plot == plot) {
							if (plot % 2 == 0) { // -|
								for (int l = 0; l < (plot / 2); l++) {
									printf("-|");
								}
								printf("\n");
							}
							else if (plot % 2 == 1) {
								printf("|");
								for (int l = 0; l < (plot / 2); l++) {
									printf("-|");
								}
								printf("\n");
							}				

						}
						else {
		 					if (plot % 2 == 0) { // -|
								for (int l = 0; l < (plot / 2); l++) {
									printf(" |");
								}
								printf("\n");
							}
							else if (plot % 2 == 1) {
								printf("|");
								for (int l = 0; l < (plot / 2); l++) {
									printf(" |");
								}
								printf("\n");
							}
						}
				help_plot = help_plot - 1;
				}
				else { 
					printf("X\n");		
				}
			}
	    	}	
	}
	else {
		
		// 2 casti: strecha + domecek bez strechy
	
		// Strecha
		int k = (sirka / 2); // Pomocna promenna pro vypocet mezer mezi zacatkem radku a prvnim x-kem
		int n = 1; // Pomocna promenna pro vypocet mezer mezi x-kama

		for (int i = 0; i < (sirka / 2) ; i++) {
			if (i == 0) { // Prvni radek je vzdy spicka - jedno x
				for (int j = 0; j < (sirka / 2); j++) {
					printf(" ");
				}		
				printf("X\n");
			}	
			else {
			
				for (int m = k; m > 0; m--) {
					printf(" ");
				}
				printf("X");
				for (int l = 0; l < n ; l++) {
					printf(" ");
				}
				printf("X\n");
				n = n + 2; // Vypocet mezer mezi prvnim a druhym x-kem (pouze v pripade 2x = vynechano pro spicku domecku)
			}
			k--; // Vypocet meze mezi zacatkem radku a prvnim x-kem
		}
				   
	    	// Domecek bez strechy
	    	for (int i = 0; i < vyska; i++) {
			if (i == 0) {
	       		     for (int j = 0; j < sirka; j++) {
		        	printf("X");
		    		}
		    		printf("\n");
			}
	       	      	else if (i == vyska - 1) {
	 
		    		for (int j = 0; j < sirka; j++) {
		        		printf("X");
		    		}
		    		printf("\n");
			}
			else {
				printf("X");
				for (int k = 0; k < sirka - 2; k++) {
		    			printf(" ");
				}
				printf("X\n");
	 		}
	    	}
  	}
    	return 0;
 
}
