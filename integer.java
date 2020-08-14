import java.util.concurrent.atomic.AtomicInteger;

public class integer {
    public static void main(String[] args) {
  for (int i = 0; i < 100; i++){ 
  final integer obj = new integer();
    //   System.out.printf("%40s\n",Integer.toBinaryString( obj.threadLocalHashCode));
    System.out.printf("%40s\n", obj.threadLocalHashCode & 15);
  }
    }
    private final int threadLocalHashCode = nextHashCode();

    
    private static AtomicInteger nextHashCode =
        new AtomicInteger();

    
    private static final int HASH_INCREMENT = 0x61c88647;

    
    private static int nextHashCode() {
        return nextHashCode.getAndAdd(HASH_INCREMENT);
    }
    
}