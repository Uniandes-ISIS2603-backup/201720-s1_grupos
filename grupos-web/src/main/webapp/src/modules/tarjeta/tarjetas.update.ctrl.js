(function (ng) {
    //Módulo Actual
    var mod = ng.module("tarjetaModule");
    //Contextos que se utilizan en el controlador
    mod.constant("tarjetasContext", "tarjetas");
    mod.constant("usuariosContext", "Stark/usuarios");
    mod.controller('tarjetaUpdateCtrl', ['$scope', '$http', 'usuariosContext', '$state', 'tarjetasContext', '$rootScope',
        function ($scope, $http, usuariosContext, $state, tarjetasContext, $rootScope) {
            $rootScope.edit = false;
            
            var numTarjeta = $state.params.numTarjeta;
            //Se busca la tarjeta que se va a modificar
            if (numTarjeta !== undefined) {
                $http.get(usuariosContext + '/' + $state.params.usuarioId + '/' + tarjetasContext + '/' + $state.params.numTarjeta).then(function (response) {
                    $scope.currentTarjeta = response.data;
                });
            }
            //Función utilizada para modificar una tarjeta
            $scope.updateTarjeta = function () {
                //Poner bien path REST!
                $http.put(usuariosContext + '/' + $state.params.usuarioId + '/' + tarjetasContext + '/' + $state.params.numTarjeta, {
                    numero: numTarjeta,
                    banco: $scope.tarjetaBanco,
                    dineroDisponible: $scope.tarjetaDineroDisponible,
                    maxCupo: $scope.tarjetaMaxCupo
                }).then(function (response) {
                    //Tarjeta modificada exitosamente
                    $state.go('tarjetasList', {numTarjeta: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(angular);


