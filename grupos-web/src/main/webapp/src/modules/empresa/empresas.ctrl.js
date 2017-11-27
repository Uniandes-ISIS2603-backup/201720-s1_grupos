(function (ng) {
    //Módulo actual
var mod = ng.module("empresaModule");
    //Contextos que se utilizan dentro del controlador
    mod.constant("empresaUsuarioContext", "empresa");
    mod.constant("usuariosContext", "Stark/usuarios");
    mod.controller('empresaCtrl', ['$scope', '$http', 'usuariosContext', '$state', 'empresaUsuarioContext', '$rootScope',
        function ($scope, $http, usuariosContext, $state, empresaUsuarioContext, $rootScope) {
            //Buscar la empresa de un usurio
            $http.get(usuariosContext + '/' + $state.params.usuarioId + '/' + empresaUsuarioContext).then(function (response) {
                $scope.currentEmpresa = response.data;
                $state.params.nitEmpresa = $scope.currentEmpresa.nit;
            },function(){
                //Si no se encuentra la empresa
                $rootScope.alerts.pop();
                $state.go('empresaNotFound', {reload: true});
            });
           
        }
    ]);
}
)(angular);