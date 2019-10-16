%{package Kittlein_Gallo;
import java.io.IOException;
import java.util.*;
import java.io.*;%}
%token BEGIN END ';' '[' ']' ',' '_' ID T_int T_float Print CADENA '(' ')' ASIG FOREACH IN IF END_IF ELSE '<' '>' MENOI MAYOI IGUAL '+' '-' '*' '/' CTE_INT CTE_FLOAT
%start PROGRAMA

%%

PROGRAMA : ListaDeclarativas BEGIN ListaEjecutables END 	   {GramLog.println("Se encuentra ListaDeclarativas BEGIN ListaEjecutables END reduzco a PROGRAMA");
																GramLog.close();
																EstrucLog.close();
																}

		 | ListaDeclarativas ListaEjecutables END				{//Error 
																Al.warning("Linea: " + Al.getLinea() + " Begin inicial faltante");}			
		 
		 | ListaDeclarativas BEGIN ListaEjecutables				{//Error 
																Al.warning("Linea: " + Al.getLinea() + " End final faltante");}
		 
		 | ListaDeclarativas ListaEjecutables					{//Error 
																Al.warning("Linea: " + Al.getLinea() + " Begin y End principal faltantes");}
                                                                                                
         | BEGIN ListaEjecutables END							{GramLog.println("Se encuentra BEGIN ListaEjecutables END reduzco a PROGRAMA");}
		 
		 | ListaEjecutables END									{//Error 
																Al.warning("Linea: " + Al.getLinea() + " Begin inicial faltante");}
		 
		 | BEGIN ListaEjecutables								{//Error 
																Al.warning("Linea: " + Al.getLinea() + " End final faltante");}
		 
		 | ListaEjecutables										{//Error 
																Al.warning("Linea: " + Al.getLinea() + " Begin y End principal faltantes");}
		 		 
ListaEjecutables : EjecutableSimple ';' ListaEjecutables		{GramLog.println("Se encuentra EjecutableSimple ';' ListaEjecutables reduzco a ListaEjecutables");
																pila.pop();}

				 | EjecutableSimple ListaEjecutables			{//Error 
																Al.warning("Linea: " + pila.pop() +" Una de las setencias ejecutables necesita ;");}		

                 | EjecutableSimple  ';'						{GramLog.println("Se encuentra EjecutableSimple  ';' reduzco a ListaEjecutables");
																pila.pop();}
				 
				 | EjecutableSimple								{//Error 
																Al.warning("Linea: " + pila.pop() +" Una de las setencias ejecutables necesita ;");}			

ListaDeclarativas: ListaDeclarativas DeclarativaSimple ';' 		{GramLog.println("Se encuentra DeclarativaSimple ';' ListaDeclarativas reduzco a ListaDeclarativas");}

				 | ListaDeclarativas DeclarativaSimple			{//Error 
																Al.warning("Linea: " + Al.getLinea() + " ; faltante al terminar la sentencia declarativa");}	

				 | DeclarativaSimple ';'						{GramLog.println("Se encuentra DeclarativaSimple ';' reduzco a ListaDeclarativas");}
				 
				 | DeclarativaSimple							{//Error 
																Al.warning("Linea: " + Al.getLinea() + " ; faltante al terminar la sentencia declarativa");}	

