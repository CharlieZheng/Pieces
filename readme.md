# a&b 和 a&~b

```
@Test
public void test2() {
    int a[] = new int[]{0, 1 << 0, 1 << 1, 1 << 2, 1 << 3, 1 << 4};
    for (int i = 0; i < a.length; i++) {
        for (int j = 0; j < a.length; j++) {
            System.out.println(String.format("%10s%10s%10s%10s", Integer.toBinaryString(a[i]), Integer.toBinaryString(a[j]), Integer.toBinaryString(a[i] & (~a[j])), Integer.toBinaryString(a[i] & a[j])));
        }
        System.out.println();
    }
}
```
