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
    3,    3,   12,   12,   12,   12,   12,   12,   12,   11,
   11,   14,   10,   10,   15,   15,   15,   15,   15,   15,
   15,   15,   16,   16,   16,   16,   17,   18,   18,   18,
   18,   18,   13,   13,   13,   19,   19,   19,   20,   20,
   20,   20,   20,   20,    7,    7,   21,   21,
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
   85,    0,   39,    0,    0,    0,   51,    0,    0,    0,
   68,   69,   70,   71,   72,    0,    0,    0,    0,    0,
    0,    0,    2,   13,    9,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   23,    0,   31,
   27,    0,    0,    0,   38,   52,    0,    0,    0,   82,
    0,    0,    0,    0,    0,    0,    0,    0,   76,   77,
    1,    0,    0,   19,   24,   32,    0,   64,    0,    0,
    0,    0,    0,    0,   26,    0,    0,    0,   50,   84,
   81,    0,    0,    0,    0,   62,    0,   61,   18,   22,
   63,    0,    0,   57,    0,   58,    0,   56,   55,
};
final static short yydgoto[] = {                          8,
    9,   45,   11,   12,   13,   48,   35,  101,   58,   14,
   15,   16,   36,   32,   17,   18,   37,   78,   38,   39,
   28,
};
final static short yysindex[] = {                      -226,
  -97,  121,    0,    0,  -18, -245,  -40,    0, -144, -238,
  -49,  -13, -227,    0,    0,    0, -231, -219,  133, -200,
  -17,  -31,  -30, -222,    0,    0,  -24,    0,   30, -188,
 -182, -117,   10,  -30,    0,  -35,   48,  -26,    0,  -97,
 -168,   34,    0,  -97,    0,    0,  -19,   52,    0,  216,
    0,   49,    0,    0,  -82,  -81,    7,  -42, -165,  -36,
    0, -161,    0,   68, -148,  -97,    0,   92,   29,  -37,
    0,    0,    0,    0,    0,  -30,  -30,  -30,  231,  -30,
  -30, -139,    0,    0,    0,  -21,   31,   40, -124,  -97,
 -138,  227,  -80, -128,  -30, -126,  -30,    0,  -21,    0,
    0,  -42,  -30,  -30,    0,    0, -114,   59,   61,    0,
  -97,  362,   82,  -26,  -26,  -36,  -97,   97,    0,    0,
    0,   64,   65,    0,    0,    0,  -95,    0,  -97, -126,
  -30,  -36,  -30,  -36,    0,  -42,  -36,  -36,    0,    0,
    0,  -94,  -97,  -89,  262,    0,  -87,    0,    0,    0,
    0,  -36,  -36,    0,  -86,    0,  -97,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  174,
    6,  -61,    0,    0,    0,    0,    0,    0,    0,  175,
    0,    0,    0,    0,    0,    0,    0,    0,  102,    0,
    0,    0,    1,    0,    0,    0,    0,   45,    0,    0,
  176,  353,    0,    9,    0,    0,  274,  240,    0,    0,
    0,    0,    0,    0,    0,  -27,    0,    0,    0,  114,
    0,    0,    0,  131,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  177,    0,    0,    0,    0,  -27,    0,    0,    0,
    0,  -78,    0,    0,    0,  304,    0,    0,    0,    0,
    0,  291,    0,    0,    0,    0,    0,    0,   23,    0,
    0,    0,    0,   67,   87,  220,    0,    0,    0,    0,
    0,  252,  327,    0,    0,    0,  -83,    0, -218,    0,
    0,  143,    0,  155,    0,  316,  167,  179,    0,    0,
    0,    0,    0,    0,  -78,    0,    0,    0,    0,    0,
    0,  191,  203,    0, -212,    0, -239,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  559,   27,  172,    0,    0,  583,   26,   -4,    0,
    0,    0,   56,    0,    0,    0,  152,    0,    4,   11,
  164,
};
final static int YYTABLESIZE=719;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         34,
   79,   99,   24,  112,   24,   12,   76,   76,   11,   44,
   94,   96,  130,   31,   24,   80,   31,   31,   11,   43,
   81,   30,   83,   24,   71,   24,   72,   24,   60,   60,
    1,   47,    2,    3,    4,    5,   49,   50,    6,   11,
    7,   79,   79,   79,   75,   46,   57,   79,   51,   66,
   25,   26,   54,  102,   24,   59,   59,   53,   67,   79,
   79,   59,   79,   83,   83,   83,   73,   31,   62,   83,
   63,   86,   88,   54,   64,   54,   92,   54,   60,  114,
  115,   83,   83,   65,   83,   75,   74,   75,   79,   83,
  119,  120,   84,   24,  136,   89,  113,  102,  103,   98,
   68,   41,  104,   75,   75,  118,   75,   73,  105,   73,
  106,  123,   40,   43,    2,    3,    4,    5,  121,  128,
    6,  110,    7,  124,  135,   73,   73,   74,   73,   74,
   40,  102,  125,  116,  126,  131,   24,  133,  145,   66,
  146,   19,   49,  139,    5,   74,   74,    6,   74,    7,
  132,  140,  134,  141,   46,  148,  149,  150,  137,  138,
   41,   19,  151,  154,    5,   24,   48,    6,  156,    7,
  158,  159,   43,    8,    7,    4,    3,   24,   45,   12,
   42,   95,   97,   97,   65,   70,  152,   61,  153,   40,
   47,    0,    0,    0,    0,   16,    0,   16,   16,   16,
   16,   49,   44,   16,    0,   16,    0,    0,    0,   19,
    0,   21,    5,   46,    0,    6,    0,    7,   33,  111,
    0,   19,    0,   52,    5,   48,    0,    6,   33,    7,
   25,   26,   25,   26,   73,   74,   75,   45,   77,   77,
    0,   55,   25,   26,   29,   31,   31,    0,    0,   47,
    0,   25,   26,   25,   26,   25,   26,   79,   79,   79,
   67,   44,   79,   12,    0,   79,   11,   79,   79,   79,
   79,   79,   79,   12,   12,   79,   11,   11,    0,   83,
   83,   83,   25,   26,   83,  129,    0,   83,    0,   83,
   83,   83,   83,   83,   83,   31,   31,   83,   17,    0,
    0,   75,   75,   75,    0,    0,   75,   55,    0,   75,
   20,   75,   75,   75,   75,   75,   75,   33,    0,   75,
  157,   25,   26,   73,   73,   73,    0,    0,   73,    0,
    0,   73,   33,   73,   73,   73,   73,   73,   73,    0,
    0,   73,    0,   74,   74,   74,   31,    0,   74,   29,
  108,   74,    0,   74,   74,   74,   74,   74,   74,   41,
   41,   74,   21,   41,   25,   26,   41,    0,   41,   41,
   41,   43,   43,    0,   28,   43,    0,    0,   43,   22,
   43,   43,   43,   29,   23,   25,    0,    0,   40,   40,
    0,   22,   40,   25,   26,   40,   23,   40,   40,   40,
   49,   49,    0,    0,   49,   25,   26,   49,   28,   49,
   49,   49,   46,   46,    0,    0,   46,    0,    0,   46,
    0,   46,   46,   46,   48,   48,    0,    0,   48,    0,
    0,   48,    0,   48,   48,   48,   45,   45,    0,    0,
   45,    0,    0,   45,    0,   45,   45,   45,   47,   47,
    0,    0,   47,    0,    0,   47,    0,   47,   47,   47,
   44,   44,    0,    0,   44,    0,    0,   44,    0,   44,
   44,   44,   90,    0,   19,    0,   67,    5,   67,    0,
    6,   67,    7,    0,   67,   19,   67,  117,    5,   19,
    0,    6,    5,    7,    0,    6,   17,    7,   17,   17,
   17,   17,    0,    0,   17,    0,   17,    0,   20,    0,
   20,   20,   20,   20,    0,    0,   20,    0,   20,    0,
   19,    0,    0,    5,   31,   31,    6,    0,    7,    0,
   33,    0,   33,   33,   33,   33,    0,    0,   33,    0,
   33,    0,    0,    0,    0,    0,    0,   29,    0,   29,
   29,   29,   29,    0,    0,   29,    0,   29,   10,   20,
   21,    0,   21,   21,   21,   21,    0,   41,   21,    0,
   21,    0,   28,    0,   28,   28,   28,   28,    0,    0,
   28,    0,   28,   25,   27,   25,   25,   25,   25,    0,
    0,   25,    0,   25,    0,    0,    0,    0,   82,    0,
    0,   27,   85,   56,    0,    0,    0,    0,   91,   14,
    0,   14,   14,   14,   14,   69,    0,   14,  143,   14,
   19,    0,    0,    5,  107,    0,    6,    0,    7,   87,
    0,    0,    0,    0,   93,    0,    0,    0,    0,    0,
  100,    0,    0,    0,    0,    0,    0,    0,  127,    0,
  109,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  122,  142,
  144,    0,    0,    0,    0,  147,    0,    0,    0,    0,
    0,  100,    0,    0,  100,    0,    0,   85,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  155,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   85,    0,    0,  100,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   44,   45,   41,   45,    0,   43,   43,    0,   59,
   93,   93,   93,  259,   45,   42,   44,   45,  258,  258,
   47,   40,    0,   45,   60,   45,   62,   45,  268,  269,
  257,  259,  259,  260,  261,  262,  268,  269,  265,  258,
  267,   41,   42,   43,    0,   59,   21,   47,  268,  268,
  273,  274,   95,   58,   45,  268,  269,  258,   32,   59,
   60,   93,   62,   41,   42,   43,    0,   95,   93,   47,
   41,   91,   47,   95,  263,   95,   50,   95,   23,   76,
   77,   59,   60,  266,   62,   41,    0,   43,   41,  258,
   80,   81,   59,   45,   99,   44,   70,  102,  264,   93,
   91,    0,  264,   59,   60,   79,   62,   41,   41,   43,
  259,   86,  257,    0,  259,  260,  261,  262,  258,  258,
  265,   93,  267,   93,   99,   59,   60,   41,   62,   43,
    0,  136,   93,   78,  259,  264,   45,  264,  112,  257,
   59,  259,    0,  258,  262,   59,   60,  265,   62,  267,
   95,   93,   97,   93,    0,   59,   93,   93,  103,  104,
   59,  259,  258,  258,  262,   45,    0,  265,  258,  267,
  258,  258,   59,    0,    0,    0,    0,   45,    0,  258,
    9,  264,  264,  264,  268,   34,  131,   24,  133,   59,
    0,   -1,   -1,   -1,   -1,  257,   -1,  259,  260,  261,
  262,   59,    0,  265,   -1,  267,   -1,   -1,   -1,  259,
   -1,   91,  262,   59,   -1,  265,   -1,  267,  259,  257,
   -1,  259,   -1,   91,  262,   59,   -1,  265,  259,  267,
  273,  274,  273,  274,  270,  271,  272,   59,  275,  275,
   -1,  259,  273,  274,  263,  273,  274,   -1,   -1,   59,
   -1,  273,  274,  273,  274,  273,  274,  257,  258,  259,
   41,   59,  262,  258,   -1,  265,  258,  267,  268,  269,
  270,  271,  272,  268,  269,  275,  268,  269,   -1,  257,
  258,  259,  273,  274,  262,   59,   -1,  265,   -1,  267,
  268,  269,  270,  271,  272,   44,   45,  275,   59,   -1,
   -1,  257,  258,  259,   -1,   -1,  262,  259,   -1,  265,
   59,  267,  268,  269,  270,  271,  272,   44,   -1,  275,
   59,  273,  274,  257,  258,  259,   -1,   -1,  262,   -1,
   -1,  265,   59,  267,  268,  269,  270,  271,  272,   -1,
   -1,  275,   -1,  257,  258,  259,   95,   -1,  262,   59,
  259,  265,   -1,  267,  268,  269,  270,  271,  272,  258,
  259,  275,   59,  262,  273,  274,  265,   -1,  267,  268,
  269,  258,  259,   -1,   59,  262,   -1,   -1,  265,  259,
  267,  268,  269,   93,  264,   59,   -1,   -1,  258,  259,
   -1,  259,  262,  273,  274,  265,  264,  267,  268,  269,
  258,  259,   -1,   -1,  262,  273,  274,  265,   93,  267,
  268,  269,  258,  259,   -1,   -1,  262,   -1,   -1,  265,
   -1,  267,  268,  269,  258,  259,   -1,   -1,  262,   -1,
   -1,  265,   -1,  267,  268,  269,  258,  259,   -1,   -1,
  262,   -1,   -1,  265,   -1,  267,  268,  269,  258,  259,
   -1,   -1,  262,   -1,   -1,  265,   -1,  267,  268,  269,
  258,  259,   -1,   -1,  262,   -1,   -1,  265,   -1,  267,
  268,  269,  257,   -1,  259,   -1,  257,  262,  259,   -1,
  265,  262,  267,   -1,  265,  259,  267,  257,  262,  259,
   -1,  265,  262,  267,   -1,  265,  257,  267,  259,  260,
  261,  262,   -1,   -1,  265,   -1,  267,   -1,  257,   -1,
  259,  260,  261,  262,   -1,   -1,  265,   -1,  267,   -1,
  259,   -1,   -1,  262,  273,  274,  265,   -1,  267,   -1,
  257,   -1,  259,  260,  261,  262,   -1,   -1,  265,   -1,
  267,   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,  259,
  260,  261,  262,   -1,   -1,  265,   -1,  267,    0,    1,
  257,   -1,  259,  260,  261,  262,   -1,    9,  265,   -1,
  267,   -1,  257,   -1,  259,  260,  261,  262,   -1,   -1,
  265,   -1,  267,  257,    2,  259,  260,  261,  262,   -1,
   -1,  265,   -1,  267,   -1,   -1,   -1,   -1,   40,   -1,
   -1,   19,   44,   21,   -1,   -1,   -1,   -1,   50,  257,
   -1,  259,  260,  261,  262,   33,   -1,  265,  257,  267,
  259,   -1,   -1,  262,   66,   -1,  265,   -1,  267,   47,
   -1,   -1,   -1,   -1,   52,   -1,   -1,   -1,   -1,   -1,
   58,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   90,   -1,
   68,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   86,  111,
  112,   -1,   -1,   -1,   -1,  117,   -1,   -1,   -1,   -1,
   -1,   99,   -1,   -1,  102,   -1,   -1,  129,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  143,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  157,   -1,   -1,  136,
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
"ElementoColeccion : CTE",
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

