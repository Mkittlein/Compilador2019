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
    9,    6,    6,    5,    5,    3,    3,    3,    3,    3,
    3,    3,   13,   13,   13,   13,   13,   13,   13,   12,
   12,   15,   11,   11,   16,   16,   16,   16,   16,   16,
   16,   16,   17,   17,   17,   17,   18,   19,   19,   19,
   19,   19,   14,   14,   14,   20,   20,   20,   21,   21,
   21,   21,   21,   21,    7,    7,   10,   10,
};
final static short yylen[] = {                            2,
    4,    3,    3,    2,    3,    2,    2,    1,    3,    2,
    2,    1,    3,    2,    2,    1,    2,    5,    4,    4,
    4,    5,    4,    4,    4,    3,    2,    3,    2,    1,
    1,    3,    1,    1,    1,    1,    1,    4,    3,    3,
    2,    1,    3,    6,    5,    5,    6,    5,    5,    5,
    3,    3,    2,    2,    7,    6,    6,    6,    6,    6,
    5,    5,    5,    4,    4,    4,    3,    1,    1,    1,
    1,    1,    3,    3,    1,    3,    3,    1,    1,    1,
    4,    3,    3,    4,    2,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,   34,   35,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   36,   37,   42,    0,    0,    0,    0,
    0,    0,    0,    0,   87,   88,    0,   86,    0,    0,
    0,    0,    0,    0,   80,    0,    0,    0,   78,    0,
    0,    0,    6,    0,   10,   15,    0,    0,   53,    0,
   54,    0,    5,   30,    0,    0,    0,    0,    0,    0,
    0,   85,    0,   39,    0,    0,    0,   51,    0,    0,
    0,   68,   69,   70,   71,   72,    0,    0,    0,    0,
    0,    0,    0,    2,   13,    9,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   23,    0,
   27,    0,   31,    0,    0,   38,   52,    0,    0,    0,
   82,    0,    0,    0,    0,    0,    0,    0,    0,   76,
   77,    1,    0,    0,   19,   24,   32,    0,   64,    0,
    0,    0,    0,    0,    0,   26,    0,    0,    0,   50,
   84,   81,    0,    0,    0,    0,   62,    0,   61,   18,
   22,   63,    0,    0,   57,    0,   58,    0,   56,   55,
};
final static short yydgoto[] = {                          8,
    9,   45,   11,   12,   13,   48,   35,  101,   58,   28,
   14,   15,   16,   36,   32,   17,   18,   37,   79,   38,
   39,
};
final static short yysindex[] = {                       -97,
 -226,  -17,    0,    0,  -22, -242,  -40,    0,  -61, -239,
  -49,  -10, -237,    0,    0,    0, -231, -228,  121, -204,
   27,  -31,  -30, -227,    0,    0,  -20,    0,   30, -195,
 -191, -142,   10,  -30,    0,  -35,   37,  -26,    0, -226,
 -177,   25,    0, -226,    0,    0,   49,   47,    0,  -70,
    0,   74,    0,    0,  -81,  -80,   -4,  -42,    0, -171,
  -36,    0, -169,    0,   55, -160, -226,    0,   92,    7,
  -37,    0,    0,    0,    0,    0,  -30,  -30,  -30,  216,
  -30,  -30, -155,    0,    0,    0,  -19,   13,   16, -147,
 -226, -140,  259,  -79, -143,  -30, -132,  -30,    0,  -92,
    0,  -42,    0,  -30,  -30,    0,    0, -134,   41,   42,
    0, -226,  376,   77,  -26,  -26,  -36, -226,   79,    0,
    0,    0,   52,   58,    0,    0,    0, -116,    0, -226,
 -132,  -30,  -36,  -30,  -36,    0,  -42,  -36,  -36,    0,
    0,    0, -105, -226, -104,  325,    0, -101,    0,    0,
    0,    0,  -36,  -36,    0,  -87,    0, -226,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  172,
    6,  229,    0,    0,    0,    0,    0,    0,    0,  174,
    0,    0,    0,    0,    0,    0,    0,    0,  102,    0,
    0,    0,    1,    0,    0,    0,    0,   45,    0,    0,
  175,  365,    0,    9,    0,    0,  252,  268,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  347,    0,
  114,    0,    0,    0,  131,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  177,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -78,    0,    0,    0,  294,    0,    0,    0,
    0,  238,    0,    0,    0,    0,    0,    0,    0,   23,
    0,    0,    0,    0,   67,   87,  220,    0,    0,    0,
    0,    0,  309,  318,    0,    0,    0,  -90,    0, -234,
    0,    0,  143,    0,  155,    0,  281,  167,  179,    0,
    0,    0,    0,    0,    0,  -78,    0,    0,    0,    0,
    0,    0,  191,  203,    0, -217,    0, -238,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  549,  -21,  184,    0,    0,  584,   69,   39,   11,
    0,    0,    0,   54,    0,    0,    0,  160,    0,    2,
  -25,
};
final static int YYTABLESIZE=707;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         34,
   79,  100,   54,  113,   24,   12,   77,   77,   11,   44,
   68,   95,   97,  131,   24,   81,   31,   30,   43,   11,
   82,   47,   83,   11,   72,   24,   73,   24,   93,   60,
   60,   59,   19,   66,   62,    5,   49,   50,    6,   51,
    7,   79,   79,   79,   75,   25,   26,   79,   46,  114,
   59,   59,   54,   53,   24,  120,  121,   59,  119,   79,
   79,   60,   79,   83,   83,   83,   73,   65,  103,   83,
   64,   24,   63,   21,   66,   54,   61,   80,  115,  116,
   84,   83,   83,   85,   83,   75,   74,   75,   99,   57,
   90,  146,  104,   24,  105,  106,  102,   59,  107,  111,
   69,   41,  122,   75,   75,  125,   75,   73,  126,   73,
  103,  127,  103,   43,   67,   89,   19,  129,   24,    5,
  132,   54,    6,  140,    7,   73,   73,   74,   73,   74,
   40,  134,  117,  141,  142,  147,   24,  149,  137,   87,
  102,  152,   49,   54,  150,   74,   74,  103,   74,  133,
  151,  135,  155,  157,   46,  124,  159,  138,  139,    1,
   41,    2,    3,    4,    5,   24,   48,    6,  136,    7,
  160,    8,   43,    7,    4,  102,    3,   65,   45,   12,
   25,   26,   96,   98,   98,  153,   91,  154,   19,   40,
   47,    5,   42,   71,    6,   40,    7,    2,    3,    4,
    5,   49,   44,    6,    0,    7,    0,    0,    0,   19,
    0,   52,    5,   46,    0,    6,    0,    7,   33,  112,
    0,   19,    0,    0,    5,   48,    0,    6,   33,    7,
   25,   26,   25,   26,   74,   75,   76,   45,   78,   78,
   29,   22,   25,   26,    0,    0,   23,    0,    0,   47,
    0,    0,    0,   25,   26,   25,   26,   79,   79,   79,
   67,   44,   79,   12,    0,   79,   11,   79,   79,   79,
   79,   79,   79,   12,   12,   79,   11,   11,    0,   83,
   83,   83,   25,   26,   83,   55,    0,   83,    0,   83,
   83,   83,   83,   83,   83,   33,   29,   83,    0,   25,
   26,   75,   75,   75,    0,    0,   75,    0,    0,   75,
   33,   75,   75,   75,   75,   75,   75,  130,    0,   75,
    0,   25,   26,   73,   73,   73,   17,    0,   73,    0,
   29,   73,   55,   73,   73,   73,   73,   73,   73,   28,
    0,   73,    0,   74,   74,   74,   25,   26,   74,    0,
  109,   74,   21,   74,   74,   74,   74,   74,   74,   41,
   41,   74,    0,   41,   25,   26,   41,   20,   41,   41,
   41,   43,   43,   28,    0,   43,   25,    0,   43,   22,
   43,   43,   43,  158,   23,    0,    0,    0,   40,   40,
   31,    0,   40,   25,   26,   40,    0,   40,   40,   40,
   49,   49,    0,    0,   49,   86,    0,   49,    0,   49,
   49,   49,   46,   46,    0,    0,   46,    0,    0,   46,
    0,   46,   46,   46,   48,   48,    0,    0,   48,    0,
    0,   48,    0,   48,   48,   48,   45,   45,    0,   86,
   45,   31,    0,   45,    0,   45,   45,   45,   47,   47,
    0,    0,   47,    0,    0,   47,    0,   47,   47,   47,
   44,   44,    0,    0,   44,    0,    0,   44,    0,   44,
   44,   44,  118,    0,   19,    0,   67,    5,   67,    0,
    6,   67,    7,    0,   67,   16,   67,   16,   16,   16,
   16,    0,    0,   16,   29,   16,   29,   29,   29,   29,
    0,    0,   29,    0,   29,    0,    0,    0,   33,    0,
   33,   33,   33,   33,    0,    0,   33,   19,   33,    0,
    5,    0,    0,    6,   17,    7,   17,   17,   17,   17,
    0,    0,   17,    0,   17,    0,    0,   28,    0,   28,
   28,   28,   28,    0,    0,   28,    0,   28,   10,   20,
   21,    0,   21,   21,   21,   21,    0,   41,   21,    0,
   21,    0,    0,    0,    0,   20,    0,   20,   20,   20,
   20,    0,    0,   20,   25,   20,   25,   25,   25,   25,
    0,    0,   25,   19,   25,   27,    5,    0,   83,    6,
    0,    7,   86,    0,    0,    0,    0,    0,   92,    0,
    0,    0,   27,   86,   56,   86,   86,   86,   86,    0,
   86,   86,    0,   86,    0,  108,   70,    0,    0,   31,
   31,   14,    0,   14,   14,   14,   14,    0,    0,   14,
   88,   14,  144,    0,   19,   94,    0,    5,    0,  128,
    6,    0,    7,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  110,    0,    0,    0,    0,    0,    0,    0,
  143,  145,    0,    0,    0,    0,  148,    0,    0,    0,
  123,    0,    0,    0,    0,    0,    0,    0,   86,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  156,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   86,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   44,   95,   41,   45,    0,   43,   43,    0,   59,
   32,   93,   93,   93,   45,   42,  259,   40,  258,  258,
   47,  259,    0,  258,   60,   45,   62,   45,   50,  268,
  269,   21,  259,  268,   24,  262,  268,  269,  265,  268,
  267,   41,   42,   43,    0,  273,  274,   47,   59,   71,
  268,  269,   95,  258,   45,   81,   82,   47,   80,   59,
   60,   93,   62,   41,   42,   43,    0,  263,   58,   47,
   41,   45,   93,   91,  266,   95,   23,   41,   77,   78,
  258,   59,   60,   59,   62,   41,    0,   43,   93,   21,
   44,  113,  264,   45,  264,   41,   58,   87,  259,   93,
   91,    0,  258,   59,   60,   93,   62,   41,   93,   43,
  100,  259,  102,    0,  257,   47,  259,  258,   45,  262,
  264,   95,  265,  258,  267,   59,   60,   41,   62,   43,
    0,  264,   79,   93,   93,   59,   45,   59,  100,   91,
  102,  258,    0,   95,   93,   59,   60,  137,   62,   96,
   93,   98,  258,  258,    0,   87,  258,  104,  105,  257,
   59,  259,  260,  261,  262,   45,    0,  265,  100,  267,
  258,    0,   59,    0,    0,  137,    0,  268,    0,  258,
  273,  274,  264,  264,  264,  132,  257,  134,  259,   59,
    0,  262,    9,   34,  265,  257,  267,  259,  260,  261,
  262,   59,    0,  265,   -1,  267,   -1,   -1,   -1,  259,
   -1,   91,  262,   59,   -1,  265,   -1,  267,  259,  257,
   -1,  259,   -1,   -1,  262,   59,   -1,  265,  259,  267,
  273,  274,  273,  274,  270,  271,  272,   59,  275,  275,
  263,  259,  273,  274,   -1,   -1,  264,   -1,   -1,   59,
   -1,   -1,   -1,  273,  274,  273,  274,  257,  258,  259,
   41,   59,  262,  258,   -1,  265,  258,  267,  268,  269,
  270,  271,  272,  268,  269,  275,  268,  269,   -1,  257,
  258,  259,  273,  274,  262,  259,   -1,  265,   -1,  267,
  268,  269,  270,  271,  272,   44,   59,  275,   -1,  273,
  274,  257,  258,  259,   -1,   -1,  262,   -1,   -1,  265,
   59,  267,  268,  269,  270,  271,  272,   59,   -1,  275,
   -1,  273,  274,  257,  258,  259,   59,   -1,  262,   -1,
   93,  265,  259,  267,  268,  269,  270,  271,  272,   59,
   -1,  275,   -1,  257,  258,  259,  273,  274,  262,   -1,
  259,  265,   59,  267,  268,  269,  270,  271,  272,  258,
  259,  275,   -1,  262,  273,  274,  265,   59,  267,  268,
  269,  258,  259,   93,   -1,  262,   59,   -1,  265,  259,
  267,  268,  269,   59,  264,   -1,   -1,   -1,  258,  259,
   44,   -1,  262,  273,  274,  265,   -1,  267,  268,  269,
  258,  259,   -1,   -1,  262,   59,   -1,  265,   -1,  267,
  268,  269,  258,  259,   -1,   -1,  262,   -1,   -1,  265,
   -1,  267,  268,  269,  258,  259,   -1,   -1,  262,   -1,
   -1,  265,   -1,  267,  268,  269,  258,  259,   -1,   93,
  262,   95,   -1,  265,   -1,  267,  268,  269,  258,  259,
   -1,   -1,  262,   -1,   -1,  265,   -1,  267,  268,  269,
  258,  259,   -1,   -1,  262,   -1,   -1,  265,   -1,  267,
  268,  269,  257,   -1,  259,   -1,  257,  262,  259,   -1,
  265,  262,  267,   -1,  265,  257,  267,  259,  260,  261,
  262,   -1,   -1,  265,  257,  267,  259,  260,  261,  262,
   -1,   -1,  265,   -1,  267,   -1,   -1,   -1,  257,   -1,
  259,  260,  261,  262,   -1,   -1,  265,  259,  267,   -1,
  262,   -1,   -1,  265,  257,  267,  259,  260,  261,  262,
   -1,   -1,  265,   -1,  267,   -1,   -1,  257,   -1,  259,
  260,  261,  262,   -1,   -1,  265,   -1,  267,    0,    1,
  257,   -1,  259,  260,  261,  262,   -1,    9,  265,   -1,
  267,   -1,   -1,   -1,   -1,  257,   -1,  259,  260,  261,
  262,   -1,   -1,  265,  257,  267,  259,  260,  261,  262,
   -1,   -1,  265,  259,  267,    2,  262,   -1,   40,  265,
   -1,  267,   44,   -1,   -1,   -1,   -1,   -1,   50,   -1,
   -1,   -1,   19,  257,   21,  259,  260,  261,  262,   -1,
  264,  265,   -1,  267,   -1,   67,   33,   -1,   -1,  273,
  274,  257,   -1,  259,  260,  261,  262,   -1,   -1,  265,
   47,  267,  257,   -1,  259,   52,   -1,  262,   -1,   91,
  265,   -1,  267,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   69,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  112,  113,   -1,   -1,   -1,   -1,  118,   -1,   -1,   -1,
   87,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  130,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  144,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  158,
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

