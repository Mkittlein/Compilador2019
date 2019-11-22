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
public final static short DIST=275;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    0,    0,    0,    0,    2,    2,
    2,    2,    1,    1,    1,    1,    4,    4,    4,    4,
    4,    4,    4,    4,    4,    8,    8,    8,    8,    9,
    9,    9,    6,    6,    5,    5,    3,    3,    3,    3,
    3,    3,    3,   12,   12,   12,   12,   12,   12,   12,
   11,   11,   14,   10,   10,   15,   15,   15,   15,   15,
   15,   15,   15,   16,   16,   16,   16,   17,   18,   18,
   18,   18,   18,   18,   13,   13,   13,   19,   19,   19,
   20,   20,   20,   20,   20,   20,   20,    7,    7,
};
final static short yylen[] = {                            2,
    4,    3,    3,    2,    3,    2,    2,    1,    3,    2,
    2,    1,    3,    2,    2,    1,    2,    5,    4,    4,
    4,    5,    4,    4,    4,    3,    2,    3,    2,    1,
    1,    2,    3,    1,    1,    1,    1,    1,    4,    3,
    3,    2,    1,    3,    6,    5,    5,    6,    5,    5,
    5,    3,    3,    2,    2,    7,    6,    6,    6,    6,
    6,    5,    5,    5,    4,    4,    4,    3,    1,    1,
    1,    1,    1,    1,    3,    3,    1,    3,    3,    1,
    1,    2,    1,    4,    3,    3,    4,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,   35,   36,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   37,   38,   43,    0,    0,    0,    0,
    0,    0,    0,   88,   89,    0,    0,    0,    0,    0,
    0,    0,    0,   83,    0,    0,    0,   80,    0,    0,
    0,    6,    0,   10,   15,    0,    0,   54,    0,   55,
    0,    5,   30,    0,    0,    0,    0,    0,    0,    0,
    0,   40,    0,    0,    0,   52,    0,    0,    0,   82,
   69,   70,   71,   72,   73,    0,    0,   74,    0,    0,
    0,    0,    0,    2,   13,    9,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   32,    0,    0,   23,
    0,   31,   27,    0,    0,    0,   39,   53,    0,    0,
    0,   85,    0,    0,    0,    0,    0,    0,    0,    0,
   78,   79,    1,    0,    0,   19,   24,   33,    0,   65,
    0,    0,    0,    0,    0,    0,   26,    0,    0,    0,
   51,   87,   84,    0,    0,    0,    0,   63,    0,   62,
   18,   22,   64,    0,    0,   58,    0,   59,    0,   57,
   56,
};
final static short yydgoto[] = {                          8,
    9,   44,   11,   12,   13,   47,   34,  103,   58,   14,
   15,   16,   35,   30,   17,   18,   36,   79,   37,   38,
};
final static short yysindex[] = {                      -123,
 -114,  -79,    0,    0,  -24, -230,  -40,    0, -103, -225,
  224,   -4, -201,    0,    0,    0, -176, -206,  -77, -187,
  -17,    4,  -30,    0,    0,   10,   32, -174, -172,  231,
  -74,  -30,  -30,    0,  -35,   68,   12,    0, -114, -147,
   56,    0, -114,    0,    0,  -19,   73,    0,  338,    0,
 -239,    0,    0,  -80, -175,  -72,   28,  -42, -140,  -13,
 -139,    0,   92, -124, -114,    0, -222,   48,  -37,    0,
    0,    0,    0,    0,    0,  -30,  -30,    0,  -30,  350,
  -30,  -30,  -98,    0,    0,    0,  -21,   70,   72,  -87,
 -114,  -83,  315,  -71,  -86,  -30,    0,  -81,  -30,    0,
  -21,    0,    0,  -42,  -30,  -30,    0,    0,  -70,   84,
   88,    0, -114,  363,  127,   12,   12,  -13, -114,  130,
    0,    0,    0,  105,  108,    0,    0,    0,  -54,    0,
 -114,  -81,  -30,  -13,  -30,  -13,    0,  -42,  -13,  -13,
    0,    0,    0,  -47, -114,  -46,  316,    0,  -41,    0,
    0,    0,    0,  -13,  -13,    0,  -34,    0, -114,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  206,
    6,  -91,    0,    0,    0,    0,    0,    0,    0,  216,
    0,    0,    0,    0,    0,    0,  102,    0,    0,    0,
    1,    0,    0,    0,    0,    0,   45,    0,    0,  218,
  331,    0,    9,    0,    0,  240,  281,    0,    0,    0,
    0,    0,    0,    0,    0,  -26,    0,    0,    0,  114,
    0,    0,  131,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  221,    0,    0,    0,    0,  -26,    0,    0,
    0,    0,  -31,    0,    0,    0,    0,  292,    0,    0,
    0,    0,    0,  -52,    0,    0,    0,    0,    0,    0,
   23,    0,    0,    0,    0,   67,   87,  220,    0,    0,
    0,    0,    0,  252,  304,    0,    0,    0,  -45,    0,
 -211,    0,    0,  143,    0,  155,    0,  272,  167,  179,
    0,    0,    0,    0,    0,    0,  -31,    0,    0,    0,
    0,    0,    0,  191,  203,    0, -168,    0, -219,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,  567,   26,  232,    0,    0,  568,  -10,  -20,    0,
    0,    0,   17,    0,    0,    0,  213,    0,   43,   -2,
};
final static int YYTABLESIZE=726;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         32,
   81,  101,   55,  114,   33,   12,   29,   76,   11,   77,
   57,   21,   95,   51,   33,   28,   67,   31,   31,   54,
   98,  132,   86,   55,   71,   55,   72,   55,   29,   76,
   70,   77,   42,   24,   25,   89,  110,  104,   11,   60,
   29,   81,   81,   81,   77,   81,   11,   81,   61,   61,
   24,   25,   53,   81,   45,   66,   67,   46,   82,   81,
   81,   50,   81,   86,   86,   86,   75,   86,   31,   86,
   52,   87,   62,   53,   93,   53,  125,   53,  121,  122,
  138,   86,   86,  104,   86,   77,   76,   77,   63,   77,
  137,   48,   49,   64,  115,  118,   59,   24,   25,   60,
   60,   42,   61,   77,   77,  120,   77,   75,   80,   75,
   84,   75,  134,   44,   85,  136,   90,  104,  116,  117,
  100,  139,  140,  105,  106,   75,   75,   76,   75,   76,
   41,   76,  107,    1,  108,    2,    3,    4,    5,  147,
  112,    6,   50,    7,   19,   76,   76,    5,   76,  154,
    6,  155,    7,   39,   47,    2,    3,    4,    5,  123,
   42,    6,  126,    7,  127,   16,   49,   16,   16,   16,
   16,  128,   44,   16,  130,   16,  142,  133,   46,   22,
  143,   22,  135,   96,   23,  148,   23,  141,  150,   41,
   48,   99,   99,   24,   25,   24,   25,  151,   24,   25,
  152,   50,   45,  153,   29,    8,   29,   29,   29,   29,
  156,  158,   29,   47,   29,    7,  160,    4,   31,  113,
    3,   19,   66,  161,    5,   49,   12,    6,   31,    7,
   24,   25,   24,   25,   73,   74,   75,   46,   27,   78,
   41,   54,   24,   25,   69,    0,   31,   31,    0,   48,
    0,   24,   25,   24,   25,   24,   25,   81,   81,   81,
   68,   45,   81,   12,    0,   81,   11,   81,   81,   81,
   81,   81,   81,   12,   12,   81,   11,   11,    0,   86,
   86,   86,   43,   34,   86,    0,    0,   86,    0,   86,
   86,   86,   86,   86,   86,   31,   31,   86,   34,    0,
    0,   77,   77,   77,    0,    0,   77,    0,    0,   77,
   20,   77,   77,   77,   77,   77,   77,    0,    0,   77,
    0,    0,    0,   75,   75,   75,    0,    0,   75,    0,
   28,   75,    0,   75,   75,   75,   75,   75,   75,   17,
    0,   75,    0,   76,   76,   76,   31,    0,   76,    0,
   21,   76,    0,   76,   76,   76,   76,   76,   76,   42,
   42,   76,   25,   42,   28,    0,   42,    0,   42,   42,
   42,   44,   44,  131,  159,   44,    0,    0,   44,    0,
   44,   44,   44,    0,    0,    0,    0,    0,   41,   41,
    0,    0,   41,    0,    0,   41,    0,   41,   41,   41,
   50,   50,    0,    0,   50,    0,    0,   50,    0,   50,
   50,   50,   47,   47,    0,    0,   47,    0,    0,   47,
    0,   47,   47,   47,   49,   49,    0,    0,   49,    0,
    0,   49,    0,   49,   49,   49,   46,   46,    0,    0,
   46,    0,    0,   46,    0,   46,   46,   46,   48,   48,
    0,    0,   48,    0,    0,   48,    0,   48,   48,   48,
   45,   45,    0,    0,   45,    0,    0,   45,    0,   45,
   45,   45,    0,    0,    0,    0,   68,    0,   68,    0,
    0,   68,   19,    0,   68,    5,   68,   65,    6,   19,
    7,    0,    5,    0,    0,    6,   34,    7,   34,   34,
   34,   34,    0,    0,   34,    0,   34,    0,   20,    0,
   20,   20,   20,   20,    0,    0,   20,    0,   20,    0,
    0,    0,    0,    0,   31,   31,    0,    0,   28,    0,
   28,   28,   28,   28,    0,    0,   28,   17,   28,   17,
   17,   17,   17,    0,    0,   17,    0,   17,   21,    0,
   21,   21,   21,   21,    0,    0,   21,    0,   21,    0,
   25,    0,   25,   25,   25,   25,   10,   20,   25,   26,
   25,    0,    0,   19,   19,   40,    5,    5,    0,    6,
    6,    7,    7,    0,    0,    0,   26,   14,   56,   14,
   14,   14,   14,    0,   91,   14,   19,   14,   68,    5,
    0,    0,    6,    0,    7,   83,  119,    0,   19,   86,
    0,    5,    0,   88,    6,   92,    7,    0,   94,  145,
    0,   19,   97,    0,    5,  102,    0,    6,    0,    7,
    0,  109,    0,    0,  111,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  124,    0,    0,  129,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  102,    0,
    0,  102,    0,    0,    0,    0,    0,    0,    0,  144,
  146,    0,    0,    0,    0,  149,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   86,    0,    0,
    0,    0,    0,    0,    0,  102,    0,    0,    0,    0,
    0,  157,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   86,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   44,   45,   41,   45,    0,   59,   43,    0,   45,
   21,   91,   93,   91,   45,   40,   91,   44,   45,  259,
   93,   93,    0,   45,   60,   45,   62,   45,  259,   43,
   33,   45,  258,  273,  274,   46,  259,   58,  258,   23,
   93,   41,   42,   43,    0,   45,  258,   47,  268,  269,
  273,  274,   95,   42,   59,   30,  268,  259,   47,   59,
   60,  268,   62,   41,   42,   43,    0,   45,   95,   47,
  258,   91,   41,   95,   49,   95,   87,   95,   81,   82,
  101,   59,   60,  104,   62,   41,    0,   43,  263,   45,
  101,  268,  269,  266,   69,   79,   93,  273,  274,  268,
  269,    0,   93,   59,   60,   80,   62,   41,   41,   43,
  258,   45,   96,    0,   59,   99,   44,  138,   76,   77,
   93,  105,  106,  264,  264,   59,   60,   41,   62,   43,
    0,   45,   41,  257,  259,  259,  260,  261,  262,  114,
   93,  265,    0,  267,  259,   59,   60,  262,   62,  133,
  265,  135,  267,  257,    0,  259,  260,  261,  262,  258,
   59,  265,   93,  267,   93,  257,    0,  259,  260,  261,
  262,  259,   59,  265,  258,  267,   93,  264,    0,  259,
   93,  259,  264,  264,  264,   59,  264,  258,   59,   59,
    0,  264,  264,  273,  274,  273,  274,   93,  273,  274,
   93,   59,    0,  258,  257,    0,  259,  260,  261,  262,
  258,  258,  265,   59,  267,    0,  258,    0,  259,  257,
    0,  259,  268,  258,  262,   59,  258,  265,  259,  267,
  273,  274,  273,  274,  270,  271,  272,   59,  263,  275,
    9,  259,  273,  274,   32,   -1,  273,  274,   -1,   59,
   -1,  273,  274,  273,  274,  273,  274,  257,  258,  259,
   41,   59,  262,  258,   -1,  265,  258,  267,  268,  269,
  270,  271,  272,  268,  269,  275,  268,  269,   -1,  257,
  258,  259,   59,   44,  262,   -1,   -1,  265,   -1,  267,
  268,  269,  270,  271,  272,   44,   45,  275,   59,   -1,
   -1,  257,  258,  259,   -1,   -1,  262,   -1,   -1,  265,
   59,  267,  268,  269,  270,  271,  272,   -1,   -1,  275,
   -1,   -1,   -1,  257,  258,  259,   -1,   -1,  262,   -1,
   59,  265,   -1,  267,  268,  269,  270,  271,  272,   59,
   -1,  275,   -1,  257,  258,  259,   95,   -1,  262,   -1,
   59,  265,   -1,  267,  268,  269,  270,  271,  272,  258,
  259,  275,   59,  262,   93,   -1,  265,   -1,  267,  268,
  269,  258,  259,   59,   59,  262,   -1,   -1,  265,   -1,
  267,  268,  269,   -1,   -1,   -1,   -1,   -1,  258,  259,
   -1,   -1,  262,   -1,   -1,  265,   -1,  267,  268,  269,
  258,  259,   -1,   -1,  262,   -1,   -1,  265,   -1,  267,
  268,  269,  258,  259,   -1,   -1,  262,   -1,   -1,  265,
   -1,  267,  268,  269,  258,  259,   -1,   -1,  262,   -1,
   -1,  265,   -1,  267,  268,  269,  258,  259,   -1,   -1,
  262,   -1,   -1,  265,   -1,  267,  268,  269,  258,  259,
   -1,   -1,  262,   -1,   -1,  265,   -1,  267,  268,  269,
  258,  259,   -1,   -1,  262,   -1,   -1,  265,   -1,  267,
  268,  269,   -1,   -1,   -1,   -1,  257,   -1,  259,   -1,
   -1,  262,  259,   -1,  265,  262,  267,  257,  265,  259,
  267,   -1,  262,   -1,   -1,  265,  257,  267,  259,  260,
  261,  262,   -1,   -1,  265,   -1,  267,   -1,  257,   -1,
  259,  260,  261,  262,   -1,   -1,  265,   -1,  267,   -1,
   -1,   -1,   -1,   -1,  273,  274,   -1,   -1,  257,   -1,
  259,  260,  261,  262,   -1,   -1,  265,  257,  267,  259,
  260,  261,  262,   -1,   -1,  265,   -1,  267,  257,   -1,
  259,  260,  261,  262,   -1,   -1,  265,   -1,  267,   -1,
  257,   -1,  259,  260,  261,  262,    0,    1,  265,    2,
  267,   -1,   -1,  259,  259,    9,  262,  262,   -1,  265,
  265,  267,  267,   -1,   -1,   -1,   19,  257,   21,  259,
  260,  261,  262,   -1,  257,  265,  259,  267,   31,  262,
   -1,   -1,  265,   -1,  267,   39,  257,   -1,  259,   43,
   -1,  262,   -1,   46,  265,   49,  267,   -1,   51,  257,
   -1,  259,   55,   -1,  262,   58,   -1,  265,   -1,  267,
   -1,   65,   -1,   -1,   67,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   87,   -1,   -1,   91,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  101,   -1,
   -1,  104,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  113,
  114,   -1,   -1,   -1,   -1,  119,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  131,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  138,   -1,   -1,   -1,   -1,
   -1,  145,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  159,
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
"CTE_INT","CTE_FLOAT","DIST",
};
final static String yyrule[] = {
"$accept : PROGRAMA",
"PROGRAMA : ListaDeclarativas BEGIN ListaEjecutables END",
"PROGRAMA : ListaDeclarativas ListaEjecutables END",
"PROGRAMA : ListaDeclarativas BEGIN ListaEjecutables",
"PROGRAMA : ListaDeclarativas ListaEjecutables",
"PROGRAMA : BEGIN ListaEjecutables END",
"PROGRAMA : ListaEjecutables END",
"PROGRAMA : BEGIN ListaEjecutables",
"PROGRAMA : ListaEjecutables",
"ListaEjecutables : EjecutableSimple ';' ListaEjecutables",
"ListaEjecutables : EjecutableSimple ListaEjecutables",
"ListaEjecutables : EjecutableSimple ';'",
"ListaEjecutables : EjecutableSimple",
"ListaDeclarativas : ListaDeclarativas DeclarativaSimple ';'",
"ListaDeclarativas : ListaDeclarativas DeclarativaSimple",
"ListaDeclarativas : DeclarativaSimple ';'",
"ListaDeclarativas : DeclarativaSimple",
"DeclarativaSimple : Tipo ListaID",
"DeclarativaSimple : Tipo ID '[' CTE ']'",
"DeclarativaSimple : Tipo ID CTE ']'",
"DeclarativaSimple : Tipo ID '[' CTE",
"DeclarativaSimple : ID '[' CTE ']'",
"DeclarativaSimple : Tipo ID '[' Lista_Coleccion ']'",
"DeclarativaSimple : ID '[' Lista_Coleccion ']'",
"DeclarativaSimple : Tipo ID Lista_Coleccion ']'",
"DeclarativaSimple : Tipo ID '[' Lista_Coleccion",
"Lista_Coleccion : ElementoColeccion ',' Lista_Coleccion",
"Lista_Coleccion : ElementoColeccion Lista_Coleccion",
"Lista_Coleccion : ElementoColeccion ',' ElementoColeccion",
"Lista_Coleccion : ElementoColeccion ElementoColeccion",
"ElementoColeccion : '_'",
"ElementoColeccion : CTE",
"ElementoColeccion : '-' CTE",
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
"BloqueForeach : FOREACH CondColec BEGIN ListaEjecutables END",
"BloqueForeach : FOREACH CondColec EjecutableSimple",
"CondColec : ID IN ID",
"BloqueIF : IfSinElse END_IF",
"BloqueIF : IfConElse END_IF",
"IfSinElse : IF '(' Comparacion ')' BEGIN ListaEjecutables END",
"IfSinElse : IF Comparacion ')' BEGIN ListaEjecutables END",
"IfSinElse : IF '(' Comparacion BEGIN ListaEjecutables END",
"IfSinElse : IF '(' Comparacion ')' ListaEjecutables END",
"IfSinElse : IF '(' Comparacion ')' BEGIN ListaEjecutables",
"IfSinElse : IF '(' Comparacion ')' EjecutableSimple ';'",
"IfSinElse : IF Comparacion ')' EjecutableSimple ';'",
"IfSinElse : IF '(' Comparacion EjecutableSimple ';'",
"IfConElse : IfSinElse ELSE BEGIN ListaEjecutables END",
"IfConElse : IfSinElse ELSE ListaEjecutables END",
"IfConElse : IfSinElse ELSE BEGIN ListaEjecutables",
"IfConElse : IfSinElse ELSE EjecutableSimple ';'",
"Comparacion : Expresion Comparador Expresion",
"Comparador : '<'",
"Comparador : '>'",
"Comparador : MENOI",
"Comparador : MAYOI",
"Comparador : IGUAL",
"Comparador : DIST",
"Expresion : Expresion '+' Termino",
"Expresion : Expresion '-' Termino",
"Expresion : Termino",
"Termino : Termino '*' Factor",
"Termino : Termino '/' Factor",
"Termino : Factor",
"Factor : ID",
"Factor : '-' Factor",
"Factor : CTE",
"Factor : ID '[' CTE ']'",
"Factor : ID CTE ']'",
"Factor : ID '[' CTE",
"Factor : ID '[' ID ']'",
"CTE : CTE_INT",
"CTE : CTE_FLOAT",
};

