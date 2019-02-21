import os
#os.system("dnslookup")
import subprocess
#subprocess.check_output('./dnslookup google.com' )
args='/users/level3/2236676u/eclispes_stuff/git-eclipse/Assessed/dnslookup google.com youtube.com facebook.com baidu.com wikipedia.org qq.com taobao.com tmall.com yahoo.com amazon.com twitter.com sohu.com jd.com live.com instagram.com'.split()

result=subprocess.check_output(args)
print(result)
addresses=result.split()
span=3
addr_split=[" ".join(addresses[i:i+span]) for i in range(0, len(addresses), span)]
addr_list=[]
for i in addr_split:
    addr_list.append(i.split())
for addr_info in addr_list:
    if addr_info[1]=="IPv4":
        result=subprocess.check_output(['/usr/bin/traceroute', '-4','-q', '1', '-n', addr_info[2]])
        with open("v4_trace.txt", "a+") as v4_file:
            v4_file.write(result)

    else:
        result=subprocess.check_output(['/usr/bin/traceroute', '-6','-q', '1', '-n',addr_info[2]])
        with open("v6_trace.txt", "a+") as v6_file:
            v6_file.write(result)
import sys
sys.exit()