DeclarativaSimple     : Tipo ListaID							{GramLog.println("Se encuentra Tipo ListaID reduzco a DeclarativaSimple");
																if (($1.sval).equals("int")){
																	for (String s: ListaIds) {
																		Ts.getSimbolo(s).setTipo('I');			
																}} else {
																	if(($1.sval).equals("float")){
																		for (String s: ListaIds) {
																			Ts.getSimbolo(s).setTipo('F');}	
																								}
																}
																ListaIds.clear();}
					
					  | Tipo ID '[' CTE ']'						{GramLog.println("Se encuentra Tipo ID '[' CTE ']' reduzco a DeclarativaSimple");
																Ts.setSize($2.sval,Integer.parseInt($4.sval));
																if (($1.sval).equals("int")){
																	Ts.getSimbolo($2.sval).setTipo('I');
																} else {
																	if(($1.sval).equals("float")){
																		Ts.getSimbolo($2.sval).setTipo('F');
																		}
																}
																}
					  
					  | Tipo ID CTE ']'							{//Error 
																Al.warning("Linea: " + Al.getLinea() + " [ faltante en la declaracion de coleccion");}
					  
					  | Tipo ID '[' CTE							{//Error 
																Al.warning("Linea: " + Al.getLinea() + " ] faltante en la declaracion de coleccion");}
					  
					  | ID '[' CTE ']'							{//Error 
																Al.warning("Linea: " + Al.getLinea() + " Falta declaracion de tipo para la coleccion");}		
					  
					  | Tipo ID '[' Lista_Coleccion ']'			{GramLog.println("Se encuentra Tipo ID '[' Lista_Coleccion ']' reduzco a DeclarativaSimple");
																Ts.setSize($2.sval,tam);
																if (($1.sval).equals("int")){
																	Ts.getSimbolo($2.sval).setTipo('I');
																} else {
																	if(($1.sval).equals("float")){
																		Ts.getSimbolo($2.sval).setTipo('F');
																		}
																}
																}
					  
					  | ID '[' Lista_Coleccion ']'				{//Error 
																Al.warning("Linea: " + Al.getLinea() + " tipo faltante en la declaracion de coleccion");}
					  
					  | Tipo ID Lista_Coleccion ']'				{//Error 
																Al.warning("Linea: " + Al.getLinea() + " [ faltante en la declaracion de coleccion");}
					  
					  | Tipo ID '[' Lista_Coleccion				{//Error 
																Al.warning("Linea: " + Al.getLinea() + " ] faltante en la declaracion de coleccion");}
					 
Lista_Coleccion: ElementoColeccion ',' Lista_Coleccion			{GramLog.println("Se encuentra ElementoColeccion ',' Lista_Coleccion reduzco a Lista_Coleccion");
																tam++;}

			   | ElementoColeccion Lista_Coleccion				{//Error 
																Al.warning("Linea: " + Al.getLinea() + " , faltante entre elementos de coleccion");}

			   | ElementoColeccion ',' ElementoColeccion		{GramLog.println("Se encuentra ElementoColeccion ',' ElementoColeccion reduzco a Lista_Coleccion");
																tam = 2;}
			   
			   | ElementoColeccion ElementoColeccion			{//Error 
																Al.warning("Linea: " + Al.getLinea() + " , faltante entre elementos de coleccion");}

ElementoColeccion: '_'											{GramLog.println("Se encuentra _ reduzco a ElementoColeccion");}

				 | CTE_POS										{GramLog.println("Se encuentra CTE_POS reduzco a ElementoColeccion");}

ListaID : ListaID ',' ID										{GramLog.println("Se encuentra ListaID ',' ID reduzco a ListaID");
																ListaIds.add($3.sval);}

		| ID													{GramLog.println("Se encuentra ID reduzco a ListaID");
																ListaIds.add($1.sval.toString());}

Tipo: 	T_int													{GramLog.println("Se encuentra T_int reduzco a Tipo");}

	|	T_float													{GramLog.println("Se encuentra T_float reduzco a Tipo");}
	
EjecutableSimple    : BloqueIF									{GramLog.println("Se encuentra BloqueIF reduzco a EjecutableSimple");
																EstrucLog.println("Sentencia IF en linea " + (Al.getLinea()+1));
																pila.push(Al.getLinea()+1);};

					| BloqueForeach								{GramLog.println("Se encuentra BloqueForeach reduzco a EjecutableSimple");
																EstrucLog.println("Sentencia ForEach en linea " + (Al.getLinea()+1));
																pila.push(Al.getLinea()+1);}
					
					| Print '(' CADENA ')'						{GramLog.println("Se encuentra Print '(' CADENA ')' reduzco a EjecutableSimple");
															   	EstrucLog.println("Sentencia Print en linea " + (Al.getLinea()+1));
																pila.push(Al.getLinea()+1);}
					
					| Print CADENA ')'							{//Error 
																Al.warning("Linea: " + Al.getLinea() + " ( faltante en el print");
																pila.push(Al.getLinea()+1);}
					
					| Print '(' CADENA							{//Error 
																Al.warning("Linea: " + Al.getLinea() + " ) faltante en el print");
																pila.push(Al.getLinea()+1);}
					
					| Print  CADENA 							{//Error 
																Al.warning("Linea: " + Al.getLinea() + " ( y ) faltante en el print");
																pila.push(Al.getLinea()+1);}
					
					| Asignacion								{GramLog.println("Se encuentra Asignacion reduzco a EjecutableSimple");
															  	EstrucLog.println("Sentencia Asignacion en linea " + (Al.getLinea()+1));
																pila.push(Al.getLinea()+1);}
	