//#line 528 "Gramatica2019.y"
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
//#line 532 "Parser.java"
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
{GramLog.println("Se encuentra ListaDeclarativas BEGIN ListaEjecutables END reduzco a PROGRAMA");
																GramLog.close();
																EstrucLog.close();
																/*Este es nuevo*/
																Al.addPolaca("End");
																}
break;
case 2:
//#line 17 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " Begin inicial faltante");}
break;
case 3:
//#line 20 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " End final faltante");}
break;
case 4:
//#line 23 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " Begin y End principal faltantes");}
break;
case 5:
//#line 26 "Gramatica2019.y"
{GramLog.println("Se encuentra BEGIN ListaEjecutables END reduzco a PROGRAMA");}
break;
case 6:
//#line 28 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " Begin inicial faltante");}
break;
case 7:
//#line 31 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " End final faltante");}
break;
case 8:
//#line 34 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " Begin y End principal faltantes");}
break;
case 9:
//#line 37 "Gramatica2019.y"
{GramLog.println("Se encuentra EjecutableSimple ';' ListaEjecutables reduzco a ListaEjecutables");
																pilaErrorPtoComa.pop();}
break;
case 10:
//#line 40 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + pilaErrorPtoComa.pop() +" Una de las setencias ejecutables necesita ;");}
break;
case 11:
//#line 43 "Gramatica2019.y"
{GramLog.println("Se encuentra EjecutableSimple  ';' reduzco a ListaEjecutables");
																pilaErrorPtoComa.pop();}
