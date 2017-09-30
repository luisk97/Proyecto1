
package estructura;

import java.io.Serializable;

/**
 * Esta clase se define que un JsonStore guardara el nombre del store y la lista de documentos que posee
 * @author luisk
 */
public class JsonStore implements Serializable{
    private String nombre;
    private ListaDoblementeEnlazadaCircular lista;
    private JsonStore siguiente;
    private JsonStore anterior;
    
    /**
     *Constructor
     * @param nombre
     */
    public JsonStore(String nombre){
        this.nombre = nombre;
        lista = new ListaDoblementeEnlazadaCircular();
    }
    
    /**
     * Se le asigna un valor a la variable nombre
     * @param nombre
     */
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    /**
     * Retorna el valor de la variable nombre
     * @return
     */
    public String obtenerNombre(){
        return nombre;
    }
    
    /**
     * se verifica si la variable lista es nula
     * @return
     */
    public boolean estaVacio(){
        return (lista==null);
    }
    
    /**
     * imprime en consola la lista que esta contenida dentro del JsonStore
     */
    public void verLista(){
        lista.obtenerLista();
    }
    
    /**
     * 
     * @return
     */
    public ListaDoblementeEnlazadaCircular obtenerLista(){
        return lista;
    }
    
    /**
     *
     * @param n
     */
    public void enlazarSiguiente(JsonStore n){
        siguiente = n;
    }
    
    /**
     *
     * @param a
     */
    public void enlazarAnterior(JsonStore a){
        anterior = a;
    }
    
    /**
     *
     * @return
     */
    public JsonStore obtenerSiguiente(){
        return siguiente;
    }
    
    /**
     *
     * @return
     */
    public JsonStore obtenerAnterior(){
        return anterior;
    }

}
