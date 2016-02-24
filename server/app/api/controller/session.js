'use strict';

/**
 * rest controller
 * @type {Class}
 */
var Base = require('./base.js');
var token = require('token');
module.exports = think.controller("rest", {

  * postAction() {
    let params = this.post();
    let users = think.model("user", null, "common");
    let user = yield users.find({
      mobile: params.mobile,
      password: params.password
    });

    if (user) {
      var key = token.generate(user.id+"");
      return this.success({
        id: user.id,
        token: key
      });
    }

    return this.success({
      id: user.id,
      token: '用户名或密码不正确'
    });
  }
});
