package estructura;

import java.io.Serializable;

/**
 * Esta clase se define que un DocumentoJson guardara el nombre del documento y la lista de atributos que posee
 * @author luisk
 */
public class DocumentoJson implements Serializable{
    private String nombre;
    private ListaEnlazada lista;
    private DocumentoJson siguiente;
    private DocumentoJson anterior;
    
    /**
     * Constructor
     * @param nombre
     */
    public DocumentoJson(String nombre){
        this.nombre = nombre;
        lista = new ListaEnlazada();;
    }
    
    /**
     * Nos devuleve la variable nombre
     * @return
     */
    public String obtenerNombre(){
        return nombre;
    }
    
    /**
     * se le ingresa un valor a la variable siguiente de tipo DocumentoJson
     * @param n
     */
    public void enlazarSiguiente(DocumentoJson n){
        siguiente = n;
    }
    
    /**
     * se le ingresa un valor a la variable anterior de tipo DocumentoJson
     * @param a
     */
    public void enlazarAnterior(DocumentoJson a){
        anterior = a;
    }
    
    /**
     * nos retornala variable siguiente
     * @return
     */
    public DocumentoJson obtenerSiguiente(){
        return siguiente;
    }
    
    /**
     * nos retornala variable anterior
     * @return
     */
    public DocumentoJson obtenerAnterior(){
        return anterior;
    }
    
    /**
     * nos retorna la variable lista
     * @return
     */
    public ListaEnlazada obtenerLista(){
        return lista;
    }
}
