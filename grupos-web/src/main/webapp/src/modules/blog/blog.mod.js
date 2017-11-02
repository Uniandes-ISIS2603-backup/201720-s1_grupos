(function (ng) {
    var mod = ng.module("blogModule", ['grupoModule', 'ui.router']);
    mod.constant("blogContext", "blogs");
    mod.constant("grupoContext", "Stark/grupos");
    
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/blog/';
            $urlRouterProvider.otherwise("/blogList");
            
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
                url:'/list',
                parent:'blog',
                param: {
                    usuarioId: null
                },
                views: {
                   'listView': {
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
            }).state('blogFavDetail', {
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
            });
            
        }]);
    
})(window.angular);

