# a&b 和 a&~b

### 举例：

|a=3                                       |           b=1                               |          a&b=1 != 0                           |
|:---|:---:|---:|
|a=2                                       |           b=1                               |          a&b=0 == 0                          |
|a=(11110000)_2                    |           b=(00001111)_2          |           a&b=0 == 0                          |
|a=(11110000)_2                    |           b=(11001100)_2          |           a&b=192 != 0                       |


现存在一个“彗星”对象

它有若干状态

int A（表示是否正在太阳系中飞行）=0000 0001

int B=0000 0010

int C=0000 0100

int D=0000 1000

然后用一个标识Big表示所有状态的true false情况

当Big=0000 0011时，则表示此彗星正在太阳系中飞行且也正处于B状态中，而不处于C和D状态中

1. add，当要增加状态A时

    Big |= A

2. exists，Big中是否有A状态

    return Big & A

3. remove，去除状态A

    Big = Big & ~A

### 写在最后
当然如果某个属性有多个状态则不能用这种表示法

# 实例
```
@Test
    public void addAndRemove() {
        int flags = 0;
        int flag1 = 0b0000001001;
        int flag2 = 0b1100000100;
        int flag3 = 0b0011100010;
        flags |= flag1;
        System.out.println("新增 flag1\t" + Integer.toBinaryString(flags));
        flags |= flag2;
        System.out.println("新增 flag2\t" + Integer.toBinaryString(flags));
        flags |= flag3;
        System.out.println("新增 flag3\t" + Integer.toBinaryString(flags));
        flags &= ~flag1;
        flags &= ~flag2;
        flags &= ~flag3;
        System.out.println("移除 flag1 flag2 flag3\t" + Integer.toBinaryString(flags));
    }

    @Test
    public void addAndRemove2() {
        int flags = 0;
        int flag1 = 0b0000001001;
        int flag2 = 0b1100000100;
        int flag3 = 0b0011100010;
        flags |= flag1;
        System.out.println("新增 flag1\t" + Integer.toBinaryString(flags));
        flags |= flag2;
        System.out.println("新增 flag2\t" + Integer.toBinaryString(flags));
        flags |= flag3;
        System.out.println("新增 flag3\t" + Integer.toBinaryString(flags));
        flags &= ~flag1 & ~flag2 & ~flag3;
        System.out.println("移除 flag1 flag2 flag3\t" + Integer.toBinaryString(flags));
    }
```
上面两个方法输出的结果是一样的
```
新增 flag1	1001
新增 flag2	1100001101
新增 flag3	1111101111
移除 flag1 flag2 flag3	0
```
总结
 - flags |= flag1：是往 flags 加 flag1
 - flags &= ~flag1：是从 flags 里移除 flag1
