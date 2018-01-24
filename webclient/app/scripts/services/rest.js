'use strict';

/**
 * @ngdoc function
 * @author Marco Romagnolo
 * @version 17/08/2015
 * @name webApp.service:webService
 * @description webService utility to handle errors of the webApp
 */
angular.module('webApp')

  .factory('rest', function ($q, $http, $log, message, config) {

    function async(request) {
      var deferred = $q.defer();
      var promise = request.then(function (response) {
        if (response.status===200) {
          if (response.data.httpStatus===200) {
            $log.debug(response.data);
            deferred.resolve(response);
          } else {
            $log.debug('Error Message Status');
            message.setError(response.data);
            deferred.reject(response);
          }
        }
      }, function (response) {
        message.setError(response.data);
        deferred.reject(response);
      });
      return deferred.promise;
    }

    return {

      get: function(url) {
        return async($http.get(config.apiEndpoint + url));
      },

      post: function(url, params) {
        return async($http.post(config.apiEndpoint + url, params));
      },

      put: function(url, params) {
        return async($http.put(config.apiEndpoint + url, params));
      },

      delete: function(url, params) {
        return async($http.delete(config.apiEndpoint + url, params));
      }

    };

  });
