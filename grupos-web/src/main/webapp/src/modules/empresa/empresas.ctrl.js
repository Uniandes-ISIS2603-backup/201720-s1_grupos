(function (ng) {
var mod = ng.module("empresaModule");
    mod.constant("empresaUsuarioContext", "empresa");
    mod.constant("usuariosContext", "Stark/usuarios");
    mod.controller('empresaCtrl', ['$scope', '$http', 'usuariosContext', '$state', 'empresaUsuarioContext', '$rootScope',
        function ($scope, $http, usuariosContext, $state, empresaUsuarioContext, $rootScope) {
            $http.get(usuariosContext + '/' + $state.params.usuarioId + '/' + empresaUsuarioContext).then(function (response) {
                $scope.currentEmpresa = response.data;
                $state.params.nitEmpresa = $scope.currentEmpresa.nit;
            },function(response){
                $rootScope.alerts.pop();
                $state.go('empresaNotFound', {reload: true});
            });
           
        }
    ]);
}
)(angular);