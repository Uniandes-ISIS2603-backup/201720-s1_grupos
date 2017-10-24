(function (ng) {
    var mod = ng.module("blogModule");
   
    mod.controller('blogUpdateCtrl', ['$scope', '$http', 'blogContext', '$state', 'grupoContext', '$rootScope',
        function ($scope, $http, blogContext, $state, grupoContext, $rootScope) {
            $rootScope.edit=true;
            $scope.crearBlog=false;
            $scope.actualizarBlog=true;
            
            $scope.createBlog = function() {
                $http.put(grupoContext+'/'+$state.params.grupoId+'/'+blogContext + '/' + $state.params.blogId, {
                    titulo: $scope.tituloBlog,
                    contenido: $scope.contenidoBlog
                }).then(function (response) {
                    $state.go('blogDetail', {blogId:response.data.id}, {reload:true});
                });

            };
            
        }
    ]);
}
)(angular);

