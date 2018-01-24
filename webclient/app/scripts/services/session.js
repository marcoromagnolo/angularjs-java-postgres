'use strict';

/**
 * @ngdoc function
 * @author Marco Romagnolo
 * @version 17/08/2015
 * @name webApp.service:session
 * @description session service of the webApp
 */
angular.module('webApp')

  .service('session', function () {

    this.session = {};

    this.createSession = function (token, userId, username, firstName, lastName, createDate) {
      if (!this.session) {
        this.session.token = token;
        this.session.userId = userId;
        this.session.username = username;
        this.session.firstName = firstName;
        this.session.lastName = lastName;
        this.session.createDate = createDate;
      }
    };

    this.destroySession = function () {
      this.session = null;
    };

    this.hasPermissions = function () {
      return true;
    };

    this.sessionExists = function () {
      return !this.session;
    };
  });
