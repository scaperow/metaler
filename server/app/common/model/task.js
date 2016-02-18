'use strict';
/**
 * model
 * @type {Class}
 */
var moment = require("moment")

module.exports = think.model({
 schema : {
  title: { //全名
   default: function() {
    return null;
   }
  },
  content: {
   default: function() {
    return '';
   }
  },
  created: { //创建时间
   default: function() { //获取当前时间
    return moment().format("YYYY-MM-DD HH:mm:ss")
    //return ''
   },
   readonly: true
  }
 }
});
