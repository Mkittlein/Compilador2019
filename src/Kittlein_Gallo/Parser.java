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
    2,    2,    1,    1,    1,    1,    4,    4,    4,    4,
    4,    4,    4,    4,    4,    8,    8,    8,    8,    9,
    9,    9,    6,    6,    5,    5,    3,    3,    3,    3,
    3,    3,    3,   12,   12,   12,   12,   12,   12,   12,
   11,   11,   14,   10,   10,   15,   15,   15,   15,   15,
   15,   15,   15,   16,   16,   16,   16,   17,   18,   18,
   18,   18,   18,   13,   13,   13,   19,   19,   19,   20,
   20,   20,   20,   20,   20,   20,    7,    7,
};
final static short yylen[] = {                            2,
    4,    3,    3,    2,    3,    2,    2,    1,    3,    2,
    2,    1,    3,    2,    2,    1,    2,    5,    4,    4,
    4,    5,    4,    4,    4,    3,    2,    3,    2,    1,
    1,    2,    3,    1,    1,    1,    1,    1,    4,    3,
    3,    2,    1,    3,    6,    5,    5,    6,    5,    5,
    5,    3,    3,    2,    2,    7,    6,    6,    6,    6,
    6,    5,    5,    5,    4,    4,    4,    3,    1,    1,
    1,    1,    1,    3,    3,    1,    3,    3,    1,    1,
    2,    1,    4,    3,    3,    4,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,   35,   36,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   37,   38,   43,    0,    0,    0,    0,
    0,    0,    0,   87,   88,    0,    0,    0,    0,    0,
    0,    0,    0,   82,    0,    0,    0,   79,    0,    0,
    0,    6,    0,   10,   15,    0,    0,   54,    0,   55,
    0,    5,   30,    0,    0,    0,    0,    0,    0,    0,
    0,   40,    0,    0,    0,   52,    0,    0,    0,   81,
   69,   70,   71,   72,   73,    0,    0,    0,    0,    0,
    0,    0,    2,   13,    9,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   32,    0,    0,   23,    0,
   31,   27,    0,    0,    0,   39,   53,    0,    0,    0,
   84,    0,    0,    0,    0,    0,    0,    0,    0,   77,
   78,    1,    0,    0,   19,   24,   33,    0,   65,    0,
    0,    0,    0,    0,    0,   26,    0,    0,    0,   51,
   86,   83,    0,    0,    0,    0,   63,    0,   62,   18,
   22,   64,    0,    0,   58,    0,   59,    0,   57,   56,
};
final static short yydgoto[] = {                          8,
    9,   44,   11,   12,   13,   47,   34,  102,   58,   14,
   15,   16,   35,   30,   17,   18,   36,   78,   37,   38,
};
final static short yysindex[] = {                      -103,
 -123,  -78,    0,    0,  -36, -200,  -40,    0,  -91, -238,
  230,   13, -183,    0,    0,    0, -229, -175,  -75, -161,
  -20,    7,   24,    0,    0,   16,   57, -164, -165,  229,
  -73,   24,   24,    0,  -28,   65,    8,    0, -123, -147,
   58,    0, -123,    0,    0,  -18,   76,    0,  303,    0,
 -222,    0,    0,  -79, -217,  -72,   31,  -42, -139,  -12,
 -129,    0,   96, -121, -123,    0, -140,   47,  216,    0,
    0,    0,    0,    0,    0,   24,   24,   24,  326,   24,
   24, -117,    0,    0,    0,   26,   52,   55, -108, -123,
 -105,  246,  -67, -104,   24,    0, -101,   24,    0,   26,
    0,    0,  -42,   24,   24,    0,    0,  -93,   79,   82,
    0, -123,  338,  118,    8,    8,  -12, -123,  119,    0,
    0,    0,   89,   90,    0,    0,    0,  -71,    0, -123,
 -101,   24,  -12,   24,  -12,    0,  -42,  -12,  -12,    0,
    0,    0,  -70, -123,  -65,  304,    0,  -64,    0,    0,
    0,    0,  -12,  -12,    0,  -54,    0, -123,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  180,
    6,  319,    0,    0,    0,    0,    0,    0,    0,  205,
    0,    0,    0,    0,    0,    0,  102,    0,    0,    0,
    1,    0,    0,    0,    0,    0,   45,    0,    0,  206,
  365,    0,    9,    0,    0,  242,  -49,    0,    0,    0,
    0,    0,    0,    0,    0,  -33,    0,    0,    0,  114,
    0,    0,  131,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  207,    0,    0,    0,    0,  -33,    0,    0,    0,
    0,  -43,    0,    0,    0,    0,  281,    0,    0,    0,
    0,    0,  260,    0,    0,    0,    0,    0,    0,   23,
    0,    0,    0,    0,   67,   87,  220,    0,    0,    0,
    0,    0,  -37,  292,    0,    0,    0,  -59,    0, -230,
    0,    0,  143,    0,  155,    0,  269,  167,  179,    0,
    0,    0,    0,    0,    0,  -43,    0,    0,    0,    0,
    0,    0,  191,  203,    0, -189,    0, -239,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  555,    5,  208,    0,    0,  556,    3,  -22,    0,
    0,    0,   18,    0,    0,    0,  189,    0,   15,   14,
};
final static int YYTABLESIZE=713;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         32,
   80,  100,   55,   28,   33,   12,   31,   31,   11,   17,
   31,   31,   21,   94,   76,   51,   77,   67,   11,   42,
   97,   20,   85,   57,   55,  131,   55,   11,   61,   61,
   76,   71,   77,   72,   66,  103,   54,   67,   48,   49,
   60,   80,   80,   80,   76,   80,   70,   80,   88,   80,
   24,   25,   53,   92,   81,   24,   25,   31,   29,   80,
   80,   31,   80,   85,   85,   85,   74,   85,   33,   85,
   55,   45,   86,  114,   53,   46,   53,  137,   60,   60,
  103,   85,   85,  119,   85,   76,   75,   76,  124,   76,
  115,  116,   50,  120,  121,  117,   52,   62,   63,   59,
   64,   42,  136,   76,   76,   79,   76,   74,   61,   74,
   83,   74,  133,   44,  103,  135,   84,  146,  109,   89,
   53,  138,  139,   99,  104,   74,   74,   75,   74,   75,
   41,   75,   24,   25,  105,   19,  106,  107,    5,  111,
  122,    6,   50,    7,  125,   75,   75,  126,   75,  153,
  127,  154,  129,    1,   47,    2,    3,    4,    5,  132,
   42,    6,  134,    7,  140,   39,   49,    2,    3,    4,
    5,  141,   44,    6,  142,    7,  147,  149,   46,    8,
   22,  150,  151,   22,   95,   23,  152,  155,   23,   41,
   48,   98,  157,  159,   24,   25,   98,   24,   25,   24,
   25,   50,   45,  160,    7,    4,    3,   17,   66,   17,
   17,   17,   17,   47,   12,   17,   41,   17,   31,   20,
   69,   20,   20,   20,   20,   49,   27,   20,    0,   20,
   24,   25,   24,   25,    0,   31,   31,   46,   54,   31,
   31,   73,   74,   75,    0,    0,    0,    0,    0,   48,
    0,    0,   24,   25,   24,   25,  113,   80,   80,   80,
   68,   45,   80,   12,    0,   80,   11,   80,   80,   80,
   80,   80,   80,   12,   12,    0,   11,   11,    0,   85,
   85,   85,   31,    0,   85,   34,    0,   85,   43,   85,
   85,   85,   85,   85,   85,    0,   24,   25,   24,   25,
   34,   76,   76,   76,  130,    0,   76,    0,    0,   76,
    0,   76,   76,   76,   76,   76,   76,    0,   29,    0,
    0,    0,    0,   74,   74,   74,    0,   28,   74,    0,
    0,   74,    0,   74,   74,   74,   74,   74,   74,   21,
    0,    0,    0,   75,   75,   75,    0,    0,   75,    0,
   25,   75,   29,   75,   75,   75,   75,   75,   75,   42,
   42,   28,  158,   42,    0,    0,   42,    0,   42,   42,
   42,   44,   44,    0,    0,   44,    0,    0,   44,    0,
   44,   44,   44,    0,    0,    0,    0,    0,   41,   41,
    0,    0,   41,    0,    0,   41,    0,   41,   41,   41,
   50,   50,    0,    0,   50,    0,    0,   50,    0,   50,
   50,   50,   47,   47,    0,    0,   47,    0,    0,   47,
    0,   47,   47,   47,   49,   49,    0,    0,   49,    0,
    0,   49,    0,   49,   49,   49,   46,   46,    0,    0,
   46,    0,    0,   46,    0,   46,   46,   46,   48,   48,
    0,    0,   48,    0,    0,   48,    0,   48,   48,   48,
   45,   45,    0,    0,   45,    0,    0,   45,    0,   45,
   45,   45,  112,    0,   19,    0,   68,    5,   68,    0,
    6,   68,    7,    0,   68,   65,   68,   19,   19,    0,
    5,    5,    0,    6,    6,    7,    7,    0,   34,    0,
   34,   34,   34,   34,   19,    0,   34,    5,   34,    0,
    6,    0,    7,    0,    0,    0,   29,    0,   29,   29,
   29,   29,    0,    0,   29,   28,   29,   28,   28,   28,
   28,    0,    0,   28,    0,   28,    0,   21,    0,   21,
   21,   21,   21,    0,    0,   21,    0,   21,   25,    0,
   25,   25,   25,   25,   10,   20,   25,   26,   25,   90,
    0,   19,   19,   40,    5,    5,    0,    6,    6,    7,
    7,    0,    0,    0,   26,   16,   56,   16,   16,   16,
   16,    0,  118,   16,   19,   16,   68,    5,    0,    0,
    6,    0,    7,   82,  144,    0,   19,   85,    0,    5,
    0,   87,    6,   91,    7,    0,   93,    0,    0,    0,
   96,    0,    0,  101,    0,    0,    0,    0,    0,  108,
    0,   14,  110,   14,   14,   14,   14,    0,    0,   14,
    0,   14,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  123,    0,    0,  128,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  101,    0,    0,  101,    0,
    0,    0,    0,    0,    0,    0,  143,  145,    0,    0,
    0,    0,  148,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   85,    0,    0,    0,    0,    0,
    0,    0,  101,    0,    0,    0,    0,    0,  156,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   85,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   44,   45,   40,   45,    0,   44,   45,    0,   59,
   44,   45,   91,   93,   43,   91,   45,   91,  258,  258,
   93,   59,    0,   21,   45,   93,   45,  258,  268,  269,
   43,   60,   45,   62,   30,   58,  259,  268,  268,  269,
   23,   41,   42,   43,    0,   45,   33,   47,   46,   42,
  273,  274,   95,   49,   47,  273,  274,   95,  259,   59,
   60,   95,   62,   41,   42,   43,    0,   45,   45,   47,
   45,   59,   91,   69,   95,  259,   95,  100,  268,  269,
  103,   59,   60,   79,   62,   41,    0,   43,   86,   45,
   76,   77,  268,   80,   81,   78,  258,   41,  263,   93,
  266,    0,  100,   59,   60,   41,   62,   41,   93,   43,
  258,   45,   95,    0,  137,   98,   59,  113,  259,   44,
   95,  104,  105,   93,  264,   59,   60,   41,   62,   43,
    0,   45,  273,  274,  264,  259,   41,  259,  262,   93,
  258,  265,    0,  267,   93,   59,   60,   93,   62,  132,
  259,  134,  258,  257,    0,  259,  260,  261,  262,  264,
   59,  265,  264,  267,  258,  257,    0,  259,  260,  261,
  262,   93,   59,  265,   93,  267,   59,   59,    0,    0,
  259,   93,   93,  259,  264,  264,  258,  258,  264,   59,
    0,  264,  258,  258,  273,  274,  264,  273,  274,  273,
  274,   59,    0,  258,    0,    0,    0,  257,  268,  259,
  260,  261,  262,   59,  258,  265,    9,  267,  259,  257,
   32,  259,  260,  261,  262,   59,  263,  265,   -1,  267,
  273,  274,  273,  274,   -1,  273,  274,   59,  259,  273,
  274,  270,  271,  272,   -1,   -1,   -1,   -1,   -1,   59,
   -1,   -1,  273,  274,  273,  274,   41,  257,  258,  259,
   41,   59,  262,  258,   -1,  265,  258,  267,  268,  269,
  270,  271,  272,  268,  269,   -1,  268,  269,   -1,  257,
  258,  259,  259,   -1,  262,   44,   -1,  265,   59,  267,
  268,  269,  270,  271,  272,   -1,  273,  274,  273,  274,
   59,  257,  258,  259,   59,   -1,  262,   -1,   -1,  265,
   -1,  267,  268,  269,  270,  271,  272,   -1,   59,   -1,
   -1,   -1,   -1,  257,  258,  259,   -1,   59,  262,   -1,
   -1,  265,   -1,  267,  268,  269,  270,  271,  272,   59,
   -1,   -1,   -1,  257,  258,  259,   -1,   -1,  262,   -1,
   59,  265,   93,  267,  268,  269,  270,  271,  272,  258,
  259,   93,   59,  262,   -1,   -1,  265,   -1,  267,  268,
  269,  258,  259,   -1,   -1,  262,   -1,   -1,  265,   -1,
  267,  268,  269,   -1,   -1,   -1,   -1,   -1,  258,  259,
   -1,   -1,  262,   -1,   -1,  265,   -1,  267,  268,  269,
  258,  259,   -1,   -1,  262,   -1,   -1,  265,   -1,  267,
  268,  269,  258,  259,   -1,   -1,  262,   -1,   -1,  265,
   -1,  267,  268,  269,  258,  259,   -1,   -1,  262,   -1,
   -1,  265,   -1,  267,  268,  269,  258,  259,   -1,   -1,
  262,   -1,   -1,  265,   -1,  267,  268,  269,  258,  259,
   -1,   -1,  262,   -1,   -1,  265,   -1,  267,  268,  269,
  258,  259,   -1,   -1,  262,   -1,   -1,  265,   -1,  267,
  268,  269,  257,   -1,  259,   -1,  257,  262,  259,   -1,
  265,  262,  267,   -1,  265,  257,  267,  259,  259,   -1,
  262,  262,   -1,  265,  265,  267,  267,   -1,  257,   -1,
  259,  260,  261,  262,  259,   -1,  265,  262,  267,   -1,
  265,   -1,  267,   -1,   -1,   -1,  257,   -1,  259,  260,
  261,  262,   -1,   -1,  265,  257,  267,  259,  260,  261,
  262,   -1,   -1,  265,   -1,  267,   -1,  257,   -1,  259,
  260,  261,  262,   -1,   -1,  265,   -1,  267,  257,   -1,
  259,  260,  261,  262,    0,    1,  265,    2,  267,  257,
   -1,  259,  259,    9,  262,  262,   -1,  265,  265,  267,
  267,   -1,   -1,   -1,   19,  257,   21,  259,  260,  261,
  262,   -1,  257,  265,  259,  267,   31,  262,   -1,   -1,
  265,   -1,  267,   39,  257,   -1,  259,   43,   -1,  262,
   -1,   46,  265,   49,  267,   -1,   51,   -1,   -1,   -1,
   55,   -1,   -1,   58,   -1,   -1,   -1,   -1,   -1,   65,
   -1,  257,   67,  259,  260,  261,  262,   -1,   -1,  265,
   -1,  267,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   86,   -1,   -1,   90,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  100,   -1,   -1,  103,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  112,  113,   -1,   -1,
   -1,   -1,  118,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  130,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  137,   -1,   -1,   -1,   -1,   -1,  144,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  158,
};
}
final static short YYFINAL=8;
final static short YYMAXTOKEN=274;
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
"CTE_INT","CTE_FLOAT",
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

