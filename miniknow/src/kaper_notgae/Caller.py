'''
Created on 2009-12-23

@author: Administrator
'''

import test

if __name__ == '__main__':
    input = open('jhm.txt', 'r')
    try:
        k = 0
        for line in input:
            k=k+1
            #line2 = 'https://activation.kaspersky.com/cn/index.html?SEND_TOEMAIL=1&user_email=lx0319@gmail.com&ACT_CODE='+line
            #print 'processing',line
            #test.download(line2)
            
        #all_the_text = input.read()
    finally:
        input.close( )
        print k
    pass