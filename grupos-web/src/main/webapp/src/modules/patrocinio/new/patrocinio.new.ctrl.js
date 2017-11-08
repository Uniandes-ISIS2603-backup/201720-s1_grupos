(function (ng) {
    //modulo de patrocinio
    var mod = ng.module("patrocinioModule");
    //contexto de patrocinio
    mod.constant("patrociniosContext", "patrocinios");
    //contexto de usuario
    mod.constant("usuariosContext", "Stark/usuarios");
    mod.controller('patrocinioNewCtrl', ['$scope', '$http', 'usuariosContext', '$state', 'patrociniosContext', '$rootScope',
        function ($scope, $http, usuariosContext, $state, patrociniosContext, $rootScope) {
            $rootScope.edit = false;
            //función que permite crear el patrocinio
            $scope.createPatrocinio = function () {
                $http.post(usuariosContext + '/' + $state.params.usuarioId + '/' + patrociniosContext, {
                    pago: $scope.patrocinioPago,
                }).then(function (response) {
                    //Patrocinio modificado exitosamente
                    $state.go('patrocinioListDetail', {patrocinioId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(angular);