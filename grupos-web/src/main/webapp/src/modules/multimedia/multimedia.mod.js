(function (ng) {
var mod = ng.module("multimediaModule", ["noticiasModule","ui.router"]);
    mod.constant("multimediaContext", "multimedia");

    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/multimedia/';
            $urlRouterProvider.otherwise("/multimediaList");
            
            $stateProvider.state('noticiaNoEditableMultimediaList', {
                url: '/multimedia',
                parent: 'usuarioNoticiaNoEditableDetail',
                views: {
                    'noticiaMultimediaView': {
                        controller: 'noticiaMultimediaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.list.html'
                    }
                }
            }).state('usuarioNoticiaMultimediaList', {
                url: '/multimedia/editable',
                parent: 'usuarioNoticiaDetail',
                params:{
                    idUsuario:1
                },
                views: {
                    'noticiaMultimediaView': {
                        controller: 'usuarioNoticiaMultimediaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.list.html'
                    }
                }
            }).state('usuarioNoticiaMultimediaCreate', {
                url: '/multimedia/create',
                parent:'usuarioNoticiaMultimediaList',
                
                views: {
                    'multimediaCreate': {
                        controller: 'usuarioNoticiaMultimediaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.create.html'
                    }
                }

            }).state('usuarioNoticiaMultimediaEdit', {
                url: '/multimedia/:multimediaLink',
                parent:'usuarioNoticiaMultimediaList',
                param: {
                    multimediaLink:null
                },
                views: {
                    'multimediaCreate': {
                        controller: 'usuarioNoticiaMultimediaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.create.html'
                    }
                }
            }).state('grupoNoticiaMultimediaList', {
                url: '/multimedia/editable',
                parent: 'grupoNoticiaDetail',
                params:{
                    idUsuario:1
                },
                views: {
                    'noticiaMultimediaView': {
                        controller: 'grupoNoticiaMultimediaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.list.html'
                    }
                }
            }).state('grupoNoticiaMultimediaCreate', {
                url: '/multimedia/create',
                parent:'grupoNoticiaDetail',
                
                views: {
                    'multimediaCreate': {
                        controller: 'grupoNoticiaMultimediaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.create.html'
                    }
                }

            }).state('grupoNoticiaMultimediaEdit', {
                url: '/multimedia/:multimediaLink',
                parent:'grupoNoticiaDetail',
                param: {
                    multimediaLink:null
                },
                views: {
                    'multimediaCreate': {
                        controller: 'grupoNoticiaMultimediaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.create.html'
                    }
                }
            }).state('blogMultimediaList', {
                url: '/multimedia/editable',
                parent: 'blogDetail',
                views: {
                    'childrenView': {
                        controller: 'multimediaBlogCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.list.html'
                    }
                }
            }).state('blogMultimediaCreate', {
                url: '/multimedia/create',
                parent:'blogDetail',
                views: {
                    'multimediaCreate': {
                        controller: 'multimediaBlogCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.create.html'
                    }
                }

            }).state('blogMultimediaEdit', {
                url: '/multimedia/:multimediaLink',
                parent:'blogDetail',
                param: {
                    multimediaLink:null
                },
                views: {
                    'multimediaCreate': {
                        controller: 'multimediaBlogCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.create.html'
                    }
                }
            });
        }]);

})(window.angular);

