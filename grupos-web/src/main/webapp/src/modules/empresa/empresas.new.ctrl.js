(function (ng) {
    var mod = ng.module("empresaModule");
    mod.constant("authorsContext", "api/authors");
    mod.constant("empresaUsuarioContext", "Stark/usuarios/2/empresa");
    mod.constant("usuariosContext", "Stark/usuarios");
    mod.controller('empresaNewCtrl', ['$scope', '$http', 'empresaUsuarioContext', '$state', 'usuariosContext', '$rootScope',
        function ($scope, $http, empresaUsuarioContext, $state, usuariosContext, $rootScope) {
            $rootScope.edit = false;
            $scope.createEmpresa = function () {
                $http.post(empresaUsuarioContext, {
                    nit: $scope.empresaNit,
                    nombre: $scope.empresaNombre,
                    logo: $scope.empresaLogo
                }).then(function (response) {
                    //Empresa creada exitosamente
                    $state.go('empresaDetail', {reload: true});
                });
            };
        }
    ]);
}
)(angular);


