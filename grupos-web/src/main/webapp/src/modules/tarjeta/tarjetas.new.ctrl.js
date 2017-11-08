(function (ng) {
    //Módulo actual
    var mod = ng.module("tarjetaModule");
    //Contextos utilizados en el controlador
    mod.constant("tarjetasContext", "tarjetas");
    mod.constant("usuariosContext", "Stark/usuarios");
    mod.controller('tarjetaNewCtrl', ['$scope', '$http', 'usuariosContext', '$state', 'tarjetasContext', '$rootScope',
        function ($scope, $http, usuariosContext, $state, tarjetasContext, $rootScope) {
            $rootScope.edit = false;
            //Función utilizada para crear una nueva tarjeta.
            $scope.createTarjeta = function () {
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