//#line 497 "Gramatica2019.y"
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
																			Ts.getSimbolo(val_peek(3).sval).setTipo('I');
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
																ListaInferencia.add(val_peek(2).sval);
																/*El arreglo esta al revez*/
																if (!val_peek(2).sval.equals("_")){
																	if ((Ts.getSimbolo(val_peek(2).sval).getTipo() != colecTipo) && (colecTipo != 'E')){
																		colecTipo = 'E';
																		Al.error((Al.getLinea()+1)+ " Tipos incompatibles en declaracion del arreglo por inferencia");
																		}
																	}
																}
break;
case 27:
//#line 147 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " , faltante entre elementos de coleccion");}
break;
case 28:
//#line 150 "Gramatica2019.y"
{GramLog.println("Se encuentra ElementoColeccion ',' ElementoColeccion reduzco a Lista_Coleccion");
																tam = 2;
																if (!val_peek(2).sval.equals("_")){
																	if (Ts.getSimbolo(val_peek(2).sval).getTipo() == Ts.getSimbolo(val_peek(0).sval).getTipo())
																			colecTipo = Ts.getSimbolo(val_peek(2).sval).getTipo();
																			else{
																			colecTipo = 'E';
																			Al.error((Al.getLinea()+1)+ " Tipos incompatibles en declaracion del arreglo por inferencia");
																			}
																		}
																ListaInferencia.add(val_peek(0).sval);
																ListaInferencia.add(val_peek(2).sval);}
