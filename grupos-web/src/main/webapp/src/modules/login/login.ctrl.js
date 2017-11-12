(function (ng) {
    var mod = ng.module("loginModule");
    mod.controller('loginCtrl', ['$scope', '$http', '$state', '$rootScope',
        function ($scope, $http, $state, $rootScope) {

            $scope.user = {};
            $scope.data = {};
            
            $http.get('Stark/usuarios').then(function (response) {
                $scope.users = response.data;
            });

            this.prueba= function()
            {
                console.log("HOLAAAAA");
            }
            this.autenticar = function () {
                var flag = false;
                for (var item in $scope.users) {
                    console.log($scope.users[item]);
                    console.log($scope.data.email+" "+$scope.data.password);
                    if ($scope.users[item].email == $scope.data.email && $scope.users[item].password == $scope.data.password /*&& $scope.users[item].rol == $scope.data.rol*/) {
                        flag = true;
                        $scope.user = $scope.users[item];
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
                        console.log("AAA"+sessionStorage.getItem("id"));
                        if($scope.user.nickname=== null || $scope.user.nickname===undefined)
                        {
                            $rootScope.currentUser = $scope.user.nombre+" "+$scope.user.apellido;
                        }
                        else
                        {
                            $rootScope.currentUser = $scope.user.nickname; 

                        }
                        $state.go('usuarioDetail', {usuarioId:$scope.user.id}, {reload: true});
                        break;
                    }
                }
               
            };
        }
    ]);
}
)(window.angular);

