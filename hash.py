import numpy as np
import matplotlib.pyplot as plt  
ITEM =0x61c88647
cur = ITEM
x=[]
y =[]
arr =[7,14,5,12,3,10,1,8,15,6,13,4,11,2,9,0]
arr_len =len(arr)
arr_index = 0
print('for begin')
for i in range(0,330):
    x.append(i)
    if  arr[arr_index ] != cur &15:
        # 没有打印 Error 说明 cur 值累加 ITEM 的过程其实是对 arr 的遍历[笑哭]
        print('Error')
    arr_index += 1
    arr_index %=16
    y .append( cur & 15)
    cur += ITEM
print('for end')
l2=plt.plot(x,y,'g+',label='')
plt.title('')
plt.xlabel('')
plt.ylabel('')
# plt.legend()
plt.savefig("mygraph.png")
