package procesamientos.generacioncodigo;

import java.util.Stack;

import procesamientos.Procesamiento;
import programa.Programa;
import programa.Programa.*;


public class Etiquetado extends Procesamiento {
	private int etq; 
	private Stack<DecProc> procsPendientes;
	public Etiquetado() {
		etq = 0;
		procsPendientes = new Stack<DecProc>();
	}

	public void procesa(Null n) {
		n.ponDirPrimeraInstruccion(etq);
		// apilaInt(-1)
		n.ponDirInstruccionSiguiente(++etq);
	}

	public void procesa(Var exp) {
		DecVar dvar =  exp.declaracion();
		if (dvar.nivel() == 0) 
			etq++; // apilaInt 
		else {
			etq += 3; // apilad, apilaInt, suma 
			if (dvar.esParametroPorReferencia())
				etq++; // apilaInd 
		}
	} 

	public void procesa(DRef exp) {
		exp.ponDirPrimeraInstruccion(etq);
		exp.mem().procesaCon(this);
		// apilaind
		etq++;
		exp.ponDirInstruccionSiguiente(etq);
	}

	public void procesa(CteInt exp) {
		exp.ponDirPrimeraInstruccion(etq);
		// apilaInt(...)
		exp.ponDirInstruccionSiguiente(++etq);
	} 
	public void procesa(CteBool exp) {
		exp.ponDirPrimeraInstruccion(etq);
		// apilaBool(...)
		exp.ponDirInstruccionSiguiente(++etq);
	}

	public void procesa(CteChar exp) {
		exp.ponDirPrimeraInstruccion(etq);
		// apilaChar(...)
		exp.ponDirInstruccionSiguiente(++etq);
	}

	public void procesa(CteReal exp) {
		exp.ponDirPrimeraInstruccion(etq);
		// apilaReal(...)
		exp.ponDirInstruccionSiguiente(++etq);
	}

	public void procesa(CteString exp) {
		exp.ponDirPrimeraInstruccion(etq);
		// apilaString(...)
		exp.ponDirInstruccionSiguiente(++etq);
	}

