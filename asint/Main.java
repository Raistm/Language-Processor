package asint;
import errores.Errores;
import java.io.FileReader;
import maquinaP.MaquinaP;
import procesamientos.comprobaciontipos.ComprobacionTipos;
import procesamientos.comprobaciontipos.Vinculacion;
import procesamientos.generacioncodigo.AsignacionEspacio;
import procesamientos.generacioncodigo.Etiquetado;
import procesamientos.generacioncodigo.GeneracionDeCodigo;
import procesamientos.impresion.Impresion;
public class Main{
   public static void main(String[] args) throws Exception {
     try{   
      AnalizadorSintacticoTiny asint = new AnalizadorSintacticoTiny(new FileReader(args[0]));
      Errores errores = new Errores();
      ASTOps ops = new ASTOps(errores);
      asint.setOps(ops);
      asint.inicio();
      new Impresion().procesa(ops.raiz());
      Vinculacion vinculacion = new Vinculacion(errores);
      ops.raiz().procesaCon(vinculacion);
      if (vinculacion.error()) System.exit(1);
      ComprobacionTipos tipado = new ComprobacionTipos(ops,errores);
      ops.raiz().procesaCon(tipado);
      if (ops.raiz().tipo() != ops.tipoError()) {
          AsignacionEspacio asignacionEspacio = new AsignacionEspacio();
          ops.raiz().procesaCon(asignacionEspacio);
          Etiquetado etiquetado = new Etiquetado();
          ops.raiz().procesaCon(etiquetado);
          MaquinaP maquina = new MaquinaP(asignacionEspacio.tamanioDatos(),50,10,asignacionEspacio.numDisplays());
          GeneracionDeCodigo generacioncod = new GeneracionDeCodigo(maquina,ops);
          ops.raiz().procesaCon(generacioncod);
          System.out.println();
          maquina.ejecuta();
          System.out.println();
          maquina.muestraEstado();
      }
     }
     catch(TokenMgrError err) {
         System.err.println(err.getMessage());
     }     
     catch(ParseException err) {
         System.err.println(err.getMessage());
     }     
   }
}