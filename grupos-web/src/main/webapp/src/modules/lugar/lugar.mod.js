(function (ng) {
    var mod = ng.module("lugarModule", ['eventoModule','ui.router']);
    mod.constant("lugaresContext", "Stark/lugares");
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/lugar/';
            $urlRouterProvider.otherwise("/lugaresList");

            $stateProvider.state('lugares', {
                url: '/lugares',
                abstract: true,
                views: {
                    'mainView': {
                        templateUrl: basePath + 'lugar.html',
                        controller: 'lugarCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('lugaresList', {
                url: '/list',
                parent: 'lugares',
                views: {
                    'listView': {
                        templateUrl: basePath + 'lugares.list.html',
                    }
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
                }
            }).state('lugaresCreate',{
                url: '/create',
                parent: 'lugares',
                views: {
                    'detailView':{
                        templateUrl: basePath + '/create/lugar.create.html',
                        controller: 'lugarCreateCtrl'
                    }
                }
            }).state('lugaresEvento', {
                url: '/lugar',
                abstract: true,
                parent: 'eventoDetail',
                views: {
                    'childrenView': {
                        templateUrl: basePath + 'lugar.html',
                        controller: 'lugarCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('lugarEvento', {
                url: '/lugar',
                parent: 'lugaresEvento',
                views: {
                    'childrenView': {
                        templateUrl: basePath + 'lugar.detail.html',
                        controller: 'lugarCtrl',
                        controllerAs: 'ctrl'
                    }

                }

            });
        }]);
    })(window.angular);