break;
case 12:
//#line 46 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + pilaErrorPtoComa.pop() +" Una de las setencias ejecutables necesita ;");}
break;
case 13:
//#line 49 "Gramatica2019.y"
{GramLog.println("Se encuentra DeclarativaSimple ';' ListaDeclarativas reduzco a ListaDeclarativas");}
break;
case 14:
//#line 51 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ; faltante al terminar la sentencia declarativa");}
break;
case 15:
//#line 54 "Gramatica2019.y"
{GramLog.println("Se encuentra DeclarativaSimple ';' reduzco a ListaDeclarativas");}
break;
case 16:
//#line 56 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ; faltante al terminar la sentencia declarativa");}
break;
case 17:
//#line 59 "Gramatica2019.y"
{GramLog.println("Se encuentra Tipo ListaID reduzco a DeclarativaSimple");
																if ((val_peek(1).sval).equals("int")){
																	for (String s: ListaIds) {
																		if ((Ts.getSimbolo(s).getTipo()) == 'D')
																			Ts.getSimbolo(s).setTipo('I');
																			else
																			Al.warning("Linea: " + (Al.getLinea()+1) + " variable " + s +" ya declarada");
																}} else {
																	if((val_peek(1).sval).equals("float")){
																		for (String s: ListaIds) {
																			if ((Ts.getSimbolo(s).getTipo()) == 'D')
																			Ts.getSimbolo(s).setTipo('F');
																			else
																			Al.warning("Linea: " + (Al.getLinea()+1) + " variable " + s +" ya declarada");}	
																								}
																}
																ListaIds.clear();}
