%{package Kittlein_Gallo;
import java.io.IOException;
import java.util.*;
import java.io.*;%}
%token BEGIN END ';' '[' ']' ',' '_' ID T_int T_float Print CADENA '(' ')' ASIG FOREACH IN IF END_IF ELSE '<' '>' MENOI MAYOI IGUAL '+' '-' '*' '/' CTE_INT CTE_FLOAT DIST
%start PROGRAMA

%%

PROGRAMA : ListaDeclarativas BEGIN ListaEjecutables END 	   {GramLog.println("Se encuentra ListaDeclarativas BEGIN ListaEjecutables END reduzco a PROGRAMA");
																GramLog.close();
																EstrucLog.close();
																//Este es nuevo
																Al.addPolaca("End");
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
																pilaErrorPtoComa.pop();}

				 | EjecutableSimple ListaEjecutables			{//Error 
																Al.warning("Linea: " + pilaErrorPtoComa.pop() +" Una de las setencias ejecutables necesita ;");}		

                 | EjecutableSimple  ';'						{GramLog.println("Se encuentra EjecutableSimple  ';' reduzco a ListaEjecutables");
																pilaErrorPtoComa.pop();}
				 
				 | EjecutableSimple								{//Error 
																Al.warning("Linea: " + pilaErrorPtoComa.pop() +" Una de las setencias ejecutables necesita ;");}			

ListaDeclarativas: ListaDeclarativas DeclarativaSimple ';' 		{GramLog.println("Se encuentra DeclarativaSimple ';' ListaDeclarativas reduzco a ListaDeclarativas");}

				 | ListaDeclarativas DeclarativaSimple			{//Error 
																Al.warning("Linea: " + Al.getLinea() + " ; faltante al terminar la sentencia declarativa");}	

				 | DeclarativaSimple ';'						{GramLog.println("Se encuentra DeclarativaSimple ';' reduzco a ListaDeclarativas");}
				 
				 | DeclarativaSimple							{//Error 
																Al.warning("Linea: " + Al.getLinea() + " ; faltante al terminar la sentencia declarativa");}	

DeclarativaSimple     : Tipo ListaID							{GramLog.println("Se encuentra Tipo ListaID reduzco a DeclarativaSimple");
																if (($1.sval).equals("int")){
																	for (String s: ListaIds) {
																		if ((Ts.getSimbolo(s).getTipo()) == 'D')
																			Ts.getSimbolo(s).setTipo('I');
																			else
																			Al.warning("Linea: " + (Al.getLinea()+1) + " variable " + s +" ya declarada");
																}} else {
																	if(($1.sval).equals("float")){
																		for (String s: ListaIds) {
																			if ((Ts.getSimbolo(s).getTipo()) == 'D')
																			Ts.getSimbolo(s).setTipo('F');
																			else
																			Al.warning("Linea: " + (Al.getLinea()+1) + " variable " + s +" ya declarada");}	
																								}
																}
																ListaIds.clear();}
					
					  | Tipo ID '[' CTE ']'						{GramLog.println("Se encuentra Tipo ID '[' CTE ']' reduzco a DeclarativaSimple");
																Ts.setSize($2.sval,Integer.parseInt($4.sval));
																if (($1.sval).equals("int")){
																	if ((Ts.getSimbolo($2.sval).getTipo()) == 'D')
																			Ts.getSimbolo($2.sval).setTipo('I');
																			else
																			Al.warning("Linea: " + (Al.getLinea()+1) + " variable " + $2.sval +" ya declarada");
																} else {
																	if(($1.sval).equals("float")){
																		if ((Ts.getSimbolo($2.sval).getTipo()) == 'D')
																			Ts.getSimbolo($2.sval).setTipo('F');
																			else
																			Al.warning("Linea: " + (Al.getLinea()+1) + " variable " + $2.sval +" ya declarada");
																		}
																}
																Ts.getSimbolo($2.sval).setUso('A');
																Al.clearPolaca();
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
																	if ((Ts.getSimbolo($2.sval).getTipo()) == 'D')
																			Ts.getSimbolo($2.sval).setTipo('I');
																			else
																			Al.warning("Linea: " + (Al.getLinea()+1) + " variable " + $2.sval +" ya declarada");
																} else {
																	if(($1.sval).equals("float")){
																		if ((Ts.getSimbolo($2.sval).getTipo()) == 'D')
																			Ts.getSimbolo($2.sval).setTipo('F');
																			else
																			Al.warning("Linea: " + (Al.getLinea()+1) + " variable " + $2.sval +" ya declarada");
																		}
																}
																if (Ts.getSimbolo($2.sval).getTipo() != colecTipo)
																	Al.error((Al.getLinea()+1) + " Tipo del arreglo y contenido de tipos diferentes");
																Ts.getSimbolo($2.sval).setUso('A');
																Ts.getSimbolo($2.sval).asignarValor(ListaInferencia);
																ListaInferencia.clear();
																Al.clearPolaca();
																}
					  
					  | ID '[' Lista_Coleccion ']'				{//Error 
																Al.warning("Linea: " + Al.getLinea() + " tipo faltante en la declaracion de coleccion");}
					  
					  | Tipo ID Lista_Coleccion ']'				{//Error 
																Al.warning("Linea: " + Al.getLinea() + " [ faltante en la declaracion de coleccion");}
					  
					  | Tipo ID '[' Lista_Coleccion				{//Error 
																Al.warning("Linea: " + Al.getLinea() + " ] faltante en la declaracion de coleccion");}
					 
