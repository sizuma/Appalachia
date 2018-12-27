#include "defs.h"
#define INDENT_STRING "\t"

char buffer[4096];

int main(void) {
	linecounter = 1;
	yyparse();
	return(EXIT_SUCCESS);
}

void copy(void* str) {
	strcpy(buffer, str);
}

void append(void* str) {
	strcat(buffer, str);
}

char* dup() {
	return strdup(buffer);
}

char* clone(void* in) {
	copy(in);
	return dup();
};

void* concat(void* left, char* middle, void* right) {
	copy(left);
	append(middle);
	append(right);
	return dup();
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

void program(void* program) {
	copy(program);
	indent();
	printf("%s\n", dup());
}

void* statements1(void* statement) {
	return clone(statement);
}

void* statements2(void* statements, void* statement) {
	return concat(statements, "\n\n", statement);
}

void* block(void* blockBody) {
	copy("{\n");
	append(blockBody);
	append("\n}");
	return dup();
}

void* blockBody1(void* expression) {
	return clone(expression);
}

void* blockBody2(void* blockBody, void* expression) {
	return concat(blockBody, "\n", expression);
}

void* expressionCall(void* call) {
	return clone(call);
}

void* expressionValue(void* value) {
	return clone(value);
}

void* expressions1(void* expression) {
	return clone(expression);
}

void* expressions2(void* expressions, void* expression) {
	return concat(expressions, ", ", expression);
}

void* callWithArgs(void* f, void* args) {
	copy(f);
	append("(");
	append(args);
	append(")");
	return dup();
}

void* callWithoutArgs(void* f) {
	copy(f);
	append("()");
	return dup();
}

void* callAssign(void* assign) {
	return clone(assign);
}

void* assign(void* ref, void* expression) {
	return concat(ref, " = ", expression);
}

void* valueConstant(void* value) {
	return clone(value);
}

void* valueLambda(void* value) {
	return clone(value);
}

void* valueBlock(void* value) {
	return clone(value);
}

void* valueRef(void* value){
	return clone(value);
}

void* lambdaWithArgs(void* args, void* block) {
	copy("\\ ");
	append(args);
	append(" -> ");
	append(block);
	return dup();
}

void* lambdaWithoutArgs(void* block) {
	copy("\\ -> ");
	append(block);
	return dup();
}

void* ref(char* text) {
	return clone(text);
}

void* nestedRef(void* expression, char* text) {
	return concat(expression, ".", text);
}

void* number(char* text) {
	return clone(text);
}

void* string(char* text) {
	return clone(text);
}

void* this(char* text) {
	return clone(text);
}