//#line 316 "Gramatica2019.y"
Stack<Integer> pilaErrorPtoComa;
private Stack<Integer> pilaPolacaHelper;
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
				pilaErrorPtoComa  = new Stack<Integer>();
				pilaPolacaHelper  = new Stack<Integer>();
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
//#line 517 "Parser.java"
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
																		Ts.getSimbolo(s).setTipo('I');			
																}} else {
																	if((val_peek(1).sval).equals("float")){
																		for (String s: ListaIds) {
																			Ts.getSimbolo(s).setTipo('F');}	
																								}
																}
																ListaIds.clear();}
break;
case 18:
//#line 69 "Gramatica2019.y"
{GramLog.println("Se encuentra Tipo ID '[' CTE ']' reduzco a DeclarativaSimple");
																Ts.setSize(val_peek(3).sval,Integer.parseInt(val_peek(1).sval));
																if ((val_peek(4).sval).equals("int")){
																	Ts.getSimbolo(val_peek(3).sval).setTipo('I');
																} else {
																	if((val_peek(4).sval).equals("float")){
																		Ts.getSimbolo(val_peek(3).sval).setTipo('F');
																		}
																}
																}
break;
case 19:
//#line 80 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " [ faltante en la declaracion de coleccion");}
break;
case 20:
//#line 83 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ] faltante en la declaracion de coleccion");}
break;
case 21:
//#line 86 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " Falta declaracion de tipo para la coleccion");}
break;
case 22:
//#line 89 "Gramatica2019.y"
{GramLog.println("Se encuentra Tipo ID '[' Lista_Coleccion ']' reduzco a DeclarativaSimple");
																Ts.setSize(val_peek(3).sval,tam);
																if ((val_peek(4).sval).equals("int")){
																	Ts.getSimbolo(val_peek(3).sval).setTipo('I');
																} else {
																	if((val_peek(4).sval).equals("float")){
																		Ts.getSimbolo(val_peek(3).sval).setTipo('F');
																		}
																}
																}
