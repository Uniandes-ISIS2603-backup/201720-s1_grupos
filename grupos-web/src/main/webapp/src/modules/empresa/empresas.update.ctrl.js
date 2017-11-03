(function (ng) {
    var mod = ng.module("empresaModule");
    mod.constant("authorsContext", "api/authors");
    mod.constant("empresaUsuarioContext", "Stark/usuarios/2/empresa");
    mod.constant("usuariosContext", "Stark/usuarios");
    mod.controller('empresaUpdateCtrl', ['$scope', '$http', 'empresaUsuarioContext', '$state', 'usuariosContext', '$rootScope',
        function ($scope, $http, empresaUsuarioContext, $state, $rootScope) {
            $rootScope.edit = false;
            
            
            
                var nitActual;
                $http.get("Stark/usuarios/2/empresa").then(function (response) {
                    var empresaActual = response.data;
                    nitActual = empresaActual.nit;
                    $scope.currentNit = empresaActual.nit;
                });
            
           
            $scope.updateEmpresa = function () {
                //Poner bien path REST!
                $http.put("Stark/usuarios/2/empresa", {
                    nit: nitActual,
                    nombre: $scope.empresaNombre,
                    logo: $scope.empresaLogo
                }).then(function (response) {
                    //Empresa modificada exitosamente
                    $state.go('empresaDetail', {nitEmpresa: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(angular);


