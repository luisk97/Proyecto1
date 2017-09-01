package estructura;


public class TestConstants{
    
    public enum Constants{
     ENTERO,FLOTANTE,CADENA,FECHAHORA;}


    
    public static void main(String[] args){
     
        System.out.println(Constants.values()[3]);
    }
    
    
    String upperCase(String pVar)
    {
        String temp = pVar;
        temp = temp.trim();
        return temp.toUpperCase();  
    }
}