break;
case 23:
//#line 100 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " tipo faltante en la declaracion de coleccion");}
break;
case 24:
//#line 103 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " [ faltante en la declaracion de coleccion");}
break;
case 25:
//#line 106 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ] faltante en la declaracion de coleccion");}
break;
case 26:
//#line 109 "Gramatica2019.y"
{GramLog.println("Se encuentra ElementoColeccion ',' Lista_Coleccion reduzco a Lista_Coleccion");
																tam++;}
break;
case 27:
//#line 112 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " , faltante entre elementos de coleccion");}
break;
case 28:
//#line 115 "Gramatica2019.y"
{GramLog.println("Se encuentra ElementoColeccion ',' ElementoColeccion reduzco a Lista_Coleccion");
																tam = 2;}
break;
case 29:
//#line 118 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " , faltante entre elementos de coleccion");}
break;
case 30:
//#line 121 "Gramatica2019.y"
{GramLog.println("Se encuentra _ reduzco a ElementoColeccion");}
break;
case 31:
//#line 123 "Gramatica2019.y"
{GramLog.println("Se encuentra CTE_POS reduzco a ElementoColeccion");}
break;
case 32:
//#line 125 "Gramatica2019.y"
{GramLog.println("Se encuentra ListaID ',' ID reduzco a ListaID");
																ListaIds.add(val_peek(0).sval);}
