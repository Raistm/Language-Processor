package procesamientos.comprobaciontipos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import programa.Programa.DecProc;
import programa.Programa.DecTipo;
import programa.Programa.DecVar;

public class TablaDeSimbolos {
	private static class Nivel {
		public Map<String,DecTipo> tipos;
		public Map<String,DecVar> variables;
		public Map<String,DecProc> procedimientos;
		public Nivel() {
			tipos = new HashMap<>();  
			variables = new HashMap<>();
			procedimientos = new HashMap<>();
		}
	}
	private List<Nivel> niveles; 

	public TablaDeSimbolos() {
		niveles = new ArrayList<Nivel>();
	}
	public void ponDecTipo(String tipo, DecTipo dec) {
		niveles.get(0).tipos.put(tipo,dec);
	}  
	public void ponDecVar(String var, DecVar dec) {
		niveles.get(0).variables.put(var, dec);
	} 
	public void ponDecProc(String proc, DecProc dec) {
		niveles.get(0).procedimientos.put(proc,dec);
	}
	public boolean decTipoDuplicada(String tipo) {
		return niveles.get(0).tipos.containsKey(tipo);
	} 
	public boolean decVarDuplicada(String var) {
		return niveles.get(0).variables.containsKey(var);
	} 
	public boolean decProcDuplicado(String proc) {
		return niveles.get(0).variables.containsKey(proc);
	} 
	public DecTipo decTipo(String tipo) {
		Iterator<Nivel> iniveles = niveles.iterator();
		DecTipo dec = null;
		while(dec == null && iniveles.hasNext()) {
			Nivel nivel = iniveles.next();
			dec = nivel.tipos.get(tipo);
		}
		return dec;
	}
	public DecVar decVar(String var) {
		Iterator<Nivel> iniveles = niveles.iterator();
		DecVar dec = null;
		while(dec == null && iniveles.hasNext()) {
			Nivel nivel = iniveles.next();
			dec = nivel.variables.get(var);
		}
		return dec;
	}
	public DecProc decProc(String proc) {
		Iterator<Nivel> iniveles = niveles.iterator();
		DecProc dec = null;
		while(dec == null && iniveles.hasNext()) {
			Nivel nivel = iniveles.next();
			dec = nivel.procedimientos.get(proc);
		}
		return dec;
	}
	public void creaNivel() {
		niveles.add(0,new Nivel());
	}
	public void destruyeNivel() {
		niveles.remove(0);
	}
}