break;
case 29:
//#line 163 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " , faltante entre elementos de coleccion");}
break;
case 30:
//#line 166 "Gramatica2019.y"
{GramLog.println("Se encuentra _ reduzco a ElementoColeccion");}
break;
case 31:
//#line 168 "Gramatica2019.y"
{GramLog.println("Se encuentra CTE reduzco a ElementoColeccion");}
break;
case 32:
//#line 170 "Gramatica2019.y"
{GramLog.println("Se encuentra ListaID ',' ID reduzco a ListaID");
																ListaIds.add(val_peek(0).sval);}
break;
case 33:
//#line 173 "Gramatica2019.y"
{GramLog.println("Se encuentra ID reduzco a ListaID");
																ListaIds.add(val_peek(0).sval.toString());}
break;
case 34:
//#line 176 "Gramatica2019.y"
{GramLog.println("Se encuentra T_int reduzco a Tipo");}
break;
case 35:
//#line 178 "Gramatica2019.y"
{GramLog.println("Se encuentra T_float reduzco a Tipo");}
break;
case 36:
//#line 180 "Gramatica2019.y"
{GramLog.println("Se encuentra BloqueIF reduzco a EjecutableSimple");
																EstrucLog.println("Sentencia IF en linea " + (Al.getLinea()+1));
																pilaErrorPtoComa.push(Al.getLinea()+1);
																Al.addPolaca(pilaPolacaHelper.pop(),Integer.valueOf(Al.getPosPolaca()).toString());}