break;
case 33:
//#line 128 "Gramatica2019.y"
{GramLog.println("Se encuentra ID reduzco a ListaID");
																ListaIds.add(val_peek(0).sval.toString());}
break;
case 34:
//#line 131 "Gramatica2019.y"
{GramLog.println("Se encuentra T_int reduzco a Tipo");}
break;
case 35:
//#line 133 "Gramatica2019.y"
{GramLog.println("Se encuentra T_float reduzco a Tipo");}
break;
case 36:
//#line 135 "Gramatica2019.y"
{GramLog.println("Se encuentra BloqueIF reduzco a EjecutableSimple");
																EstrucLog.println("Sentencia IF en linea " + (Al.getLinea()+1));
																pilaErrorPtoComa.push(Al.getLinea()+1);
																Al.addPolaca(pilaPolacaHelper.pop(),Integer.valueOf(Al.getPosPolaca()).toString());}
break;
case 37:
//#line 140 "Gramatica2019.y"
{GramLog.println("Se encuentra BloqueForeach reduzco a EjecutableSimple");
																EstrucLog.println("Sentencia ForEach en linea " + (Al.getLinea()+1));
																pilaErrorPtoComa.push(Al.getLinea()+1);}
break;
case 38:
//#line 144 "Gramatica2019.y"
{GramLog.println("Se encuentra Print '(' CADENA ')' reduzco a EjecutableSimple");
															   	EstrucLog.println("Sentencia Print en linea " + (Al.getLinea()+1));
																pilaErrorPtoComa.push(Al.getLinea()+1);
																Al.addPolaca(val_peek(1).sval);
                                                                Al.addPolaca(val_peek(3).sval);}
