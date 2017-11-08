(function (ng) {
    var mod = ng.module("blogModule");
   
    mod.controller('blogUpdateCtrl', ['$scope', '$http', 'blogContext', '$state', 'grupoContext', '$rootScope',
        function ($scope, $http, blogContext, $state, grupoContext, $rootScope) {
            $rootScope.edit=true;
            $scope.crearBlog=false;
            $scope.actualizarBlog=true;
            //se obtienen los datos del blog a modificar
            $http.get(grupoContext+'/'+$state.params.grupoId+'/'+blogContext + '/' + $state.params.blogId).then(function (response) {
                $scope.tituloBlog = response.data.titulo;
                $scope.contenidoBlog = response.data.contenido;
            });
            
            //se modifica el blog con un put
            $scope.createBlog = function() {
                $http.put(grupoContext+'/'+$state.params.grupoId+'/'+blogContext + '/' + $state.params.blogId, {
                    titulo: $scope.tituloBlog,
                    contenido: $scope.contenidoBlog
                }).then(function (response) {
                    $state.go('blogDetail', {blogId:response.data.id}, {reload:true});
                }, function (error) {
                    $state.go('blogGrupoError', {mensaje: error.data}, {reload: true});
                });

            };
            
        }
    ]);
}
)(angular);

