/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructura;

import java.io.Serializable;

/**
 * En esta clase se define que va a contener los datos ingresados en la base de datos
 * @author luisk
 */
public class ObjetoJson implements Serializable{
    private Object valor;
    private ObjetoJson siguiente;
    
    /**
     * Constructor
     * @param valor
     */
    public ObjetoJson(Object valor){
        this.valor = valor;
        siguiente = null;
    }
    
    /**
     * retorna la variable valor
     * @return 
     */
    public Object obtenerValor(){
        return valor;
    }
    
    /**
     * se le asigna un ObjetoJson a la variable siguiente
     * @param n 
     */
    public void enlazarSiguiente(ObjetoJson n){
        siguiente = n;
    }
    
    /**
     * Retorna el valor de la variable siguiente
     * @return 
     */
    public ObjetoJson obtenerSiguiente(){
        return siguiente;
    }
    
}
