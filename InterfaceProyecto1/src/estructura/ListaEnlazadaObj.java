/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructura;

import java.io.Serializable;

/**
 *Esta ee la clase lista que almacenara nos servira para enlazar y recorrer los ObjetosJson de cada atributo
 * @author luisk
 */
public class ListaEnlazadaObj implements Serializable{
    private ObjetoJson cabeza;
    private ObjetoJson ultimo;
    private int size;
    
    /**
     * constructor
     */
    public ListaEnlazadaObj(){
        cabeza = null;
        ultimo = null;
        size = 0;
    }
    
    /**
     * Este metodo nos devuelve un string con una lista de todos los datos que contiene la ListaEnlazadaObj
     * @return lista
     */
   public String obtenerLista(){
        ObjetoJson temp = cabeza;
        if(cabeza == null){
            return("DocumentoJson vacio");
        }else{
            String lista = "";
            for(int i = 0;i < size;i++){
                lista += (temp.obtenerValor()+",  ");
                temp = temp.obtenerSiguiente();
            }
            return lista;
        }
    }
   
   /**
    * Nos permite obtener 
    * @param index
    * @return 
    */
    public ObjetoJson obtener(int index){
        ObjetoJson temp = cabeza;
        while(0 < index){
            temp = temp.obtenerSiguiente();
            index--;
        }
        return temp;
    }
    
    /**
     * se introduce el valor y busca el nodo que lo tenga
     * @param valor
     * @return 
     */
    public ObjetoJson obtenerPorValor(String valor){
        ObjetoJson temp = cabeza;
        while(temp != null){
            if(temp.obtenerValor().equals(valor)){
                return temp;
            }else{
                temp = temp.obtenerSiguiente();
            }
        }
        return null;
    }

    /**
     * se introducce el valor y devuelve el indice en el que se encuentra
     * @param valor
     * @return 
     */
    public int obtenerIndice(String valor){
        ObjetoJson temp = cabeza;
        int cont = 0;
        while(temp != null){
            if(temp.obtenerValor().equals(valor)){
                return cont;
            }else{
                temp = temp.obtenerSiguiente();
                cont++;
            }
        }
        return -1;
    }
    
    
    public int obtenerIndices(String valor,int cont){
        ObjetoJson temp = cabeza;
        for(int i = 0;i <cont;i++){
            temp = temp.obtenerSiguiente();
        }
        while(temp != null){
            if(temp.obtenerValor().equals(valor)){
                return cont;
            }else{
                temp = temp.obtenerSiguiente();
                cont++;
            }
        }
        return -1;
    }
    
    /**
     * regresa el ultimo nodo
     * @return 
     */
    public Object obtenerUltimo(){
        return ultimo;
    } 

    
    public void insertar(int ind,int num){
        ObjetoJson temp = cabeza;
        ObjetoJson nuevo = new ObjetoJson(num);
        for(int i = 0;i<(ind-1);i++){
            temp.obtenerSiguiente();
        }
        nuevo.enlazarSiguiente(temp.obtenerSiguiente());
        temp.enlazarSiguiente(nuevo);
        size++;
    }
    
    /**
     * se introduce el valor y se crea un nuevo nodo con ese valor y se añade a la lista
     * @param valor 
     */
    public void add(Object valor){
        if(cabeza == null){
            cabeza = new ObjetoJson(valor);
            ultimo = cabeza;
        }else{
            ObjetoJson temp = ultimo;
            ObjetoJson nuevo = new ObjetoJson(valor);
            temp.enlazarSiguiente(nuevo);
            ultimo = nuevo;
            }
        size++;
    }
    
    public String addComoLlave(Object valor){
        if(cabeza == null){
            cabeza = new ObjetoJson(valor);
            ultimo = cabeza;
        }else{
            ObjetoJson actual = cabeza;
            while(actual != null){
                if(actual.obtenerValor().equals(valor)){
                    return("existe");
                }else{
                    actual = actual.obtenerSiguiente();
                }
            }
            ObjetoJson temp = ultimo;
            ObjetoJson nuevo = new ObjetoJson(valor);
            temp.enlazarSiguiente(nuevo);
            ultimo = nuevo;
            }
        size++;
        return("");
    }
    
    /**
     * se introduce el indice y se elimina el nodo que este en esa posicion
     * @param indice 
     */
    public void eliminar(int indice){
        if(indice == 0){
            cabeza = cabeza.obtenerSiguiente();
        }else if (indice == (size-1)){
            ObjetoJson temp = cabeza;
            for(int i = 0;i < (indice-1);i++){
                temp = temp.obtenerSiguiente();
            }
            temp.enlazarSiguiente(temp.obtenerSiguiente().obtenerSiguiente());
            ultimo = temp;
        }else{
            ObjetoJson temp = cabeza;
            for(int i = 0;i < (indice-1);i++){
                temp = temp.obtenerSiguiente();
            }
            temp.enlazarSiguiente(temp.obtenerSiguiente().obtenerSiguiente());
        }
        size--;
    }

    /**
     * regresa el tamaño de la lista
     * @return 
     */
    public int size(){
        return size;
    }
    
    public boolean estaVacio(){
        return (cabeza==null);
    }
    
}

