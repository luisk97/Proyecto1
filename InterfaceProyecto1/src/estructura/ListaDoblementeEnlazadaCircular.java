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
        int contador = 0;
        DocumentoJson temp = primero;
        while(contador < indice){
            temp = temp.obtenerSuiguiente();
            contador++;
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
   public Object obtenerUltimo(){
           return ultimo;
        } 
//    public Object obtenerPrimero(){
//        return primero;
//    }

    
    public void obtenerLista(){
        if(primero == null){
            System.out.println("JsonStore vacio.");
        }else{
            DocumentoJson temp = primero;
            for(int i = 0;i < size;i++){
                System.out.print(temp.obtenerNombre()+", ");
                temp = temp.obtenerSuiguiente();
            }
            System.out.println("");
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
