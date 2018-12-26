#include "defs.h"

char buffer[4096];

#define INDENT_STRING "\t"

int main(void) {

	linecounter = 1;
	if (yyparse() == 0) {
		fprintf(stderr, "\nparser successfully ended\n\n");
	}
	return(EXIT_SUCCESS);
}

void copy(char* s) {
	strcpy(buffer, s);
}

void append(char* s) {
	strcat(buffer, s);
}

char* clone() {
	return strdup(buffer);
}

void newLine() {
	append("\n");
}

void indent() {
	char result[4096];

	result[0] = '\0';

	int indent = 0;

	for(int index=0; buffer[index] != '\0'; index++) {
		char c = buffer[index];
		int len = strlen(result);
		result[len] = c;
		result[len+1] = '\0';

		if (c == '{') indent++;
		if (c == '}') {
			indent--;
			result[strlen(result)-2] = '}';
			result[strlen(result)-1] = '\0';
		}
		if (c == '\n') {
			for(int level=0; level<indent; level++) {
				strcat(result, INDENT_STRING);
			}
		}
	}
	strcpy(buffer, result);
}