//#line 520 "Gramatica2019.y"
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


// Verificar que en colecciones todos los componenetes sean del mismo tipo

//Variables no declaradas, como es mejor? Cada vez que encuentro una? SI, sad soup

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
//#line 530 "Parser.java"
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
																}
break;
case 2:
//#line 15 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " Begin inicial faltante");}
break;
case 3:
//#line 18 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " End final faltante");}
break;
case 4:
//#line 21 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " Begin y End principal faltantes");}
break;
case 5:
//#line 24 "Gramatica2019.y"
{GramLog.println("Se encuentra BEGIN ListaEjecutables END reduzco a PROGRAMA");}
break;
case 6:
//#line 26 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " Begin inicial faltante");}
break;
case 7:
//#line 29 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " End final faltante");}
break;
case 8:
//#line 32 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " Begin y End principal faltantes");}
break;
case 9:
//#line 35 "Gramatica2019.y"
{GramLog.println("Se encuentra EjecutableSimple ';' ListaEjecutables reduzco a ListaEjecutables");
																pilaErrorPtoComa.pop();}
break;
case 10:
//#line 38 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + pilaErrorPtoComa.pop() +" Una de las setencias ejecutables necesita ;");}
break;
case 11:
//#line 41 "Gramatica2019.y"
{GramLog.println("Se encuentra EjecutableSimple  ';' reduzco a ListaEjecutables");
																pilaErrorPtoComa.pop();}
