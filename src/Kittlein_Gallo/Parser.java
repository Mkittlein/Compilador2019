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
            2,    1,    3,    2,    2,    5,    4,    4,    4,    5,
            4,    4,    4,    3,    2,    3,    2,    1,    1,    3,
            1,    1,    1,    1,    1,    4,    3,    3,    2,    1,
            3,    6,    5,    5,    6,    5,    5,    7,    6,    2,
            2,    7,    6,    6,    6,    6,    6,    5,    5,    5,
            4,    4,    4,    3,    1,    1,    1,    1,    1,    3,
            3,    1,    3,    3,    1,    1,    1,    4,    3,    3,
            4,    2,    1,    1,    1,
    };
    final static short yydefred[] = {                         0,
            0,    0,   32,   33,    0,    0,    0,    0,    0,    0,
            0,    0,    0,   34,   35,   40,    0,    0,    0,    0,
            0,    0,    0,    0,   84,   85,    0,   83,    0,    0,
            0,    0,    0,   77,    0,    0,    0,   75,    0,    0,
            0,    6,    0,   10,   14,    0,    0,   50,    0,   51,
            0,    5,   28,    0,    0,    0,    0,    0,    0,    0,
            82,    0,   37,    0,    0,    0,    0,    0,   65,   66,
            67,   68,   69,    0,    0,    0,    0,    0,    0,    0,
            2,   13,    9,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,   21,    0,   25,    0,   29,
            0,    0,   36,    0,    0,    0,   79,    0,    0,    0,
            0,    0,    0,    0,    0,   73,   74,    1,    0,    0,
            17,   22,   30,    0,   61,    0,    0,    0,    0,    0,
            0,   24,    0,    0,    0,    0,    0,   81,   78,    0,
            0,    0,    0,   59,    0,   58,   16,   20,   60,    0,
            0,    0,   49,   54,    0,   55,    0,   53,   48,   52,
    };
    final static short yydgoto[] = {                          8,
            9,   44,   11,   12,   13,   47,   34,   98,   57,   28,
            14,   15,   16,   35,   17,   18,   36,   76,   37,   38,
    };
    final static short yysindex[] = {                      -144,
            -114,  121,    0,    0,  -18, -248,  -40,    0,  -97, -225,
            -49,  -24, -221,    0,    0,    0, -222, -228,  133, -206,
            -17,  -22,  -30, -224,    0,    0,  -20,    0,   16, -188,
            -187,   10,  -30,    0,  -35,   48,  -10,    0, -114, -167,
            34,    0, -114,    0,    0,  -19,   59,    0,  -61,    0,
            49,    0,    0,  -89,  -81,   13,  -26,    0, -140,  -36,
            0, -139,    0,   79, -127,   74,   41,  220,    0,    0,
            0,    0,    0,  -30,  -30,  -30,  254,  -30,  -30, -120,
            0,    0,    0,  -21,   46,   47, -117, -114, -104,  237,
            -80, -108,  -30, -107,  -30,    0,  -92,    0,  -26,    0,
            -30,  -30,    0,  266,   51,   76,    0, -114,  270,  113,
            -10,  -10,  -36, -114,  115,    0,    0,    0,   83,   84,
            0,    0,    0,  -78,    0, -114, -107,  -30,  -36,  -30,
            -36,    0,  -26,  -36,  -36, -114,  127,    0,    0,  -70,
            -114,  -69,  238,    0,  -66,    0,    0,    0,    0,  -36,
            -36,  -65,    0,    0,  -64,    0, -114,    0,    0,    0,
    };
    final static short yyrindex[] = {                         0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,  195,
            6,    0,    0,    0,    0,    0,    0,    0,    0,  197,
            0,    0,    0,    0,    0,    0,    0,    0,  102,    0,
            0,    1,    0,    0,    0,    0,   45,    0,    0,  199,
            0,    0,    9,    0,    0,  -23,  141,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,  -42,    0,  114,
            0,    0,    0,  131,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,  205,
            0,    0,    0,    0,    0,    0,    0,    0,    0,  -51,
            0,    0,    0,  149,    0,    0,    0,    0,  -39,    0,
            0,    0,    0,    0,    0,   23,    0,    0,    0,    0,
            67,   87,  224,    0,    0,    0,    0,    0,  150,  152,
            0,    0,    0,  -53,    0, -227,    0,    0,  143,    0,
            155,    0,  -25,  167,  179,    0,    0,    0,    0,    0,
            0,    0,  -51,    0,    0,    0,    0,    0,    0,  191,
            203,    0,    0,    0, -172,    0, -239,    0,    0,    0,
    };
    final static short yygindex[] = {                         0,
            0,  475,   32,  208,    0,    0,  488,   -7,  -41,   38,
            0,    0,    0,   57,    0,    0,  187,    0,   24,   33,
    };
    final static int YYTABLESIZE=632;
    static short yytable[];
    static { yytable();}
    static void yytable(){
        yytable = new short[]{                         33,
                76,   29,   53,   92,   24,   12,   74,   74,   11,   43,
                31,   94,  127,   56,   24,   99,   83,   97,   11,   27,
                31,   30,   80,   24,   69,   24,   70,   24,   57,   57,
                11,   78,   42,   26,   45,   31,   79,   46,   86,   50,
                63,   76,   76,   76,   72,   48,   49,   76,   25,   26,
                83,   52,   29,   27,   24,  133,   63,   99,   58,   76,
                76,   61,   76,   80,   80,   80,   70,   26,   53,   80,
                59,   84,   62,   53,   64,   53,  120,   53,   65,   60,
                90,   80,   80,   58,   80,   72,   71,   72,   77,  132,
                81,   99,   82,   24,  100,   56,   56,  111,  112,  110,
                66,   39,   87,   72,   72,   96,   72,   70,  115,   70,
                116,  117,    1,   41,    2,    3,    4,    5,   24,  103,
                6,   58,    7,  101,  102,   70,   70,   71,   70,   71,
                38,  104,  113,  107,  100,  137,  100,  118,  121,  122,
                143,  123,   47,  138,   19,   71,   71,    5,   71,  129,
                6,  131,    7,  125,   44,  128,  130,  134,  135,   39,
                39,    2,    3,    4,    5,   24,   46,    6,  139,    7,
                100,  144,   41,  146,   93,  147,  148,   24,   43,  149,
                25,   26,   95,   95,  150,  153,  151,  154,  156,   38,
                45,  158,  159,  160,    8,   88,    7,   19,    4,   15,
                5,   47,   42,    6,    3,    7,   12,   19,   18,   19,
                23,   21,    5,   44,   62,    6,   41,    7,   32,   68,
                0,   83,    0,   51,    0,   46,    0,    0,   32,    0,
                29,   29,   25,   26,   71,   72,   73,   43,   75,   75,
                0,   54,   25,   26,   29,    0,   25,   26,    0,   45,
                0,   25,   26,   25,   26,   25,   26,   76,   76,   76,
                109,   42,   76,   12,   64,   76,   11,   76,   76,   76,
                76,   76,   76,   12,   12,   76,   11,   11,    0,   80,
                80,   80,   25,   26,   80,    0,    0,   80,    0,   80,
                80,   80,   80,   80,   80,  126,  157,   80,    0,    0,
                0,   72,   72,   72,    0,    0,   72,   54,    0,   72,
                0,   72,   72,   72,   72,   72,   72,    0,    0,   72,
                0,   25,   26,   70,   70,   70,    0,    0,   70,    0,
                0,   70,  105,   70,   70,   70,   70,   70,   70,    0,
                0,   70,    0,   71,   71,   71,   25,   26,   71,    0,
                0,   71,    0,   71,   71,   71,   71,   71,   71,   39,
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
                42,   42,    0,    0,   10,   20,  108,    0,   19,    0,
                64,    5,   64,   40,    6,   64,    7,    0,   64,   27,
                64,    0,    0,    0,    0,   19,   19,    0,    5,    5,
                0,    6,    6,    7,    7,    0,   27,    0,   55,    0,
                114,    0,   19,   80,    0,    5,    0,   83,    6,   67,
                7,    0,  136,   89,   19,    0,  141,    5,   19,    0,
                6,    5,    7,   85,    6,    0,    7,    0,   91,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,  106,    0,    0,    0,    0,    0,    0,
                0,    0,  124,    0,    0,    0,    0,    0,    0,    0,
                0,  119,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,  140,  142,    0,    0,    0,    0,  145,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                83,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                152,    0,    0,    0,    0,  155,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,   83,
        };
    }
    static short yycheck[];
    static { yycheck(); }
    static void yycheck() {
        yycheck = new short[] {                         40,
                0,   44,   95,   93,   45,    0,   43,   43,    0,   59,
                259,   93,   93,   21,   45,   57,   59,   44,  258,   59,
                44,   40,    0,   45,   60,   45,   62,   45,  268,  269,
                258,   42,  258,   59,   59,   59,   47,  259,   46,  268,
                268,   41,   42,   43,    0,  268,  269,   47,  273,  274,
                93,  258,   95,   93,   45,   97,   41,   99,   21,   59,
                60,   24,   62,   41,   42,   43,    0,   93,   95,   47,
                93,   91,   93,   95,  263,   95,   84,   95,  266,   23,
                49,   59,   60,   46,   62,   41,    0,   43,   41,   97,
                258,  133,   59,   45,   57,  268,  269,   74,   75,   68,
                91,    0,   44,   59,   60,   93,   62,   41,   77,   43,
                78,   79,  257,    0,  259,  260,  261,  262,   45,   41,
                265,   84,  267,  264,  264,   59,   60,   41,   62,   43,
                0,  259,   76,   93,   97,  104,   99,  258,   93,   93,
                109,  259,    0,   93,  259,   59,   60,  262,   62,   93,
                265,   95,  267,  258,    0,  264,  264,  101,  102,  257,
                59,  259,  260,  261,  262,   45,    0,  265,   93,  267,
                133,   59,   59,   59,  264,   93,   93,   45,    0,  258,
                273,  274,  264,  264,  128,   59,  130,  258,  258,   59,
                0,  258,  258,  258,    0,  257,    0,  259,    0,   59,
                262,   59,    0,  265,    0,  267,  258,   59,   59,  259,
                59,   91,  262,   59,  268,  265,    9,  267,  259,   33,
                -1,  264,   -1,   91,   -1,   59,   -1,   -1,  259,   -1,
                273,  274,  273,  274,  270,  271,  272,   59,  275,  275,
                -1,  259,  273,  274,  263,   -1,  273,  274,   -1,   59,
                -1,  273,  274,  273,  274,  273,  274,  257,  258,  259,
                41,   59,  262,  258,   41,  265,  258,  267,  268,  269,
                270,  271,  272,  268,  269,  275,  268,  269,   -1,  257,
                258,  259,  273,  274,  262,   -1,   -1,  265,   -1,  267,
                268,  269,  270,  271,  272,   59,   59,  275,   -1,   -1,
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
                258,  259,   -1,   -1,  262,   -1,   -1,  265,   -1,  267,
                268,  269,   -1,   -1,    0,    1,  257,   -1,  259,   -1,
                257,  262,  259,    9,  265,  262,  267,   -1,  265,    2,
                267,   -1,   -1,   -1,   -1,  259,  259,   -1,  262,  262,
                -1,  265,  265,  267,  267,   -1,   19,   -1,   21,   -1,
                257,   -1,  259,   39,   -1,  262,   -1,   43,  265,   32,
                267,   -1,  257,   49,  259,   -1,  257,  262,  259,   -1,
                265,  262,  267,   46,  265,   -1,  267,   -1,   51,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   66,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   88,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   84,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,  108,  109,   -1,   -1,   -1,   -1,  114,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                126,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                136,   -1,   -1,   -1,   -1,  141,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,  157,
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
            "ListaDeclarativas : DeclarativaSimple ';'",
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
            "BloqueForeach : FOREACH ID IN ID EjecutableSimple ';'",
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

    //#line 256 "Gramatica2019.y"
    AnalizadorLexico Al;
    TablaSimbolos Ts;
    PrintStream GramLog;
    PrintStream EstrucLog;

    ArrayList<String> ListaIds;
    int tam;

    public void addTS(String key, Simbolo value){
        Ts.add(key,value);
    }

    public Parser(AnalizadorLexico AL){
        this.Al= AL;
        Ts = this.Al.getTablaDeSimbolos();
        ListaIds = new ArrayList();
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
    //#line 499 "Parser.java"
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
                case 2:
//#line 15 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " Begin inicial faltante");}
                break;
                case 3:
//#line 18 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " End final faltante");}
                break;
                case 4:
//#line 21 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " Begin y End principal faltantes");}
                break;
                case 5:
//#line 24 "Gramatica2019.y"
                {GramLog.println("Se encuentra BEGIN ListaEjecutables END reduzco a PROGRAMA");}
                break;
                case 6:
//#line 26 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " Begin inicial faltante");}
                break;
                case 7:
//#line 29 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " End final faltante");}
                break;
                case 8:
//#line 32 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " Begin y End principal faltantes");}
                break;
                case 9:
//#line 35 "Gramatica2019.y"
                {GramLog.println("Se encuentra EjecutableSimple ';' ListaEjecutables reduzco a ListaEjecutables");}
                break;
                case 10:
//#line 37 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " ; faltante al terminar la linea");}
                break;
                case 11:
//#line 40 "Gramatica2019.y"
                {GramLog.println("Se encuentra EjecutableSimple  ';' reduzco a ListaEjecutables");}
                break;
                case 12:
//#line 42 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " ; faltante al terminar la linea");}
                break;
                case 13:
//#line 45 "Gramatica2019.y"
                {GramLog.println("Se encuentra DeclarativaSimple ';' ListaDeclarativas reduzco a ListaDeclarativas");}
                break;
                case 14:
//#line 47 "Gramatica2019.y"
                {GramLog.println("Se encuentra DeclarativaSimple ';' reduzco a ListaDeclarativas");}
                break;
                case 15:
//#line 49 "Gramatica2019.y"
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
                case 16:
//#line 61 "Gramatica2019.y"
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
                case 17:
//#line 72 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " [ faltante en la declaracion de coleccion");}
                break;
                case 18:
//#line 75 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " ] faltante en la declaracion de coleccion");}
                break;
                case 19:
//#line 78 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " Falta declaracion de tipo para la coleccion");}
                break;
                case 20:
//#line 81 "Gramatica2019.y"
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
                case 21:
//#line 92 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " tipo faltante en la declaracion de coleccion");}
                break;
                case 22:
//#line 95 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " [ faltante en la declaracion de coleccion");}
                break;
                case 23:
//#line 98 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " ] faltante en la declaracion de coleccion");}
                break;
                case 24:
//#line 101 "Gramatica2019.y"
                {GramLog.println("Se encuentra ElementoColeccion ',' Lista_Coleccion reduzco a Lista_Coleccion");
                    tam++;}
                break;
                case 25:
//#line 104 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " , faltante entre elementos de coleccion");}
                break;
                case 26:
//#line 107 "Gramatica2019.y"
                {GramLog.println("Se encuentra ElementoColeccion ',' ElementoColeccion reduzco a Lista_Coleccion");
                    tam = 2;}
                break;
                case 27:
//#line 110 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " , faltante entre elementos de coleccion");}
                break;
                case 28:
//#line 113 "Gramatica2019.y"
                {GramLog.println("Se encuentra _ reduzco a ElementoColeccion");}
                break;
                case 29:
//#line 115 "Gramatica2019.y"
                {GramLog.println("Se encuentra CTE_POS reduzco a ElementoColeccion");}
                break;
                case 30:
//#line 117 "Gramatica2019.y"
                {GramLog.println("Se encuentra ListaID ',' ID reduzco a ListaID");
                    ListaIds.add(val_peek(0).sval);}
                break;
                case 31:
//#line 120 "Gramatica2019.y"
                {GramLog.println("Se encuentra ID reduzco a ListaID");
                    ListaIds.add(val_peek(0).sval.toString());}
                break;
                case 32:
//#line 123 "Gramatica2019.y"
                {GramLog.println("Se encuentra T_int reduzco a Tipo");}
                break;
                case 33:
//#line 125 "Gramatica2019.y"
                {GramLog.println("Se encuentra T_float reduzco a Tipo");}
                break;
                case 34:
//#line 127 "Gramatica2019.y"
                {GramLog.println("Se encuentra BloqueIF reduzco a EjecutableSimple");
                    EstrucLog.println("Sentencia IF en linea " + Al.getLinea());}
                break;
                case 35:
//#line 130 "Gramatica2019.y"
                {GramLog.println("Se encuentra BloqueForeach reduzco a EjecutableSimple");
                    EstrucLog.println("Sentencia ForEach en linea " + Al.getLinea());}
                break;
                case 36:
//#line 133 "Gramatica2019.y"
                {GramLog.println("Se encuentra Print '(' CADENA ')' reduzco a EjecutableSimple");
                    EstrucLog.println("Sentencia Print en linea " + Al.getLinea());}
                break;
                case 37:
//#line 136 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " ( faltante en el print");}
                break;
                case 38:
//#line 139 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " ) faltante en el print");}
                break;
                case 39:
//#line 142 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " ( y ) faltante en el print");}
                break;
                case 40:
//#line 145 "Gramatica2019.y"
                {GramLog.println("Se encuentra Asignacion reduzco a EjecutableSimple");
                    EstrucLog.println("Sentencia Asignacion en linea " + Al.getLinea());}
                break;
                case 41:
//#line 148 "Gramatica2019.y"
                {GramLog.println("Se encuentra ID ASIG Expresion reduzco a Asignacion");}
                break;
                case 42:
//#line 150 "Gramatica2019.y"
                {GramLog.println("Se encuentra ID '[' CTE ']' ASIG Expresion reduzco a Asignacion");}
                break;
                case 43:
//#line 152 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " [ faltante en la asignacion de coleccion");}
                break;
                case 44:
//#line 155 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " ] faltante en la asignacion de coleccion");}
                break;
                case 45:
//#line 158 "Gramatica2019.y"
                {GramLog.println("Se encuentra ID '[' ID ']' ASIG Expresion reduzco a Asignacion");}
                break;
                case 46:
//#line 160 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " [ faltante en la asignacion de coleccion");}
                break;
                case 47:
//#line 163 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " ] faltante en la asignacion de coleccion");}
                break;
                case 48:
//#line 166 "Gramatica2019.y"
                {GramLog.println("Se encuentra FOREACH ID IN ID BEGIN ListaEjecutables END reduzco a BloqueForeach");}
                break;
                case 49:
//#line 168 "Gramatica2019.y"
                {GramLog.println("Se encuentra FOREACH ID IN ID EjecutableSimple reduzco a BloqueForeach");}
                break;
                case 50:
//#line 170 "Gramatica2019.y"
                {GramLog.println("Se encuentra IfSinElse END_IF reduzco a BloqueIF");}
                break;
                case 51:
//#line 172 "Gramatica2019.y"
                {GramLog.println("Se encuentra IfConElse END_IF reduzco a BloqueIF");}
                break;
                case 52:
//#line 174 "Gramatica2019.y"
                {GramLog.println("Se encuentra IF '(' Comparacion ')' BEGIN ListaEjecutables END reduzco a IfSinElse");}
                break;
                case 53:
//#line 176 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " ( faltante en la comparacion");}
                break;
                case 54:
//#line 179 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " ) faltante en la comparacion");}
                break;
                case 55:
//#line 182 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " Begin faltante en la lista de sentencias");}
                break;
                case 56:
//#line 185 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " End faltante en la lista de sentencias");}
                break;
                case 57:
//#line 188 "Gramatica2019.y"
                {GramLog.println("Se encuentra IF '(' Comparacion ')' EjecutableSimple reduzco a IfSinElse");}
                break;
                case 58:
//#line 190 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " ( faltante en la lista de sentencias");}
                break;
                case 59:
//#line 193 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " ) faltante en la comparacion");}
                break;
                case 60:
//#line 196 "Gramatica2019.y"
                {GramLog.println("Se encuentra IfSinElse ELSE BEGIN ListaEjecutables END reduzco a IfConElse");}
                break;
                case 61:
//#line 198 "Gramatica2019.y"
                {/*Error */
                    Al.warning(Al.getLinea() + " Begin faltante en la lista de sentencias");}
                break;
                case 62:
//#line 201 "Gramatica2019.y"
                {/*Error*/
                    Al.warning(Al.getLinea() + " End faltante en la lista de sentencias");}
                break;
                case 63:
//#line 204 "Gramatica2019.y"
                {GramLog.println("Encuentro IfSinElse ELSE EjecutableSimple reduzco a IfConElse");}
                break;
                case 64:
//#line 206 "Gramatica2019.y"
                {GramLog.println("Encuentro Expresion Comparador Expresion reduzco a Comparacion");}
                break;
                case 65:
//#line 208 "Gramatica2019.y"
                {GramLog.println("Encuentro < reduzco a Comparador");}
                break;
                case 66:
//#line 210 "Gramatica2019.y"
                {GramLog.println("Encuentro > reduzco a Comparador");}
                break;
                case 67:
//#line 212 "Gramatica2019.y"
                {GramLog.println("Encuentro MenorIgual reduzco a Comparador");}
                break;
                case 68:
//#line 214 "Gramatica2019.y"
                {GramLog.println("Encuentro MayorIgual reduzco a Comparador");}
                break;
                case 69:
//#line 216 "Gramatica2019.y"
                {GramLog.println("Encuentro Igual reduzco a Comparador");}
                break;
                case 70:
//#line 219 "Gramatica2019.y"
                {GramLog.println("Encuentro Expresion '+' Termino reduzco a Expresion");}
                break;
                case 71:
//#line 221 "Gramatica2019.y"
                {GramLog.println("Encuentro Expresion '–' Termino reduzco a Expresion");}
                break;
                case 72:
//#line 223 "Gramatica2019.y"
                {GramLog.println("Encuentro Termino reduzco a Expresion");}
                break;
                case 73:
//#line 225 "Gramatica2019.y"
                {GramLog.println("Encuentro Termino '*' Factor reduzco a Termino");}
                break;
                case 74:
//#line 227 "Gramatica2019.y"
                {GramLog.println("Encuentro Termino '/' Factor reduzco a Termino");}
                break;
                case 75:
//#line 229 "Gramatica2019.y"
                {GramLog.println("Encuentro Factor reduzco a Termino");}
                break;
                case 76:
//#line 231 "Gramatica2019.y"
                {GramLog.println("Encuentro ID reduzco a Factor");}
                break;
                case 77:
//#line 233 "Gramatica2019.y"
                {GramLog.println("Encuentro CTE reduzco a Factor");}
                break;
                case 78:
//#line 235 "Gramatica2019.y"
                {GramLog.println("Encuentro ID'[' CTE ']' reduzco a Factor");}
                break;
                case 79:
//#line 237 "Gramatica2019.y"
                {/*Error*/
                    Al.warning(Al.getLinea() + " [ faltante en declaracion de coleccion");}
                break;
                case 80:
//#line 240 "Gramatica2019.y"
                {/*Error*/
                    Al.warning(Al.getLinea() + " ] faltante en declaracion de coleccion");}
                break;
                case 81:
//#line 243 "Gramatica2019.y"
                {GramLog.println("Encuentro ID '[' ID ']' reduzco a Factor");}
                break;
                case 82:
//#line 245 "Gramatica2019.y"
                {GramLog.println("Encuentro CTE_POS y '-' reduzco a CTE");
                    Ts.setNeg(val_peek(0).sval);
                }
                break;
                case 83:
//#line 249 "Gramatica2019.y"
                {GramLog.println("Encuentro CTE_POS reduzco a CTE");}
                break;
                case 84:
//#line 251 "Gramatica2019.y"
                {GramLog.println("Encuentro CTE_INT reduzco a CTE_POS");}
                break;
                case 85:
//#line 253 "Gramatica2019.y"
                {GramLog.println("Encuentro CTE_FLOAT reduzco a CTE_POS");}
                break;
//#line 1062 "Parser.java"
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
