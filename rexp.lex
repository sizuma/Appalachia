%{
int linecounter = 1;
%}
%option nounput
%%

"call"                          { return(CALL); }
"return"                        { return(RETURN); }
"if"                            { return(IF); }
"else"                          { return(ELSE); }
[a-zA-Z][a-zA-Z0-9]*			{ return(ID); }
[0-9]+							{ return(NUMBER); }
[0-9]+"."[0-9]+					{ return(NUMBER); }
\"[^\"]*\" 						{ return(STRING); }

"="					{ return(EQUAL); }
"("					{ return(LPAR); }
")"					{ return(RPAR); }
"{"					{ return(LBRA); }
"}"					{ return(RBRA); }
"<-"				{ return(LARROW); }
"->"				{ return(RALLOW); }
","					{ return(COMMA); }
"\n"				{ linecounter++; }
"\r\n"				{ linecounter++; }
"\r"				{ linecounter++; }
" "|"\t"				{ }
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