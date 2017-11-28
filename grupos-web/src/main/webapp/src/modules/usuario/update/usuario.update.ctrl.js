(function (ng) {
    //se define el modulo actual
    var mod = ng.module("usuarioModule");
    //se define la constante para el usuario
    mod.constant("usuarioContext", "Stark/usuarios");
    mod.controller('usuarioUpdateCtrl', ['$scope', '$http', 'usuarioContext', '$state', '$rootScope',
        function ($scope, $http, usuarioContext, $state, $rootScope) {
            $rootScope.edit = true;
            //variable que almacena el id del usuario
            var idu= $state.params.usuarioId;
            //se determina que se va a actualizar el usuario
            $scope.usuarioCreacion=false;
            $scope.usuarioActualizar=true;
            //Consulto el usuario a editar.
            $http.get(usuarioContext + '/' + idu).then(function (response) {
                var usuarioActual = response.data;
                $scope.usuarioNombre = usuarioActual.nombre;
                $scope.usuarioApellido = usuarioActual.apellido;
                $scope.usuarioNickname = usuarioActual.nickname;
                $scope.usuarioPassword = usuarioActual.password;
                $scope.usuarioEmail = usuarioActual.email;
            });
            //Función que determina la actualización del usuario
            $scope.createUsuario = function () {
                $http.put(usuarioContext + '/' + idu, {
                    nombre: $scope.usuarioNombre,
                    apellido: $scope.usuarioApellido,
                    nickname: $scope.usuarioNickname,
                    password: $scope.usuarioPassword,
                    email: $scope.usuarioEmail
                }).then(function (response) {
                    //Usuario created successfully
                    $state.go('usuariosList', {usuarioId: response.data.id}, {reload: true});
                });
            };
            
            
        }
    ]);
}
)(angular);