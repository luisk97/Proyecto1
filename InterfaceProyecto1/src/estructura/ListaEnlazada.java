package estructura;

public class ListaEnlazada {
    private ObjetoJson cabeza;
    private ObjetoJson ultimo;
    private int size;
    
    
    public ListaEnlazada(){
        cabeza = null;
    }
    
    public void addAtributo(String nombre,Object tipo,Object tipoEsp,String requerido){
        if(cabeza==null){
            cabeza = new ObjetoJson(nombre,tipo,tipoEsp,requerido);
            ultimo = cabeza;
        }else{
        ObjetoJson temp = ultimo;
        ObjetoJson nuevo = new ObjetoJson(nombre,tipo,tipoEsp,requerido);
        temp.enlazarSiguiente(nuevo);
        ultimo = nuevo;
        }
        size++;
    }
    
    public void verAtributos(){
        if(cabeza!=null){
            ObjetoJson temp = cabeza;
            for(int i = 0;i<size;i++){
                System.out.println(temp.obtenerNombre());
                temp = temp.obtenerSiguiente();
            }
        }else{
            System.out.println("No hay atributos");
        }
    }
    
    public ObjetoJson obtenerAtributo(int ind){
        ObjetoJson temp = cabeza;
            for(int i = 0;i<(size-1);i++){
                temp = temp.obtenerSiguiente();
            }
        return temp;
        }
    }
