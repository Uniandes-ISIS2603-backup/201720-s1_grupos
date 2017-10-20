(function (ng) {
var mod = ng.module("multimediaModule", []);
    mod.constant("multimediaContext", "Stark/usuarios/1/noticias/1/multimedia");
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/multimedia/';
            $urlRouterProvider.otherwise("/multimediaList");

            $stateProvider.state('multimediaList', {
                url: '/multimedia',
                views: {
                    'mainView': {
                        controller: 'multimediaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.list.html'
                    }
                }
            }).state('multimediaCreate', {
                url: '/multimedia/create',
                views: {
                    'mainView': {
                        controller: 'multimediaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.create.html'
                    }
                }

            }).state('multimediaEdit', {
                url: '/multimedia/:noticiaId',
                param: {
                    cityId: null
                },
                views: {
                    'mainView': {
                        controller: 'multimediaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.create.html'
                    }
                }
            });
        }]);

})(window.angular);

