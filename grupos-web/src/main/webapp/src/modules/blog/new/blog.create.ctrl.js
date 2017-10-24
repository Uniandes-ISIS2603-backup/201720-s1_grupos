(function (ng) {
    var mod = ng.module("blogModule");
   
    mod.controller('blogCreateCtrl', ['$scope', '$http', 'blogContext', '$state', 'grupoContext', '$rootScope',
        function ($scope, $http, blogContext, $state, grupoContext, $rootScope) {
            $rootScope.edit=false;
            $scope.crearBlog=true;
            $scope.actualizarBlog=false;
            
            $scope.createBlog = function() {
                $http.post(grupoContext+'/'+$state.params.grupoId+'/'+blogContext, {
                    titulo: $scope.tituloBlog,
                    contenido: $scope.contenidoBlog
                }).then(function (response) {
                    $state.go('blogList', {blogId:response.data.id}, {reload:true});
                });

            };
            
        }
    ]);
}
)(angular);