Lista_Coleccion: ElementoColeccion ',' Lista_Coleccion			{GramLog.println("Se encuentra ElementoColeccion ',' Lista_Coleccion reduzco a Lista_Coleccion");
																tam++;
																if ($1.sval.equals("_"))
																	ListaInferencia.add("?");
																	else
																	ListaInferencia.add($1.sval);
																//El arreglo esta al revez pero manu lo arregla al pasarlo a simbolo
																if (!$1.sval.equals("_")){
																	if ((Ts.getSimbolo($1.sval).getTipo() != colecTipo) && (colecTipo != 'E')){
																		colecTipo = 'E';
																		Al.error((Al.getLinea()+1)+ " Tipos incompatibles en declaracion del arreglo por inferencia");
																		}
																	}
																}
																
			   | ElementoColeccion Lista_Coleccion				{//Error 
																Al.warning("Linea: " + Al.getLinea() + " , faltante entre elementos de coleccion");}

			   | ElementoColeccion ',' ElementoColeccion		{GramLog.println("Se encuentra ElementoColeccion ',' ElementoColeccion reduzco a Lista_Coleccion");
																tam = 2;
																if ((!$1.sval.equals("_")) && (!$3.sval.equals("_"))){
																	if (Ts.getSimbolo($1.sval).getTipo() == Ts.getSimbolo($3.sval).getTipo())
																			colecTipo = Ts.getSimbolo($1.sval).getTipo();
																			else{
																			colecTipo = 'E';
																			Al.error((Al.getLinea()+1)+ " Tipos incompatibles en declaracion del arreglo por inferencia");
																			}
																} else {
																if ($1.sval.equals("_"))	
																	colecTipo = Ts.getSimbolo($3.sval).getTipo();
																	else
																	colecTipo = Ts.getSimbolo($1.sval).getTipo();
																}
																if ($3.sval.equals("_"))
																	ListaInferencia.add("?");
																	else
																	ListaInferencia.add($3.sval);
																if ($1.sval.equals("_"))
																	ListaInferencia.add("?");
																	else
																	ListaInferencia.add($1.sval);
																}
			   
			   | ElementoColeccion ElementoColeccion			{//Error 
																Al.warning("Linea: " + Al.getLinea() + " , faltante entre elementos de coleccion");}

ElementoColeccion: '_'											{GramLog.println("Se encuentra _ reduzco a ElementoColeccion");}

				 | CTE											{GramLog.println("Se encuentra CTE reduzco a ElementoColeccion");}
				 
				 | '-' CTE										{GramLog.println("Se encuentra '-' y CTE reduzco a ElementoColeccion");
																if (Ts.getSimbolo($2.sval).getUso() == 'C')
																	Ts.setNeg($2.sval);
																$$.sval = $1.sval + $2.sval;}

