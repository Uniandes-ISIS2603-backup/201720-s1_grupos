(function(ng){
    var mod = ng.module("lugarModule");
    mod.constant("lugaresContext","Stark/lugares");
    mod.controller('lugarCreateCtrl',['$scope','$http','lugaresContext','$state','$rootScope',
       function($scope, $http, lugaresContext, $state, $rootScope){
        $rootScope.edit = false;
        $scope.createLugar = function(){
            $http.post(lugaresContext, {
                nombre :$scope.lugarNombre,
                direccion :$scope.lugarDireccion,             
            }).then(function (response) {
                $state.go('lugaresList',{lugarId: response.data.id},{reload:true});
            });
        };
    }
    ]);
}
)(angular);


