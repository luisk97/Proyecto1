/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructura;

/**
 *
 * @author luisk
 */
public class ObjetoJson {
    private Object valor;
    private ObjetoJson siguiente;
    
    
    public ObjetoJson(Object valor){
        this.valor = valor;
        siguiente = null;
    }
    
    
    public Object obtenerValor(){
        return valor;
    }
    
    public void enlazarSiguiente(ObjetoJson n){
        siguiente = n;
    }
    
    public ObjetoJson obtenerSiguiente(){
        return siguiente;
    }
    
}