break;
case 39:
//#line 150 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ( faltante en el print");
																pilaErrorPtoComa.push(Al.getLinea()+1);}
break;
case 40:
//#line 154 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ) faltante en el print");
																pilaErrorPtoComa.push(Al.getLinea()+1);}
break;
case 41:
//#line 158 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ( y ) faltante en el print");
																pilaErrorPtoComa.push(Al.getLinea()+1);}
break;
case 42:
//#line 162 "Gramatica2019.y"
{GramLog.println("Se encuentra Asignacion reduzco a EjecutableSimple");
															  	EstrucLog.println("Sentencia Asignacion en linea " + (Al.getLinea()+1));
																pilaErrorPtoComa.push(Al.getLinea()+1);}
break;
case 43:
//#line 166 "Gramatica2019.y"
{GramLog.println("Se encuentra ID ASIG Expresion reduzco a Asignacion");
																Al.addPolaca(val_peek(2).sval);
                                                                Al.addPolaca(":=");}
break;
case 44:
//#line 170 "Gramatica2019.y"
{GramLog.println("Se encuentra ID '[' CTE ']' ASIG Expresion reduzco a Asignacion");
																Al.addPolaca(val_peek(5).sval + "[" + val_peek(3).sval + "]"); /*POSIBLE ERROR*/
                                                                Al.addPolaca(":=");}
