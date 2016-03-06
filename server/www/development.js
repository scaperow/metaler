var thinkjs = require('thinkjs');
var path = require('path');
var moment = require('moment');
var rootPath = path.dirname(__dirname);
var token = require('token');
var instance = new thinkjs({
  APP_PATH: rootPath + path.sep + 'app',
  RUNTIME_PATH: rootPath + path.sep + 'runtime',
  ROOT_PATH: rootPath,
  RESOURCE_PATH: __dirname,
  env: 'development'
});
token.defaults.secret = 'AAB';
token.defaults.timeStep = 24 * 60 * 60; // 24h in seconds
think.config('host', '0.0.0.0')
think.hook("controller_before", ["validation"],"append");
instance.run();
