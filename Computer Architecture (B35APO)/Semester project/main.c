/*******************************************************************
  Julia Set algortihm on LCD display by Martin Turyna
 *******************************************************************/

#define _POSIX_C_SOURCE 200112L

#include <stdlib.h>
#include <stdio.h>
#include <stdint.h>
#include <time.h>
#include <unistd.h>
#include <math.h>
#include <string.h>
#include <pthread.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <errno.h>

#include "mzapo_parlcd.h"
#include "mzapo_phys.h"
#include "mzapo_regs.h"
#include "font_types.h"

#define MYPORT "44444"    // the port users will be connecting to
#define MAXBUFLEN 100

//real and imaginary part of the constant c, determinate shape of the Julia Set
// declared as volatile for changing in other functions
volatile double cRe;
volatile double cIm;
volatile int maxIterations;
volatile uint8_t status;
volatile uint8_t whileBreaker;

unsigned char *parlcd_mem_base;
unsigned char *mem_base;
volatile uint32_t knobs;
volatile uint32_t redKnob;
volatile uint32_t greenKnob;
volatile uint32_t blueKnob;

volatile uint32_t redKnobPressed;
volatile uint32_t blueKnobPressed;

uint16_t buffer[320][480];


typedef struct {
    int r;       // a fraction between 0 and 1
    int g;       // a fraction between 0 and 1
    int b;       // a fraction between 0 and 1
} ColorRGB;

typedef struct {
    float h;       // angle in degrees
    float s;       // a fraction between 0 and 1
    float v;       // a fraction between 0 and 1
} ColorHSV;


ColorRGB HSVtoRGB(ColorHSV colorHSV) { 
  float r = 0, g = 0, b = 0, h, s, v; //this function works with floats between 0 and 1
  h = colorHSV.h / 256.0;
  s = colorHSV.s / 256.0;
  v = colorHSV.v / 256.0;

  //If saturation is 0, the color is a shade of gray
  if(s == 0) {
    r = g = b = v;
  }
  else {
    float f, p, q, t;
    int i;
    h *= 6; //to bring hue to a number between 0 and 6, better for the calculations
    i = (int)h;  //e.g. 2.7 becomes 2 and 3.01 becomes 3 or 4.9999 becomes 4
    f = h - i;  //the fractional part of h
    p = v * (1 - s);
    q = v * (1 - (s * f));
    t = v * (1 - (s * (1 - f))); 
    switch(i) {
      case 0: r = v; g = t; b = p; break;
      case 1: r = q; g = v; b = p; break;
      case 2: r = p; g = v; b = t; break;
      case 3: r = p; g = q; b = v; break;
      case 4: r = t; g = p; b = v; break;
      case 5: r = v; g = p; b = q; break;
    }
  }

  ColorRGB colorRGB;
  colorRGB.r = (int)(r * 255.0);
  colorRGB.g = (int)(g * 255.0);
  colorRGB.b = (int)(b * 255.0);
  
  return colorRGB;
}

uint16_t RGB888toRGB565(unsigned char red, unsigned char green, unsigned char blue) {
  uint16_t b = blue; 
  uint16_t g = green << 5;
  uint16_t r = red << 11;

  return (uint16_t) (r | g | b);
}

void calculateJulia(double moveX, double moveY, int maxIterations, double zoom, uint16_t buffer[320][480]) {
  double newRe, newIm, oldRe, oldIm;   //real and imaginary parts of new and old
  ColorRGB color;

  for(int y = 0; y < 320; y++) {
    for(int x = 0; x < 480; x++) {
        //calculate the initial real and imaginary part of c, based on the pixel location and zoom and position values
        newRe = 1.5 * (x - 480 / 2) / (0.5 * zoom * 480) + moveX;
        newIm = (y - 320 / 2) / (0.5 * zoom * 320) + moveY;
        //iterations will represent the number of iterations
        int iterations;
        //start the iteration process
        for(iterations = 0; iterations < maxIterations; iterations++)  {
          //remember value of previous iteration
          oldRe = newRe;
          oldIm = newIm;
          //the actual iteration, the real and imaginary part are calculated
          newRe = oldRe * oldRe - oldIm * oldIm + cRe;
          newIm = 2 * oldRe * oldIm + cIm;
          //if the point is outside the circle with radius 2: stop
          if((newRe * newRe + newIm * newIm) > 4) {
              break; 
          } 
        }
        
        ColorHSV colorHSVtmp;
        colorHSVtmp.h = iterations % 256;
        colorHSVtmp.s = 255;
        colorHSVtmp.v = 255 * (iterations < maxIterations);

        color = HSVtoRGB(colorHSVtmp);
        buffer[y][x] = RGB888toRGB565((unsigned char)color.r, (unsigned char)color.g, (unsigned char)color.b);
    }
  }
} 