break;
case 18:
//#line 77 "Gramatica2019.y"
{GramLog.println("Se encuentra Tipo ID '[' CTE ']' reduzco a DeclarativaSimple");
																Ts.setSize(val_peek(3).sval,Integer.parseInt(val_peek(1).sval));
																if ((val_peek(4).sval).equals("int")){
																	if ((Ts.getSimbolo(val_peek(3).sval).getTipo()) == 'D')
																			Ts.getSimbolo(val_peek(3).sval).setTipo('I');
																			else
																			Al.warning("Linea: " + (Al.getLinea()+1) + " variable " + val_peek(3).sval +" ya declarada");
																} else {
																	if((val_peek(4).sval).equals("float")){
																		if ((Ts.getSimbolo(val_peek(3).sval).getTipo()) == 'D')
																			Ts.getSimbolo(val_peek(3).sval).setTipo('F');
																			else
																			Al.warning("Linea: " + (Al.getLinea()+1) + " variable " + val_peek(3).sval +" ya declarada");
																		}
																}
																Ts.getSimbolo(val_peek(3).sval).setUso('A');
																Al.clearPolaca();
																}
break;
case 19:
//#line 96 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " [ faltante en la declaracion de coleccion");}
break;
case 20:
//#line 99 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ] faltante en la declaracion de coleccion");}
break;
case 21:
//#line 102 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " Falta declaracion de tipo para la coleccion");}
break;
case 22:
//#line 105 "Gramatica2019.y"
{GramLog.println("Se encuentra Tipo ID '[' Lista_Coleccion ']' reduzco a DeclarativaSimple");
																Ts.setSize(val_peek(3).sval,tam);
																if ((val_peek(4).sval).equals("int")){
																	if ((Ts.getSimbolo(val_peek(3).sval).getTipo()) == 'D')
																			Ts.getSimbolo(val_peek(3).sval).setTipo('I');
																			else
																			Al.warning("Linea: " + (Al.getLinea()+1) + " variable " + val_peek(3).sval +" ya declarada");
																} else {
																	if((val_peek(4).sval).equals("float")){
																		if ((Ts.getSimbolo(val_peek(3).sval).getTipo()) == 'D')
																			Ts.getSimbolo(val_peek(3).sval).setTipo('F');
																			else
																			Al.warning("Linea: " + (Al.getLinea()+1) + " variable " + val_peek(3).sval +" ya declarada");
																		}
																}
																if (Ts.getSimbolo(val_peek(3).sval).getTipo() != colecTipo)
																	Al.error((Al.getLinea()+1) + " Tipo del arreglo y contenido de tipos diferentes");
																Ts.getSimbolo(val_peek(3).sval).setUso('A');
																Ts.getSimbolo(val_peek(3).sval).asignarValor(ListaInferencia);
																ListaInferencia.clear();
																Al.clearPolaca();
																}
