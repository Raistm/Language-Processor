package procesamientos.impresion;

import procesamientos.Procesamiento;

import programa.Programa.CteInt;
import programa.Programa.CteReal;
import programa.Programa.CteString;
import programa.Programa.CteBool;
import programa.Programa.CteChar;
import programa.Programa.Var;
import programa.Programa.DRef;
import programa.Programa.Suma;
import programa.Programa.TArray;
import programa.Programa.TNull;
import programa.Programa.Prog;
import programa.Programa.Real;
import programa.Programa.RestInt;
import programa.Programa.Resta;
import programa.Programa.Select;
import programa.Programa.StringR;
import programa.Programa.DecVar;
import programa.Programa.Distinto;
import programa.Programa.Divi;
import programa.Programa.ElementoCadena;
import programa.Programa.DecTipo;
import programa.Programa.IAsig;
import programa.Programa.IBloque;
import programa.Programa.INew;
import programa.Programa.IFree;
import programa.Programa.ICall;
import programa.Programa.IDoWhile;
import programa.Programa.IIf;
import programa.Programa.IIfElse;
import programa.Programa.ISwitch;
import programa.Programa.IWhile;
import programa.Programa.Igual;
import programa.Programa.Index;
import programa.Programa.And;
import programa.Programa.Int;
import programa.Programa.Lee;
import programa.Programa.Mayor;
import programa.Programa.MayorIgual;
import programa.Programa.Menor;
import programa.Programa.MenorIgual;
import programa.Programa.Multi;
import programa.Programa.Not;
import programa.Programa.Null;
import programa.Programa.Bool;
import programa.Programa.CambiaSign;
import programa.Programa.CampoReg;
import programa.Programa.Case;
import programa.Programa.Char;
import programa.Programa.ConversionBool;
import programa.Programa.ConversionChar;
import programa.Programa.ConversionInt;
import programa.Programa.ConversionReal;
import programa.Programa.ConversionString;
import programa.Programa.Error;
import programa.Programa.Escribe;
import programa.Programa.Ok;
import programa.Programa.Or;
import programa.Programa.TRef;
import programa.Programa.TRegistro;
import programa.Programa.TPointer;
import programa.Programa.Exp;
import programa.Programa.FParam;
import programa.Programa.Inst;
import programa.Programa.Dec;
import programa.Programa.DecProc;



public class Impresion extends Procesamiento {
	private boolean atributos;
	private int identacion;
	public Impresion(boolean atributos) {
		this.atributos = atributos; 
		identacion = 0;
	}
	public Impresion() {
		this(false);
	}

	private void imprimeAtributos(Exp exp) {
		if(atributos) {
			System.out.print("@{t:");
			exp.tipo().procesaCon(this);
			System.out.print("}"); 
		}
	}
	private void imprimeAtributos(Prog prog) {
		if(atributos) {
			System.out.print("@{t:"+prog.tipo()+"}"); 
		}
	}
	private void imprimeAtributos(Inst i) {
		if(atributos) {
			System.out.print("@{t:"+i.tipo()+",dirc:"+i.dirPrimeraInstruccion()+",dirs:"+
					i.dirInstruccionSiguiente()+"}");       
		}
	}

	private void identa() {
		for (int i=0; i < identacion; i++)
			System.out.print(" ");
	}

	public void procesa(Null n) {
		System.out.print("null");
	}
	
	public void procesa(INew i) {
		identa();
		System.out.print("new ");
		i.mem().procesaCon(this);
		System.out.println();
	}
	
	public void procesa(IFree i) {
		identa();
		System.out.print("delete ");
		i.mem().procesaCon(this);
		System.out.println();
	}

	public void procesa(String i) {
		System.out.print(i);
	}

