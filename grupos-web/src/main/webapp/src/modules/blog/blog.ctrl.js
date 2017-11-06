(function (ng) {
    var mod = ng.module("blogModule");
   
    mod.controller('blogCtrl', ['$scope', '$http', 'blogContext', '$state', 'grupoContext', 'usuarioContext',
        function ($scope, $http, blogContext, $state, grupoContext, usuarioContext) {
            $scope.blogsDeUsuario = false;
            
            $http.get(grupoContext+'/'+$state.params.grupoId+'/'+blogContext).then(function (response) {
                $scope.listBlog = response.data;
            });
            
            if ($state.params.blogId !== undefined) {
                
                $http.get(grupoContext + '/' + $state.params.grupoId+'/'+blogContext+'/'+$state.params.blogId).then(function (response) {
                    $scope.blogActual = response.data;
                });
                
                $scope.agregarFavoritos = function () {
                    $http.post(usuarioContext + '/' + '1' +'/'+ 
                        blogContext+'/'+$state.params.blogId).then(function (response) {
                        $state.go('blogDetail', {}, {reload: true});
                    });
                };
                
                $scope.eliminarFavoritos = function () {
                    $http.delete(usuarioContext + '/' + '1' +'/'+ 
                        blogContext+'/'+$state.params.blogId).then(function (response) {
                        $state.reload();
                    });
                };
                
                
               
                //En vez de 1 debe ir el id del usuario logueado
                $http.get(usuarioContext + '/' + '1' +'/'+ 
                    blogContext+'/'+$state.params.blogId).then(function (response) {
                     $scope.esFavorito = true;
                }, function(error) {
                     $scope.esFavorito = false;
                });
            }
        }
    ]);
}
)(angular);
