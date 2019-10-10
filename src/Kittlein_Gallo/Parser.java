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
    2,    2,    1,    1,    4,    4,    4,    4,    4,    4,
    4,    4,    4,    8,    8,    8,    8,    9,    9,    6,
    6,    5,    5,    3,    3,    3,    3,    3,    3,    3,
   13,   13,   13,   13,   13,   13,   13,   12,   12,   11,
   11,   15,   15,   15,   15,   15,   15,   15,   15,   16,
   16,   16,   16,   17,   18,   18,   18,   18,   18,   14,
   14,   14,   19,   19,   19,   20,   20,   20,   20,   20,
   20,    7,    7,   10,   10,
};
final static short yylen[] = {                            2,
    4,    3,    3,    2,    3,    2,    2,    1,    3,    2,
    2,    1,    4,    1,    2,    5,    4,    4,    4,    5,
    4,    4,    4,    3,    2,    3,    2,    1,    1,    3,
    1,    1,    1,    1,    1,    4,    3,    3,    2,    1,
    3,    6,    5,    5,    6,    5,    5,    7,    5,    2,
    2,    7,    6,    6,    6,    6,    5,    4,    4,    5,
    4,    4,    3,    3,    1,    1,    1,    1,    1,    3,
    3,    1,    3,    3,    1,    1,    1,    4,    3,    3,
    4,    2,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,   32,   33,    0,    0,    0,    0,    0,    0,
    0,   14,    0,   34,   35,   40,    0,    0,    0,    0,
    0,    0,    0,    0,   84,   85,    0,   83,    0,    0,
    0,    0,    0,   77,    0,    0,    0,   75,    0,    0,
    0,    6,    0,   10,    0,    0,   50,    0,   51,    0,
    5,   28,    0,    0,    0,    0,    0,    0,    0,   82,
    0,   37,    0,    0,    0,    0,    0,   65,   66,   67,
   68,   69,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    2,    9,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   21,    0,   25,    0,   29,
    0,    0,   36,    0,    0,    0,   79,    0,    0,   59,
    0,    0,    0,    0,   58,   73,   74,    1,    0,   13,
    0,    0,   17,   22,   30,    0,   61,    0,    0,    0,
    0,    0,   24,    0,    0,    0,    0,   49,   81,   78,
    0,    0,    0,    0,    0,    0,   16,   20,   60,    0,
    0,    0,   54,    0,   55,   53,   19,   48,   52,
};
final static short yydgoto[] = {                          8,
    9,   44,   11,   12,   13,   46,   34,   98,   56,   28,
   14,   15,   16,   35,   17,   18,   36,   75,   37,   38,
};
final static short yysindex[] = {                      -226,
 -126,  121,    0,    0,  -18, -243,  -40,    0,  -49, -218,
  292,    0, -227,    0,    0,    0, -248, -222,  133, -202,
  -17,  -25,  -30, -224,    0,    0,  -22,    0,   32, -188,
 -187,   10,  -30,    0,  -35,   43,   15,    0, -126, -168,
 -159,    0, -126,    0,  -19,   59,    0, -144,    0,   49,
    0,    0,  -89,  -81,   13,  -26,    0, -153,  -36,    0,
 -148,    0,   76, -139,   74,   29,  220,    0,    0,    0,
    0,    0,  -30,  -30,  -30,  -97,  -30,  -30, -134,   34,
   75,    0,    0,  -21,   42,   47, -121, -126, -114,  292,
  -80, -119,  -30, -116,  -30,    0,  -92,    0,  -26,    0,
  -30,  -30,    0,  -70,   58,   60,    0, -126,  -61,    0,
   15,   15,  -36, -126,    0,    0,    0,    0,  -21,    0,
   61,   63,    0,    0,    0, -101,    0, -116,  -30,  -36,
  -30,  -36,    0,  -26,  -36,  -36, -126,    0,    0,    0,
  -95, -126,  -94,  292,  -87,   79,    0,    0,    0,  -36,
  -36,  -84,    0,  -82,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  169,
    6,    0,    0,    0,    0,    0,    0,    0,    0,  177,
    0,    0,    0,    0,    0,    0,    0,    0,  102,    0,
    0,    1,    0,    0,    0,    0,   45,    0,    0,    0,
  180,    0,    9,    0,  242,  259,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -42,    0,  114,    0,
    0,    0,  131,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  185,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -221,
    0,    0,    0,  271,    0,    0,    0,    0,  238,    0,
    0,    0,    0,    0,    0,   23,    0,    0,    0,    0,
   67,   87,  224,    0,    0,    0,    0,    0,    0,    0,
  282,  291,    0,    0,    0,  -75,    0,    0,    0,  143,
    0,  155,    0,  260,  167,  179,    0,    0,    0,    0,
    0,    0,    0, -239,    0,    0,    0,    0,    0,  191,
  203,    0,    0, -210,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  531,   33,  154,    0,    0,  541,   -7,  -45,  544,
    0,    0,    0,   57,    0,    0,  166,    0,   22,   20,
};
final static int YYTABLESIZE=678;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         33,
   76,   29,   52,   92,   24,   12,   73,   73,   11,   40,
   99,   94,  128,   55,   24,   31,   83,   97,   12,   47,
   48,   30,   80,   24,   68,   24,   69,   24,   57,   57,
    1,   45,    2,    3,    4,    5,   12,   86,    6,   42,
    7,   76,   76,   76,   72,   49,   63,   76,   25,   26,
   83,  134,   29,   99,   24,   51,   77,   56,   56,   76,
   76,   78,   76,   80,   80,   80,   70,   58,   52,   80,
   61,   84,   62,   52,   63,   52,  122,   52,   64,   59,
   90,   80,   80,   76,   80,   72,   71,   72,   99,  133,
   80,    3,    4,   24,  111,  112,  116,  117,   82,  110,
   65,   39,   87,   72,   72,   96,   72,   70,  115,   70,
  101,   55,   88,   41,   19,  102,  103,    5,   24,  104,
    6,  107,    7,  118,  119,   70,   70,   71,   70,   71,
   38,  113,   19,  120,  123,    5,  138,  125,    6,  124,
    7,  144,   47,  127,  129,   71,   71,  131,   71,  130,
  139,  132,  140,  147,   44,  148,  149,  135,  136,  114,
   39,   19,  153,  155,    5,   24,   46,    6,    8,    7,
  156,  157,   41,  158,   93,  159,    7,   24,   43,    4,
   25,   26,   95,   95,    3,  150,  137,  151,   19,   38,
   45,    5,   62,   81,    6,  142,    7,   19,   67,    0,
    5,   47,   42,    6,    0,    7,    0,   39,    0,   19,
    0,   21,    5,   44,   83,    6,   83,    7,   32,   83,
    0,   83,   83,   50,   83,   46,    0,    0,   32,    0,
   29,   29,   25,   26,   70,   71,   72,   43,   74,   74,
    0,   53,   25,   26,   29,    0,   25,   26,    0,   45,
    0,   25,   26,   25,   26,   25,   26,   76,   76,   76,
  109,   42,   76,   12,   64,   76,   11,   76,   76,   76,
   76,   76,   76,   12,   12,   76,   11,   11,    0,   80,
   80,   80,   25,   26,   80,   31,    0,   80,    0,   80,
   80,   80,   80,   80,   80,    0,   27,   80,    0,    0,
   31,   72,   72,   72,    0,    0,   72,   53,    0,   72,
    0,   72,   72,   72,   72,   72,   72,   15,   26,   72,
    0,   25,   26,   70,   70,   70,    0,    0,   70,   19,
   27,   70,  105,   70,   70,   70,   70,   70,   70,    0,
   18,   70,    0,   71,   71,   71,   25,   26,   71,   23,
   43,   71,   26,   71,   71,   71,   71,   71,   71,   39,
   39,   71,    0,   39,    0,    0,   39,    0,   39,   39,
   39,   41,   41,    0,    0,   41,    0,    0,   41,   22,
   41,   41,   41,    0,   23,    0,    0,    0,   38,   38,
    0,   22,   38,   25,   26,   38,   23,   38,   38,   38,
   47,   47,    0,    0,   47,   25,   26,   47,    0,   47,
   47,   47,   44,   44,    0,    0,   44,    0,    0,   44,
    0,   44,   44,   44,   46,   46,    0,    0,   46,    0,
    0,   46,    0,   46,   46,   46,   43,   43,    0,    0,
   43,    0,    0,   43,    0,   43,   43,   43,   45,   45,
    0,    0,   45,    0,    0,   45,    0,   45,   45,   45,
   42,   42,    0,    0,   42,    0,    0,   42,    0,   42,
   42,   42,    0,    0,    0,    0,  108,    0,   19,    0,
   64,    5,   64,    0,    6,   64,    7,    0,   64,    0,
   64,    0,    0,    0,   27,    0,   27,    0,   31,   27,
   31,    0,   27,   31,   27,    0,   31,    0,   31,    0,
    0,    0,    0,    0,    0,   15,   26,   15,   26,    0,
   15,   26,    0,   15,   26,   15,   26,   19,    0,   19,
   10,   20,   19,    0,    0,   19,    0,   19,   18,   41,
   18,    0,   27,   18,    0,    0,   18,   23,   18,   23,
   19,    0,   23,    5,    0,   23,    6,   23,    7,   27,
    0,   54,    0,    0,   57,    0,    0,   60,    0,   79,
    0,    0,   66,   83,    0,    0,    0,    0,   89,    0,
    0,    0,    0,    0,    0,   85,    0,    0,   57,    0,
   91,    0,    0,    0,    0,    0,    0,    0,    0,  100,
    0,    0,    0,    0,    0,  106,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  126,    0,
    0,    0,    0,    0,  121,    0,    0,   57,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  141,  143,
  100,    0,  100,    0,  145,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  146,
    0,    0,   57,    0,    0,    0,    0,  152,    0,    0,
    0,    0,  154,    0,    0,    0,    0,  100,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   44,   95,   93,   45,    0,   43,   43,    0,   59,
   56,   93,   93,   21,   45,  259,   59,   44,  258,  268,
  269,   40,    0,   45,   60,   45,   62,   45,  268,  269,
  257,  259,  259,  260,  261,  262,  258,   45,  265,  258,
  267,   41,   42,   43,    0,  268,  268,   47,  273,  274,
   93,   97,   95,   99,   45,  258,   42,  268,  269,   59,
   60,   47,   62,   41,   42,   43,    0,   93,   95,   47,
   93,   91,   41,   95,  263,   95,   84,   95,  266,   23,
   48,   59,   60,   41,   62,   41,    0,   43,  134,   97,
  259,  260,  261,   45,   73,   74,   77,   78,  258,   67,
   91,    0,   44,   59,   60,   93,   62,   41,   76,   43,
  264,  119,  257,    0,  259,  264,   41,  262,   45,  259,
  265,   93,  267,  258,   91,   59,   60,   41,   62,   43,
    0,   75,  259,   59,   93,  262,  104,  259,  265,   93,
  267,  109,    0,  258,  264,   59,   60,  264,   62,   93,
   93,   95,   93,   93,    0,   93,  258,  101,  102,  257,
   59,  259,  258,  258,  262,   45,    0,  265,    0,  267,
  258,   93,   59,  258,  264,  258,    0,   45,    0,    0,
  273,  274,  264,  264,    0,  129,  257,  131,  259,   59,
    0,  262,  268,   40,  265,  257,  267,  259,   33,   -1,
  262,   59,    0,  265,   -1,  267,   -1,  257,   -1,  259,
   -1,   91,  262,   59,  257,  265,  259,  267,  259,  262,
   -1,  264,  265,   91,  267,   59,   -1,   -1,  259,   -1,
  273,  274,  273,  274,  270,  271,  272,   59,  275,  275,
   -1,  259,  273,  274,  263,   -1,  273,  274,   -1,   59,
   -1,  273,  274,  273,  274,  273,  274,  257,  258,  259,
   41,   59,  262,  258,   41,  265,  258,  267,  268,  269,
  270,  271,  272,  268,  269,  275,  268,  269,   -1,  257,
  258,  259,  273,  274,  262,   44,   -1,  265,   -1,  267,
  268,  269,  270,  271,  272,   -1,   59,  275,   -1,   -1,
   59,  257,  258,  259,   -1,   -1,  262,  259,   -1,  265,
   -1,  267,  268,  269,  270,  271,  272,   59,   59,  275,
   -1,  273,  274,  257,  258,  259,   -1,   -1,  262,   59,
   93,  265,  259,  267,  268,  269,  270,  271,  272,   -1,
   59,  275,   -1,  257,  258,  259,  273,  274,  262,   59,
   59,  265,   93,  267,  268,  269,  270,  271,  272,  258,
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
  258,  259,   -1,   -1,  262,   -1,   -1,  265,   -1,  267,
  268,  269,   -1,   -1,   -1,   -1,  257,   -1,  259,   -1,
  257,  262,  259,   -1,  265,  262,  267,   -1,  265,   -1,
  267,   -1,   -1,   -1,  257,   -1,  259,   -1,  257,  262,
  259,   -1,  265,  262,  267,   -1,  265,   -1,  267,   -1,
   -1,   -1,   -1,   -1,   -1,  257,  257,  259,  259,   -1,
  262,  262,   -1,  265,  265,  267,  267,  257,   -1,  259,
    0,    1,  262,   -1,   -1,  265,   -1,  267,  257,    9,
  259,   -1,    2,  262,   -1,   -1,  265,  257,  267,  259,
  259,   -1,  262,  262,   -1,  265,  265,  267,  267,   19,
   -1,   21,   -1,   -1,   21,   -1,   -1,   24,   -1,   39,
   -1,   -1,   32,   43,   -1,   -1,   -1,   -1,   48,   -1,
   -1,   -1,   -1,   -1,   -1,   45,   -1,   -1,   45,   -1,
   50,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   56,
   -1,   -1,   -1,   -1,   -1,   65,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   88,   -1,
   -1,   -1,   -1,   -1,   84,   -1,   -1,   84,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  108,  109,
   97,   -1,   99,   -1,  114,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  119,
   -1,   -1,  119,   -1,   -1,   -1,   -1,  137,   -1,   -1,
   -1,   -1,  142,   -1,   -1,   -1,   -1,  134,
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
"ListaDeclarativas : ListaDeclarativas ';' DeclarativaSimple ';'",
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