	public void procesa(CteInt exp) {
		System.out.print(exp.valEntero());
		imprimeAtributos(exp);
	} 
	public void procesa(CteBool exp) {
		System.out.print(exp.valBool());
		imprimeAtributos(exp);
	}
	public void procesa(CteReal exp) {
		System.out.print(exp.valReal());
		imprimeAtributos(exp);
	} 
	public void procesa(CteChar exp) { 
		System.out.print(exp.valChar());
		imprimeAtributos(exp);
	} 
	public void procesa(CteString exp) { 
		System.out.print(exp.valString());
		imprimeAtributos(exp);
	}
	public void procesa(DRef mem) {
		System.out.print("(*");  
		mem.mem().procesaCon(this);
		System.out.print(")");
		imprimeAtributos(mem);
	} 
	public void procesa(Var exp) {
		System.out.print(exp.var());
		imprimeAtributos(exp);
	} 
	public void procesa(Suma exp) {
		System.out.print('('); 
		exp.opnd1().procesaCon(this);
		System.out.print('+');
		imprimeAtributos(exp);
		exp.opnd2().procesaCon(this);
		System.out.print(')'); 
	} 
	public void procesa(Resta exp) {
		System.out.print('('); 
		exp.opnd1().procesaCon(this);
		System.out.print('-');
		imprimeAtributos(exp);
		exp.opnd2().procesaCon(this);
		System.out.print(')'); 
	} 

	public void procesa(Multi exp) {
		System.out.print('('); 
		exp.opnd1().procesaCon(this);
		System.out.print('*');
		imprimeAtributos(exp);
		exp.opnd2().procesaCon(this);
		System.out.print(')'); 
	} 

	public void procesa(Divi exp) {
		System.out.print('('); 
		exp.opnd1().procesaCon(this);
		System.out.print('/');
		imprimeAtributos(exp);
		exp.opnd2().procesaCon(this);
		System.out.print(')'); 
	} 

