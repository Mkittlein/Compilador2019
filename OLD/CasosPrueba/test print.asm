.386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.data
STR_CON_O_SIN_MULTILINEA DB ,"CON O SIN MULTILINEA",0
STR_EL_PRINT_ANDA_BIEN DB ,"EL PRINT ANDA BIEN",0
D_1.0E300 DQ ,1.0E300,0
UI_100 DW ,100,0
_vd DQ ,0,0
_vu DW ,0,0
.code
start:
MOV R1,D_1.0E300
MOV _vd, R1
MOV R1,UI_100
MOV _vu, R1
invoke MessageBox, NULL, addr _vd, addr _vd, MB_OK
invoke MessageBox, NULL, addr _vu, addr _vu, MB_OK
invoke MessageBox, NULL, addr STR_EL_PRINT_ANDA_BIEN, addr STR_EL_PRINT_ANDA_BIEN, MB_OK
invoke MessageBox, NULL, addr STR_CON_O_SIN_MULTILINEA, addr STR_CON_O_SIN_MULTILINEA, MB_OK
invoke ExitProcess, 0
end start