break;
case 12:
//#line 44 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + pilaErrorPtoComa.pop() +" Una de las setencias ejecutables necesita ;");}
break;
case 13:
//#line 47 "Gramatica2019.y"
{GramLog.println("Se encuentra DeclarativaSimple ';' ListaDeclarativas reduzco a ListaDeclarativas");}
break;
case 14:
//#line 49 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ; faltante al terminar la sentencia declarativa");}
break;
case 15:
//#line 52 "Gramatica2019.y"
{GramLog.println("Se encuentra DeclarativaSimple ';' reduzco a ListaDeclarativas");}
break;
case 16:
//#line 54 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ; faltante al terminar la sentencia declarativa");}
break;
case 17:
//#line 57 "Gramatica2019.y"
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
//#line 75 "Gramatica2019.y"
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
//#line 94 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " [ faltante en la declaracion de coleccion");}
break;
case 20:
//#line 97 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ] faltante en la declaracion de coleccion");}
break;
case 21:
//#line 100 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " Falta declaracion de tipo para la coleccion");}
break;
case 22:
//#line 103 "Gramatica2019.y"
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
//#line 126 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " tipo faltante en la declaracion de coleccion");}
break;
case 24:
//#line 129 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " [ faltante en la declaracion de coleccion");}
break;
case 25:
//#line 132 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ] faltante en la declaracion de coleccion");}
break;
case 26:
//#line 135 "Gramatica2019.y"
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
//#line 150 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " , faltante entre elementos de coleccion");}
break;
case 28:
//#line 153 "Gramatica2019.y"
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
//#line 178 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " , faltante entre elementos de coleccion");}
break;
case 30:
//#line 181 "Gramatica2019.y"
{GramLog.println("Se encuentra _ reduzco a ElementoColeccion");}
break;
case 31:
//#line 183 "Gramatica2019.y"
{GramLog.println("Se encuentra CTE reduzco a ElementoColeccion");}
break;
case 32:
//#line 185 "Gramatica2019.y"
{GramLog.println("Se encuentra '-' y CTE reduzco a ElementoColeccion");
																if (Ts.getSimbolo(val_peek(0).sval).getUso() == 'C')
																	Ts.setNeg(val_peek(0).sval);
																yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 33:
//#line 190 "Gramatica2019.y"
{GramLog.println("Se encuentra ListaID ',' ID reduzco a ListaID");
																ListaIds.add(val_peek(0).sval);}
break;
case 34:
//#line 193 "Gramatica2019.y"
{GramLog.println("Se encuentra ID reduzco a ListaID");
																ListaIds.add(val_peek(0).sval.toString());}
break;
case 35:
//#line 196 "Gramatica2019.y"
{GramLog.println("Se encuentra T_int reduzco a Tipo");}
break;
case 36:
//#line 198 "Gramatica2019.y"
{GramLog.println("Se encuentra T_float reduzco a Tipo");}
break;
case 37:
//#line 200 "Gramatica2019.y"
{GramLog.println("Se encuentra BloqueIF reduzco a EjecutableSimple");
																EstrucLog.println("Sentencia IF en linea " + (Al.getLinea()+1));
																pilaErrorPtoComa.push(Al.getLinea()+1);
																Al.addPolaca(pilaPolacaHelper.pop(),Integer.valueOf(Al.getPosPolaca()).toString());}
break;
case 38:
//#line 205 "Gramatica2019.y"
{GramLog.println("Se encuentra BloqueForeach reduzco a EjecutableSimple");
																EstrucLog.println("Sentencia ForEach en linea " + (Al.getLinea()+1));
																pilaErrorPtoComa.push(Al.getLinea()+1);}
break;
case 39:
//#line 209 "Gramatica2019.y"
{GramLog.println("Se encuentra Print '(' CADENA ')' reduzco a EjecutableSimple");
															   	EstrucLog.println("Sentencia Print en linea " + (Al.getLinea()+1));
																pilaErrorPtoComa.push(Al.getLinea()+1);
																Al.addPolaca(val_peek(1).sval);
                                                                Al.addPolaca(val_peek(3).sval);}
break;
case 40:
//#line 215 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ( faltante en el print");
																pilaErrorPtoComa.push(Al.getLinea()+1);}
