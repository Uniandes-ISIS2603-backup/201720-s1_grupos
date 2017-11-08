(function (ng) {
    //Módulo actual 
    var mod = ng.module("empresaModule");
    //Contextos utilizados dentro del controlador
    mod.constant("empresaUsuarioContext", "empresa");
    mod.constant("usuariosContext", "Stark/usuarios");
    mod.controller('empresaUpdateCtrl', ['$scope', '$http', 'usuariosContext', '$state', 'empresaUsuarioContext', '$rootScope',
        function ($scope, $http, usuariosContext, $state, empresaUsuarioContext, $rootScope) {
            $rootScope.edit = false;
            //Búsqueda de la empresa que se va a modificar.
                var nitActual;
                $http.get(usuariosContext + '/' + $state.params.usuarioId + '/' + empresaUsuarioContext).then(function (response) {
                    var empresaActual = response.data;
                    nitActual = empresaActual.nit;
                    $scope.currentNit = empresaActual.nit;
                });
            
            //Función utilizada para modificar una empresa.
            $scope.updateEmpresa = function () {
                //Poner bien path REST!
                $http.put(usuariosContext + '/' + $state.params.usuarioId + '/' + empresaUsuarioContext, {
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


