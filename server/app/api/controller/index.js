'use strict';

var Base = require('./base.js');

module.exports = think.controller(Base, {
  /**
   * index action
   * @return {Promise} []
   */
  * indexAction(self) {
   let model = this.model("task");
   let insertId = yield model.add({ title: "xxx", content: "yyy"});
   return self.display();
  }
});