Asignacion: ID ASIG Expresion									{GramLog.println("Se encuentra ID ASIG Expresion reduzco a Asignacion");}

		  | ID '[' CTE ']' ASIG Expresion						{GramLog.println("Se encuentra ID '[' CTE ']' ASIG Expresion reduzco a Asignacion");}
		  
		  | ID  CTE ']' ASIG Expresion							{//Error 
																Al.warning("Linea: " + Al.getLinea() + " [ faltante en la asignacion de coleccion");}
			
		  | ID '[' CTE  ASIG Expresion							{//Error 
																Al.warning("Linea: " + Al.getLinea() + " ] faltante en la asignacion de coleccion");}
		  	
		  | ID '[' ID ']' ASIG Expresion						{GramLog.println("Se encuentra ID '[' ID ']' ASIG Expresion reduzco a Asignacion");}
		  
		  | ID  ID ']' ASIG Expresion							{//Error 
																Al.warning("Linea: " + Al.getLinea() + " [ faltante en la asignacion de coleccion");}
		  
		  | ID '[' ID  ASIG Expresion							{//Error 
																Al.warning("Linea: " + Al.getLinea() + " ] faltante en la asignacion de coleccion");}
	
BloqueForeach: FOREACH ID IN ID BEGIN ListaEjecutables END		{GramLog.println("Se encuentra FOREACH ID IN ID BEGIN ListaEjecutables END reduzco a BloqueForeach");}

			 | FOREACH ID IN ID EjecutableSimple				{GramLog.println("Se encuentra FOREACH ID IN ID EjecutableSimple reduzco a BloqueForeach");
																pila.pop();}
	
BloqueIF : IfSinElse END_IF										{GramLog.println("Se encuentra IfSinElse END_IF reduzco a BloqueIF");}
		
		 | IfConElse END_IF										{GramLog.println("Se encuentra IfConElse END_IF reduzco a BloqueIF");}

IfSinElse: IF '(' Comparacion ')' BEGIN ListaEjecutables END	{GramLog.println("Se encuentra IF '(' Comparacion ')' BEGIN ListaEjecutables END reduzco a IfSinElse");}

		 | IF  Comparacion ')' BEGIN ListaEjecutables END		{//Error 
																Al.warning("Linea: " + Al.getLinea() + " ( faltante en la comparacion");}
			
		 | IF '(' Comparacion  BEGIN ListaEjecutables END		{//Error 
																Al.warning("Linea: " + Al.getLinea() + " ) faltante en la comparacion");}
		 
		 | IF '(' Comparacion ')' ListaEjecutables END			{//Error 
																Al.warning("Linea: " + Al.getLinea() + " Begin faltante en la lista de sentencias");}
		 
		 | IF '(' Comparacion ')' BEGIN ListaEjecutables		{//Error 
																Al.warning("Linea: " + Al.getLinea() + " End faltante en la lista de sentencias");}

		 | IF '(' Comparacion ')' EjecutableSimple ';'			{GramLog.println("Se encuentra IF '(' Comparacion ')' EjecutableSimple reduzco a IfSinElse");
																pila.pop();}
		 
		 | IF  Comparacion ')' EjecutableSimple ';'				{//Error 
																Al.warning("Linea: " + Al.getLinea() + " ( faltante en la lista de sentencias");
																pila.pop();}
		 
		 | IF '(' Comparacion  EjecutableSimple ';'				{//Error 
																Al.warning("Linea: " + Al.getLinea() + " ) faltante en la comparacion");
																pila.pop();}
	
