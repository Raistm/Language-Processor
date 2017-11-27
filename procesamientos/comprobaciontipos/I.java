package procesamientos.comprobaciontipos;

import programa.Programa.Bool;
import programa.Programa.Dec;
import programa.Programa.DecProc;
import programa.Programa.Int;
import programa.Programa.Mem;
import programa.Programa.TPointer;
import programa.Programa.Tipo;
import programa.Programa.Var;
import programa.Programa.Error;
import programa.Programa.Ok;
import programa.Programa.TArray;
import programa.Programa.TNull;
import programa.Programa.TRef;
import programa.Programa.TRegistro;


public class I {
	public static boolean esInt(Tipo t) {
		return t instanceof Int; 
	} 
	public static boolean esBool(Tipo t) {
		return t instanceof Bool; 
	} 
	public static boolean esNull(Tipo t) {
		return t instanceof TNull;
	}
	public static boolean esPointer(Tipo t) {
		return t instanceof TPointer; 
	} 
	public static TPointer tpointer(Tipo t) {
		return (TPointer) t; 
	} 
	public static boolean esArray(Tipo t) {
		return t instanceof TArray; 
	}
	public static TArray tarray(Tipo t) {
		return (TArray) t;
	}
	public static boolean esRef(Tipo t) {
		return t instanceof TRef; 
	}
	public static TRef tref(Tipo t) {
		return (TRef) t; 
	} 
	public static boolean esRegistro(Tipo t) {
		return t instanceof TRegistro;
	}
	public static TRegistro tregistro(Tipo t) {
		return (TRegistro) t;
	}
	public static boolean esError(Tipo t) {
		return t instanceof Error;
	} 
	public static boolean esOk(Tipo t) {
		return t instanceof Ok;
	}
	public static boolean esProc(Dec d) {
		return d instanceof DecProc;
	}
	public static DecProc proc(Dec d) {
		return (DecProc) d;
	}
	public static Var var(Mem t) {
		return (Var) t;
	}
	public static Tipo tipoBase(Tipo t) {
		while(I.esRef(t)) {
			t = I.tref(t).declaracion().tipoDec();  
		}
		return t;
	}
}