break;
case 37:
//#line 185 "Gramatica2019.y"
{GramLog.println("Se encuentra BloqueForeach reduzco a EjecutableSimple");
																EstrucLog.println("Sentencia ForEach en linea " + (Al.getLinea()+1));
																pilaErrorPtoComa.push(Al.getLinea()+1);}
break;
case 38:
//#line 189 "Gramatica2019.y"
{GramLog.println("Se encuentra Print '(' CADENA ')' reduzco a EjecutableSimple");
															   	EstrucLog.println("Sentencia Print en linea " + (Al.getLinea()+1));
																pilaErrorPtoComa.push(Al.getLinea()+1);
																Al.addPolaca(val_peek(1).sval);
                                                                Al.addPolaca(val_peek(3).sval);}
break;
case 39:
//#line 195 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ( faltante en el print");
																pilaErrorPtoComa.push(Al.getLinea()+1);}
break;
case 40:
//#line 199 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ) faltante en el print");
																pilaErrorPtoComa.push(Al.getLinea()+1);}
break;
case 41:
//#line 203 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ( y ) faltante en el print");
																pilaErrorPtoComa.push(Al.getLinea()+1);}
break;
case 42:
//#line 207 "Gramatica2019.y"
{GramLog.println("Se encuentra Asignacion reduzco a EjecutableSimple");
															  	EstrucLog.println("Sentencia Asignacion en linea " + (Al.getLinea()+1));
																pilaErrorPtoComa.push(Al.getLinea()+1);}
