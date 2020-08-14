import java.math.BigDecimal;

/**
 * 该示例演示了斐波那契数列的后一项除以前一项为黄金分割数
 */
public class Hash2 {

    public static void main(String[] args) {
       final int size = 47;
       final Hash2 obj = new Hash2();
        int[]  arr=new int[size];
        for (int i = 0; i < size; i++) {
            obj.f(i ,arr);
            System.out.print(arr[i]+", ");
        }
        System.out.println();
        for (int i = size-1; i >= 2; i--) {
            final int second = arr[i];
            final int first = arr[i-1];
            System.out.println( BigDecimal.valueOf( second).divide( BigDecimal.valueOf( first),20,BigDecimal.ROUND_HALF_UP ));
        }
    }

    private int f(int index, int[] arr) {
            if (index ==0)  {arr[index]=0;}
            else if (index == 1) {arr[index]=1;}
            else {
                if ( arr[index-1]!=0 && arr[index-2] !=0 ) {
                        arr[index] = arr[index-1] + arr[index-2];
                }
              else  {  
                  arr[index]=f(index-1 ,arr )+f(index-2 ,arr);
            }
               
            } return  arr[index];
    }

}
