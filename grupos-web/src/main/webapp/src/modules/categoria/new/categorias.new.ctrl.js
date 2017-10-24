(function (ng) {
    var mod = ng.module("categoriaModule");
    mod.constant("categoriasContext", "Stark/categorias");
    mod.controller('categoriaNewCtrl', ['$scope', '$http', 'categoriasContext', '$state', '$rootScope',
        function ($scope, $http, categoriasContext, $state, $rootScope) {
            $rootScope.edit = false;
            $scope.crearcategoria=true;
            $scope.actualizarcategoria=false;
            $scope.createCategoria = function () {
                $http.post(categoriasContext, {
                    tipo: $scope.categoriaName,
                    descripcion: $scope.categoriaDescription,
                    rutaIcono: $scope.categoriaRuta
                }).then(function (response) {
                    //categoria created successfully
                    console.log("la data:" + response.data.id);
                    $state.go('listaCategorias',{}, {reload: true});
                }, function (error) {
                    console.log("llega Status: "+ error.status);
                    console.log(error.data)
                    $scope.errorcategorias=error.data;
                    $("#modalErrorcategorias").modal('show');
                });
            };
            
        }
    ]);
}
        )(angular);