ListaID : ListaID ',' ID										{GramLog.println("Se encuentra ListaID ',' ID reduzco a ListaID");
																ListaIds.add($3.sval);}

		| ID													{GramLog.println("Se encuentra ID reduzco a ListaID");
																ListaIds.add($1.sval.toString());}

Tipo: 	T_int													{GramLog.println("Se encuentra T_int reduzco a Tipo");}

	|	T_float													{GramLog.println("Se encuentra T_float reduzco a Tipo");}
	
EjecutableSimple    : BloqueIF									{GramLog.println("Se encuentra BloqueIF reduzco a EjecutableSimple");
																EstrucLog.println("Sentencia IF en linea " + (Al.getLinea()+1));
																pilaErrorPtoComa.push(Al.getLinea()+1);
																
																//PREGUNTAR
																
																System.out.println("Abuela: "+pilaPolacaHelper.peek());
																System.out.println("Abuela: "+Al.getPosPolaca().toString());
																Al.addPolaca(pilaPolacaHelper.pop(),(Al.getPosPolaca()).toString());}

					| BloqueForeach								{GramLog.println("Se encuentra BloqueForeach reduzco a EjecutableSimple");
																EstrucLog.println("Sentencia ForEach en linea " + (Al.getLinea()+1));
																pilaErrorPtoComa.push(Al.getLinea()+1);}
					
					| Print '(' CADENA ')'						{GramLog.println("Se encuentra Print '(' CADENA ')' reduzco a EjecutableSimple");
															   	EstrucLog.println("Sentencia Print en linea " + (Al.getLinea()+1));
																pilaErrorPtoComa.push(Al.getLinea()+1);
																Al.addPolaca($3.sval);
                                                                Al.addPolaca($1.sval);}
					
					| Print CADENA ')'							{//Error 
																Al.warning("Linea: " + Al.getLinea() + " ( faltante en el print");
																pilaErrorPtoComa.push(Al.getLinea()+1);}
					
					| Print '(' CADENA							{//Error 
																Al.warning("Linea: " + Al.getLinea() + " ) faltante en el print");
																pilaErrorPtoComa.push(Al.getLinea()+1);}
					
					| Print  CADENA 							{//Error 
																Al.warning("Linea: " + Al.getLinea() + " ( y ) faltante en el print");
																pilaErrorPtoComa.push(Al.getLinea()+1);}
					
					| Asignacion								{GramLog.println("Se encuentra Asignacion reduzco a EjecutableSimple");
															  	EstrucLog.println("Sentencia Asignacion en linea " + (Al.getLinea()+1));
																pilaErrorPtoComa.push(Al.getLinea()+1);}
	