break;
case 41:
//#line 219 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ) faltante en el print");
																pilaErrorPtoComa.push(Al.getLinea()+1);}
break;
case 42:
//#line 223 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ( y ) faltante en el print");
																pilaErrorPtoComa.push(Al.getLinea()+1);}
break;
case 43:
//#line 227 "Gramatica2019.y"
{GramLog.println("Se encuentra Asignacion reduzco a EjecutableSimple");
															  	EstrucLog.println("Sentencia Asignacion en linea " + (Al.getLinea()+1));
																pilaErrorPtoComa.push(Al.getLinea()+1);}
break;
case 44:
//#line 231 "Gramatica2019.y"
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
//#line 243 "Gramatica2019.y"
{GramLog.println("Se encuentra ID '[' CTE ']' ASIG Expresion reduzco a Asignacion");
																if (Ts.getSimbolo(val_peek(5).sval).getTipo() == 'D')
																	Al.error((Al.getLinea()+1) + " Variable no definida");
																	else
																	if ((Ts.getSimbolo(val_peek(3).sval).getTipo() != pilaTipo.peek().charValue()) && (pilaTipo.pop().charValue() != 'E'))
																		Al.error((Al.getLinea()+1) + " Tipos incompatibles en la asignacion");
																if (Ts.getSimbolo(val_peek(3).sval).getTipo() != 'I')
																	Al.error((Al.getLinea()+1) + " Indice de tipo no valido");	
																Al.addPolaca(val_peek(5).sval);
																Al.addPolaca(val_peek(3).sval);
																Al.addPolaca("[]");
                                                                Al.addPolaca(":=");}
