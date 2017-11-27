package procesamientos.comprobaciontipos;

import errores.Errores;
import java.util.HashSet;
import java.util.Set;
import procesamientos.Procesamiento;
import programa.Programa;
import programa.Programa.CteInt;
import programa.Programa.CteReal;
import programa.Programa.CteString;
import programa.Programa.CteBool;
import programa.Programa.Exp;
import programa.Programa.CteChar;
import programa.Programa.Suma;
import programa.Programa.TArray;
import programa.Programa.TRegistro;
import programa.Programa.TRef;
import programa.Programa.And;
import programa.Programa.CambiaSign;
import programa.Programa.CampoReg;
import programa.Programa.Case;
import programa.Programa.ConversionBool;
import programa.Programa.ConversionChar;
import programa.Programa.ConversionInt;
import programa.Programa.ConversionReal;
import programa.Programa.ConversionString;
import programa.Programa.IAsig;
import programa.Programa.IBloque;
import programa.Programa.ICall;
import programa.Programa.IDoWhile;
import programa.Programa.IWhile;
import programa.Programa.Igual;
import programa.Programa.Index;
import programa.Programa.Inst;
import programa.Programa.Lee;
import programa.Programa.Mayor;
import programa.Programa.MayorIgual;
import programa.Programa.Menor;
import programa.Programa.MenorIgual;
import programa.Programa.Multi;
import programa.Programa.Not;
import programa.Programa.Null;
import programa.Programa.Or;
import programa.Programa.Prog;
import programa.Programa.RestInt;
import programa.Programa.Resta;
import programa.Programa.Select;
import programa.Programa.Var;
import programa.Programa.DRef;
import programa.Programa.Dec;
import programa.Programa.DecProc;
import programa.Programa.Distinto;
import programa.Programa.Divi;
import programa.Programa.ElementoCadena;
import programa.Programa.Escribe;
import programa.Programa.Exp;
import programa.Programa.IFree;
import programa.Programa.IIf;
import programa.Programa.IIfElse;
import programa.Programa.INew;
import programa.Programa.ISwitch;
import programa.Programa.Tipo;
public class ComprobacionTipos extends Procesamiento {
	private final static String ERROR_DREF = "Se espera un objeto de tipo puntero";
	private final static String ERROR_INDEX = "Se espera un objeto de tipo array";
	private final static String ERROR_INDEX_INDICE = "La expresion indice debe ser de tipo INT";
	private final static String ERROR_SELECT = "Se espera un objeto de tipo registro";
	private final static String ERROR_SELECT_CAMPO = "El campo seleccionado no existe en el registro";
	private final static String ERROR_TIPO_OPERANDOS = "Los tipos de los operandos no son correctos";
	private final static String ERROR_TIPO_BASE = "El tipo base no es valido";
	private final static String ERROR_TIPO_CAMPOS = "El tipo de uno de los campos no es correcto";
	private final static String ERROR_DIMENSION = "La dimension proporcionada no es valida";
	private final static String ERROR_ASIG = "Tipos no compatibles en asignacion";
	private final static String ERROR_COND = "Tipo erroneo en condicion";
	private final static String ERROR_NEW = "El operando de New debe ser un puntero";
	private final static String ERROR_FREE = "El operando de Free debe ser un puntero";
	private final static String ERROR_SWITCH_CASE = "El tipo de la expresion no coincide con el del caso";
	private final static String ERROR_SWITCH_EXP = "El tipo de la expresion del switch es erroneo";
	private final static String ERROR_INDEX_ARRAY = "Error en el array referenciado";
	private final static String ERROR_INDEX_INNDICE = "Error en el indice";
	private static final String ERROR_SELECT_TIPOREG = "Error en el tipo del registro";
	private final static String ERROR_NUM_PARAMETROS = "El numero de parametros reales no coincide con el numero de parametros formales";
	private final static String ERROR_MODO_PARAMETRO = "Se esta pasando por referencia una expresion que no es un designador";
	private final static String ERROR_TIPO_PARAMETRO = "El tipo del parametro real no es compatible con el tipo del parametro formal";
	private Programa programa;
	private Errores errores;
	public ComprobacionTipos(Programa programa, Errores errores) {
		this.programa = programa;  
		this.errores = errores;
	}

