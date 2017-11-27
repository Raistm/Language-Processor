package procesamientos;

import programa.Programa.CteInt;
import programa.Programa.CteReal;
import programa.Programa.CteString;
import programa.Programa.CteBool;
import programa.Programa.CteChar;
import programa.Programa.TArray;
import programa.Programa.TNull;
import programa.Programa.TRegistro;
import programa.Programa.Var;
import programa.Programa.DRef;
import programa.Programa.DecProc;
import programa.Programa.Suma;
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
import programa.Programa.ICall;
import programa.Programa.IDoWhile;
import programa.Programa.IWhile;
import programa.Programa.Igual;
import programa.Programa.Index;
import programa.Programa.IFree;
import programa.Programa.IIf;
import programa.Programa.IIfElse;
import programa.Programa.INew;
import programa.Programa.ISwitch;
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
import programa.Programa.TPointer;

public class Procesamiento {
   public void procesa(Prog p) {} 
   public void procesa(CteInt exp) {} 
   public void procesa(CteBool exp) {} 
   public void procesa(CteString exp) {}
   public void procesa(CteChar exp) {}
   public void procesa(CteReal exp) {}
   public void procesa(Null i) {}
   public void procesa(Var exp) {} 
   public void procesa(DRef exp) {} 
   public void procesa(Select exp) {}
   public void procesa(Index exp) {}
   public void procesa(Suma exp) {} 
   public void procesa(Resta exp) {} 
   public void procesa(Multi exp) {} 
   public void procesa(Divi exp) {} 
   public void procesa(RestInt exp) {}
   public void procesa(CambiaSign exp) {}    
   public void procesa(ElementoCadena exp) {}
   public void procesa(ConversionReal exp) {}
   public void procesa(ConversionInt exp) {}
   public void procesa(ConversionChar exp) {}
   public void procesa(ConversionBool exp) {}
   public void procesa(ConversionString exp) {}
   public void procesa(Igual exp) {}
   public void procesa(Mayor exp) {}
   public void procesa(Menor exp) {}
   public void procesa(MayorIgual exp) {}
   public void procesa(MenorIgual exp) {}
   public void procesa(Distinto exp) {}
   public void procesa(And exp) {}
   public void procesa(Or exp) {}
   public void procesa(Not exp) {}
   public void procesa(Lee exp) {} 
   public void procesa(Escribe exp) {}
   public void procesa(Int t) {}     
   public void procesa(Bool t) {} 
   public void procesa(Real t) {}
   public void procesa(Char t) {}
   public void procesa(StringR t) {}
   public void procesa(TRef t) {}     
   public void procesa(TPointer t) {} 
   public void procesa(TArray t){}
   public void procesa(TNull t) {}
   public void procesa(TRegistro t) {}
   public void procesa(Ok t) {}     
   public void procesa(Error t) {}     
   public void procesa(DecVar d) {}     
   public void procesa(DecTipo d) {}   
   public void procesa(DecProc d) {}
   public void procesa(IAsig i) {}     
   public void procesa(INew i) {}     
   public void procesa(IFree i) {}     
   public void procesa(IBloque i) {}     
   public void procesa(IWhile i) {}
   public void procesa(IIf i) {}
   public void procesa(IIfElse i) {}
   public void procesa(IDoWhile i) {}
   public void procesa(ISwitch i) {}
   public void procesa(ICall i) {} 
}