Asignacion: ID ASIG Expresion									{GramLog.println("Se encuentra ID ASIG Expresion reduzco a Asignacion");
																if (Ts.getSimbolo($1.sval).getTipo() == 'D')
																	Al.error((Al.getLinea()+1) + " Variable no definida");
																	else
																	if ((Ts.getSimbolo($1.sval).getTipo() != pilaTipo.peek().charValue()) && (pilaTipo.pop().charValue() != 'E'))
																		Al.error((Al.getLinea()+1) + " Tipos incompatibles en la asignacion");
																if (Ts.getSimbolo($1.sval).getUso() == 'A')
																	Al.error((Al.getLinea()+1) + " Necesario especificar posicion del arreglo");
																Al.addPolaca($1.sval);
                                                                Al.addPolaca(":=");
																}

		  | ID '[' CTE ']' ASIG Expresion						{GramLog.println("Se encuentra ID '[' CTE ']' ASIG Expresion reduzco a Asignacion");
																if (Ts.getSimbolo($1.sval).getTipo() == 'D')
																	Al.error((Al.getLinea()+1) + " Variable no definida");
																	else
																	if ((Ts.getSimbolo($1.sval).getTipo() != pilaTipo.peek().charValue()) && (pilaTipo.pop().charValue() != 'E'))
																		Al.error((Al.getLinea()+1) + " Tipos incompatibles en la asignacion");
																if (Ts.getSimbolo($3.sval).getTipo() != 'I')
																	Al.error((Al.getLinea()+1) + " Indice de tipo no valido");	
																Al.addPolaca($1.sval+ "["+ $3.sval +"]");
																Al.addPolaca(":=");}
		  
		  | ID  CTE ']' ASIG Expresion							{//Error 
																Al.warning("Linea: " + Al.getLinea() + " [ faltante en la asignacion de coleccion");
																pilaTipo.pop();}
			
		  | ID '[' CTE  ASIG Expresion							{//Error 
																Al.warning("Linea: " + Al.getLinea() + " ] faltante en la asignacion de coleccion");
																pilaTipo.pop();}
		  	
		  | ID '[' ID ']' ASIG Expresion						{GramLog.println("Se encuentra ID '[' ID ']' ASIG Expresion reduzco a Asignacion");
																if (Ts.getSimbolo($1.sval).getTipo() == 'D')
																	Al.error((Al.getLinea()+1) + " Variable no definida");
																	else
																	if ((Ts.getSimbolo($1.sval).getTipo() != pilaTipo.peek().charValue()) && (pilaTipo.pop().charValue() != 'E'))
																		Al.error((Al.getLinea()+1) + " Tipos incompatibles en la asignacion");
																if (Ts.getSimbolo($3.sval).getTipo() == 'D')
																	Al.error((Al.getLinea()+1) + " Variable no definida");
																	else
																	if (Ts.getSimbolo($3.sval).getTipo() != 'I')
																		Al.error((Al.getLinea()+1) + " Indice de tipo no valido");	
																Al.addPolaca($1.sval+ "["+ $3.sval +"]");
                                                                Al.addPolaca(":=");}
		  
		  | ID  ID ']' ASIG Expresion							{//Error 
																Al.warning("Linea: " + Al.getLinea() + " [ faltante en la asignacion de coleccion");
																pilaTipo.pop();}
		  
		  | ID '[' ID  ASIG Expresion							{//Error 
																Al.warning("Linea: " + Al.getLinea() + " ] faltante en la asignacion de coleccion");
																pilaTipo.pop();}
	
BloqueForeach: FOREACH CondColec BEGIN ListaEjecutables END		{GramLog.println("Se encuentra FOREACH ID IN ID BEGIN ListaEjecutables END reduzco a BloqueForeach");
																Al.addPolaca("");
																Al.addPolaca("BIF");
																Al.addPolaca(pilaPolacaHelper.pop(),Integer.valueOf(Al.getPosPolaca()).toString());
																Al.addPolaca((Al.getPosPolaca()-2),pilaPolacaHelper.pop().toString());
                                                                }

			 | FOREACH CondColec EjecutableSimple				{GramLog.println("Se encuentra FOREACH ID IN ID EjecutableSimple reduzco a BloqueForeach");
																//pilaErrorPtoComa.pop();
																Al.addPolaca("");
																Al.addPolaca("BIF");
                                                                Al.addPolaca(pilaPolacaHelper.pop(),Integer.valueOf(Al.getPosPolaca()).toString());
																Al.addPolaca((Al.getPosPolaca()-2),pilaPolacaHelper.pop().toString());
                                                                }
																
CondColec: ID IN ID												{GramLog.println("Se encuentra ID IN ID reduzco a CondColec");
																if (Ts.getSimbolo($1.sval).getTipo() == 'D')
																	Al.error((Al.getLinea()+1) + " Variable no definida");
																if (Ts.getSimbolo($3.sval).getTipo() == 'D')
																	Al.error((Al.getLinea()+1) + " Variable no definida");
																if (Ts.getSimbolo($3.sval).getUso() == 'A'){
																if (Ts.getSimbolo($1.sval).getTipo() == Ts.getSimbolo($3.sval).getTipo()){
																	Al.addPolaca($1.sval);
																	pilaPolacaHelper.push(Al.getPosPolaca()-1);
																	Al.addPolaca($3.sval);
																	Al.addPolaca("IN");																
																	Al.addPolaca("");
																	pilaPolacaHelper.push(Al.getPosPolaca()-1);
																	Al.addPolaca("BFF");
																} else {
																	Al.error(Al.getLinea() + " Tipos incompatibles en el foreach");
																}
																} else {
																	Al.error(Al.getLinea() + " " + $3.sval + " No ha sido declarado como arreglo");
																}
																pilaTipo.pop();
																pilaTipo.pop();
																}
	
