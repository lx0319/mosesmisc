Conf_NotFound = "/notFound?p="
# the system will append the path of the page not found

import wsgiref.handlers
from google.appengine.ext import webapp
from google.appengine.ext import db
import random

class UrlData(db.Model):
    url = db.StringProperty()
    code = db.StringProperty()


class newUrl:
    CodeFrom = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890"
    def __init__(self, url):    
        self.url = url
    def checkCode(self):
        if len(self.code) < 3:
            return 1
        back = UrlData.gql("WHERE code = :1 LIMIT 1", self.code)
        return back.count(1) > 0
    def makeCode(self):
        self.code = str(random.randint(0,9))
        while self.checkCode():
            self.code += self.CodeFrom[random.randint(0, len(self.CodeFrom) - 1)]
    def build(self):
        self.makeCode()
        info = UrlData(url = self.url, code = self.code)
        info.put()
        return self.code
        

def getCode(url):
    # get the code for the url
    data = UrlData.gql("WHERE url = :1 LIMIT 1", url)
    if data.count(1) > 0:
        return data.fetch(1)[0].code
    else:
        u = newUrl(url)
        return u.build()
    
def getUrl(code):
    # get the url from the code
    data = UrlData.gql("WHERE code = :1 LIMIT 1", code)
    if data.count(1) > 0:
        return data.fetch(1)[0].url
    else:
        return ""

class UrlHandler(webapp.RequestHandler):
    def get(self):
        write = self.response.out.write
        req = self.request.path.split('/')
        code =req[1]
        url = getUrl(code)
        if url == "":
            url = Conf_NotFound + self.request.path
        self.redirect(url)
    def post(self):
        self.get()

class notFoundHandler(webapp.RequestHandler):
    def get(self):
        self.response.out.write("The requested page was not found")
    def post(self):
        self.get()


application = webapp.WSGIApplication([('/notFound', notFoundHandler), ('.*', UrlHandler),],
                                       debug=True)

def main():
    wsgiref.handlers.CGIHandler().run(application)

if __name__ == "__main__":
    main()