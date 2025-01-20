
public class Message {
    public int frames;
    public String message; 
    
    public Message(String m, int f){
        message = m;
        frames = f;
    }

    public boolean update() {
        frames--;
        if (frames == 0) 
            return false;
            
        return true;
    }
    
    public void change(String m) {
        message = m;
    }
}
