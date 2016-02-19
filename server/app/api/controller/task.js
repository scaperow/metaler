'use strict';
/**
 * rest controller
 * @type {Class}
 */
module.exports = think.controller('rest', {
 /**
  * init
  * @param  {Object} http []
  * @return {}      []
  */
 init(http) {
   this.super('init', http);
  },
  /**
   * before magic method
   * @return {Promise} []
   */
  * __before() {

   var passport = require('passport'),
   LocalStrategy = require('passport-local').Strategy;
   passport.authenticate('local', {
     failureRedirect: '/login'
    },
    function(req, res) {
     console.log('aaa');
      return this.json({});
    }
   );
  },

  * checkAuth() {

   //  deferred = think.defer();
   //  LocalStrategy = require('passport-local').Strategy;
   //
   // passport.use(new LocalStrategy(
   //  function(username, password, done) {
   //    return done(null, {id:1});
   //
   //   // User.findOne({
   //   //  username: username
   //   // }, function(err, user) {
   //   //  if (err) {
   //   //   return done(err);
   //   //  }
   //   //  if (!user) {
   //   //   return done(null, false, {
   //   //    message: '用户名不存在.'
   //   //   });
   //   //  }
   //   //  if (!user.validPassword(password)) {
   //   //   return done(null, false, {
   //   //    message: '密码不匹配.'
   //   //   });
   //   //  }
   //   //
   //   // });
   //  }
   //
   //  return deferred.promise;
  }
});
