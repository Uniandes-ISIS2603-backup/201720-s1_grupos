(function (ng) {
var mod = ng.module("tarjetaModule");
    mod.constant("tarjetasContext", "tarjetas");
    mod.constant("usuariosContext", "Stark/usuarios");
    mod.controller('tarjetaCtrl', ['$scope', '$http', 'usuariosContext', '$state', 'tarjetasContext',
        function ($scope, $http, usuariosContext, $state, tarjetasContext) {
            $http.get(usuariosContext + '/' + $state.params.usuarioId + '/' + tarjetasContext).then(function (response) {
                $scope.tarjetasRecords = response.data;
            });
            
            if ($state.params.numTarjeta !== undefined) {
                $http.get(usuariosContext + '/' + $state.params.usuarioId + '/' + tarjetasContext + '/' + $state.params.numTarjeta).then(function (response) {
                    $scope.currentTarjeta = response.data;
                });
            }
        }
    ]);
}
)(angular);
    