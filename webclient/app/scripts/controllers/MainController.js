'use strict';

/**
 * @ngdoc function
 * @author Marco Romagnolo
 * @version 17/08/2015
 * @name webApp.controller:MainController
 * @description Main Controller of the webApp
 */
angular.module('webApp')

  .controller('MainController', function ($scope, account, config, message) {

    // Set Configuration Parameters
    $scope.CONFIG = config;

    $scope.error = message.error;

    $scope.resetError = function() {
      message.reset();
      $scope.error = message.error;
    };

    $scope.isAuthenticated = account.isAuthenticated();

    // Set Authorization
    $scope.isAuthorized = account.isAuthenticated() && account.isAuthorized();

    // Show Modal
    $scope.showModal = false;

    // Action to apply after login
    $scope.applyAfterLogin = null;

    // Toogle Modal
    $scope.toggleModal = function (title, file) {
      $scope.modalTitle = title;
      $scope.modalInclude = file;
      $scope.showModal = !$scope.showModal;
    };

    // Change Modal page
    $scope.changeModal = function (title, file) {
      $scope.modalTitle = title;
      $scope.modalInclude = file;
    };

    $scope.openRegisterDialog = function () {
      $scope.toggleModal('ADD_RELEASE', 'views/partials/addRelease.html');
    };

    $scope.openLoginDialog = function () {
      $scope.toggleModal('ACCOUNT_LOGIN_TITLE', 'views/partials/login.html');
    };

    //$scope.load = true;

  });