break;
case 46:
//#line 256 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " [ faltante en la asignacion de coleccion");
																pilaTipo.pop();}
break;
case 47:
//#line 260 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ] faltante en la asignacion de coleccion");
																pilaTipo.pop();}
break;
case 48:
//#line 264 "Gramatica2019.y"
{GramLog.println("Se encuentra ID '[' ID ']' ASIG Expresion reduzco a Asignacion");
																if (Ts.getSimbolo(val_peek(5).sval).getTipo() == 'D')
																	Al.error((Al.getLinea()+1) + " Variable no definida");
																	else
																	if ((Ts.getSimbolo(val_peek(3).sval).getTipo() != pilaTipo.peek().charValue()) && (pilaTipo.pop().charValue() != 'E'))
																		Al.error((Al.getLinea()+1) + " Tipos incompatibles en la asignacion");
																if (Ts.getSimbolo(val_peek(3).sval).getTipo() == 'D')
																	Al.error((Al.getLinea()+1) + " Variable no definida");
																	else
																	if (Ts.getSimbolo(val_peek(3).sval).getTipo() != 'I')
																		Al.error((Al.getLinea()+1) + " Indice de tipo no valido");	
																Al.addPolaca(val_peek(5).sval);
																Al.addPolaca(val_peek(3).sval);
																Al.addPolaca("[]");
                                                                Al.addPolaca(":=");}
