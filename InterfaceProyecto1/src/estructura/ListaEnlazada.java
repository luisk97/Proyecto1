package estructura;

import java.io.Serializable;

/**
 * En esta clase se define que va a manipular o a ser la lista que contenga los 
 * nodos Atributo
 * @author luisk
 */
public class ListaEnlazada implements Serializable{
    private Atributo cabeza;
    private Atributo ultimo;
    private int size;
    
    /**
     * Constructor
     */
    public ListaEnlazada(){
        cabeza = null;
    }
    
    /**
     * Este metodo permitira añadir y definir un nuevo Atributo
     * @param nombre
     * @param tipo
     * @param tipoEsp
     * @param requerido
     * @param predefinido
     */
    public void addAtributo(String nombre,String tipo,String tipoEsp,boolean requerido,String predefinido){
        if(cabeza==null){
            cabeza = new Atributo(nombre,tipo,tipoEsp,requerido,predefinido);
            ultimo = cabeza;
        }else{
        Atributo temp = ultimo;
        Atributo nuevo = new Atributo(nombre,tipo,tipoEsp,requerido,predefinido);
        temp.enlazarSiguiente(nuevo);
        ultimo = nuevo;
        }
        size++;
    }
    
    /**
     * Mostrara los atributos creados en consola
     */
    public void verAtributos(){
        if(cabeza!=null){
            Atributo temp = cabeza;
            for(int i = 0;i<size;i++){
                System.out.print(temp.obtenerNombre()+", ");
                temp = temp.obtenerSiguiente();
            }
        }else{
            System.out.println("No hay atributos");
        }
        System.out.println("");
    }
    
    /**
     * Retornara el Atributo que se encuentre en el indice especificado
     * @param ind
     * @return
     */
    public Atributo obtenerAtributo(int ind){
        Atributo temp = cabeza;
        for(int i = 0;i<(size-1);i++){
            temp = temp.obtenerSiguiente();
        }
        return temp;
    }
    
    /**
     * Retornara la variable cabeza
     * @return
     */
    public Atributo obtenerCabeza(){
       return cabeza;
   }
    
    /**
     * Permitira anadir datos a los atributos
     * @param valor
     */
    public void addObjeto(Object valor){
        Atributo temp = cabeza;
        while(temp != null){
            temp.obtenerLista().add(valor);
            temp = temp.obtenerSiguiente();
        }
    }
    
    /**
     * retornara el tamaño de la lista
     * @return
     */
    public int size(){
        return size;
    }
}
