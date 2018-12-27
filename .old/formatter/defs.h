#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define TRUE	1
#define FALSE	0

extern char *yytext;
extern int linecounter;

extern char buffer[];

int main(void);
int yylex(void);
void comment(void);
int yyparse(void);
void yyerror(char*);

void copy(char* s);

void append(char* s);

char* clone();

void newLine();

void indent();