void draw(uint16_t buffer[320][480], unsigned char *parlcd_mem_base) {

  for(int y = 0; y < 320; y++) {
    for(int x = 0; x < 480; x++)  {
      parlcd_write_data(parlcd_mem_base, buffer[y][x]);
    }
  }
}

void drawCharToBuffer(char c, int row, int column, uint16_t color, uint16_t buffer[320][480]){
    for(int i = 0; i < 16; i++){
		int tmp = 15; // For bit shift (From 15 to 8)
		for(int j = 0; j < 8; j++) {
			if (font_rom8x16.bits[(int)c*16+i] >> tmp & 1){
				buffer[i+row*16][column*8 + j] =  color;
			}
			tmp--;
		}
    }
}

void drawStringToBuffer(char *string, int row, int column, uint16_t color, uint16_t buffer[320][480]) {
	int lenght = strlen(string);
	
	for(int i = 0; i < lenght; i++){
		drawCharToBuffer(string[i],row, column+i, color, buffer);
	}
}

int drawMenu(uint16_t buffer[320][480]) {
	
	for(int y = 0; y < 320; y++) {
		for(int x = 0; x < 480; x++) {
			buffer[y][x] = 0;				
		}
	}
	
	drawStringToBuffer("Semestral work: by Martin Turyna", 1, 10, 800, buffer);
	int set = (blueKnob / 4) % 4; // Set represents status in the menu
		
	if(set == 0) {
		drawStringToBuffer("- Draw Julia Set <", 3, 2, 0xFFFF, buffer);
    }
	else {
		drawStringToBuffer("- Draw Julia Set", 3, 2, 0xFFFF, buffer);	
	}
	if(set == 1) {
		drawStringToBuffer("- Draw Julia Animation <", 4, 2, 0xFFFF, buffer);
	}
	else {
		drawStringToBuffer("- Draw Julia Animation", 4, 2, 0xFFFF, buffer);	
	}	
	if(set == 2) {
		drawStringToBuffer("- Draw Julia with UDP settings <", 5, 2, 0xFFFF, buffer);
	}
	else {
		drawStringToBuffer("- Draw Julia with UDP settings", 5, 2, 0xFFFF, buffer);	
	}
	if(set == 3) {
		drawStringToBuffer("- EXIT <", 6, 2, 0xFFFF, buffer);
	}
	else {
		drawStringToBuffer("- EXIT", 6, 2, 0xFFFF, buffer);	
	}
	return set;
}

