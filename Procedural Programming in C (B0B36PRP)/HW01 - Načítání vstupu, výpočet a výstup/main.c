#include <stdio.h>
#include <stdlib.h>

#define MAX 10000
#define MIN -10000


/* The main program */
int main(void)
{
  
	int a, b, c;
	scanf("%d %d", &a, &b);

 	if (a > MAX || a < MIN || b > MAX || b < MIN) {
  		
  		printf("Vstup je mimo interval!\n");
  		return 0;

  	}

  	printf("Desitkova soustava: %d %d\n", a, b);
  	printf("Sestnactkova soustava: %x %x\n", a, b);

  	c = a + b;
  	printf("Soucet: %d + %d = %d\n", a, b, c);

  	c = a - b;
  	printf("Rozdil: %d - %d = %d\n", a, b, c);

  	c = a * b;
  	printf("Soucin: %d * %d = %d\n", a, b, c);

  	
  	if (b != 0) {
  		c = a / b;
  		printf("Podil: %d / %d = %d\n", a, b , c);
  	}
  	else {
  		printf("Nedefinovany vysledek!\n");
  	}

  	float d, e, f;
  	e = a;
  	f = b;
  	d = (e + f) / 2;
  	printf("Prumer: %.1f\n", d);



  return 0;
}

