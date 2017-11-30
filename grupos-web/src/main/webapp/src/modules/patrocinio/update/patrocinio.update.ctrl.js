(function (ng) {
    //modulo de patrocinio
    var mod = ng.module("patrocinioModule");
    //se define el contexto de patrocinio
    mod.constant("patrociniosContext", "patrocinios");
    //se define el contexto de usuario
    mod.constant("usuariosContext", "Stark/usuarios");
    mod.controller('patrocinioUpdateCtrl', ['$scope', '$http', 'usuariosContext', '$state', 'patrociniosContext', '$rootScope',
        function ($scope, $http, usuariosContext, $state, patrociniosContext, $rootScope) {
            $rootScope.edit = true;
            //se determinan variables en el scope para determinar si se esta creando o actualizando
            $scope.patrocinioCreacion=false;
            $scope.patrocinioActualizar=true;
            //función que permite actualizar un patrocinio
            $scope.createPatrocinio = function () {
                $http.put(usuariosContext + '/' + sessionStorage.getItem("id") + '/' + patrociniosContext + '/' + $state.params.patrocinioId, {
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