	public void procesa(ElementoCadena exp) {
		exp.ponDirPrimeraInstruccion(etq);
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen())
			etq++; // apilaInd
		exp.opnd2().procesaCon(this);
		if (exp.opnd2().esMen())
			etq++; // apilaInd
		// elementoCadena
		exp.ponDirInstruccionSiguiente(++etq);
	}

	public void procesa(Suma exp) {
		exp.ponDirPrimeraInstruccion(etq);
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) /* apilaind*/ etq++;
		exp.opnd2().procesaCon(this);
		if (exp.opnd2().esMen()) /* apilaind*/ etq++;
		// suma
		exp.ponDirInstruccionSiguiente(++etq);
	} 
	public void procesa(Resta exp) {
		exp.ponDirPrimeraInstruccion(etq);
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) /* apilaind*/ etq++;
		exp.opnd2().procesaCon(this);
		if (exp.opnd2().esMen()) /* apilaind*/ etq++;
		// resta
		exp.ponDirInstruccionSiguiente(++etq);
	}
	public void procesa(Multi exp) {
		exp.ponDirPrimeraInstruccion(etq);
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) /* apilaind*/ etq++;
		exp.opnd2().procesaCon(this);
		if (exp.opnd2().esMen()) /* apilaind*/ etq++;
		// multi
		exp.ponDirInstruccionSiguiente(++etq);
	}
	public void procesa(Divi exp) {
		exp.ponDirPrimeraInstruccion(etq);
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) /* apilaind*/ etq++;
		exp.opnd2().procesaCon(this);
		if (exp.opnd2().esMen()) /* apilaind*/ etq++;
		// divi
		exp.ponDirInstruccionSiguiente(++etq);
	}
	public void procesa(RestInt exp) {
		exp.ponDirPrimeraInstruccion(etq);
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) /* apilaind*/ etq++;
		exp.opnd2().procesaCon(this);
		if (exp.opnd2().esMen()) /* apilaind*/ etq++;
		// resta entera
		exp.ponDirInstruccionSiguiente(++etq);
	}
	public void procesa(CambiaSign exp) {
		exp.ponDirPrimeraInstruccion(etq);
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) /* apilaind*/ etq++;
		// cambio de signo
		exp.ponDirInstruccionSiguiente(++etq);
	}
	public void procesa(ConversionInt exp) {
		exp.ponDirPrimeraInstruccion(etq);
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) /* apilaind*/ etq++;
		// conversion a entero 
		exp.ponDirInstruccionSiguiente(++etq);
	} 
	public void procesa(ConversionReal  exp) {
		exp.ponDirPrimeraInstruccion(etq);
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) /* apilaind*/ etq++;
		// conversion a real 
		exp.ponDirInstruccionSiguiente(++etq);
	} 
	public void procesa(ConversionChar exp) {
		exp.ponDirPrimeraInstruccion(etq);
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) /* apilaind*/ etq++;
		// conversion a char
		exp.ponDirInstruccionSiguiente(++etq);
	} 
	public void procesa(ConversionBool exp) {
		exp.ponDirPrimeraInstruccion(etq);
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) /* apilaind*/ etq++;
		// conversion a bool 
		exp.ponDirInstruccionSiguiente(++etq);
	} 
	public void procesa(ConversionString exp) {
		exp.ponDirPrimeraInstruccion(etq);
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) /* apilaind*/ etq++;
		// conversion a string  
		exp.ponDirInstruccionSiguiente(++etq);
	}
	public void procesa(And exp) {
		exp.ponDirPrimeraInstruccion(etq);
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) /* apilaind*/ etq++;
		exp.opnd2().procesaCon(this);
		if (exp.opnd2().esMen()) /* apilaind*/ etq++;
		// and
		exp.ponDirInstruccionSiguiente(++etq);
	}   
	public void procesa(Igual exp) {
		exp.ponDirPrimeraInstruccion(etq);
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) /* apilaind*/ etq++;
		exp.opnd2().procesaCon(this);
		if (exp.opnd2().esMen()) /* apilaind*/ etq++;
		// igual 
		exp.ponDirInstruccionSiguiente(++etq);
	} 
	public void procesa(Mayor exp) {
		exp.ponDirPrimeraInstruccion(etq);
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) /* apilaind*/ etq++;
		exp.opnd2().procesaCon(this);
		if (exp.opnd2().esMen()) /* apilaind*/ etq++;
		// mayor 
		exp.ponDirInstruccionSiguiente(++etq);
	}
	public void procesa(Menor exp) {
		exp.ponDirPrimeraInstruccion(etq);
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) /* apilaind*/ etq++;
		exp.opnd2().procesaCon(this);
		if (exp.opnd2().esMen()) /* apilaind*/ etq++;
		// menor 
		exp.ponDirInstruccionSiguiente(++etq);
	} 
	public void procesa(MayorIgual exp) {
		exp.ponDirPrimeraInstruccion(etq);
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) /* apilaind*/ etq++;
		exp.opnd2().procesaCon(this);
		if (exp.opnd2().esMen()) /* apilaind*/ etq++;
		// mayor o igual 
		exp.ponDirInstruccionSiguiente(++etq);
	} 
	public void procesa(MenorIgual exp) {
		exp.ponDirPrimeraInstruccion(etq);
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) /* apilaind*/ etq++;
		exp.opnd2().procesaCon(this);
		if (exp.opnd2().esMen()) /* apilaind*/ etq++;
		// menor o igual 
		exp.ponDirInstruccionSiguiente(++etq);
	} 
	public void procesa(Distinto exp) {
		exp.ponDirPrimeraInstruccion(etq);
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) /* apilaind*/ etq++;
		exp.opnd2().procesaCon(this);
		if (exp.opnd2().esMen()) /* apilaind*/ etq++;
		// distinto 
		exp.ponDirInstruccionSiguiente(++etq);
	} 
	public void procesa(Or exp) {
		exp.ponDirPrimeraInstruccion(etq);
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) /* apilaind*/ etq++;
		exp.opnd2().procesaCon(this);
		if (exp.opnd2().esMen()) /* apilaind*/ etq++;
		// or 
		exp.ponDirInstruccionSiguiente(++etq);
	} 
	public void procesa(Not exp) {
		exp.ponDirPrimeraInstruccion(etq);
		exp.opnd1().procesaCon(this);
		if (exp.opnd1().esMen()) /* apilaind*/ etq++;
		// not 
		exp.ponDirInstruccionSiguiente(++etq);
	} 
	public void procesa(Lee exp) {
		exp.ponDirPrimeraInstruccion(etq);
		exp.var().procesaCon(this);
		// lee
		exp.ponDirInstruccionSiguiente(++etq);
	}
	public void procesa(Escribe exp) {
		exp.ponDirPrimeraInstruccion(etq);
		exp.exp().procesaCon(this);
		if (exp.exp().esMen())
			etq++; // apilaInd
		// escribe
		exp.ponDirInstruccionSiguiente(++etq);
	}
	public void procesa(Prog p) {
		for (Dec d : p.decs()) {
			if (d instanceof DecProc) {
				procsPendientes.push((DecProc) d);
			}
		}
		p.inst().procesaCon(this);
		etq++; // stop
		while (!procsPendientes.isEmpty())
			procsPendientes.pop().procesaCon(this);
	}  
	public void procesa(DecProc p) {
		p.cuerpo().procesaCon(this);
		etq += 2;
	}
	public void procesa(IAsig i) {
		i.ponDirPrimeraInstruccion(etq);
		i.mem().procesaCon(this);
		i.exp().procesaCon(this);
		// desapilaind o mueve
		i.ponDirInstruccionSiguiente(++etq);
	}     
	public void procesa(IBloque b) {
		b.ponDirPrimeraInstruccion(etq);
		for (Dec dec : b.decs())
			if (dec instanceof DecProc)
				procsPendientes.push((DecProc) dec);
		for (Programa.Inst i : b.is())
			i.procesaCon(this);
		b.ponDirInstruccionSiguiente(etq);
	}     
	public void procesa(IWhile i) {
		i.ponDirPrimeraInstruccion(etq);
		i.exp().procesaCon(this);
		// ir_f(...)
		etq++;
		i.cuerpo().procesaCon(this);
		// ir_a(...)
		etq++;
		i.ponDirInstruccionSiguiente(etq);
	}     
	public void procesa(IIf i) {
		i.ponDirPrimeraInstruccion(etq);
		i.exp().procesaCon(this);
		if (i.exp().esMen())
			etq++; //apilaInd
		// ir_f(...)
		etq++;
		i.cuerpo().procesaCon(this);
		i.ponDirInstruccionSiguiente(etq);
	}
	public void procesa(IIfElse i) {
		i.ponDirPrimeraInstruccion(etq);
		i.exp().procesaCon(this);
		if (i.exp().esMen())
			etq++; //apilaInd
		// ir_f(...)
		etq++;
		i.cuerpo1().procesaCon(this);
		// ir_a()
		etq++;
		i.cuerpo2().procesaCon(this);
		i.ponDirInstruccionSiguiente(etq);
	}
	public void procesa(IDoWhile i) {
		i.ponDirPrimeraInstruccion(etq);
		i.cuerpo().procesaCon(this);
		i.exp().procesaCon(this);
		if (i.exp().esMen())
			etq++; //apilaInd	
		// ir_v(...)
		etq++;
		i.ponDirInstruccionSiguiente(etq);
	}
	public void procesa(ISwitch s) {
		s.ponDirPrimeraInstruccion(etq);
		s.exp().procesaCon(this);
		if (s.exp().esMen())
			etq++; //apilaInd
		for (int i = 0; i < s.cases().length; i++) {
			s.cases()[i].getVal().procesaCon(this);
			etq++;
			// ir_f
			etq++;
			s.cases()[i].getInst().procesaCon(this);
			// ir_a
			etq++;
			if (i == s.cases().length - 1 && s.defaultCase() == null)
				etq--;
		}
		if (s.defaultCase() != null) {
			s.defaultCase().procesaCon(this);
		}
		s.ponDirInstruccionSiguiente(etq);
	}
	public void procesa(INew i) {
		i.ponDirPrimeraInstruccion(etq);
		i.mem().procesaCon(this);  
		// alloc desapilaind
		etq +=2;
		i.ponDirInstruccionSiguiente(etq);     
	}
	public void procesa(IFree i) {
		i.ponDirPrimeraInstruccion(etq);
		i.mem().procesaCon(this);  
		// apilaind dealloc 
		etq +=2;
		i.ponDirInstruccionSiguiente(etq);     
	}
	public void procesa(ICall c) {
		c.ponDirPrimeraInstruccion(etq);
		DecProc p = c.declaracion();
		etq++; // activa
		for (int i = 0; i < p.fparams().length; i++) {
			etq += 3; // dup, apilaInt, suma
			c.aparams()[i].procesaCon(this);
			etq++; // desapilaInd/mueve
		}
		etq += 2; // setD, irA
		c.ponDirInstruccionSiguiente(etq);
	}
	public void procesa(Index i) {
		i.buffer().procesaCon(this);
		i.index().procesaCon(this);
		etq++; //enrango(d)
		if (i.index().esMen())
			etq++; //apilaInd
		// apilaint(tamanio_tipobase)
		// mul
		// suma
		etq += 3;
	}
	public void procesa(Select s) {
		s.registro().procesaCon(this);
		// apilaInt(direccion relativa del campo a partir de la incial del
		// registro)
		// suma --> direccion real del campo
		etq += 2;
	}

}
