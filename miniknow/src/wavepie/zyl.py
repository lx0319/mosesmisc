# coding=utf-8
import urllib

import cgi
import os,urlparse,base64,sys
import urlparser
import urlfetcher

from google.appengine.api import users
from google.appengine.ext import webapp
from google.appengine.ext import db
from google.appengine.ext.webapp.util import run_wsgi_app
from google.appengine.ext.webapp import template

code = sys.getdefaultencoding()
if code != 'utf8':
    reload(sys)
    sys.setdefaultencoding('utf8')
          
dirs = {
    "template":"snippet/"
}

templates = {
    "head":"head.html",
    "urlform":"urlconvert.html"
}



class MainPage(webapp.RequestHandler):
    def get(self):
    
        self.response.out.write("<html>")
        self.includeSnippet(dirs['template'],templates['head'],{})
        self.response.out.write("<body>")
        self.response.out.write(sys.getdefaultencoding())
        
        self.includeSnippet(dirs['template'],templates['urlform'],{})
        self.response.out.write("</body></html>")
       
    
    def post(self):
        content = self.request.get('content')
        shorturl = self.request.get('shorturl')
        flag_zyl = 1
        flag_dwz = 1
        if len(content.strip())==0:
            flag_zyl = 0
        if len(shorturl.strip())==0:
            flag_dwz = 0
        if flag_zyl + flag_dwz == 0:
            self.redirect('/url/')
        
        self.response.out.write("<html>")
        self.includeSnippet(dirs['template'],templates['head'],{})
        self.response.out.write("<body>")
        if flag_zyl == 1:
            self.response.out.write(u"<div>需要进行专用链转换的地址<br/>")
            self.response.out.write(content)
            self.response.out.write(u"<br/>的结果是：<br/><br/>")
            
            if content.lower().startswith('thunder://'):            
                converted = urlparser.thuderAddress(content)
            elif content.lower().startswith('flashget://'):
                converted = urlparser.flashgetAddress(content)
            elif content.lower().startswith('qqdl://'):
                converted = urlparser.qqdlAddress(content)
            else:
                strl = urlparser.encodeAddress(content)
            
            if locals().has_key('converted'):
                self.response.out.write("<a href='%s'>%s</a>" % (converted,converted));
                self.response.out.write("<br/>");
            else:
                for str in strl:
                    self.response.out.write("<a href='%s'>%s</a>" % (str,str));                    
                    self.response.out.write('<br/><br/>');
            self.response.out.write('</div><br/>');
        if flag_dwz == 1:
            self.response.out.write(u"<div>需要进行长短URL转换的地址<br/>")
            self.response.out.write(shorturl)
            self.response.out.write(u"<br/>的结果是：<br/><br/>")
            urllong = urlfetcher.fetchURL("http://untiny.me/api/1.0/extract/?format=text&url="+urllib.quote(shorturl))
            self.response.out.write(urllong)
            pass
        self.includeSnippet(dirs['template'],templates['urlform'],{})
        self.response.out.write("</body></html>")
    
    def includeSnippet(self,dir,snippet,templatevalues={}):
        path = os.path.join(os.path.dirname(__file__), dir + snippet)
        self.response.out.write(template.render(path, template))



        
application = webapp.WSGIApplication(
                                     [('/url/.*', MainPage)],
                                     debug=True)


#专用链
def main():
  run_wsgi_app(application)

if __name__ == "__main__":
  main()