break;
case 43:
//#line 211 "Gramatica2019.y"
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
case 44:
//#line 224 "Gramatica2019.y"
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
																Al.addPolaca("ACESS");
                                                                Al.addPolaca(":=");}
break;
case 45:
//#line 237 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " [ faltante en la asignacion de coleccion");
																pilaTipo.pop();}
break;
case 46:
//#line 241 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ] faltante en la asignacion de coleccion");
																pilaTipo.pop();}
break;
case 47:
//#line 245 "Gramatica2019.y"
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
																Al.addPolaca("ACESS");
                                                                Al.addPolaca(":=");}
break;
case 48:
//#line 261 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " [ faltante en la asignacion de coleccion");
																pilaTipo.pop();}
break;
case 49:
//#line 265 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ] faltante en la asignacion de coleccion");
																pilaTipo.pop();}
break;
case 50:
//#line 269 "Gramatica2019.y"
{GramLog.println("Se encuentra FOREACH ID IN ID BEGIN ListaEjecutables END reduzco a BloqueForeach");
																Al.addPolaca("");
																Al.addPolaca("BI");
																Al.addPolaca(pilaPolacaHelper.pop(),Integer.valueOf(Al.getPosPolaca()).toString());
																Al.addPolaca((Al.getPosPolaca()-2),pilaPolacaHelper.pop().toString());
                                                                }
