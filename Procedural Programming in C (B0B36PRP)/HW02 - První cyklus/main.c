#include <stdio.h>
// #include <stdlib.h> 
 
 
#define MAX 10000
#define MIN -10000
 
int main()
{
    int value = 0;
    int c_numbers = 0;
    int c_even_numbers = 0;
    int c_odd_numbers = 0;
    int c_positive_numbers = 0;
    int c_negative_numbers = 0;
    int temporary_sum = 0;
    int max = 0;
    int min = 0;
 
 
    float average;
    float percent_negative_numbers, percent_positive_numbers;
    float percent_odd_numbers, percent_even_numbers;
 
    while (scanf("%d", &value) == 1) {
        
        if (value > 10000 || value < -10000) {
            printf("\nError: Vstup je mimo interval!\n");
            
            return 100;
        }
 
        c_numbers++;
        temporary_sum += value;
        
        if (c_numbers == 1){
            max = value;
            min = value;
        }
        else {
            if (value > max) {
                max = value;
            }
            if (value < min) {
                min = value;
            }
 
        }
          
        if (value > 0) {
            c_positive_numbers++;
        }
        if (value < 0) {
            c_negative_numbers++;
        }
 
 
        if ((value % 2 == 0)){
            c_even_numbers++;
        }
        if (((value % 2 == 1) || (value % 2 == -1) ) && (value != 0)){
            c_odd_numbers++;
        }
 
 
 
        if (c_numbers != 1) {
            printf(", %d", value);
        }
        else {
            printf("%d", value);
        }
 
 
    }
 
    // Percent of negative/positive numbers
    float help_variable;
    help_variable = 100.0 / ((float)c_numbers);
    percent_negative_numbers = (float)c_negative_numbers * help_variable;
    percent_positive_numbers = (float)c_positive_numbers * help_variable;
    // Percent of even/odd numbers
    percent_even_numbers = (float)c_even_numbers * help_variable;
    percent_odd_numbers = (float)c_odd_numbers * help_variable;
    // Numbers averag
    average = (float)temporary_sum / c_numbers;
 
 
    printf("\n");
    printf("Pocet cisel: %d\n", c_numbers);
    printf("Pocet kladnych: %d\n", c_positive_numbers);
    printf("Pocet zapornych: %d\n", c_negative_numbers);
    printf("Procento kladnych: %.2f\n", percent_positive_numbers);
    printf("Procento zapornych: %.2f\n", percent_negative_numbers);
    printf("Pocet sudych: %d\n", c_even_numbers);
    printf("Pocet lichych: %d\n", c_odd_numbers);
    printf("Procento sudych: %.2f\n", percent_even_numbers);
    printf("Procento lichych: %.2f\n", percent_odd_numbers);
    printf("Prumer: %.2f\n", average);
    printf("Maximum: %d\n", max);
    printf("Minimum: %d\n", min);
  
 
    return 0;
}
