package procesamientos.comprobaciontipos;

import errores.Errores;

import java.util.HashSet;
import java.util.Set;

import procesamientos.Procesamiento;
import programa.Programa.*;

public class Vinculacion extends Procesamiento {
	private final static String ERROR_ID_TIPO_DUPLICADO = "Identificador de tipo ya declarado";
	private final static String ERROR_ID_VAR_DUPLICADO = "Variable ya declarada";
	private final static String ERROR_ID_CAMPO_DUPLICADO = "Campo duplicado en tipo registro";
	private final static String ERROR_ID_CAMPO_NO_DECLARADO = "Campo no declarado en tipo registro";
	private final static String ERROR_ID_TIPO_NO_DECLARADO = "Identificador de tipo no declarado";
	private final static String ERROR_ID_VAR_NO_DECLARADO = "Variable no declarada";
	private final static String ERROR_ID_PROC_DUPLICADO = "Procedimiento ya declarado";
	private static final String ERROR_ID_PROC_NO_DECLARADO = "Procedimiento no declarado";

	private boolean error;
	private Errores errores;
	private TablaDeSimbolos tablaDeSimbolos;
	private final ComplecionRefs CREFS;

	public Vinculacion(Errores errores) {
		tablaDeSimbolos = new TablaDeSimbolos();
		this.errores = errores;
		error = false;
		CREFS = new ComplecionRefs();
	}   
	private class ComplecionRefs extends Procesamiento {
		public void procesa(DecTipo d) {
			d.tipoDec().procesaCon(this);
		}
		public void procesa(DecVar d) {
			d.tipoDec().procesaCon(this);
		}     
		public void procesa(DecProc p) {
			for (FParam param : p.fparams()) {
				param.tipoDec().procesaCon(this);
			}
		}
		public void procesa(TPointer p) {
			if (I.esRef(p.tbase())) {
				procesaRef(I.tref(p.tbase()));
			} 
			else {
				p.tbase().procesaCon(this);
			}
		}
		public void procesa(TArray a) {
			if (I.esRef(a.tbase())) {
				procesaRef(I.tref(a.tbase()));
			} 
			else {
				a.tbase().procesaCon(this);
			}
		}
		public void procesa(TRegistro reg) {
			for (CampoReg campo : reg.getCampos()) {
				if (I.esRef(campo.getTipo())) {
					procesaRef(I.tref(campo.getTipo()));
				} else {
					campo.getTipo().procesaCon(this);
				}
			}
		}
		private void procesaRef(TRef r) {
			DecTipo d = tablaDeSimbolos.decTipo(r.idtipo());
			if (d == null) {
				error = true;
				errores.msg(r.enlaceFuente() + ":" + ERROR_ID_TIPO_NO_DECLARADO
						+ "(" + r.idtipo() + ")");
			} 
			else {
				r.ponDeclaracion(d);
			}
		}
	}   
	
	public void procesa(Prog p) {
		tablaDeSimbolos.creaNivel();
		for (Dec d : p.decs()) d.procesaCon(this);
		for (Dec d : p.decs()) d.procesaCon(CREFS);
		p.inst().procesaCon(this);
	}     

	public void procesa(DecTipo d) {
		if (tablaDeSimbolos.decTipoDuplicada(d.idtipo())) {
			error = true;
			errores.msg(d.enlaceFuente() + ":" + ERROR_ID_TIPO_DUPLICADO + "("
					+ d.idtipo() + ")");
		} 
		else {
			tablaDeSimbolos.ponDecTipo(d.idtipo(), d);
			d.tipoDec().procesaCon(this);
		}
	}
	public void procesa(DecVar d) {
		if (tablaDeSimbolos.decVarDuplicada(d.var())) {
			error = true;
			errores.msg(d.enlaceFuente() + ":" + ERROR_ID_VAR_DUPLICADO + "("
					+ d.var() + ")");
		} 
		else {
			tablaDeSimbolos.ponDecVar(d.var(), d);
			d.tipoDec().procesaCon(this);
		}
	}   
	public void procesa(DecProc d) {
		if (tablaDeSimbolos.decProcDuplicado(d.idproc())) {
			error = true;
			errores.msg(d.enlaceFuente() + ":" + ERROR_ID_PROC_DUPLICADO + "("
					+ d.idproc() + ")");
		} 
		else {
			tablaDeSimbolos.ponDecProc(d.idproc(), d);
			tablaDeSimbolos.creaNivel();
			for (FParam param : d.fparams()) {
				tablaDeSimbolos.ponDecVar(param.var(), param);
				param.tipoDec().procesaCon(this);
			}
			d.cuerpo().procesaCon(this);
			tablaDeSimbolos.destruyeNivel();
		}
	}
	public void procesa(TPointer p) {
		if (! I.esRef(p.tbase())) {
			p.tbase().procesaCon(this);
		}    
	}

