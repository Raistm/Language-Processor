package procesamientos.generacioncodigo;

import procesamientos.Procesamiento;
import programa.Programa;
import programa.Programa.Bool;
import programa.Programa.CampoReg;
import programa.Programa.Char;
import programa.Programa.DecProc;
import programa.Programa.DecTipo;
import programa.Programa.Prog;
import programa.Programa.DecVar;
import programa.Programa.FParam;
import programa.Programa.IBloque;
import programa.Programa.Int;
import programa.Programa.Prog;
import programa.Programa.Real;
import programa.Programa.StringR;
import programa.Programa.TArray;
import programa.Programa.TNull;
import programa.Programa.TPointer;
import programa.Programa.TRef;
import programa.Programa.TRegistro;


public class AsignacionEspacio extends Procesamiento {
	private int tam;
	private int dir;
	private int nivel;
	private int numDisplays;
	public AsignacionEspacio() {
		dir = 0;
		nivel = 0;
		numDisplays = 0;
	}

	public void procesa(IBloque b) {
		int dirAntesDeBloque = dir;
		for (Programa.Dec d : b.decs())
			d.procesaCon(this);
		int tamCuerpo = 0;
		for (Programa.Inst i : b.is()) {
			tam = 0;
			i.procesaCon(this);
			if (tam > tamCuerpo)
				tamCuerpo = tam;
		}
		tam = tamCuerpo + (dir - dirAntesDeBloque);
		dir = dirAntesDeBloque;
	}
	public void procesa(Prog p) {
		for (Programa.Dec d : p.decs())
			d.procesaCon(this);
		int dirAntesDeInst = dir;
		tam = 0;
		p.inst().procesaCon(this);
		tam += dirAntesDeInst;
	}
	public void procesa(DecVar d) {
		d.tipoDec().procesaCon(this);
		d.ponDir(dir);
		d.ponNivel(nivel);
		if (d.esParametroPorReferencia())
			dir++;
		else
			dir += d.tipoDec().tamanio();
	}
	public void procesa(DecTipo d) {
		if (d.tipoDec().tamanio() == 0) {
			d.tipoDec().procesaCon(this);
		}
	}
	public void procesa(DecProc d) {
		int dirAntesDeProc = dir;
		dir = 0;
		nivel++;
		if (numDisplays < nivel)
			numDisplays = nivel;
		for (FParam p : d.fparams()) {
			p.procesaCon(this);
		}
		int tamParametros = dir;
		d.cuerpo().procesaCon(this);
		d.ponNivel(nivel);
		d.ponTamanio(tam + tamParametros);
		nivel--;
		dir = dirAntesDeProc;
	}
	public void procesa(Int t) {
		if (t.tamanio() == 0)
			t.ponTamanio(1);
	}
	public void procesa(Bool t) {
		if (t.tamanio() == 0)
			t.ponTamanio(1);
	}
	public void procesa(Real t) {
		if (t.tamanio() == 0)
			t.ponTamanio(1);
	}
	public void procesa(StringR t) {
		if (t.tamanio() == 0)
			t.ponTamanio(1);
	}
	public void procesa(Char t) {
		if (t.tamanio() == 0)
			t.ponTamanio(1);
	}
	public void procesa(TPointer t) {
		if (t.tamanio() == 0) {
			t.ponTamanio(1);
			t.tbase().procesaCon(this);
		}
	}
	public void procesa(TArray t) {
		if (t.tamanio() == 0) {
			t.tbase().procesaCon(this);
			t.ponTamanio(t.dimension()*t.tbase().tamanio());
		}
	}
	public void procesa(TNull t) {
		if (t.tamanio() == 0) {
			t.ponTamanio(1);
		}
	}
	public void procesa(TRegistro t) {
		int tmpTam = 0;
		for (CampoReg campo : t.getCampos()) {
			campo.getTipo().procesaCon(this);
			tmpTam += campo.getTipo().tamanio();
		}
		t.ponTamanio(tmpTam);
	}
	public void procesa(TRef r) {
		if (r.tamanio() == 0) {
			r.declaracion().tipoDec().procesaCon(this);
			r.ponTamanio(r.declaracion().tipoDec().tamanio());
		}
	}
	public int tamanioDatos() {return dir;}
	public int numDisplays() {return numDisplays;}
}
