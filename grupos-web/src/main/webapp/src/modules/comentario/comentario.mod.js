(function (ng) {
    var mod = ng.module("comentarioModule", ['blogModule', 'ui.router','noticiasModule']);
    mod.constant("comentarioContext", "comentarios");
    mod.constant("blogContext", "blogs");
    mod.constant("grupoContext", "Stark/grupos");
    mod.constant("noticiaContext", "Stark/noticias");
    
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/comentario/';
            $urlRouterProvider.otherwise("/comentarioList");
            
            $stateProvider.state('comentarioList', {
                url: '/comentarios',
                parent: 'blogDetail',
                views: {
                    'childrenView': {
                        templateUrl: basePath + 'comentario.list.html',
                        controller: 'comentarioCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('comentarioNoticiaList', {
                url: '/comentarios',
                parent: 'noticiaNoEditableDetail',
                views: {
                    'comentarioView': {
                        templateUrl: basePath + 'comentario.list.html',
                        controller: 'comentarioNoticiaCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('comentarioUsuarioNoticiaList', {
                url: '/comentarios',
                parent: 'usuarioNoticiaDetail',
                views: {
                    'comentarioView': {
                        templateUrl: basePath + 'comentario.list.html',
                        controller: 'comentarioUsuarioNoticiaCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('comentarioGrupoNoticiaList', {
                url: '/comentarios',
                parent: 'grupoNoticiaDetail',
                views: {
                    'comentarioView': {
                        templateUrl: basePath + 'comentario.list.html',
                        controller: 'comentarioGrupoNoticiaCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('comentarioCreate', {
                url: '/create',
                parent: 'comentarioList',
                views: {
                    'createView': {
                        templateUrl: basePath + 'new/comentario.create.html',
                        controller: 'comentarioCreateCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('comentarioNoticiaCreate', {
                url: '/create',
                parent: 'comentarioNoticiaList',
                views: {
                    'createView': {
                        templateUrl: basePath + 'new/comentario.create.html',
                        controller: 'comentarioNoticiaCreateCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('comentarioUsuarioNoticiaCreate', {
                url: '/create',
                parent: 'comentarioUsuarioNoticiaList',
                views: {
                    'createView': {
                        templateUrl: basePath + 'new/comentario.create.html',
                        controller: 'comentarioNoticiaCreateCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('comentarioGrupoNoticiaCreate', {
                url: '/create',
                parent: 'comentarioGrupoNoticiaList',
                views: {
                    'createView': {
                        templateUrl: basePath + 'new/comentario.create.html',
                        controller: 'comentarioNoticiaCreateCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('comentarioDelete', {
                url: '/{comentarioId:int}/delete',
                parent: 'comentarioList',
                param: {
                    comentarioId: null
                },
                views: {
                    'deleteView': {
                        templateUrl: basePath + 'delete/comentario.delete.html',
                        controller: 'comentarioDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('comentarioNoticiaDelete', {
                url: '/{comentarioId:int}/delete',
                parent: 'comentarioNoticiaList',
                param: {
                    comentarioId: null
                },
                views: {
                    'deleteView': {
                        templateUrl: basePath + 'delete/comentario.delete.html',
                        controller: 'comentarioNoticiaDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('comentarioUsuarioNoticiaDelete', {
                url: '/{comentarioId:int}/delete',
                parent: 'comentarioUsuarioNoticiaList',
                param: {
                    comentarioId: null
                },
                views: {
                    'deleteView': {
                        templateUrl: basePath + 'delete/comentario.delete.html',
                        controller: 'comentarioNoticiaDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('comentarioGrupoNoticiaDelete', {
                url: '/{comentarioId:int}/delete',
                parent: 'comentarioGrupoNoticiaList',
                param: {
                    comentarioId: null
                },
                views: {
                    'deleteView': {
                        templateUrl: basePath + 'delete/comentario.delete.html',
                        controller: 'comentarioNoticiaDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('comentarioUpdate', {
                url: '/{comentarioId:int}/update',
                parent: 'comentarioList',
                param: {
                    comentarioId: null
                },
                views: {
                    'deleteView': {
                        templateUrl: basePath + 'update/comentario.update.html',
                        controller: 'comentarioUpdateCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('comentarioNoticiaUpdate', {
                url: '/{comentarioId:int}/update',
                parent: 'comentarioNoticiaList',
                param: {
                    comentarioId: null
                },
                views: {
                    'deleteView': {
                        templateUrl: basePath + 'update/comentario.update.html',
                        controller: 'comentarioNoticiaUpdateCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('comentarioUsuarioNoticiaUpdate', {
                url: '/{comentarioId:int}/update',
                parent: 'comentarioUsuarioNoticiaList',
                param: {
                    comentarioId: null
                },
                views: {
                    'deleteView': {
                        templateUrl: basePath + 'update/comentario.update.html',
                        controller: 'comentarioNoticiaUpdateCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('comentarioGrupoNoticiaUpdate', {
                url: '/{comentarioId:int}/update',
                parent: 'comentarioGrupoNoticiaList',
                param: {
                    comentarioId: null
                },
                views: {
                    'deleteView': {
                        templateUrl: basePath + 'update/comentario.update.html',
                        controller: 'comentarioNoticiaUpdateCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
        }]);
    
})(window.angular);
