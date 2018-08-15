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

打印结果：

```
         0         0         0         0
         0         1         0         0
         0        10         0         0
         0       100         0         0
         0      1000         0         0
         0     10000         0         0

         1         0         1         0
         1         1         0         1
         1        10         1         0
         1       100         1         0
         1      1000         1         0
         1     10000         1         0

        10         0        10         0
        10         1        10         0
        10        10         0        10
        10       100        10         0
        10      1000        10         0
        10     10000        10         0

       100         0       100         0
       100         1       100         0
       100        10       100         0
       100       100         0       100
       100      1000       100         0
       100     10000       100         0

      1000         0      1000         0
      1000         1      1000         0
      1000        10      1000         0
      1000       100      1000         0
      1000      1000         0      1000
      1000     10000      1000         0

     10000         0     10000         0
     10000         1     10000         0
     10000        10     10000         0
     10000       100     10000         0
     10000      1000     10000         0
     10000     10000         0     10000
```