break;
case 49:
//#line 280 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " [ faltante en la asignacion de coleccion");
																pilaTipo.pop();}
break;
case 50:
//#line 284 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ] faltante en la asignacion de coleccion");
																pilaTipo.pop();}
break;
case 51:
//#line 288 "Gramatica2019.y"
{GramLog.println("Se encuentra FOREACH ID IN ID BEGIN ListaEjecutables END reduzco a BloqueForeach");
																Al.addPolaca("");
																Al.addPolaca("BI");
																Al.addPolaca(pilaPolacaHelper.pop(),Integer.valueOf(Al.getPosPolaca()).toString());
																Al.addPolaca((Al.getPosPolaca()-2),pilaPolacaHelper.pop().toString());
                                                                }
break;
case 52:
//#line 295 "Gramatica2019.y"
{GramLog.println("Se encuentra FOREACH ID IN ID EjecutableSimple reduzco a BloqueForeach");
																/*pilaErrorPtoComa.pop();*/
																Al.addPolaca("");
																Al.addPolaca("BI");
                                                                Al.addPolaca(pilaPolacaHelper.pop(),Integer.valueOf(Al.getPosPolaca()).toString());
																Al.addPolaca((Al.getPosPolaca()-2),pilaPolacaHelper.pop().toString());
                                                                }
