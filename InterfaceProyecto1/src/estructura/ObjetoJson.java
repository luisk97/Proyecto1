package estructura;

import java.util.Scanner;

public class ObjetoJson {
    private ObjetoJson siguiente;
    private String nombre;
    private Object tipo;
    private Object tipoEspecial;
    private boolean requerido;
    private Object valorPredeterminado;
    
    public ObjetoJson(String nombre,Object tipo,Object tipoEsp,String requerido){
        this.nombre = nombre;
        this.tipo = tipo;
        tipoEspecial = tipoEsp;
        if("si".equals(requerido)){
            this.requerido = true;
        }else{
            this.requerido = false;
            Scanner s = new Scanner(System.in);
            System.out.println("Ingrese el valor predetermido:");
            Object valor;
            valor = s.next();
            valorPredeterminado = valor;
        }
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
    
    public void enlazarSiguiente(ObjetoJson siguiente){
        this.siguiente = siguiente;
    }
    
    public ObjetoJson obtenerSiguiente(){
        return siguiente;
    }
}