	public void procesa(RestInt exp) {
		System.out.print('('); 
		exp.opnd1().procesaCon(this);
		System.out.print('%');
		imprimeAtributos(exp);
		exp.opnd2().procesaCon(this);
		System.out.print(')'); 
	} 
	public void procesa(CambiaSign exp) {
		System.out.print('('); 
		System.out.print("Cambia el signo de ");
		exp.opnd1().procesaCon(this);
		imprimeAtributos(exp);
		System.out.print(')'); 
	} 
	public void procesa(ElementoCadena exp) {
		System.out.print('('); 
		System.out.print("Coge el elemento ");
		exp.opnd2().procesaCon(this);
		System.out.print(" de la cadena ");
		exp.opnd1().procesaCon(this);
		imprimeAtributos(exp);
		System.out.print(')'); 
	} 
	public void procesa(ConversionInt exp) {
		System.out.print('('); 
		System.out.print("Convierte ");
		exp.opnd1().procesaCon(this);
		System.out.print(" a entero");
		imprimeAtributos(exp);
		System.out.print(')'); 
	}
	public void procesa(ConversionReal exp) {
		System.out.print('('); 
		System.out.print("Convierte ");
		exp.opnd1().procesaCon(this);
		System.out.print(" a real");
		imprimeAtributos(exp);
		System.out.print(')'); 
	}
	public void procesa(ConversionChar exp) {
		System.out.print('('); 
		System.out.print("Convierte ");
		exp.opnd1().procesaCon(this);
		System.out.print(" a char");
		imprimeAtributos(exp);
		System.out.print(')'); 
	}
	public void procesa(ConversionBool exp) {
		System.out.print('('); 
		System.out.print("Convierte ");
		exp.opnd1().procesaCon(this);
		System.out.print(" a boolean");
		imprimeAtributos(exp);
		System.out.print(')'); 
	}
	public void procesa(ConversionString exp) {
		System.out.print('('); 
		System.out.print("Convierte ");
		exp.opnd1().procesaCon(this);
		System.out.print(" a string");
		imprimeAtributos(exp);
		System.out.print(')'); 
	}
	public void procesa(Igual exp) {
		System.out.print('('); 
		exp.opnd1().procesaCon(this);
		System.out.print("==");
		imprimeAtributos(exp);
		exp.opnd2().procesaCon(this);
		System.out.print(')'); 
	}
	public void procesa(Mayor exp) {
		System.out.print('('); 
		exp.opnd1().procesaCon(this);
		System.out.print('>');
		imprimeAtributos(exp);
		exp.opnd2().procesaCon(this);
		System.out.print(')'); 
	}
	public void procesa(Menor exp) {
		System.out.print('('); 
		exp.opnd1().procesaCon(this);
		System.out.print('<');
		imprimeAtributos(exp);
		exp.opnd2().procesaCon(this);
		System.out.print(')'); 
	}
	public void procesa(MayorIgual exp) {
		System.out.print('('); 
		exp.opnd1().procesaCon(this);
		System.out.print(">=");
		imprimeAtributos(exp);
		exp.opnd2().procesaCon(this);
		System.out.print(')'); 
	}
	public void procesa(MenorIgual exp) {
		System.out.print('('); 
		exp.opnd1().procesaCon(this);
		System.out.print("<=");
		imprimeAtributos(exp);
		exp.opnd2().procesaCon(this);
		System.out.print(')'); 
	}
	public void procesa(Distinto exp) {
		System.out.print('('); 
		exp.opnd1().procesaCon(this);
		System.out.print("!=");
		imprimeAtributos(exp);
		exp.opnd2().procesaCon(this);
		System.out.print(')'); 
	}
	public void procesa(And exp) {
		System.out.print('('); 
		exp.opnd1().procesaCon(this);
		System.out.print("&&");
		imprimeAtributos(exp);
		exp.opnd2().procesaCon(this);
		System.out.print(')'); 
	}
	public void procesa(Or exp) {
		System.out.print('('); 
		exp.opnd1().procesaCon(this);
		System.out.print("||");
		imprimeAtributos(exp);
		exp.opnd2().procesaCon(this);
		System.out.print(')'); 
	}
	public void procesa(Not exp) {
		System.out.print('('); 
		System.out.print("!");
		imprimeAtributos(exp);
		exp.opnd1().procesaCon(this);
		System.out.print(')'); 
	}
	public void procesa(Lee i) {
		identa();
		System.out.print("read(");
		i.var().procesaCon(this);
		System.out.print(')');
		imprimeAtributos(i);
		System.out.println();
	}
	public void procesa(Escribe i) {
		identa();
		System.out.print("print(");
		i.exp().procesaCon(this);
		System.out.print(')');
		imprimeAtributos(i);
		System.out.println();
	}
	public void procesa(Prog p) {
		for(Dec d: p.decs()) 
			d.procesaCon(this);
		p.inst().procesaCon(this);
		imprimeAtributos(p);
		System.out.println();
	}     
	public void procesa(DecVar v) { 
		v.tipoDec().procesaCon(this);
		System.out.print(" "+v.var());    
		System.out.println();
	}     
	public void procesa(DecTipo t) {
		System.out.print("typedef "); 
		t.tipoDec().procesaCon(this);
		System.out.print(" "+t.idtipo());    
		System.out.println();
	}     
	public void procesa(DecProc p) {
		identa();
		System.out.print("proc " + p.idproc() + "(");
		int nparam = 0;
		for (FParam param : p.fparams()) {
			if (nparam > 0)
				System.out.print(",");
			nparam++;
			param.tipoDec().procesaCon(this);
			if (param.esParametroPorReferencia())
				System.out.print("&");
			else
				System.out.print(" ");
			System.out.print(param.var());
		}
		System.out.println(")");
		p.cuerpo().procesaCon(this);
	}
	public void procesa(TPointer t) {
		System.out.print("(");  
		t.tbase().procesaCon(this);
		System.out.print("*)");
	} 
	public void procesa(TNull t) {
		System.out.print("(");  
		System.out.print("null)");
	}
	public void procesa(TArray t) {
		t.tbase().procesaCon(this);
		System.out.print("[" + t.dimension() + "]");
	}
	public void procesa(TRef t) {
		System.out.print(t.idtipo());
	} 
	public void procesa(Index i) {
		i.buffer().procesaCon(this);
		System.out.print("[");
		i.index().procesaCon(this);
		System.out.print("]");
	}
	public void procesa(TRegistro reg) {
		System.out.println("struct {");
		identacion++;
		for (CampoReg campo : reg.getCampos()) {
			identa();
			campo.getTipo().procesaCon(this);
			System.out.println(" " + campo.getId());
		}
		identacion--;
		identa();
		System.out.print("} ");
	}
	public void procesa(Select s) {	
		s.registro().procesaCon(this);
		System.out.print("." + s.campo());
	}
	public void procesa(Int t) {
		System.out.print(t);  
	}
	public void procesa(Bool t) {
		System.out.print(t);  
	}
	public void procesa(Real t) {
		System.out.print(t);  
	}
	public void procesa(StringR t){
		System.out.print(t);
	}
	public void procesa(Char t){
		System.out.print(t);
	}
	public void procesa(Error t) {
		System.out.print(t);  
	}
	public void procesa(Ok t) {
		System.out.print(t);  
	}

