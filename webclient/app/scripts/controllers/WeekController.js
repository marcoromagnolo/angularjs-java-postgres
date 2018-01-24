'use strict';

/**
 * @ngdoc function
 * @author Alessandro Cacciotti
 * @version 09/05/2016
 * @name webApp.controller:WeekController
 * @description Week Controller of the webApp
 */
angular.module('webApp')

  .controller('WeekController', function ($scope) {

    $scope.giorni = ["Lunedi","Martedi","Mercoledi","Giovedi","Venerdi","Sabato","Domenica"];

  });
