'use strict';

angular.module('webApp')

  .directive('modal', function () {
    return {
      templateUrl: 'views/templates/modal.html',
      restrict: 'E',
      transclude: true,
      replace:true,
      scope:true,
      link: function post(scope, element, attrs) {
        scope.title = attrs.title;

        scope.$watch(attrs.rendered, function(value) {
          if(value === true) {
            $(element).modal('show');
          } else {
            $(element).modal('hide');
          }
        });

        $(element).on('shown.bs.modal', function(){
          scope.$apply(function(){
            scope.$parent[attrs.rendered] = true;
          });
        });

        $(element).on('hidden.bs.modal', function(){
          scope.$apply(function(){
            scope.$parent[attrs.rendered] = false;
          });
        });
      }
    };
  });
