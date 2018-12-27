#include "defs.h"

int main(void) {
	linecounter = 1;
	yyparse();
	return(EXIT_SUCCESS);
}

enum {CONS, NODE, LEAF};

typedef struct cell {
	int          kind;
	struct cell *head;
	struct cell *tail;
} Cell;
void visit(Cell *pointer, int level);

Cell *cons(Cell *car, Cell *cdr) {
	Cell *pointer;

	pointer = (Cell *)malloc(sizeof(Cell));
	pointer->kind = CONS;
	pointer->head = car;
	pointer->tail = cdr;
	return(pointer);
}

Cell *node(char *car, Cell *cdr) {
	Cell *pointer;

	pointer = (Cell *)malloc(sizeof(Cell));
	pointer->kind = NODE;
	pointer->head = (Cell *)strdup(car);
	pointer->tail = cdr;
	return(pointer);
}

Cell *leaf(char *car, char *cdr) {
	Cell *pointer;

	pointer = (Cell *)malloc(sizeof(Cell));
	pointer->kind = LEAF;
	pointer->head = (Cell *)strdup(car);
	pointer->tail = (Cell *)strdup(cdr);
	return(pointer);
}

Cell* nop() {
    return leaf("NOP", "");
}

void tree(Cell *pointer) {
	visit(pointer, 1);
	printf("\n");
}

void visit(Cell *pointer, int level) {
	int count;

	printf("\n");
	for (count = 0; count < level; count++) {
		printf("    ");
	}
	if (pointer->kind == CONS) {
		printf("cons(");
		visit(pointer->head, level + 1);
		visit(pointer->tail, level + 1);
		printf(")");
	}
	if (pointer->kind == NODE) {
		printf("node(");
		printf("%s ", (char *)pointer->head);
		visit(pointer->tail, level + 1);
		printf(")");
	}
	if (pointer->kind == LEAF) {
		printf("leaf(");
		printf("%s ", (char *)pointer->head);
		printf("%s", (char *)pointer->tail);
		printf(")");
	}
	return;
}

/*
    代入文のシンタックスシュガー用
    block.a.b のような参照を代入のために block.aとb に分割する
*/
char* shortenRef(Cell *cell) {
    if (cell->kind == LEAF) {
        char* result = (char*) malloc(sizeof(char)*100);
        strcat(result, "\"");
        strcat(result, (char*) cell->tail);
        strcat(result, "\"");

        cell->kind = LEAF;
        cell->head = (Cell *)"BLOCK";
        cell->tail = (Cell *)"";

        return result;
    }
    Cell* current = cell;
    Cell* lastRefCell;
    while(current->kind != LEAF) {
        if(current->kind == NODE && strcmp((char*)current->head, "ref") == 0) {
            lastRefCell = current;
            current = current->tail->tail;
        }
    }
    Cell* lastRefTail = lastRefCell->tail->head;
    char* result = (char*) malloc(sizeof(char)*100);
    strcat(result, "\"");
    strcat(result, (char*)lastRefCell->tail->tail->tail);
    strcat(result, "\"");
    lastRefCell->kind = lastRefTail->kind;
    lastRefCell->head = lastRefTail->head;
    lastRefCell->tail = lastRefTail->tail;

    return result;
}

void program(void* program) {
}

void* statements1(void* statement) {
	tree(statement);
	return statement;
}

void* statements2(void* statements, void* statement) {
	tree(statement);
	return statement;
}

void* block(void* blockBody) {
	return node("block", blockBody);
}

void* blockBody1(void* expression) {
	return expression;
}

void* blockBody2(void* blockBody, void* expression) {
	return cons(blockBody, expression);
}

void* expressionCall(void* call) {
	return call;
}

void* expressionValue(void* value) {
	return value;
}

void* expressions1(void* expression) {
	return expression;
}

void* expressions2(void* expressions, void* expression) {
	return cons(expressions, expression);
}

void* callWithArgs(void* f, void* args) {
	return node("call", cons(f, args));
}

void* callWithoutArgs(void* f) {
	return node("call", cons(f, nop()));
}

void* callAssign(void* assign) {
	return assign;
}

void* assign(void* ref, void* expression) {
	char* name = shortenRef(ref);
    return node(
        "call",
        cons(
            leaf("ID", "assign"),
            cons(
                cons(
                    ref,
                    leaf("STRING", name)
                ),
                expression
            )
        )
    );
}

void* valueConstant(void* value) {
	return value;
}

void* valueLambda(void* value) {
	return value;
}

void* valueBlock(void* value) {
	return value;
}

void* valueRef(void* value){
	return value;
}

void* lambdaWithArgs(void* args, void* block) {
	return node("lambda", cons(args, block));
}

void* lambdaWithoutArgs(void* block) {
	return node("lambda", cons(nop(), block));
}

void* ref(char* text) {
	return leaf("ID", text);
}

void* nestedRef(void* expression, char* text) {
	return node("ref", cons(expression, leaf("ID", text)));
}

void* number(char* text) {
	return leaf("NUMBER", text);
}

void* string(char* text) {
	return leaf("STRING", text);
}

void* this(char* text) {
	return leaf("BLOCK", "");
}