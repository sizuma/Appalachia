all: formatter compiler

lex:
	flex rexp.lex
yacc: lex
	yacc syns.yac
	cc -W -O2 -c y.tab.c -o y.tab.o
formatter: yacc
	cc -W -O2 -c formatter.c -o formatter.o
	cc -W -o formatter formatter.o y.tab.o
compiler: yacc
	cc -W -O2 -c compiler.c -o compiler.o
	cc -W -o compiler compiler.o y.tab.o

clean:
	rm -rf *.o lex.yy.c y.tab.c formatter compiler
