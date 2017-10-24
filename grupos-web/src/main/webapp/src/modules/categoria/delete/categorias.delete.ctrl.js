(function (ng) {
    var mod = ng.module("categoriaModule");
    mod.constant("categoriasContext", "Stark/categorias");
    
    mod.controller('categoriaDeleteCtrl', ['$scope', '$http', 'categoriasContext', '$state',
        function ($scope, $http, categoriasContext, $state) {
            var idcategoria = $state.params.categoriaId;
            
            $scope.crearcategoria=true;
            $scope.actualizarcategoria=false;
            $scope.deleteCategoria = function () {
                console.log("voy a borrar");
                $http.delete(categoriasContext + '/' + idcategoria, {}).then(function (response) {
                    $state.go('listaCategorias',{}, {reload: true});
                }, function (error) {
                    $scope.errorcategorias=error.status;
                    console.log(errorcategorias);
                    $("#modalErrorcategorias").modal();
                });
            };
        }
    ]);
}
        )(angular);