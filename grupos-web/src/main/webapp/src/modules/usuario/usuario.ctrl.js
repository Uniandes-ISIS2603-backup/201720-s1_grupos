(function (ng) {
    //variable con el modulo actual
    var mod = ng.module("usuarioModule");
    //se determina el contexto del usuario
    mod.constant("usuarioContext", "Stark/usuarios");
    mod.controller('usuarioCtrl', ['$scope', '$http', 'usuarioContext', '$state',
        function ($scope, $http, usuarioContext, $state) {
            $scope.deGrupo=false;
            //Busca todos los usuarios que tiene la aplicación
            $http.get(usuarioContext).then(function (response) {
                $scope.usuariosRecords = response.data;
            });
            //Se busca el usuario con id: $state.params.usuarioId
            if ($state.params.usuarioId !== undefined) {
                $http.get(usuarioContext + '/' + $state.params.usuarioId).then(function (response) {
                    $scope.usuarioActual = response.data;
                });
            }
        }
    ]);

}
)(angular);