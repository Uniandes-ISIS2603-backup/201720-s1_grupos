(function (ng) {
var mod = ng.module("tarjetaModule");
    mod.constant("tarjetasContext", "Stark/usuarios/3/tarjetas");
    mod.constant("usuariosContext", "Stark/usuarios");
    mod.controller('tarjetaCtrl', ['$scope', '$http', 'tarjetasContext', '$state',
        function ($scope, $http, tarjetasContext, $state) {
            $http.get(tarjetasContext).then(function (response) {
                $scope.tarjetasRecords = response.data;
            });
            
            if ($state.params.numTarjeta !== undefined) {
                $http.get(tarjetasContext + '/' + $state.params.numTarjeta).then(function (response) {
                    $scope.currentTarjeta = response.data;
                });
            }
        }
    ]);
}
)(angular);
    