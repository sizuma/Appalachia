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

void* callList(void* list) {
	return clone(list);
}

#ifdef FORMATTER
void* assign(void* ref, void* expression) {
	return concat(ref, " = ", expression);
}
void* emptyList() {
	copy("[]");
	return dup();
}
void* list(void* expressions) {
	copy("[");
	append(expressions);
	append("]");
	return dup();
}
#else
void* assign(void* ref, void* expression) {
	char* refString = (char*)ref;
	int index = strlen(refString);
	char c = refString[index];
	while(c != '.' && index > 0) {
		index--;
		c = refString[index];
	}

	if (index == 0) {
		copy("assign(this, \"");
		append(refString);
		append("\", ");
		append(expression);
		append(")");
		return dup();
	} else {
		copy("assign(");
		char tmp[4096];
		for(int i=0; i<index; i++) {
			tmp[i] = refString[i];
		}
		tmp[index] = '\0';
		append(tmp);
		append(", \"");
		append(&refString[index+1]);
		append("\", ");
		append(expression);
		append(")");
		return dup();
	}
}
void* emptyList() {
	copy("List.empty()");
	return dup();
}

void* list(void* expressions) {
	char* str = (char*) expressions;
	char exps[100][4096];
	int expsN=0;
	int expsIndex=0;
	int index=0;
	int inQuate=FALSE;
	int nest = 0;
	while(str[index] != '\0') {
		char c = str[index];
		if(c == '\"') inQuate = !inQuate;
		if(!inQuate) {
			if (c == '(' || c == '{') nest++;
			if (c == ')' || c == '}') nest--;
			if (c == ',' && nest == 0) {
				exps[expsN][expsIndex] = '\0';
				expsN++;
				expsIndex=0;
			} else {
				exps[expsN][expsIndex] = c;
				expsIndex++;
			}
			
		} else {
			exps[expsN][expsIndex] = c;
			expsIndex++;
		}	
		index++;
	}
	copy("{");
	append("List.empty()");
	for(int n=0; n<=expsN; n++) {
		append(".append(");
		append(exps[n]);
		append(")");
	}
	append(" }");
	return dup();
}
#endif

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