break;
case 53:
//#line 303 "Gramatica2019.y"
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
																	Al.addPolaca("BF");
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
//#line 327 "Gramatica2019.y"
{GramLog.println("Se encuentra IfSinElse END_IF reduzco a BloqueIF");
																Al.polacaIfSaver();
																pilaPolacaHelper.pop();
																pilaPolacaHelper.push(Al.getPosPolaca()-1);
																}
break;
case 55:
//#line 333 "Gramatica2019.y"
{GramLog.println("Se encuentra IfConElse END_IF reduzco a BloqueIF");}
break;
case 56:
//#line 335 "Gramatica2019.y"
{GramLog.println("Se encuentra IF '(' Comparacion ')' BEGIN ListaEjecutables END reduzco a IfSinElse");
																Al.addPolaca("");
                                                                Al.addPolaca(pilaPolacaHelper.pop(),Integer.valueOf(Al.getPosPolaca()+1).toString());
                                                                pilaPolacaHelper.push(Al.getPosPolaca()-1);
                                                                Al.addPolaca("BI");}
break;
case 57:
//#line 341 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ( faltante en la comparacion");}
break;
case 58:
//#line 344 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ) faltante en la comparacion");}
break;
case 59:
//#line 347 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " Begin faltante en la lista de sentencias");}
break;
case 60:
//#line 350 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " End faltante en la lista de sentencias");}
break;
case 61:
//#line 353 "Gramatica2019.y"
{GramLog.println("Se encuentra IF '(' Comparacion ')' EjecutableSimple reduzco a IfSinElse");
																pilaErrorPtoComa.pop();
																Al.addPolaca("");
                                                                Al.addPolaca(pilaPolacaHelper.pop(),Integer.valueOf(Al.getPosPolaca()+1).toString());
                                                                pilaPolacaHelper.push(Al.getPosPolaca()-1);
                                                                Al.addPolaca("BI");}
break;
case 62:
//#line 360 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ( faltante en la lista de sentencias");
																pilaErrorPtoComa.pop();}
break;
case 63:
//#line 364 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ) faltante en la comparacion");
																pilaErrorPtoComa.pop();}
break;
case 64:
//#line 368 "Gramatica2019.y"
{GramLog.println("Se encuentra IfSinElse ELSE BEGIN ListaEjecutables END reduzco a IfConElse");}
break;
case 65:
//#line 370 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " Begin faltante en la lista de sentencias");}
break;
case 66:
//#line 373 "Gramatica2019.y"
{/*Error*/
																Al.warning("Linea: " + Al.getLinea() + " End faltante en la lista de sentencias");}
