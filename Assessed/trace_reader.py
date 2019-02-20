addr_list=[]
print("dgs")
with open ("v6_trace.txt", 'r') as f:        
    for line in f:
        line=line.split()
        if line[0]!="traceroute" and line[1]!="*":
            addr_list.append(line[1])

sum=''
proccessed=[]
for i,addr in enumerate(addr_list):
    if i ==0:
        sum= addr
    else:
        sum=sum+" -- "+addr
        proccessed.append(sum)
        sum=addr 
with open('router-topology-v4.dot', 'w') as f:
    for i in proccessed:
        f.write(i+'\n')
#####IPV6############
addr_listv6=[]
with open ("v6_trace.txt", 'r') as f:        
    for line in f:
        line=line.split()
        if line[0]!="traceroute" and line[1]!="*":
            addr_list.append(line[1])

sum=''
proccessed_v6=[]
for i,addr in enumerate(addr_listv6):
    if i ==0:
        sum= addr
    else:
        sum=sum+" -- "+addr
        proccessed_v6.append(sum)
        sum=addr 
with open('router-topology-v6.dot', 'w') as f:
    for i in proccessed_v6:
        f.write(i+'\n')