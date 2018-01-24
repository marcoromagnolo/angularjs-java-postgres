'use strict';

angular.module('webApp')

  .directive('authClick', function () {
    return {
      restrict: 'A',
      scope: true,
      link: function(scope, element, attrs){
        element.on('click', function() {
          if (!scope.isAuthorized) {
            scope.$apply(function(){
              scope.openLoginDialog();
            });
            scope.applyAfterLogin = attrs.authClick;
          } else {
            scope.$apply(attrs.authClick);
          }
        });

      }
    };
  });
