'use strict';

/**
 * @ngdoc function
 * @author Marco Romagnolo
 * @version 17/08/2015
 * @name webApp.service:account
 * @description account service of the webApp
 */
angular.module('webApp')

  .factory('formField', function ($log, message) {

    return {
      checkError: function(form) {
        if (message.error.fields) {
          for (var key in message.error.fields) {
            if (form[key]) {
              form[key].$error.custom = message.error.fields[key];
              form[key].$invalid = true;
            }
          }
        }
      }
    }

  });