BloqueIF : IfSinElse END_IF										{GramLog.println("Se encuentra IfSinElse END_IF reduzco a BloqueIF");
																Al.polacaIfSaver();
																pilaPolacaHelper.pop();
																pilaPolacaHelper.push(Al.getPosPolaca()-1);
																}
		
		 | IfConElse END_IF										{GramLog.println("Se encuentra IfConElse END_IF reduzco a BloqueIF");
													
																}

IfSinElse: IF '(' Comparacion ')' BEGIN ListaEjecutables END	{GramLog.println("Se encuentra IF '(' Comparacion ')' BEGIN ListaEjecutables END reduzco a IfSinElse");
																Al.addPolaca("");
                                                                Al.addPolaca(pilaPolacaHelper.pop(),Integer.valueOf(Al.getPosPolaca()+1).toString());
                                                                pilaPolacaHelper.push(Al.getPosPolaca()-1);
                                                                Al.addPolaca("BI");}

		 | IF  Comparacion ')' BEGIN ListaEjecutables END		{//Error 
																Al.warning("Linea: " + Al.getLinea() + " ( faltante en la comparacion");}
			
		 | IF '(' Comparacion  BEGIN ListaEjecutables END		{//Error 
																Al.warning("Linea: " + Al.getLinea() + " ) faltante en la comparacion");}
		 
		 | IF '(' Comparacion ')' ListaEjecutables END			{//Error 
																Al.warning("Linea: " + Al.getLinea() + " Begin faltante en la lista de sentencias");}
		 
		 | IF '(' Comparacion ')' BEGIN ListaEjecutables		{//Error 
																Al.warning("Linea: " + Al.getLinea() + " End faltante en la lista de sentencias");}

		 | IF '(' Comparacion ')' EjecutableSimple ';'			{GramLog.println("Se encuentra IF '(' Comparacion ')' EjecutableSimple reduzco a IfSinElse");
																pilaErrorPtoComa.pop();
																Al.addPolaca("");
                                                                Al.addPolaca(pilaPolacaHelper.pop(),Integer.valueOf(Al.getPosPolaca()+1).toString());
                                                                pilaPolacaHelper.push(Al.getPosPolaca()-1);
                                                                Al.addPolaca("BI");}
		 
		 | IF  Comparacion ')' EjecutableSimple ';'				{//Error 
																Al.warning("Linea: " + Al.getLinea() + " ( faltante en la lista de sentencias");
																pilaErrorPtoComa.pop();}
		 
		 | IF '(' Comparacion  EjecutableSimple ';'				{//Error 
																Al.warning("Linea: " + Al.getLinea() + " ) faltante en la comparacion");
																pilaErrorPtoComa.pop();}
	
IfConElse: IfSinElse ELSE BEGIN ListaEjecutables END			{GramLog.println("Se encuentra IfSinElse ELSE BEGIN ListaEjecutables END reduzco a IfConElse");
																Al.addPolaca(pilaPolacaHelper.pop(),Integer.valueOf(Al.getPosPolaca()+1).toString());}

		 | IfSinElse ELSE ListaEjecutables END					{//Error 
																Al.warning("Linea: " + Al.getLinea() + " Begin faltante en la lista de sentencias");}
		 
		 | IfSinElse ELSE BEGIN ListaEjecutables				{//Error
																Al.warning("Linea: " + Al.getLinea() + " End faltante en la lista de sentencias");}

		 | IfSinElse ELSE EjecutableSimple	';'					{GramLog.println("Se encuentra IfSinElse ELSE EjecutableSimple reduzco a IfConElse");
																pilaErrorPtoComa.pop();}
	
