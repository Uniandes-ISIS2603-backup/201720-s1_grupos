(function (ng) {
var mod = ng.module("multimediaModule", ["noticiasModule","blogModule","ui.router"]);
    mod.constant("multimediaContext", "multimedia");

    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/multimedia/';
            $urlRouterProvider.otherwise("/multimediaList");
            
            $stateProvider.state('noticiaNoEditableMultimediaList', {
                url: '/multimedia',
                parent: 'noticiaNoEditableDetail',
                views: {
                    'noticiaMultimediaView': {
                        controller: 'noticiaMultimediaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.list.html'
                    }
                }
            }).state('ERRORMULTIMEDIANOTICIA', {
                url: '/error',
                parent:'noticiaNoEditableDetail',
                params:{
                    mensaje:null
                },
                views: {
                    'noticiaMultimediaView': {
                        ontroller: 'noticiaMultimediaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.error.html'
                    }
                }
            }).state('usuarioNoticiaMultimediaList', {
                url: '/multimedia/editable',
                parent: 'usuarioNoticiaDetail',
                views: {
                    'noticiaMultimediaView': {
                        controller: 'usuarioNoticiaMultimediaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.list.html'
                    }
                }
            }).state('usuarioNoticiaMultimediaCreate', {
                url: '/create',
                parent:'usuarioNoticiaMultimediaList',
                views: {
                    'multimediaListView': {
                        controller: 'usuarioNoticiaMultimediaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.create.html'
                    }
                }

            }).state('usuarioNoticiaMultimediaDelete', {
                url: '/:multimediaLink/delete',
                parent:'usuarioNoticiaMultimediaList',
                views: {
                    'multimediaListView': {
                        controller: 'usuarioNoticiaMultimediaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.delete.html'
                    }
                }
            }).state('usuarioNoticiaMultimediaEdit', {
                url: '/:multimediaLink/edit',
                parent:'usuarioNoticiaMultimediaList',
                param: {
                    multimediaLink:null
                },
                views: {
                    'multimediaListView': {
                        controller: 'usuarioNoticiaMultimediaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.create.html'
                    }
                }
            }).state('ERRORMULTIMEDIAUSUARIONOTICIA', {
                url: '/error',
                parent:'usuarioNoticiaDetail',
                params:{
                    mensaje:null
                },
                views: {
                    'noticiaMultimediaView': {
                        controller: 'usuarioNoticiaMultimediaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.error.html'
                    }
                }
            }).state('grupoNoticiaMultimediaList', {
                url: '/multimedia/editable',
                parent: 'grupoNoticiaDetail',
                views: {
                    'noticiaMultimediaView': {
                        controller: 'grupoNoticiaMultimediaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.list.html'
                    }
                }
            }).state('grupoNoticiaMultimediaCreate', {
                url: '/create',
                parent:'grupoNoticiaMultimediaList',
                
                views: {
                    'multimediaListView': {
                        controller: 'grupoNoticiaMultimediaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.create.html'
                    }
                }

            }).state('grupoNoticiaMultimediaDelete', {
                url: '/:multimediaLink/delete',
                parent:'grupoNoticiaMultimediaList',
                param: {
                    multimediaLink:null
                },
                views: {
                    'multimediaListView': {
                        controller: 'grupoNoticiaMultimediaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.delete.html'
                    }
                }
            }).state('grupoNoticiaMultimediaEdit', {
                url: '/:multimediaLink',
                parent:'grupoNoticiaMultimediaList',
                param: {
                    multimediaLink:null
                },
                views: {
                    'multimediaListView': {
                        controller: 'grupoNoticiaMultimediaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.create.html'
                    }
                }
            }).state('ERRORMULTIMEDIAGRUPONOTICIA', {
                url: '/error',
                parent:'grupoNoticiaDetail',
                params:{
                    mensaje:null
                },
                views: {
                    'noticiaMultimediaView': {
                        controller: 'grupoNoticiaMultimediaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.error.html'
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
                url: '/create',
                parent:'blogMultimediaList',
                views: {
                    'multimediaListView': {
                        controller: 'multimediaBlogCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.create.html'
                    }
                }

            }).state('blogMultimediaDelete', {
                url: '/:multimediaLink/delete',
                parent:'blogMultimediaList',
                param: {
                    multimediaLink:null
                },
                views: {
                    'multimediaListView': {
                        controller: 'multimediaBlogCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.delete.html'
                    }
                }
            }).state('blogMultimediaEdit', {
                url: '/:multimediaLink',
                parent:'blogMultimediaList',
                param: {
                    multimediaLink:null
                },
                views: {
                    'multimediaListView': {
                        controller: 'multimediaBlogCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.create.html'
                    }
                }
            }).state('ERRORMULTIMEDIABLOG', {
                url: '/error',
                parent:'blogDetail',
                params:{
                    mensaje:null
                },
                views: {
                    'childrenView': {
                        controller: 'multimediaBlogCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.error.html'
                    }
                }
            });
        }]);

})(window.angular);

