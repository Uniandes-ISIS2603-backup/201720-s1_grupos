(function (ng) {
    //Módulo actual
var mod = ng.module("empresaModule");
    //Contextos utilizados en el controlador
    mod.constant("empresasContext", "Stark/empresas");
    mod.constant("empresaUsuarioContext", "Stark/usuarios/2/empresa");
    mod.constant("usuarioContext", "Stark/usuarios");
    mod.controller('empresaListCtrl', ['$scope', '$http', 'empresasContext', 'empresaUsuarioContext','$state',
        function ($scope, $http, empresasContext) {
            //Buscar todas las empresas del sistema
            $http.get(empresasContext).then(function (response) {
                $scope.empresasRecords = response.data;
            });
           
        }
    ]);
}
)(angular);

