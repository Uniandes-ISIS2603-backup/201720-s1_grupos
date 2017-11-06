(function (ng) {
var mod = ng.module("patrocinioModule");
    mod.constant("patrociniosUsuarioContext", "patrocinios");
    mod.constant("usuariosContext", "Stark/usuarios");
    mod.controller('patrociniosUCtrl', ['$scope', '$http', 'usuariosContext', '$state', 'patrociniosUsuarioContext', '$rootScope',
        function ($scope, $http, usuariosContext, $state, patrociniosUsuarioContext, $rootScope) {
            console.log(usuariosContext + '/' + $state.params.usuarioId + '/' + patrociniosUsuarioContext);
            console.log("**************");
            $http.get(usuariosContext + '/' + $state.params.usuarioId + '/' + patrociniosUsuarioContext).then(function (response) {
                $scope.patrociniosRecords = response.data;
            });

            if ($state.params.patrocinioId !== undefined) {
                $http.get(usuariosContext + '/' + $state.params.usuarioId + '/' + patrociniosUsuarioContext + '/' + $state.params.patrocinioId).then(function (response) {
                    $scope.currentPatrocinio = response.data;
                });
            }
        }
    ]);
}
)(angular); 