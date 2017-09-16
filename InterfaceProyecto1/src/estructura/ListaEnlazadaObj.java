/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructura;

import java.io.Serializable;

/**
 *
 * @author luisk
 */
public class ListaEnlazadaObj implements Serializable{
    private ObjetoJson cabeza;
    private ObjetoJson ultimo;
    private int size;

    public ListaEnlazadaObj(){
        cabeza = null;
        ultimo = null;
        size = 0;
    }
    
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
   
   
    public ObjetoJson obtener(int index){
        ObjetoJson temp = cabeza;
        while(0 < index){
            temp = temp.obtenerSiguiente();
            index--;
        }
        return temp;
    }
    
    public ObjetoJson obtenerPorValor(int num){
        ObjetoJson temp = cabeza;
        while(temp != null){
            if(temp.obtenerValor().equals(num)){
                return temp;
            }else{
                temp = temp.obtenerSiguiente();
            }
        }
        return null;
    }

    
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
    
    
    public void eliminar(int indice){
        if(indice == 0){
            cabeza = cabeza.obtenerSiguiente();
        }else{
            ObjetoJson temp = cabeza;
            for(int i = 0;i < (indice-1);i++){
                temp = temp.obtenerSiguiente();
            }
            temp.enlazarSiguiente(temp.obtenerSiguiente());
        }
        size--;
    }
    
    
    
    public void eliminarPrimero(){
        cabeza = cabeza.obtenerSiguiente();
        size--;
  }
    
    public int size(){
        return size;
    }
    
    public boolean estaVacio(){
        return (cabeza==null);
    }
    
}