	public void procesa(DRef p) {
		p.mem().procesaCon(this);
		if(I.esPointer(p.mem().tipo())) {
			p.ponTipo(I.tpointer(p.mem().tipo()).tbase());
		}
		else {
			if(! p.mem().tipo().equals(programa.tipoError())) {
				errores.msg(p.enlaceFuente()+":"+ERROR_DREF); 
			}
			p.ponTipo(programa.tipoError());
		}
	}
	
	public void procesa(TRegistro reg) {
		for (CampoReg campo : reg.getCampos())
			campo.getTipo().procesaCon(this);

		boolean hayError = false;

		for (CampoReg campo : reg.getCampos()) {
			if (I.esError(campo.getTipo())) {
				hayError = true;
				break;
			}
		}

		if (hayError)
			reg.ponTipo(programa.tipoOk());
		else {
			errores.msg(reg.enlaceFuente() + ":" + ERROR_TIPO_BASE);
			reg.ponTipo(programa.tipoError());
		}
	}

	public void procesa(Var exp) {
		exp.ponTipo(exp.declaracion().tipoDec());
	} 
	public void procesa(CteInt exp) {
		exp.ponTipo(programa.tipoInt());
	} 
	public void procesa(CteBool exp) {
		exp.ponTipo(programa.tipoBool());
	} 
	public void procesa(CteReal exp) {
		exp.ponTipo(programa.tipoReal());
	}
	public void procesa(CteChar exp) {
		exp.ponTipo(programa.tipoChar());
	}
	public void procesa(CteString exp) {
		exp.ponTipo(programa.tipoString());
	}
	public void procesa(Null exp) {
		exp.ponTipo(programa.tnull());
	}
	public void procesa(Suma exp) {
		exp.opnd1().procesaCon(this);
		exp.opnd2().procesaCon(this);
		if(exp.opnd1().tipo().equals(programa.tipoInt()) &&
				exp.opnd2().tipo().equals(programa.tipoInt())) {
			exp.ponTipo(programa.tipoInt()); 
		}  	
		else if((exp.opnd1().tipo().equals(programa.tipoInt()) || 
				exp.opnd1().tipo().equals(programa.tipoReal())) &&
				(exp.opnd2().tipo().equals(programa.tipoInt()) ||
						exp.opnd2().tipo().equals(programa.tipoReal()))) {
			exp.ponTipo(programa.tipoReal());
		}
		else if(exp.opnd1().tipo().equals(programa.tipoString()) && exp.opnd2().tipo().equals(programa.tipoString())) {
			exp.ponTipo(programa.tipoString());
		}
		else {
			if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
					! exp.opnd2().tipo().equals(programa.tipoError()))
				errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
			exp.ponTipo(programa.tipoError());
		}     
	} 
	public void procesa(Resta exp) {
		exp.opnd1().procesaCon(this);
		exp.opnd2().procesaCon(this);
		if(exp.opnd1().tipo().equals(programa.tipoInt()) &&
				exp.opnd2().tipo().equals(programa.tipoInt())) {
			exp.ponTipo(programa.tipoInt()); 
		}
		else if((exp.opnd1().tipo().equals(programa.tipoInt()) || 
				exp.opnd1().tipo().equals(programa.tipoReal())) &&
				(exp.opnd2().tipo().equals(programa.tipoInt()) ||
						exp.opnd2().tipo().equals(programa.tipoReal()))) {
			exp.ponTipo(programa.tipoReal());
		}
		else {
			if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
					! exp.opnd2().tipo().equals(programa.tipoError()))
				errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
			exp.ponTipo(programa.tipoError());
		}     
	} 

	public void procesa(Multi exp) {
		exp.opnd1().procesaCon(this);
		exp.opnd2().procesaCon(this);
		if(exp.opnd1().tipo().equals(programa.tipoInt()) &&
				exp.opnd2().tipo().equals(programa.tipoInt())) {
			exp.ponTipo(programa.tipoInt()); 
		}  	
		else if((exp.opnd1().tipo().equals(programa.tipoInt()) || 
				exp.opnd1().tipo().equals(programa.tipoReal())) &&
				(exp.opnd2().tipo().equals(programa.tipoInt()) ||
						exp.opnd2().tipo().equals(programa.tipoReal()))) {
			exp.ponTipo(programa.tipoReal());
		}
		else {
			if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
					! exp.opnd2().tipo().equals(programa.tipoError()))
				errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
			exp.ponTipo(programa.tipoError());
		}     
	} 

	public void procesa(Divi exp) {
		exp.opnd1().procesaCon(this);
		exp.opnd2().procesaCon(this);
		if((exp.opnd1().tipo().equals(programa.tipoInt()) || exp.opnd1().tipo().equals(programa.tipoReal())) &&
				(exp.opnd2().tipo().equals(programa.tipoInt()) || exp.opnd2().tipo().equals(programa.tipoReal()))) {
			exp.ponTipo(programa.tipoReal()); 
		}  		
		else {
			if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
					! exp.opnd2().tipo().equals(programa.tipoError()))
				errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
			exp.ponTipo(programa.tipoError());
		}     
	}

	public void procesa(RestInt exp) {
		exp.opnd1().procesaCon(this);
		exp.opnd2().procesaCon(this);
		if(exp.opnd1().tipo().equals(programa.tipoInt()) &&
				exp.opnd2().tipo().equals(programa.tipoInt())) {
			exp.ponTipo(programa.tipoInt()); 
		}  		
		else {
			if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
					! exp.opnd2().tipo().equals(programa.tipoError()))
				errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
			exp.ponTipo(programa.tipoError());
		}     
	} 

	public void procesa(CambiaSign exp) {
		exp.opnd1().procesaCon(this);
		if(exp.opnd1().tipo().equals(programa.tipoInt())) {
			exp.ponTipo(programa.tipoInt()); 
		}  	
		else if (exp.opnd1().tipo().equals(programa.tipoReal()))
			exp.ponTipo(programa.tipoReal());
		else {
			if (! exp.opnd1().tipo().equals(programa.tipoError()))
				errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
			exp.ponTipo(programa.tipoError());
		}     
	} 

	public void procesa(ElementoCadena exp) {
		exp.opnd1().procesaCon(this);
		exp.opnd2().procesaCon(this);
		if(exp.opnd1().tipo().equals(programa.tipoString()) &&
				exp.opnd2().tipo().equals(programa.tipoInt())) {
			exp.ponTipo(programa.tipoChar()); 
		}  		
		else {
			if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
					! exp.opnd2().tipo().equals(programa.tipoError()))
				errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
			exp.ponTipo(programa.tipoError());
		}     
	}

	public void procesa(ConversionReal exp){
		exp.opnd1().procesaCon(this);
		if(exp.opnd1().tipo().equals(programa.tipoInt()) || exp.opnd1().tipo().equals(programa.tipoBool()) 
				|| exp.opnd1().tipo().equals(programa.tipoReal()) || exp.opnd1().tipo().equals(programa.tipoChar())) {
			exp.ponTipo(programa.tipoReal()); 
		}  		
		else {
			if (! exp.opnd1().tipo().equals(programa.tipoError()))
				errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
			exp.ponTipo(programa.tipoError());
		}     
	}

	public void procesa(ConversionInt exp){
		exp.opnd1().procesaCon(this);
		if(exp.opnd1().tipo().equals(programa.tipoInt()) || exp.opnd1().tipo().equals(programa.tipoBool()) 
				|| exp.opnd1().tipo().equals(programa.tipoReal()) || exp.opnd1().tipo().equals(programa.tipoChar())) {
			exp.ponTipo(programa.tipoInt()); 
		}  		
		else {
			if (! exp.opnd1().tipo().equals(programa.tipoError()))
				errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
			exp.ponTipo(programa.tipoError());
		}     
	}

	public void procesa(ConversionChar exp){
		exp.opnd1().procesaCon(this);
		if(exp.opnd1().tipo().equals(programa.tipoInt())) {
			exp.ponTipo(programa.tipoChar()); 
		}  		
		else {
			if (! exp.opnd1().tipo().equals(programa.tipoError()))
				errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
			exp.ponTipo(programa.tipoError());
		}     
	}

	public void procesa(ConversionBool exp){
		exp.opnd1().procesaCon(this);
		if(exp.opnd1().tipo().equals(programa.tipoInt())) {
			exp.ponTipo(programa.tipoBool()); 
		}  		
		else {
			if (! exp.opnd1().tipo().equals(programa.tipoError()))
				errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
			exp.ponTipo(programa.tipoError());
		}     
	}

	public void procesa(ConversionString exp){
		exp.opnd1().procesaCon(this);
		if(exp.opnd1().tipo().equals(programa.tipoChar())) {
			exp.ponTipo(programa.tipoString()); 
		}  		
		else {
			if (! exp.opnd1().tipo().equals(programa.tipoError()))
				errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
			exp.ponTipo(programa.tipoError());
		}     
	}

	public void procesa(Igual exp) {
		exp.opnd1().procesaCon(this);
		exp.opnd2().procesaCon(this);
		if((exp.opnd1().tipo().equals(programa.tipoBool()) &&
				exp.opnd2().tipo().equals(programa.tipoBool()))
				|| (exp.opnd1().tipo().equals(programa.tipoInt()) &&
						exp.opnd2().tipo().equals(programa.tipoInt())) 
				|| (exp.opnd1().tipo().equals(programa.tipoInt()) &&
						exp.opnd2().tipo().equals(programa.tipoReal()))
				|| (exp.opnd1().tipo().equals(programa.tipoReal()) &&
						exp.opnd2().tipo().equals(programa.tipoInt()))
				|| (exp.opnd1().tipo().equals(programa.tipoReal()) &&
						exp.opnd2().tipo().equals(programa.tipoReal()))
				|| (exp.opnd1().tipo().equals(programa.tipoChar()) &&
						exp.opnd2().tipo().equals(programa.tipoChar()))
				|| (exp.opnd1().tipo().equals(programa.tipoString()) &&
						exp.opnd2().tipo().equals(programa.tipoString()))) {
			exp.ponTipo(programa.tipoBool()); 
		}
		else {
			if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
					! exp.opnd2().tipo().equals(programa.tipoError()))
				errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
			exp.ponTipo(programa.tipoError());
		}     
	}

	public void procesa(Mayor exp) {
		exp.opnd1().procesaCon(this);
		exp.opnd2().procesaCon(this);
		if((exp.opnd1().tipo().equals(programa.tipoBool()) &&
				exp.opnd2().tipo().equals(programa.tipoBool()))
				|| (exp.opnd1().tipo().equals(programa.tipoInt()) &&
						exp.opnd2().tipo().equals(programa.tipoInt())) 
				|| (exp.opnd1().tipo().equals(programa.tipoInt()) &&
						exp.opnd2().tipo().equals(programa.tipoReal()))
				|| (exp.opnd1().tipo().equals(programa.tipoReal()) &&
						exp.opnd2().tipo().equals(programa.tipoInt()))
				|| (exp.opnd1().tipo().equals(programa.tipoReal()) &&
						exp.opnd2().tipo().equals(programa.tipoReal()))
				|| (exp.opnd1().tipo().equals(programa.tipoChar()) &&
						exp.opnd2().tipo().equals(programa.tipoChar()))
				|| (exp.opnd1().tipo().equals(programa.tipoString()) &&
						exp.opnd2().tipo().equals(programa.tipoString()))) {
			exp.ponTipo(programa.tipoBool()); 
		}
		else {
			if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
					! exp.opnd2().tipo().equals(programa.tipoError()))
				errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
			exp.ponTipo(programa.tipoError());
		}     
	}

	public void procesa(Menor exp) {
		exp.opnd1().procesaCon(this);
		exp.opnd2().procesaCon(this);
		if((exp.opnd1().tipo().equals(programa.tipoBool()) &&
				exp.opnd2().tipo().equals(programa.tipoBool()))
				|| (exp.opnd1().tipo().equals(programa.tipoInt()) &&
						exp.opnd2().tipo().equals(programa.tipoInt())) 
				|| (exp.opnd1().tipo().equals(programa.tipoInt()) &&
						exp.opnd2().tipo().equals(programa.tipoReal()))
				|| (exp.opnd1().tipo().equals(programa.tipoReal()) &&
						exp.opnd2().tipo().equals(programa.tipoInt()))
				|| (exp.opnd1().tipo().equals(programa.tipoReal()) &&
						exp.opnd2().tipo().equals(programa.tipoReal()))
				|| (exp.opnd1().tipo().equals(programa.tipoChar()) &&
						exp.opnd2().tipo().equals(programa.tipoChar()))
				|| (exp.opnd1().tipo().equals(programa.tipoString()) &&
						exp.opnd2().tipo().equals(programa.tipoString()))) {
			exp.ponTipo(programa.tipoBool()); 
		}
		else {
			if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
					! exp.opnd2().tipo().equals(programa.tipoError()))
				errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
			exp.ponTipo(programa.tipoError());
		}     
	}

	public void procesa(MayorIgual exp) {
		exp.opnd1().procesaCon(this);
		exp.opnd2().procesaCon(this);
		if((exp.opnd1().tipo().equals(programa.tipoBool()) &&
				exp.opnd2().tipo().equals(programa.tipoBool()))
				|| (exp.opnd1().tipo().equals(programa.tipoInt()) &&
						exp.opnd2().tipo().equals(programa.tipoInt())) 
				|| (exp.opnd1().tipo().equals(programa.tipoInt()) &&
						exp.opnd2().tipo().equals(programa.tipoReal()))
				|| (exp.opnd1().tipo().equals(programa.tipoReal()) &&
						exp.opnd2().tipo().equals(programa.tipoInt()))
				|| (exp.opnd1().tipo().equals(programa.tipoReal()) &&
						exp.opnd2().tipo().equals(programa.tipoReal()))
				|| (exp.opnd1().tipo().equals(programa.tipoChar()) &&
						exp.opnd2().tipo().equals(programa.tipoChar()))
				|| (exp.opnd1().tipo().equals(programa.tipoString()) &&
						exp.opnd2().tipo().equals(programa.tipoString()))) {
			exp.ponTipo(programa.tipoBool()); 
		}
		else {
			if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
					! exp.opnd2().tipo().equals(programa.tipoError()))
				errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
			exp.ponTipo(programa.tipoError());
		}     
	}

	public void procesa(MenorIgual exp) {
		exp.opnd1().procesaCon(this);
		exp.opnd2().procesaCon(this);
		if((exp.opnd1().tipo().equals(programa.tipoBool()) &&
				exp.opnd2().tipo().equals(programa.tipoBool()))
				|| (exp.opnd1().tipo().equals(programa.tipoInt()) &&
						exp.opnd2().tipo().equals(programa.tipoInt())) 
				|| (exp.opnd1().tipo().equals(programa.tipoInt()) &&
						exp.opnd2().tipo().equals(programa.tipoReal()))
				|| (exp.opnd1().tipo().equals(programa.tipoReal()) &&
						exp.opnd2().tipo().equals(programa.tipoInt()))
				|| (exp.opnd1().tipo().equals(programa.tipoReal()) &&
						exp.opnd2().tipo().equals(programa.tipoReal()))
				|| (exp.opnd1().tipo().equals(programa.tipoChar()) &&
						exp.opnd2().tipo().equals(programa.tipoChar()))
				|| (exp.opnd1().tipo().equals(programa.tipoString()) &&
						exp.opnd2().tipo().equals(programa.tipoString()))) {
			exp.ponTipo(programa.tipoBool()); 
		}
		else {
			if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
					! exp.opnd2().tipo().equals(programa.tipoError()))
				errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
			exp.ponTipo(programa.tipoError());
		}     
	}

	public void procesa(Distinto exp) {
		exp.opnd1().procesaCon(this);
		exp.opnd2().procesaCon(this);
		if((exp.opnd1().tipo().equals(programa.tipoBool()) &&
				exp.opnd2().tipo().equals(programa.tipoBool()))
				|| (exp.opnd1().tipo().equals(programa.tipoInt()) &&
						exp.opnd2().tipo().equals(programa.tipoInt())) 
				|| (exp.opnd1().tipo().equals(programa.tipoInt()) &&
						exp.opnd2().tipo().equals(programa.tipoReal()))
				|| (exp.opnd1().tipo().equals(programa.tipoReal()) &&
						exp.opnd2().tipo().equals(programa.tipoInt()))
				|| (exp.opnd1().tipo().equals(programa.tipoReal()) &&
						exp.opnd2().tipo().equals(programa.tipoReal()))
				|| (exp.opnd1().tipo().equals(programa.tipoChar()) &&
						exp.opnd2().tipo().equals(programa.tipoChar()))
				|| (exp.opnd1().tipo().equals(programa.tipoString()) &&
						exp.opnd2().tipo().equals(programa.tipoString()))) {
			exp.ponTipo(programa.tipoBool()); 
		}
		else {
			if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
					! exp.opnd2().tipo().equals(programa.tipoError()))
				errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
			exp.ponTipo(programa.tipoError());
		}     
	}

	public void procesa(And exp) {
		exp.opnd1().procesaCon(this);
		exp.opnd2().procesaCon(this);
		if(exp.opnd1().tipo().equals(programa.tipoBool()) &&
				exp.opnd2().tipo().equals(programa.tipoBool())) {
			exp.ponTipo(programa.tipoBool()); 
		}
		else {
			if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
					! exp.opnd2().tipo().equals(programa.tipoError()))
				errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
			exp.ponTipo(programa.tipoError());
		}     
	}
	public void procesa(Or exp) {
		exp.opnd1().procesaCon(this);
		exp.opnd2().procesaCon(this);
		if(exp.opnd1().tipo().equals(programa.tipoBool()) &&
				exp.opnd2().tipo().equals(programa.tipoBool())) {
			exp.ponTipo(programa.tipoBool()); 
		}
		else {
			if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
					! exp.opnd2().tipo().equals(programa.tipoError()))
				errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
			exp.ponTipo(programa.tipoError());
		}     
	}
	public void procesa(Not exp) {
		exp.opnd1().procesaCon(this);
		if(exp.opnd1().tipo().equals(programa.tipoBool())) {
			exp.ponTipo(programa.tipoBool()); 
		}
		else {
			if (! exp.opnd1().tipo().equals(programa.tipoError()))
				errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
			exp.ponTipo(programa.tipoError());
		}     
	}
	public void procesa(Lee exp) {
		exp.var().procesaCon(this);
		if (exp.var().tipo().equals(programa.tipoError())
				|| !(exp.var().esMen())) {
			if (!exp.var().tipo().equals(programa.tipoError()))
				errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
			exp.ponTipo(programa.tipoError());
		} else
			exp.ponTipo(programa.tipoOk());
	}
	public void procesa(Escribe exp) {
		exp.exp().procesaCon(this);
		if (exp.exp().tipo().equals(programa.tipoError())) {
			if (!exp.exp().tipo().equals(programa.tipoError()))
				errores.msg(exp.enlaceFuente() + ":" + ERROR_TIPO_OPERANDOS);
			exp.ponTipo(programa.tipoError());
		} else
			exp.ponTipo(programa.tipoOk());
	}
	public void procesa(Index i) {
		i.buffer().procesaCon(this);
		i.index().procesaCon(this);
		if (I.esArray(i.buffer().tipo()) && I.esInt(i.index().tipo())) {
			if (I.esRef(I.tarray(i.buffer().tipo()).tbase())) {
				i.ponTipo(I.tref(I.tarray(i.buffer().tipo()).tbase()).declaracion().tipoDec());
			} 
			else i.ponTipo(I.tarray(i.buffer().tipo()).tbase());
		} 
		else {
			if (!i.buffer().tipo().equals(programa.tipoError()))
				errores.msg(i.enlaceFuente()+":"+ERROR_INDEX_ARRAY);
			if (!i.index().tipo().equals(programa.tipoError()))
				errores.msg(i.enlaceFuente()+":"+ERROR_INDEX_INNDICE);
			i.ponTipo(programa.tipoError());
		}
	}
	public void procesa(Select s) {
		s.registro().procesaCon(this);
		if (I.esRegistro(s.registro().tipo())) {
			if (I.esRef(s.registro().tipo())) {
				s.ponTipo(I.tregistro(I.tref(s.registro().tipo()).declaracion().tipoDec()).getTipoByCampo(s.campo()));
			} 
			else s.ponTipo(I.tregistro(s.registro().tipo()).getTipoByCampo(s.campo()));
		} 
		else {
			errores.msg(s.enlaceFuente()+":"+ERROR_SELECT_TIPOREG);
			s.ponTipo(programa.tipoError());
		}
	}
	public void procesa(Prog p) {
		boolean ok = true;
		for (Dec d : p.decs()) {
			d.procesaCon(this);
			if (I.esProc(d)) {
				ok = ok && (I.proc(d).cuerpo().tipo() == programa.tipoOk());
			}
		}
		p.inst().procesaCon(this);
		ok = ok && (p.inst().tipo() == programa.tipoOk());
		if (ok) {
			p.ponTipo(programa.tipoOk());
		} else {
			p.ponTipo(programa.tipoError());
		}
	}
	public void procesa(IAsig i) {
		i.mem().procesaCon(this);
		i.exp().procesaCon(this);
		if(sonCompatibles(i.mem().tipo(),i.exp().tipo())) {
			i.ponTipo(programa.tipoOk());    
		}
		else {
			if (! i.mem().tipo().equals(programa.tipoError()) && 
					! i.exp().tipo().equals(programa.tipoError())) {
				errores.msg(i.enlaceFuente()+":"+ERROR_ASIG);
			}     
			i.ponTipo(programa.tipoError()); 
		}
	}     

	public void procesa(INew i) {
		i.mem().procesaCon(this);
		if (I.esPointer(i.mem().tipo())) {
			i.ponTipo(programa.tipoOk()); 
		}
		else {
			if (! i.mem().tipo().equals(programa.tipoError())) {
				errores.msg(i.enlaceFuente()+":"+ERROR_NEW);
			}
			i.ponTipo(programa.tipoError());
		}
	}

	public void procesa(IFree i) {
		i.mem().procesaCon(this);
		if (I.esPointer(i.mem().tipo())) {
			i.ponTipo(programa.tipoOk()); 
		}
		else {
			if (! i.mem().tipo().equals(programa.tipoError())) {
				errores.msg(i.enlaceFuente()+":"+ERROR_FREE);
			}
			i.ponTipo(programa.tipoError());
		}
	}

	public void procesa(IBloque b) {
		boolean ok = true;
		for (Dec d : b.decs()) {
			d.procesaCon(this);
			if (I.esProc(d)) {
				ok = ok && (I.proc(d).cuerpo().tipo() == programa.tipoOk());
			}
		}
		for (Inst i : b.is()) {
			i.procesaCon(this);
			ok = ok && i.tipo().equals(programa.tipoOk());
		}
		if (ok) 
			b.ponTipo(programa.tipoOk());
		else
			b.ponTipo(programa.tipoError());   
	}     

	public void procesa(IIf i) {
		i.exp().procesaCon(this);
		if (!i.exp().tipo().equals(programa.tipoError())
				&& !i.exp().tipo().equals(programa.tipoBool())) {
			errores.msg(i.enlaceFuente()+":"+ERROR_COND);

		}
		i.cuerpo().procesaCon(this);
		if (i.exp().tipo().equals(programa.tipoBool())
				&& i.cuerpo().tipo().equals(programa.tipoOk())) {
			i.ponTipo(programa.tipoOk());
		} else {
			i.ponTipo(programa.tipoError());
		}
	}

	public void procesa(IIfElse i) {
		i.exp().procesaCon(this);
		if (!i.exp().tipo().equals(programa.tipoError())
				&& !i.exp().tipo().equals(programa.tipoBool())) {
			errores.msg(i.enlaceFuente()+":"+ERROR_COND);

		}
		i.cuerpo1().procesaCon(this);
		i.cuerpo2().procesaCon(this);
		if (i.exp().tipo().equals(programa.tipoBool())
				&& i.cuerpo1().tipo().equals(programa.tipoOk())
				&& i.cuerpo2().tipo().equals(programa.tipoOk())) {
			i.ponTipo(programa.tipoOk());
		} else {
			i.ponTipo(programa.tipoError());
		}
	}

	public void procesa(ISwitch s) { 
		s.exp().procesaCon(this);
		if (!s.exp().tipo().equals(programa.tipoError())
				&& s.exp().tipo().equals(programa.tipoString())) {
			errores.msg(s.enlaceFuente()+":"+ERROR_SWITCH_EXP);

		}
		boolean error = false;
		for (Case c : s.cases()) {
			c.getVal().procesaCon(this);

			if (!c.getVal().tipo().equals(s.exp().tipo())
					&& !(c.getVal().tipo().equals(programa.tipoInt())
							&& s.exp().tipo().equals(programa.tipoReal()))
					&& !(c.getVal().tipo().equals(programa.tipoReal()) 
							&& s.exp().tipo().equals(programa.tipoInt()))) {

				errores.msg(s.enlaceFuente()+":"+ERROR_SWITCH_CASE);
				error = true;
			}

			c.getInst().procesaCon(this);
			if (c.getInst().tipo().equals(programa.tipoError())) error = true;
		}
		if (s.defaultCase() != null) {
			s.defaultCase().procesaCon(this);
			if (s.defaultCase().tipo() == programa.tipoError()) error = true;
		}
		if (!error) s.ponTipo(programa.tipoOk());
		else s.ponTipo(programa.tipoError());
	}

	public void procesa(IWhile i) {
		i.exp().procesaCon(this);
		if (! i.exp().tipo().equals(programa.tipoError()) &&
				! i.exp().tipo().equals(programa.tipoBool())) {
			errores.msg(i.enlaceFuente()+":"+ERROR_COND);

		}   
		i.cuerpo().procesaCon(this);
		if(i.exp().tipo().equals(programa.tipoBool()) &&
				i.cuerpo().tipo().equals(programa.tipoOk())) {
			i.ponTipo(programa.tipoOk()); 
		}
		else {
			i.ponTipo(programa.tipoError()); 
		}
	}

	public void procesa(IDoWhile i) {
		i.exp().procesaCon(this);
		if (!i.exp().tipo().equals(programa.tipoError())
				&& !i.exp().tipo().equals(programa.tipoBool())) {
			errores.msg(i.enlaceFuente()+":"+ERROR_COND);

		}
		i.cuerpo().procesaCon(this);
		if (i.exp().tipo().equals(programa.tipoBool())
				&& i.cuerpo().tipo().equals(programa.tipoOk())) {
			i.ponTipo(programa.tipoOk());
		} else {
			i.ponTipo(programa.tipoError());
		}
	}

	public void procesa(ICall c) {
		DecProc dec = c.declaracion();
		if (dec.fparams().length != c.aparams().length) {
			errores.msg(c.enlaceFuente()+":"+ERROR_NUM_PARAMETROS +"("+c.idproc()+")");
			for (Exp e : c.aparams()) {
				e.procesaCon(this);
			}
			c.ponTipo(programa.tipoError());
		} 
		else {
			boolean error = false;
			for (int i = 0; i < dec.fparams().length; i++) {
				if (dec.fparams()[i].esParametroPorReferencia()
						&& !c.aparams()[i].esMen()) {
					errores.msg(c.enlaceFuente()+":"+ERROR_MODO_PARAMETRO+"("+c.idproc()+", param:"+(i + 1)+")");
					error = true;
				}
				c.aparams()[i].procesaCon(this);
				if (!sonCompatibles(dec.fparams()[i].tipoDec(), c.aparams()[i].tipo())) {
					if (!c.aparams()[i].tipo().equals(programa.tipoError())) {
						errores.msg(c.enlaceFuente()+":"+ ERROR_TIPO_PARAMETRO+"("+c.idproc()+", param:"+(i + 1)+")");
					}
					error = true;
				}
			}
			if (error) c.ponTipo(programa.tipoError());
			else c.ponTipo(programa.tipoOk());
		}
	}

	public void procesa(DecProc d) {
		d.cuerpo().procesaCon(this);
	}

	public void procesa(TArray t) {
	t.tbase().procesaCon(this);

		if (t.tbase().equals(programa.tipoError())) {
			errores.msg(t.enlaceFuente() + ":" + ERROR_TIPO_BASE);
			t.ponTipo(programa.tipoError());
		} else
			t.ponTipo(programa.tipoOk());
	}

	private static class Tipox2 {
		private Tipo t1;
		private Tipo t2;
		public Tipox2 (Tipo t1, Tipo t2) {
			this.t1 = t1;
			this.t2 = t2;
		}
		public boolean equals(Object o) {
			return (o instanceof Tipox2) &&
					t1.equals(((Tipox2)o).t1) &&
					t2.equals(((Tipox2)o).t2);                 
		}
		public int hashCode() {
			return t1.hashCode()+t2.hashCode();
		}
	}

	private boolean sonCompatibles(Tipo t1, Tipo t2) {
		return sonCompatibles(t1,t2,new HashSet<Tipox2>()); 
	}
	private boolean sonCompatibles(Tipo t1, Tipo t2, Set<Tipox2> considerados) { 
		t1 = I.tipoBase(t1);
		t2 = I.tipoBase(t2);
		if(considerados.add(new Tipox2(t1,t2))) {
			if(!t1.equals(programa.tipoError()) && t1.equals(t2)) return true;        
			else if (I.esPointer(t1) && I.esPointer(t2)) {
				return sonCompatibles(I.tpointer(t1).tbase(),I.tpointer(t2).tbase(),considerados);
			}     
			else if (I.esPointer(t1) && I.esNull(t2)) {
				return true;
			}
			else if (I.esArray(t1) && I.esArray(t2)) {
				return I.tarray(t1).dimension() == I.tarray(t2).dimension()
						&& sonCompatibles(I.tarray(t1).tbase(), I.tarray(t2)
								.tbase(), considerados);
			} 
			else if (I.esRegistro(t1) && I.esRegistro(t2)) {
				if (I.tregistro(t1).getCampos().length != I.tregistro(t2)
						.getCampos().length)
					return false;
				boolean sonComp = true;
				int i = 0;
				while (i < I.tregistro(t1).getCampos().length && sonComp) {
					if (!I.tregistro(t1).getCampos()[i].getId().equals(I.tregistro(t2).getCampos()[i].getId())) {
						sonComp = false;
					} 
					else sonComp = sonComp 
							&& sonCompatibles(I.tregistro(t1).getCampos()[i].getTipo(), I.tregistro(t2).getCampos()[i].getTipo());
				}

				return sonComp;

			} 
			else return false;
		}
		else return true;
	} 
}
