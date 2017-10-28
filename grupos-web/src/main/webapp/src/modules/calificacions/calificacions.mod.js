(function (ng) {
var mod = ng.module("calificacionsModule", []);
    mod.constant("gruposContext", "Stark/grupos");
        mod.constant("blogContext", "blogs");

    mod.constant("calificacionsContext", "calificaciones");
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/calificacions/';
            $urlRouterProvider.otherwise("/calificacionsList");

            $stateProvider.state('calificacionsList', {
                url: '/calificacions',
                parent:'blogDetail',
                views: {
                    'childrenView': {
                        controller: 'calificacionsCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'calificacions.list.html'
                    }
                }
            }).state('calificacionCreate', {
                url: '/calificacions/create',
                parent:'blogDetail',
                views: {
                    'childrenView': {
                        controller: 'calificacionsCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'calificacions.create.html'
                    }
                }

            }).state('calificacionEdit', {
                url: '/calificacions/:calificacionId',
                parent:'blogDetail',

                param: {
                    calificacionId: null
                },
                views: {
                    'childrenView': {
                        controller: 'calificacionsCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'calificacions.create.html'
                    }
                }
            }).state('calificacionDelete', {
                url: '/calificacions/delete/:calificacionId',
                parent:'blogDetail',
                param: {
                    calificacionId: null
                },
                views: {
                    'childrenView': {
                        controller: 'calificacionsCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'calificacion.delete.html'
                    }
                }
            }).state('calificacionDetail',{
                url:'/calificacions/:calificacionId/detail',
               parent:'blogDetail',

                param:{
                    calificacionId:null
                },
                views:{
                        'childrenView': {
                        controller: 'calificacionsCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'calificacions.detail.html'
                            }
                        }
            });
        }]);

})(window.angular);