	public void procesa(TArray a) {
		if (!I.esRef(a.tbase())) {
			a.tbase().procesaCon(this);
		}
	}
	public void procesa(TRegistro reg) {
		Set<String> regIDs = new HashSet<String>();
		for (CampoReg campo : reg.getCampos()) {
			if (regIDs.contains(campo.getId())) {
				error = true;
				errores.msg(reg.enlaceFuente() + ":" + ERROR_ID_CAMPO_DUPLICADO);
			} else {
				regIDs.add(campo.getId());

				if (!I.esRef(campo.getTipo())) {
					campo.getTipo().procesaCon(this);
				}
			}
		}
	}
	public void procesa(TRef r) {
		DecTipo d = tablaDeSimbolos.decTipo(r.idtipo());
		if (d == null) {
			error = true;
			errores.msg(r.enlaceFuente()+":"+ERROR_ID_TIPO_NO_DECLARADO+"("+r.idtipo()+")");             
		}
		else {
			r.ponDeclaracion(d);
		}
	}
	public void procesa(Select s) {
		s.registro().procesaCon(this);
	}
	public void procesa(Index i) {
		i.buffer().procesaCon(this);
		i.index().procesaCon(this);
	}
	public void procesa(IAsig i) {
		i.mem().procesaCon(this);   
		i.exp().procesaCon(this);
	}     
	public void procesa(INew i) {
		i.mem().procesaCon(this);   
	}     
	public void procesa(IFree i) {
		i.mem().procesaCon(this);   
	}     
	public void procesa(IBloque b) {
		tablaDeSimbolos.creaNivel();
		for (Dec d : b.decs())
			d.procesaCon(this);
		for (Dec d : b.decs())
			d.procesaCon(CREFS);
		for (Inst i : b.is())
			i.procesaCon(this);
		tablaDeSimbolos.destruyeNivel();
	}       
	public void procesa(IIf i) {
		i.exp().procesaCon(this);
		i.cuerpo().procesaCon(this);
	}
	public void procesa(IIfElse i) {
		i.exp().procesaCon(this);
		i.cuerpo1().procesaCon(this);
		i.cuerpo2().procesaCon(this);
	}
	public void procesa(IWhile i) {
		i.exp().procesaCon(this);
		i.cuerpo().procesaCon(this);
	} 
	public void procesa(IDoWhile i){
		i.exp().procesaCon(this);
		i.cuerpo().procesaCon(this);
	}
	public void procesa(ISwitch s) {
		s.exp().procesaCon(this);
		for (Case c : s.cases()) {
			c.getVal().procesaCon(this);
			c.getInst().procesaCon(this);
		}
		if (s.defaultCase() != null)
			s.defaultCase().procesaCon(this);
	}
	public void procesa(ICall c) {
		DecProc proc = tablaDeSimbolos.decProc(c.idproc());
		if (proc == null) {
			error = true;
			errores.msg(c.enlaceFuente() + ":" + ERROR_ID_PROC_NO_DECLARADO
					+ "(" + c.idproc() + ")");
		} else {
			c.ponDeclaracion(proc);
		}
		for (Exp e : c.aparams())
			e.procesaCon(this);
	}
	public boolean error() {
		return error; 
	}
	public void procesa(And exp) {
		exp.opnd1().procesaCon(this);
		exp.opnd2().procesaCon(this);
	}
	public void procesa(Or exp) {
		exp.opnd1().procesaCon(this);
		exp.opnd2().procesaCon(this);
	}
	public void procesa(Not exp) {
		exp.opnd1().procesaCon(this);
	}
	public void procesa(Suma exp) {
		exp.opnd1().procesaCon(this);
		exp.opnd2().procesaCon(this);
	}

	public void procesa(Resta exp) {
		exp.opnd1().procesaCon(this);
		exp.opnd2().procesaCon(this);
	}

	public void procesa(Multi exp) {
		exp.opnd1().procesaCon(this);
		exp.opnd2().procesaCon(this);
	}

	public void procesa(Divi exp) {
		exp.opnd1().procesaCon(this);
		exp.opnd2().procesaCon(this);
	}

	public void procesa(RestInt exp) {
		exp.opnd1().procesaCon(this);
		exp.opnd2().procesaCon(this);
	}

	public void procesa(ElementoCadena exp){
		exp.opnd1().procesaCon(this);
		exp.opnd2().procesaCon(this);
	}

	public void procesa(CambiaSign exp) {
		exp.opnd1().procesaCon(this);
	}

	public void procesa(ConversionInt exp) {
		exp.opnd1().procesaCon(this);
	}

	public void procesa(ConversionReal exp) {
		exp.opnd1().procesaCon(this);
	}

	public void procesa(ConversionChar exp) {
		exp.opnd1().procesaCon(this);
	}

	public void procesa(ConversionBool exp) {
		exp.opnd1().procesaCon(this);
	}

	public void procesa(ConversionString exp) {
		exp.opnd1().procesaCon(this);
	}
	public void procesa(Igual exp) {
		exp.opnd1().procesaCon(this);
		exp.opnd2().procesaCon(this);
	}
	public void procesa(Mayor exp) {
		exp.opnd1().procesaCon(this);
		exp.opnd2().procesaCon(this);
	}
	public void procesa(Menor exp) {
		exp.opnd1().procesaCon(this);
		exp.opnd2().procesaCon(this);
	}
	public void procesa(MayorIgual exp) {
		exp.opnd1().procesaCon(this);
		exp.opnd2().procesaCon(this);
	}
	public void procesa(MenorIgual exp) {
		exp.opnd1().procesaCon(this);
		exp.opnd2().procesaCon(this);
	}
	public void procesa(Distinto exp) {
		exp.opnd1().procesaCon(this);
		exp.opnd2().procesaCon(this);
	}

	public void procesa(Lee exp) { 
		exp.var().procesaCon(this);
	}
	public void procesa(Escribe exp) {
		exp.exp().procesaCon(this);
	}
	public void procesa(DRef d) {
		d.mem().procesaCon(this);
	}
	public void procesa(Var exp) {
		DecVar decVar = tablaDeSimbolos.decVar(exp.var());
		if (decVar == null) {
			error = true;
			errores.msg(exp.enlaceFuente() + ":" + ERROR_ID_VAR_NO_DECLARADO
					+ "(" + exp.var() + ")");
		} else {
			exp.ponDeclaracion(decVar);
		}
	}
}
