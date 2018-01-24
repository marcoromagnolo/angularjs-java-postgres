'use strict';

/**
 * @ngdoc function
 * @author Marco Romagnolo
 * @version 17/08/2015
 * @name webApp.controller:HomeController
 * @description Home Controller of the webApp
 */
angular.module('webApp')

  .controller('HomeController', function ($scope) {

    $scope.myDate = new Date();

  });
