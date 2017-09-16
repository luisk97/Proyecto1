
package estructura;

import java.io.Serializable;

public class JsonStore implements Serializable{
    private String nombre;
    //private Object valor;
    private ListaDoblementeEnlazadaCircular lista;
    private JsonStore siguiente;
    private JsonStore anterior;
    
    public JsonStore(String nombre){
        this.nombre = nombre;
        lista = new ListaDoblementeEnlazadaCircular();
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String obtenerNombre(){
        return nombre;
    }
    
    //public Object obtenerValor(){
        //return valor;
    //}
    public boolean estaVacio(){
        return (lista==null);
    }
    
    public void verLista(){
        lista.obtenerLista();
    }
    
    public ListaDoblementeEnlazadaCircular obtenerLista(){
        return lista;
    }
    
    public void enlazarSiguiente(JsonStore n){
        siguiente = n;
    }
    
    public void enlazarAnterior(JsonStore a){
        anterior = a;
    }
    
    public JsonStore obtenerSiguiente(){
        return siguiente;
    }
    
    public JsonStore obtenerAnterior(){
        return anterior;
    }
}
