'use strict';

/**
 * @ngdoc function
 * @author Marco Romagnolo
 * @version 17/08/2015
 * @name webApp.service:account
 * @description account service of the webApp
 */
angular.module('webApp')

  .factory('account', function ($http, $log, config, message, rest, session) {

    return {

      isAuthorized: function () {
        return session.hasPermissions();
      },

      isAuthenticated: function () {
        return session.sessionExists();
      },

      register: function (firstName, lastName, email, username, password, locale) {
        return rest.post(config.api.url.register, {firstName: firstName, lastName: lastName, email: email, username: username, password: password, locale: locale});
      },

      login: function (username, password, expiry) {
        var data = rest.post(config.api.url.login, {username: username, password: password, expiry: expiry});
        if (data!=null) {
          session.createSession(data.token, data.userId, data.username, data.firstName, data.lastName, data.createDate);
        }
      },

      logout: function (token) {
        return $http.post(config.API_URL_LOGOUT, token)
          .then(function (response) {
            if (response.status===200 && !response.data.error) {
              session.destroySession();
            }
          }, function (response) {
            $log.error(response);
          });
      }
    };

  });
