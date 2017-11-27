package maquinaP;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class MaquinaP {
	private final static String W_ACCESO = "**** WARNING: Acceso a memoria sin inicializar";
	private final Valor UNKNOWN;

	public static class EAccesoIlegitimo extends RuntimeException {}
	public static class EAccesoAMemoriaNoInicializada extends RuntimeException {}
	public static class EAccesoFueraDeRango extends RuntimeException {}

	private GestorMemoriaDinamica gestorMemoriaDinamica;
	private GestorPilaActivaciones gestorPilaActivaciones;

	private class Valor {
		public int valorInt() {throw new EAccesoIlegitimo();}
		public boolean valorBool() {throw new EAccesoIlegitimo();}
		public double valorReal() {throw new EAccesoIlegitimo();}
		public char valorChar() {throw new EAccesoIlegitimo();}
		public String valorString() {throw new EAccesoIlegitimo();}
	}
	private class ValorInt extends Valor {
		private int valor;

		public ValorInt(int valor) {
			this.valor = valor;
		}

		public int valorInt() {
			return valor;
		}

		public boolean valorBool() {
			return (valor != 0);
		}

		public char valorChar() {
			return (char) valor;
		}

		public double valorReal() {
			return valor;
		}

		public String toString() {
			return String.valueOf(valor);
		}
	}

	private class ValorBool extends Valor {
		private boolean valor;

		public ValorBool(boolean valor) {
			this.valor = valor;
		}

		public int valorInt() {
			return (valor) ? 1 : 0;
		}

		public boolean valorBool() {
			return valor;
		}

		public double valorReal() {
			return (valor) ? 1.0 : 0;
		}

		public String toString() {
			return String.valueOf(valor);
		}
	}

	private class ValorChar extends Valor {
		private char valor;

		public ValorChar(char valor) {
			this.valor = valor;
		}

		public int valorInt() {
			return (int) valor;
		}

		public char valorChar() {
			return valor;
		}

		public double valorReal() {
			return (double) valor;
		}

		public String valorString() {
			return "" + valor;
		}

		public String toString() {
			return String.valueOf(valor);
		}
	}

	private class ValorReal extends Valor {
		private double valor;

		public ValorReal(double valor) {
			this.valor = valor;
		}

		public int valorInt() {
			return (int) valor;
		}

		public double valorReal() {
			return valor;
		}

		public String toString() {
			return String.valueOf(valor);
		}
	}

	private class ValorString extends Valor {
		private String valor;

		public ValorString(String valor) {
			this.valor = valor;
		}

		public String valorString() {
			return valor;
		}

		public String toString() {
			return valor;
		}
	}

	private class ValorUnknown extends Valor {
		public String toString() {
			return "?";
		}
	}

	private List<Instruccion> codigoP;
	private Stack<Valor> pilaEvaluacion;
	private Valor[] datos;
	private int pc;

	public interface Instruccion {
		void ejecuta();
	}
	private abstract class IOpBin implements Instruccion {
		public void ejecuta() {
			Valor o2 = pilaEvaluacion.pop(); 
			Valor o1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(opera(o1,o2));
			pc++;
		} 
		protected abstract Valor opera(Valor o1, Valor o2);
	}
	private abstract class IOpUn implements Instruccion {
		public void ejecuta() {
			Valor o1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(opera(o1));
			pc++;
		} 
		protected abstract Valor opera(Valor o1);
	}

	private ICaseNum ICASENUM;

	private class ICaseNum implements Instruccion {
		public void ejecuta() {
			Valor caso = pilaEvaluacion.pop();
			Valor original = pilaEvaluacion.pop();

			Valor resul = new ValorBool(
					original.valorReal() == caso.valorReal());
			if (!resul.valorBool()) {
				pilaEvaluacion.push(original);
			}
			pilaEvaluacion.push(resul);
			pc++;

		}

		public String toString() {
			return "caseInt";
		}
	}

	private ICaseBool ICASEBOOL;

	private class ICaseBool implements Instruccion {
		public void ejecuta() {
			Valor caso = pilaEvaluacion.pop();
			Valor original = pilaEvaluacion.pop();

			Valor resul = new ValorBool(
					original.valorBool() == caso.valorBool());
			if (!resul.valorBool()) {
				pilaEvaluacion.push(original);
			}
			pilaEvaluacion.push(resul);
			pc++;

		}

		public String toString() {
			return "caseBool";
		}
	}

	private ICaseChar ICASECHAR;

	private class ICaseChar implements Instruccion {
		public void ejecuta() {
			Valor caso = pilaEvaluacion.pop();
			Valor original = pilaEvaluacion.pop();

			Valor resul = new ValorBool(
					original.valorChar() == caso.valorChar());
			if (!resul.valorBool()) {
				pilaEvaluacion.push(original);
			}
			pilaEvaluacion.push(resul);
			pc++;

		}

		public String toString() {
			return "caseChar";
		}
	}

	private class IApilaInt implements Instruccion {
		private int valor;

		public IApilaInt(int valor) {
			this.valor = valor;
		}

		public void ejecuta() {
			pilaEvaluacion.push(new ValorInt(valor));
			pc++;
		}

		public String toString() {
			return "apilaInt(" + valor + ")";
		};
	}
	private class IApilaBool implements Instruccion {
		private boolean valor;

		public IApilaBool(boolean valor) {
			this.valor = valor;
		}

		public void ejecuta() {
			pilaEvaluacion.push(new ValorBool(valor));
			pc++;
		}

		public String toString() {
			return "apilaBool(" + valor + ")";
		};
	}
	private class IApilaReal implements Instruccion {
		private double valor;

		public IApilaReal(double valor) {
			this.valor = valor;
		}

		public void ejecuta() {
			pilaEvaluacion.push(new ValorReal(valor));
			pc++;
		}

		public String toString() {
			return "apilaReal(" + valor + ")";
		};
	}
	private class IApilaChar implements Instruccion {
		private char valor;

		public IApilaChar(char valor) {
			this.valor = valor;
		}

		public void ejecuta() {
			pilaEvaluacion.push(new ValorChar(valor));
			pc++;
		}

		public String toString() {
			return "apilaChar(" + valor + ")";
		};
	}
	private class IApilaString implements Instruccion {
		private String valor;

		public IApilaString(String valor) {
			this.valor = valor;
		}

		public void ejecuta() {
			pilaEvaluacion.push(new ValorString(valor));
			pc++;
		}

		public String toString() {
			return "apilaString(" + valor + ")";
		};
	}
	private ISuma ISUMA;
	private class ISuma extends IOpBin {
		public Valor opera(Valor o1, Valor o2) {
			return new ValorInt(o1.valorInt()+o2.valorInt());  
		}
		public String toString() {return "suma";};
	}
	private ISumaReal ISUMAREAL;
	private class ISumaReal extends IOpBin { 
		public Valor opera(Valor o1, Valor o2) {
			return new ValorReal(o1.valorReal()*o2.valorReal());  
		}
		public String toString() {return "sumaReal";}
	}

	private ISumaString ISUMASTRING;
	private class ISumaString extends IOpBin { 
		public Valor opera(Valor o1, Valor o2) {
			return new ValorString(o1.valorString()+o2.valorString());  
		} 
		public String toString() {return "sumaString";}
	}

	private IResta IRESTA;
	private class IResta extends IOpBin {
		public Valor opera(Valor o1, Valor o2) {
			return new ValorInt(o1.valorInt()-o2.valorInt());  
		} 
		public String toString() {return "resta";}
	}  

	private IRestaReal IRESTAREAL;
	private class IRestaReal extends IOpBin { 
		public Valor opera(Valor o1, Valor o2) {
			return new ValorReal(o1.valorReal()-o2.valorReal());  
		} 
		public String toString() {return "restaReal";}
	}

	private IMulti IMULTI;
	private class IMulti extends IOpBin {
		public Valor opera(Valor o1, Valor o2) {
			return new ValorInt(o1.valorInt()*o2.valorInt()); 
		} 
		public String toString() {return "multi";}
	}  

	private IMultiReal IMULTIREAL;
	private class IMultiReal extends IOpBin { 
		public Valor opera(Valor o1, Valor o2) {
			return new ValorReal(o1.valorReal()*o2.valorReal());
		} 
		public String toString() {return "multiReal";}
	}

	private IDivi IDIVI;
	private class IDivi extends IOpBin {
		public Valor opera(Valor o1, Valor o2) {
			return new ValorReal(o1.valorInt()/o2.valorInt());
		} 
		public String toString() {return "divi";}
	}

	private IDiviReal IDIVIREAL;
	private class IDiviReal extends IOpBin {
		public Valor opera(Valor o1, Valor o2) {
			return new ValorReal(o1.valorReal()/o2.valorReal());
		} 
		public String toString() {return "diviReal";}
	}

	private IRestInt IRESTINT;
	private class IRestInt extends IOpBin {
		public Valor opera(Valor o1, Valor o2) {
			return new ValorInt(o1.valorInt()%o2.valorInt());
		}  
		public String toString() {return "restint";}
	}

	private ICambiaSign ICAMBIASIGN;
	private class ICambiaSign extends IOpUn {
		public Valor opera(Valor o1) {
			return new ValorInt(-o1.valorInt());
		} 
		public String toString() {return "cambiasign";}
	}

	private ICambiaSignReal ICAMBIASIGNREAL;
	private class ICambiaSignReal extends IOpUn {
		public Valor opera(Valor o1) {
			return new ValorReal(-o1.valorReal());
		} 
		public String toString() {return "cambiasignReal";}
	}

	private IElementoCadena IELEMENTOCADENA;
	private class IElementoCadena extends IOpBin {
		public Valor opera(Valor o1, Valor o2) {
			return new ValorChar(o1.valorString().charAt(o2.valorInt()));
		}  
		public String toString() {return "elementocadena";}
	}

	private class IEnRango implements Instruccion {
		private int d;

		public IEnRango(int d) {
			this.d = d;
		}

		public void ejecuta() {
			Valor index = pilaEvaluacion.peek();

			if (index.valorInt() >= d)
				throw new EAccesoFueraDeRango();
			pc++;
		}

		public String toString() {
			return "enRango(" + d + ")";
		}
	}

	private IConversionIntR ICONVERSIONINTR; //Conversion real a int
	private class IConversionIntR extends IOpUn {
		public Valor opera(Valor o1) {
			return new ValorInt((int) o1.valorReal()); 
		} 
		public String toString() {return "conversionintr";}
	}

	private IConversionIntB ICONVERSIONINTB; //Conversion bool a int
	private class IConversionIntB extends IOpUn {
		public Valor opera(Valor o1) {
			if (o1.valorBool())	return new ValorInt(1);
			else return new ValorInt(0);
		} 
		public String toString() {return "conversionintb";}
	}

	private IConversionIntC ICONVERSIONINTC; //Conversion char a int
	private class IConversionIntC extends IOpUn {
		public Valor opera(Valor o1) {
			return new ValorInt(o1.valorChar());
		} 
		public String toString() {return "conversionintc";}
	}

	private IConversionReal ICONVERSIONREAL; //Conversion real a real
	private class IConversionReal extends IOpUn {
		public Valor opera(Valor o1) {
			return new ValorReal(o1.valorReal());
		} 
		public String toString() {return "conversionreal";}
	}

	private IConversionRealI ICONVERSIONREALI; //Conversion int a real
	private class IConversionRealI extends IOpUn {
		public Valor opera(Valor o1) {
			return new ValorReal(o1.valorInt());
		} 
		public String toString() {return "conversionreali";}
	}

	private IConversionRealB ICONVERSIONREALB; //Conversion bool a real
	private class IConversionRealB extends IOpUn {
		public Valor opera(Valor o1) {
			if (o1.valorBool()) return new ValorReal(1);
			else return new ValorReal(0);
		} 
		public String toString() {return "convertirareal";}
	}

	private IConversionRealC ICONVERSIONREALC; //Conversion char a real
	private class IConversionRealC extends IOpUn {
		public Valor opera(Valor o1) {
			return new ValorReal(o1.valorChar());
		}
		public String toString() {return "conversionrealc";}
	}

	private IConversionChar ICONVERSIONCHAR; //Conversion int a char
	private class IConversionChar extends IOpUn {
		public Valor opera(Valor o1) {
			return new ValorChar((char) o1.valorInt());
		}
		public String toString() {return "conversionchar";}
	}

	private IConversionBool ICONVERSIONBOOL; //Conversion int a bool
	private class IConversionBool extends IOpUn {
		public Valor opera(Valor o1) {
			if (o1.valorInt()==0) return new ValorBool(false);
			else return new ValorBool(true);
		}
		public String toString() {return "convertirabool";};
	}

	private IConversionString ICONVERSIONSTRING;
	private class IConversionString extends IOpUn {
		public Valor opera(Valor o1) {
			return new ValorString(""+o1.valorChar());
		}
		public String toString() {return "convertirabool";};
	}

	private IIgual IIGUAL;
	private class IIgual extends IOpBin {
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o1.valorInt()==o2.valorInt());  
		}
		public String toString() {return "igualint";};
	}

	private IIgualReal IIGUALREAL;
	private class IIgualReal extends IOpBin { 
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o1.valorReal()==o2.valorReal());
		}
		public String toString() {return "igualReal";}
	}

	private IIgualChar IIGUALCHAR;
	private class IIgualChar extends IOpBin { 
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o2.valorChar()==o1.valorChar());
		}
		public String toString() {return "igualChar";}
	}

	private IIgualBool IIGUALBOOL;
	private class IIgualBool extends IOpBin { 
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o2.valorBool()==o1.valorBool());
		}
		public String toString() {return "igualBool";}
	}

	private IIgualString IIGUALSTRING;
	private class IIgualString extends IOpBin { 
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o1.valorString().equals(o2.valorString()));
		}
		public String toString() {return "igualString";}
	}

	private IMayor IMAYOR;
	private class IMayor extends IOpBin {
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o1.valorInt()>o2.valorInt());
		}
		public String toString() {return "mayor";}
	} 

	private IMayorReal IMAYORREAL;
	private class IMayorReal extends IOpBin { 
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o1.valorReal()>o2.valorReal());
		}
		public String toString() {return "mayorReal";}
	}

	private IMayorChar IMAYORCHAR;
	private class IMayorChar extends IOpBin { 
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o1.valorChar()>o2.valorChar());
		}
		public String toString() {return "mayorChar";}
	}

	private IMayorBool IMAYORBOOL;
	private class IMayorBool extends IOpBin { 
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o1.valorBool()&&!o2.valorBool());
		}
		public String toString() {return "mayorBool";}
	}

	private IMayorString IMAYORSTRING;
	private class IMayorString extends IOpBin { 
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o1.valorString().compareTo(o2.valorString()) > 0);
		}
		public String toString() {return "mayorString";}
	}

	private IMenor IMENOR;
	private class IMenor extends IOpBin {
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o1.valorInt()<o2.valorInt());
		}
		public String toString() {return "menor";}
	} 

	private IMenorReal IMENORREAL;
	private class IMenorReal extends IOpBin { 
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o1.valorReal()<o2.valorReal());
		}
		public String toString() {return "menorReal";}
	}

	private IMenorChar IMENORCHAR;
	private class IMenorChar extends IOpBin { 
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o1.valorChar()<o2.valorChar());
		}
		public String toString() {return "menorChar";}
	}

	private IMenorBool IMENORBOOL;
	private class IMenorBool extends IOpBin { 
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(!o1.valorBool()&&o2.valorBool());
		}
		public String toString() {return "menorBool";}
	}

	private IMenorString IMENORSTRING;
	private class IMenorString extends IOpBin {
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o1.valorString().compareTo(o2.valorString()) < 0);
		}
		public String toString() {return "menorString";}
	}

	private IMayorIgual IMAYORIGUAL;
	private class IMayorIgual extends IOpBin {
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o1.valorInt() >= o2.valorInt());
		}
		public String toString() {return "mayorigual";}
	} 

	private IMayorIgualReal IMAYORIGUALREAL;
	private class IMayorIgualReal extends IOpBin { 
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o1.valorReal()>=o2.valorReal());
		}
		public String toString() {return "mayorIgualReal";}
	}

	private IMayorIgualChar IMAYORIGUALCHAR;
	private class IMayorIgualChar extends IOpBin {
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o1.valorChar()>=o2.valorChar());
		}
		public String toString() {return "mayorIgualChar";}
	}

	private IMayorIgualBool IMAYORIGUALBOOL;
	private class IMayorIgualBool extends IOpBin { 
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(!o1.valorBool()&&o2.valorBool());
		}
		public String toString() {return "mayorIgualBool";}
	}

	private IMayorIgualString IMAYORIGUALSTRING;
	private class IMayorIgualString extends IOpBin {
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o1.valorString().compareTo(o2.valorString()) < 0);
		}
		public String toString() {return "mayorIgualString";}
	}

	private IMenorIgual IMENORIGUAL;
	private class IMenorIgual extends IOpBin {
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o1.valorInt() <= o2.valorInt());
		}
		public String toString() {return "menorigual";}
	} 

	private IMenorIgualReal IMENORIGUALREAL;
	private class IMenorIgualReal extends IOpBin {
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o1.valorReal()<=o2.valorReal());
		}
		public String toString() {return "menorIgualReal";}
	}

	private IMenorIgualChar IMENORIGUALCHAR;
	private class IMenorIgualChar extends IOpBin { 
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o1.valorChar()<=o2.valorChar());
		}
		public String toString() {return "menorIgualChar";}
	}

	private IMenorIgualBool IMENORIGUALBOOL;
	private class IMenorIgualBool extends IOpBin { 
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o1.valorBool()&&!o2.valorBool());
		}
		public String toString() {return "menorIgualBool";}
	}

	private IMenorIgualString IMENORIGUALSTRING;
	private class IMenorIgualString extends IOpBin { 
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o1.valorString().compareTo(o2.valorString()) > 0);
		}
		public String toString() {return "menorIgualString";}
	}

	private IDistinto IDISTINTO;
	private class IDistinto extends IOpBin {
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o1.valorInt()!=o2.valorInt());
		}
		public String toString() {return "idistinto";}
	} 

	private IDistintoReal IDISTINTOREAL;
	private class IDistintoReal extends IOpBin { 
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o1.valorReal()!=o2.valorReal());
		}
		public String toString() {return "distintoReal";}
	}

	private IDistintoChar IDISTINTOCHAR;
	private class IDistintoChar extends IOpBin {
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o2.valorChar()!=o1.valorChar());
		}
		public String toString() {return "distintoChar";}
	}

	private IDistintoBool IDISTINTOBOOL;
	private class IDistintoBool extends IOpBin {
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o2.valorBool()!=o1.valorBool());
		}
		public String toString() {return "distintoBool";}
	}

	private IDistintoString IDISTINTOSTRING;
	private class IDistintoString extends IOpBin {
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(!o1.valorString().equals(o2.valorString()));
		}
		public String toString() {return "distintoString";}
	}

	private IAnd IAND;
	private class IAnd extends IOpBin {
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o1.valorBool()&&o2.valorBool());
		}
		public String toString() {return "and";};
	}

	private IOr IOR;
	private class IOr extends IOpBin {
		public Valor opera(Valor o1, Valor o2) {
			return new ValorBool(o1.valorBool() || o2.valorBool());
		}
		public String toString() {return "or";}
	}

	private INot INOT;
	private class INot extends IOpUn {
		public Valor opera(Valor o1) {
			return new ValorBool(!o1.valorBool());
		}
		public String toString() {return "not";}
	}

	private abstract class ILee implements Instruccion {
		protected Scanner in;

		public ILee() {
			in = new Scanner(System.in);
		}
	}

	private ILeeI ILEEI;
	private class ILeeI extends ILee {
		public void ejecuta() {
			int dir = pilaEvaluacion.pop().valorInt();
			try {
				System.out.print("Introduce un valor entero --> ");
				int v = in.nextInt();
				datos[dir] = new ValorInt(v);
			} catch (Exception e) {
				datos[dir] = UNKNOWN;
			}
			pc++;
		}

		public String toString() {
			return "leeI";
		}
	}

	private ILeeR ILEER;
	private class ILeeR extends ILee {

		public void ejecuta() {
			int dir = pilaEvaluacion.pop().valorInt();
			try {
				System.out.print("Introduce un valor real --> ");
				double v = in.nextDouble();
				datos[dir] = new ValorReal(v);
			} catch (Exception e) {
				datos[dir] = UNKNOWN;
			}
			pc++;
		}

		public String toString() {
			return "leeR";
		}
	}

	private ILeeB ILEEB;
	private class ILeeB extends ILee {
		public void ejecuta() {
			int dir = pilaEvaluacion.pop().valorInt();
			try {
				System.out.print("Introduce un valor bool --> ");
				boolean v = in.nextBoolean();
				datos[dir] = new ValorBool(v);
			} catch (Exception e) {
				datos[dir] = UNKNOWN;
			}
			pc++;
		}

		public String toString() {
			return "leeB";
		}
	}

	private ILeeC ILEEC;
	private class ILeeC extends ILee {
		public void ejecuta() {
			int dir = pilaEvaluacion.pop().valorInt();
			try {
				System.out.print("Introduce un valor char --> ");
				char v = in.next().charAt(0);
				datos[dir] = new ValorChar(v);
			} catch (Exception e) {
				datos[dir] = UNKNOWN;
			}
			pc++;
		}

		public String toString() {
			return "leeC";
		}
	}

	private ILeeS ILEES;
	private class ILeeS extends ILee {
		public void ejecuta() {
			int dir = pilaEvaluacion.pop().valorInt();
			try {
				System.out.print("Introduce un valor string --> ");
				String v = in.nextLine();
				datos[dir] = new ValorString(v);
			} catch (Exception e) {
				datos[dir] = UNKNOWN;
			}
			pc++;
		}

		public String toString() {
			return "leeS";
		}
	}

	private IEscribe IESCRIBE;
	private class IEscribe implements Instruccion {
		public void ejecuta() {
			Valor resul = pilaEvaluacion.pop();
			System.out.print(resul);
			pc++;
		}

		public String toString() {
			return "escribe";
		}
	}

	private class IIrA implements Instruccion {
		private int dir;
		public IIrA(int dir) {
			this.dir = dir;
		}
		public void ejecuta() {
			pc=dir;
		}
		public String toString() {return "ira("+dir+")";};
	}

	private class IIrF implements Instruccion {
		private int dir;
		public IIrF(int dir) {
			this.dir = dir;
		}
		public void ejecuta() {
			if(! pilaEvaluacion.pop().valorBool()) {
				pc=dir;
			}
			else {
				pc++;
			}
		}
		public String toString() {return "irf("+dir+")";};
	}

	private class IIrV implements Instruccion {
		private int dir;

		public IIrV(int dir) {
			this.dir = dir;
		}

		public void ejecuta() {
			if (pilaEvaluacion.pop().valorBool()) {
				pc = dir;
			} else {
				pc++;
			}
		}

		public String toString() {
			return "irv(" + dir + ")";
		};
	}

	private class IMueve implements Instruccion {
		private int tam;
		public IMueve(int tam) {
			this.tam = tam;
		}
		public void ejecuta() {
			int dirOrigen = pilaEvaluacion.pop().valorInt();
			int dirDestino = pilaEvaluacion.pop().valorInt();
			if ((dirOrigen + (tam-1)) >= datos.length)
				throw new EAccesoFueraDeRango();
			if ((dirDestino + (tam-1)) >= datos.length)
				throw new EAccesoFueraDeRango();
			for (int i=0; i < tam; i++)
				datos[dirDestino+i] = datos[dirOrigen+i];
			pc++;
		}
		public String toString() {return "mueve("+tam+")";};
	}

	private IApilaind IAPILAIND;
	private class IApilaind implements Instruccion {
		public void ejecuta() {
			int dir = pilaEvaluacion.pop().valorInt();
			if (dir >= datos.length) throw new EAccesoFueraDeRango();
			if (datos[dir] == null) throw new EAccesoAMemoriaNoInicializada();
			pilaEvaluacion.push(datos[dir]);
			pc++;
		}
		public String toString() {return "apilaind";};
	}

	private IDesapilaind IDESAPILAIND;
	private class IDesapilaind implements Instruccion {
		public void ejecuta() {
			Valor valor = pilaEvaluacion.pop();
			int dir = pilaEvaluacion.pop().valorInt();
			if (dir >= datos.length) throw new EAccesoFueraDeRango();
			datos[dir] = valor;
			pc++;
		}
		public String toString() {return "desapilaind";};
	}

	private class IAlloc implements Instruccion {
		private int tam;
		public IAlloc(int tam) {
			this.tam = tam;
		}
		public void ejecuta() {
			int inicio = gestorMemoriaDinamica.alloc(tam);
			pilaEvaluacion.push(new ValorInt(inicio));
			pc++;
		}
		public String toString() {return "alloc("+tam+")";};
	}

	private class IDealloc implements Instruccion {
		private int tam;
		public IDealloc(int tam) {
			this.tam = tam;
		}
		public void ejecuta() {
			int inicio = pilaEvaluacion.pop().valorInt();
			gestorMemoriaDinamica.free(inicio,tam);
			pc++;
		}
		public String toString() {return "dealloc("+tam+")";};
	}

	private class IActiva implements Instruccion {
		private int nivel;
		private int tamdatos;
		private int dirretorno;

		public IActiva(int nivel, int tamdatos, int dirretorno) {
			this.nivel = nivel;
			this.tamdatos = tamdatos;
			this.dirretorno = dirretorno;
		}

		public void ejecuta() {
			int base = gestorPilaActivaciones.creaRegistroActivacion(tamdatos);
			datos[base] = new ValorInt(dirretorno);
			datos[base + 1] = new ValorInt(
					gestorPilaActivaciones.display(nivel));
			pilaEvaluacion.push(new ValorInt(base + 2));
			pc++;
		}

		public String toString() {
			return "activa(" + nivel + "," + tamdatos + "," + dirretorno + ")";
		}
	}

	private class IDesactiva implements Instruccion {
		private int nivel;
		private int tamdatos;

		public IDesactiva(int nivel, int tamdatos) {
			this.nivel = nivel;
			this.tamdatos = tamdatos;
		}

		public void ejecuta() {
			int base = gestorPilaActivaciones
					.liberaRegistroActivacion(tamdatos);
			gestorPilaActivaciones.fijaDisplay(nivel,
					datos[base + 1].valorInt());
			pilaEvaluacion.push(datos[base]);
			pc++;
		}

		public String toString() {
			return "desactiva(" + nivel + "," + tamdatos + ")";
		}

	}

	private class ISetd implements Instruccion {
		private int nivel;

		public ISetd(int nivel) {
			this.nivel = nivel;
		}

		public void ejecuta() {
			gestorPilaActivaciones.fijaDisplay(nivel, pilaEvaluacion.pop().valorInt());
			pc++;
		}

		public String toString() {
			return "setd(" + nivel + ")";
		}
	}
	
	private Instruccion ISTOP;
	private class IStop implements Instruccion {
		public void ejecuta() {
			pc = codigoP.size();
		}
		public String toString() {
			return "stop";                 
		}
	}

	private class IApilad implements Instruccion {
		private int nivel;

		public IApilad(int nivel) {
			this.nivel = nivel;
		}

		public void ejecuta() {
			pilaEvaluacion.push(new ValorInt(gestorPilaActivaciones
					.display(nivel)));
			pc++;
		}

		public String toString() {
			return "apilad(" + nivel + ")";
		}

	}

	private Instruccion IIRIND;

	private class IIrind implements Instruccion {
		public void ejecuta() {
			pc = pilaEvaluacion.pop().valorInt();
		}

		public String toString() {
			return "irind";
		}
	}

	private IDup IDUP;

	private class IDup implements Instruccion {
		public void ejecuta() {
			pilaEvaluacion.push(pilaEvaluacion.peek());
			pc++;
		}

		public String toString() {
			return "dup";
		}
	}

	public Instruccion suma() {return ISUMA;}
	public Instruccion sumaReal() {return ISUMAREAL;}
	public Instruccion sumaString() {return ISUMASTRING;}
	public Instruccion resta() {return IRESTA;}
	public Instruccion restaReal() {return IRESTAREAL;}
	public Instruccion multi() {return IMULTI;}
	public Instruccion multiReal() {return IMULTIREAL;}
	public Instruccion divi() {return IDIVI;}
	public Instruccion diviReal() {return IDIVIREAL;}
	public Instruccion restint() {return IRESTINT;}
	public Instruccion cambiasign() {return ICAMBIASIGN;}
	public Instruccion cambiasignReal() {return ICAMBIASIGNREAL;}
	public Instruccion elementocadena() {return IELEMENTOCADENA;}
	public Instruccion igual() {return IIGUAL;}
	public Instruccion igualReal() {return IIGUALREAL;}
	public Instruccion igualChar() {return IIGUALCHAR;}
	public Instruccion igualBool() {return IIGUALBOOL;}
	public Instruccion igualString() {return IIGUALSTRING;}
	public Instruccion mayor() {return IMAYOR;}
	public Instruccion mayorReal() {return IMAYORREAL;}
	public Instruccion mayorChar() {return IMAYORCHAR;}
	public Instruccion mayorBool() {return IMAYORBOOL;}
	public Instruccion mayorString() {return IMAYORSTRING;}
	public Instruccion menor() {return IMENOR;}
	public Instruccion menorReal() {return IMENORREAL;}
	public Instruccion menorChar() {return IMENORCHAR;}
	public Instruccion menorBool() {return IMENORBOOL;}
	public Instruccion menorString() {return IMENORSTRING;}
	public Instruccion mayorIgual() {return IMAYORIGUAL;}
	public Instruccion mayorIgualReal() {return IMAYORIGUALREAL;}
	public Instruccion mayorIgualChar() {return IMAYORIGUALCHAR;}
	public Instruccion mayorIgualBool() {return IMAYORIGUALBOOL;}
	public Instruccion mayorIgualString() {return IMAYORIGUALSTRING;}
	public Instruccion menorIgual() {return IMENORIGUAL;}
	public Instruccion menorIgualReal() {return IMENORIGUALREAL;}
	public Instruccion menorIgualChar() {return IMENORIGUALCHAR;}
	public Instruccion menorIgualBool() {return IMENORIGUALBOOL;}
	public Instruccion menorIgualString() {return IMENORIGUALSTRING;}
	public Instruccion distinto() {return IDISTINTO;}
	public Instruccion distintoReal() {return IDISTINTOREAL;}
	public Instruccion distintoChar() {return IDISTINTOCHAR;}
	public Instruccion distintoBool() {return IDISTINTOBOOL;}
	public Instruccion distintoString() {return IDISTINTOSTRING;}
	public Instruccion and() {return IAND;}
	public Instruccion or() {return IOR;}
	public Instruccion not() {return INOT;}
	public Instruccion leei() {return ILEEI;}
	public Instruccion leer() {return ILEER;}
	public Instruccion leec() {return ILEEC;}
	public Instruccion lees() {return ILEES;}
	public Instruccion leeb() {return ILEEB;}
	public Instruccion escribe() {return IESCRIBE;}
	public Instruccion conversionIntR() {return ICONVERSIONINTR;}
	public Instruccion conversionIntB() {return ICONVERSIONINTB;}
	public Instruccion conversionIntC() {return ICONVERSIONINTC;}
	public Instruccion conversionReal() {return ICONVERSIONREAL;}
	public Instruccion conversionRealI() {return ICONVERSIONREALI;}
	public Instruccion conversionRealB() {return ICONVERSIONREALB;}
	public Instruccion conversionRealC() {return ICONVERSIONREALC;}
	public Instruccion conversionChar() {return ICONVERSIONCHAR;}
	public Instruccion conversionaBool() {return ICONVERSIONBOOL;}
	public Instruccion conversionaString() {return ICONVERSIONSTRING;}
	public Instruccion apilaInt(int val) {return new IApilaInt(val);}
	public Instruccion apilaBool(boolean val) {return new IApilaBool(val);}
	public Instruccion apilaReal(double val) {return new IApilaReal(val);}
	public Instruccion apilaChar(char val) {return new IApilaChar(val);}
	public Instruccion apilaString(String val) {return new IApilaString(val);}
	public Instruccion apilaInd() {return IAPILAIND;}
	public Instruccion desapilaInd() {return IDESAPILAIND;}
	public Instruccion mueve(int tam) {return new IMueve(tam);}
	public Instruccion irA(int dir) {return new IIrA(dir);}
	public Instruccion irF(int dir) {return new IIrF(dir);}
	public Instruccion irV(int dir) {return new IIrV(dir);}
	public Instruccion icaseNum() {return ICASENUM;}
	public Instruccion icaseBool() {return ICASEBOOL;}
	public Instruccion icaseChar() {return ICASECHAR;}
	public Instruccion alloc(int tam) {return new IAlloc(tam);}
	public Instruccion dealloc(int tam) {return new IDealloc(tam);}
	public Instruccion enrango(int d) {return new IEnRango(d);}
	public Instruccion activa(int nivel, int tam, int dirretorno) {return new IActiva(nivel, tam, dirretorno);}
	public Instruccion desactiva(int nivel, int tam) {return new IDesactiva(nivel, tam);}
	public Instruccion setd(int nivel) {return new ISetd(nivel);}
	public Instruccion apilad(int nivel) {return new IApilad(nivel);}
	public Instruccion irInd() {return IIRIND;}
	public Instruccion dup() {return IDUP;}
	public Instruccion stop() {return ISTOP;}
	public void addInstruccion(Instruccion i) {
		codigoP.add(i);
	}

	private int tamdatos;
	private int tamheap;
	private int ndisplays;
	public MaquinaP(int tamdatos, int tampila, int tamheap, int ndisplays) {

		this.codigoP = new ArrayList<>();
		pilaEvaluacion = new Stack<>();
		this.tamdatos = tamdatos;
		this.tamheap = tamheap;
		this.ndisplays = ndisplays;
		datos = new Valor[tamdatos+tamheap+tampila];
		this.pc = 0;

		ICASENUM = new ICaseNum();
		ICASEBOOL = new ICaseBool();
		ICASECHAR = new ICaseChar();
		ISUMA = new ISuma();
		ISUMAREAL = new ISumaReal();
		ISUMASTRING = new ISumaString();
		IRESTA = new IResta();
		IRESTAREAL = new IRestaReal();
		IMULTI = new IMulti();
		IMULTIREAL = new IMultiReal();
		IDIVI = new IDivi();
		IDIVIREAL = new IDiviReal();
		IRESTINT = new IRestInt();
		ICAMBIASIGN = new ICambiaSign();
		ICAMBIASIGNREAL = new ICambiaSignReal();
		IELEMENTOCADENA = new IElementoCadena();
		ICONVERSIONINTR = new IConversionIntR();
		ICONVERSIONINTB = new IConversionIntB();
		ICONVERSIONINTC = new IConversionIntC();
		ICONVERSIONREAL = new IConversionReal();
		ICONVERSIONREALI = new IConversionRealI();
		ICONVERSIONREALB = new IConversionRealB();
		ICONVERSIONREALC = new IConversionRealC();
		ICONVERSIONCHAR = new IConversionChar();
		ICONVERSIONBOOL = new IConversionBool();
		ICONVERSIONSTRING = new IConversionString();
		IIGUAL = new IIgual();
		IIGUALREAL = new IIgualReal();
		IIGUALCHAR = new IIgualChar();
		IIGUALBOOL = new IIgualBool();
		IIGUALSTRING = new IIgualString();
		IMAYOR = new IMayor();
		IMAYORREAL = new IMayorReal();
		IMAYORCHAR = new IMayorChar();
		IMAYORBOOL = new IMayorBool();
		IMAYORSTRING = new IMayorString();
		IMENOR = new IMenor();
		IMENORREAL = new IMenorReal();
		IMENORCHAR = new IMenorChar();
		IMENORBOOL = new IMenorBool();
		IMENORSTRING = new IMenorString();
		IMAYORIGUAL = new IMayorIgual();
		IMAYORIGUALREAL = new IMayorIgualReal();
		IMAYORIGUALCHAR = new IMayorIgualChar();
		IMAYORIGUALBOOL = new IMayorIgualBool();
		IMAYORIGUALSTRING = new IMayorIgualString();
		IMENORIGUAL = new IMenorIgual();
		IMENORIGUALREAL = new IMenorIgualReal();
		IMENORIGUALCHAR = new IMenorIgualChar();
		IMENORIGUALBOOL = new IMenorIgualBool();
		IMENORIGUALSTRING = new IMenorIgualString();
		IDISTINTO = new IDistinto();
		IDISTINTOREAL = new IDistintoReal();
		IDISTINTOCHAR = new IDistintoChar();
		IDISTINTOBOOL = new IDistintoBool();
		IDISTINTOSTRING = new IDistintoString();
		IAND = new IAnd();
		IOR = new IOr();
		INOT = new INot();
		ILEEI = new ILeeI();
		ILEER = new ILeeR();
		ILEES = new ILeeS();
		ILEEC = new ILeeC();
		ILEEB = new ILeeB();
		IESCRIBE = new IEscribe();
		UNKNOWN = new ValorUnknown();
		IAPILAIND = new IApilaind();
		IDESAPILAIND = new IDesapilaind();
		IIRIND = new IIrind();
		IDUP = new IDup();
		ISTOP = new IStop();
		gestorMemoriaDinamica = new GestorMemoriaDinamica(tamdatos+tampila,(tamdatos+tamheap+tampila)-1);
		gestorPilaActivaciones = new GestorPilaActivaciones(tamdatos,
				(tamdatos + tampila) - 1, ndisplays);
	}
	public void ejecuta() {
		while(pc != codigoP.size()) {
			codigoP.get(pc).ejecuta();
		}
	}
	public void muestraCodigo() {
		System.out.println("CodigoP");
		for(int i=0; i < codigoP.size(); i++) {
			System.out.println(" "+i+":"+codigoP.get(i));
		}
	}
	public void muestraEstado() {
		System.out.println("Tamaño de datos: " + this.tamdatos);
		System.out.println("Tamaño de heap: " + this.tamheap);
		System.out.println("PP: " + gestorPilaActivaciones.pp());
		System.out.print("Prints:");
		for (int i=1; i <= ndisplays; i++)
			System.out.print(i+":"+gestorPilaActivaciones.display(i)+" ");
		System.out.println();
		System.out.println("Pila de evaluacion");
		for(int i=0; i < pilaEvaluacion.size(); i++) {
			System.out.println(" "+i+":"+pilaEvaluacion.get(i));
		}
		System.out.println("Datos");
		for(int i=0; i < datos.length; i++) {
			System.out.println(" "+i+":"+datos[i]);
		}
		System.out.println("PC:"+pc);
	}
}