	public void procesa(ICall c) {
		identa();
		System.out.print(c.idproc() + "(");
		int nparam = 0;
		for (Exp param : c.aparams()) {
			if (nparam > 0)
				System.out.print(",");
			nparam++;
			param.procesaCon(this);
		}
		System.out.println(")");
	}
	public void procesa(IAsig i) {
		identa();
		i.mem().procesaCon(this);
		System.out.print("=");
		i.exp().procesaCon(this);
		imprimeAtributos(i);
		System.out.println(); 
	}     
	public void procesa(IBloque b) {
		identa(); 
		System.out.println("{");
		identacion += 3;
		for(Inst i: b.is())
			i.procesaCon(this);
		identacion -=3;
		identa();
		System.out.print("}");
		imprimeAtributos(b);
		System.out.println();
	}     
	public void procesa(IWhile b) {
		identa(); 
		System.out.print("while ");
		b.exp().procesaCon(this);
		System.out.println(" do {");
		identacion += 3;
		b.cuerpo().procesaCon(this);
		identacion -=3;
		identa();
		System.out.print("}");
		imprimeAtributos(b);
		System.out.println();
	}    
	public void procesa(IIf b) {
		identa(); 
		System.out.print("if ");
		b.exp().procesaCon(this);
		System.out.println(" do {");
		identacion += 3;
		b.cuerpo().procesaCon(this);
		identacion -=3;
		identa();
		System.out.print("}");
		imprimeAtributos(b);
		System.out.println();
	}
	public void procesa(IIfElse b) {
		identa(); 
		System.out.print("if ");
		b.exp().procesaCon(this);
		System.out.println(" do {");
		identacion += 3;
		b.cuerpo1().procesaCon(this);
		identacion -=3;
		identa();
		System.out.println("} else do {");
		identacion += 3;
		b.cuerpo2().procesaCon(this);
		identacion -=3;
		identa();
		System.out.print("}");
		imprimeAtributos(b);
		System.out.println();
	}
	public void procesa(IDoWhile b){
		identa();
		System.out.println("do {");
		identacion += 3;
		b.cuerpo().procesaCon(this);
		identacion -= 3;
		identa();
		System.out.print("} while ");
		b.exp().procesaCon(this);
		imprimeAtributos(b);
		System.out.println();
	}
	public void procesa(ISwitch s) {
		identa();
		System.out.print("switch ");
		s.exp().procesaCon(this);
		System.out.println(" {");
		identacion += 3;
		for (Case c : s.cases()) {
			identa();
			System.out.print("case ");
			c.getVal().procesaCon(this);
			System.out.println(":");
			identacion += 3;
			c.getInst().procesaCon(this);
			identacion -= 3;
		}
		if (s.defaultCase() != null) {
			identa();
			System.out.println("default:");
			identacion += 3;
			s.defaultCase().procesaCon(this);
			identacion -= 3;
		}
		identacion -= 3;
		identa();
		System.out.println("}");
	}
}