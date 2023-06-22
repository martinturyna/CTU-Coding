#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define MIN(a,b) (((a)<(b)) ? (a) : (b))
#define MAX(a,b) (((a)>(b)) ? (a) : (b))


static int get_square_area(int height, int (*matrix)[height], int x, int y, int sqSize) {
	
	int a = matrix[x][y];
	int b;
	if(y - sqSize >= 0) {
		b = matrix[x][y - sqSize];
	}
	else {
		b = 0;
	}

	int c;
	if(x - sqSize >= 0) {
		c = matrix[x - sqSize][y];
	}
	else {
		c = 0;
	}
	int d;
	if((x - sqSize) >= 0 && (y - sqSize) >= 0) {
		d = matrix[x-sqSize][y-sqSize];
	}
	else {
		d = 0;
	}
	
	int result = a - b - c + d;
	return result;	
}

int main(int argc, char * argv[]) {

	int x, y;
	int number;

	scanf("%d %d", &x, &y);

	/* Creating two matrixes, one for toxic fields and second for rich fields.
	It is necessary to transform each of them to the matrix of the sums	*/
	int (*toxicMatrix)[y] = malloc(x * sizeof(*toxicMatrix));
	int (*richMatrix)[y] = malloc(x * sizeof(*richMatrix));

	for(int i = 0; i < x; i++) {
		for(int j = 0; j < y; j++) {
			
			switch(number = getchar()) {
				case '0':
					toxicMatrix[i][j] = 0;
					richMatrix[i][j] = 0;
					break;
				case '1':
					toxicMatrix[i][j] = 0;
					richMatrix[i][j] = 1;
					break;
				case '2':
					toxicMatrix[i][j] = 1;
					richMatrix[i][j] = 0;
					break;
				default:
					j--;
					continue;
			}
		}
	}
	
	// Transformig matrixes to the sum matrixes
	for(int i = 0; i < x; i++) {
		for(int j = 0; j < y; j++) {
			if(j == 0 && i == 0) { // Incase of the first field
				continue;
			}
			else if(i == 0) { // Incase of the field in the Y border
				toxicMatrix[i][j] += toxicMatrix[i][j-1];
				richMatrix[i][j] += richMatrix[i][j-1];
			}
			else if(j == 0) { // Incase of the field in the X border
				toxicMatrix[i][j] += toxicMatrix[i-1][j];
				richMatrix[i][j] += richMatrix[i-1][j];
			}
			else { // Any other field
				toxicMatrix[i][j] += toxicMatrix[i-1][j] + toxicMatrix[i][j-1] - toxicMatrix[i-1][j-1];
				richMatrix[i][j] += richMatrix[i-1][j] + richMatrix[i][j-1] - richMatrix[i-1][j-1];
			}
		}
	}

	int maxSquareSide = MIN(x, y);
	int xNum, yNum;
	int squareLength;
	int maxCost = 0;
		
	for (int squareSide = maxSquareSide; squareSide > 1; squareSide--) {
		xNum = (1 + (x - squareSide));
		yNum = (1 + (y - squareSide));
		squareLength = 2*squareSide + 2*(squareSide-2);

		if (maxCost >= squareLength) {
			printf("%d\n", maxCost);
			free(richMatrix);
			free(toxicMatrix);
			return 0;
		}

		for(int g = squareSide-1; g < (xNum + squareSide - 1 ); g++) {
			for(int h = squareSide-1; h < (yNum + squareSide - 1); h++) {
				
				int aRich = get_square_area(y, richMatrix, g, h, squareSide);
				int aToxic = get_square_area(y, toxicMatrix, g, h, squareSide);

				int bRich, bToxic;
				if (squareSide > 2) {
					bRich = get_square_area(y, richMatrix, g-1, h-1, squareSide-2);
					bToxic = get_square_area(y, toxicMatrix, g-1, h-1, squareSide-2);
				}
				else {
					bRich = 0;
					bToxic = 0;
				}

				int resRich = aRich - bRich;
				int resToxic = aToxic - bToxic;

				if (resToxic * 2 <= resRich) {
					if(resRich > maxCost) {
						maxCost = resRich;
					}
				}
			}
		}
	}

	printf("%d\n", maxCost);
	free(richMatrix);
	free(toxicMatrix);
	return 0;
}