'''
Created on 2009-12-23

@author: Administrator
'''
import sys,re,urllib2,cookielib
def download(url):
    opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(cookielib.CookieJar()))
    opener.addheaders = [('User-agent', 'Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; .NET CLR 1.1.4322)')]
    f = opener.open(url)
    s = f.read()
    f.close()
    return s
#s = download("http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2009-2403")
#print s

 