Comparacion : Expresion Comparador Expresion					{GramLog.println("Se encuentra Expresion Comparador Expresion reduzco a Comparacion");
																if ((Ts.getSimbolo($1.sval).getTipo()) == (Ts.getSimbolo($3.sval).getTipo())){
																	Al.addPolaca($2.sval);
																	Al.addPolaca("");
																	pilaPolacaHelper.push(Al.getPosPolaca()-1);
																	Al.addPolaca("BF");
																} else {
																	Al.error(Al.getLinea() + " Tipos incompatibles en la comparacion");
																} 
																pilaTipo.pop();
																pilaTipo.pop();
																}
	
Comparador : '<' 												{GramLog.println("Se encuentra < reduzco a Comparador");}

		   | '>' 												{GramLog.println("Se encuentra > reduzco a Comparador");}
		   
		   | MENOI 												{GramLog.println("Se encuentra MenorIgual reduzco a Comparador");}
		   
		   | MAYOI 												{GramLog.println("Se encuentra MayorIgual reduzco a Comparador");}
		   
		   | IGUAL												{GramLog.println("Se encuentra Igual reduzco a Comparador");}
		   
		   | DIST												{GramLog.println("Se encuentra Dist reduzco a Comparador");}
	
	
Expresion   : Expresion '+' Termino								{GramLog.println("Se encuentra Expresion '+' Termino reduzco a Expresion");
																Al.addPolaca("+");
																System.out.println("Pila Error" + pilaTipo);
																c1 = pilaTipo.pop();
																c2 = pilaTipo.pop();
																if (c1.equals(c2))
																	pilaTipo.push(c1);
																	else {
																	pilaTipo.push('E');
																	if ((!c1.equals(ErrorChar)) && (!c2.equals(ErrorChar)))
																		Al.error((Al.getLinea()+1) + " Operacion invalida, tipos incompatibles");
																	}
																System.out.println("Pila Error3 " + pilaTipo);
																}
		
			| Expresion '-' Termino								{GramLog.println("Se encuentra Expresion '–' Termino reduzco a Expresion");
																Al.addPolaca("-");
																System.out.println("Pila Error" + pilaTipo);
																c1 = pilaTipo.pop();
																c2 = pilaTipo.pop();
																if (c1.equals(c2))
																	pilaTipo.push(c1);
																	else {
																	pilaTipo.push('E');
																	if ((!c1.equals(ErrorChar)) && (!c2.equals(ErrorChar)))
																		Al.error((Al.getLinea()+1) + " Operacion invalida, tipos incompatibles");
																	}
																System.out.println("Pila Error3 " + pilaTipo);
																}
	
			| Termino											{GramLog.println("Se encuentra Termino reduzco a Expresion");}
	
Termino : Termino '*' Factor									{GramLog.println("Se encuentra Termino '*' Factor reduzco a Termino");
																Al.addPolaca("*");
																System.out.println("Pila Error" + pilaTipo);
																c1 = pilaTipo.pop();
																c2 = pilaTipo.pop();
																if (c1.equals(c2))
																	pilaTipo.push(c1);
																	else {
																	pilaTipo.push('E');
																	if ((!c1.equals(ErrorChar)) && (!c2.equals(ErrorChar)))
																		Al.error((Al.getLinea()+1) + " Operacion invalida, tipos incompatibles");
																	}
																System.out.println("Pila Error3 " + pilaTipo);}
		
		| Termino '/' Factor									{GramLog.println("Se encuentra Termino '/' Factor reduzco a Termino"); 
																Al.addPolaca("/");
																System.out.println("Pila Error" + pilaTipo);
																c1 = pilaTipo.pop();
																c2 = pilaTipo.pop();
																if (c1.equals(c2))
																	pilaTipo.push(c1);
																	else {
																	pilaTipo.push('E');
																	if ((!c1.equals(ErrorChar)) && (!c2.equals(ErrorChar)))
																		Al.error((Al.getLinea()+1) + " Operacion invalida, tipos incompatibles");
																	}
																System.out.println("Pila Error" + pilaTipo);
																}
		
		| Factor												{GramLog.println("Se encuentra Factor reduzco a Termino");}
	
