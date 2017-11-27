(function (ng) {
    var mod = ng.module("lugarModule", ['eventoModule','ui.router']);
    mod.constant("lugaresContext", "Stark/lugares");
    mod.config(['$stateProvider', function ($stateProvider) {
            var basePath = 'src/modules/lugar/';

            $stateProvider.state('lugares', {
                url: '/lugares',
                abstract: true,
                views: {
                    'mainView': {
                        templateUrl: basePath + 'lugar.html',
                        controller: 'lugarCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Ciudadano','Administrador']
                }
            }).state('lugaresList', {
                url: '/list',
                parent: 'lugares',
                views: {
                    'listView': {
                        templateUrl: basePath + 'lugares.list.html',
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Ciudadano','Administrador']
                }
            }).state('lugarDetail', {
                url: '/{lugarId:int}/detail',
                parent: 'lugares',
                param: {
                    lugarId: null
                },
                views: {
                        'listView': {
                        templateUrl: basePath + 'lugares.list.html'
                    },
                    'detailView': {
                        templateUrl: basePath + 'lugar.detail.html',
                        controller: 'lugarCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Ciudadano','Administrador']
                }
            }).state('lugaresCreate',{
                url: '/create',
                parent: 'lugares',
                views: {
                    'detailView':{
                        templateUrl: basePath + '/create/lugar.create.html',
                        controller: 'lugarCreateCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Administrador']
                }
            }).state('lugaresEvento', {
                abstract: true,
                parent: 'eventoDetail',
                views: {
                    'childrenView': {
                        templateUrl: basePath + 'lugar.html',
                        controller: 'lugarCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Ciudadano', 'Administrador']
                }
            }).state('lugarEvento', {
                url: '/lugar',
                parent: 'lugaresEvento',
                views: {
                    'detailView': {
                        templateUrl: basePath + 'lugar.detail.html',
                        controller: 'lugarCtrl',
                        controllerAs: 'ctrl'
                    }

                },
                data: {
                    requireLogin: true,
                    roles: ['Ciudadano','Administrador']
                }
            });
        }]);
    })(window.angular);