//class clean
public class Clean {
    //constructor clean
    public Clean(){
    }
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  
    
}
