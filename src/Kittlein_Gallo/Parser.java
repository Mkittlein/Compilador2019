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






//#line 1 "Gramatica2019.y"
package Kittlein_Gallo;
import java.io.IOException;
import java.util.*;
import java.io.*;
//#line 22 "Parser.java"




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
public final static short BEGIN=257;
public final static short END=258;
public final static short ID=259;
public final static short T_int=260;
public final static short T_float=261;
public final static short Print=262;
public final static short CADENA=263;
public final static short ASIG=264;
public final static short FOREACH=265;
public final static short IN=266;
public final static short IF=267;
public final static short END_IF=268;
public final static short ELSE=269;
public final static short MENOI=270;
public final static short MAYOI=271;
public final static short IGUAL=272;
public final static short CTE_INT=273;
public final static short CTE_FLOAT=274;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    0,    0,    0,    0,    2,    2,
    2,    2,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    7,    7,    7,    7,    8,    8,    5,    5,    4,
    4,    3,    3,    3,    3,    3,    3,    3,   12,   12,
   12,   12,   12,   12,   12,   11,   11,   10,   10,   14,
   14,   14,   14,   14,   14,   14,   14,   15,   15,   15,
   15,   16,   17,   17,   17,   17,   17,   13,   13,   13,
   18,   18,   18,   19,   19,   19,   19,   19,   19,    6,
    6,    9,    9,
};
final static short yylen[] = {                            2,
    4,    3,    3,    2,    3,    2,    2,    1,    3,    2,
    2,    1,    2,    5,    4,    4,    4,    5,    4,    4,
    4,    3,    2,    3,    2,    1,    1,    3,    1,    1,
    1,    1,    1,    4,    3,    3,    2,    1,    3,    6,
    5,    5,    6,    5,    5,    7,    5,    2,    2,    7,
    6,    6,    6,    6,    5,    4,    4,    5,    4,    4,
    3,    3,    1,    1,    1,    1,    1,    3,    3,    1,
    3,    3,    1,    1,    1,    4,    3,    3,    4,    2,
    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,   30,   31,    0,    0,    0,    0,    0,    0,
    0,    0,   32,   33,   38,    0,    0,    0,    0,    0,
    0,    0,    0,   82,   83,    0,   81,    0,    0,    0,
    0,    0,   75,    0,    0,    0,   73,    0,    0,    6,
    0,   10,    0,    0,   48,    0,   49,    0,    5,   26,
    0,    0,    0,    0,    0,    0,    0,   80,    0,   35,
    0,    0,    0,    0,    0,   63,   64,   65,   66,   67,
    0,    0,    0,    0,    0,    0,    0,    2,    9,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   19,    0,   23,    0,   27,    0,    0,   34,    0,
    0,    0,   77,    0,    0,   57,    0,    0,    0,    0,
   56,   71,   72,    1,    0,    0,   15,   20,   28,    0,
   59,    0,    0,    0,    0,    0,   22,    0,    0,    0,
    0,   47,   79,   76,    0,    0,    0,    0,    0,   14,
   18,   58,    0,    0,    0,   52,    0,   53,   51,   46,
   50,
};
final static short yydgoto[] = {                          8,
    9,   42,   11,   12,   44,   33,   94,   54,   27,   13,
   14,   15,   34,   16,   17,   35,   73,   36,   37,
};
final static short yysindex[] = {                      -103,
 -228,  121,    0,    0,  -22, -240,  -40,    0, -125, -217,
  -49, -209,    0,    0,    0, -256, -221,  133, -206,  -17,
  -39,  -30, -241,    0,    0,  -31,    0,   17, -192, -193,
   10,  -30,    0,  -35,   40,  -12,    0, -228, -174,    0,
 -228,    0,  -19,   51,    0,  -58,    0,   49,    0,    0,
  -77,  -71,   -4,  -27,    0, -161,  -36,    0, -158,    0,
   75, -148,   74,   22,  220,    0,    0,    0,    0,    0,
  -30,  -30,  -30,  246,  -30,  -30, -141,    0,    0,  -21,
   25,   28, -137, -228, -135,  -49,  -64, -139,  -30, -131,
  -30,    0,  -92,    0,  -27,    0,  -30,  -30,    0,  257,
   42,   46,    0, -228,  258,    0,  -12,  -12,  -36, -228,
    0,    0,    0,    0,   48,   55,    0,    0,    0, -107,
    0, -131,  -30,  -36,  -30,  -36,    0,  -27,  -36,  -36,
 -228,    0,    0,    0, -106, -228, -105,  -49,  -98,    0,
    0,    0,  -36,  -36,  -90,    0,  -89,    0,    0,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  171,
    6,    0,    0,    0,    0,    0,    0,    0,  174,    0,
    0,    0,    0,    0,    0,    0,    0,  102,    0,    0,
    1,    0,    0,    0,    0,   45,    0,    0,  176,    0,
    9,    0,  235,  270,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -42,    0,  114,    0,    0,    0,
  131,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  188,    0,    0,    0,
    0,    0,    0,    0,    0, -254,    0,    0,    0,  271,
    0,    0,    0,    0,  -82,    0,    0,    0,    0,    0,
    0,   23,    0,    0,    0,    0,   67,   87,  224,    0,
    0,    0,    0,    0,  286,  287,    0,    0,    0,  -72,
    0,    0,    0,  143,    0,  155,    0,  -73,  167,  179,
    0,    0,    0,    0,    0,    0,    0, -212,    0,    0,
    0,    0,  191,  203,    0,    0, -177,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,  466,  -25,    0,    0,  478,   16,  -16,   70,    0,
    0,    0,   47,    0,    0,  163,    0,   26,   24,
};
final static int YYTABLESIZE=602;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         32,
   74,   27,   50,   12,   23,   12,   71,   71,   11,   41,
   25,   45,   46,   61,   23,   88,   93,   29,   30,   24,
   86,   90,   78,   23,   66,   23,   67,   23,  122,   75,
   18,   24,   25,    5,   76,   53,    6,   95,    7,  106,
   40,   74,   74,   74,   70,   12,   47,   74,  111,   43,
   81,   49,   27,   56,   23,   55,   55,   60,   82,   74,
   74,   59,   74,   78,   78,   78,   68,   50,   57,   78,
   61,   80,   62,   50,  132,   50,  128,   50,   95,  138,
   74,   78,   78,   78,   78,   70,   69,   70,   92,   55,
   54,   54,   58,   23,   83,  116,  107,  108,  112,  113,
   63,   37,   97,   70,   70,   98,   70,   68,  127,   68,
  100,   95,   55,   39,  103,   99,  114,  117,   23,  109,
  118,  119,  121,   96,  123,   68,   68,   69,   68,   69,
   36,   38,  125,   18,  133,  124,    5,  126,  134,    6,
  140,    7,   45,  129,  130,   69,   69,  141,   69,   55,
  142,  146,  148,    1,   42,    2,    3,    4,    5,  149,
   37,    6,   96,    7,   96,   23,   44,  150,  151,  143,
    8,  144,   39,    7,   25,    4,   25,   23,   41,   25,
   24,   25,   25,   24,   25,   24,   89,    3,   24,   36,
   43,   24,   91,   24,   65,   60,    0,   96,   84,   91,
   18,   45,   40,    5,    0,    0,    6,    0,    7,   18,
    0,   20,    5,   42,   81,    6,   81,    7,   31,   81,
    0,   81,   81,   48,   81,   44,    0,    0,   31,    0,
   27,   27,   24,   25,   68,   69,   70,   41,   72,   72,
   28,   51,   24,   25,    0,   24,   25,    0,    0,   43,
    0,   24,   25,   24,   25,   24,   25,   74,   74,   74,
  105,   40,   74,   12,   62,   74,   11,   74,   74,   74,
   74,   74,   74,   12,   12,   74,   11,   11,   29,   78,
   78,   78,   24,   25,   78,    0,    0,   78,    0,   78,
   78,   78,   78,   78,   78,    0,    0,   78,    0,    0,
    0,   70,   70,   70,    0,    0,   70,   51,    0,   70,
    0,   70,   70,   70,   70,   70,   70,    0,    0,   70,
    0,   24,   25,   68,   68,   68,    0,    0,   68,    0,
    0,   68,  101,   68,   68,   68,   68,   68,   68,    0,
    0,   68,    0,   69,   69,   69,   24,   25,   69,    0,
    0,   69,    0,   69,   69,   69,   69,   69,   69,   37,
   37,   69,    0,   37,    0,    0,   37,    0,   37,   37,
   37,   39,   39,    0,    0,   39,    0,    0,   39,   21,
   39,   39,   39,    0,   22,    0,    0,    0,   36,   36,
    0,   21,   36,   24,   25,   36,   22,   36,   36,   36,
   45,   45,    0,    0,   45,   24,   25,   45,    0,   45,
   45,   45,   42,   42,    0,    0,   42,    0,    0,   42,
    0,   42,   42,   42,   44,   44,    0,    0,   44,    0,
    0,   44,    0,   44,   44,   44,   41,   41,    0,    0,
   41,    0,    0,   41,    0,   41,   41,   41,   43,   43,
    0,    0,   43,    0,    0,   43,    0,   43,   43,   43,
   40,   40,    0,    0,   40,   10,   19,   40,    0,   40,
   40,   40,    0,    0,   39,    0,  104,    0,   18,   26,
   62,    5,   62,    0,    6,   62,    7,    0,   62,    0,
   62,   29,    0,   29,    0,   26,   29,   52,    0,   29,
    0,   29,  110,   77,   18,    0,   79,    5,   64,    0,
    6,   85,    7,  131,  136,   18,   18,    0,    5,    5,
   81,    6,    6,    7,    7,   87,   13,   17,   13,   17,
    0,   13,   17,    0,   13,   17,   13,   17,    0,    0,
  102,    0,   16,   21,   16,   21,    0,   16,   21,  120,
   16,   21,   16,   21,    0,    0,    0,  115,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  135,
  137,    0,    0,    0,    0,  139,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  145,    0,    0,    0,
    0,  147,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   44,   95,  258,   45,    0,   43,   43,    0,   59,
   93,  268,  269,  268,   45,   93,   44,   40,  259,   93,
   46,   93,    0,   45,   60,   45,   62,   45,   93,   42,
  259,  273,  274,  262,   47,   20,  265,   54,  267,   65,
  258,   41,   42,   43,    0,  258,  268,   47,   74,  259,
   93,  258,   95,   93,   45,  268,  269,   41,   43,   59,
   60,   93,   62,   41,   42,   43,    0,   95,   22,   47,
  263,   91,  266,   95,  100,   95,   93,   95,   95,  105,
   41,   59,   60,  258,   62,   41,    0,   43,   93,   20,
  268,  269,   23,   45,   44,   80,   71,   72,   75,   76,
   91,    0,  264,   59,   60,  264,   62,   41,   93,   43,
  259,  128,   43,    0,   93,   41,  258,   93,   45,   73,
   93,  259,  258,   54,  264,   59,   60,   41,   62,   43,
    0,  257,  264,  259,   93,   89,  262,   91,   93,  265,
   93,  267,    0,   97,   98,   59,   60,   93,   62,   80,
  258,  258,  258,  257,    0,  259,  260,  261,  262,  258,
   59,  265,   93,  267,   95,   45,    0,  258,  258,  123,
    0,  125,   59,    0,  257,    0,  259,   45,    0,  262,
  273,  274,  265,  257,  267,  259,  264,    0,  262,   59,
    0,  265,  264,  267,   32,  268,   -1,  128,  257,  264,
  259,   59,    0,  262,   -1,   -1,  265,   -1,  267,  259,
   -1,   91,  262,   59,  257,  265,  259,  267,  259,  262,
   -1,  264,  265,   91,  267,   59,   -1,   -1,  259,   -1,
  273,  274,  273,  274,  270,  271,  272,   59,  275,  275,
  263,  259,  273,  274,   -1,  273,  274,   -1,   -1,   59,
   -1,  273,  274,  273,  274,  273,  274,  257,  258,  259,
   41,   59,  262,  258,   41,  265,  258,  267,  268,  269,
  270,  271,  272,  268,  269,  275,  268,  269,   44,  257,
  258,  259,  273,  274,  262,   -1,   -1,  265,   -1,  267,
  268,  269,  270,  271,  272,   -1,   -1,  275,   -1,   -1,
   -1,  257,  258,  259,   -1,   -1,  262,  259,   -1,  265,
   -1,  267,  268,  269,  270,  271,  272,   -1,   -1,  275,
   -1,  273,  274,  257,  258,  259,   -1,   -1,  262,   -1,
   -1,  265,  259,  267,  268,  269,  270,  271,  272,   -1,
   -1,  275,   -1,  257,  258,  259,  273,  274,  262,   -1,
   -1,  265,   -1,  267,  268,  269,  270,  271,  272,  258,
  259,  275,   -1,  262,   -1,   -1,  265,   -1,  267,  268,
  269,  258,  259,   -1,   -1,  262,   -1,   -1,  265,  259,
  267,  268,  269,   -1,  264,   -1,   -1,   -1,  258,  259,
   -1,  259,  262,  273,  274,  265,  264,  267,  268,  269,
  258,  259,   -1,   -1,  262,  273,  274,  265,   -1,  267,
  268,  269,  258,  259,   -1,   -1,  262,   -1,   -1,  265,
   -1,  267,  268,  269,  258,  259,   -1,   -1,  262,   -1,
   -1,  265,   -1,  267,  268,  269,  258,  259,   -1,   -1,
  262,   -1,   -1,  265,   -1,  267,  268,  269,  258,  259,
   -1,   -1,  262,   -1,   -1,  265,   -1,  267,  268,  269,
  258,  259,   -1,   -1,  262,    0,    1,  265,   -1,  267,
  268,  269,   -1,   -1,    9,   -1,  257,   -1,  259,    2,
  257,  262,  259,   -1,  265,  262,  267,   -1,  265,   -1,
  267,  257,   -1,  259,   -1,   18,  262,   20,   -1,  265,
   -1,  267,  257,   38,  259,   -1,   41,  262,   31,   -1,
  265,   46,  267,  257,  257,  259,  259,   -1,  262,  262,
   43,  265,  265,  267,  267,   48,  257,  257,  259,  259,
   -1,  262,  262,   -1,  265,  265,  267,  267,   -1,   -1,
   63,   -1,  257,  257,  259,  259,   -1,  262,  262,   84,
  265,  265,  267,  267,   -1,   -1,   -1,   80,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  104,
  105,   -1,   -1,   -1,   -1,  110,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  131,   -1,   -1,   -1,
   -1,  136,
};
}
final static short YYFINAL=8;
final static short YYMAXTOKEN=275;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'",null,"'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'['",null,"']'",null,"'_'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"BEGIN","END","ID","T_int","T_float","Print",
"CADENA","ASIG","FOREACH","IN","IF","END_IF","ELSE","MENOI","MAYOI","IGUAL",
"CTE_INT","CTE_FLOAT","\"\\342\\200\\223\"",
};
final static String yyrule[] = {
"$accept : PROGRAMA",
"PROGRAMA : SentenciasDeclarativas BEGIN ListaEjecutables END",
"PROGRAMA : SentenciasDeclarativas ListaEjecutables END",
"PROGRAMA : SentenciasDeclarativas BEGIN ListaEjecutables",
"PROGRAMA : SentenciasDeclarativas ListaEjecutables",
"PROGRAMA : BEGIN ListaEjecutables END",
"PROGRAMA : ListaEjecutables END",
"PROGRAMA : BEGIN ListaEjecutables",
"PROGRAMA : ListaEjecutables",
"ListaEjecutables : EjecutableSimple ';' ListaEjecutables",
"ListaEjecutables : EjecutableSimple ListaEjecutables",
"ListaEjecutables : EjecutableSimple ';'",
"ListaEjecutables : EjecutableSimple",
"SentenciasDeclarativas : Tipo ListaID",
"SentenciasDeclarativas : Tipo ID '[' CTE ']'",
"SentenciasDeclarativas : Tipo ID CTE ']'",
"SentenciasDeclarativas : Tipo ID '[' CTE",
"SentenciasDeclarativas : ID '[' CTE ']'",
"SentenciasDeclarativas : Tipo ID '[' Lista_Coleccion ']'",
"SentenciasDeclarativas : ID '[' Lista_Coleccion ']'",
"SentenciasDeclarativas : Tipo ID Lista_Coleccion ']'",
"SentenciasDeclarativas : Tipo ID '[' Lista_Coleccion",
"Lista_Coleccion : ElementoColeccion ',' Lista_Coleccion",
"Lista_Coleccion : ElementoColeccion Lista_Coleccion",
"Lista_Coleccion : ElementoColeccion ',' ElementoColeccion",
"Lista_Coleccion : ElementoColeccion ElementoColeccion",
"ElementoColeccion : '_'",
"ElementoColeccion : CTE_POS",
"ListaID : ListaID ',' ID",
"ListaID : ID",
"Tipo : T_int",
"Tipo : T_float",
"EjecutableSimple : BloqueIF",
"EjecutableSimple : BloqueForeach",
"EjecutableSimple : Print '(' CADENA ')'",
"EjecutableSimple : Print CADENA ')'",
"EjecutableSimple : Print '(' CADENA",
"EjecutableSimple : Print CADENA",
"EjecutableSimple : Asignacion",
"Asignacion : ID ASIG Expresion",
"Asignacion : ID '[' CTE ']' ASIG Expresion",
"Asignacion : ID CTE ']' ASIG Expresion",
"Asignacion : ID '[' CTE ASIG Expresion",
"Asignacion : ID '[' ID ']' ASIG Expresion",
"Asignacion : ID ID ']' ASIG Expresion",
"Asignacion : ID '[' ID ASIG Expresion",
"BloqueForeach : FOREACH ID IN ID BEGIN ListaEjecutables END",
"BloqueForeach : FOREACH ID IN ID EjecutableSimple",
"BloqueIF : IfSinElse END_IF",
"BloqueIF : IfConElse END_IF",
"IfSinElse : IF '(' Comparacion ')' BEGIN ListaEjecutables END",
"IfSinElse : IF Comparacion ')' BEGIN ListaEjecutables END",
"IfSinElse : IF '(' Comparacion BEGIN ListaEjecutables END",
"IfSinElse : IF '(' Comparacion ')' ListaEjecutables END",
"IfSinElse : IF '(' Comparacion ')' BEGIN ListaEjecutables",
"IfSinElse : IF '(' Comparacion ')' EjecutableSimple",
"IfSinElse : IF Comparacion ')' EjecutableSimple",
"IfSinElse : IF '(' Comparacion EjecutableSimple",
"IfConElse : IfSinElse ELSE BEGIN ListaEjecutables END",
"IfConElse : IfSinElse ELSE ListaEjecutables END",
"IfConElse : IfSinElse ELSE BEGIN ListaEjecutables",
"IfConElse : IfSinElse ELSE EjecutableSimple",
"Comparacion : Expresion Comparador Expresion",
"Comparador : '<'",
"Comparador : '>'",
"Comparador : MENOI",
"Comparador : MAYOI",
"Comparador : IGUAL",
"Expresion : Expresion '+' Termino",
"Expresion : Expresion \"\\342\\200\\223\" Termino",
"Expresion : Termino",
"Termino : Termino '*' Factor",
"Termino : Termino '/' Factor",
"Termino : Factor",
"Factor : ID",
"Factor : CTE",
"Factor : ID '[' CTE ']'",
"Factor : ID CTE ']'",
"Factor : ID '[' CTE",
"Factor : ID '[' ID ']'",
"CTE : '-' CTE_POS",
"CTE : CTE_POS",
"CTE_POS : CTE_INT",
"CTE_POS : CTE_FLOAT",
};

