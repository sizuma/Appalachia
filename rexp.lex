%{
int linecounter = 1;
%}
%option nounput
%%

[a-zA-Z_][a-zA-Z0-9_]*			{ return(ID); }
[0-9]+							{ return(NUMBER); }
[0-9]*"."[0-9]+					{ return(NUMBER); }
\"[^\"]*\" 						{ return(STRING); }
"="					{ return(EQUAL); }

"("					{ return(LPAR); }
")"					{ return(RPAR); }
"{"					{ return(LBRA); }
"}"					{ return(RBRA); }


"\n"				{ linecounter++; }
"\r\n"				{ linecounter++; }
"\r"				{ linecounter++; }

"/*"					{ comment(); }

%%

int yywrap(void) {
	return(1);
}

void comment(void) {
	int boolean, first, second;

	boolean = TRUE;
	first = input();
	while (first != EOF && boolean) {
		second = input();
		if (first == '*' && second == '/') {
			boolean = FALSE;
		} else if (first == '\r' && second == '\n') {
			linecounter++;
			first = input();
		} else {
			if (first == '\r' || first == '\n') {
				linecounter++;
			}
			first = second;
		}
	}
	if (first == EOF) {
		fprintf(stderr, "eof in comment\n");
	}
}