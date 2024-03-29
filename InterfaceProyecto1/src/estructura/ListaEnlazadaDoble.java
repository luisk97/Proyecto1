package estructura;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * En esta clase se define que este tipo de lista permitira recorrer los nodos JsonStore
 * @author luisk
 */
public class ListaEnlazadaDoble implements Serializable{
    private JsonStore cabeza;
    private JsonStore ultimo;
    private int size;
    
    /**
     * Constructor
     */
    public ListaEnlazadaDoble(){
        cabeza = null;
        ultimo = null;
        size = 0;
    }
    
    /**
     * Retorna un String con todos los JsonStore contenidos dentro de la lista 
     * @return
     */
    public String obtenerLista(){
        JsonStore temp = cabeza;
        if(cabeza == null){
            return("lista de JsonStore vacia");
        }else{
            String lista = "";
            for(int i = 0;i < size;i++){
                lista += (temp.obtenerNombre()+",  ");
                temp = temp.obtenerSiguiente();
            }
            return lista;
        }
    }
    
    /**
     * retorna el tamaño de la lista
     * @return
     */
    public int imprimirSize(){
        return size;
    }
    
    /**
     * Retorna el JsonStore que se encuentra en el indice especificado
     * @param index
     * @return
     */
    public JsonStore obtener(int index){
        JsonStore temp = cabeza;
        while(0 < index){
            temp = temp.obtenerSiguiente();
            index--;
        }
        return temp;
    }
    
    /**
     * Devuelve el JsonStore con el nombre especificado
     * @param nombre
     * @return
     */
    public JsonStore obtener(String nombre){
        JsonStore temp = cabeza;
        while(temp != null){
            if(temp.obtenerNombre().equals(nombre)){
                return temp;
            }else{
                temp = temp.obtenerSiguiente();
            }
        }
        return null;
    }
    
    /**
     * Retorna el JsonStore que se ecuentra en la variable ultimo
     * @return
     */
    public Object obtenerUltimo(){
            return ultimo;
    } 
    
    /**
     * Nos permite incertar un JsonStore en un indice especifico ademas de verificar 
     * si este ya existe
     * @param ind
     * @param nom
     * @return
     */
    public String insertar(int ind,String nom){
        JsonStore actual = cabeza;
        while(actual != null){
            if(actual.obtenerNombre().equals(nom)){
                System.out.println("Store existente");
                return("Store existente");
            }else{
                actual = actual.obtenerSiguiente();
            }
        }
        JsonStore temp = cabeza;
        JsonStore nuevo = new JsonStore(nom);
        if(ind == 0){
            nuevo.enlazarSiguiente(cabeza);
            cabeza.enlazarAnterior(nuevo);
            cabeza= nuevo;
        }else{
            for(int i = 0;i<(ind-1);i++){
                temp = temp.obtenerSiguiente();
            }
            nuevo.enlazarSiguiente(temp.obtenerSiguiente());
            nuevo.obtenerSiguiente().enlazarAnterior(nuevo);
            temp.enlazarSiguiente(nuevo);
            nuevo.enlazarAnterior(temp);
        }
        size++;
        return("Se incerto a "+nuevo.obtenerNombre()+" en el indice "+ind);
    }
        
    /**
     * Nos permite añadir un nuevo JsonStore ademas de verificar si este ya existe
     * @param nom
     * @return
     */
    public String add(String nom){
        if(cabeza == null){
            cabeza = new JsonStore(nom);
            ultimo = cabeza;
        }else{
            JsonStore actual = cabeza;
            while(actual != null){
                if(actual.obtenerNombre().equals(nom)){
                    System.out.println("Store existente");
                    return("Store existente");
                }else{
                    actual = actual.obtenerSiguiente();
                }
            }
            JsonStore temp = ultimo;
            JsonStore nuevo = new JsonStore(nom);
            temp.enlazarSiguiente(nuevo);
            nuevo.enlazarAnterior(temp);
            ultimo = nuevo;
            }
        size++;
        System.out.println("Se creo el Store: "+ nom);
        return("Se creo el Store: "+ nom);
    }
    
    /**
     * Nos permite eliminar el JsonStore que se encuentra en el indice especificado
     * @param indice
     */
    public void eliminar(int indice){
        if(indice == 0 && cabeza == ultimo){
            cabeza=null;
            ultimo=null;
        }else{
            if(indice == 0){
                cabeza = cabeza.obtenerSiguiente();
                cabeza.enlazarAnterior(null);
            }else{
                JsonStore temp = cabeza;
                for(int i = 0;i < indice;i++){
                    temp = temp.obtenerSiguiente();
                }
                if(indice == (size-1)){
                    ultimo = temp.obtenerAnterior();
                    ultimo.enlazarSiguiente(null);
                }else{
                    temp.obtenerAnterior().enlazarSiguiente(temp.obtenerSiguiente());
                    temp.obtenerSiguiente().enlazarAnterior(temp.obtenerAnterior());
                }
            }
        }
        size--;
    }
    
    /**
     * Nos permite eliminar el JsonStore con el nombre ingresado
     * @param nom
     * @return
     */
    public String eliminar(String nom){
        JsonStore actual = cabeza;
        while(actual != null){
            if(actual.obtenerNombre().equals(nom)){
                if(actual==cabeza && cabeza==ultimo){
                    cabeza=null;
                    ultimo=null;
                    size--;
                    return("Eliminado");
                }else{
                    if(actual == cabeza){
                        cabeza = cabeza.obtenerSiguiente();
                        cabeza.enlazarAnterior(null);
                        size--;
                        return("Eliminado");
                    }else{
                        if(actual == ultimo){
                            ultimo = actual.obtenerAnterior();
                            ultimo.enlazarSiguiente(null);
                            size--;
                            return("Eliminado");
                        }
                    }
                    actual.obtenerAnterior().enlazarSiguiente(actual.obtenerSiguiente());
                    actual.obtenerSiguiente().enlazarAnterior(actual.obtenerAnterior());
                    size--;
                    return("Eliminado");
                }
            }
            actual = actual.obtenerSiguiente();
        }
        return("");
     }
     
    /**
     * Elimina el primer nodo JsonStore de la lista
     */
    public void eliminarPrimero(){
        cabeza = cabeza.obtenerSiguiente();
        cabeza.enlazarAnterior(null);
        size--;
  }
    
    /**
     * retorna el tamaño de la lista
     * @return
     */
    public int size(){
        return size;
    }
    
    /**
     * Verifica si la lista esta vacia
     * @return
     */
    public boolean estaVacio(){
        return (cabeza==null);
    }
    public void commit(){
        File file = new File("ListaEnlazadaDoble\\JsonStores.txt");
        if(!file.exists()){
            try{
                file.createNewFile();
                System.out.println(file.getName()+" ha sido Creado");
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }else{
            try{
                PrintWriter pw = new PrintWriter(file);
                pw.println("Primera linea");
                pw.println("Segunda linea");
                pw.println("   ");
                pw.printf("Hola %s por %d veces d/", "mundo", 100);
                pw.close();
            }catch(IOException ex){
                ex.printStackTrace();
            }
    }
}
    
}
