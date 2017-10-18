(function (ng) {
var mod = ng.module("noticiasModule", []);
    mod.constant("noticiasContext", "Stark/usuarios/1/noticias");
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/noticias/';
            $urlRouterProvider.otherwise("/noticiasList");

            $stateProvider.state('noticiasList', {
                url: '/noticias',
                views: {
                    'mainView': {
                        controller: 'noticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticias.list.html'
                    }
                }
            }).state('noticiaCreate', {
                url: '/noticias/create',
                views: {
                    'mainView': {
                        controller: 'noticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticias.create.html'
                    }
                }

            }).state('noticiaEdit', {
                url: '/noticias/:noticiaId',
                param: {
                    cityId: null
                },
                views: {
                    'mainView': {
                        controller: 'noticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticias.create.html'
                    }
                }
            });
        }]);

})(window.angular);