break;
case 51:
//#line 276 "Gramatica2019.y"
{GramLog.println("Se encuentra FOREACH ID IN ID EjecutableSimple reduzco a BloqueForeach");
																/*pilaErrorPtoComa.pop();*/
																Al.addPolaca("");
																Al.addPolaca("BI");
                                                                Al.addPolaca(pilaPolacaHelper.pop(),Integer.valueOf(Al.getPosPolaca()).toString());
																Al.addPolaca((Al.getPosPolaca()-2),pilaPolacaHelper.pop().toString());
                                                                }
break;
case 52:
//#line 284 "Gramatica2019.y"
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
case 53:
//#line 308 "Gramatica2019.y"
{GramLog.println("Se encuentra IfSinElse END_IF reduzco a BloqueIF");}
break;
case 54:
//#line 310 "Gramatica2019.y"
{GramLog.println("Se encuentra IfConElse END_IF reduzco a BloqueIF");}
break;
case 55:
//#line 312 "Gramatica2019.y"
{GramLog.println("Se encuentra IF '(' Comparacion ')' BEGIN ListaEjecutables END reduzco a IfSinElse");
																Al.addPolaca("");
                                                                Al.addPolaca(pilaPolacaHelper.pop(),Integer.valueOf(Al.getPosPolaca()+1).toString());
                                                                pilaPolacaHelper.push(Al.getPosPolaca()-1);
                                                                Al.addPolaca("BI");}
break;
case 56:
//#line 318 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ( faltante en la comparacion");}
break;
case 57:
//#line 321 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ) faltante en la comparacion");}
break;
case 58:
//#line 324 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " Begin faltante en la lista de sentencias");}
break;
case 59:
//#line 327 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " End faltante en la lista de sentencias");}
break;
case 60:
//#line 330 "Gramatica2019.y"
{GramLog.println("Se encuentra IF '(' Comparacion ')' EjecutableSimple reduzco a IfSinElse");
																pilaErrorPtoComa.pop();
																Al.addPolaca("");
                                                                Al.addPolaca(pilaPolacaHelper.pop(),Integer.valueOf(Al.getPosPolaca()+1).toString());
                                                                pilaPolacaHelper.push(Al.getPosPolaca()-1);
                                                                Al.addPolaca("BI");}
break;
case 61:
//#line 337 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ( faltante en la lista de sentencias");
																pilaErrorPtoComa.pop();}
break;
case 62:
//#line 341 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " ) faltante en la comparacion");
																pilaErrorPtoComa.pop();}
break;
case 63:
//#line 345 "Gramatica2019.y"
{GramLog.println("Se encuentra IfSinElse ELSE BEGIN ListaEjecutables END reduzco a IfConElse");}
break;
case 64:
//#line 347 "Gramatica2019.y"
{/*Error */
																Al.warning("Linea: " + Al.getLinea() + " Begin faltante en la lista de sentencias");}
break;
case 65:
//#line 350 "Gramatica2019.y"
{/*Error*/
																Al.warning("Linea: " + Al.getLinea() + " End faltante en la lista de sentencias");}
break;
case 66:
//#line 353 "Gramatica2019.y"
{GramLog.println("Se encuentra IfSinElse ELSE EjecutableSimple reduzco a IfConElse");
																pilaErrorPtoComa.pop();}
