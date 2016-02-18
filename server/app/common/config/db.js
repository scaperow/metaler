'use strict';
/**
 * db config
 * @type {Object}
 */
module.exports = {
  type: 'mysql',
  log_sql: true,
  log_connect: true,
  adapter: {
    mysql: {
      host: 'rdsqj834228mlrfmm8vk.mysql.rds.aliyuncs.com',
      port: '',
      database: 'riu3hg9o927l2645',
      user: 'scaperow',
      password: 'scape890315',
      prefix: '',
      encoding: 'utf8'
    },
    mongo: {

    }
  }
};
