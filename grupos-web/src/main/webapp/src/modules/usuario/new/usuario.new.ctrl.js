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
                if($scope.usuarioPassword === $scope.usuarioPassword2)
                {
                    $http.post(usuarioContext, {
                        nombre: $scope.usuarioNombre,
                        apellido: $scope.usuarioApellido,
                        nickname: $scope.usuarioNickname,
                        password: $scope.usuarioPassword,
                        email: $scope.usuarioEmail
                    }).then(function (response) {
                        //Usuario created successfully
                        $scope.user=response.data;
                        sessionStorage.token = $scope.user.token;
                        sessionStorage.setItem("email", $scope.user.email);
                        sessionStorage.setItem("password", $scope.user.password);
                        sessionStorage.setItem("rol", $scope.user.rol);
                        if($scope.user.nickname!==null && $scope.user.nickname!==undefined)
                        {
                            sessionStorage.setItem("nickname",$scope.user.nickname);
                        }
                        sessionStorage.setItem("nombreCompleto",$scope.user.nombre+" "+$scope.user.apellido);
                        sessionStorage.setItem("id",$scope.user.id);
                        if($scope.user.nickname=== null || $scope.user.nickname===undefined)
                        {
                            $rootScope.currentUser = $scope.user.nombre+" "+$scope.user.apellido;
                        }
                        else
                        {
                            $rootScope.currentUser = $scope.user.nickname; 
                            
                        }
                        $state.go('usuarioDetail', {usuarioId:$scope.user.id}, {reload: true});
                        
                    });
                }
                else
                {
                    $("#modalErrorPassword").modal('show');
                }
            };
        }
    ]);
}
        )(angular);