package asint;

import errores.Errores;
import programa.Programa;
import java.lang.reflect.Array;

public class ASTOps extends Programa {
	private Errores errores;
	private final String ERROR_DESIG=" Se esperada un designador";
	public ASTOps(Errores errores) {
		this.errores = errores; 
	}
	public abstract static class Lista<T> {
		public Lista<T> todosMenosElUltimo() {throw new UnsupportedOperationException();}
		public T elUltimo() {throw new UnsupportedOperationException();}
		public abstract boolean esVacia();
		public abstract int numElems();
	}
	private static class ListaVacia<T> extends Lista<T> {
		public boolean esVacia() {return true;}
		public int numElems() {return 0;}

	}
	private static class ListaNoVacia<T> extends Lista<T> {
		private Lista<T> todosMenosElUltimo;
		private T elUltimo;
		private int nElems;
		public ListaNoVacia(Lista<T> todosMenosElUltimo, T elUltimo) {
			this.todosMenosElUltimo = todosMenosElUltimo;
			this.elUltimo = elUltimo;
			this.nElems = todosMenosElUltimo.numElems()+1;
		}
		public Lista<T> todosMenosElUltimo() {return todosMenosElUltimo;}
		public T elUltimo() {return elUltimo;}
		public boolean esVacia() {return false;}
		public int numElems() {
			return nElems;
		}
	}
	private <T> T[] toArray(Class<T> c, Lista<T> l) {
		T[] a = (T[]) Array.newInstance(c,l.numElems());
		for (int i=l.numElems()-1; i >=0; i--) {
			a[i] = l.elUltimo();
			l = l.todosMenosElUltimo();
		}
		return a;
	}
	private void chequeaDesig(Exp d, String enlaceFuente) {
		if (! (d.esMen())) {
			errores.msg(enlaceFuente+":"+ERROR_DESIG);
			System.exit(1);
		}
	}

	private Prog raiz;
	public Prog raiz() {return raiz;}
	public Prog prog(Dec[] decs, Inst i) {
		raiz = super.prog(decs, i);
		return raiz;
	}
	public Prog prog(Lista<Dec> decs, Inst i) {
		return prog(toArray(Dec.class, decs),i); 
	}
	public Lista<Dec> nodecs() {
		return new ListaVacia<Dec>();        
	} 
	public Lista<Dec> decs(Lista<Dec> todasMenosLaUltima, Dec laUltima) {
		return new ListaNoVacia<Dec>(todasMenosLaUltima, laUltima);        
	} 
	public Dec decProc(String nombre, Lista<FParam> fparams, Inst cuerpo, 
			String enlaceFuente) {
		return decProc(nombre, toArray(FParam.class, fparams), cuerpo, 
				enlaceFuente); 
	}
	public Lista<FParam> nofparams() {
		return new ListaVacia<FParam>(); 
	}
	public Lista<FParam> fparams(Lista<FParam> todosMenosElUltimo, FParam elUltimo) {
		return new ListaNoVacia<FParam>(todosMenosElUltimo,elUltimo); 
	}    
	public Inst ibloque(Lista<Dec> decs, Lista<Inst> is) {
		return ibloque(toArray(Dec.class, decs), toArray(Inst.class, is));
	}
	public Lista<Inst> noInsts() {
		return new ListaVacia<Inst>(); 
	}
	public Lista<Inst> insts(Lista<Inst> todosMenosLaUltima, Inst laUltima) {
		return new ListaNoVacia<Inst>(todosMenosLaUltima, laUltima); 
	}

	public Inst iasig(Exp l, Exp r,String enlaceFuente) {
		chequeaDesig(l,enlaceFuente);
		return iasig((Mem)l,r,enlaceFuente);
	}