IfConElse: IfSinElse ELSE BEGIN ListaEjecutables END			{GramLog.println("Se encuentra IfSinElse ELSE BEGIN ListaEjecutables END reduzco a IfConElse");}

		 | IfSinElse ELSE ListaEjecutables END					{//Error 
																Al.warning("Linea: " + Al.getLinea() + " Begin faltante en la lista de sentencias");}
		 
		 | IfSinElse ELSE BEGIN ListaEjecutables				{//Error
																Al.warning("Linea: " + Al.getLinea() + " End faltante en la lista de sentencias");}

		 | IfSinElse ELSE EjecutableSimple	';'					{GramLog.println("Se encuentra IfSinElse ELSE EjecutableSimple reduzco a IfConElse");
																pila.pop();}
	
Comparacion : Expresion Comparador Expresion					{GramLog.println("Se encuentra Expresion Comparador Expresion reduzco a Comparacion");}
	
Comparador : '<' 												{GramLog.println("Se encuentra < reduzco a Comparador");}

		   | '>' 												{GramLog.println("Se encuentra > reduzco a Comparador");}
		   
		   | MENOI 												{GramLog.println("Se encuentra MenorIgual reduzco a Comparador");}
		   
		   | MAYOI 												{GramLog.println("Se encuentra MayorIgual reduzco a Comparador");}
		   
		   | IGUAL												{GramLog.println("Se encuentra Igual reduzco a Comparador");}
	
	
Expresion   : Expresion '+' Termino								{GramLog.println("Se encuentra Expresion '+' Termino reduzco a Expresion");}
		
			| Expresion '–' Termino								{GramLog.println("Se encuentra Expresion '–' Termino reduzco a Expresion");}
	
			| Termino											{GramLog.println("Se encuentra Termino reduzco a Expresion");}
	
Termino : Termino '*' Factor									{GramLog.println("Se encuentra Termino '*' Factor reduzco a Termino");}
		
		| Termino '/' Factor									{GramLog.println("Se encuentra Termino '/' Factor reduzco a Termino");}
		
		| Factor												{GramLog.println("Se encuentra Factor reduzco a Termino");}
	
Factor  : ID													{GramLog.println("Se encuentra ID reduzco a Factor");}

		| CTE													{GramLog.println("Se encuentra CTE reduzco a Factor");}
		
		| ID '[' CTE ']'										{GramLog.println("Se encuentra ID'[' CTE ']' reduzco a Factor");}
		
		| ID CTE ']'											{//Error
																Al.warning("Linea: " + Al.getLinea() + " [ faltante en declaracion de coleccion");}
		
		| ID'[' CTE												{//Error
																Al.warning("Linea: " + Al.getLinea() + " ] faltante en declaracion de coleccion");}
		
		| ID '[' ID ']'											{GramLog.println("Se encuentra ID '[' ID ']' reduzco a Factor");}
	
CTE : '-' CTE_POS												{GramLog.println("Se encuentra CTE_POS y '-' reduzco a CTE");
																 Ts.setNeg($2.sval);
																}
		
	| CTE_POS													{GramLog.println("Se encuentra CTE_POS reduzco a CTE");}
	
CTE_POS : CTE_INT												{GramLog.println("Se encuentra CTE_INT reduzco a CTE_POS");}

		| CTE_FLOAT												{GramLog.println("Se encuentra CTE_FLOAT reduzco a CTE_POS");}
		
%%
Stack<Integer> pila;
AnalizadorLexico Al;
TablaSimbolos Ts;
PrintStream GramLog;
PrintStream EstrucLog;
PrintStream ErrorLog;
ArrayList<String> ListaIds;
int tam;

public Parser(AnalizadorLexico AL){
                this.Al= AL;
                Ts = this.Al.getTablaDeSimbolos();
				ListaIds = new ArrayList();
				pila  = new Stack<Integer>();
    try {
        GramLog = new PrintStream(new File("./GramaticaLog.txt"));
		EstrucLog = new PrintStream(new File("./EstructurasSintacticas.txt"));
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
}

public String imprimirTablaSimbolos(){
    StringBuilder aux= new StringBuilder("Tabla de Simbolos: \n");
    aux.append(Ts.toString());
    return aux.toString();
}

public int yylex(){
  Token aux=null;
  try {
    aux=Al.getToken();
  } catch (IOException e) {
    e.printStackTrace();
  }
  if (aux!=null){
        yylval = new ParserVal();
        yylval.sval = aux.getT();
        yylval.ival = aux.getCod();
    return aux.getCod();
  }
  return 0;
}


private void yyerror(String syntax_error) {
    System.out.println(syntax_error);
}