break;
case 45:
//#line 174 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " [ faltante en la asignacion de coleccion");}
break;
case 46:
//#line 177 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ] faltante en la asignacion de coleccion");}
break;
case 47:
//#line 180 "Gramatica2019.y"
{GramLog.println("Se encuentra ID '[' ID ']' ASIG Expresion reduzco a Asignacion");
																Al.addPolaca(val_peek(5).sval + "[" + val_peek(3).sval + "]"); /*POSIBLE ERROR*/
                                                                Al.addPolaca(":=");}
break;
case 48:
//#line 184 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " [ faltante en la asignacion de coleccion");}
break;
case 49:
//#line 187 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ] faltante en la asignacion de coleccion");}
break;
case 50:
//#line 190 "Gramatica2019.y"
{GramLog.println("Se encuentra FOREACH ID IN ID BEGIN ListaEjecutables END reduzco a BloqueForeach");
																Al.addPolaca(pilaPolacaHelper.pop(),Integer.valueOf(Al.getPosPolaca()+2).toString());
                                                                Al.addPolaca(pilaPolacaHelper.pop().toString());
                                                                Al.addPolaca("BI");}
break;
case 51:
//#line 195 "Gramatica2019.y"
{GramLog.println("Se encuentra FOREACH ID IN ID EjecutableSimple reduzco a BloqueForeach");
																pilaErrorPtoComa.pop();
																Al.addPolaca(pilaPolacaHelper.pop(),Integer.valueOf(Al.getPosPolaca()+2).toString());
                                                                Al.addPolaca(pilaPolacaHelper.pop().toString());
                                                                Al.addPolaca("BI");}
break;
case 52:
//#line 201 "Gramatica2019.y"
{GramLog.println("Se encuentra ID IN ID reduzco a CondColec");
																pilaPolacaHelper.push(Al.getPosPolaca());
																}
break;
case 53:
//#line 205 "Gramatica2019.y"
{GramLog.println("Se encuentra IfSinElse END_IF reduzco a BloqueIF");}
break;
case 54:
//#line 207 "Gramatica2019.y"
{GramLog.println("Se encuentra IfConElse END_IF reduzco a BloqueIF");}
break;
case 55:
//#line 209 "Gramatica2019.y"
{GramLog.println("Se encuentra IF '(' Comparacion ')' BEGIN ListaEjecutables END reduzco a IfSinElse");
																Al.addPolaca("");
                                                                Al.addPolaca(pilaPolacaHelper.pop(),Integer.valueOf(Al.getPosPolaca()+1).toString());
                                                                pilaPolacaHelper.push(Al.getPosPolaca()-1);
                                                                Al.addPolaca("BI");}
break;
case 56:
//#line 215 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ( faltante en la comparacion");}
break;
case 57:
//#line 218 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ) faltante en la comparacion");}
break;
case 58:
//#line 221 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " Begin faltante en la lista de sentencias");}
break;
case 59:
//#line 224 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " End faltante en la lista de sentencias");}
break;
case 60:
//#line 227 "Gramatica2019.y"
{GramLog.println("Se encuentra IF '(' Comparacion ')' EjecutableSimple reduzco a IfSinElse");
																pilaErrorPtoComa.pop();
																Al.addPolaca("");
                                                                Al.addPolaca(pilaPolacaHelper.pop(),Integer.valueOf(Al.getPosPolaca()).toString());
                                                                pilaPolacaHelper.push(Al.getPosPolaca()-1);
                                                                Al.addPolaca("BI");}
break;
case 61:
//#line 234 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ( faltante en la lista de sentencias");
																pilaErrorPtoComa.pop();}
break;
case 62:
//#line 238 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ) faltante en la comparacion");
																pilaErrorPtoComa.pop();}
break;
case 63:
//#line 242 "Gramatica2019.y"
{GramLog.println("Se encuentra IfSinElse ELSE BEGIN ListaEjecutables END reduzco a IfConElse");}
break;
case 64:
//#line 244 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " Begin faltante en la lista de sentencias");}
break;
case 65:
//#line 247 "Gramatica2019.y"
{/*Error*/
																Al.warning("Linea: " + Al.getLinea() + " End faltante en la lista de sentencias");}