break;
case 67:
//#line 376 "Gramatica2019.y"
{GramLog.println("Se encuentra IfSinElse ELSE EjecutableSimple reduzco a IfConElse");
																pilaErrorPtoComa.pop();}
break;
case 68:
//#line 379 "Gramatica2019.y"
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
//#line 392 "Gramatica2019.y"
{GramLog.println("Se encuentra < reduzco a Comparador");}
break;
case 70:
//#line 394 "Gramatica2019.y"
{GramLog.println("Se encuentra > reduzco a Comparador");}
break;
case 71:
//#line 396 "Gramatica2019.y"
{GramLog.println("Se encuentra MenorIgual reduzco a Comparador");}
break;
case 72:
//#line 398 "Gramatica2019.y"
{GramLog.println("Se encuentra MayorIgual reduzco a Comparador");}
break;
case 73:
//#line 400 "Gramatica2019.y"
{GramLog.println("Se encuentra Igual reduzco a Comparador");}
break;
case 74:
//#line 403 "Gramatica2019.y"
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
case 75:
//#line 418 "Gramatica2019.y"
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
case 76:
//#line 433 "Gramatica2019.y"
{GramLog.println("Se encuentra Termino reduzco a Expresion");}
break;
case 77:
//#line 435 "Gramatica2019.y"
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
case 78:
//#line 449 "Gramatica2019.y"
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
case 79:
//#line 464 "Gramatica2019.y"
{GramLog.println("Se encuentra Factor reduzco a Termino");}
break;
case 80:
//#line 466 "Gramatica2019.y"
{GramLog.println("Se encuentra ID reduzco a Factor");
																Al.addPolaca(val_peek(0).sval);
																System.out.println("Pila Error" + pilaTipo);
																if (Ts.getSimbolo(val_peek(0).sval).getTipo() == 'D')
																	Al.error((Al.getLinea()+1) + " Variable no definida");
																pilaTipo.push(Ts.getSimbolo(val_peek(0).sval).getTipo());}
break;
case 81:
//#line 473 "Gramatica2019.y"
{GramLog.println("Se encuentra Factor y '-' reduzco a Factor");
																if (Ts.getSimbolo(val_peek(0).sval).getUso() == 'C')
																	Ts.setNeg(val_peek(0).sval);
																yyval.sval = val_peek(1).sval + val_peek(0).sval;
																Al.borraLastPolaca();
																Al.addPolaca(yyval.sval);
																}
break;
case 82:
//#line 481 "Gramatica2019.y"
{GramLog.println("Se encuentra CTE reduzco a Factor");
																Al.addPolaca(val_peek(0).sval);
																pilaTipo.push(Ts.getSimbolo(val_peek(0).sval).getTipo());
																System.out.println("Pila Error" + pilaTipo);}
break;
case 83:
//#line 486 "Gramatica2019.y"
{GramLog.println("Se encuentra ID'[' CTE ']' reduzco a Factor");
																Al.addPolaca(val_peek(3).sval);
																Al.addPolaca(val_peek(1).sval);
																Al.addPolaca("[]");
																System.out.println("Pila Error" + pilaTipo);
																if (Ts.getSimbolo(val_peek(3).sval).getTipo() == 'D')
																	Al.error((Al.getLinea()+1) + " Variable no definida");
																pilaTipo.push(Ts.getSimbolo(val_peek(3).sval).getTipo());}
break;
case 84:
//#line 495 "Gramatica2019.y"
{/*Error*/
																Al.warning("Linea: " + Al.getLinea() + " [ faltante en declaracion de coleccion");}
break;
case 85:
//#line 498 "Gramatica2019.y"
{/*Error*/
																Al.warning("Linea: " + Al.getLinea() + " ] faltante en declaracion de coleccion");}
break;
case 86:
//#line 501 "Gramatica2019.y"
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
case 87:
//#line 515 "Gramatica2019.y"
{GramLog.println("Se encuentra CTE_INT reduzco a CTE_POS");}
break;
case 88:
//#line 517 "Gramatica2019.y"
{GramLog.println("Se encuentra CTE_FLOAT reduzco a CTE_POS");}
break;
//#line 1363 "Parser.java"
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
