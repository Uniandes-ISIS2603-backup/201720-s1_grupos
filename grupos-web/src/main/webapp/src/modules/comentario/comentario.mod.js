(function (ng) {
    var mod = ng.module("comentarioModule", ['blogModule', 'ui.router']);
    mod.constant("comentarioContext", "comentarios");
    mod.constant("blogContext", "blogs");
    mod.constant("grupoContext", "Stark/grupos");
    
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
            })
        }]);
    
})(window.angular);
