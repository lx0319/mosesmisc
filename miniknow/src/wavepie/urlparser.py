# coding=utf-8

import cgi
import os,urlparse,base64



def thuderAddress(d):
    try:
        temp = base64.decodestring(d.strip()[10:])
        if temp.startswith('AA')&temp.endswith('ZZ'): 
            temp = temp[2:-2]
    except:
        return '错误的编码，请检查'
    return temp
def flashgetAddress(d):
    try:
        temp = base64.b64decode(d.strip()[11:].split('&')[0])
        if temp.startswith('[FLASHGET]')&temp.endswith('[FLASHGET]'): 
            temp = temp[10:-10]
    except:
        return '错误的编码，请检查'
    return temp
def qqdlAddress(d):
    try:
        temp = base64.decodestring(d.strip()[7:])
    except:
        return '错误的编码，请检查'
    return temp
    
def encodeAddress(str):
    a = []
    str_thunder = 'AA' + str + 'ZZ'
    a.append('thunder://'+base64.b64encode(str_thunder))    
    str_flashget = '[FLASHGET]' + str + '[FLASHGET]'
    a.append('Flashget://'+base64.b64encode(str_flashget)+'&MoSeS')    
    str_qqld = str
    a.append('qqdl://'+base64.b64encode(str_qqld))
    return a
    
    
    
if __name__ == "__main__":
    print 'main'