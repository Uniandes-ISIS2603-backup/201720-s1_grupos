(function (ng) {
    var mod = ng.module("blogModule", ['ui.router']);
     mod.constant("blogContext", "blog");
    mod.constant("grupoContext", "Stark/grupos");
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/blog/';
            $urlRouterProvider.otherwise("/blogList");
            
            $stateProvider.state('blogList', {
                url:'blogs',
                abstract:true,
                views: {
                   'mainView': {
                        templateUrl: basePath + 'blog.list.html',
                        controller: 'blogCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            
        }]);
    
})(window.angular);

