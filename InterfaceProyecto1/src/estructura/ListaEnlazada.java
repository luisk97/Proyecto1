package estructura;

public class ListaEnlazada {
    private Atributo cabeza;
    private Atributo ultimo;
    private int size;
    
    
    public ListaEnlazada(){
        cabeza = null;
    }
    
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
    
    public Atributo obtenerAtributo(int ind){
        Atributo temp = cabeza;
        for(int i = 0;i<(size-1);i++){
            temp = temp.obtenerSiguiente();
        }
        return temp;
    }
    
    public Atributo obtenerCabeza(){
       return cabeza;
   }
    
    public void addObjeto(Object valor){
        Atributo temp = cabeza;
        while(temp != null){
            temp.obtenerLista().add(valor);
            temp = temp.obtenerSiguiente();
        }
    }
    
    public int size(){
        return size;
    }
}
