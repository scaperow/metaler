var thinkjs = require('thinkjs');
var path = require('path');
var passport = require('passport');
var rootPath = path.dirname(__dirname);
var passport = require('passport');
var LocalStrategy = require('passport-local').Strategy;
var instance = new thinkjs({
 APP_PATH: rootPath + path.sep + 'app',
 RUNTIME_PATH: rootPath + path.sep + 'runtime',
 ROOT_PATH: rootPath,
 RESOURCE_PATH: __dirname,
 env: 'development'
});
passport.use(new LocalStrategy(
 function(username, password, done) {
   console.log('aaa');
  if (username !== 'aaa') {
   return done(null, false);
  }
  return done(null, user);
 }
));
instance.run();
