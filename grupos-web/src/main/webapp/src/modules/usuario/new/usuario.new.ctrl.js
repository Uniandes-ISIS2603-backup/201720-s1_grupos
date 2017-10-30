(function (ng) {
    var mod = ng.module("usuarioModule");
    mod.constant("usuarioContext", "Stark/usuarios");
    mod.controller('usuarioNewCtrl', ['$scope', '$http', 'usuarioContext', '$state', '$rootScope',
        function ($scope, $http, usuarioContext, $state, $rootScope) {
            $rootScope.edit = false;
            $scope.usuarioCreacion=true;
            $scope.usuarioActualizar=false;
            $scope.createUsuario = function () {
                $http.post(usuarioContext, {
                    nombre: $scope.usuarioNombre,
                    apellido: $scope.usuarioApellido,
                    nickname: $scope.usuarioNickname,
                    contrasena: $scope.usuarioContrasena,
                    email: $scope.usuarioEmail,
                }).then(function (response) {
                    //Author created successfully
                    $state.go('usuariosList', {usuarioId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(angular);