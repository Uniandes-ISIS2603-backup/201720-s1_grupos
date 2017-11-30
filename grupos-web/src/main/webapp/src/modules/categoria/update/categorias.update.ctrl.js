/**
 * Controlador para actualizar las categorías
 */
(function (ng) {
    var mod = ng.module("categoriaModule");
    mod.constant("categoriasContext", "Stark/categorias");    
    mod.controller('categoriaUpdateCtrl', ['$scope', '$http', 'categoriasContext', '$state', '$rootScope',
        function ($scope, $http, categoriasContext, $state, $rootScope) {
            //Guarda el id de la categoría
            
            $scope.errorCategoriaNombre=false;
            $rootScope.edit = true;            
            var idcategoria = $state.params.categoriaId;
            $scope.crearcategoria=false;
            $scope.actualizarcategoria=true;
            //Consulta la categoría a editar
            $http.get(categoriasContext + '/' + idcategoria).then(function (response) {
                var categoriaActual = response.data;
                $scope.categoriaName = categoriaActual.tipo;
                $scope.categoriaDescription = categoriaActual.descripcion;
                $scope.categoriaRuta = categoriaActual.rutaIcono;
            });
            /**
             * Función para actualizar la categoría
             */
            $scope.createCategoria = function () {
                /*Se llama a la función newBooks() para buscar cada uno de los ids de los books
                         en el array que tiene todos los books y así saber como queda la lista final de los books asociados al autor.
                 */
                $http.put(categoriasContext + "/" + idcategoria, {
                    //Se envía el servicio PUT
                    nombre: $scope.categoriaName,
                    descripcion: $scope.categoriaDescription,
                    rutaIcono: $scope.categoriaRuta
                }).then(function (response) {                    
                    //se creó bien, se va a su detail
                    $state.go('categoriaDetail2', {categoriaId: response.data.id}, {reload: true});
                }, function () {                    
                    $scope.errorCategoriaNombre=true;
                });
            };
            
            
        }
    ]);
}
        )(angular);