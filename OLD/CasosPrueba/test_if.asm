.386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.data
_v DW ,0,0
_v2 DW ,0,0
UI_77 DW ,77,0
UI_7 DW ,7,0
UI_78 DW ,78,0
UI_4 DW ,4,0
.code
start:
CMP UI_4,_v
JGE Label10
MOV R1,UI_77
MOV _v, R1
JMP Label13
Label10:
MOV R1,UI_78
MOV _v, R1
Label13:
MOV R1,UI_7
MOV _v2, R1
invoke ExitProcess, 0
end start