void *drawJuliaThread(void *vargp)
{
	  cRe = -0.7; 
      cIm = 0.27015;
      maxIterations = 200; //after how much iterations the function should stop
      
	
      while(1) {
				
        //each iteration, it calculates: new = old*old + c, where c is a constant and old starts at current pixel
        double zoom = 1, moveX = 0, moveY = 0; 
        //you can change these to zoom and change position
        //pick some values for the constant c, this determines the shape of the Julia Set
        printf("RGB KNOBS Value: %d\n", knobs);
       
        int set = (redKnob / 4) % 5; // set represents which kind of Julia set should be drawn
        if(status == 1) { // If wait for UDP parameters, not necessary to change it manually 
			if (set == 0){
				cRe = -0.7; 
				cIm = 0.27015;
			}
			if(set == 1) {
				cRe = 0.285; 
				cIm = 0;
			}
			if(set == 2) {
				cRe = 0.285; 
				cIm = 0.01;
			}
			if(set == 3) {
				cRe = 0; 
				cIm = -0.8;	
			}
			if(set == 4) {
				cRe = -0.835; 
				cIm = -0.2321;
			}
		}
		
        printf("redKnob position %d\n", redKnob);
        printf("greenKnob position %d\n", greenKnob);
        printf("blueKnob position %d\n", blueKnob);
       
        // Setting move (X/Y)
        int8_t newBlueKnob = blueKnob;
        moveX = newBlueKnob * 0.01;        
        int8_t newGreenKnob = greenKnob;
		moveY = newGreenKnob * 0.01;    
        
        // Every value has its own buffer
        char stringIter[100]; 
        char stringcRe[100];
        char stringcIm[100];
        
        char numberBuffer[50];
        
        strcpy(stringIter, "MaxIterations: ");
        strcpy(stringcRe, "cRe: ");
        strcpy(stringcIm, "cIm: ");
        
        snprintf(numberBuffer, 10, "%d", maxIterations);
        strcat(stringIter, numberBuffer);
        
        snprintf(numberBuffer, 10, "%.4lf", cRe);
        strcat(stringcRe, numberBuffer);
        
        snprintf(numberBuffer, 10, "%.4lf", cIm);
        strcat(stringcIm, numberBuffer);
        
        calculateJulia(moveX, moveY, maxIterations, zoom, buffer);
        drawStringToBuffer(stringcRe, 1, 2, 255, buffer);
        drawStringToBuffer(stringcIm, 2, 2, 255, buffer);
        drawStringToBuffer(stringIter, 3, 2, 255, buffer);
        draw(buffer, parlcd_mem_base);

       if(redKnobPressed == 1) { 
			status = 0;
			whileBreaker = 1;
			break;
		}
      }
  
    return NULL;
}

void *drawMenuThread(void *vargp) {
	
	while(1) {
		
		int set = drawMenu(buffer);
		set++; // 0 for Menu -> not needed
		if(blueKnobPressed == 1) { 
			status = set;
			whileBreaker = 1;
			break;
		}
		draw(buffer, parlcd_mem_base);
	
	}
	return NULL;
}

void *drawAnimationThread(void *vargp) {
					
  //each iteration, it calculates: new = old*old + c, where c is a constant and old starts at current pixel
  double zoom = 1, moveX = 0, moveY = 0; 
  //you can change these to zoom and change position
 
  //pick some values for the constant c, this determines the shape of the Julia Set
    
  // Animation will add or subtract constat values cRe and cIm
  int add = 1;
  int iterationsAdd = 1;

  cRe = -0.7; 
  cIm = 0.27015;
  maxIterations = 100; //after how much iterations the function should stop
  	
	while(1) {
		
		if (add == 1) {
		  cRe += 0.01;
		  cIm += 0.005;
		  
		  if(cRe > -0.65){
			add = 0;
	      }
		}
		else {
		  cRe -= 0.01;
		  cIm -= 0.005;
		  
		  if(cRe < -0.85) {
			  add = 1;
		  }		
		}
		
		if (iterationsAdd == 1) {
		  maxIterations++;
		  		  
		  if(maxIterations > 100){
			iterationsAdd = 0;
	      }
		}
		else {
		  maxIterations--;
		  
		  if(maxIterations < 50) {
			  iterationsAdd = 1;
		  }		
		}
		
        
        printf("RGB KNOBS Value: %d\n", knobs);
        printf("redKnob position %d\n", redKnob);
        printf("greenKnob position %d\n", greenKnob);
        printf("blueKnob position %d\n", blueKnob); 
        printf("cRe: %3f cIm: %3f Iterations: %d\n", cRe, cIm, maxIterations);
       
        char stringIter[100]; 
        char stringcRe[100];
        char stringcIm[100];
        
        char numberBuffer[50];
        
        strcpy(stringIter, "MaxIterations: ");
        strcpy(stringcRe, "cRe: ");
        strcpy(stringcIm, "cIm: ");
        
        snprintf(numberBuffer, 10, "%d", maxIterations);
        strcat(stringIter, numberBuffer);
        
        snprintf(numberBuffer, 10, "%.4lf", cRe);
        strcat(stringcRe, numberBuffer);
        
        snprintf(numberBuffer, 10, "%.4lf", cIm);
        strcat(stringcIm, numberBuffer);
        
		calculateJulia(moveX, moveY, maxIterations, zoom, buffer);
        drawStringToBuffer(stringcRe, 1, 2, 255, buffer);
        drawStringToBuffer(stringcIm, 2, 2, 255, buffer);
        drawStringToBuffer(stringIter, 3, 2, 255, buffer);
        draw(buffer, parlcd_mem_base);

        if(redKnobPressed == 1) { 
			status = 0;
			whileBreaker = 1;
			break;
		}
      }
  
    return NULL;
}