Factor  : ID													{GramLog.println("Se encuentra ID reduzco a Factor");
																Al.addPolaca($1.sval);
																System.out.println("Pila Error" + pilaTipo);
																if (Ts.getSimbolo($1.sval).getTipo() == 'D')
																	Al.error((Al.getLinea()+1) + " Variable no definida");
																pilaTipo.push(Ts.getSimbolo($1.sval).getTipo());}
		| '-' Factor																
																{GramLog.println("Se encuentra Factor y '-' reduzco a Factor");
																if (Ts.getSimbolo($2.sval).getUso() == 'C')
																	Ts.setNeg($2.sval);
																$$.sval = $1.sval + $2.sval;
																Al.borraLastPolaca();
																Al.addPolaca($$.sval);
																}

		| CTE													{GramLog.println("Se encuentra CTE reduzco a Factor");
																Al.addPolaca($1.sval);
																pilaTipo.push(Ts.getSimbolo($1.sval).getTipo());
																System.out.println("Pila Error" + pilaTipo);}
		
		| ID '[' CTE ']'										{GramLog.println("Se encuentra ID'[' CTE ']' reduzco a Factor");
																Al.addPolaca($1.sval);
																Al.addPolaca($3.sval);
																Al.addPolaca("[]");
																System.out.println("Pila Error" + pilaTipo);
																if (Ts.getSimbolo($1.sval).getTipo() == 'D')
																	Al.error((Al.getLinea()+1) + " Variable no definida");
																pilaTipo.push(Ts.getSimbolo($1.sval).getTipo());}
		
		| ID CTE ']'											{//Error
																Al.warning("Linea: " + Al.getLinea() + " [ faltante en declaracion de coleccion");}
		
		| ID'[' CTE												{//Error
																Al.warning("Linea: " + Al.getLinea() + " ] faltante en declaracion de coleccion");}
		
		| ID '[' ID ']'											{GramLog.println("Se encuentra ID '[' ID ']' reduzco a Factor");
																Al.addPolaca($1.sval);
																Al.addPolaca($3.sval);
																Al.addPolaca("[]");
																if (Ts.getSimbolo($1.sval).getTipo() == 'D')
																	Al.error((Al.getLinea()+1) + " Variable no definida");
																if (Ts.getSimbolo($3.sval).getTipo() == 'D')
																	Al.error((Al.getLinea()+1) + " Variable no definida");
																	else
																	if (Ts.getSimbolo($3.sval).getTipo() != 'I')
																		Al.error((Al.getLinea()+1) + " Indice de tipo no valido");	
																pilaTipo.push(Ts.getSimbolo($1.sval).getTipo());
																}
	
CTE : CTE_INT												{GramLog.println("Se encuentra CTE_INT reduzco a CTE_POS");}

	| CTE_FLOAT												{GramLog.println("Se encuentra CTE_FLOAT reduzco a CTE_POS");}
		
%%
Stack<Integer> pilaErrorPtoComa;
Stack<Integer> pilaPolacaHelper;
Stack<Character> pilaTipo;
AnalizadorLexico Al;
TablaSimbolos Ts;
PrintStream GramLog;
PrintStream EstrucLog;
PrintStream ErrorLog;
ArrayList<String> ListaIds;
ArrayList<String> ListaInferencia;
int tam;
Character c1,c2;
Character ErrorChar;
char colecTipo;

public Parser(AnalizadorLexico AL){
                this.Al= AL;
                Ts = this.Al.getTablaDeSimbolos();
				ListaIds = new ArrayList();
				ListaInferencia = new ArrayList();
				pilaErrorPtoComa  = new Stack<Integer>();
				pilaPolacaHelper  = new Stack<Integer>();
				pilaTipo = new Stack<Character>();
				ErrorChar = new Character('E');
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
    Al.error(syntax_error);
}