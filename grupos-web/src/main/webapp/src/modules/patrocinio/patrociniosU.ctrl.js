(function (ng) {
    //variable con el modulo actual
    var mod = ng.module("patrocinioModule");
    //se define la constante que une patrocinios con el usuario actual
    mod.constant("patrociniosUsuarioContext", "patrocinios");
    //se define la constante que determina la ruta de usuarios
    mod.constant("usuariosContext", "Stark/usuarios");
    mod.controller('patrociniosUCtrl', ['$scope', '$http', 'usuariosContext', '$state', 'patrociniosUsuarioContext',
        function ($scope, $http, usuariosContext, $state, patrociniosUsuarioContext) {
           //Se buscan todos los patrocinios del usuario actual
            $http.get(usuariosContext + '/' + $state.params.usuarioId + '/' + patrociniosUsuarioContext).then(function (response) {
                $scope.patrociniosRecords = response.data;
            });
            //Se busca el patrocinio actual
            if ($state.params.patrocinioId !== undefined) {
                $http.get(usuariosContext + '/' + $state.params.usuarioId + '/' + patrociniosUsuarioContext + '/' + $state.params.patrocinioId).then(function (response) {
                    $scope.currentPatrocinio = response.data;
                });
            }
        }
    ]);
}
)(angular); 