void *refreshKnobsThread(void *vargp) {
	
	while(1) {
		
		knobs = *(volatile uint32_t*)(mem_base + SPILED_REG_KNOBS_8BIT_o);
		redKnob = (uint8_t) ((knobs >> 16) & 0xFF);
		greenKnob = (uint8_t) ((knobs >> 8) & 0xFF);
		blueKnob = (uint8_t) ((knobs & 0xFF));	
		redKnobPressed = (uint8_t) ((knobs >> 26 & 1));
		blueKnobPressed = (uint8_t) ((knobs >> 24 & 1));
	}
	return NULL;
}

// get sockaddr, IPv4 or IPv6:
void *get_in_addr(struct sockaddr *sa)
{
    if (sa->sa_family == AF_INET) {
        return &(((struct sockaddr_in*)sa)->sin_addr);
    }

    return &(((struct sockaddr_in6*)sa)->sin6_addr);
}

void *UDPThread(void *vargp) {
	int sockfd;
	struct addrinfo hints, *servinfo, *p;
	int rv;
	int numbytes;
	struct sockaddr_storage their_addr;
	char buf[MAXBUFLEN];
	socklen_t addr_len;
	char s[INET6_ADDRSTRLEN];

	memset(&hints, 0, sizeof hints);
	hints.ai_family = AF_INET; // set to AF_INET to force IPv4
	hints.ai_socktype = SOCK_DGRAM;
	hints.ai_flags = AI_PASSIVE; // use my IP

	if ((rv = getaddrinfo(NULL, MYPORT, &hints, &servinfo)) != 0) {
		fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(rv));
		return NULL;
	}
	// loop through all the results and bind to the first we can
	for(p = servinfo; p != NULL; p = p->ai_next) {
		if ((sockfd = socket(p->ai_family, p->ai_socktype,
			p->ai_protocol)) == -1) {
			perror("listener: socket");
			continue;
		}
		int yes=1;

		if (setsockopt(sockfd, SOL_SOCKET, SO_REUSEADDR, &yes,
            sizeof(yes)) == -1) {
			perror("setsockopt (SO_REUSEADDR)");
			exit(1);
		}
		
    	if (bind(sockfd, p->ai_addr, p->ai_addrlen) == -1) {
			close(sockfd);
			perror("listener: bind");
			continue;
		}
		
		break;
	}

	if (p == NULL) {
		fprintf(stderr, "listener: failed to bind socket\n");
		return NULL;
	}

	freeaddrinfo(servinfo);

	printf("listener: waiting to recvfrom...\n");

	addr_len = sizeof their_addr;
	
	while(1) { // recieve packets until the UDP Thread is executed
		
		if ((numbytes = recvfrom(sockfd, buf, MAXBUFLEN-1 , 0,
			(struct sockaddr *)&their_addr, &addr_len)) == -1) {
			perror("recvfrom");
			exit(1);
		}

		printf("listener: got packet from %s\n",
			inet_ntop(their_addr.ss_family,
				get_in_addr((struct sockaddr *)&their_addr),
				s, sizeof s));
		printf("listener: packet is %d bytes long\n", numbytes);
		buf[numbytes] = '\0';
		printf("listener: packet contains \"%s\"\n", buf);
		
		
		double newcRe, newcIm;
		int newMaxIterations;
		if (sscanf(buf, "%lf %lf %d", &newcRe, &newcIm, &newMaxIterations) == 3) {
			cRe = newcRe;
			cIm = newcIm;
			maxIterations = newMaxIterations;
		} 
		else {
			printf("Invalid data format received!\n");
		}
			
	}
	close(sockfd);
	return NULL;
}


