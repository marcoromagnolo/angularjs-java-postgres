'use strict';

/**
 * @ngdoc function
 * @author Alessandro Cacciotti
 * @version 09/05/2016
 * @name webApp.controller:CustomersController
 * @description Customers Controller of the webApp
 */
angular.module('webApp')

  .controller('CustomersController', function ($scope) {

    $scope.clienti = ["ALC","Betfred","NT",];
  });
