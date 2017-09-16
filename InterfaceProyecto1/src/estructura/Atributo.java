package estructura;

import java.util.Scanner;

public class Atributo {
    private Atributo siguiente;
    private String nombre;
    private Object tipo;
    private Object tipoEspecial;
    private boolean requerido;
    private Object valorPredeterminado;
    private ListaEnlazadaObj lista;
    
    public Atributo(String nombre,Object tipo,Object tipoEsp,boolean requerido, Object valorPredeterminado){
        this.nombre = nombre;
        this.tipo = tipo;
        tipoEspecial = tipoEsp;
    this.requerido = requerido;
        this.valorPredeterminado = valorPredeterminado;
        lista = new ListaEnlazadaObj();
    }
    
    public void verCaracteristicas(){
        System.out.println("Nombre: "+nombre);
        System.out.println("Tipo: "+tipo);
        System.out.println("Tipo especial: "+tipoEspecial);
        System.out.println("Requerido: "+requerido);
    }
    
    public String obtenerNombre(){
        return nombre;
    }
    
    public Object obtenerTipo(){
        return tipo;
    }
    
    public Object obtenerTipoEspecial(){
        return tipoEspecial;
    }
    
    public void enlazarSiguiente(Atributo siguiente){
        this.siguiente = siguiente;
    }
    
    public Atributo obtenerSiguiente(){
        return siguiente;
    }
    
    public ListaEnlazadaObj obtenerLista(){
        return lista;
    }
    
    public void prueba(String pValor){
        lista.add(pValor);
    }
            
           
}
