package estructura;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class ListaEnlazadaDoble {
    
    private JsonStore cabeza;
    private JsonStore ultimo;
    private int size;
    
    public ListaEnlazadaDoble(){
        cabeza = null;
        ultimo = null;
        size = 0;
    }
    
    
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
    
    
    public int imprimirLista(){
        return size;
    }
    
    public JsonStore obtener(int index){
        JsonStore temp = cabeza;
        while(0 < index){
            temp = temp.obtenerSiguiente();
            index--;
        }
        return temp;
    }
//    public Object obtenerAnterior(int index){
//        JsonStore temp = cabeza;
//        for(int i = 0;i< index;i++){
//            temp = temp.obtenerSiguiente();
//        }
//        return temp.obtenerAnterior().obtenerNombre();
//    }
    
    public Object obtenerUltimo(){
            return ultimo;
        } 
//    public Object obtenerPrimero(){
//        return cabeza;
//    }
    
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
    
//    public void cortar(int index){
//        JsonStore temp = cabeza;
//        for(int i = 0;i < (index-1);i++){
//                temp = temp.obtenerSuiguiente();
//        }
//        temp.enlazarSiguiente(null);
//        size = index;
//    }
    
    public void eliminar(int indice){
        if(indice == 0){
            cabeza = cabeza.obtenerSiguiente();
        }else{
            JsonStore temp = cabeza;
            for(int i = 0;i < (indice-1);i++){
                temp = temp.obtenerSiguiente();
            }
            temp.enlazarSiguiente(temp.obtenerSiguiente().obtenerSiguiente());
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
//            FileWriter fw = new FileWriter(file,true);
//            fw.append("Primera linea Hola!!");
//            fw.append("Continua linea Hola!!");
//            fw.close();
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
