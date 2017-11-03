(function (ng) {
var mod = ng.module("empresaModule");
    mod.constant("empresasContext", "Stark/empresas");
    mod.constant("empresaUsuarioContext", "Stark/usuarios/2/empresa");
    mod.constant("usuarioContext", "Stark/usuarios");
    mod.controller('empresaListCtrl', ['$scope', '$http', 'empresasContext', 'empresaUsuarioContext','$state',
        function ($scope, $http, empresasContext, empresaUsuarioContext,$state) {
            
            $http.get(empresasContext).then(function (response) {
                $scope.empresasRecords = response.data;
            });
           
        }
    ]);
}
)(angular);

