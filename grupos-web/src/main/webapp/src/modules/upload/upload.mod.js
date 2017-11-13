(function (ng) {
    'use strict';
    var mod=angular.module('uploadModule', [// External dependencies
        'ui.router',
       ])
    .controller('uploadCtrl', [
        '$scope',
        '$upload',
        function ($scope, $upload) {
            $scope.model = {};
            $scope.selectedFile = [];
            $scope.uploadProgress = 0;

            $scope.uploadFile = function () {
                console.log("HOLAAAAAA");
                var file = $scope.selectedFile[0];
                $scope.upload = $upload.upload({
                    url: '/test',
                    method: 'POST',
                    data: angular.toJson($scope.model),
                    file: file
                }).progress(function (evt) {
                    $scope.uploadProgress = parseInt(100.0 * evt.loaded / evt.total, 10);
                }).success(function (data) {
                    console.log("EXITO");
                    //do something
                });
            };

            $scope.onFileSelect = function ($files) {
                $scope.uploadProgress = 0;
                $scope.selectedFile = $files;
            };
        }
    ])
    .directive('progressBar', [
        function () {
            return {
                link: function ($scope, el, attrs) {
                    $scope.$watch(attrs.progressBar, function (newValue) {
                        if(newValue!==undefined)
                        el.css('width', newValue.toString() + '%');
                    });
                }
            };
        }
    ]);
    
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

            var basePath = 'src/modules/upload/';

            $urlRouterProvider.otherwise("/upload");


            $stateProvider.state('upload', {
                url: '/upload',
                data: {
                    requireLogin: false
                },
                views: {
                    'mainView': {
                        templateUrl: basePath + 'upload.html'
                    }
                }
            });
        }]);

})(window.angular);

