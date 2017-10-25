(function (ng) {
    var mod = ng.module("tarjetaModule");
    mod.constant("authorsContext", "api/authors");
    mod.constant("tarjetasContext", "Stark/usuarios/3/tarjetas");
    mod.constant("usuariosContext", "Stark/usuarios");
    mod.controller('tarjetaUpdateCtrl', ['$scope', '$http', 'tarjetasContext', '$state', 'usuariosContext', '$rootScope',
        function ($scope, $http, tarjetasContext, $state, usuariosContext, $rootScope) {
            $rootScope.edit = false;
            
            var numTarjeta = $state.params.numTarjeta;
            
            if (numTarjeta !== undefined) {
                $http.get(tarjetasContext + '/' + $state.params.numTarjeta).then(function (response) {
                    $scope.currentTarjeta = response.data;
                });
            }
            
            $scope.updateTarjeta = function () {
                //Poner bien path REST!
                $http.put(tarjetasContext + '/' + numTarjeta, {
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


