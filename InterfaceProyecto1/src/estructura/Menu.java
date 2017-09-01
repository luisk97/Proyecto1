package estructura;

public class Menu {
    private String nombre;
    private String nombre2;
    
    public void imprimirMenu(String nombre,String nombre2){
        this.nombre = nombre;
        this.nombre2 = nombre2;
        
    System.out.println("         Menu de "+nombre);
            System.out.println("Elija una opcion:");
            System.out.println("1-Crear un "+nombre2+".");
            System.out.println("2-Crear e insertar un "+nombre2+" en un indice especifico.");
            System.out.println("3-Entrar en un "+nombre2+" especifico.");            
            System.out.println("4-Entrar en el ultimo "+nombre2+".");
            System.out.println("5-Eliminar un "+nombre2+" especifico.");
            System.out.println("6-Eliminar el primer "+nombre2+".");
            System.out.println("7-Verificar si hay "+nombre2+".");
            System.out.println("8-Ver la cantidad de "+nombre2+" existentes.");
            System.out.println("9-Ver lista de "+nombre2+".");
            System.out.println("10-Hacer commit");
            System.out.println("11-Salir");
    }
}
