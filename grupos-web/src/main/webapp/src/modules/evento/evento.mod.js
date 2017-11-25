(function (ng) {
    var mod = ng.module("eventoModule", ['mwl.calendar', 'ui.bootstrap', 'ui.router']);
       //Constante global
    mod.constant("globalContext","Stark");
    //Contante de noticias
    mod.constant("eventosContext", "eventos");
    
    mod.constant("eventoGrupoContext","grupos");
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/evento/';

            $stateProvider.state('eventos', {
                url: '/eventos',
                abstract: true,
                views: {
                    'mainView': {
                        templateUrl: basePath + 'evento.html',
                        controller: 'eventoCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                data: {
                    requireLogin: false,
                    roles: []
                }
            }).state('eventosList', {
                url: '/list',
                parent: 'eventos',
                views: {
                    'listView': {
                        templateUrl: basePath + 'eventos.list.html'
                    }
                }
            }).state('eventoDetail', {
                url: '/{eventoId:int}/detail',
                parent: 'eventos',
                param: {
                    eventoId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + 'evento.detail.html',
                        controller: 'eventoCtrl',
                        controllerAs: 'ctrl'
                    }
                },data: {
                    requireLogin: false,
                    roles: []
                }
            }).state('grupoEventos',{
                url: '/eventos',
                abstract : true,
                parent : 'grupoDetail',
                params : {
                    grupoId : null
                },
                views : {
                        'childrenView': {
                        controller: 'grupoEventosCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'evento.html'
                    }
                }

            }).state('eventosDeGrupo', {
                url: '/list',
                parent:'grupoEventos',
                params:{
                    grupoId:null
                },
                views: {
                    'listView': {
                        controller: 'grupoEventosCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'eventos.list.html'
                    }
                },
                data: {
                    requireLogin: false,
                    roles: []
                }
            }).state('eventosCreate',{
                url: '/create',
                parent: 'grupoEventos',
                params:{
                    grupoId:null
                },
                views: {
                    'detailView':{
                        templateUrl: basePath + '/create/evento.create.html',
                        controller: 'grupoEventosCtrl'
                    }
                }
            }).state('eventosCalendar',{
                url: '/calendar',
                parent: 'eventosDeGrupo',
                views: {
                    'calendarView':{
                        templateUrl: basePath + 'eventos.calendar.html',
                        controller: 'eventosCalendarCtrl',
                        controllerAs: 'vm'
                    }
                }
            }).state('eventoCalendarDetail',{
                url: '/{eventoId}/detail',
                parent: 'eventosCalendar',
                param: {
                    eventoId: null
                },
                views: {
                    'detailView':{
                        templateUrl: basePath + 'evento.detail.html',
                        controller: 'eventoCalendarDetailCtrl'
                    }
                }
            }).state('eventoUpdate', {
                url: '/update/{eventoId:int}',
                parent: 'eventos',
                param: {
                    eventoId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + '/update/evento.update.html',
                        controller: 'eventoUpdateCtrl'
                    }
                }
            }).state('eventoDelete', {
                url: '/delete/{eventoId:int}',
                parent: 'grupoEventos',
                params: {
                    eventoId: null,
                    grupoId:null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + '/delete/evento.delete.html',
                        controller: 'grupoEventosCtrl'
                    }
                }
            });
        }]);
    })(window.angular);