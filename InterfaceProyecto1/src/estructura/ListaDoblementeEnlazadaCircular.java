package estructura;

public class ListaDoblementeEnlazadaCircular {
    private DocumentoJson primero;
    private DocumentoJson ultimo;
    private int size;
    
    public ListaDoblementeEnlazadaCircular(){
        primero = null;
        ultimo = null;
        size = 0;
    }
    
    
    public String add(String nom){
        if(primero == null){
            primero = new DocumentoJson(nom);
            ultimo = primero;
            primero.enlazarSiguiente(ultimo);
            ultimo.enlazarSiguiente(primero);
            primero.enlazarAnterior(ultimo);
            ultimo.enlazarAnterior(primero);
        }else{
            DocumentoJson actual = primero;
            for(int i = 0;i<size;i++){
                if(actual.obtenerNombre().equals(nom)){
                    return("Documento existente");
                }else{
                    actual = actual.obtenerSiguiente();
                }
            }
            DocumentoJson temp = ultimo;
            DocumentoJson nuevo = new DocumentoJson(nom);
            temp.enlazarSiguiente(nuevo);
            nuevo.enlazarAnterior(temp);
            nuevo.enlazarSiguiente(primero);
            primero.enlazarAnterior(nuevo);
            ultimo = nuevo;
        }
        size++;
        return("Se creo el Documento "+nom);
    }
    
    
    
    
    public String insertar(int ind,String nom){
        DocumentoJson actual = primero;
        for(int i = 0;i<size;i++){
                if(actual.obtenerNombre().equals(nom)){
                    return("Documento existente");
                }else{
                    actual = actual.obtenerSiguiente();
                }
        }
        DocumentoJson temp = primero;
        DocumentoJson nuevo = new DocumentoJson(nom);
        if(ind == 0){
            nuevo.enlazarSiguiente(primero);
            primero.enlazarAnterior(nuevo);
            ultimo.enlazarSiguiente(nuevo);
            nuevo.enlazarAnterior(ultimo);
            primero = nuevo;
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
    
    
    
    
    
    
    public void addPrimero(String nom){
        if(primero == null){
            primero = new DocumentoJson(nom);
            ultimo = primero;
            primero.enlazarSiguiente(ultimo);
            ultimo.enlazarSiguiente(primero);
            primero.enlazarAnterior(ultimo);
            ultimo.enlazarAnterior(primero); 
        }else{
            DocumentoJson temp = primero;
            DocumentoJson nuevo = new DocumentoJson(nom);
            temp.enlazarAnterior(nuevo);
            nuevo.enlazarSiguiente(temp);
            primero = nuevo;
            primero.enlazarAnterior(ultimo);
            ultimo.enlazarSiguiente(primero);
            }
        size++;
    }
    
    public DocumentoJson obtener(int indice){
        DocumentoJson temp = primero;
        while(0 < indice){
            temp = temp.obtenerSiguiente();
            indice--;
        }
        return temp;
    }
    
    
    public DocumentoJson obtener(String nombre){
        DocumentoJson temp = primero;
        for(int i = 0; i < size;i++){
            if(temp.obtenerNombre().equals(nombre)){
                return temp;
            }else{
                temp = temp.obtenerSiguiente();
            }
        }
        return null;
    }
    
//    public String obtenerAnterior(int index){
//        DocumentoJson temp = primero;
//        for(int i = 0;i< index;i++){
//            temp = temp.obtenerSuiguiente();
//        }
//        return temp.obtenerAnterior().obtenerNombre();
//    }
//    
   public DocumentoJson obtenerUltimo(){
           return ultimo;
        } 
//    public Object obtenerPrimero(){
//        return primero;
//    }

    
    public String obtenerLista(){
        if(primero == null){
            return("JsonStore vacio.");
        }else{
            String lista = "";
            DocumentoJson temp = primero;
            for(int i = 0;i < size;i++){
                lista += (temp.obtenerNombre()+", ");
                temp = temp.obtenerSiguiente();
            }
            return lista;
        }
    }
    
    public void eliminar(int indice){
        if(indice == 0){
            primero = primero.obtenerSiguiente();
            primero.enlazarAnterior(ultimo);
            ultimo.enlazarSiguiente(primero);
        }else{
            DocumentoJson temp = primero;
            for(int i = 0;i < (indice-1);i++){
                temp = temp.obtenerSiguiente();
            }
            temp.enlazarSiguiente(temp.obtenerSiguiente().obtenerSiguiente());
            temp.obtenerSiguiente().enlazarAnterior(temp);
        }
        size--;
    }
    
    
    public String eliminar(String nom){
        DocumentoJson actual = primero;
        for(int i = 0;i < size;i++){
            if(actual.obtenerNombre().equals(nom)){
                if(actual==primero && primero==ultimo){
                    primero=null;
                    ultimo=null;
                    size--;
                    return("Eliminado");
                }else{
                    if(actual == primero){
                        ultimo.enlazarSiguiente(primero.obtenerSiguiente());
                        primero = primero.obtenerSiguiente();
                        primero.enlazarAnterior(ultimo);
                        size--;
                        return("Eliminado");
                    }else{
                        if(actual == ultimo){
                            ultimo.obtenerAnterior().enlazarSiguiente(primero);
                            ultimo = ultimo.obtenerAnterior();
                            primero.enlazarAnterior(ultimo);
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
    
    
    
    
    
    public void eliminarPrimero(){
        primero = primero.obtenerSiguiente();
        primero.enlazarAnterior(ultimo);
        ultimo.enlazarSiguiente(primero);
        size--;
    }
    
    public int size(){
        return size;
    }
    
    public boolean estaVacio(){
        return (primero==null);
    }
}
