# coding=utf-8

from google.appengine.api import urlfetch
import cgi
import os,urlparse,base64



def fetchURL(url):
    try:
        result = urlfetch.fetch(url)
        if result.status_code == 200:
            return result.content
        else:
            return url +"error"
    except:
        return url +"except"
    

    
if __name__ == "__main__":
    print 'main'