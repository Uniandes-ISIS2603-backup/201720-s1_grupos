(function (ng) {
    //Variable de módulo
var mod = ng.module("multimediaModule", ["noticiasModule","blogModule","ui.router"]);
//Constante del contexto de multimedia
    mod.constant("multimediaContext", "multimedia");
    //Configuración con $stateProvider, $urlRouterProvider
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            //Ruta base
            var basePath = 'src/modules/multimedia/';
            //Ruta por default
            $urlRouterProvider.otherwise("/multimediaList");
            /**
             * Diferentes estados
             * noticiaNoEditableMultimediaList
             * -ERRORMULTIMEDIANOTICIA
             * -usuarioNoticiaMultimediList
             * -usuarioNoticiaMultimediaCreate
             * -usuarioNoticiaMultimediaDelete
             * -usuarioNoticiaMultimediaEdit
             * -ERRORMULTIMEDIAUSUARIONOTICIA
             * -grupoNoticiaMultimediaList
             * -grupoNoticiaMultimediaCreate
             * -grupoNoticiaMultimediaDelete
             * -grupoNoticiaMultimediaDelete
             * -ERORMULTIMEDIAGRUPONOTTICIA
             * -blogMultimediaList
             * -blogMultimediaCreate
             * -blogMultimediaDelete
             * -blogMultimediaEdit
             * -ERRORMULTIMEDIABLOG
             */
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
            }).state('noticiaNoEditableMultimediaDetail', {
                url: '/detail/:multimediaLink',
                parent:'noticiaNoEditableMultimediaList',
                param: {
                    multimediaLink:null
                },
                views: {
                    'multimediaListView': {
                        controller: 'noticiaMultimediaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.detail.html'
                    }
            }}).state('ERRORMULTIMEDIANOTICIA', {
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
            }).state('usuarioNoticiaMultimediaDetail', {
                url: '/detail/:multimediaLink',
                parent:'usuarioNoticiaMultimediaList',
                param: {
                    multimediaLink:null
                },
                views: {
                    'multimediaListView': {
                        controller: 'usuarioNoticiaMultimediaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.detail.html'
                    }
            }}).state('usuarioNoticiaMultimediaCreate', {
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
                parent:'usuarioNoticiaMultimediaDetail',
                views: {
                    'multimediaListView': {
                        controller: 'usuarioNoticiaMultimediaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.delete.html'
                    }
                }
            }).state('usuarioNoticiaMultimediaEdit', {
                url: '/:multimediaLink/edit',
                parent:'usuarioNoticiaMultimediaDetail',
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
                param:{
                    admin:null,
                    miembro:null
                },
                views: {
                    'noticiaMultimediaView': {
                        controller: 'grupoNoticiaMultimediaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.list.html'
                    }
                }
            }).state('grupoNoticiaMultimediaDetail', {
                url: '/detail/:multimediaLink',
                parent:'grupoNoticiaMultimediaList',
                param: {
                    multimediaLink:null
                },
                views: {
                    'multimediaListView': {
                        controller: 'grupoNoticiaMultimediaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.detail.html'
                    }
            }}).state('grupoNoticiaMultimediaCreate', {
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
                parent:'grupoNoticiaMultimediaDetail',
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
                parent:'grupoNoticiaMultimediaDetail',
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
                param:{
                    admin:null,
                    miembro:null
                },
                views: {
                    'childrenView': {
                        controller: 'multimediaBlogCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.list.html'
                    }
                }
            }).state('blogMultimediaDetail', {
                url: '/detail/:multimediaLink',
                parent:'blogMultimediaList',
                param: {
                    multimediaLink:null
                },
                views: {
                    'multimediaListView': {
                        controller: 'multimediaBlogCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'multimedia.detail.html'
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
                parent:'blogMultimediaDetail',
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
                parent:'blogMultimediaDetail',
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
            })
        }]);

})(window.angular);

