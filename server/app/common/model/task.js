'use strict';
/**
 * model
 * @type {Class}
 */
var moment = require("moment")

module.exports = think.model('relation',{
  schema: {
    created: { //创建时间
      default: function() { //获取当前时间
        return moment().format("YYYY-MM-DD HH:mm:ss")
          //return ''
      },
      readonly: true
    }
  },
  init() {
    this.super('init', arguments);
    this.relation = {
      //user:think.model.BELONG_TO
      user:think.model.BELONG_TO
    }
  }
});