break;
case 23:
//#line 128 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " tipo faltante en la declaracion de coleccion");}
break;
case 24:
//#line 131 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " [ faltante en la declaracion de coleccion");}
break;
case 25:
//#line 134 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ] faltante en la declaracion de coleccion");}
break;
case 26:
//#line 137 "Gramatica2019.y"
{GramLog.println("Se encuentra ElementoColeccion ',' Lista_Coleccion reduzco a Lista_Coleccion");
																tam++;
																if (val_peek(2).sval.equals("_"))
																	ListaInferencia.add("?");
																	else
																	ListaInferencia.add(val_peek(2).sval);
																/*El arreglo esta al revez pero manu lo arregla al pasarlo a simbolo*/
																if (!val_peek(2).sval.equals("_")){
																	if ((Ts.getSimbolo(val_peek(2).sval).getTipo() != colecTipo) && (colecTipo != 'E')){
																		colecTipo = 'E';
																		Al.error((Al.getLinea()+1)+ " Tipos incompatibles en declaracion del arreglo por inferencia");
																		}
																	}
																}
break;
case 27:
//#line 152 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " , faltante entre elementos de coleccion");}
break;
case 28:
//#line 155 "Gramatica2019.y"
{GramLog.println("Se encuentra ElementoColeccion ',' ElementoColeccion reduzco a Lista_Coleccion");
																tam = 2;
																if ((!val_peek(2).sval.equals("_")) && (!val_peek(0).sval.equals("_"))){
																	if (Ts.getSimbolo(val_peek(2).sval).getTipo() == Ts.getSimbolo(val_peek(0).sval).getTipo())
																			colecTipo = Ts.getSimbolo(val_peek(2).sval).getTipo();
																			else{
																			colecTipo = 'E';
																			Al.error((Al.getLinea()+1)+ " Tipos incompatibles en declaracion del arreglo por inferencia");
																			}
																} else {
																if (val_peek(2).sval.equals("_"))	
																	colecTipo = Ts.getSimbolo(val_peek(0).sval).getTipo();
																	else
																	colecTipo = Ts.getSimbolo(val_peek(2).sval).getTipo();
																}
																if (val_peek(0).sval.equals("_"))
																	ListaInferencia.add("?");
																	else
																	ListaInferencia.add(val_peek(0).sval);
																if (val_peek(2).sval.equals("_"))
																	ListaInferencia.add("?");
																	else
																	ListaInferencia.add(val_peek(2).sval);
																}
