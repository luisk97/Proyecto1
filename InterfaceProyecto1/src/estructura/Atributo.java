package estructura;

import java.io.Serializable;

/**
 * Esta clase guardara el nombre del atributo ademas de las propiedades de este
 * @author luisk
 */
public class Atributo implements Serializable{
    private Atributo siguiente;
    private String nombre;
    private Object tipo;
    private Object tipoEspecial;
    private boolean requerido;
    private Object valorPredeterminado;
    private ListaEnlazadaObj lista;
    
    /**
     * Constructor
     * @param nombre
     * @param tipo
     * @param tipoEsp
     * @param requerido
     * @param valorPredeterminado
     */
    public Atributo(String nombre,Object tipo,Object tipoEsp,boolean requerido, Object valorPredeterminado){
        this.nombre = nombre;
        this.tipo = tipo;
        tipoEspecial = tipoEsp;
    this.requerido = requerido;
        this.valorPredeterminado = valorPredeterminado;
        lista = new ListaEnlazadaObj();
    }
    
    /**
     * metodo que nos permite ver las caracteristicas de un atributo en consola
     */
    public void verCaracteristicas(){
        System.out.println("Nombre: "+nombre);
        System.out.println("Tipo: "+tipo);
        System.out.println("Tipo especial: "+tipoEspecial);
        System.out.println("Requerido: "+requerido);
    }
    
    /**
     * Nos devuelve la variable nombre
     * @return
     */
    public String obtenerNombre(){
        return nombre;
    }
    
    /**
     * nos devuelve la variable tipo
     * @return
     */
    public Object obtenerTipo(){
        return tipo;
    }
    
    /**
     * nos devuelve la variable tipoEspecial
     * @return
     */
    public Object obtenerTipoEspecial(){
        return tipoEspecial;
    }
    
    /**
     * Se introduce un nodo Atributo en la variable siguiente
     * @param siguiente
     */
    public void enlazarSiguiente(Atributo siguiente){
        this.siguiente = siguiente;
    }
    
    /**
     * nos devuelve el valor de la variable siguiente
     * @return
     */
    public Atributo obtenerSiguiente(){
        return siguiente;
    }
    
    /**
     * nos devuelve el valor de la variable lista
     * @return
     */
    public ListaEnlazadaObj obtenerLista(){
        return lista;
    }
    
    /**
     * 
     * @param pValor
     */
    public void prueba(String pValor){
        lista.add(pValor);
    }
    
    /**
     * nos retorna el valor de la variable requerido
     * @return
     */
    public boolean requerido(){
        return requerido;
    }
    
    /**
     * nos retorna el valor de la variable valorPredeterminado
     * @return
     */
    public Object valPorDefecto(){
        return valorPredeterminado;
    }
}
