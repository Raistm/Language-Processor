package procesamientos.generacioncodigo;

import java.util.Stack;

import maquinaP.MaquinaP;
import procesamientos.Procesamiento;
import procesamientos.comprobaciontipos.I;
import programa.Programa;
import programa.Programa.CteInt;
import programa.Programa.CteReal;
import programa.Programa.CteString;
import programa.Programa.CteBool;
import programa.Programa.CteChar;
import programa.Programa.Suma;
import programa.Programa.And;
import programa.Programa.CambiaSign;
import programa.Programa.ConversionBool;
import programa.Programa.ConversionChar;
import programa.Programa.ConversionInt;
import programa.Programa.ConversionReal;
import programa.Programa.ConversionString;
import programa.Programa.DRef;
import programa.Programa.Distinto;
import programa.Programa.Divi;
import programa.Programa.Dec;
import programa.Programa.DecProc;
import programa.Programa.DecVar;
import programa.Programa.ElementoCadena;
import programa.Programa.Escribe;
import programa.Programa.Prog;
import programa.Programa.RestInt;
import programa.Programa.Resta;
import programa.Programa.IBloque;
import programa.Programa.IWhile;
import programa.Programa.IIf;
import programa.Programa.IIfElse;
import programa.Programa.IDoWhile;
import programa.Programa.ISwitch;
import programa.Programa.Igual;
import programa.Programa.ICall;
import programa.Programa.Index;
import programa.Programa.Lee;
import programa.Programa.Mayor;
import programa.Programa.MayorIgual;
import programa.Programa.Menor;
import programa.Programa.MenorIgual;
import programa.Programa.Multi;
import programa.Programa.Not;
import programa.Programa.Null;
import programa.Programa.Or;
import programa.Programa.Select;
import programa.Programa.IAsig;
import programa.Programa.IFree;
import programa.Programa.INew;
import programa.Programa.TDefinido;
import programa.Programa.TPointer;
import programa.Programa.Var;


public class GeneracionDeCodigo extends Procesamiento {
	private MaquinaP maquina;
	private Programa prog;
	private Stack<DecProc> procsPendientes;

	public GeneracionDeCodigo(MaquinaP maquina, Programa prog) {
		this.maquina = maquina;
		this.prog = prog;
		procsPendientes = new Stack<DecProc>();
	}
	
	public void procesa(Null n) {
		maquina.addInstruccion(maquina.apilaInt(-1));
	}

	public void procesa(Var exp) {
		DecVar dvar = exp.declaracion();
		if (dvar.nivel() == 0)
			maquina.addInstruccion(maquina.apilaInt(dvar.dir()));
		else {
			maquina.addInstruccion(maquina.apilad(dvar.nivel()));
			maquina.addInstruccion(maquina.apilaInt(dvar.dir()));
			maquina.addInstruccion(maquina.suma());
			if (dvar.esParametroPorReferencia())
				maquina.addInstruccion(maquina.apilaInd());
		}
	}


	public void procesa(DRef exp) {
		exp.mem().procesaCon(this);
		maquina.addInstruccion(maquina.apilaInd());
	}


