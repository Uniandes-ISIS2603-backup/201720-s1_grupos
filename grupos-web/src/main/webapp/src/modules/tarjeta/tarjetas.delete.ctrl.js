(function (ng) {
    var mod = ng.module("tarjetaModule");
    mod.constant("authorsContext", "api/authors");
    mod.constant("tarjetasContext", "Stark/usuarios/3/tarjetas");
    mod.constant("usuariosContext", "Stark/usuarios");
    mod.controller('tarjetaDeleteCtrl', ['$scope', '$http', 'tarjetasContext', '$state',
        function ($scope, $http, tarjetasContext, $state) {
            var numTarjeta = $state.params.numTarjeta;
            $scope.deleteTarjeta = function () {
                $http.delete(tarjetasContext + '/' + numTarjeta, {}).then(function (response) {
                    $state.go('tarjetasList', {numTarjeta: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(angular);