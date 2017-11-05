(function (ng) {
    var mod = ng.module("empresaModule");
    mod.constant("empresaUsuarioContext", "empresa");
    mod.constant("usuariosContext", "Stark/usuarios");
    mod.controller('empresaDeleteCtrl', ['$scope', '$http', 'usuariosContext', '$state', 'empresaUsuarioContext',
        function ($scope, $http, usuariosContext, $state, empresaUsuarioContext) {
            $scope.deleteEmpresa = function () {
                $http.delete(usuariosContext + '/' + $state.params.usuarioId + '/' + empresaUsuarioContext, {}).then(function (response) {
                    $state.go('empresaNotFound', {reload: true});
                });
            };
        }
    ]);
}
)(angular);
