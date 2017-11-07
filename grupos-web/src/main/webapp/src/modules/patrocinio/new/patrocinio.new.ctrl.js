(function (ng) {
    var mod = ng.module("patrocinioModule");
    mod.constant("patrociniosContext", "patrocinios");
    mod.constant("usuariosContext", "Stark/usuarios");
    mod.controller('patrocinioNewCtrl', ['$scope', '$http', 'usuariosContext', '$state', 'patrociniosContext', '$rootScope',
        function ($scope, $http, usuariosContext, $state, patrociniosContext, $rootScope) {
            $rootScope.edit = false;
            $scope.patrocinioCreacion=true;
            $scope.patrocinoActualizar=false;
            $scope.createPatrocinio = function () {
                console.log("==============")
                console.log(usuariosContext + '/' + $state.params.usuarioId + '/' + patrociniosContext);
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