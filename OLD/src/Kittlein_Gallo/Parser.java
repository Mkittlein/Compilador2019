//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 1 "GECAS-TS-POS.txt"
package Kittlein_Gallo;
import java.io.IOException;
import java.util.*;
//#line 21 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short READONLY=257;
public final static short WRITE=258;
public final static short PASS=259;
public final static short ID=260;
public final static short INTEGER=261;
public final static short USINTEGER=262;
public final static short DOUBLE=263;
public final static short CTE_DOUBLE=264;
public final static short CTE_USINTENGER=265;
public final static short CADENA=266;
public final static short IF=267;
public final static short ELSE=268;
public final static short END_IF=269;
public final static short FOR=270;
public final static short PRINT=271;
public final static short DIST=272;
public final static short MAYOI=273;
public final static short MENOI=274;
public final static short ASIG=275;
public final static short RETURN=276;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    1,    2,    2,    3,    3,    6,    6,
    6,    4,   10,   11,    9,    9,   13,   13,   13,   13,
   13,    8,    8,    8,    7,    7,    5,    5,    5,    5,
    5,    5,    5,    5,    5,    5,    5,    5,    5,    5,
   15,   14,   14,   19,   19,   19,   19,   19,   19,   19,
   19,   18,   18,   18,   18,   16,   20,   20,   20,   20,
   20,   20,   12,   12,   12,   21,   21,   21,   17,   17,
   17,   17,   17,   17,   17,   22,   22,
};
final static short yylen[] = {                            2,
    1,    2,    3,    2,    3,    1,    1,    1,    3,    3,
    1,    9,    2,    4,    3,    3,    1,    1,    1,    3,
    3,    3,    1,    3,    1,    1,    1,   11,   10,   11,
    9,    8,    9,    9,    4,    3,    3,    4,    3,    3,
    1,    2,    2,    7,    6,    6,    6,    6,    5,    6,
    5,    5,    4,    4,    4,    3,    1,    1,    1,    1,
    1,    1,    3,    3,    1,    3,    3,    1,    1,    1,
    6,    5,    5,    5,    6,    1,    1,
};
final static short yydefred[] = {                         0,
    0,   26,   25,    0,    0,    0,    0,    1,    0,    0,
    0,    7,    8,    0,   11,    0,   27,    0,    0,    0,
    0,    0,   76,   77,    0,    0,    0,   68,    0,   70,
    0,    0,    0,    0,    0,    2,    0,    0,    0,   13,
    0,    0,    0,    0,   42,    0,   43,    0,    0,    0,
    0,    0,   62,   58,   60,   57,   59,   61,    0,    0,
    0,    0,    0,    0,    0,   41,    0,   36,   39,    0,
    0,    3,    5,    0,   23,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   67,   66,    0,    0,   35,   38,    0,
    0,    0,    0,    0,   53,    0,   17,    0,   19,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   49,
    0,    0,   22,   24,   14,    0,   52,    0,    0,   72,
   73,    0,    0,   74,    0,   47,    0,   46,   50,   45,
    0,    0,    0,    0,   20,   21,   75,   71,   44,    0,
    0,    0,    0,    0,    0,    0,    0,   32,    0,    0,
   31,   34,    0,   33,    0,   12,    0,    0,   29,   28,
   30,
};
final static short yydgoto[] = {                          7,
   72,    9,   10,   11,   12,   13,   37,   76,   15,   16,
   44,   26,  110,   17,   67,   27,   28,   18,   19,   61,
   29,   30,
};
final static short yysindex[] = {                      -163,
  -48,    0,    0,  -34,  -26,  -37,    0,    0, -163,   42,
   59,    0,    0,  -14,    0,   54,    0, -160, -244, -175,
 -175,   22,    0,    0, -175,   12,   70,    0,    9,    0,
 -137,   66,   98,  114, -179,    0,   55, -163, -157,    0,
 -129, -129, -157,   33,    0, -103,    0,  -38,  -38,   58,
 -102,   39,    0,    0,    0,    0,    0,    0, -175, -175,
 -175,  -77, -175, -175,   66,    0, -175,    0,    0,  120,
  131,    0,    0,  -87,    0,   62,   62,  -86, -163, -163,
   51,  133, -105, -105,  -42,  -71, -163, -163,    9,    9,
  -38, -163,  134,    0,    0, -175,  121,    0,    0,  -81,
  -79,  141,  -92,   72,    0, -163,    0,   69,    0,  146,
  154, -115,  157, -163,   78,  163,   83,  165,   85,    0,
   87, -175,    0,    0,    0,  171,    0,  -41,  -40,    0,
    0,  179,  181,    0,   99,    0, -163,    0,    0,    0,
 -175, -175,  184, -175,    0,    0,    0,    0,    0,   47,
  187,   40,  -39,   46, -163,   52, -163,    0,  110, -163,
    0,    0, -163,    0,  125,    0,  126,  128,    0,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -130,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -32,    0,    0,    0,    0,    0,    0,  -25,    0,
    0,    0,    0,    0,    0,    0,    0,    1,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -36,   -5,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -12,
   -3,    0,    0,    0,    0,   19,   25,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   -1,    6,
   43,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -15,    0, -104,    0,  214,    0,    0,
    0,    0,    0,    0,    0, -134,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -55,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
  212,  220,   50,    0,    0,    0,   76,  218,   17,    0,
    0,   57,   -2,    0,  196,   28,   29,    0,    0,    0,
   90,    0,
};
final static int YYTABLESIZE=375;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         69,
    4,  159,   35,   59,   59,   25,   65,   15,   69,   69,
   69,   69,   21,   31,   69,   65,  112,   65,   65,   80,
    4,   32,   15,   46,   47,   69,   69,   69,   69,   69,
   63,   37,   65,   65,   65,   65,   65,   64,   16,   63,
   40,   63,   63,   42,   41,   92,   64,   65,   64,   64,
   64,  114,   52,   16,   59,   63,   63,   63,   63,   63,
   63,   51,   10,   64,   64,   64,   64,   64,    9,    4,
   88,   56,   58,   54,   56,   14,   48,   49,  155,   86,
   70,  111,  113,   56,   22,   38,   71,  154,   23,   24,
   69,   94,   95,   43,   97,   82,    1,   65,    2,    3,
   56,   56,   39,    4,    2,    3,    5,    6,   45,  133,
   62,   93,   42,   41,   74,   84,   83,   91,   78,  101,
  100,   63,    1,  121,   66,    4,  129,  128,   64,    6,
   75,    6,    6,   51,   51,  116,    6,  118,   68,    6,
    6,  107,  108,  109,  142,  141,    2,    3,   89,   90,
  143,  107,  108,  109,   69,   79,    1,   85,    2,    3,
   98,   87,  157,    4,   55,   56,    5,    6,  160,  150,
  151,   99,   40,  102,  163,  105,  106,  120,  123,  122,
  124,  125,    1,  126,    2,    3,  130,  132,    1,    4,
    2,    3,    5,    6,  131,    4,  127,  134,    5,    6,
  153,  158,  136,  161,  162,  164,  137,  138,  139,  140,
  144,    8,   48,   48,  107,  108,  109,  145,  146,  147,
   36,  148,   33,  149,  152,   22,   20,  156,   34,   23,
   24,   15,   15,    1,  166,   69,   69,   60,   60,   69,
   69,   69,   65,   65,   69,   40,   65,   65,   65,  169,
  170,   65,  171,   54,   18,   37,   37,   81,   73,   77,
   96,    0,   16,   16,   40,   40,   63,   63,    0,    4,
   63,   63,   63,   64,   64,   63,    4,   64,   64,   64,
    0,   50,   64,   53,   55,   57,   10,   10,   60,    0,
  103,  104,    9,    9,    0,    0,    0,  115,  117,    1,
    0,    2,    3,  119,    0,    1,    4,    2,    3,    5,
    6,    1,    4,    2,    3,    5,    6,    0,    4,    0,
    0,    5,    6,    0,    0,  135,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  165,    0,
    0,  167,    0,    0,  168,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         32,
    0,   41,   40,   43,   43,   40,   32,   44,   41,   42,
   43,   44,   61,   40,   47,   41,   59,   43,   44,  123,
  125,    5,   59,  268,  269,   58,   59,   60,   61,   62,
   32,   44,   58,   59,   60,   61,   62,   32,   44,   41,
   44,   43,   44,   58,   59,  123,   41,   31,   43,   44,
   42,  123,   25,   59,   43,   47,   58,   59,   60,   61,
   62,   40,   44,   58,   59,   60,   61,   62,   44,  125,
   32,   60,   61,   62,   32,    0,   20,   21,   32,   41,
  260,   84,   85,   41,  260,   44,  266,   41,  264,  265,
  123,   63,   64,   40,   67,   46,  260,  123,  262,  263,
   58,   59,   44,  267,  262,  263,  270,  271,  269,  112,
   41,   62,   58,   59,   39,   58,   59,   61,   43,   58,
   59,  123,  260,   96,   59,  125,   58,   59,  123,  260,
  260,  262,  263,  268,  269,   86,  267,   88,   41,  270,
  271,  257,  258,  259,   58,   59,  262,  263,   59,   60,
  122,  257,  258,  259,   41,  123,  260,  260,  262,  263,
   41,  123,  123,  267,  269,  123,  270,  271,  123,  141,
  142,   41,  260,  260,  123,  125,   44,   44,  260,   59,
  260,   41,  260,  276,  262,  263,   41,  112,  260,  267,
  262,  263,  270,  271,   41,  267,  125,   41,  270,  271,
  144,  152,  125,  154,  155,  156,   44,  125,   44,  125,
   40,    0,  268,  269,  257,  258,  259,  259,  259,   41,
    9,   41,  260,  125,   41,  260,  275,   41,  266,  264,
  265,  268,  269,  260,  125,  268,  269,  277,  277,  272,
  273,  274,  268,  269,  277,  260,  272,  273,  274,  125,
  125,  277,  125,  269,   41,  268,  269,   46,   39,   42,
   65,   -1,  268,  269,  268,  269,  268,  269,   -1,  269,
  272,  273,  274,  268,  269,  277,  276,  272,  273,  274,
   -1,  260,  277,  272,  273,  274,  268,  269,  277,   -1,
   79,   80,  268,  269,   -1,   -1,   -1,   86,   87,  260,
   -1,  262,  263,   92,   -1,  260,  267,  262,  263,  270,
  271,  260,  267,  262,  263,  270,  271,   -1,  267,   -1,
   -1,  270,  271,   -1,   -1,  114,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  157,   -1,
   -1,  160,   -1,   -1,  163,
};
}
final static short YYFINAL=7;
final static short YYMAXTOKEN=277;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"' '",null,null,null,null,null,null,null,"'('","')'","'*'","'+'",
"','","'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"READONLY","WRITE","PASS","ID",
"INTEGER","USINTEGER","DOUBLE","CTE_DOUBLE","CTE_USINTENGER","CADENA","IF",
"ELSE","END_IF","FOR","PRINT","DIST","MAYOI","MENOI","ASIG","RETURN",
"\"\\342\\200\\223\"",
};
final static String yyrule[] = {
"$accept : PROGRAMA",
"PROGRAMA : ListaSentencias",
"PROGRAMA : DeclaracionesFunciones ListaSentencias",
"ListaSentencias : SingleSentence ',' ListaSentencias",
"ListaSentencias : SingleSentence ','",
"DeclaracionesFunciones : SentenciaFuncionDeclarativa ',' DeclaracionesFunciones",
"DeclaracionesFunciones : SentenciaFuncionDeclarativa",
"SingleSentence : SentenciaEjecutable",
"SingleSentence : SentenciaDeclarativa",
"SentenciaDeclarativa : Tipo ':' ListaID",
"SentenciaDeclarativa : Tipo ';' ListaID",
"SentenciaDeclarativa : Asignacion",
"SentenciaFuncionDeclarativa : InicFun ParametroFunc '{' ListaSentencias RETURN '(' Expresion ')' '}'",
"InicFun : Tipo ID",
"ParametroFunc : '(' Tipo ID ')'",
"Asignacion : ID ASIG Expresion",
"Asignacion : ID '=' Expresion",
"TipoParametro : READONLY",
"TipoParametro : WRITE",
"TipoParametro : PASS",
"TipoParametro : WRITE ';' PASS",
"TipoParametro : WRITE ':' PASS",
"ListaID : ListaID ';' ID",
"ListaID : ID",
"ListaID : ListaID ':' ID",
"Tipo : DOUBLE",
"Tipo : USINTEGER",
"SentenciaEjecutable : BloqueIF",
"SentenciaEjecutable : FOR '(' Asignacion SeparadorComp Comparacion ';' Factor ')' '{' ListaSentencias '}'",
"SentenciaEjecutable : FOR Asignacion SeparadorComp Comparacion ';' Factor ')' '{' ListaSentencias '}'",
"SentenciaEjecutable : FOR '(' Asignacion SeparadorComp Comparacion ':' Factor ')' '{' ListaSentencias '}'",
"SentenciaEjecutable : FOR '(' Asignacion SeparadorComp Comparacion ';' Factor ')' SingleSentence",
"SentenciaEjecutable : FOR Asignacion SeparadorComp Comparacion ';' Factor ')' SingleSentence",
"SentenciaEjecutable : FOR '(' Asignacion SeparadorComp Comparacion ':' Factor ')' SingleSentence",
"SentenciaEjecutable : FOR '(' Asignacion SeparadorComp Comparacion ';' Factor ' ' SingleSentence",
"SentenciaEjecutable : PRINT '(' ID ')'",
"SentenciaEjecutable : PRINT ID ')'",
"SentenciaEjecutable : PRINT '(' ID",
"SentenciaEjecutable : PRINT '(' CADENA ')'",
"SentenciaEjecutable : PRINT CADENA ')'",
"SentenciaEjecutable : PRINT '(' CADENA",
"SeparadorComp : ';'",
"BloqueIF : IfConElse END_IF",
"BloqueIF : IfSinElse END_IF",
"IfSinElse : IF '(' Comparacion ')' '{' ListaSentencias '}'",
"IfSinElse : IF Comparacion ')' '{' ListaSentencias '}'",
"IfSinElse : IF '(' Comparacion '{' ListaSentencias '}'",
"IfSinElse : IF '(' Comparacion ')' ListaSentencias '}'",
"IfSinElse : IF '(' Comparacion ')' SingleSentence ','",
"IfSinElse : IF Comparacion ')' SingleSentence ','",
"IfSinElse : IF '(' Comparacion ' ' SingleSentence ','",
"IfSinElse : IF '(' Comparacion ')' SingleSentence",
"IfConElse : IfSinElse ELSE '{' ListaSentencias '}'",
"IfConElse : IfSinElse ELSE ListaSentencias '}'",
"IfConElse : IfSinElse ELSE '{' ListaSentencias",
"IfConElse : IfSinElse ELSE SingleSentence ','",
"Comparacion : Expresion Comparador Expresion",
"Comparador : '<'",
"Comparador : '>'",
"Comparador : MENOI",
"Comparador : MAYOI",
"Comparador : '='",
"Comparador : DIST",
"Expresion : Expresion '+' Termino",
"Expresion : Expresion \"\\342\\200\\223\" Termino",
"Expresion : Termino",
"Termino : Termino '*' Factor",
"Termino : Termino '/' Factor",
"Termino : Factor",
"Factor : ID",
"Factor : CTE",
"Factor : ID '(' ID ';' TipoParametro ')'",
"Factor : ID ID ';' TipoParametro ')'",
"Factor : ID ID ':' TipoParametro ')'",
"Factor : ID '(' ID TipoParametro ')'",
"Factor : ID '(' ID ';' Tipo ')'",
"CTE : CTE_DOUBLE",
"CTE : CTE_USINTENGER",
};

