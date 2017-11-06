(function (ng) {
    var mod = ng.module("patrocinioModule");
    mod.constant("patrociniosContext", "patrocinios");
    mod.constant("usuariosContext", "Stark/usuarios");
    mod.controller('patrocinioUpdateCtrl', ['$scope', '$http', 'usuariosContext', '$state', 'patrociniosContext', '$rootScope',
        function ($scope, $http, usuariosContext, $state, patrociniosContext, $rootScope) {
            $rootScope.edit = true;
            
            var patrocinioId = $state.params.patrocinioId;
            
            $scope.patrocinioCreacion=false;
            $scope.patrocinioActualizar=true;
            //if (patrocinioId !== undefined) {
            //    $http.get(usuariosContext + '/' + $state.params.usuarioId + '/' + patrociniosContext + '/' + $state.params.patrocinioId).then(function (response) {
            //        $scope.currentPatrocinio = response.data;
            //    });
            //}
            
            $scope.createPatrocinio = function () {
                //Poner bien path REST!
                console.log(usuariosContext + '/' + $state.params.usuarioId + '/' + patrociniosContext + '/' + $state.params.patrocinioId)
                $http.put(usuariosContext + '/' + $state.params.usuarioId + '/' + patrociniosContext + '/' + $state.params.patrocinioId, {
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


