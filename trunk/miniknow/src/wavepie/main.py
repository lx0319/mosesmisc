# coding=utf-8

import cgi
import os

from google.appengine.api import users
from google.appengine.ext import webapp
from google.appengine.ext import db
from google.appengine.ext.db import KindError
from google.appengine.ext.webapp.util import run_wsgi_app
from google.appengine.ext.webapp import template

class Node(db.Model):
    #基本信息
    author = db.UserProperty()
    content = db.StringProperty()
    date = db.DateTimeProperty(auto_now_add=True)
    lastedittime = db.DateTimeProperty(auto_now_add=True)
    
    #用户评价信息
    stared = db.BooleanProperty()
    temperature = db.IntegerProperty()
    
    #关联信息
    parent_node = db.SelfReferenceProperty()
    child_nodes = db.ListProperty(db.Key)
    
    

class MainPage(webapp.RequestHandler):
    def get(self):
      
        nodess_query = Node.all().order('date')
        nodes = nodess_query.fetch(50)
        
        if users.get_current_user():
            url = users.create_logout_url(self.request.uri)
            url_linktext = 'Logout'
        else:
            self.redirect(users.create_login_url(self.request.uri))
        
        template_values = {
          'nodes': nodes,
#          'url': url,
#          'url_linktext': url_linktext
          }
        path = os.path.join(os.path.dirname(__file__), 'inputarea.html')
        self.response.out.write(template.render(path, template_values))

class NodeBook(webapp.RequestHandler):
    def post(self):
        node = Node()
        if users.get_current_user():
            node.author = users.get_current_user()
        else:
            self.redirect(users.create_login_url(self.request.uri))
        node.content = self.request.get('content')
        node.put()
        parent_node = self.request.get('parent_node')
        if parent_node is not None and len(parent_node) > 0:
            node = self.setFatherNode(node, parent_node)
        node.put()
        self.redirect('/')
        
    def setFatherNode(self, node, parent_node):
        
        try:
            pnode = Node.get(db.Key(parent_node))
            if pnode is not None:
                node.parent_node = pnode
                if node.key() not in pnode.child_nodes:
                    pnode.child_nodes.append(node.key())
                    pnode.put()
        except   KindError, e:
            print 'error'
        return node
        
        
            
        

application = webapp.WSGIApplication(
                                     [('/', MainPage),
                                      ('/fabu', NodeBook)],
                                     debug=True)

def main():
    run_wsgi_app(application)

if __name__ == "__main__":
    main()
