//#include <unistd.h> /* TODO: write your own system call wrappers for read, write, exit */
//#include <stdio.h>  /* TODO: sprintf -- convert number to hex string */
//#include <string.h> /* TODO: strlen -- length of output for write */
 
int isnum(char ch)
{
        return ch >= '0' && ch <= '9';
}
 
int isspc(char ch)
{
        return ch == ' ' || ch == '\n';
}
 
static void print(unsigned num)
{
        char buf[20];
        char reverse_buf[20];

        unsigned quotient = num;
        unsigned tmp;
        int length = 0;;
        while(quotient != 0) {
            tmp = quotient % 16;
            if (tmp < 10) {
                tmp = tmp + 48;
            }
            else {
                tmp = tmp + 87;
            }

            reverse_buf[length] = tmp;
            length++;
            quotient = quotient / 16;

        }

        buf[0] = '0';
        buf[1] = 'x';

        if (length == 0) {
            buf[2] = '0';
            buf[3] = '\n';

            int retval;
            asm volatile ("int $0x80" : "=a"(retval) : "a"(4), "b"(1), "c"(buf), "d"(4) : "memory"); 
            if (retval == -1) {
                asm volatile ("int $0x80" : : "a"(1), "b"(1));
            }

        }
        else {
            int tmp_length = length - 1;
            for (int i = 2; i < length + 2; i++) {
                buf[i] = reverse_buf[tmp_length];
                tmp_length--;
            }
            buf[length + 2] = '\n';

            int retval;
            asm volatile ("int $0x80" : "=a"(retval) : "a"(4), "b"(1), "c"(buf), "d"(length + 3) : "memory"); 
            if (retval == -1) {
                asm volatile ("int $0x80" : : "a"(1), "b"(1));
            }


        }
}

/* TODO: main() is called by libc. Real entry point is _start(). */
void _start()
{
        char buf[20];
        unsigned num = 0;
        int i;
        int num_digits = 0;
        unsigned chars_in_buffer = 0;
 
        for (/* no init */; /* no end condition */; i++, chars_in_buffer--) {
            if (chars_in_buffer == 0) {
                int retval;
                asm volatile("int $0x80" : "=a"(retval) : "a"(3), "b"(0), "c"(buf), "d"(20) : "memory");
                if (retval < 0) {
                    asm volatile ("int $0x80" : : "a"(1), "b"(1));
                }
                i = 0;
                chars_in_buffer = retval;
            }
            if (num_digits > 0 && (chars_in_buffer == 0 /* EOF */ || !isnum(buf[i]))) {
                print(num);
                num_digits = 0;
                num = 0;
            }
            if (chars_in_buffer == 0 /* EOF */ || (!isspc(buf[i]) && !isnum(buf[i]))) {
                asm volatile ("int $0x80" : : "a"(1), "b"(0));
            }

            if (isnum(buf[i])) {
                num = num * 10 + buf[i] - '0';
                num_digits++;
            }
        }
}