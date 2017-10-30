(function (ng) {
    var mod = ng.module("usuarioModule");
    mod.constant("usuarioContext", "Stark/usuarios");
   
    mod.controller('usuarioCtrl', ['$scope', '$http', 'usuarioContext', '$state',
        function ($scope, $http, usuarioContext, $state) {
            $http.get(usuarioContext).then(function (response) {
                $scope.usuariosRecords = response.data;
            });
            if ($state.params.usuarioId !== undefined) {
                $http.get(usuarioContext + '/' + $state.params.usuarioId).then(function (response) {
                    $scope.usuarioActual = response.data;
                });
            }
        }
    ]);

}
)(angular);