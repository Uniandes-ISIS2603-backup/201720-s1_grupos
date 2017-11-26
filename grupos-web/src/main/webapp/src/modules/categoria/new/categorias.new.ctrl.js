/**
 * Controlador para crear una categoría
 */
(function (ng) {
    var mod = ng.module("categoriaModule");
    mod.constant("categoriasContext", "Stark/categorias");
    mod.controller('categoriaNewCtrl', ['$scope', '$http', 'categoriasContext', '$state', '$rootScope',
        function ($scope, $http, categoriasContext, $state, $rootScope) {
            //Búsqueda de las imágenes en la carpeta data
            $http.get("./data/archivos.json").then(function(response)
            {
                $scope.rutaImagenes = response.data;
            });
            //Se indica que se está creando una categoría
            $scope.errorCategoriaNombre=false;
            $rootScope.edit = false;
            $scope.crearcategoria=true;
            $scope.actualizarcategoria=false;
            /**
             * Crea la categoría
             */
            $scope.createCategoria = function () {
                //Invoca el servicio POST correspondiente
                $http.post(categoriasContext, {
                    tipo: $scope.categoriaName,
                    descripcion: $scope.categoriaDescription,
                    rutaIcono: $scope.categoriaRuta
                }).then(function (response) {
                    //Se creó correctamente
                    console.log("HOLAAA");
                    $state.go('carruselCategorias',{}, {reload: true});
                }, function (error, status) {
                    
                    $scope.errorCategoriaNombre=true;
                });
            };
            
        }
    ]);
}
        )(angular);