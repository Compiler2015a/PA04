class Library {
 static void println(string s); /* prints string s followed by a newline */
 static void print(string s);   /* prints string s */
 static void printi(int i);     /* prints integer i */
 static void printb(boolean b); /* prints boolean b */

 static int readi();     /* reads an integer from the input */
 static string readln(); /* reads one line from the input */
 static boolean eof();   /* checks end-of-file on standard input */

 static int stoi(string s, int n); /* returns the integer that s represents
                                      or n if s is not an integer */
 static string itos(int i);   /* returns a string representation of i */
 static int[] stoa(string s); /* an array with the ascii codes of chars in s */
 static string atos(int[] a); /* builds a string from the ascii codes in a */

 static int random(int n); /* returns a random number between 0 and n-1 */
 static int time();        /* number of milliseconds since program start */
 static int exit(int i);   /* terminates the program with exit code n */
}
