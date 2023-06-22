#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <signal.h>
#include <sys/wait.h>
#include <string.h>
#include <sys/types.h>

#define READ 0
#define WRITE 1

void term(int signal) { // Function from the tutorial

	if(signal == 15) { // SIGTERM should be checked (No. 15)
		fprintf(stderr, "GEN TERMINATED\n");
	}
	exit(0);
}

int main(int argc, char const *argv[])
{
	int fd[2];

	// Code from tutorial airtower.wordpress.com
	struct sigaction action;
	memset(&action, 0, sizeof(struct sigaction));
	action.sa_handler = term;
	sigaction(SIGTERM, &action, NULL);
	// End of code used from tutorial

	if(pipe(fd) != 0) {
		return 2;
	}

	pid_t GEN, NSD;

	if((GEN = fork()) == 0) { // Code for sucecssfully created GEN process
		
		close(fd[READ]);
		int ret = dup2(fd[WRITE], WRITE);
		close(fd[WRITE]);

		//fprintf( stderr, "%d HODNOTA RET\n", ret);


		if(ret < 0) {
			return 2;
		}

		while(1) {
			printf("%d %d\n", rand(), rand());
			fflush(stdout);
			sleep(1);
		}
	}
	else if(GEN == -1) {
		
		return 2;
	
	}
	else if((NSD = fork()) == 0) { // Code for successfully created NSD process
		
		close(fd[WRITE]);
		int ret2 = dup2(fd[READ], READ);
		close(fd[READ]);

		//fprintf( stderr, "%d HODNOTA RET2\n", ret2);


		if(ret2 < 0) {	
			return 2;
		}		

		int ret3 = execl("nsd", "nsd", NULL);

		if(ret3 < 0) {
			return 2;
		}
		else {
			exit(0);
		}

	}
	else if(NSD == -1) {

		return 2;
	
	}
	else { // Code for parent
		
		close(fd[READ]);
		close(fd[WRITE]);

		sleep(5);
		int ret4 = kill(GEN, SIGTERM);

		if (ret4 < 0) {
			return 2;
		}

		int GENStatus, NSDStatus;
		waitpid(GEN, &GENStatus, 0);
		waitpid(NSD, &NSDStatus, 0);
		//fprintf( stderr, "GEN STATUS: %d NSD STATUS: %d\n", GENStatus, NSDStatus);
		if( GENStatus != 0 || NSDStatus != 0) {
			
			printf("ERROR\n");
			return 1;
		
		}
		else {
		
			printf("OK\n");
		
		}
	}

	return 0;

}