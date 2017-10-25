(function (ng) {
    var mod = ng.module("tarjetaModule");
    mod.constant("authorsContext", "api/authors");
    mod.constant("tarjetasContext", "Stark/usuarios/3/tarjetas");
    mod.constant("usuariosContext", "Stark/usuarios");
    mod.controller('tarjetaNewCtrl', ['$scope', '$http', 'tarjetasContext', '$state', 'usuariosContext', '$rootScope',
        function ($scope, $http, tarjetasContext, $state, usuariosContext, $rootScope) {
            $rootScope.edit = false;
            $scope.createTarjeta = function () {
                $http.post(tarjetasContext, {
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