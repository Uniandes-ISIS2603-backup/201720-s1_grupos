(function (ng) {
    var mod = ng.module("empresaModule");
    mod.constant("authorsContext", "api/authors");
    mod.constant("empresasContext", "Stark/empresas");
    mod.constant("empresaUsuarioContext", "Stark/usuarios/2/empresa");
    mod.controller('empresaDeleteCtrl', ['$scope', '$http', 'empresaUsuarioContext', '$state',
        function ($scope, $http, empresaUsuarioContext, $state) {
            $scope.deleteEmpresa = function () {
                $http.delete(empresaUsuarioContext, {}).then(function (response) {
                    $state.go('empresaNotFound', {reload: true});
                });
            };
        }
    ]);
}
)(angular);
