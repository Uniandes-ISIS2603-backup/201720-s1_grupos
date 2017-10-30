(function (ng) {
    var mod = ng.module("usuarioModule");
    mod.constant("usuarioContext", "Stark/usuarios");
    mod.controller('usuarioUpdateCtrl', ['$scope', '$http', 'usuarioContext', '$state', '$rootScope',
        function ($scope, $http, usuarioContext, $state, $rootScope) {
            $rootScope.edit = true;
            var idu= $state.params.usuarioId;

            $scope.usuarioCreacion=false;
            $scope.usuarioActualizar=true;
            //Consulto el usuario a editar.
            //console.log(idu + '*********************************')
            $http.get(usuarioContext + '/' + idu).then(function (response) {
                var usuarioActual = response.data;
                $scope.usuarioNombre = usuarioActual.nombre;
                $scope.usuarioApellido = usuarioActual.apellido;
                $scope.usuarioNickname = usuarioActual.nickname;
                $scope.usuarioContrasena = usuarioActual.contrasena;
                $scope.usuarioEmail = usuarioActual.email;
            });

            $scope.createUsuario = function () {
                $http.put(usuarioContext + '/' + idu, {
                    nombre: $scope.usuarioNombre,
                    apellido: $scope.usuarioApellido,
                    nickname: $scope.usuarioNickname,
                    contrasena: $scope.usuarioContrasena,
                    email: $scope.usuarioEmail
                }).then(function (response) {
                    //Author created successfully
                    $state.go('usuariosList', {usuarioId: response.data.id}, {reload: true});
                });
            };
            
            
        }
    ]);
})
(angular);