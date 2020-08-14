import numpy as np
import matplotlib.pyplot as plt  
ITEM =0x61c88647
cur = ITEM
x=[]
y =[]
for i in range(0,330):
    x.append(i)
    # y .append(i)
    print(ITEM&15)
    y .append( cur & 15)
    cur += ITEM

l2=plt.plot(x,y,'g+',label='type2')
plt.title('The Lasers in Three Conditions')
plt.xlabel('row')
plt.ylabel('column')
plt.legend()
plt.savefig("mygraph.png")
