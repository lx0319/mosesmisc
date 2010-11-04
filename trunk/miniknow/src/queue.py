'''
Created on 2009-10-15

@author: Moses
'''
from google.appengine.api import users
from google.appengine.ext import webapp
from google.appengine.ext.webapp.util import run_wsgi_app

class MainPage(webapp.RequestHandler):
  def get(self):
    self.response.out.write("""
      <html>
        <body>
          <form action="/queue/sign" method="post">
            <div><textarea name="content" rows="3" cols="60"></textarea></div>
            <div><input type="submit" value="Sign Guestbook"></div>
          </form>
        </body>
      </html>""")
    
class MainPage2(webapp.RequestHandler):
    
    
    def get(self):
        
        user = users.get_current_user()

        if user:
            self.response.out.write(
                'Hello %s <a href="%s">Sign out</a><br>Is administrator: %s' % 
                (user.nickname(), users.create_logout_url("/queue/"), users.is_current_user_admin())
            )
        else:
            self.redirect(users.create_login_url(self.request.uri))

application = webapp.WSGIApplication(
                                     [('/queue/', MainPage),
                                      ('/queue/sign', MainPage2)],
                                     debug=True)

def main():
    run_wsgi_app(application)


if __name__ == "__main__":
    main()