//#line 178 "Gramatica2019.y"
AnalizadorLexico Al;
TablaSimbolos Ts;
PrintStream GramLog;

public void addTS(String key, Simbolo value){
				Ts.add(key,value);
}

public Parser(AnalizadorLexico AL){
                this.Al= AL;
                Ts = this.Al.getTablaDeSimbolos();
    try {
        GramLog = new PrintStream(new File("./GramaticaLog.txt"));
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
//#line 484 "Parser.java"
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
//#line 10 "Gramatica2019.y"
{GramLog.print("Se encuentra SentenciasDeclarativas BEGIN ListaEjecutables END reduzco a PROGRAMA");}
break;
case 5:
//#line 18 "Gramatica2019.y"
{GramLog.print("Se encuentra BEGIN ListaEjecutables END reduzco a PROGRAMA");}
break;
case 9:
//#line 26 "Gramatica2019.y"
{GramLog.print("Se encuentra EjecutableSimple ';' ListaEjecutables reduzco a ListaEjecutables");}
break;
case 11:
//#line 30 "Gramatica2019.y"
{GramLog.print("Se encuentra EjecutableSimple  ';' reduzco a ListaEjecutables");}
break;
case 13:
//#line 34 "Gramatica2019.y"
{GramLog.print("Se encuentra Tipo ListaID reduzco a SentenciasDeclarativas");}
break;
case 14:
//#line 36 "Gramatica2019.y"
{GramLog.print("Se encuentra Tipo ID '[' CTE ']' reduzco a SentenciasDeclarativas");}
break;
case 18:
//#line 44 "Gramatica2019.y"
{GramLog.print("Se encuentra Tipo ID '[' Lista_Coleccion ']' reduzco a SentenciasDeclarativas");}
break;
case 22:
//#line 52 "Gramatica2019.y"
{GramLog.print("Se encuentra ElementoColeccion ',' Lista_Coleccion reduzco a Lista_Coleccion");}
break;
case 24:
//#line 56 "Gramatica2019.y"
{GramLog.print("Se encuentra ElementoColeccion ',' ElementoColeccion reduzco a Lista_Coleccion");}
break;
case 26:
//#line 60 "Gramatica2019.y"
{GramLog.print("Se encuentra _ reduzco a ElementoColeccion");}
break;
case 27:
//#line 62 "Gramatica2019.y"
{GramLog.print("Se encuentra CTE_POS reduzco a ElementoColeccion");}
break;
case 28:
//#line 64 "Gramatica2019.y"
{GramLog.print("Se encuentra ListaID ',' ID reduzco a ListaID");}
break;
case 29:
//#line 66 "Gramatica2019.y"
{GramLog.print("Se encuentra ID reduzco a ListaID");}
break;
case 30:
//#line 68 "Gramatica2019.y"
{GramLog.print("Se encuentra T_int reduzco a Tipo");}
break;
case 31:
//#line 70 "Gramatica2019.y"
{GramLog.print("Se encuentra T_float reduzco a Tipo");}
break;
case 32:
//#line 72 "Gramatica2019.y"
{GramLog.print("Se encuentra BloqueIF reduzco a EjecutableSimple");}
break;
case 33:
//#line 74 "Gramatica2019.y"
{GramLog.print("Se encuentra BloqueForeach reduzco a EjecutableSimple");}
break;
case 34:
//#line 76 "Gramatica2019.y"
{GramLog.print("Se encuentra Print '(' CADENA ')' reduzco a EjecutableSimple");}
break;
case 38:
//#line 84 "Gramatica2019.y"
{GramLog.print("Se encuentra Asignacion reduzco a EjecutableSimple");}
break;
case 39:
//#line 86 "Gramatica2019.y"
{GramLog.print("Se encuentra ID ASIG Expresion reduzco a Asignacion");}
break;
case 40:
//#line 88 "Gramatica2019.y"
{GramLog.print("Se encuentra ID '[' CTE ']' ASIG Expresion reduzco a Asignacion");}
break;
case 43:
//#line 94 "Gramatica2019.y"
{GramLog.print("Se encuentra ID '[' ID ']' ASIG Expresion reduzco a Asignacion");}
break;
case 46:
//#line 100 "Gramatica2019.y"
{GramLog.print("Se encuentra FOREACH ID IN ID BEGIN ListaEjecutables END reduzco a BloqueForeach");}
break;
case 47:
//#line 102 "Gramatica2019.y"
{GramLog.print("Se encuentra FOREACH ID IN ID EjecutableSimple reduzco a BloqueForeach");}
break;
case 48:
//#line 104 "Gramatica2019.y"
{GramLog.print("Se encuentra IfSinElse END_IF reduzco a BloqueIF");}
break;
case 49:
//#line 106 "Gramatica2019.y"
{GramLog.print("Se encuentra IfConElse END_IF reduzco a BloqueIF");}
break;
case 50:
//#line 108 "Gramatica2019.y"
{GramLog.print("Se encuentra IF '(' Comparacion ')' BEGIN ListaEjecutables END reduzco a IfSinElse");}
break;
case 58:
//#line 124 "Gramatica2019.y"
{GramLog.print("Se encuentra IfSinElse ELSE BEGIN ListaEjecutables END reduzco a IfConElse");}
break;
case 61:
//#line 130 "Gramatica2019.y"
{GramLog.print("Encuentro IfSinElse ELSE EjecutableSimple reduzco a IfConElse");}
break;
case 62:
//#line 132 "Gramatica2019.y"
{GramLog.print("Encuentro Expresion Comparador Expresion reduzco a Comparacion");}
break;
case 63:
//#line 134 "Gramatica2019.y"
{GramLog.print("Encuentro < reduzco a Comparador");}
break;
case 64:
//#line 136 "Gramatica2019.y"
{GramLog.print("Encuentro > reduzco a Comparador");}
break;
case 65:
//#line 138 "Gramatica2019.y"
{GramLog.print("Encuentro MENOI reduzco a Comparador");}
break;
case 66:
//#line 140 "Gramatica2019.y"
{GramLog.print("Encuentro MAYOI reduzco a Comparador");}
break;
case 67:
//#line 142 "Gramatica2019.y"
{GramLog.print("Encuentro IGUAL reduzco a Comparador");}
break;
case 68:
//#line 145 "Gramatica2019.y"
{GramLog.print("Encuentro Expresion '+' Termino reduzco a Expresion");}
break;
case 69:
//#line 147 "Gramatica2019.y"
{GramLog.print("Encuentro Expresion '–' Termino reduzco a Expresion");}
break;
case 70:
//#line 149 "Gramatica2019.y"
{GramLog.print("Encuentro Termino reduzco a Expresion");}
break;
case 71:
//#line 151 "Gramatica2019.y"
{GramLog.print("Encuentro Termino '*' Factor reduzco a Termino");}
break;
case 72:
//#line 153 "Gramatica2019.y"
{GramLog.print("Encuentro Termino '/' Factor reduzco a Termino");}
break;
case 73:
//#line 155 "Gramatica2019.y"
{GramLog.print("Encuentro Factor reduzco a Termino");}
break;
case 74:
//#line 157 "Gramatica2019.y"
{GramLog.print("Encuentro ID reduzco a Factor");}
break;
case 75:
//#line 159 "Gramatica2019.y"
{GramLog.print("Encuentro CTE reduzco a Factor");}
break;
case 76:
//#line 161 "Gramatica2019.y"
{GramLog.print("Encuentro ID'[' CTE ']' reduzco a Factor");}
break;
case 79:
//#line 167 "Gramatica2019.y"
{GramLog.print("Encuentro ID '[' ID ']'	 reduzco a Factor");}
break;
case 80:
//#line 169 "Gramatica2019.y"
{GramLog.print("Encuentro CTE_POS y '-' reduzco a CTE");}
break;
case 81:
//#line 171 "Gramatica2019.y"
{GramLog.print("Encuentro CTE_POS reduzco a CTE");}
break;
case 82:
//#line 173 "Gramatica2019.y"
{GramLog.print("Encuentro CTE_INT reduzco a CTE_POS");}
break;
case 83:
//#line 175 "Gramatica2019.y"
{GramLog.print("Encuentro CTE_FLOAT reduzco a CTE_POS");}
break;
//#line 829 "Parser.java"
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
