#include <stdio.h>
#include <stdlib.h>
 
/* The main program */
 
struct Matrix {
    int rows;
    int cols;
    int * data;
 
};
 
int main()
{
  // TODO - insert your code here
 
  
    char mark[1];
    int curr_rows;
    int curr_cols;
    int curr_matrix = 0;
    int tmp = 0;
 
    struct Matrix matrix_A;
    struct Matrix matrix_B;
    struct Matrix matrix_C;
    struct Matrix matrix_D;
    
    matrix_A.data = malloc(1 * sizeof(int));
    matrix_B.data = malloc(1 * sizeof(int));
    matrix_C.data = malloc(1 * sizeof(int));
    matrix_D.data = malloc(1 * sizeof(int));
  
    while (scanf("%d %d", &curr_rows, &curr_cols) != 0) {
 
        if (curr_matrix  == 0) {
            matrix_A.data = realloc(matrix_A.data, curr_rows * curr_cols * sizeof(int));
            matrix_A.rows = curr_rows;
            matrix_A.cols = curr_cols;

            for (int i = 0; i < matrix_A.rows; i++) {
                for (int j = 0; j < matrix_A.cols; j++) {
                    if (!(scanf("%d", &matrix_A.data[i*matrix_A.cols + j]) != 0)) {
                        free(matrix_A.data);
                        free(matrix_B.data);
                        free(matrix_C.data);
                        free(matrix_D.data);
                        fprintf(stderr, "Error: Chybny vstup!\n");
                        return 100;
                    }
                }
            }

            if (scanf("%s", mark) != 0) {
           
            
            }  

        }
        else {
            matrix_B.data = realloc(matrix_B.data, curr_rows * curr_cols * sizeof(int));
            matrix_B.rows = curr_rows;
            matrix_B.cols = curr_cols;

            for (int i = 0; i < matrix_B.rows; i++) {
                for (int j = 0; j < matrix_B.cols; j++) {
                    if (!(scanf("%d", &matrix_B.data[i*matrix_B.cols + j]) != 0)) {
                        free(matrix_A.data);
                        free(matrix_B.data);
                        free(matrix_C.data);
                        free(matrix_D.data);
                        fprintf(stderr, "Error: Chybny vstup!\n");
                        return 100;
                    }
                }
            }
        

            if (mark[0] == 42) { // 42 = *

            	if (matrix_A.cols != matrix_B.rows) {
                    free(matrix_A.data);
                    free(matrix_B.data);
                    free(matrix_C.data);
                    free(matrix_D.data);
                    fprintf(stderr, "Error: Chybny vstup!\n");
                    return 100;
                }
                
                matrix_C.data = realloc(matrix_C.data, matrix_A.rows * matrix_B.cols * sizeof(int));
                matrix_C.rows = matrix_A.rows;
                matrix_C.cols = matrix_B.cols;

                for (int i = 0; i < matrix_A.rows; i++) {
                    for (int j = 0; j < matrix_B.cols; j++) {
                        tmp = 0;
                        for (int k = 0; k < matrix_A.cols; k++) {
                            tmp = tmp + (matrix_A.data[i*matrix_A.cols + k]*matrix_B.data[k*matrix_B.cols + j]);
                        }
                        matrix_C.data[i*matrix_C.cols + j] = tmp;
                    }
                }

                // A = C
                matrix_A.data = realloc(matrix_A.data, matrix_C.rows * matrix_C.cols * sizeof(int));
                matrix_A.rows = matrix_C.rows;
                matrix_A.cols = matrix_C.cols;

                for (int i = 0; i < matrix_A.rows; i++) {
                    for (int j = 0; j < matrix_A.cols; j++) {
                        matrix_A.data[i*matrix_A.cols + j] = matrix_C.data[i*matrix_A.cols + j];
                    }
                }



                
               if (scanf("%s", mark) == EOF){	
	                printf("%d %d\n", matrix_A.rows, matrix_A.cols);
	                for (int i = 0; i < matrix_A.rows; i++) {
	                    for (int j =0; j < matrix_A.cols; j++) {
	                        if (j == 0) {
	                            printf("%d", matrix_A.data[i*matrix_A.cols + j]);
	                        } else {
	                            printf(" %d", matrix_A.data[i*matrix_A.cols + j]); 
	                        }
	                    }
	                    printf("\n");
	                }

	                free(matrix_A.data);
	                free(matrix_B.data);
	                free(matrix_C.data);
	                free(matrix_D.data);
	                return 0;
	            }
	            else {
	            	
	            }

            }
            else if (mark[0] == 43) { // +

            	if (scanf("%s", mark) == EOF) {
            		// Soucet C = A + B a vratit C jako vysledek

					// Overit spravny pocet rows a cols jinak ERROR            		
                    if (matrix_A.rows != matrix_B.rows || matrix_A.cols != matrix_B.cols) {
                        free(matrix_A.data);
                        free(matrix_B.data);
                        free(matrix_C.data);
                        free(matrix_D.data);
                        fprintf(stderr, "Error: Chybny vstup!\n");
                        return 100;
                    }

            		matrix_C.rows = matrix_A.rows;
                    matrix_C.cols = matrix_A.cols;
                    matrix_C.data = realloc(matrix_C.data, matrix_A.rows * matrix_A.cols * sizeof(int));

                    for (int i = 0; i < matrix_C.rows; i++) {
                        for (int j =0; j < matrix_C.cols; j++) {
                            matrix_C.data[i*matrix_C.cols + j] = matrix_A.data[i*matrix_C.cols + j] + matrix_B.data[i*matrix_C.cols + j]; 
                        }
                    }



            		// Konecny vystup:
            		printf("%d %d\n", matrix_C.rows, matrix_C.cols);
	                for (int i = 0; i < matrix_C.rows; i++) {
	                    for (int j =0; j < matrix_C.cols; j++) {
	                        if (j == 0) {
	                            printf("%d", matrix_C.data[i*matrix_C.cols + j]);
	                        } else {
	                            printf(" %d", matrix_C.data[i*matrix_C.cols + j]); 
	                        }
	                    }
	                    printf("\n");
	                }

	                free(matrix_A.data);
	                free(matrix_B.data);
	                free(matrix_C.data);
	                free(matrix_D.data);
	                return 0;
            	}


            	// Pokud neni EOF tak se rozhoduji podle dalsich znamenek

            	if (mark[0] == 43 || mark[0] == 45) {
            		// Soucet C = A + B a nahrat C do A, pak pokracovat

					// Overit spravny pocet rows a cols jinak ERROR            		
                    if (matrix_A.rows != matrix_B.rows || matrix_A.cols != matrix_B.cols) {
                        free(matrix_A.data);
                        free(matrix_B.data);
                        free(matrix_C.data);
                        free(matrix_D.data);
                        fprintf(stderr, "Error: Chybny vstup!\n");
                        return 100;
                    }

            		matrix_C.rows = matrix_A.rows;
                    matrix_C.cols = matrix_A.cols;
                    matrix_C.data = realloc(matrix_C.data, matrix_A.rows * matrix_A.cols * sizeof(int));

                    for (int i = 0; i < matrix_C.rows; i++) {
                        for (int j =0; j < matrix_C.cols; j++) {
                            matrix_C.data[i*matrix_C.cols + j] = matrix_A.data[i*matrix_C.cols + j] + matrix_B.data[i*matrix_C.cols + j]; 
                        }
                    }

                    matrix_A.data = realloc(matrix_A.data, matrix_C.rows * matrix_C.cols * sizeof(int));
                    matrix_A.rows = matrix_C.rows;
                    matrix_A.cols = matrix_C.cols;

                    for (int i = 0; i < matrix_A.rows; i++) {
                        for (int j = 0; j < matrix_A.cols; j++) {
                            matrix_A.data[i*matrix_A.cols + j] = matrix_C.data[i*matrix_C.cols + j];
                        }
                    }
            	}
            	else if (mark[0] == 42) { // *
            		// Nacitani dalsi matice a nasledny soucin do te doby, dokud je znamenko *
            		while (scanf("%d %d", &curr_rows, &curr_cols) != 0) {

                        matrix_C.data = realloc(matrix_C.data, curr_rows * curr_cols * sizeof(int));
                        matrix_C.rows = curr_rows;
                        matrix_C.cols = curr_cols;

                        for (int i = 0; i < matrix_C.rows; i++) {
                            for (int j = 0; j < matrix_C.cols; j++) {
                                if (!(scanf("%d", &matrix_C.data[i*matrix_C.cols + j]) != 0)) {
                                    free(matrix_A.data);
                                    free(matrix_B.data);
                                    free(matrix_C.data);
                                    free(matrix_D.data);
                                    fprintf(stderr, "Error: Chybny vstup!\n");
                                    return 100;
                                }
                            }
                        }

                        // D = B * C
                        if (matrix_B.cols != matrix_C.rows) {
                            free(matrix_A.data);
                            free(matrix_B.data);
                            free(matrix_C.data);
                            free(matrix_D.data);
                            fprintf(stderr, "Error: Chybny vstup!\n");
                            return 100;
                        }
                        
                        matrix_D.data = realloc(matrix_D.data, matrix_B.rows * matrix_C.cols * sizeof(int));
                        matrix_D.rows = matrix_B.rows;
                        matrix_D.cols = matrix_C.cols;

                        for (int i = 0; i < matrix_B.rows; i++) {
                            for (int j = 0; j < matrix_C.cols; j++) {
                                tmp = 0;
                                for (int k = 0; k < matrix_B.cols; k++) {
                                    tmp = tmp + (matrix_B.data[i*matrix_B.cols + k]*matrix_C.data[k*matrix_C.cols + j]);
                                }
                                matrix_D.data[i*matrix_D.cols + j] = tmp;
                            }
                        }



                        // B = D
                        matrix_B.data = realloc(matrix_B.data, matrix_D.rows * matrix_D.cols * sizeof(int));
                        matrix_B.rows = matrix_D.rows;
                        matrix_B.cols = matrix_D.cols;

                        for (int i = 0; i < matrix_B.rows; i++) {
                            for (int j = 0; j < matrix_B.cols; j++) {
                                matrix_B.data[i*matrix_B.cols + j] = matrix_D.data[i*matrix_D.cols + j];
                            }
                        }

                    	if (scanf("%s", mark) == EOF) {	
                        // Musim udelat C = A + B


                            if (matrix_A.rows != matrix_B.rows || matrix_A.cols != matrix_B.cols) {
                                free(matrix_A.data);
                                free(matrix_B.data);
                                free(matrix_C.data);
                                free(matrix_D.data);
                                fprintf(stderr, "Error: Chybny vstup!\n");
                                return 100;
                            }

                            matrix_C.rows = matrix_A.rows;
                            matrix_C.cols = matrix_A.cols;
                            matrix_C.data = realloc(matrix_C.data, matrix_A.rows * matrix_A.cols * sizeof(int));

                            for (int i = 0; i < matrix_C.rows; i++) {
                                for (int j =0; j < matrix_C.cols; j++) {
                                    matrix_C.data[i*matrix_C.cols + j] = matrix_A.data[i*matrix_C.cols + j] + matrix_B.data[i*matrix_C.cols + j]; 
                                }
                            }
                    
	                	    printf("%d %d\n", matrix_C.rows, matrix_C.cols);
	                		for (int i = 0; i < matrix_C.rows; i++) {
	                    		for (int j =0; j < matrix_C.cols; j++) {
	                        		if (j == 0) {
	                          	  	    printf("%d", matrix_C.data[i*matrix_C.cols + j]);
	                        		} else {
	                        	        printf(" %d", matrix_C.data[i*matrix_C.cols + j]); 
	                       			}
	                   			}
	                    		printf("\n");
	                		}

	                		free(matrix_A.data);
	                		free(matrix_B.data);
	                		free(matrix_C.data);
	               			free(matrix_D.data);
	                		return 0;
	            		}
	            		else if (mark[0] == 42) {
	            			continue;
	            		}
	            		else if (mark[0] == 43 || mark[0] == 45) {

	            			///////
	            			if (matrix_A.rows != matrix_B.rows || matrix_A.cols != matrix_B.cols) {
                        		free(matrix_A.data);
                        		free(matrix_B.data);
                        		free(matrix_C.data);
                        		free(matrix_D.data);
                        		fprintf(stderr, "Error: Chybny vstup!\n");
                        		return 100;
                    		}

            				matrix_C.rows = matrix_A.rows;
                    		matrix_C.cols = matrix_A.cols;
                    		matrix_C.data = realloc(matrix_C.data, matrix_A.rows * matrix_A.cols * sizeof(int));

                    		for (int i = 0; i < matrix_C.rows; i++) {
                        		for (int j =0; j < matrix_C.cols; j++) {
                            		matrix_C.data[i*matrix_C.cols + j] = matrix_A.data[i*matrix_C.cols + j] + matrix_B.data[i*matrix_C.cols + j]; 
                        		}
                    		}

                    		matrix_A.data = realloc(matrix_A.data, matrix_C.rows * matrix_C.cols * sizeof(int));
                    		matrix_A.rows = matrix_C.rows;
                    		matrix_A.cols = matrix_C.cols;

                    		for (int i = 0; i < matrix_A.rows; i++) {
                        		for (int j = 0; j < matrix_A.cols; j++) {
                            		matrix_A.data[i*matrix_A.cols + j] = matrix_C.data[i*matrix_C.cols + j];
                        		}
                    		}
	            			
	            			break;
	            		}
	            		else { // Znamenko jine nez je ma byt
	            			free(matrix_A.data);
                            free(matrix_B.data);
                            free(matrix_C.data);
                            free(matrix_D.data);
                            fprintf(stderr, "Error: Chybny vstup!\n");
                            return 100;
	            		}

                        // Konec nacitani nasobeni while
                    }
            	}
            	else { // Znamenko neni ani * ani + ani -
            		free(matrix_A.data);
                    free(matrix_B.data);
                    free(matrix_C.data);
                    free(matrix_D.data);
                    fprintf(stderr, "Error: Chybny vstup!\n");
                    return 100;
                }
            } ///////////////////////////////////////
            else if (mark[0] == 45) { // -

            	if (scanf("%s", mark) == EOF) {
            		// Soucet C = A + B a vratit C jako vysledek

					// Overit spravny pocet rows a cols jinak ERROR            		
                    if (matrix_A.rows != matrix_B.rows || matrix_A.cols != matrix_B.cols) {
                        free(matrix_A.data);
                        free(matrix_B.data);
                        free(matrix_C.data);
                        free(matrix_D.data);
                        fprintf(stderr, "Error: Chybny vstup!\n");
                        return 100;
                    }

            		matrix_C.rows = matrix_A.rows;
                    matrix_C.cols = matrix_A.cols;
                    matrix_C.data = realloc(matrix_C.data, matrix_A.rows * matrix_A.cols * sizeof(int));

                    for (int i = 0; i < matrix_C.rows; i++) {
                        for (int j =0; j < matrix_C.cols; j++) {
                            matrix_C.data[i*matrix_C.cols + j] = matrix_A.data[i*matrix_C.cols + j] - matrix_B.data[i*matrix_C.cols + j]; 
                        }
                    }



            		// Konecny vystup:
            		printf("%d %d\n", matrix_C.rows, matrix_C.cols);
	                for (int i = 0; i < matrix_C.rows; i++) {
	                    for (int j =0; j < matrix_C.cols; j++) {
	                        if (j == 0) {
	                            printf("%d", matrix_C.data[i*matrix_C.cols + j]);
	                        } else {
	                            printf(" %d", matrix_C.data[i*matrix_C.cols + j]); 
	                        }
	                    }
	                    printf("\n");
	                }

	                free(matrix_A.data);
	                free(matrix_B.data);
	                free(matrix_C.data);
	                free(matrix_D.data);
	                return 0;
            	}


            	// Pokud neni EOF tak se rozhoduji podle dalsich znamenek

            	if (mark[0] == 43 || mark[0] == 45) {
            		// Soucet C = A + B a nahrat C do A, pak pokracovat

					// Overit spravny pocet rows a cols jinak ERROR            		
                    if (matrix_A.rows != matrix_B.rows || matrix_A.cols != matrix_B.cols) {
                        free(matrix_A.data);
                        free(matrix_B.data);
                        free(matrix_C.data);
                        free(matrix_D.data);
                        fprintf(stderr, "Error: Chybny vstup!\n");
                        return 100;
                    }

            		matrix_C.rows = matrix_A.rows;
                    matrix_C.cols = matrix_A.cols;
                    matrix_C.data = realloc(matrix_C.data, matrix_A.rows * matrix_A.cols * sizeof(int));

                    for (int i = 0; i < matrix_C.rows; i++) {
                        for (int j =0; j < matrix_C.cols; j++) {
                            matrix_C.data[i*matrix_C.cols + j] = matrix_A.data[i*matrix_C.cols + j] - matrix_B.data[i*matrix_C.cols + j]; 
                        }
                    }

                    matrix_A.data = realloc(matrix_A.data, matrix_C.rows * matrix_C.cols * sizeof(int));
                    matrix_A.rows = matrix_C.rows;
                    matrix_A.cols = matrix_C.cols;

                    for (int i = 0; i < matrix_A.rows; i++) {
                        for (int j = 0; j < matrix_A.cols; j++) {
                            matrix_A.data[i*matrix_A.cols + j] = matrix_C.data[i*matrix_C.cols + j];
                        }
                    }
            	}
            	else if (mark[0] == 42) { // *
            		// Nacitani dalsi matice a nasledny soucin do te doby, dokud je znamenko *
            		while (scanf("%d %d", &curr_rows, &curr_cols) != 0) {

                        matrix_C.data = realloc(matrix_C.data, curr_rows * curr_cols * sizeof(int));
                        matrix_C.rows = curr_rows;
                        matrix_C.cols = curr_cols;

                        for (int i = 0; i < matrix_C.rows; i++) {
                            for (int j = 0; j < matrix_C.cols; j++) {
                                if (!(scanf("%d", &matrix_C.data[i*matrix_C.cols + j]) != 0)) {
                                    free(matrix_A.data);
                                    free(matrix_B.data);
                                    free(matrix_C.data);
                                    free(matrix_D.data);
                                    fprintf(stderr, "Error: Chybny vstup!\n");
                                    return 100;
                                }
                            }
                        }

                        // D = B * C
                        if (matrix_B.cols != matrix_C.rows) {
                            free(matrix_A.data);
                            free(matrix_B.data);
                            free(matrix_C.data);
                            free(matrix_D.data);
                            fprintf(stderr, "Error: Chybny vstup!\n");
                            return 100;
                        }
                        
                        matrix_D.data = realloc(matrix_D.data, matrix_B.rows * matrix_C.cols * sizeof(int));
                        matrix_D.rows = matrix_B.rows;
                        matrix_D.cols = matrix_C.cols;

                        for (int i = 0; i < matrix_B.rows; i++) {
                            for (int j = 0; j < matrix_C.cols; j++) {
                                tmp = 0;
                                for (int k = 0; k < matrix_B.cols; k++) {
                                    tmp = tmp + (matrix_B.data[i*matrix_B.cols + k]*matrix_C.data[k*matrix_C.cols + j]);
                                }
                                matrix_D.data[i*matrix_D.cols + j] = tmp;
                            }
                        }



                        // B = D
                        matrix_B.data = realloc(matrix_B.data, matrix_D.rows * matrix_D.cols * sizeof(int));
                        matrix_B.rows = matrix_D.rows;
                        matrix_B.cols = matrix_D.cols;

                        for (int i = 0; i < matrix_B.rows; i++) {
                            for (int j = 0; j < matrix_B.cols; j++) {
                                matrix_B.data[i*matrix_B.cols + j] = matrix_D.data[i*matrix_D.cols + j];
                            }
                        }

                    	if (scanf("%s", mark) == EOF) {	
	                		if (matrix_A.rows != matrix_B.rows || matrix_A.cols != matrix_B.cols) {
                                free(matrix_A.data);
                                free(matrix_B.data);
                                free(matrix_C.data);
                                free(matrix_D.data);
                                fprintf(stderr, "Error: Chybny vstup!\n");
                                return 100;
                            }

                            matrix_C.rows = matrix_A.rows;
                            matrix_C.cols = matrix_A.cols;
                            matrix_C.data = realloc(matrix_C.data, matrix_A.rows * matrix_A.cols * sizeof(int));

                            for (int i = 0; i < matrix_C.rows; i++) {
                                for (int j =0; j < matrix_C.cols; j++) {
                                    matrix_C.data[i*matrix_C.cols + j] = matrix_A.data[i*matrix_C.cols + j] - matrix_B.data[i*matrix_C.cols + j]; 
                                }
                            }
                    
                            printf("%d %d\n", matrix_C.rows, matrix_C.cols);
                            for (int i = 0; i < matrix_C.rows; i++) {
                                for (int j =0; j < matrix_C.cols; j++) {
                                    if (j == 0) {
                                        printf("%d", matrix_C.data[i*matrix_C.cols + j]);
                                    } else {
                                        printf(" %d", matrix_C.data[i*matrix_C.cols + j]); 
                                    }
                                }
                                printf("\n");
                            }

                            free(matrix_A.data);
                            free(matrix_B.data);
                            free(matrix_C.data);
                            free(matrix_D.data);
                            return 0;
	            		}
	            		else if (mark[0] == 42) {
	            			continue;
	            		}
	            		else if (mark[0] == 43 || mark[0] == 45) {

	            			///////
	            			if (matrix_A.rows != matrix_B.rows || matrix_A.cols != matrix_B.cols) {
                        		free(matrix_A.data);
                        		free(matrix_B.data);
                        		free(matrix_C.data);
                        		free(matrix_D.data);
                        		fprintf(stderr, "Error: Chybny vstup!\n");
                        		return 100;
                    		}

            				matrix_C.rows = matrix_A.rows;
                    		matrix_C.cols = matrix_A.cols;
                    		matrix_C.data = realloc(matrix_C.data, matrix_A.rows * matrix_A.cols * sizeof(int));

                    		for (int i = 0; i < matrix_C.rows; i++) {
                        		for (int j =0; j < matrix_C.cols; j++) {
                            		matrix_C.data[i*matrix_C.cols + j] = matrix_A.data[i*matrix_C.cols + j] - matrix_B.data[i*matrix_C.cols + j]; 
                        		}
                    		}

                    		matrix_A.data = realloc(matrix_A.data, matrix_C.rows * matrix_C.cols * sizeof(int));
                    		matrix_A.rows = matrix_C.rows;
                    		matrix_A.cols = matrix_C.cols;

                    		for (int i = 0; i < matrix_A.rows; i++) {
                        		for (int j = 0; j < matrix_A.cols; j++) {
                            		matrix_A.data[i*matrix_A.cols + j] = matrix_C.data[i*matrix_C.cols + j];
                        		}
                    		}
	            			
	            			break;
	            		}
	            		else { // Znamenko jine nez je ma byt
	            			free(matrix_A.data);
                            free(matrix_B.data);
                            free(matrix_C.data);
                            free(matrix_D.data);
                            fprintf(stderr, "Error: Chybny vstup!\n");
                            return 100;
	            		}

                        // Konec nacitani nasobeni while
                    }
            	}
            	else { // Znamenko neni ani * ani + ani -
            		free(matrix_A.data);
                    free(matrix_B.data);
                    free(matrix_C.data);
                    free(matrix_D.data);
                    fprintf(stderr, "Error: Chybny vstup!\n");
                    return 100;
                }
            } ///////////////////////////////////////////////////////////////////////////////////////////////
        }
        curr_matrix++;
    }
    return 0;
}