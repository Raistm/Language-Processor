package programa;

import java.util.List;

import procesamientos.Procesamiento;
import procesamientos.comprobaciontipos.I;

public abstract class Programa {
	private final TDefinido TENT;
	private final TDefinido TBOOL;
	private final TDefinido TREAL;
	private final TDefinido TCHAR;
	private final TDefinido TSTRING;
	private final Tipo TOK;
	private final Tipo TERROR;

	public Programa() {
		TENT = new Int();
		TBOOL = new Bool();
		TCHAR = new Char();
		TREAL = new Real();
		TSTRING = new StringR();
		TOK = new Ok();
		TERROR = new Error();
	}


	public interface Tipo {
		void procesaCon(Procesamiento p);
	}

	public abstract class TDefinido implements Tipo {
		private int tam;
		public TDefinido() {
			tam=0;  
		}
		public int tamanio() {return tam;}
		public void ponTamanio(int tam) {this.tam = tam;}
	}

	public class Int extends TDefinido {
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}     
		public String toString() {return "INT";}
	}
	public class Bool extends TDefinido {
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}         
		public String toString() {return "BOOL";}
	}
	public class Char extends TDefinido {
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}     
		public String toString() {return "CHAR";}
	}
	public class StringR extends TDefinido {
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}     
		public String toString() {return "STRING";}
	}
	public class Real extends TDefinido {
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}     
		public String toString() {return "REAL";}
	}
	public class TPointer extends TDefinido {
		private TDefinido tbase;
		public TPointer(TDefinido tbase) {
			this.tbase = tbase;
		}
		public TDefinido tbase() {return tbase;}
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}         
	}
	public class TArray extends TDefinido {
		private int dimension;
		private TDefinido tbase;
		private String enlaceFuente;
		private Tipo tipo;
		public TArray(TDefinido tbase, int dimension){
			this(tbase, dimension, null);
		}
		public TArray(TDefinido tbase, int dimension, String enlaceFuente){
			this.dimension = dimension;
			this.tbase = tbase;
			this.enlaceFuente = enlaceFuente;
		}
		public void ponTipo(Tipo tipo){
			this.tipo = tipo;
		}
		public int dimension() {return this.dimension;}
		public TDefinido tbase() {return tbase;}
		public String enlaceFuente() {return this.enlaceFuente;}
		public Tipo tipo() {return this.tipo;}
		@Override
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}         
	}
	public class CampoReg {
		private String id;
		private TDefinido tipo;

		public CampoReg(String id, TDefinido tipo) {
			this.id = id;
			this.tipo = tipo;
		}

		public String getId() {
			return id;
		}

		public TDefinido getTipo() {
			return tipo;
		}
	}

	public class TRegistro extends TDefinido {
		private CampoReg[] campos;
		private String enlaceFuente;
		private Tipo tipo;

		public TRegistro(CampoReg[] campos) {
			this(campos, null);
		}

		public TRegistro(CampoReg[] campos, String enlaceFuente) {
			this.campos = campos;
			this.enlaceFuente = enlaceFuente;
		}

		public String enlaceFuente() {
			return enlaceFuente;
		}

		@Override
		public void procesaCon(Procesamiento p) {
			p.procesa(this);
		}

		public CampoReg[] getCampos() {
			return campos;
		}

		public boolean tieneCampo(String campo) {
			int i = 0;
			boolean ok = false;

			while (i < campos.length && !ok) {
				if (campos[i].getId().equals(campo))
					ok = true;
				else
					i++;
			}

			return ok;
		}
		public Tipo getTipoByCampo(String campo) {
			Tipo tipo = null;
			int i = 0;

			while (i < campos.length && tipo == null) {
				if (campos[i].getId().equals(campo))
					tipo = campos[i].getTipo();
				else
					i++;
			}

			return tipo;
		}

		public int getIndexByCampo(String campo) {
			int i = 0;
			boolean ok = false;

			while (i < campos.length && !ok) {
				if (campos[i].getId().equals(campo))
					ok = true;
				else
					i++;
			}

			return (ok) ? i : -1;
		}

		public void ponTipo(Tipo tipo) {
			this.tipo = tipo;
		}

		public Tipo tipo() {
			return tipo;
		}
	}
	public class TNull extends TDefinido {
		@Override
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}         
	}
	public class Null extends Exp {
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		} 
	}
	public class TRef extends TDefinido {
		private String idtipo;
		private DecTipo vinculo;
		private String enlaceFuente;
		public TRef(String idtipo) {
			this(idtipo,null); 
		}       
		public TRef(String idtipo, String enlaceFuente) {
			this.idtipo = idtipo;
			this.enlaceFuente = enlaceFuente;
		}
		public DecTipo declaracion() {
			return vinculo; 
		}
		public void ponDeclaracion(DecTipo decTipo) {
			this.vinculo = decTipo; 
		}
		public String idtipo() {
			return idtipo; 
		}
		public String enlaceFuente() {
			return enlaceFuente; 
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}         
	}


	public class Ok implements Tipo {
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}         
		public String toString() {return "OK";}
	}
	public class Error implements Tipo {
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}         
		public String toString() {return "ERROR";}
	}

	public class Prog {
		private Dec[] decs;
		private Inst i;
		private Tipo tipo;
		public Prog(Dec[] decs, Inst i) {
			this.decs = decs;
			this.i = i;
			this.tipo = null;
		}
		public Dec[] decs() {return decs;}
		public Inst inst() {return i;}
		public Tipo tipo() {return tipo;}
		public void ponTipo(Tipo tipo) {this.tipo = tipo;}
		public void procesaCon(Procesamiento p) {
			p.procesa(this);
		}
	}


	public abstract class Dec {
		public abstract void procesaCon(Procesamiento p);      
	}

	public class DecVar extends Dec {
		private String enlaceFuente;
		private String var;
		private TDefinido tipoDec;
		private int dir;
		private int nivel;
		public DecVar(TDefinido tipo,String var) {
			this(tipo,var,null);
		}
		public DecVar(TDefinido tipo,String var, String enlaceFuente) {
			this.tipoDec = tipo;  
			this.enlaceFuente = enlaceFuente;
			this.var= var;
		}
		public TDefinido tipoDec() {return tipoDec;}
		public String var() {
			return var;
		}
		public int dir() {return dir;}
		public void ponDir(int dir) {this.dir = dir;}
		public String enlaceFuente() {
			return enlaceFuente;  
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this);
		}
		public boolean esParametroPorReferencia() {
			return false;
		}

		public int nivel() {
			return nivel;
		}

		public void ponNivel(int nivel) {
			this.nivel = nivel;
		}
	}

	public class DecTipo extends Dec {
		private String enlaceFuente;
		private String idtipo;
		private TDefinido tipoDec;
		public DecTipo(TDefinido tipo,String idtipo) {
			this(tipo,idtipo,null);
		}
		public DecTipo(TDefinido tipo,String idtipo, String enlaceFuente) {
			this.tipoDec = tipo;  
			this.enlaceFuente = enlaceFuente;
			this.idtipo= idtipo;
		}
		public TDefinido tipoDec() {return tipoDec;}
		public String idtipo() {
			return idtipo;
		}
		public String enlaceFuente() {
			return enlaceFuente;  
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this);
		}
	}


	public abstract class Inst  {
		private Tipo tipo;
		private int dirPrimeraInstruccion;
		private int dirInstruccionSiguiente;
		public Inst() {
			tipo = null;   
		}
		public Tipo tipo() {return tipo;}
		public void ponTipo(Tipo tipo) {this.tipo = tipo;}
		public int dirPrimeraInstruccion() {
			return dirPrimeraInstruccion;
		}
		public void ponDirPrimeraInstruccion(int dir) {
			dirPrimeraInstruccion = dir;
		}
		public int dirInstruccionSiguiente() {
			return dirInstruccionSiguiente;
		}
		public void ponDirInstruccionSiguiente(int dir) {
			dirInstruccionSiguiente = dir;
		}     
		public abstract void procesaCon(Procesamiento p); 
	}


	public class IAsig extends Inst {
		private Mem mem;
		private Exp exp;
		private String enlaceFuente;
		public IAsig(Mem mem, Exp exp, String enlaceFuente) {
			this.mem = mem;
			this.exp = exp;
			this.enlaceFuente = enlaceFuente;
		}
		public IAsig(Mem mem, Exp exp) {
			this(mem,exp,null); 
		}
		public Mem mem() {return mem;}
		public Exp exp() {return exp;}
		public String enlaceFuente() {
			return enlaceFuente;
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this);
		}
	}   

	public class IBloque extends Inst {
		private Inst[] is;
		private Dec[] decs;
		public IBloque(Dec[] decs, Inst[] is) {
			this.decs = decs; 
			this.is = is;
		}
		public Inst[] is() {return is;}
		public Dec[] decs() {
			return decs;
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this);
		}
	}   

	public class IWhile extends Inst {
		private Exp exp;
		private Inst cuerpo;
		private String enlaceFuente;
		public IWhile(Exp exp, Inst cuerpo) {
			this(exp,cuerpo,null); 
		}
		public IWhile(Exp exp, Inst cuerpo, String enlaceFuente) {
			this.exp = exp;
			this.cuerpo = cuerpo;
			this.enlaceFuente = enlaceFuente;
		}
		public Exp exp() {return exp;}
		public Inst cuerpo() {return cuerpo;}
		public String enlaceFuente() {return enlaceFuente;}

		public void procesaCon(Procesamiento p) {
			p.procesa(this);
		}
	}   

	public class IIf extends Inst {
		private Exp exp;
		private Inst cuerpo;
		private String enlaceFuente;
		public IIf(Exp exp, Inst cuerpo) {
			this(exp,cuerpo,null); 
		}
		public IIf(Exp exp, Inst cuerpo, String enlaceFuente) {
			this.exp = exp;
			this.cuerpo = cuerpo;
			this.enlaceFuente = enlaceFuente;
		}
		public Exp exp() {return exp;}
		public Inst cuerpo() {return cuerpo;}
		public String enlaceFuente() {return enlaceFuente;}

		public void procesaCon(Procesamiento p) {
			p.procesa(this);
		}
	}

	public class IIfElse extends Inst {
		private Exp exp;
		private Inst cuerpo1;
		private Inst cuerpo2;
		private String enlaceFuente;
		public IIfElse(Exp exp, Inst cuerpo1, Inst cuerpo2) {
			this(exp,cuerpo1,cuerpo2,null); 
		}
		public IIfElse(Exp exp, Inst cuerpo1, Inst cuerpo2, String enlaceFuente) {
			this.exp = exp;
			this.cuerpo1 = cuerpo1;
			this.cuerpo2 = cuerpo2;
			this.enlaceFuente = enlaceFuente;
		}
		public Exp exp() {return this.exp;}
		public Inst cuerpo1() {return this.cuerpo1;}
		public Inst cuerpo2() {return this.cuerpo2;}
		public String enlaceFuente() {return this.enlaceFuente;}

		public void procesaCon(Procesamiento p) {
			p.procesa(this);
		}
	}
	public class Case {
		private Exp val;
		private Inst inst;

		public Case(Exp val, Inst inst) {
			this.val = val;
			this.inst = inst;
		}

		public Exp getVal() {
			return val;
		}

		public Inst getInst() {
			return inst;
		}
	}
	public class IDoWhile extends Inst {
		private Exp exp;
		private Inst cuerpo;
		private String enlaceFuente;
		public IDoWhile(Exp exp, Inst cuerpo) {
			this(exp,cuerpo,null); 
		}
		public IDoWhile(Exp exp, Inst cuerpo, String enlaceFuente) {
			this.exp = exp;
			this.cuerpo = cuerpo;
			this.enlaceFuente = enlaceFuente;
		}
		public Exp exp() {return exp;}
		public Inst cuerpo() {return cuerpo;}
		public String enlaceFuente() {return enlaceFuente;}

		public void procesaCon(Procesamiento p) {
			p.procesa(this);
		}
	}
	public class ISwitch extends Inst {
		private Exp exp;
		private Case[] cases;
		private Inst defaultCase;
		private String enlaceFuente;

		public ISwitch(Exp exp, Case[] cases, Inst defaultCase){
			this.exp = exp;
			this.cases = cases;
			this.defaultCase = defaultCase;
			this.enlaceFuente = null;
		}
		public ISwitch(Exp exp, Case[] cases, Inst defaultCase, String enlaceFuente){
			this.exp = exp;
			this.cases = cases;
			this.defaultCase = defaultCase;
			this.enlaceFuente = enlaceFuente;
		}

		public Exp exp() {
			return exp;
		}

		public Case[] cases() {
			return cases;
		}

		public Inst defaultCase() {
			return defaultCase;
		}

		public String enlaceFuente() {
			return enlaceFuente;
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this);
		}
	}
	public class INew extends Inst {
		private Mem mem;
		private String enlaceFuente;
		public INew(Mem mem) {
			this(mem,null);  
		}
		public INew(Mem mem, String enlaceFuente) {
			this.mem = mem; 
			this.enlaceFuente = enlaceFuente;
		}
		public Mem mem() {return mem;}
		public String enlaceFuente() {return enlaceFuente;}
		public void enlaceFuente(String enlaceFuente) {this.enlaceFuente = enlaceFuente;}
		public void procesaCon(Procesamiento p) {
			p.procesa(this);
		}
	}

	public class IFree extends Inst {
		private Mem mem;
		private String enlaceFuente;
		public IFree(Mem mem) {
			this(mem,null);  
		}
		public IFree(Mem mem, String enlaceFuente) {
			this.mem = mem; 
			this.enlaceFuente = enlaceFuente;
		}
		public Mem mem() {return mem;}
		public String enlaceFuente() {return enlaceFuente;}
		public void enlaceFuente(String enlaceFuente) {this.enlaceFuente = enlaceFuente;}
		public void procesaCon(Procesamiento p) {
			p.procesa(this);
		}
	}
	public class ICall extends Inst {
		private String enlaceFuente;
		private String idproc;
		private Exp[] aparams;
		private DecProc decProc;

		public ICall(String idproc, Exp[] aparams) {
			this(null, aparams, idproc);
		}

		public ICall(String idproc, Exp[] aparams, String enlaceFuente) {
			this.enlaceFuente = enlaceFuente;
			this.idproc = idproc;
			this.aparams = aparams;
		}

		public String enlaceFuente() {
			return enlaceFuente;
		}

		public String idproc() {
			return idproc;
		}

		public Exp[] aparams() {
			return aparams;
		}

		public DecProc declaracion() {
			return decProc;
		}

		public void ponDeclaracion(DecProc dec) {
			this.decProc = dec;
		}

		public void procesaCon(Procesamiento p) {
			p.procesa(this);
		}
	}

	public class DecProc extends Dec {
		private String enlaceFuente;
		private String idproc;
		private FParam[] fparams;
		private Inst cuerpo;
		private int nivel;
		private int tamanio;

		public DecProc(String idproc, FParam[] fparams, Inst cuerpo) {
			this(null, idproc, fparams, cuerpo);
		}

		public DecProc(String enlaceFuente, String idproc, FParam[] fparams,
				Inst cuerpo) {
			this.enlaceFuente = enlaceFuente;
			this.idproc = idproc;
			this.fparams = fparams;
			this.cuerpo = cuerpo;
		}

		public String enlaceFuente() {
			return enlaceFuente;
		}

		public String idproc() {
			return idproc;
		}

		public FParam[] fparams() {
			return fparams;
		}

		public Inst cuerpo() {
			return cuerpo;
		}

		public void procesaCon(Procesamiento p) {
			p.procesa(this);
		}

		public int nivel() {
			return nivel;
		}

		public int tamanio() {
			return tamanio;
		}

		public void ponNivel(int nivel) {
			this.nivel = nivel;
		}

		public void ponTamanio(int tam) {
			this.tamanio = tam;
		}
	}

	public class FParam extends DecVar {
		private boolean porReferencia;

		public FParam(String id, TDefinido tipo, boolean porReferencia) {
			this(null, id, tipo, porReferencia);
		}

		public FParam(String enlaceFuente, String id, TDefinido tipo,
				boolean porReferencia) {
			super(tipo, id, enlaceFuente);
			this.porReferencia = porReferencia;
		}

		public boolean esParametroPorReferencia() {
			return porReferencia;
		}
	}

	public abstract class Exp {
		private Tipo tipo;
		private int dirPrimeraInstruccion;
		private int dirInstruccionSiguiente;
		public Exp() {
			tipo = null;
		}
		public void ponTipo(Tipo tipo) {
			this.tipo = I.tipoBase(tipo);  
		}
		public Tipo tipo() {
			return tipo;  
		}
		public int dirPrimeraInstruccion() {
			return dirPrimeraInstruccion;
		}
		public void ponDirPrimeraInstruccion(int dir) {
			dirPrimeraInstruccion = dir;
		}
		public int dirInstruccionSiguiente() {
			return dirInstruccionSiguiente;
		}
		public void ponDirInstruccionSiguiente(int dir) {
			dirInstruccionSiguiente = dir;
		}
		public abstract void procesaCon(Procesamiento p); 
		public boolean esMen() {return false;}
	}

	public abstract class Mem extends Exp {
		private String enlaceFuente;
		public Mem(String enlaceFuente) {
			this.enlaceFuente = enlaceFuente; 
		}
		public String enlaceFuente() {
			return enlaceFuente;  
		}
		public boolean esMen() {return true;}
	}

	public class DRef extends Mem {
		private Mem mem;
		public DRef(Mem mem) {
			this(mem,null);  
		}
		public DRef(Mem mem, String enlaceFuente) {
			super(enlaceFuente);
			this.mem = mem;
		}
		public Mem mem() {
			return mem;  
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}       
	}

	public class Select extends Mem {
		private Mem reg;
		private String campo;
		public Select(Mem reg, String campo) {
			this(reg, campo, null);
		}
		public Select(Mem reg, String campo, String enlaceFuente) {
			super(enlaceFuente);
			this.reg = reg;
			this.campo = campo;
		}
		public Mem registro() {
			return reg;
		}
		public String campo() {
			return campo;
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this);
		}

	}

	public class Index extends Mem {
		private Mem buffer;
		private Exp index;
		public Index(Mem buffer, Exp index) {
			super(null);
			this.buffer = buffer;
			this.index = index;
		}
		public Index(Mem buffer, Exp index, String enlaceFuente) {
			super(enlaceFuente);
			this.buffer = buffer;
			this.index = index;
		}
		public Exp index() {
			return index;
		}
		public Mem buffer() {
			return buffer;
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this);
		}
	}

	public class Var extends Mem {
		private String var;
		private DecVar declaracion;

		public Var(String var) {
			this(var,null);  
		}
		public Var(String var, String enlaceFuente) {
			super(enlaceFuente);  
			this.var = var;
			declaracion = null;
		}
		public String var() {return var;}
		public DecVar declaracion() {return declaracion;}
		public void ponDeclaracion(DecVar dec) {
			declaracion = dec;
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}
	}
	public class CteInt extends Exp {
		private int val;
		public CteInt(int val) {
			this.val = val;
		}
		public int valEntero() {return val;}   
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}
	}
	public class CteBool extends Exp {
		private boolean val;
		public CteBool(boolean val) {
			this.val = val;
		}
		public boolean valBool() {return val;}      
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}
	}
	public class CteReal extends Exp {
		private double val;
		public CteReal(double val) {
			this.val = val;
		}
		public double valReal() {return val;}      
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}
	}
	public class CteChar extends Exp {
		private char val;
		public CteChar(char val) {
			this.val = val;
		}
		public char valChar() {return val;}      
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}
	}
	public class CteString extends Exp {
		private String val;
		public CteString(String val) {
			this.val = val;
		}
		public String valString() {return val;}      
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}
	}
	private abstract class ExpBin extends Exp {
		private Exp opnd1;
		private Exp opnd2;
		private String enlaceFuente;
		public ExpBin(Exp opnd1, Exp opnd2) {
			this(opnd1,opnd2,null);
		}
		public ExpBin(Exp opnd1, Exp opnd2,String enlaceFuente) {
			this.enlaceFuente = enlaceFuente; 
			this.opnd1 = opnd1;
			this.opnd2 = opnd2;
		}
		public Exp opnd1() {return opnd1;}
		public Exp opnd2() {return opnd2;}
		public String enlaceFuente() {return enlaceFuente;}
	}

	private abstract class ExpUn extends Exp {
		private Exp opnd1;
		private String enlaceFuente;
		public ExpUn(Exp opnd1) {
			this(opnd1,null);
		}
		public ExpUn(Exp opnd1, String enlaceFuente) {
			this.enlaceFuente = enlaceFuente; 
			this.opnd1 = opnd1;
		}
		public Exp opnd1() {return opnd1;}
		public String enlaceFuente() {return enlaceFuente;}
	}

	public class Suma extends ExpBin {
		public Suma(Exp opnd1, Exp opnd2) {
			super(opnd1,opnd2,null);
		}
		public Suma(Exp opnd1, Exp opnd2,String enlaceFuente) {
			super(opnd1,opnd2,enlaceFuente);  
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}
	}
	public class Resta extends ExpBin {
		public Resta(Exp opnd1, Exp opnd2) {
			super(opnd1,opnd2,null);
		}
		public Resta(Exp opnd1, Exp opnd2,String enlaceFuente) {
			super(opnd1,opnd2,enlaceFuente);  
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}
	}

	public class Multi extends ExpBin {
		public Multi(Exp opnd1, Exp opnd2) {
			super(opnd1,opnd2,null);
		}
		public Multi(Exp opnd1, Exp opnd2,String enlaceFuente) {
			super(opnd1,opnd2,enlaceFuente);  
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}
	}

	public class Divi extends ExpBin {
		public Divi(Exp opnd1, Exp opnd2) {
			super(opnd1,opnd2,null);
		}
		public Divi(Exp opnd1, Exp opnd2,String enlaceFuente) {
			super(opnd1,opnd2,enlaceFuente);  
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}
	}

	public class RestInt extends ExpBin {
		public RestInt(Exp opnd1, Exp opnd2) {
			super(opnd1,opnd2,null);
		}
		public RestInt(Exp opnd1, Exp opnd2,String enlaceFuente) {
			super(opnd1,opnd2,enlaceFuente);  
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}
	}

	public class CambiaSign extends ExpUn {
		public CambiaSign(Exp opnd1) {
			super(opnd1,null);
		}
		public CambiaSign(Exp opnd1, String enlaceFuente) {
			super(opnd1, enlaceFuente);  
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}
	}

	public class ElementoCadena extends ExpBin {
		public ElementoCadena(Exp opnd1, Exp opnd2) {
			super(opnd1,opnd2,null);
		}
		public ElementoCadena(Exp opnd1, Exp opnd2, String enlaceFuente) {
			super(opnd1,opnd2,enlaceFuente);  
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}
	}

	public class ConversionInt extends ExpUn {
		public ConversionInt(Exp opnd1) {
			super(opnd1,null);
		}
		public ConversionInt(Exp opnd1, String enlaceFuente) {
			super(opnd1,enlaceFuente);  
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}
	}

	public class ConversionReal extends ExpUn {
		public ConversionReal(Exp opnd1) {
			super(opnd1,null);
		}
		public ConversionReal(Exp opnd1, String enlaceFuente) {
			super(opnd1,enlaceFuente);  
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}
	}

	public class ConversionChar extends ExpUn {
		public ConversionChar(Exp opnd1) {
			super(opnd1,null);
		}
		public ConversionChar(Exp opnd1, String enlaceFuente) {
			super(opnd1,enlaceFuente);  
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}
	}

	public class ConversionBool extends ExpUn {
		public ConversionBool(Exp opnd1) {
			super(opnd1,null);
		}
		public ConversionBool(Exp opnd1, String enlaceFuente) {
			super(opnd1,enlaceFuente);  
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}
	}

	public class ConversionString extends ExpUn {
		public ConversionString(Exp opnd1) {
			super(opnd1,null);
		}
		public ConversionString(Exp opnd1, String enlaceFuente) {
			super(opnd1,enlaceFuente);  
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}
	}

	public class Igual extends ExpBin {
		public Igual(Exp opnd1, Exp opnd2) {
			super(opnd1,opnd2,null);
		}
		public Igual(Exp opnd1, Exp opnd2, String enlaceFuente) {
			super(opnd1,opnd2,enlaceFuente);  
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}
	}

	public class Mayor extends ExpBin {
		public Mayor(Exp opnd1, Exp opnd2) {
			super(opnd1,opnd2,null);
		}
		public Mayor(Exp opnd1, Exp opnd2, String enlaceFuente) {
			super(opnd1,opnd2,enlaceFuente);  
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}
	}

	public class Menor extends ExpBin {
		public Menor(Exp opnd1, Exp opnd2) {
			super(opnd1,opnd2,null);
		}
		public Menor(Exp opnd1, Exp opnd2, String enlaceFuente) {
			super(opnd1,opnd2,enlaceFuente);  
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}
	}

	public class MayorIgual extends ExpBin {
		public MayorIgual(Exp opnd1, Exp opnd2) {
			super(opnd1,opnd2,null);
		}
		public MayorIgual(Exp opnd1, Exp opnd2, String enlaceFuente) {
			super(opnd1,opnd2,enlaceFuente);  
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}
	}

	public class MenorIgual extends ExpBin {
		public MenorIgual(Exp opnd1, Exp opnd2) {
			super(opnd1,opnd2,null);
		}
		public MenorIgual(Exp opnd1, Exp opnd2, String enlaceFuente) {
			super(opnd1,opnd2,enlaceFuente);  
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}
	}

	public class Distinto extends ExpBin {
		public Distinto(Exp opnd1, Exp opnd2) {
			super(opnd1,opnd2,null);
		}
		public Distinto(Exp opnd1, Exp opnd2, String enlaceFuente) {
			super(opnd1,opnd2,enlaceFuente);  
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}
	}

	public class And extends ExpBin {
		public And(Exp opnd1, Exp opnd2) {
			super(opnd1,opnd2,null);
		}
		public And(Exp opnd1, Exp opnd2, String enlaceFuente) {
			super(opnd1,opnd2,enlaceFuente);  
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}
	}

	public class Or extends ExpBin {
		public Or(Exp opnd1, Exp opnd2) {
			super(opnd1,opnd2,null);
		}
		public Or(Exp opnd1, Exp opnd2, String enlaceFuente) {
			super(opnd1,opnd2,enlaceFuente);  
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}
	}

	public class Not extends ExpUn {
		public Not(Exp opnd1) {
			super(opnd1,null);
		}
		public Not(Exp opnd1,String enlaceFuente) {
			super(opnd1,enlaceFuente);  
		}
		public void procesaCon(Procesamiento p) {
			p.procesa(this); 
		}
	}

	public class Lee extends Inst {
		private Exp var;
		private String enlaceFuente;

		public Lee(Exp var, String enlaceFuente) {
			this.var = var;
			this.enlaceFuente = enlaceFuente;
		}

		public Lee(Exp exp) {
			this(exp, null);
		}

		public Exp var() {
			return var;
		}

		public String enlaceFuente() {
			return enlaceFuente;
		}

		public void procesaCon(Procesamiento p) {
			p.procesa(this);
		}
	}

	public class Escribe extends Inst {
		private Exp exp;
		private String enlaceFuente;

		public Escribe(Exp exp, String enlaceFuente) {
			this.exp = exp;
			this.enlaceFuente = enlaceFuente;
		}

		public Escribe(Exp opnd) {
			this(opnd, null);
		}

		public Exp exp() {
			return exp;
		}

		public String enlaceFuente() {
			return enlaceFuente;
		}

		public void procesaCon(Procesamiento p) {
			p.procesa(this);
		}
	}

	public Prog prog(Dec[] decs, Inst i) {
		return new Prog(decs, i);  
	}
	public Dec decvar(TDefinido t, String v) {
		return new DecVar(t, v);  
	}
	public Dec decvar(TDefinido t, String v, String enlaceFuente) {
		return new DecVar(t, v, enlaceFuente);  
	}
	public Dec dectipo(TDefinido t, String v) {
		return new DecTipo(t, v);  
	}
	public Dec dectipo(TDefinido t, String v, String enlaceFuente) {
		return new DecTipo(t, v, enlaceFuente);  
	}
	public Inst iasig(Mem m, Exp e) {
		return new IAsig(m, e);  
	} 
	public Inst iasig(Mem m, Exp e, String enlaceFuente) {
		return new IAsig(m, e, enlaceFuente);  
	}
	public Inst ibloque(Dec[] decs, Inst[] is) {
		return new IBloque(decs, is);  
	}
	public Inst iwhile(Exp exp, Inst cuerpo) {
		return new IWhile(exp, cuerpo);  
	}
	public Inst iwhile(Exp exp, Inst cuerpo, String enlaceFuente) {
		return new IWhile(exp, cuerpo, enlaceFuente);  
	}
	public Inst idowhile(Exp exp, Inst cuerpo) {
		return new IDoWhile(exp, cuerpo);
	}
	public Inst idowhile(Exp exp, Inst cuerpo, String enlaceFuente) {
		return new IDoWhile(exp, cuerpo, enlaceFuente);
	}
	public Inst iif(Exp exp, Inst cuerpo) {
		return new IIf(exp, cuerpo);
	}
	public Inst iif(Exp exp, Inst cuerpo, String enlaceFuente) {
		return new IIf(exp, cuerpo, enlaceFuente);
	}
	public Inst iifelse(Exp exp, Inst cuerpo1, Inst cuerpo2) {
		return new IIfElse(exp, cuerpo1, cuerpo2);
	}
	public Inst iifelse(Exp exp, Inst cuerpo1, Inst cuerpo2, String enlaceFuente) {
		return new IIfElse(exp, cuerpo1, cuerpo2, enlaceFuente);
	}
	public Case icase(Exp val, Inst inst) {
		return new Case(val, inst);
	}
	public Inst iswitch(Exp exp, Case[] cases, Inst defaultCase) {
		return new ISwitch(exp, cases, defaultCase);
	}
	public Inst iswitch(Exp exp, Case[] cases, Inst defaultCase, String enlaceFuente) {
		return new ISwitch(exp, cases, defaultCase, enlaceFuente);
	}
	public Inst inew(Mem mem) {
		return new INew(mem);  
	}
	public Inst inew(Mem mem, String enlaceFuente) {
		return new INew(mem, enlaceFuente);  
	}
	public Inst ifree(Mem mem) {
		return new IFree(mem);  
	}
	public Inst ifree(Mem mem, String enlaceFuente) {
		return new IFree(mem, enlaceFuente);  
	}
	public Inst icall(String idproc, Exp[] aparams) {
		return new ICall(idproc, aparams);
	}
	public Inst icall(String idproc, Exp[] aparams, String enlaceFuente) {
		return new ICall(idproc, aparams, enlaceFuente);
	}
	public Mem var(String id) {
		return new Var(id);  
	}
	public Mem var(String id, String enlaceFuente) {
		return new Var(id, enlaceFuente);  
	}
	public Mem dref(Mem m) {
		return new DRef(m);
	}
	public Mem dref(Mem m, String enlaceFuente) {
		return new DRef(m, enlaceFuente);
	}
	public Index index(Mem buffer, Exp i) {
		return new Index(buffer, i);
	}
	public Index index(Mem buffer, Exp i, String enlaceFuente) {
		return new Index(buffer, i, enlaceFuente);
	}
	public Select select(Mem registro, String campo) {
		return new Select(registro, campo);
	}
	public Select select(Mem registro, String campo, String enlaceFuente) {
		return new Select(registro, campo, enlaceFuente);
	}
	public Dec decProc(String id, FParam[] fparams, Inst i, String enlaceFuente) {
		return new DecProc(enlaceFuente, id, fparams, i);
	}
	public FParam fparam(TDefinido tipo, boolean porReferencia, String id, String enlaceFuente) {
		return new FParam(enlaceFuente, id, tipo, porReferencia);
	}
	public Exp cteint(int val) {
		return new CteInt(val);  
	}
	public Exp ctebool(boolean val) {
		return new CteBool(val);  
	}
	public Exp ctereal(double val) {
		return new CteReal(val);  
	}
	public Exp ctechar(char val) {
		return new CteChar(val);  
	}
	public Exp ctestring(String val) {
		return new CteString(val);  
	}
	public Exp ctenull(){
		return new Null();
	}
	public TDefinido tnull() {
		return new TNull();
	}
	public Exp suma(Exp exp1, Exp exp2) {
		return new Suma(exp1, exp2);  
	}
	public Exp multi(Exp exp1, Exp exp2) {
		return new Multi(exp1, exp2);  
	}
	public Exp divi(Exp exp1, Exp exp2) {
		return new Divi(exp1, exp2);  
	}
	public Exp restint(Exp exp1, Exp exp2) {
		return new RestInt(exp1, exp2);  
	}
	public Exp cambiasign(Exp exp1) {
		return new CambiaSign(exp1);  
	}
	public Exp elementocadena(Exp exp1, Exp exp2) {
		return new ElementoCadena(exp1, exp2);  
	}
	public Exp conversionint(Exp exp1){
		return new ConversionInt(exp1);
	}
	public Exp conversionreal(Exp exp1){
		return new ConversionReal(exp1);
	}
	public Exp conversionchar(Exp exp1){
		return new ConversionChar(exp1);
	}
	public Exp convertirabool(Exp exp1){
		return new ConversionBool(exp1);
	}
	public Exp convertirastring(Exp exp1){
		return new ConversionString(exp1);
	}
	public Exp igual(Exp exp1, Exp exp2) {
		return new Igual(exp1, exp2);  
	}
	public Exp mayor(Exp exp1, Exp exp2) {
		return new Mayor(exp1, exp2);  
	}
	public Exp menor(Exp exp1, Exp exp2) {
		return new Menor(exp1, exp2);  
	}
	public Exp mayorigual(Exp exp1, Exp exp2) {
		return new MayorIgual(exp1, exp2);  
	}
	public Exp menorigual(Exp exp1, Exp exp2) {
		return new MenorIgual(exp1, exp2);  
	}
	public Exp distinto(Exp exp1, Exp exp2) {
		return new Distinto(exp1, exp2);  
	}
	public Exp and(Exp exp1, Exp exp2) {
		return new And(exp1, exp2);  
	}
	public Exp or(Exp exp1, Exp exp2) {
		return new Or(exp1, exp2);  
	}
	public Exp not(Exp exp1) {
		return new Not(exp1);  
	}
	public Inst lee(Exp exp1) {
		return new Lee(exp1);  
	}
	public Inst iescribe(Exp exp1) {
		return new Escribe(exp1);
	}
	public Exp resta(Exp exp1, Exp exp2) {
		return new Resta(exp1, exp2);  
	}
	public Exp suma(Exp exp1, Exp exp2, String enlaceFuente) {
		return new Suma(exp1, exp2, enlaceFuente);  
	}
	public Exp resta(Exp exp1, Exp exp2, String enlaceFuente) {
		return new Resta(exp1, exp2, enlaceFuente);  
	}
	public Exp multi(Exp exp1, Exp exp2, String enlaceFuente) {
		return new Multi(exp1, exp2, enlaceFuente);  
	}
	public Exp divi(Exp exp1, Exp exp2, String enlaceFuente) {
		return new Divi(exp1, exp2, enlaceFuente);  
	}
	public Exp restint(Exp exp1, Exp exp2, String enlaceFuente) {
		return new RestInt(exp1, exp2, enlaceFuente);  
	}
	public Exp cambiasign(Exp exp1, String enlaceFuente) {
		return new CambiaSign(exp1, enlaceFuente);  
	}
	public Exp elementocadena(Exp exp1, Exp exp2, String enlaceFuente) {
		return new ElementoCadena(exp1, exp2, enlaceFuente);  
	} 
	public Exp conversionint(Exp exp1, String enlaceFuente){
		return new ConversionInt(exp1, enlaceFuente);
	}
	public Exp conversionreal(Exp exp1, String enlaceFuente){
		return new ConversionReal(exp1, enlaceFuente);
	}
	public Exp conversionchar(Exp exp1, String enlaceFuente){
		return new ConversionChar(exp1, enlaceFuente);
	}
	public Exp convertirabool(Exp exp1, String enlaceFuente){
		return new ConversionBool(exp1, enlaceFuente);
	}
	public Exp convertirastring(Exp exp1, String enlaceFuente){
		return new ConversionString(exp1, enlaceFuente);
	}
	public Exp igual(Exp exp1, Exp exp2, String enlaceFuente) {
		return new Igual(exp1, exp2, enlaceFuente);  
	}
	public Exp mayor(Exp exp1, Exp exp2, String enlaceFuente) {
		return new Mayor(exp1, exp2, enlaceFuente);  
	}
	public Exp menor(Exp exp1, Exp exp2, String enlaceFuente) {
		return new Menor(exp1, exp2, enlaceFuente);  
	}
	public Exp mayorigual(Exp exp1, Exp exp2, String enlaceFuente) {
		return new MayorIgual(exp1, exp2, enlaceFuente);  
	}
	public Exp menorigual(Exp exp1, Exp exp2, String enlaceFuente) {
		return new MenorIgual(exp1, exp2, enlaceFuente);  
	}
	public Exp distinto(Exp exp1, Exp exp2, String enlaceFuente) {
		return new Distinto(exp1, exp2, enlaceFuente);  
	}
	public Exp and(Exp exp1, Exp exp2, String enlaceFuente) {
		return new And(exp1, exp2, enlaceFuente);  
	}
	public Exp or(Exp exp1, Exp exp2, String enlaceFuente) {
		return new Or(exp1, exp2, enlaceFuente);  
	}
	public Exp not(Exp exp1, String enlaceFuente) {
		return new Not(exp1, enlaceFuente);  
	}
	public Inst lee(Exp exp1, String enlaceFuente) {
		return new Lee(exp1, enlaceFuente);  
	}
	public Inst iescribe(Exp exp1, String enlaceFuente) {
		return new Escribe(exp1, enlaceFuente);  
	}
	public TDefinido tipoInt() {return TENT;}
	public TDefinido tipoBool() {return TBOOL;}
	public TDefinido tipoReal() {return TREAL;}
	public TDefinido tipoChar() {return TCHAR;}
	public TDefinido tipoString() {return TSTRING;}
	public TDefinido tipoNull(){return new TNull();}
	public Tipo tipoOk() {return TOK;}
	public Tipo tipoError() {return TERROR;}
	public TDefinido tipoPointer(TDefinido tbase) {return new TPointer(tbase);}
	public TDefinido tipoArray(int dimension, TDefinido tbase) {return new TArray(tbase, dimension);}
	public TDefinido tipoRegistro(CampoReg[] campos) {return new TRegistro(campos);}
	public TDefinido tipoRegistro(CampoReg[] campos, String enlaceFuente) {return new TRegistro(campos, enlaceFuente);}
	public TDefinido tipoRef(String idtipo) {return new TRef(idtipo);}
	public TDefinido tipoRef(String idtipo, String enlaceFuente) {return new TRef(idtipo,enlaceFuente);}
	public CampoReg campoRegistro(String id, TDefinido tdef) {return new CampoReg(id, tdef);}

	public abstract Prog raiz();

}
