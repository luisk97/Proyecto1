package estructura;

import java.io.Serializable;

public class DocumentoJson implements Serializable{
    private String nombre;
    private ListaEnlazada lista;
    //private Object valor;
    private DocumentoJson siguiente;
    private DocumentoJson anterior;
    
    public DocumentoJson(String nombre){
        this.nombre = nombre;
        lista = new ListaEnlazada();;
    }
    
//    public Object obtenerValor(){
//        return valor;
//    }
    
    public String obtenerNombre(){
        return nombre;
    }
    
    public void enlazarSiguiente(DocumentoJson n){
        siguiente = n;
    }
    
    public void enlazarAnterior(DocumentoJson a){
        anterior = a;
    }
    
    public DocumentoJson obtenerSiguiente(){
        return siguiente;
    }
    
    public DocumentoJson obtenerAnterior(){
        return anterior;
    }
    
    public ListaEnlazada obtenerLista(){
        return lista;
    }
}
