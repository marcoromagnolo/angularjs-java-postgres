'use strict';

/**
 * @ngdoc function
 * @author Marco Romagnolo
 * @version 17/08/2015
 * @name webApp.controller:AccountController
 * @description Account Controller of the webApp
 */
angular.module('webApp')

  .controller('AccountController', function ($scope, $translate, $log, account, formField, message) {

    var moduleName = 'AccountController';

    $scope.goToRegister = function () {
      $scope.changeModal('ACCOUNT_REGISTER_TITLE', 'views/partials/register.html');
    };

    $scope.goToConfirm = function () {
      $scope.changeModal('ACCOUNT_CONFIRM_TITLE', 'views/partials/confirm.html');
    };

    $scope.goToRecovery = function () {
      $scope.changeModal('ACCOUNT_RECOVERY_TITLE', 'views/partials/recovery.html');
    };

    $scope.login = function (form, user) {
      if (form.$valid && user && account.login(user.username, user.password, user.expiry)) {
        $scope.$apply($scope.applyAfterLogin);
      }
    };

    $scope.logout = function () {
      var session = $scope.session;
      if (session) {
        account.logout(session.token);
      }
    };

    $scope.reset = function (form) {
      if (form) {
        form.$setPristine();
        form.$setUntouched();
        $scope.resetError();
      }
      $scope.user = {};
    };

    $scope.register = function (form, user) {
      if (form.$valid && user) {
        $log.debug(moduleName+'.register(): form is valid');
        account.register(user.firstName, user.lastName, user.email, user.email, user.password, $translate.use())
          .then(function(reponse) {
            $log.debug('OK', reponse);
         }, function(reponse) {
            $log.debug('ERROR', reponse);
            $log.debug(moduleName+'.register(): error occurred in some fields', message.error);
            formField.checkError(form);
          });
      }
    };

  });
