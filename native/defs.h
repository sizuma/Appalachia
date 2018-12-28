#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define TRUE	1
#define FALSE	0

extern char *yytext;
extern int linecounter;

int yylex(void);
void comment(void);
int yyparse(void);
void yyerror(char*);

void program(void* program);

void* statements1(void* statement);
void* statements2(void* statements, void* statement);
 
void* block(void* blockBody);

void* blockBody1(void* expression);
void* blockBody2(void* blockBody, void* expression);

void* expressionCall(void* call);
void* expressionValue(void* value);

void* expressions1(void* expression);
void* expressions2(void* expressions, void* expression);

void* callWithArgs(void* f, void* args);
void* callWithoutArgs(void* f);
void* callAssign(void* assign);
void* callList(void *list);

void* assign(void* ref, void* expression);
void* list(void* expressions);

void* valueConstant(void* value);
void* valueLambda(void* value);
void* valueBlock(void* value);
void* valueRef(void* value);

void* lambdaWithArgs(void* args, void* block);
void* lambdaWithoutArgs(void* block);

void* ref(char* text);
void* nestedRef(void* expression, char* text);

void* number(char* text);
void* string(char* text);
void* this(char* text);