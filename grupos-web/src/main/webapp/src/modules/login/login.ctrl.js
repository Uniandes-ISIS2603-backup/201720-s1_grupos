(function (ng) {
    //Módulo
    var mod = ng.module("loginModule");
    /**
     * Controlador que usa el $scope, servicios rest ($http), estados ($state) y el scope raíz ($rootScope)
     */
    mod.controller('loginCtrl', ['$scope', '$http', '$state', '$rootScope',
        function ($scope, $http, $state, $rootScope) {
            //Usuario
            $scope.user = {};
            //Datos para pasar entre transiciones
            $scope.data = {};
            /**
             * Función para autenticar
             */
            this.autenticar = function () {
                $http.get('Stark/usuarios/login', {
                            headers: {'email': $scope.data.email,'password':$scope.data.password}
                            }).then(function(response){
                                //Función de éxito guarda el usuario en $scope.user
                                $scope.user=response.data;
                                //Se pasan sus parámetros al sessionStorage
                                sessionStorage.token = $scope.user.token;
                                sessionStorage.setItem("email", $scope.user.email);
                                sessionStorage.setItem("password", $scope.user.password);
                                sessionStorage.setItem("rol", $scope.user.rol);
                                //Le asigna un nickname si lo tiene
                                if($scope.user.nickname!==null && $scope.user.nickname!==undefined)
                                {
                                    sessionStorage.setItem("nickname",$scope.user.nickname);
                                }
                                sessionStorage.setItem("nombreCompleto",$scope.user.nombre+" "+$scope.user.apellido);
                                sessionStorage.setItem("id",$scope.user.id);
                                //Asigna el nombre que va a aparecer en la parte de arriba del login
                                if($scope.user.nickname=== null || $scope.user.nickname===undefined)
                                {
                                    $rootScope.currentUser = $scope.user.nombre+" "+$scope.user.apellido;
                                }
                                else
                                {
                                    $rootScope.currentUser = $scope.user.nickname; 

                                }
                                //Va al perfil del usuario loggeado
                                $state.go('usuarioDetail', {usuarioId:$scope.user.id}, {reload: true});
                            },function(response)
                            {
                                //Hubo un error al loggearse
                                console.log("Hubo un error de login");
                            });
                        
                    
                };
                /**
                * Función para autenticar
                */
                this.loggear = function () {
                    $http.get('Stark/usuarios/1').then(function(response){
                                    //Función de éxito guarda el usuario en $scope.user
                                    $scope.user=response.data;
                                    //Se pasan sus parámetros al sessionStorage
                                    sessionStorage.token = $scope.user.token;
                                    sessionStorage.setItem("email", $scope.user.email);
                                    sessionStorage.setItem("password", $scope.user.password);
                                    sessionStorage.setItem("rol", $scope.user.rol);
                                    //Le asigna un nickname si lo tiene
                                    if($scope.user.nickname!==null && $scope.user.nickname!==undefined)
                                    {
                                        sessionStorage.setItem("nickname",$scope.user.nickname);
                                    }
                                    sessionStorage.setItem("nombreCompleto",$scope.user.nombre+" "+$scope.user.apellido);
                                    sessionStorage.setItem("id",$scope.user.id);
                                    //Asigna el nombre que va a aparecer en la parte de arriba del login
                                    if($scope.user.nickname=== null || $scope.user.nickname===undefined)
                                    {
                                        $rootScope.currentUser = $scope.user.nombre+" "+$scope.user.apellido;
                                    }
                                    else
                                    {
                                        $rootScope.currentUser = $scope.user.nickname; 

                                    }
                                    //Va al perfil del usuario loggeado
                                    $state.go('usuarioDetail', {usuarioId:$scope.user.id}, {reload: true});
                                },function(response)
                                {
                                    //Hubo un error al loggearse
                                    console.log("Hubo un error de login");
                                });


                    };
                    /**
                * Función para autenticar
                */
                this.loggear2 = function () {
                    $http.get('Stark/usuarios/11').then(function(response){
                                    //Función de éxito guarda el usuario en $scope.user
                                    $scope.user=response.data;
                                    //Se pasan sus parámetros al sessionStorage
                                    sessionStorage.token = $scope.user.token;
                                    sessionStorage.setItem("email", $scope.user.email);
                                    sessionStorage.setItem("password", $scope.user.password);
                                    sessionStorage.setItem("rol", $scope.user.rol);
                                    
                                    console.log($scope.user.email+" "+$scope.user.rol);

                                    //Le asigna un nickname si lo tiene
                                    if($scope.user.nickname!==null && $scope.user.nickname!==undefined)
                                    {
                                        sessionStorage.setItem("nickname",$scope.user.nickname);
                                    }
                                    sessionStorage.setItem("nombreCompleto",$scope.user.nombre+" "+$scope.user.apellido);
                                    sessionStorage.setItem("id",$scope.user.id);
                                    //Asigna el nombre que va a aparecer en la parte de arriba del login
                                    if($scope.user.nickname=== null || $scope.user.nickname===undefined)
                                    {
                                        $rootScope.currentUser = $scope.user.nombre+" "+$scope.user.apellido;
                                    }
                                    else
                                    {
                                        $rootScope.currentUser = $scope.user.nickname; 

                                    }
                                    //Va al perfil del usuario loggeado
                                    $state.go('usuarioDetail', {usuarioId:$scope.user.id}, {reload: true});
                                },function(response)
                                {
                                    //Hubo un error al loggearse
                                    console.log("Hubo un error de login");
                                });


                    };
               
            
        }
    ]);
}
)(window.angular);