break;
case 29:
//#line 180 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " , faltante entre elementos de coleccion");}
break;
case 30:
//#line 183 "Gramatica2019.y"
{GramLog.println("Se encuentra _ reduzco a ElementoColeccion");}
break;
case 31:
//#line 185 "Gramatica2019.y"
{GramLog.println("Se encuentra CTE reduzco a ElementoColeccion");}
break;
case 32:
//#line 187 "Gramatica2019.y"
{GramLog.println("Se encuentra '-' y CTE reduzco a ElementoColeccion");
																if (Ts.getSimbolo(val_peek(0).sval).getUso() == 'C')
																	Ts.setNeg(val_peek(0).sval);
																yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 33:
//#line 192 "Gramatica2019.y"
{GramLog.println("Se encuentra ListaID ',' ID reduzco a ListaID");
																ListaIds.add(val_peek(0).sval);}
break;
case 34:
//#line 195 "Gramatica2019.y"
{GramLog.println("Se encuentra ID reduzco a ListaID");
																ListaIds.add(val_peek(0).sval.toString());}
break;
case 35:
//#line 198 "Gramatica2019.y"
{GramLog.println("Se encuentra T_int reduzco a Tipo");}
break;
case 36:
//#line 200 "Gramatica2019.y"
{GramLog.println("Se encuentra T_float reduzco a Tipo");}
break;
case 37:
//#line 202 "Gramatica2019.y"
{GramLog.println("Se encuentra BloqueIF reduzco a EjecutableSimple");
																EstrucLog.println("Sentencia IF en linea " + (Al.getLinea()+1));
																pilaErrorPtoComa.push(Al.getLinea()+1);
																
																/*PREGUNTAR*/
																
																System.out.println("Abuela: "+pilaPolacaHelper.peek());
																System.out.println("Abuela: "+Al.getPosPolaca().toString());
																Al.addPolaca(pilaPolacaHelper.pop(),(Al.getPosPolaca()).toString());}
break;
case 38:
//#line 212 "Gramatica2019.y"
{GramLog.println("Se encuentra BloqueForeach reduzco a EjecutableSimple");
																EstrucLog.println("Sentencia ForEach en linea " + (Al.getLinea()+1));
																pilaErrorPtoComa.push(Al.getLinea()+1);}
break;
case 39:
//#line 216 "Gramatica2019.y"
{GramLog.println("Se encuentra Print '(' CADENA ')' reduzco a EjecutableSimple");
															   	EstrucLog.println("Sentencia Print en linea " + (Al.getLinea()+1));
																pilaErrorPtoComa.push(Al.getLinea()+1);
																Al.addPolaca(val_peek(1).sval);
                                                                Al.addPolaca(val_peek(3).sval);}
break;
case 40:
//#line 222 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ( faltante en el print");
																pilaErrorPtoComa.push(Al.getLinea()+1);}
break;
case 41:
//#line 226 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ) faltante en el print");
																pilaErrorPtoComa.push(Al.getLinea()+1);}
break;
case 42:
//#line 230 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ( y ) faltante en el print");
																pilaErrorPtoComa.push(Al.getLinea()+1);}
break;
case 43:
//#line 234 "Gramatica2019.y"
{GramLog.println("Se encuentra Asignacion reduzco a EjecutableSimple");
															  	EstrucLog.println("Sentencia Asignacion en linea " + (Al.getLinea()+1));
																pilaErrorPtoComa.push(Al.getLinea()+1);}
break;
case 44:
//#line 238 "Gramatica2019.y"
{GramLog.println("Se encuentra ID ASIG Expresion reduzco a Asignacion");
																if (Ts.getSimbolo(val_peek(2).sval).getTipo() == 'D')
																	Al.error((Al.getLinea()+1) + " Variable no definida");
																	else
																	if ((Ts.getSimbolo(val_peek(2).sval).getTipo() != pilaTipo.peek().charValue()) && (pilaTipo.pop().charValue() != 'E'))
																		Al.error((Al.getLinea()+1) + " Tipos incompatibles en la asignacion");
																if (Ts.getSimbolo(val_peek(2).sval).getUso() == 'A')
																	Al.error((Al.getLinea()+1) + " Necesario especificar posicion del arreglo");
																Al.addPolaca(val_peek(2).sval);
                                                                Al.addPolaca(":=");
																}
break;
case 45:
//#line 250 "Gramatica2019.y"
{GramLog.println("Se encuentra ID '[' CTE ']' ASIG Expresion reduzco a Asignacion");
																if (Ts.getSimbolo(val_peek(5).sval).getTipo() == 'D')
																	Al.error((Al.getLinea()+1) + " Variable no definida");
																	else
																	if ((Ts.getSimbolo(val_peek(5).sval).getTipo() != pilaTipo.peek().charValue()) && (pilaTipo.pop().charValue() != 'E'))
																		Al.error((Al.getLinea()+1) + " Tipos incompatibles en la asignacion");
																if (Ts.getSimbolo(val_peek(3).sval).getTipo() != 'I')
																	Al.error((Al.getLinea()+1) + " Indice de tipo no valido");	
																Al.addPolaca(val_peek(5).sval+ "["+ val_peek(3).sval +"]");
																Al.addPolaca(":=");}
break;
case 46:
//#line 261 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " [ faltante en la asignacion de coleccion");
																pilaTipo.pop();}
break;
case 47:
//#line 265 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ] faltante en la asignacion de coleccion");
																pilaTipo.pop();}
break;
case 48:
//#line 269 "Gramatica2019.y"
{GramLog.println("Se encuentra ID '[' ID ']' ASIG Expresion reduzco a Asignacion");
																if (Ts.getSimbolo(val_peek(5).sval).getTipo() == 'D')
																	Al.error((Al.getLinea()+1) + " Variable no definida");
																	else
																	if ((Ts.getSimbolo(val_peek(5).sval).getTipo() != pilaTipo.peek().charValue()) && (pilaTipo.pop().charValue() != 'E'))
																		Al.error((Al.getLinea()+1) + " Tipos incompatibles en la asignacion");
																if (Ts.getSimbolo(val_peek(3).sval).getTipo() == 'D')
																	Al.error((Al.getLinea()+1) + " Variable no definida");
																	else
																	if (Ts.getSimbolo(val_peek(3).sval).getTipo() != 'I')
																		Al.error((Al.getLinea()+1) + " Indice de tipo no valido");	
																Al.addPolaca(val_peek(5).sval+ "["+ val_peek(3).sval +"]");
                                                                Al.addPolaca(":=");}
