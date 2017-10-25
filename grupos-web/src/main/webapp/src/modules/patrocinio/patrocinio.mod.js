(function (ng) {
    var mod = ng.module("patrocinioModule", []);
    mod.constant("patrocinioContext","Stark/patrocinios");
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/patrocinio/';
            $urlRouterProvider.otherwise("patrociniosList");

            $stateProvider.state('patrocinios', {
                url: '/patrocinios',
                abstract: true,
                views: {
                    'mainview': {
                        controller: 'patrocinioCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'patrocinio.html'
                    }
                }
            }).state('patrocinioDetail',{
                url: 'patrocinios/{:patrocinioId}/detail',
                parent: 'patrocinios',
                param:{
                    patrocinioId: null
                },
                views: {
                    'mainview': {
                        controller: 'patrociniosCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'patrocinio.detail.html'
                    }
                }
            }).state('patrociniosList',{
                url: '/list',
                parent: 'patrocinios',
                views: {
                    'listView': {
                        templateUrl: basePath + 'patrocinios.list.html',
                    }
                }
            })
        }]);
    
})(window.angular);