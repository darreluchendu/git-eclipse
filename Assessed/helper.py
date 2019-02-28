def parse(file):
    with open(file, 'r') as f:
       
        for line in f:
            try:
                line=line.split()
                line[0],line[2]='"'+line[0]+'"','"'+line[2]+'"'
                line=' '.join(line)
                print (line)
            except:
                continue
parse("router-topology-v6.dot")