//#line 277 "GECAS-TS-POS.txt"
private Stack<Integer> pila;
private List<String> ids;
AnalizadorLexico al;
TablaSimbolos TS;




public Parser(AnalizadorLexico AL,TablaSimbolos TS){
		this.TS=TS;
        al=AL;
		ids = new ArrayList<>();
		pila  = new Stack<Integer>();
}



public int yylex(){
  Token aux=null;
  try {
    aux=al.getToken();
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
//#line 432 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 9 "GECAS-TS-POS.txt"
{System.out.println("Se encuentra ListaSentencias, reduzco a Programa");												}
break;
case 2:
//#line 11 "GECAS-TS-POS.txt"
{System.out.println("Se encuentra DeclaracionesFunciones y ListaDeSentencias, reduzco a Programa");}
break;
case 3:
//#line 13 "GECAS-TS-POS.txt"
{System.out.println("Se encuentra ListaSentencias SingleSentence, reduzco a ListaSentencias");												}
break;
case 4:
//#line 16 "GECAS-TS-POS.txt"
{System.out.println("Se encuentra SingleSentence, reduzco a ListaSentencias");												}
break;
case 5:
//#line 18 "GECAS-TS-POS.txt"
{System.out.println("Se encuentra SentenciaFuncionDeclarativa,DeclaracionesFunciones reduzco a DeclaracionesFunciones");}
break;
case 6:
//#line 20 "GECAS-TS-POS.txt"
{System.out.println("Se encuentra SentenciaFuncionDeclarativa, reduzco a DeclaracionesFunciones");	}
break;
case 7:
//#line 23 "GECAS-TS-POS.txt"
{
															System.out.println("Se encuentra SetenciaEjecutable, reduzco a SingleSentence");
															}
break;
case 8:
//#line 27 "GECAS-TS-POS.txt"
{
													System.out.println("Se encuentra SentenciaDeclarativa, reduzco a SingleSentence");
													}
break;
case 9:
//#line 33 "GECAS-TS-POS.txt"
{System.out.println("Se reconoce Tipo ; y Lista ID");
																	for (String id : ids) {
																		Simbolo aux= new Simbolo(val_peek(2).ival);
																		TS.add(id,aux);
																		}
																		ids.clear();}
break;
case 10:
//#line 40 "GECAS-TS-POS.txt"
{System.out.println("Error en declaracion de IDs, ; en lugar de :");}
break;
case 11:
//#line 43 "GECAS-TS-POS.txt"
{System.out.println("Se encontro Asignacion, se reemplaza por SentenciaDeclarativa");
														}
break;
case 12:
//#line 46 "GECAS-TS-POS.txt"
{System.out.println("Se encuentra la sentencia que declara una funcion. Se reempleza por SentenciaFuncionDeclarativa");
																												al.addPolaca("RETURN");}
break;
case 13:
//#line 53 "GECAS-TS-POS.txt"
{System.out.println("Se reconoce Inicio de Funcion");
					al.addPolaca(val_peek(0).sval);}
break;
case 14:
//#line 56 "GECAS-TS-POS.txt"
{System.out.println("Se reconoce parametro de funcion");
										  al.addPolaca(val_peek(1).sval); al.addPolaca("FUNCTION");										  }
break;
case 15:
//#line 63 "GECAS-TS-POS.txt"
{System.out.println("Se encuentra ID ASIG Expresion, se reemplaza por asignacion");
														if (!TS.contains(val_peek(2).sval)){
															al.error("variable "+val_peek(2).sval+" no declarada");
															al.setError();}
														/*else{TS.get($1.sval).setValor($3.sval);}*/
														al.addPolaca(val_peek(2).sval);
														al.addPolaca(":=");}
break;
case 16:
//#line 71 "GECAS-TS-POS.txt"
{System.out.println("Fallo al declarar asignacion, = encontrado");}
break;
case 17:
//#line 76 "GECAS-TS-POS.txt"
{System.out.println("Parametro encontrado: READONLY, se reemplaza por TipoParametro");}
break;
case 18:
//#line 78 "GECAS-TS-POS.txt"
{System.out.println("Parametro encontrado: WRITE, se reemplaza por TipoParametro");}
break;
case 19:
//#line 80 "GECAS-TS-POS.txt"
{System.out.println("Parametro encontrado: PASS, se reemplaza por TipoParametro");}
break;
case 20:
//#line 82 "GECAS-TS-POS.txt"
{System.out.println("Parametro encontrado: WRITE;PASS, se reemplaza por TipoParametro");}
break;
case 21:
//#line 84 "GECAS-TS-POS.txt"
{System.out.println("Erroe en la definicion de Tipo, ; no encontrado");}
break;
case 22:
//#line 86 "GECAS-TS-POS.txt"
{System.out.println("Se encuentra ListaID e ID,se reduce a ListaID");
																ids.add(val_peek(0).sval);
																}
break;
case 23:
//#line 90 "GECAS-TS-POS.txt"
{System.out.println("Se encuentrauna ID, se reduce a ListaID");
																ids.add(val_peek(0).sval);
																}
break;
case 24:
//#line 94 "GECAS-TS-POS.txt"
{System.out.println("Fallo al declarar IDs, : en lugar de ;");}
break;
case 25:
//#line 96 "GECAS-TS-POS.txt"
{System.out.println("Se encuentra Tipo, se decide DOUBLE");
																}
break;
case 26:
//#line 99 "GECAS-TS-POS.txt"
{System.out.println("Se encuentra Tipo, se decide USINTEGER");
																}
break;
case 27:
//#line 102 "GECAS-TS-POS.txt"
{System.out.println("Se reduce BloqueIF A sentencia");
																	al.addPolaca(pila.pop(),Integer.valueOf(al.getPosPolaca()).toString());}
break;
case 28:
//#line 105 "GECAS-TS-POS.txt"
{System.out.println("Se encuentra FOR Asignacion Comparacion Expresion ListaDeSentencias, reduzco a SentenciaEjecutable");
																													
																													al.addPolaca(pila.pop(),Integer.valueOf(al.getPosPolaca()+2).toString());
																													al.addPolaca(pila.pop().toString());																													
																													al.addPolaca("BI");																													
																													;}
break;
case 29:
//#line 112 "GECAS-TS-POS.txt"
{al.warning("Error invocando FOR, ( faltante");}
break;
case 30:
//#line 114 "GECAS-TS-POS.txt"
{al.warning("Error invocando FOR, ; faltante");}
break;
case 31:
//#line 116 "GECAS-TS-POS.txt"
{System.out.println("Se encuentra FOR Asignacion Comparacion Expresion Sentencia, reduzco a SentenciaEjecutable");
																												System.out.println(val_peek(2).sval);
																												al.addPolaca(pila.pop(),Integer.valueOf(al.getPosPolaca()+2).toString());
																												al.addPolaca(pila.pop().toString());																												
																												al.addPolaca("BI");																																																						
																												;}
break;
case 32:
//#line 123 "GECAS-TS-POS.txt"
{al.warning("Error invocando FOR, ( faltante");}
break;
case 33:
//#line 125 "GECAS-TS-POS.txt"
{al.warning("Error invocando FOR, ; faltante");}
break;
case 34:
//#line 127 "GECAS-TS-POS.txt"
{al.warning("Error invocando FOR, ) faltante");}
break;
case 35:
//#line 129 "GECAS-TS-POS.txt"
{System.out.println("Se encuentra PRINT ID, reduzco a SentenciaEjecutable");
																										al.addPolaca(val_peek(1).sval);
																										al.addPolaca(val_peek(3).sval);}
break;
case 36:
//#line 133 "GECAS-TS-POS.txt"
{al.warning("Error invocando Print, ( faltante");}
break;
case 37:
//#line 135 "GECAS-TS-POS.txt"
{al.warning("Error invocando Print, ) faltante");}
break;
case 38:
//#line 137 "GECAS-TS-POS.txt"
{System.out.println("Se encuentra PRINT CADENA, reduzco a SentenciaEjecutable");
																										al.addPolaca("STR_"+val_peek(1).sval.replace(" ","_"));
																										al.addPolaca(val_peek(3).sval);}
break;
case 39:
//#line 141 "GECAS-TS-POS.txt"
{al.warning("Error invocando Print, ( faltante");}
break;
case 40:
//#line 143 "GECAS-TS-POS.txt"
{al.warning("Error invocando Print, ) faltante");}
break;
case 41:
//#line 145 "GECAS-TS-POS.txt"
{System.out.println("Se encontro ; se reemplaza por el separador comp del for");
																										pila.push(al.getPosPolaca());}
break;
case 42:
//#line 151 "GECAS-TS-POS.txt"
{System.out.println("Se reemplaza IfConElse por BloqueIF");}
break;
case 43:
//#line 154 "GECAS-TS-POS.txt"
{System.out.println("Se reemplaza IfSinElse por BloqueIF");}
break;
case 44:
//#line 157 "GECAS-TS-POS.txt"
{System.out.println("Se reemplaza IF (Comparacion) {ListaSentencias} por IFSINELSE ");
																	al.addPolaca("");
																	al.addPolaca(pila.pop(),Integer.valueOf(al.getPosPolaca()+1).toString());
																	pila.push(al.getPosPolaca()-1);
																	al.addPolaca("BI");
																	}
break;
case 45:
//#line 164 "GECAS-TS-POS.txt"
{al.warning("( faltante en declaracion del IF");}
break;
case 46:
//#line 166 "GECAS-TS-POS.txt"
{al.warning(") faltante en declaracion del IF");}
break;
case 47:
//#line 168 "GECAS-TS-POS.txt"
{al.warning(" , faltante en declaracion del IF");}
break;
case 48:
//#line 170 "GECAS-TS-POS.txt"
{System.out.println("Se reemplaza IF (Comparacion) SingleSentence por IFSINELSE");
																	/*al.addPolaca("");*/
																	al.addPolaca(pila.pop(),Integer.valueOf(al.getPosPolaca()).toString());
																	pila.push(al.getPosPolaca()-1);
																	al.addPolaca("BI");																	
																	}
break;
case 49:
//#line 178 "GECAS-TS-POS.txt"
{al.warning("( faltante en declaracion del IF");}
break;
case 50:
//#line 180 "GECAS-TS-POS.txt"
{al.warning(") faltante en declaracion del IF");}
break;
case 51:
//#line 182 "GECAS-TS-POS.txt"
{al.warning(" , faltante en declaracion del IF");}
break;
case 52:
//#line 187 "GECAS-TS-POS.txt"
{System.out.println("Se reduce IfSinElse ELSE {ListadeSentecias} por IFConELSE");}
break;
case 53:
//#line 190 "GECAS-TS-POS.txt"
{System.out.println("{ faltante en declaracion del IF ");}
break;
case 54:
//#line 192 "GECAS-TS-POS.txt"
{System.out.println("} faltante en declaracion del IF ");}
break;
case 55:
//#line 195 "GECAS-TS-POS.txt"
{System.out.println("Se reduce IfSinElse ELSE SingleSentence por IFConELSE");}
break;
case 56:
//#line 199 "GECAS-TS-POS.txt"
{al.addPolaca(val_peek(1).sval);
														al.addPolaca("");
														pila.push(al.getPosPolaca()-1);
														al.addPolaca("BF");
														System.out.println("Se encuentra Expresion Comparador Expresion, reduzco a Comparacion");
														}
break;
case 62:
//#line 206 "GECAS-TS-POS.txt"
{
														System.out.println("Se encuentra un Simbolo de Comparacion, reduzco a Comparador");
														}
break;
case 63:
//#line 211 "GECAS-TS-POS.txt"
{
										System.out.println("Se encuentra Expresion + Termino, reduzco a Expresion");
										al.addPolaca("+");
										}
break;
case 64:
//#line 216 "GECAS-TS-POS.txt"
{
										System.out.println("Se encuentra Expresion - Termino, reduzco a Expresion");
										al.addPolaca("-");
										}
break;
case 65:
//#line 221 "GECAS-TS-POS.txt"
{
										System.out.println("Se encuentra Termino, reduzco a Expresion");
										}
break;
case 66:
//#line 228 "GECAS-TS-POS.txt"
{
										System.out.println("Se encuentra Termino * Factor, reduzco a Termino");
										al.addPolaca("*");;
										}
break;
case 67:
//#line 233 "GECAS-TS-POS.txt"
{
										System.out.println("Se encuentra Termino / Factor, reduzco a Termino");
										al.addPolaca("/");
										}
break;
case 68:
//#line 238 "GECAS-TS-POS.txt"
{
										System.out.println("Se encuentra Factor, reduzco a Termino");
										System.out.println(val_peek(0));
										}
break;
case 69:
//#line 244 "GECAS-TS-POS.txt"
{
										System.out.println("Se encuentra ID, reduzco a Factor");
										al.addPolaca(val_peek(0).sval);
										}
break;
case 70:
//#line 248 "GECAS-TS-POS.txt"
{
										System.out.println("Se encuentra CTE, reduzco a Factor");
										}
break;
case 71:
//#line 252 "GECAS-TS-POS.txt"
{ System.out.println("Se encuentra invocación a funcion ID  (ID;TipoParametro), se reduce a Expresion");
											al.addPolaca(val_peek(5).sval);al.addPolaca(val_peek(3).sval);al.addPolaca("CALL");}
break;
case 72:
//#line 255 "GECAS-TS-POS.txt"
{System.out.println("Error en la invocacion de funcion, ( faltante");}
break;
case 73:
//#line 257 "GECAS-TS-POS.txt"
{System.out.println("Error en la invocacion de funcion, : en lugar de ;");}
break;
case 74:
//#line 259 "GECAS-TS-POS.txt"
{System.out.println("Error en la invocacion de funcion, ; faltante");}
break;
case 75:
//#line 261 "GECAS-TS-POS.txt"
{System.out.println("Fallo en la declaracion de permisos");}
break;
case 76:
//#line 263 "GECAS-TS-POS.txt"
{
										System.out.println("Se encuentra CTE_DOUBLE, reduzco a CTE");
										al.addPolaca(val_peek(0).sval+"_D");																				
										}
break;
case 77:
//#line 269 "GECAS-TS-POS.txt"
{
										System.out.println("Se encuentra CTE_USINTENGER, reduzco a CTE");
										al.addPolaca(val_peek(0).sval+"_ui");																				
										}
break;
//#line 956 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