break;
case 66:
//#line 250 "Gramatica2019.y"
{GramLog.println("Se encuentra IfSinElse ELSE EjecutableSimple reduzco a IfConElse");
																pilaErrorPtoComa.pop();}
break;
case 67:
//#line 253 "Gramatica2019.y"
{GramLog.println("Se encuentra Expresion Comparador Expresion reduzco a Comparacion");
																Al.addPolaca(val_peek(1).sval);
																Al.addPolaca("");
																pilaPolacaHelper.push(Al.getPosPolaca()-1);
                                                                Al.addPolaca("BF");}
break;
case 68:
//#line 259 "Gramatica2019.y"
{GramLog.println("Se encuentra < reduzco a Comparador");}
break;
case 69:
//#line 261 "Gramatica2019.y"
{GramLog.println("Se encuentra > reduzco a Comparador");}
break;
case 70:
//#line 263 "Gramatica2019.y"
{GramLog.println("Se encuentra MenorIgual reduzco a Comparador");}
break;
case 71:
//#line 265 "Gramatica2019.y"
{GramLog.println("Se encuentra MayorIgual reduzco a Comparador");}
break;
case 72:
//#line 267 "Gramatica2019.y"
{GramLog.println("Se encuentra Igual reduzco a Comparador");}
break;
case 73:
//#line 270 "Gramatica2019.y"
{GramLog.println("Se encuentra Expresion '+' Termino reduzco a Expresion");
																Al.addPolaca("+");}
break;
case 74:
//#line 273 "Gramatica2019.y"
{GramLog.println("Se encuentra Expresion '–' Termino reduzco a Expresion");
																Al.addPolaca("-");}
break;
case 75:
//#line 276 "Gramatica2019.y"
{GramLog.println("Se encuentra Termino reduzco a Expresion");}
break;
case 76:
//#line 278 "Gramatica2019.y"
{GramLog.println("Se encuentra Termino '*' Factor reduzco a Termino");
																Al.addPolaca("*");}
break;
case 77:
//#line 281 "Gramatica2019.y"
{GramLog.println("Se encuentra Termino '/' Factor reduzco a Termino"); 
																Al.addPolaca("/");}
break;
case 78:
//#line 284 "Gramatica2019.y"
{GramLog.println("Se encuentra Factor reduzco a Termino");}
break;
case 79:
//#line 286 "Gramatica2019.y"
{GramLog.println("Se encuentra ID reduzco a Factor");
																Al.addPolaca(val_peek(0).sval);}
break;
case 80:
//#line 289 "Gramatica2019.y"
{GramLog.println("Se encuentra CTE reduzco a Factor");}
break;
case 81:
//#line 291 "Gramatica2019.y"
{GramLog.println("Se encuentra ID'[' CTE ']' reduzco a Factor");
																Al.addPolaca(val_peek(3).sval);}
break;
case 82:
//#line 294 "Gramatica2019.y"
{/*Error*/
																Al.warning("Linea: " + Al.getLinea() + " [ faltante en declaracion de coleccion");}
break;
case 83:
//#line 297 "Gramatica2019.y"
{/*Error*/
																Al.warning("Linea: " + Al.getLinea() + " ] faltante en declaracion de coleccion");}
break;
case 84:
//#line 300 "Gramatica2019.y"
{GramLog.println("Se encuentra ID '[' ID ']' reduzco a Factor");
																Al.addPolaca(val_peek(3).sval);}
break;
case 85:
//#line 303 "Gramatica2019.y"
{GramLog.println("Se encuentra CTE_POS y '-' reduzco a CTE");
																Ts.setNeg(val_peek(0).sval);
																Al.addPolaca(val_peek(1).sval);
																}
break;
case 86:
//#line 308 "Gramatica2019.y"
{GramLog.println("Se encuentra CTE_POS reduzco a CTE");
																Al.addPolaca(val_peek(0).sval);}
break;
case 87:
//#line 311 "Gramatica2019.y"
{GramLog.println("Se encuentra CTE_INT reduzco a CTE_POS");}
break;
case 88:
//#line 313 "Gramatica2019.y"
{GramLog.println("Se encuentra CTE_FLOAT reduzco a CTE_POS");}
break;
//#line 1146 "Parser.java"
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
