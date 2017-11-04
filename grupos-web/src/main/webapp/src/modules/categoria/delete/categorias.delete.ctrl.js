/**
 * Controlador para borrar las categorías
 */
(function (ng) {
    var mod = ng.module("categoriaModule");
    mod.constant("categoriasContext", "Stark/categorias");    
    mod.controller('categoriaDeleteCtrl', ['$scope', '$http', 'categoriasContext', '$state',
        function ($scope, $http, categoriasContext, $state) {
            //Se guarda el id de la categoría
            var idcategoria = $state.params.categoriaId;
            $scope.idCategoria=idcategoria;
            $scope.crearcategoria=true;
            $scope.actualizarcategoria=false;
            /**
             * Función para borrar una categoría
             */
            $scope.deleteCategoria = function () {
                //Se invoca el servicio DELETE correspondiente
                $http.delete(categoriasContext + '/' + idcategoria, {}).then(function (response) {
                    $state.go('carruselCategorias',{}, {reload: true});
                }, function (error) {
                });
            };
        }
    ]);
}
        )(angular);