int main(int argc, char *argv[])
{
  // Setting display and knobs registers - given codes ///
  unsigned c;
  int i, j;
  parlcd_mem_base = map_phys_address(PARLCD_REG_BASE_PHYS, PARLCD_REG_SIZE, 0);
  if (parlcd_mem_base == NULL)
    exit(1);
  parlcd_hx8357_init(parlcd_mem_base);
  parlcd_write_cmd(parlcd_mem_base, 0x2c);
  for (i = 0; i < 320 ; i++) {
    for (j = 0; j < 480 ; j++) {
      c = 0;
      parlcd_write_data(parlcd_mem_base, c);
    }
  }  
  mem_base = map_phys_address(SPILED_REG_BASE_PHYS, SPILED_REG_SIZE, 0);
  if (mem_base == NULL)
    exit(1);
  //////////////////////////////////////////
 
  pthread_t tid1; // Menu
  pthread_t tid2; // DrawJulia
  pthread_t tid3; // AnimationJulia
  pthread_t tid4; // Refresh Knobs+
  pthread_t tid5; // UDP
  
  pthread_create(&tid4, NULL, refreshKnobsThread, NULL);
  
  // STATUS
  // - 0 Menu
  // - 1 DrawJulia
  // - 2 JuliaAnimation
  // - 3 UDP mode
  // - 4 EXIT
  status = 0;
  whileBreaker = 0;
  while(1) { // Main program loop
	
	if(status == 0) { 
		pthread_create(&tid1, NULL, drawMenuThread, NULL);
		
		while(1) {
			if(whileBreaker == 1) {
			  whileBreaker = 0;
			  pthread_cancel(tid1);	
			  break;
			}
		}
	}	
	
	if(status == 1) {
		pthread_create(&tid2, NULL, drawJuliaThread, NULL);
		
		while(1) {
			if(whileBreaker == 1) {
				whileBreaker = 0;
				pthread_cancel(tid2);	
				status = 0;
				break;
			}			
		}	
		
	}
	
	if(status == 2) {
		pthread_create(&tid3, NULL, drawAnimationThread, NULL);
		
		while(1) {
			if(whileBreaker == 1) {
					whileBreaker = 0;
				    pthread_cancel(tid3);	
				    status = 0;
				    break;
			}
		}		
	}
	
	if(status == 3) {
		pthread_create(&tid2, NULL, drawJuliaThread, NULL);
		pthread_create(&tid5, NULL, UDPThread, NULL);
		
		while(1) {
			if(whileBreaker == 1) {
					whileBreaker = 0;
				    pthread_cancel(tid2);
				    pthread_cancel(tid5);				    	
				    status = 0;
				    break;
			}
		}	
	}
	
	if(status == 4) {	// EXIT program
		for(int y = 0; y < 320; y++) {
			for(int x = 0; x < 480; x++) {
				buffer[y][x] = 0;				
			}
		}
		
		drawStringToBuffer("Semestral work by Martin Turyna", 5, 10, 0xFFFF, buffer);
		drawStringToBuffer("B35APO 2017", 7, 15, 0xFFFF, buffer);
		drawStringToBuffer("OI FEL CVUT", 9, 18, 0xFFFF, buffer);
		drawStringToBuffer(">>> Program closed! <<<", 14, 18, 0xF800, buffer);
		draw(buffer, parlcd_mem_base);
		break;
	}
  }

  while (0) {
     struct timespec loop_delay = {.tv_sec = 0, .tv_nsec = 200 * 1000 * 1000};

     *(volatile uint16_t*)(parlcd_mem_base + PARLCD_REG_DATA_o) = 0x0001;
     *(volatile uint16_t*)(parlcd_mem_base + PARLCD_REG_DATA_o) = 0x0002;
     *(volatile uint16_t*)(parlcd_mem_base + PARLCD_REG_DATA_o) = 0x0004;
     *(volatile uint16_t*)(parlcd_mem_base + PARLCD_REG_DATA_o) = 0x0008;
     *(volatile uint32_t*)(parlcd_mem_base + PARLCD_REG_DATA_o) = 0x0010;
     *(volatile uint16_t*)(parlcd_mem_base + PARLCD_REG_DATA_o) = 0x0020;

     clock_nanosleep(CLOCK_MONOTONIC, 0, &loop_delay, NULL);
  }
  return 0;
}
