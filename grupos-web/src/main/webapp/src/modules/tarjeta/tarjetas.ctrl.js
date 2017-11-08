(function (ng) {
    //Módulo Actual
var mod = ng.module("tarjetaModule");
    //Contextos utilizados en el controlador
    mod.constant("tarjetasContext", "tarjetas");
    mod.constant("usuariosContext", "Stark/usuarios");
    mod.controller('tarjetaCtrl', ['$scope', '$http', 'usuariosContext', '$state', 'tarjetasContext',
        function ($scope, $http, usuariosContext, $state, tarjetasContext) {
            //Buscar las tarjetas del usuario
            $http.get(usuariosContext + '/' + $state.params.usuarioId + '/' + tarjetasContext).then(function (response) {
                $scope.tarjetasRecords = response.data;
            });
            
            //Buscar una tarjeta específica
            if ($state.params.numTarjeta !== undefined) {
                $http.get(usuariosContext + '/' + $state.params.usuarioId + '/' + tarjetasContext + '/' + $state.params.numTarjeta).then(function (response) {
                    $scope.currentTarjeta = response.data;
                });
            }
        }
    ]);
}
)(angular);
    