(function (ng) {
    var mod = ng.module("usuarioModule");
    mod.constant("usuarioContext", "Stark/usuarios");
    mod.controller('usuarioUpdateCtrl', ['$scope', '$http', 'usuarioContext', '$state', '$rootScope',
        function ($scope, $http, usuarioContext, $state, $rootScope) {
            $rootScope.edit = false;
            var idu= $state.params
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