break;
case 67:
//#line 356 "Gramatica2019.y"
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
case 68:
//#line 369 "Gramatica2019.y"
{GramLog.println("Se encuentra < reduzco a Comparador");}
break;
case 69:
//#line 371 "Gramatica2019.y"
{GramLog.println("Se encuentra > reduzco a Comparador");}
break;
case 70:
//#line 373 "Gramatica2019.y"
{GramLog.println("Se encuentra MenorIgual reduzco a Comparador");}
break;
case 71:
//#line 375 "Gramatica2019.y"
{GramLog.println("Se encuentra MayorIgual reduzco a Comparador");}
break;
case 72:
//#line 377 "Gramatica2019.y"
{GramLog.println("Se encuentra Igual reduzco a Comparador");}
break;
case 73:
//#line 380 "Gramatica2019.y"
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
case 74:
//#line 395 "Gramatica2019.y"
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
case 75:
//#line 410 "Gramatica2019.y"
{GramLog.println("Se encuentra Termino reduzco a Expresion");}
break;
case 76:
//#line 412 "Gramatica2019.y"
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
case 77:
//#line 426 "Gramatica2019.y"
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
case 78:
//#line 441 "Gramatica2019.y"
{GramLog.println("Se encuentra Factor reduzco a Termino");}
break;
case 79:
//#line 443 "Gramatica2019.y"
{GramLog.println("Se encuentra ID reduzco a Factor");
																Al.addPolaca(val_peek(0).sval);
																System.out.println("Pila Error" + pilaTipo);
																if (Ts.getSimbolo(val_peek(0).sval).getTipo() == 'D')
																	Al.error((Al.getLinea()+1) + " Variable no definida");
																pilaTipo.push(Ts.getSimbolo(val_peek(0).sval).getTipo());}
break;
case 80:
//#line 450 "Gramatica2019.y"
{GramLog.println("Se encuentra CTE reduzco a Factor");
																Al.addPolaca(val_peek(0).sval);
																pilaTipo.push(Ts.getSimbolo(val_peek(0).sval).getTipo());
																System.out.println("Pila Error" + pilaTipo);}
break;
case 81:
//#line 455 "Gramatica2019.y"
{GramLog.println("Se encuentra ID'[' CTE ']' reduzco a Factor");
																Al.addPolaca(val_peek(3).sval);
																Al.addPolaca(val_peek(1).sval);
																Al.addPolaca("ACESS");
																System.out.println("Pila Error" + pilaTipo);
																if (Ts.getSimbolo(val_peek(3).sval).getTipo() == 'D')
																	Al.error((Al.getLinea()+1) + " Variable no definida");
																pilaTipo.push(Ts.getSimbolo(val_peek(3).sval).getTipo());}
break;
case 82:
//#line 464 "Gramatica2019.y"
{/*Error*/
																Al.warning("Linea: " + Al.getLinea() + " [ faltante en declaracion de coleccion");}
break;
case 83:
//#line 467 "Gramatica2019.y"
{/*Error*/
																Al.warning("Linea: " + Al.getLinea() + " ] faltante en declaracion de coleccion");}
break;
case 84:
//#line 470 "Gramatica2019.y"
{GramLog.println("Se encuentra ID '[' ID ']' reduzco a Factor");
																Al.addPolaca(val_peek(3).sval);
																Al.addPolaca(val_peek(1).sval);
																Al.addPolaca("ACESS");
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
case 85:
//#line 484 "Gramatica2019.y"
{GramLog.println("Se encuentra CTE_POS y '-' reduzco a CTE");
																Ts.setNeg(val_peek(0).sval);
																yyval.sval = val_peek(1).sval + val_peek(0).sval;
																}
break;
case 86:
//#line 489 "Gramatica2019.y"
{GramLog.println("Se encuentra CTE_POS reduzco a CTE");
																}
break;
case 87:
//#line 492 "Gramatica2019.y"
{GramLog.println("Se encuentra CTE_INT reduzco a CTE_POS");}
break;
case 88:
//#line 494 "Gramatica2019.y"
{GramLog.println("Se encuentra CTE_FLOAT reduzco a CTE_POS");}
break;
//#line 1342 "Parser.java"
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
