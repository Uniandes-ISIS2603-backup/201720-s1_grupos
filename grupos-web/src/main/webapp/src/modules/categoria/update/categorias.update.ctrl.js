(function (ng) {
    var mod = ng.module("categoriaModule");
    mod.constant("categoriasContext", "Stark/categorias");
    
    mod.controller('categoriaUpdateCtrl', ['$scope', '$http', 'categoriasContext', '$state', '$rootScope', '$filter',
        function ($scope, $http, categoriasContext, $state, $rootScope, $filter) {
            $rootScope.edit = true;
            
            var idcategoria = $state.params.categoriaId;
            $scope.crearcategoria=false;
            $scope.actualizarcategoria=true;
            //Consulto el autor a editar.
            $http.get(categoriasContext + '/' + idcategoria).then(function (response) {
                var categoriaActual = response.data;
                $scope.categoriaName = categoriaActual.nombre;
                $scope.categoriaDescription = categoriaActual.descripcion;
            });
            
            $scope.createCategoria = function () {
                /*Se llama a la función newBooks() para buscar cada uno de los ids de los books
                         en el array que tiene todos los books y así saber como queda la lista final de los books asociados al autor.
                 */
                $http.put(categoriasContext + "/" + idcategoria, {
                    nombre: $scope.categoriaName,
                    descripcion: $scope.categoriaDescription
                }).then(function (response) {
                    
                    //categoria created successfully
                    $state.go('categoriaDetail', {categoriaId: response.data.id}, {reload: true});
                }, function (error,status) {
                    $scope.errorcategorias=status;
                    $("#modalErrorcategorias").modal();
                });
            };
            
            
        }
    ]);
}
        )(angular);