#include "defs.h"

int main(void) {
	linecounter = 1;
	if (yyparse() == 0) {
		fprintf(stderr, "\nparser successfully ended\n\n");
	}
	return(EXIT_SUCCESS);
}

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