break;
case 49:
//#line 283 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " [ faltante en la asignacion de coleccion");
																pilaTipo.pop();}
break;
case 50:
//#line 287 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ] faltante en la asignacion de coleccion");
																pilaTipo.pop();}
break;
case 51:
//#line 291 "Gramatica2019.y"
{GramLog.println("Se encuentra FOREACH ID IN ID BEGIN ListaEjecutables END reduzco a BloqueForeach");
																Al.addPolaca("");
																Al.addPolaca("BIF");
																Al.addPolaca(pilaPolacaHelper.pop(),Integer.valueOf(Al.getPosPolaca()).toString());
																Al.addPolaca((Al.getPosPolaca()-2),pilaPolacaHelper.pop().toString());
                                                                }
break;
case 52:
//#line 298 "Gramatica2019.y"
{GramLog.println("Se encuentra FOREACH ID IN ID EjecutableSimple reduzco a BloqueForeach");
																/*pilaErrorPtoComa.pop();*/
																Al.addPolaca("");
																Al.addPolaca("BIF");
                                                                Al.addPolaca(pilaPolacaHelper.pop(),Integer.valueOf(Al.getPosPolaca()).toString());
																Al.addPolaca((Al.getPosPolaca()-2),pilaPolacaHelper.pop().toString());
                                                                }
break;
case 53:
//#line 306 "Gramatica2019.y"
{GramLog.println("Se encuentra ID IN ID reduzco a CondColec");
																if (Ts.getSimbolo(val_peek(2).sval).getTipo() == 'D')
																	Al.error((Al.getLinea()+1) + " Variable no definida");
																if (Ts.getSimbolo(val_peek(0).sval).getTipo() == 'D')
																	Al.error((Al.getLinea()+1) + " Variable no definida");
																if (Ts.getSimbolo(val_peek(0).sval).getUso() == 'A'){
																if (Ts.getSimbolo(val_peek(2).sval).getTipo() == Ts.getSimbolo(val_peek(0).sval).getTipo()){
																	Al.addPolaca(val_peek(2).sval);
																	pilaPolacaHelper.push(Al.getPosPolaca()-1);
																	Al.addPolaca(val_peek(0).sval);
																	Al.addPolaca("IN");																
																	Al.addPolaca("");
																	pilaPolacaHelper.push(Al.getPosPolaca()-1);
																	Al.addPolaca("BFF");
																} else {
																	Al.error(Al.getLinea() + " Tipos incompatibles en el foreach");
																}
																} else {
																	Al.error(Al.getLinea() + " " + val_peek(0).sval + " No ha sido declarado como arreglo");
																}
																pilaTipo.pop();
																pilaTipo.pop();
																}
break;
case 54:
//#line 330 "Gramatica2019.y"
{GramLog.println("Se encuentra IfSinElse END_IF reduzco a BloqueIF");
																Al.polacaIfSaver();
																pilaPolacaHelper.pop();
																pilaPolacaHelper.push(Al.getPosPolaca()-1);
																}
break;
case 55:
//#line 336 "Gramatica2019.y"
{GramLog.println("Se encuentra IfConElse END_IF reduzco a BloqueIF");
													
																}
break;
case 56:
//#line 340 "Gramatica2019.y"
{GramLog.println("Se encuentra IF '(' Comparacion ')' BEGIN ListaEjecutables END reduzco a IfSinElse");
																Al.addPolaca("");
                                                                Al.addPolaca(pilaPolacaHelper.pop(),Integer.valueOf(Al.getPosPolaca()+1).toString());
                                                                pilaPolacaHelper.push(Al.getPosPolaca()-1);
                                                                Al.addPolaca("BI");}
break;
case 57:
//#line 346 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ( faltante en la comparacion");}
break;
case 58:
//#line 349 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ) faltante en la comparacion");}
break;
case 59:
//#line 352 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " Begin faltante en la lista de sentencias");}
break;
case 60:
//#line 355 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " End faltante en la lista de sentencias");}
break;
case 61:
//#line 358 "Gramatica2019.y"
{GramLog.println("Se encuentra IF '(' Comparacion ')' EjecutableSimple reduzco a IfSinElse");
																pilaErrorPtoComa.pop();
																Al.addPolaca("");
                                                                Al.addPolaca(pilaPolacaHelper.pop(),Integer.valueOf(Al.getPosPolaca()+1).toString());
                                                                pilaPolacaHelper.push(Al.getPosPolaca()-1);
                                                                Al.addPolaca("BI");}
break;
case 62:
//#line 365 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ( faltante en la lista de sentencias");
																pilaErrorPtoComa.pop();}
break;
case 63:
//#line 369 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ) faltante en la comparacion");
																pilaErrorPtoComa.pop();}
break;
case 64:
//#line 373 "Gramatica2019.y"
{GramLog.println("Se encuentra IfSinElse ELSE BEGIN ListaEjecutables END reduzco a IfConElse");
																Al.addPolaca(pilaPolacaHelper.pop(),Integer.valueOf(Al.getPosPolaca()+1).toString());}
break;
case 65:
//#line 376 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " Begin faltante en la lista de sentencias");}
break;
case 66:
//#line 379 "Gramatica2019.y"
{/*Error*/
																Al.warning("Linea: " + Al.getLinea() + " End faltante en la lista de sentencias");}
break;
case 67:
//#line 382 "Gramatica2019.y"
{GramLog.println("Se encuentra IfSinElse ELSE EjecutableSimple reduzco a IfConElse");
																pilaErrorPtoComa.pop();}