//#line 188 "Gramatica2019.y"
AnalizadorLexico Al;
TablaSimbolos Ts;
PrintStream GramLog;
PrintStream EstrucLog;

public void addTS(String key, Simbolo value){
				Ts.add(key,value);
}

public Parser(AnalizadorLexico AL){
                this.Al= AL;
                Ts = this.Al.getTablaDeSimbolos();
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
//#line 502 "Parser.java"
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
																EstrucLog.close();}
break;
case 5:
//#line 20 "Gramatica2019.y"
{GramLog.println("Se encuentra BEGIN ListaEjecutables END reduzco a PROGRAMA");}
break;
case 9:
//#line 28 "Gramatica2019.y"
{GramLog.println("Se encuentra EjecutableSimple ';' ListaEjecutables reduzco a ListaEjecutables");}
break;
case 11:
//#line 32 "Gramatica2019.y"
{GramLog.println("Se encuentra EjecutableSimple  ';' reduzco a ListaEjecutables");}
break;
case 13:
//#line 36 "Gramatica2019.y"
{GramLog.println("Se encuentra DeclarativaSimple ';' ListaDeclarativas reduzco a ListaDeclarativas");}
break;
case 14:
//#line 38 "Gramatica2019.y"
{GramLog.println("Se encuentra DeclarativaSimple ';' reduzco a ListaDeclarativas");}
break;
case 15:
//#line 40 "Gramatica2019.y"
{GramLog.println("Se encuentra Tipo ListaID reduzco a DeclarativaSimple");}
break;
case 16:
//#line 42 "Gramatica2019.y"
{GramLog.println("Se encuentra Tipo ID '[' CTE ']' reduzco a DeclarativaSimple");}
break;
case 20:
//#line 50 "Gramatica2019.y"
{GramLog.println("Se encuentra Tipo ID '[' Lista_Coleccion ']' reduzco a DeclarativaSimple");}
break;
case 24:
//#line 58 "Gramatica2019.y"
{GramLog.println("Se encuentra ElementoColeccion ',' Lista_Coleccion reduzco a Lista_Coleccion");}
break;
case 26:
//#line 62 "Gramatica2019.y"
{GramLog.println("Se encuentra ElementoColeccion ',' ElementoColeccion reduzco a Lista_Coleccion");}
break;
case 28:
//#line 66 "Gramatica2019.y"
{GramLog.println("Se encuentra _ reduzco a ElementoColeccion");}
break;
case 29:
//#line 68 "Gramatica2019.y"
{GramLog.println("Se encuentra CTE_POS reduzco a ElementoColeccion");}
break;
case 30:
//#line 70 "Gramatica2019.y"
{GramLog.println("Se encuentra ListaID ',' ID reduzco a ListaID");}
break;
case 31:
//#line 72 "Gramatica2019.y"
{GramLog.println("Se encuentra ID reduzco a ListaID");}
break;
case 32:
//#line 74 "Gramatica2019.y"
{GramLog.println("Se encuentra T_int reduzco a Tipo");}
break;
case 33:
//#line 76 "Gramatica2019.y"
{GramLog.println("Se encuentra T_float reduzco a Tipo");}
break;
case 34:
//#line 78 "Gramatica2019.y"
{GramLog.println("Se encuentra BloqueIF reduzco a EjecutableSimple");
																EstrucLog.println("Sentencia IF en linea " + Al.getLinea());}
