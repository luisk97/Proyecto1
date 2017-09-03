package estructura;

public class ListaDoblementeEnlazadaCircular {
    private DocumentoJson primero;
    private DocumentoJson ultimo;
    private int size;
    
    public ListaDoblementeEnlazadaCircular(){
        primero = null;
        ultimo = null;
    }
    public void add(String nom){
        if(primero == null){
            primero = new DocumentoJson(nom);
            ultimo = primero;
            primero.enlazarSiguiente(ultimo);
            ultimo.enlazarSiguiente(primero);
            primero.enlazarAnterior(ultimo);
            ultimo.enlazarAnterior(primero);
        }else{
            DocumentoJson temp = ultimo;
            DocumentoJson nuevo = new DocumentoJson(nom);
            temp.enlazarSiguiente(nuevo);
            nuevo.enlazarAnterior(temp);
            ultimo = nuevo;
            ultimo.enlazarSiguiente(primero);
            primero.enlazarAnterior(ultimo);
            
        }
        size++;
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
            temp = temp.obtenerSuiguiente();
            indice--;
        }
        return temp;
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
                temp = temp.obtenerSuiguiente();
            }
            return lista;
        }
    }
    
    public void eliminar(int indice){
        if(indice == 0){
            primero = primero.obtenerSuiguiente();
            primero.enlazarAnterior(ultimo);
            ultimo.enlazarSiguiente(primero);
        }else{
            DocumentoJson temp = primero;
            for(int i = 0;i < (indice-1);i++){
                temp = temp.obtenerSuiguiente();
            }
            temp.enlazarSiguiente(temp.obtenerSuiguiente().obtenerSuiguiente());
            temp.obtenerSuiguiente().enlazarAnterior(temp);
        }
        size--;
    }
    
    public void eliminarPrimero(){
        primero = primero.obtenerSuiguiente();
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
