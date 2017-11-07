(function (ng) {
    var mod = ng.module("blogModule");
   
    mod.controller('blogCtrl', ['$scope', '$http', 'blogContext', '$state', 'grupoContext', 'usuarioContext',
        function ($scope, $http, blogContext, $state, grupoContext, usuarioContext) {
            $scope.blogsDeUsuario = false;
            
            $http.get(grupoContext+'/'+$state.params.grupoId+'/'+blogContext).then(function (response) {
                $scope.listBlog = response.data;
            }, function (error) {
                $state.go('blogGrupoError', {mensaje: error.data}, {reload: true});
            });
            
            if ($state.params.blogId !== undefined) {
                
                $http.get(grupoContext + '/' + $state.params.grupoId+'/'+blogContext+'/'+$state.params.blogId).then(function (response) {
                    $scope.blogActual = response.data;
                }, function (error) {
                    $state.go('blogGrupoError', {mensaje: error.data}, {reload: true});
                });
                
                $scope.agregarFavoritos = function () {
                    $http.post(usuarioContext + '/' + '1' +'/'+ 
                        blogContext+'/'+$state.params.blogId).then(function (response) {
                        $state.go('blogDetail', {}, {reload: true});
                    }, function(error) {
                        $state.go('blogGrupoError', {mensaje: error.data}, {reload: true});
                    });
                };
                
                $scope.eliminarFavoritos = function () {
                    $http.delete(usuarioContext + '/' + '1' +'/'+ 
                        blogContext+'/'+$state.params.blogId).then(function (response) {
                        $state.reload();
                    }, function (error) {
                        $state.go('blogGrupoError', {mensaje: error.data}, {reload: true});
                    });
                };
                
                
               
                //En vez de 1 debe ir el id del usuario logueado
                $scope.esFavorito = false;
                $http.get(usuarioContext + '/' + '1' +'/'+ 
                    blogContext).then(function (response) {
                        var lista = response.data;
                        for(var i = 0; i<lista.length; i++) {
                            if(lista[i].id === $state.params.blogId) {
                                $scope.esFavorito = true;
                                break;
                            }
                        }
                }, function(error) {
                     $state.go('blogGrupoError', {mensaje: error.data}, {reload: true});
                });
            }
        }
    ]);
}
)(angular);
