(function (ng) {
    //se crea un nuevo módulo
    var mod = ng.module("blogModule", ['grupoModule', 'ui.router']);
    //se definen las constantes necesarias para los paths
    mod.constant("blogContext", "blogs");
    mod.constant("grupoContext", "Stark/grupos");
    mod.constant("usuarioContext", "Stark/usuarios");
    
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            /**
             * path base de los scripts y templates usados
             * @type String
             */
            var basePath = 'src/modules/blog/';
            
            //Se definen los estados
            $stateProvider.state('blog', {
                url:'/blogs',
                abstract:true,
                parent:'grupoDetail',
                views: {
                   'childrenView': {
                        templateUrl: basePath + 'blog.html',
                        controller: 'blogCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                data: {
                    requireLogin: false,
                    roles: []
                }
            }).state('blogList', {
                url:'/list',
                parent:'blog',
                views: {
                   'listView': {
                        templateUrl: basePath + 'blog.list.html',
                        controller: 'blogCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('blogFavList', {
                url:'/blogs/list',
                parent:'usuarioDetail',
                views: {
                   'childrenView': {
                        templateUrl: basePath + 'blog.list.html',
                        controller: 'blogFavCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('blogDetail', {
                url:'/{blogId:int}',
                parent:'blog',
                param: {
                    blogId: null
                },
                views: {
                   'detailView': {
                        templateUrl: basePath + 'blog.detail.html',
                        controller: 'blogCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('blogCreate', {
                url:'/create',
                parent:'blog',
                views: {
                   'detailView': {
                        templateUrl: basePath + 'new/blog.create.html',
                        controller: 'blogCreateCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('blogUpdate', {
                url:'/update',
                parent:'blogDetail',
                views: {
                   'childrenView': {
                        templateUrl: basePath + 'new/blog.create.html',
                        controller: 'blogUpdateCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('blogDelete', {
                url:'/delete',
                parent:'blogDetail',
                views: {
                   'childrenView': {
                        templateUrl: basePath + 'delete/blog.delete.html',
                        controller: 'blogDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('blogGrupoError', {
                url:'/error',
                parent:'blog',
                params:{
                    mensaje: null
                },
                views: {
                   'detailView': {
                        templateUrl: basePath + 'blog.error.html',
                        controller: 'blogGrupoErrorCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('blogUsuarioError', {
                url:'/error',
                parent:'usuarioDetail',
                params:{
                    mensaje: null
                },
                views: {
                   'childrenView': {
                        templateUrl: basePath + 'blog.error.html',
                        controller: 'blogUsuarioErrorCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            
        }]);
    
})(window.angular);

