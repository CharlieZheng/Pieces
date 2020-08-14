import java.math.BigInteger;
import java.security.MessageDigest;

public class Hash3 {

  private static  final int  ITEM =0x61c88647;
  private static  int  cur =ITEM;

    public static void main(String[] args) {
       for (int i =0; i < 100; i++) {
           System.out.println(cur &15);
           cur += ITEM;
       }
    }
}
