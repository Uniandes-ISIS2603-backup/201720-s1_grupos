(function (ng) {
    var mod = ng.module("tarjetaModule");
    mod.constant("tarjetasContext", "tarjetas");
    mod.constant("usuariosContext", "Stark/usuarios");
    mod.controller('tarjetaNewCtrl', ['$scope', '$http', 'usuariosContext', '$state', 'tarjetasContext', '$rootScope',
        function ($scope, $http, usuariosContext, $state, tarjetasContext, $rootScope) {
            $rootScope.edit = false;
            $scope.createTarjeta = function () {
                console.log(usuariosContext + '/' + $state.params.usuarioId + '/' + tarjetasContext);
                $http.post(usuariosContext + '/' + $state.params.usuarioId + '/' + tarjetasContext, {
                    numero: $scope.tarjetaNumero,
                    banco: $scope.tarjetaBanco,
                    dineroDisponible: $scope.tarjetaDineroDisponible,
                    maxCupo: $scope.tarjetaMaxCupo
                }).then(function (response) {
                    //Tarjeta creada exitosamente
                    $state.go('tarjetasList', {numTarjeta: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(angular);