break;
case 35:
//#line 81 "Gramatica2019.y"
{GramLog.println("Se encuentra BloqueForeach reduzco a EjecutableSimple");
																EstrucLog.println("Sentencia ForEach en linea " + Al.getLinea());}
break;
case 36:
//#line 84 "Gramatica2019.y"
{GramLog.println("Se encuentra Print '(' CADENA ')' reduzco a EjecutableSimple");
															   	EstrucLog.println("Sentencia Print en linea " + Al.getLinea());}
break;
case 40:
//#line 93 "Gramatica2019.y"
{GramLog.println("Se encuentra Asignacion reduzco a EjecutableSimple");
																EstrucLog.println("Sentencia Asignacion en linea " + Al.getLinea());}
break;
case 41:
//#line 96 "Gramatica2019.y"
{GramLog.println("Se encuentra ID ASIG Expresion reduzco a Asignacion");}
break;
case 42:
//#line 98 "Gramatica2019.y"
{GramLog.println("Se encuentra ID '[' CTE ']' ASIG Expresion reduzco a Asignacion");}
break;
case 45:
//#line 104 "Gramatica2019.y"
{GramLog.println("Se encuentra ID '[' ID ']' ASIG Expresion reduzco a Asignacion");}
break;
case 48:
//#line 110 "Gramatica2019.y"
{GramLog.println("Se encuentra FOREACH ID IN ID BEGIN ListaEjecutables END reduzco a BloqueForeach");}
break;
case 49:
//#line 112 "Gramatica2019.y"
{GramLog.println("Se encuentra FOREACH ID IN ID EjecutableSimple reduzco a BloqueForeach");}
break;
case 50:
//#line 114 "Gramatica2019.y"
{GramLog.println("Se encuentra IfSinElse END_IF reduzco a BloqueIF");}
break;
case 51:
//#line 116 "Gramatica2019.y"
{GramLog.println("Se encuentra IfConElse END_IF reduzco a BloqueIF");}
break;
case 52:
//#line 118 "Gramatica2019.y"
{GramLog.println("Se encuentra IF '(' Comparacion ')' BEGIN ListaEjecutables END reduzco a IfSinElse");}
break;
case 60:
//#line 134 "Gramatica2019.y"
{GramLog.println("Se encuentra IfSinElse ELSE BEGIN ListaEjecutables END reduzco a IfConElse");}
break;
case 63:
//#line 140 "Gramatica2019.y"
{GramLog.println("Encuentro IfSinElse ELSE EjecutableSimple reduzco a IfConElse");}
break;
case 64:
//#line 142 "Gramatica2019.y"
{GramLog.println("Encuentro Expresion Comparador Expresion reduzco a Comparacion");}
break;
case 65:
//#line 144 "Gramatica2019.y"
{GramLog.println("Encuentro < reduzco a Comparador");}
break;
case 66:
//#line 146 "Gramatica2019.y"
{GramLog.println("Encuentro > reduzco a Comparador");}
break;
case 67:
//#line 148 "Gramatica2019.y"
{GramLog.println("Encuentro MENOI reduzco a Comparador");}
break;
case 68:
//#line 150 "Gramatica2019.y"
{GramLog.println("Encuentro MAYOI reduzco a Comparador");}
break;
case 69:
//#line 152 "Gramatica2019.y"
{GramLog.println("Encuentro IGUAL reduzco a Comparador");}
break;
case 70:
//#line 155 "Gramatica2019.y"
{GramLog.println("Encuentro Expresion '+' Termino reduzco a Expresion");}
break;
case 71:
//#line 157 "Gramatica2019.y"
{GramLog.println("Encuentro Expresion '–' Termino reduzco a Expresion");}
break;
case 72:
//#line 159 "Gramatica2019.y"
{GramLog.println("Encuentro Termino reduzco a Expresion");}
break;
case 73:
//#line 161 "Gramatica2019.y"
{GramLog.println("Encuentro Termino '*' Factor reduzco a Termino");}
break;
case 74:
//#line 163 "Gramatica2019.y"
{GramLog.println("Encuentro Termino '/' Factor reduzco a Termino");}
break;
case 75:
//#line 165 "Gramatica2019.y"
{GramLog.println("Encuentro Factor reduzco a Termino");}
break;
case 76:
//#line 167 "Gramatica2019.y"
{GramLog.println("Encuentro ID reduzco a Factor");}
break;
case 77:
//#line 169 "Gramatica2019.y"
{GramLog.println("Encuentro CTE reduzco a Factor");}
break;
case 78:
//#line 171 "Gramatica2019.y"
{GramLog.println("Encuentro ID'[' CTE ']' reduzco a Factor");}
break;
case 81:
//#line 177 "Gramatica2019.y"
{GramLog.println("Encuentro ID '[' ID ']'	 reduzco a Factor");}
break;
case 82:
//#line 179 "Gramatica2019.y"
{GramLog.println("Encuentro CTE_POS y '-' reduzco a CTE");}
break;
case 83:
//#line 181 "Gramatica2019.y"
{GramLog.println("Encuentro CTE_POS reduzco a CTE");}
break;
case 84:
//#line 183 "Gramatica2019.y"
{GramLog.println("Encuentro CTE_INT reduzco a CTE_POS");}
break;
case 85:
//#line 185 "Gramatica2019.y"
{GramLog.println("Encuentro CTE_FLOAT reduzco a CTE_POS");}
break;
//#line 861 "Parser.java"
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
