package maquinaP;

public class GestorMemoriaDinamica {
    private final static boolean DEBUG=false;
    private static class Hueco {
      private int comienzo;
      private int tamanio;
      private Hueco siguiente;
      public Hueco(int comienzo, int tamanio) {
         this.comienzo = comienzo;
         this.tamanio = tamanio;
         siguiente = null;
      }
    }
    private Hueco huecos;
    public GestorMemoriaDinamica(int comienzo, int fin) {
       huecos = new Hueco(comienzo,(fin-comienzo)+1);
       if (DEBUG) {
           System.out.print("INICIO:");
           muestraHuecos();
           System.out.println("----");
       }
    }
    public int alloc(int tamanio) {
      Hueco h = huecos;
      Hueco prev = null;
      while (h != null && h.tamanio < tamanio) {
          prev = h;
          h = h.siguiente;
      }    
      if (h==null) throw new OutOfMemoryError("alloc "+tamanio);
      int dir = h.comienzo;
      h.comienzo += tamanio;
      h.tamanio -= tamanio;
      if (h.tamanio == 0) {
         if (prev == null) huecos = h.siguiente;
         else prev.siguiente = h.siguiente;
      }
      if (DEBUG) {
          System.out.println("alloc("+tamanio+")="+dir);
          muestraHuecos();
          System.out.println("----");
    }
      return dir;
      }
    public void free(int dir, int tamanio) {
     Hueco h = huecos;
     Hueco prev = null;
     while (h != null && h.comienzo < dir) {
        prev = h;
        h = h.siguiente;
     }
     Hueco nuevo = new Hueco(dir,tamanio);
     nuevo.siguiente = h;
     
     if (prev==null) {
         huecos = nuevo;
         prev = huecos;
     }
     else {
         prev.siguiente = nuevo;
         nuevo.siguiente = h;
     }    
     if (prev != null && prev.comienzo + prev.tamanio == nuevo.comienzo) {
         prev.tamanio += nuevo.tamanio;
         prev.siguiente = h;
         if (h != null && prev.comienzo + prev.tamanio == h.comienzo) {
           prev.tamanio += h.tamanio;
           prev.siguiente = h.siguiente;
         }
     }
     if (DEBUG) {
         System.out.println("free("+dir+","+tamanio+")");  
         muestraHuecos();
         System.out.println("----");
     }
  }
  public void muestraHuecos() {
     Hueco h = huecos;
     while (h != null) {
       System.out.print("<"+h.comienzo+","+h.tamanio+","+(h.comienzo+h.tamanio-1)+">");
       h = h.siguiente;
     }
     System.out.println();
  }  
}    
         
             
      
     
     
     
     
     