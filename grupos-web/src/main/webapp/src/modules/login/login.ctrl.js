(function (ng) {
    var mod = ng.module("loginModule");
    mod.controller('loginCtrl', ['$scope', '$http', '$state', '$rootScope',
        function ($scope, $http, $state, $rootScope) {

            $scope.user = {};
            $scope.data = {};
            
            
      

            this.autenticar = function () {
                $http.get('Stark/usuarios/login', {
                            headers: {'email': $scope.data.email,'password':$scope.data.password}
                            }).then(function(response){
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
                                console.log(sessionStorage.getItem("email")+"---"+sessionStorage.getItem("password"));
                                $state.go('usuarioDetail', {usuarioId:$scope.user.id}, {reload: true});
                            },function(response)
                            {
                                console.log("Hubo un error de login");
                            });
                        
                    
                };
               
            
        }
    ]);
}
)(window.angular);

