'use strict';

/**
 * @ngdoc overview
 * @name webApp
 * @description
 * # webApp
 *
 * Main module of the application.
 */
angular
  .module('webApp', [
    'ngAnimate',
    'ngAria',
    'ngCookies',
    'ngMessages',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'pascalprecht.translate',
    'config'
  ])

  .provider('config', function(config, $logProvider) {

    this.start = function() {
      $logProvider.debugEnabled(config.debug);
      var initInjector = angular.injector(['ng']);
      var $http = initInjector.get('$http');
      var $log = initInjector.get('$log');
      $http.get(config.apiEndpoint)
        .then(function(response) {
          $log.debug('Loaded configuration from API endpoint ' + config.apiEndpoint, response);
          config.api = response.data;
        }, function(errorResponse) {
          $log.error('Server API connection error', errorResponse);
        });
    };

    this.$get = function () {};
  })

  .config(function ($routeProvider, $translateProvider, $logProvider, configProvider) {

    configProvider.start();

    $routeProvider
      /*.when('/', {
        templateUrl: 'views/home.html',
        controller: 'HomeController'
      })*/
      .when('/', {
        templateUrl: 'views/partials/body.html'
      })
      .when('/home', {
        templateUrl: 'views/home.html',
        controller: 'HomeController'
      })
      .when('/account/password-reset', {
        templateUrl: 'views/password-reset.html',
        controller: 'AccountController'
      })
      .when('/delivery', {
        templateUrl: 'views/partials/deliveries.html',
        controller: 'DeliveryController'
      })
      .otherwise({
        redirectTo: '/'
      });

    $translateProvider
      .useLocalStorage()
      .useStaticFilesLoader({
        prefix: 'i18n/',
        suffix: '.json'
      })
      .registerAvailableLanguageKeys(['en', 'de'], {
        'en_US': 'en',
        'en_UK': 'en',
        'de_DE': 'de',
        'de_CH': 'de'
      })
      .determinePreferredLanguage()
      //.determinePreferredLanguage(function () {
      //  return window.navigator.userLanguage || window.navigator.language || navigator.language;
      //})
      .fallbackLanguage('en')
      .useSanitizeValueStrategy('sanitize');

  });
