(function (ng) {
    var mod = ng.module("blogModule");
   //se define un nuevo controlador en el módulo
    mod.controller('blogCtrl', ['$scope', '$http', 'blogContext', '$state', 'grupoContext', 'usuarioContext',
        function ($scope, $http, blogContext, $state, grupoContext, usuarioContext) {
            $scope.blogsDeUsuario = false;
            //se consigue la lista de blogs del grupo
            $http.get(grupoContext+'/'+$state.params.grupoId+'/'+blogContext).then(function (response) {
                $scope.listBlog = response.data;
            }, function (error) {
                $state.go('blogGrupoError', {mensaje: error.data}, {reload: true});
            });
            
            //se prepara el estado detail
            if ($state.params.blogId !== undefined) {
                //se consiguen los datos del blog
                $http.get(grupoContext + '/' + $state.params.grupoId+'/'+blogContext+'/'+$state.params.blogId).then(function (response) {
                    $scope.blogActual = response.data;
                }, function (error) {
                    $state.go('blogGrupoError', {mensaje: error.data}, {reload: true});
                });
                
                /**
                 * agrega el blog actual a la lista de favoritos del usuario logueado
                 * @returns {undefined}
                 */
                $scope.agregarFavoritos = function () {
                    
                    $http.post(usuarioContext + '/' + sessionStorage.getItem("id") +'/'+ 
                        blogContext+'/'+$state.params.blogId).then(function () {
                        $state.go('blogDetail', {}, {reload: true});
                    }, function(error) {
                        $state.go('blogGrupoError', {mensaje: error.data}, {reload: true});
                    });
                };
                
                /**
                 * elimina el blog actual de la lista de favoritos del usuario logueado
                 * @returns {undefined}
                 */
                $scope.eliminarFavoritos = function () {
                    //el usuario logueado por defecto es el 1
                    $http.delete(usuarioContext + '/' + sessionStorage.getItem("id") +'/'+ 
                        blogContext+'/'+$state.params.blogId).then(function () {
                        $state.reload();
                    }, function (error) {
                        $state.go('blogGrupoError', {mensaje: error.data}, {reload: true});
                    });
                };
                
                
               
                //En vez de 1 debe ir el id del usuario logueado
                //Se está verificando si el blog actual pertenece a la lista de favoritos para mostrar los botones correctamente
                $scope.esFavorito = false;
                $http.get(usuarioContext + '/' + sessionStorage.getItem("id") +'/'+ 
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