	public void procesa(CteInt exp) {
		maquina.addInstruccion(maquina.apilaInt(exp.valEntero()));         
	} 
	public void procesa(CteBool exp) {
		maquina.addInstruccion(maquina.apilaBool(exp.valBool()));         
	}
	public void procesa(CteReal exp) {
		maquina.addInstruccion(maquina.apilaReal(exp.valReal()));         
	}
	public void procesa(CteChar exp) {
		maquina.addInstruccion(maquina.apilaChar(exp.valChar()));         
	}
	public void procesa(CteString exp) {
		maquina.addInstruccion(maquina.apilaString(exp.valString()));         
	}
	public void procesa(Suma exp) {
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) maquina.addInstruccion(maquina.apilaInd());
		exp.opnd2().procesaCon(this);
		if (exp.opnd2().esMen()) maquina.addInstruccion(maquina.apilaInd());
		if (exp.tipo().toString() == "REAL")
			maquina.addInstruccion(maquina.sumaReal());
		else if (exp.tipo().toString() == "STRING")
			maquina.addInstruccion(maquina.sumaString());
		else 
			maquina.addInstruccion(maquina.suma());         
	}
	public void procesa(Resta exp) {
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) maquina.addInstruccion(maquina.apilaInd());
		exp.opnd2().procesaCon(this);
		if (exp.opnd2().esMen()) maquina.addInstruccion(maquina.apilaInd());
		if (exp.tipo().toString() == "REAL"){
			maquina.addInstruccion(maquina.restaReal());}
		else 
			maquina.addInstruccion(maquina.resta());         
	} 
	public void procesa(Multi exp) {
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) maquina.addInstruccion(maquina.apilaInd());
		exp.opnd2().procesaCon(this);
		if (exp.opnd2().esMen()) maquina.addInstruccion(maquina.apilaInd());
		if (exp.tipo().toString() == "REAL"){
			maquina.addInstruccion(maquina.multiReal());}
		else 
			maquina.addInstruccion(maquina.multi());         
	} 
	public void procesa(Divi exp) {
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) maquina.addInstruccion(maquina.apilaInd());
		exp.opnd2().procesaCon(this);
		if (exp.opnd2().esMen()) maquina.addInstruccion(maquina.apilaInd());
		if (exp.tipo().toString() == "REAL"){
			maquina.addInstruccion(maquina.diviReal());}
		else 
			maquina.addInstruccion(maquina.divi());         
	} 
	public void procesa(RestInt exp) {
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) maquina.addInstruccion(maquina.apilaInd());
		exp.opnd2().procesaCon(this);
		if (exp.opnd2().esMen()) maquina.addInstruccion(maquina.apilaInd());
		maquina.addInstruccion(maquina.restint());         
	}
	public void procesa(CambiaSign exp) {
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) maquina.addInstruccion(maquina.apilaInd());
		if (exp.tipo().toString() == "REAL"){
			maquina.addInstruccion(maquina.cambiasignReal());}
		else 
			maquina.addInstruccion(maquina.cambiasign());         
	}
	public void procesa(ElementoCadena exp) {
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) maquina.addInstruccion(maquina.apilaInd());
		exp.opnd2().procesaCon(this);
		if (exp.opnd2().esMen()) maquina.addInstruccion(maquina.apilaInd());
		maquina.addInstruccion(maquina.elementocadena());         
	}
	public void procesa(Igual exp) {
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) maquina.addInstruccion(maquina.apilaInd());
		exp.opnd2().procesaCon(this);
		if (exp.opnd2().esMen()) maquina.addInstruccion(maquina.apilaInd());
		if (exp.opnd1().tipo().toString() == "REAL" || exp.opnd2().tipo().toString() == "REAL"){
			maquina.addInstruccion(maquina.igualReal());}
		else if (exp.opnd1().tipo().toString() == "CHAR")
			maquina.addInstruccion(maquina.igualChar());
		else if (exp.opnd1().tipo().toString() == "BOOL")
			maquina.addInstruccion(maquina.igualBool());
		else if (exp.opnd1().tipo().toString() == "STRING")
			maquina.addInstruccion(maquina.igualString());
		else 
			maquina.addInstruccion(maquina.igual());         
	} 
	public void procesa(Mayor exp) {
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) maquina.addInstruccion(maquina.apilaInd());
		exp.opnd2().procesaCon(this);
		if (exp.opnd2().esMen()) maquina.addInstruccion(maquina.apilaInd());
		if (exp.opnd1().tipo().toString() == "REAL" || exp.opnd2().tipo().toString() == "REAL"){
			maquina.addInstruccion(maquina.igualReal());}
		else if (exp.opnd1().tipo().toString() == "CHAR")
			maquina.addInstruccion(maquina.igualChar());
		else if (exp.opnd1().tipo().toString() == "BOOL")
			maquina.addInstruccion(maquina.igualBool());
		else if (exp.opnd1().tipo().toString() == "STRING")
			maquina.addInstruccion(maquina.igualString());
		else 
			maquina.addInstruccion(maquina.igual());         
	}
	public void procesa(Menor exp) {
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) maquina.addInstruccion(maquina.apilaInd());
		exp.opnd2().procesaCon(this);
		if (exp.opnd2().esMen()) maquina.addInstruccion(maquina.apilaInd());
		if (exp.opnd1().tipo().toString() == "REAL" || exp.opnd2().tipo().toString() == "REAL"){
			maquina.addInstruccion(maquina.menorReal());}
		else if (exp.opnd1().tipo().toString() == "CHAR")
			maquina.addInstruccion(maquina.menorChar());
		else if (exp.opnd1().tipo().toString() == "BOOL")
			maquina.addInstruccion(maquina.menorBool());
		else if (exp.opnd1().tipo().toString() == "STRING")
			maquina.addInstruccion(maquina.menorString());
		else 
			maquina.addInstruccion(maquina.menor());         
	}
	public void procesa(MayorIgual exp) {
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) maquina.addInstruccion(maquina.apilaInd());
		exp.opnd2().procesaCon(this);
		if (exp.opnd2().esMen()) maquina.addInstruccion(maquina.apilaInd());
		if (exp.opnd1().tipo().toString() == "REAL" || exp.opnd2().tipo().toString() == "REAL"){
			maquina.addInstruccion(maquina.mayorIgualReal());}
		else if (exp.opnd1().tipo().toString() == "CHAR")
			maquina.addInstruccion(maquina.mayorIgualChar());
		else if (exp.opnd1().tipo().toString() == "BOOL")
			maquina.addInstruccion(maquina.mayorIgualBool());
		else if (exp.opnd1().tipo().toString() == "STRING")
			maquina.addInstruccion(maquina.mayorIgualString());
		else 
			maquina.addInstruccion(maquina.mayorIgual());         
	}
	public void procesa(MenorIgual exp) {
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) maquina.addInstruccion(maquina.apilaInd());
		exp.opnd2().procesaCon(this);
		if (exp.opnd2().esMen()) maquina.addInstruccion(maquina.apilaInd());
		if (exp.opnd1().tipo().toString() == "REAL" || exp.opnd2().tipo().toString() == "REAL"){
			maquina.addInstruccion(maquina.menorIgualReal());}
		else if (exp.opnd1().tipo().toString() == "CHAR")
			maquina.addInstruccion(maquina.menorIgualChar());
		else if (exp.opnd1().tipo().toString() == "BOOL")
			maquina.addInstruccion(maquina.menorIgualBool());
		else if (exp.opnd1().tipo().toString() == "STRING")
			maquina.addInstruccion(maquina.menorIgualString());
		else 
			maquina.addInstruccion(maquina.menorIgual());         
	}
	public void procesa(Distinto exp) {
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) maquina.addInstruccion(maquina.apilaInd());
		exp.opnd2().procesaCon(this);
		if (exp.opnd2().esMen()) maquina.addInstruccion(maquina.apilaInd());
		if (exp.opnd1().tipo().toString() == "REAL" || exp.opnd2().tipo().toString() == "REAL"){
			maquina.addInstruccion(maquina.distintoReal());}
		else if (exp.opnd1().tipo().toString() == "CHAR")
			maquina.addInstruccion(maquina.distintoChar());
		else if (exp.opnd1().tipo().toString() == "BOOL")
			maquina.addInstruccion(maquina.distintoBool());
		else if (exp.opnd1().tipo().toString() == "STRING")
			maquina.addInstruccion(maquina.distintoString());
		else 
			maquina.addInstruccion(maquina.distinto());         
	}
	public void procesa(And exp) {
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) maquina.addInstruccion(maquina.apilaInd());
		exp.opnd2().procesaCon(this);
		if (exp.opnd2().esMen()) maquina.addInstruccion(maquina.apilaInd());
		maquina.addInstruccion(maquina.and());         
	}
	public void procesa(Or exp) {
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) maquina.addInstruccion(maquina.apilaInd());
		exp.opnd2().procesaCon(this);
		if (exp.opnd2().esMen()) maquina.addInstruccion(maquina.apilaInd());
		maquina.addInstruccion(maquina.or());         
	}
	public void procesa(Not exp) {
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) maquina.addInstruccion(maquina.apilaInd());
		maquina.addInstruccion(maquina.not());         
	}
	public void procesa(ConversionInt exp) {
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) maquina.addInstruccion(maquina.apilaInd());
		if (exp.opnd1().tipo().toString() == "REAL"){
			maquina.addInstruccion(maquina.conversionIntR());}
		else if (exp.opnd1().tipo().toString() == "BOOL")
			maquina.addInstruccion(maquina.conversionIntB());
		else if (exp.opnd1().tipo().toString() == "CHAR")
			maquina.addInstruccion(maquina.conversionIntC());        
	}
	public void procesa(ConversionReal exp) {
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) maquina.addInstruccion(maquina.apilaInd());
		if (exp.opnd1().tipo().toString() == "INT"){
			maquina.addInstruccion(maquina.conversionRealI());}
		else if (exp.opnd1().tipo().toString() == "BOOL")
			maquina.addInstruccion(maquina.conversionRealB());
		else if (exp.opnd1().tipo().toString() == "CHAR")
			maquina.addInstruccion(maquina.conversionRealC());
		else 
			maquina.addInstruccion(maquina.conversionReal());         
	}
	public void procesa(ConversionChar exp) {
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) maquina.addInstruccion(maquina.apilaInd());
		maquina.addInstruccion(maquina.conversionChar());         
	}
	public void procesa(ConversionBool exp) {
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) maquina.addInstruccion(maquina.apilaInd());
		maquina.addInstruccion(maquina.conversionaBool());         
	}
	public void procesa(ConversionString exp) {
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) maquina.addInstruccion(maquina.apilaInd());
		maquina.addInstruccion(maquina.conversionaString());         
	}   
	public void procesa(Lee exp) {
		exp.var().procesaCon(this);
		if (exp.var().tipo().equals(prog.tipoInt()))
			maquina.addInstruccion(maquina.leei());
		else if (exp.var().tipo().equals(prog.tipoReal()))
			maquina.addInstruccion(maquina.leer());
		else if (exp.var().tipo().equals(prog.tipoBool()))
			maquina.addInstruccion(maquina.leeb());
		else if (exp.var().tipo().equals(prog.tipoChar()))
			maquina.addInstruccion(maquina.leec());
		else
			maquina.addInstruccion(maquina.lees());
	}
	public void procesa(Escribe exp) {
		exp.exp().procesaCon(this);
		if (exp.exp().esMen())
			maquina.addInstruccion(maquina.apilaInd());

		maquina.addInstruccion(maquina.escribe());
	}
	public void procesa(Prog p) {
	      for(Dec d: p.decs()) { 
	          if (d instanceof DecProc) {
	             procsPendientes.push((DecProc)d);
	          }
	      }    
	      p.inst().procesaCon(this);
	      maquina.addInstruccion(maquina.stop());
	      while (! procsPendientes.isEmpty()) {
	         procsPendientes.pop().procesaCon(this);
	      }
	   }     
	public void procesa(Index i) {
		i.buffer().procesaCon(this);
		i.index().procesaCon(this);
		maquina.addInstruccion(maquina.enrango(I.tarray(i.buffer().tipo()).dimension()));
		if (i.index().esMen())
			maquina.addInstruccion(maquina.apilaInd());
		maquina.addInstruccion(maquina.apilaInt(I.tarray(i.buffer().tipo()).tbase().tamanio()));
		maquina.addInstruccion(maquina.multi());
		maquina.addInstruccion(maquina.suma());
	}
	public void procesa(Select s) {
		s.registro().procesaCon(this);

		int posCampo = I.tregistro(s.registro().tipo()).getIndexByCampo(
				s.campo());
		int dirRelativa = 0;

		for (int i = 0; i < posCampo; i++) {
			dirRelativa += I.tregistro(s.registro().tipo()).getCampos()[i].getTipo().tamanio();
		}

		maquina.addInstruccion(maquina.apilaInt(dirRelativa));
		maquina.addInstruccion(maquina.suma());
	}
	public void procesa(IAsig i) {
		i.mem().procesaCon(this);
		i.exp().procesaCon(this);
		if (i.exp().esMen()) {
			maquina.addInstruccion(maquina.mueve(((TDefinido)i.exp().tipo()).tamanio()));
		}
		else {
			maquina.addInstruccion(maquina.desapilaInd());
		}   
	}        

	public void procesa(IBloque b) {
		for (Dec d : b.decs()) {
			if (d instanceof DecProc) {
				procsPendientes.push((DecProc) d);
			}
		}
		for (Programa.Inst i : b.is())
			i.procesaCon(this);
	}     
	public void procesa(IWhile i) {
		i.exp().procesaCon(this);
		if (i.exp().esMen())
			maquina.addInstruccion(maquina.apilaInd());
		maquina.addInstruccion(maquina.irF(i.dirInstruccionSiguiente()));
		i.cuerpo().procesaCon(this);
		maquina.addInstruccion(maquina.irA(i.dirPrimeraInstruccion()));
	}

	public void procesa(IDoWhile i) {
		i.cuerpo().procesaCon(this);
		i.exp().procesaCon(this);
		if (i.exp().esMen())
			maquina.addInstruccion(maquina.apilaInd());
		maquina.addInstruccion(maquina.irV(i.dirPrimeraInstruccion()));
	}

	public void procesa(IIf i) {
		i.exp().procesaCon(this);
		if (i.exp().esMen())
			maquina.addInstruccion(maquina.apilaInd());
		maquina.addInstruccion(maquina.irF(i.dirInstruccionSiguiente()));
		i.cuerpo().procesaCon(this);
	}

	public void procesa(IIfElse i) {
		i.exp().procesaCon(this);
		if (i.exp().esMen())
			maquina.addInstruccion(maquina.apilaInd());
		maquina.addInstruccion(maquina.irF(i.cuerpo1()
				.dirInstruccionSiguiente() + 1));
		i.cuerpo1().procesaCon(this);
		maquina.addInstruccion(maquina.irA(i.dirInstruccionSiguiente()));
		i.cuerpo2().procesaCon(this);
	}

	public void procesa(ISwitch s) {
		s.exp().procesaCon(this);
		if (s.exp().esMen())
			maquina.addInstruccion(maquina.apilaInd());
		int i;
		for (i = 0; i < s.cases().length - 1; i++) {
			s.cases()[i].getVal().procesaCon(this);
			if (s.exp().tipo().equals(prog.tipoInt())
					|| s.exp().tipo().equals(prog.tipoReal()))
				maquina.addInstruccion(maquina.icaseNum());
			else if (s.exp().tipo().equals(prog.tipoBool()))
				maquina.addInstruccion(maquina.icaseNum());
			else
				maquina.addInstruccion(maquina.icaseChar());

			maquina.addInstruccion(maquina.irF(s.cases()[i + 1].getVal()
					.dirPrimeraInstruccion()));
			s.cases()[i].getInst().procesaCon(this);
			maquina.addInstruccion(maquina.irA(s.dirInstruccionSiguiente()));
		}
		if (i < s.cases().length) {
			s.cases()[i].getVal().procesaCon(this);
			if (s.exp().tipo().equals(prog.tipoInt()))
				maquina.addInstruccion(maquina.igual());
			else if (s.exp().tipo().equals(prog.tipoReal()))
				maquina.addInstruccion(maquina.igualReal());
			else if (s.exp().tipo().equals(prog.tipoBool()))
				maquina.addInstruccion(maquina.igualBool());
			else
				maquina.addInstruccion(maquina.igualChar());

			if (s.defaultCase() != null) {
				maquina.addInstruccion(maquina.irF(s.defaultCase()
						.dirPrimeraInstruccion()));
				s.cases()[i].getInst().procesaCon(this);
				maquina.addInstruccion(maquina.irA(s.dirInstruccionSiguiente()));
			} else {
				maquina.addInstruccion(maquina.irF(s.dirInstruccionSiguiente()));
				s.cases()[i].getInst().procesaCon(this);
			}
		}
		if (s.defaultCase() != null) {
			s.defaultCase().procesaCon(this);
		}
	}

	public void procesa(INew i) {
		i.mem().procesaCon(this);
		maquina.addInstruccion(maquina.alloc(((TPointer)i.mem().tipo()).tbase().tamanio()));
		maquina.addInstruccion(maquina.desapilaInd());
	}
	public void procesa(IFree i) {
		i.mem().procesaCon(this);
		maquina.addInstruccion(maquina.apilaInd());
		maquina.addInstruccion(maquina.dealloc(((TPointer)i.mem().tipo()).tbase().tamanio()));
	}

	public void procesa(ICall c) {
		DecProc p = c.declaracion();
		maquina.addInstruccion(maquina.activa(p.nivel(), p.tamanio(),
				c.dirInstruccionSiguiente()));
		for (int i = 0; i < p.fparams().length; i++) {
			maquina.addInstruccion(maquina.dup());
			maquina.addInstruccion(maquina.apilaInt(p.fparams()[i].dir()));
			maquina.addInstruccion(maquina.suma());
			c.aparams()[i].procesaCon(this);
			if (p.fparams()[i].esParametroPorReferencia()
					|| !c.aparams()[i].esMen()) {
				maquina.addInstruccion(maquina.desapilaInd());
			} else {
				maquina.addInstruccion(maquina.mueve(p.fparams()[i].tipoDec()
						.tamanio()));
			}
		}
		maquina.addInstruccion(maquina.setd(p.nivel()));
		maquina.addInstruccion(maquina.irA(p.cuerpo().dirPrimeraInstruccion()));
	}

	public void procesa(DecProc p) {
		p.cuerpo().procesaCon(this);
		maquina.addInstruccion(maquina.desactiva(p.nivel(), p.tamanio()));
		maquina.addInstruccion(maquina.irInd());
	}
}
