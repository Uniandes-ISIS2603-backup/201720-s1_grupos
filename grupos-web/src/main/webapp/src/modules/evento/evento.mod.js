(function (ng) {
    var mod = ng.module("eventoModule", ['ui.router']);
    mod.constant("eventosContext", "Stark/eventos");
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/evento/';
            $urlRouterProvider.otherwise("/eventosList");

            $stateProvider.state('eventos', {
                url: '/eventos',
                abstract: true,
                views: {
                    'mainView': {
                        templateUrl: basePath + 'evento.html',
                        controller: 'eventoCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('eventosList', {
                url: '/list',
                parent: 'eventos',
                views: {
                    'listView': {
                        templateUrl: basePath + 'eventos.list.html',
                    }
                }
            }).state('eventoDetail', {
                url: '/{eventoId:int}/detail',
                parent: 'eventos',
                param: {
                    eventoId: null
                },
                views: {
                        'listView': {
                        templateUrl: basePath + 'eventos.list.html'
                    },
                    'mainView': {
                        templateUrl: basePath + 'evento.detail.html',
                        controller: 'eventoCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('eventosCreate',{
                url: '/create',
                parent: 'eventos',
                views: {
                    'detailView':{
                        templateUrl: basePath + '/create/evento.create.html',
                        controller: 'eventoCreateCtrl'
                    }
                }
            }).state('eventoUpdate', {
                url: '/update/{eventoId:int}',
                parent: 'eventos',
                param: {
                    authorId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + '/create/evento.create.html',
                        controller: 'eventoUpdateCtrl'
                    }
                }
            }).state('eventoDelete', {
                url: '/delete/{eventoId:int}',
                parent: 'eventos',
                param: {
                    authorId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + '/delete/evento.delete.html',
                        controller: 'eventoDeleteCtrl'
                    }
                }
            });
        }]);
    })(window.angular);