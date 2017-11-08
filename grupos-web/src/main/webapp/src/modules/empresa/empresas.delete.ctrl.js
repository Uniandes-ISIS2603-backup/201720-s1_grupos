(function (ng) {
    //Módulo actual
    var mod = ng.module("empresaModule");
    //Contextos utilizados en el controlador
    mod.constant("empresaUsuarioContext", "empresa");
    mod.constant("usuariosContext", "Stark/usuarios");
    mod.controller('empresaDeleteCtrl', ['$scope', '$http', 'usuariosContext', '$state', 'empresaUsuarioContext',
        function ($scope, $http, usuariosContext, $state, empresaUsuarioContext) {
            //Función utilizada para borrar una empresa
            $scope.deleteEmpresa = function () {
                $http.delete(usuariosContext + '/' + $state.params.usuarioId + '/' + empresaUsuarioContext, {}).then(function (response) {
                    $state.go('empresaNotFound', {reload: true});
                });
            };
        }
    ]);
}
)(angular);