	public Inst inew(Exp d, String enlaceFuente) {
		chequeaDesig(d,enlaceFuente);
		return inew((Mem)d,enlaceFuente);
	}
	public Inst idelete(Exp d,String enlaceFuente) {
		chequeaDesig(d,enlaceFuente);
		return ifree((Mem)d,enlaceFuente);
	}
	public Inst icall(String nombre, Lista<Exp> params, String enlaceFuente) {
		return icall(nombre,toArray(Exp.class,params),enlaceFuente);
	}
	public Lista<Exp> norparams() {
		return new ListaVacia<Exp>();
	}
	public Lista<Exp> rparams(Lista<Exp> todosMenosElUltimo, Exp elUltimo) {
		return new ListaNoVacia(todosMenosElUltimo,elUltimo);
	}
	public Index arrayAccess(Exp m, Exp exp, String enlaceFuente) {
		chequeaDesig(m, enlaceFuente);
		return index((Mem) m, exp, enlaceFuente);
	}

	public Select selField(Exp exp, String campo, String enlaceFuente) {
		chequeaDesig(exp, enlaceFuente);
		return select((Mem) exp, campo, enlaceFuente);
	}

	public Inst iread(Exp exp, String enlaceFuente) {
		chequeaDesig(exp, enlaceFuente);
		return lee(exp);
	}
	public Exp opUn(String op, Exp exp, String enlaceFuente) {
		switch(op) {
			case "*": {
				chequeaDesig(exp,enlaceFuente);
				return dref((Mem)exp,enlaceFuente);
			} 
			case "!": {
				chequeaDesig(exp, enlaceFuente);
				return new Not(exp, enlaceFuente);
			}
			case "-": {
				return cambiasign(exp, enlaceFuente);
			}
			case "int": {
				return conversionint(exp, enlaceFuente);
			}
			case "float": {
				return conversionreal(exp, enlaceFuente);
			}
			case "bool": {
				return convertirabool(exp, enlaceFuente);
			}
			case "char": {
				return conversionchar(exp, enlaceFuente);
			}
			case "string": {
				return convertirastring(exp, enlaceFuente);
			}
		}
		throw new UnsupportedOperationException("opUn("+op+"...)");
	}
	public Exp opBin(String op, Exp opnd0, Exp opnd1,String enlaceFuente) {
		switch (op) {
			case "+": return new Suma(opnd0, opnd1, enlaceFuente);
			case "-": return new Resta(opnd0, opnd1, enlaceFuente);
			case "*": return new Multi(opnd0, opnd1, enlaceFuente);
			case "/": return new Divi(opnd0, opnd1, enlaceFuente);
			case "%": return new RestInt(opnd0, opnd1, enlaceFuente);
			case "_": return new ElementoCadena(opnd0, opnd1, enlaceFuente);
			case "==": return new Igual(opnd0, opnd1, enlaceFuente);
			case ">": return new Mayor(opnd0, opnd1, enlaceFuente);
			case "<": return new Menor(opnd0, opnd1, enlaceFuente);		
			case ">=": return new MayorIgual(opnd0, opnd1, enlaceFuente);
			case "<=": return new MenorIgual(opnd0, opnd1, enlaceFuente);
			case "!=": return new Distinto(opnd0, opnd1, enlaceFuente);
			case "&&": return new And(opnd0, opnd1, enlaceFuente);
			case "||": return new Or(opnd0, opnd1, enlaceFuente);
		}
		throw new UnsupportedOperationException("opBin("+op+"...)");
	}
	public Exp cteInt(String i) {
		return cteint(Integer.valueOf(i).intValue());                
	}    
	public Lista<Case> norcases() {
		return new ListaVacia<Case>();
	}

	public Lista<Case> rcases(Lista<Case> todosMenosElUltimo, Case elUltimo) {
		return new ListaNoVacia(todosMenosElUltimo, elUltimo);
	}

	public Inst iswitch(Exp exp, Lista<Case> cases, Inst defaultCase,
			String enlaceFuente) {
		return iswitch(exp, toArray(Case.class, cases), defaultCase,
				enlaceFuente);
	}

	public Lista<CampoReg> norcampos() {
		return new ListaVacia<CampoReg>();
	}

	public Lista<CampoReg> rcampos(Lista<CampoReg> todosMenosElUltimo,
			CampoReg elUltimo) {
		return new ListaNoVacia(todosMenosElUltimo, elUltimo);
	}

	public TDefinido tipoRegistro(Lista<CampoReg> cases, String enlaceFuente) {
		return tipoRegistro(toArray(CampoReg.class, cases), enlaceFuente);
	}
}
