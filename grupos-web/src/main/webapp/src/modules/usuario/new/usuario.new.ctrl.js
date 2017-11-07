(function (ng) {
    //Se define el modulo
    var mod = ng.module("usuarioModule");
    //Constante con el contexto del usuario
    mod.constant("usuarioContext", "Stark/usuarios");
    mod.controller('usuarioNewCtrl', ['$scope', '$http', 'usuarioContext', '$state', '$rootScope',
        function ($scope, $http, usuarioContext, $state, $rootScope) {
            $rootScope.edit = false;
            
            //Se determina que se va a crear un usuario
            $scope.usuarioCreacion=true;
            $scope.usuarioActualizar=false;
            
            //Función que crea el usuario
            $scope.createUsuario = function () {
                $http.post(usuarioContext, {
                    nombre: $scope.usuarioNombre,
                    apellido: $scope.usuarioApellido,
                    nickname: $scope.usuarioNickname,
                    password: $scope.usuarioPassword,
                    email: $scope.usuarioEmail,
                }).then(function (response) {
                    //Usuario created successfully
                    $state.go('usuariosList', {usuarioId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(angular);