break;
case 68:
//#line 385 "Gramatica2019.y"
{GramLog.println("Se encuentra Expresion Comparador Expresion reduzco a Comparacion");
																if ((Ts.getSimbolo(val_peek(2).sval).getTipo()) == (Ts.getSimbolo(val_peek(0).sval).getTipo())){
																	Al.addPolaca(val_peek(1).sval);
																	Al.addPolaca("");
																	pilaPolacaHelper.push(Al.getPosPolaca()-1);
																	Al.addPolaca("BF");
																} else {
																	Al.error(Al.getLinea() + " Tipos incompatibles en la comparacion");
																} 
																pilaTipo.pop();
																pilaTipo.pop();
																}
break;
case 69:
//#line 398 "Gramatica2019.y"
{GramLog.println("Se encuentra < reduzco a Comparador");}
break;
case 70:
//#line 400 "Gramatica2019.y"
{GramLog.println("Se encuentra > reduzco a Comparador");}
break;
case 71:
//#line 402 "Gramatica2019.y"
{GramLog.println("Se encuentra MenorIgual reduzco a Comparador");}
break;
case 72:
//#line 404 "Gramatica2019.y"
{GramLog.println("Se encuentra MayorIgual reduzco a Comparador");}
break;
case 73:
//#line 406 "Gramatica2019.y"
{GramLog.println("Se encuentra Igual reduzco a Comparador");}
break;
case 74:
//#line 408 "Gramatica2019.y"
{GramLog.println("Se encuentra Dist reduzco a Comparador");}
break;
case 75:
//#line 411 "Gramatica2019.y"
{GramLog.println("Se encuentra Expresion '+' Termino reduzco a Expresion");
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
break;
case 76:
//#line 426 "Gramatica2019.y"
{GramLog.println("Se encuentra Expresion '–' Termino reduzco a Expresion");
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
break;
case 77:
//#line 441 "Gramatica2019.y"
{GramLog.println("Se encuentra Termino reduzco a Expresion");}
break;
case 78:
//#line 443 "Gramatica2019.y"
{GramLog.println("Se encuentra Termino '*' Factor reduzco a Termino");
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
break;
case 79:
//#line 457 "Gramatica2019.y"
{GramLog.println("Se encuentra Termino '/' Factor reduzco a Termino"); 
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
break;
case 80:
//#line 472 "Gramatica2019.y"
{GramLog.println("Se encuentra Factor reduzco a Termino");}
break;
case 81:
//#line 474 "Gramatica2019.y"
{GramLog.println("Se encuentra ID reduzco a Factor");
																Al.addPolaca(val_peek(0).sval);
																System.out.println("Pila Error" + pilaTipo);
																if (Ts.getSimbolo(val_peek(0).sval).getTipo() == 'D')
																	Al.error((Al.getLinea()+1) + " Variable no definida");
																pilaTipo.push(Ts.getSimbolo(val_peek(0).sval).getTipo());}
break;
case 82:
//#line 481 "Gramatica2019.y"
{GramLog.println("Se encuentra Factor y '-' reduzco a Factor");
																if (Ts.getSimbolo(val_peek(0).sval).getUso() == 'C')
																	Ts.setNeg(val_peek(0).sval);
																yyval.sval = val_peek(1).sval + val_peek(0).sval;
																Al.borraLastPolaca();
																Al.addPolaca(yyval.sval);
																}
break;
case 83:
//#line 489 "Gramatica2019.y"
{GramLog.println("Se encuentra CTE reduzco a Factor");
																Al.addPolaca(val_peek(0).sval);
																pilaTipo.push(Ts.getSimbolo(val_peek(0).sval).getTipo());
																System.out.println("Pila Error" + pilaTipo);}
break;
case 84:
//#line 494 "Gramatica2019.y"
{GramLog.println("Se encuentra ID'[' CTE ']' reduzco a Factor");
																Al.addPolaca(val_peek(3).sval);
																Al.addPolaca(val_peek(1).sval);
																Al.addPolaca("[]");
																System.out.println("Pila Error" + pilaTipo);
																if (Ts.getSimbolo(val_peek(3).sval).getTipo() == 'D')
																	Al.error((Al.getLinea()+1) + " Variable no definida");
																pilaTipo.push(Ts.getSimbolo(val_peek(3).sval).getTipo());}
break;
case 85:
//#line 503 "Gramatica2019.y"
{/*Error*/
																Al.warning("Linea: " + Al.getLinea() + " [ faltante en declaracion de coleccion");}
break;
case 86:
//#line 506 "Gramatica2019.y"
{/*Error*/
																Al.warning("Linea: " + Al.getLinea() + " ] faltante en declaracion de coleccion");}
break;
case 87:
//#line 509 "Gramatica2019.y"
{GramLog.println("Se encuentra ID '[' ID ']' reduzco a Factor");
																Al.addPolaca(val_peek(3).sval);
																Al.addPolaca(val_peek(1).sval);
																Al.addPolaca("[]");
																if (Ts.getSimbolo(val_peek(3).sval).getTipo() == 'D')
																	Al.error((Al.getLinea()+1) + " Variable no definida");
																if (Ts.getSimbolo(val_peek(1).sval).getTipo() == 'D')
																	Al.error((Al.getLinea()+1) + " Variable no definida");
																	else
																	if (Ts.getSimbolo(val_peek(1).sval).getTipo() != 'I')
																		Al.error((Al.getLinea()+1) + " Indice de tipo no valido");	
																pilaTipo.push(Ts.getSimbolo(val_peek(3).sval).getTipo());
																}
break;
case 88:
//#line 523 "Gramatica2019.y"
{GramLog.println("Se encuentra CTE_INT reduzco a CTE_POS");}
break;
case 89:
//#line 525 "Gramatica2019.y"
{GramLog.println("Se encuentra CTE_FLOAT reduzco a CTE_POS");}
break;
//#line 1375 "Parser.java"
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
