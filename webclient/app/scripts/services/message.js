'use strict';

/**
 * @ngdoc function
 * @author Marco Romagnolo
 * @version 17/08/2015
 * @name webApp.service:account
 * @description message service of the webApp
 */
angular.module('webApp')

  .service('message', function ($log) {

    this.error = {code:null, fields:{}};

    this.setError = function(data) {
      if (data.error) {
        $log.debug('Server error:', data.error);
        this.error.code = data.error.code;
        this.error.fields = data.error.fields;
        $log.debug('Client error:', this.error);
      }
    };

    this.reset = function() {
      this.error = {code:null, fields:{}};
    };

  });
