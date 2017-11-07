(function(ng){
    var mod = ng.module("eventoModule");
    mod.constant("eventoContext","Stark/eventos");
    mod.controller('eventoCreateCtrl',['$scope','$http','eventoContext','$state','$rootScope',
       function($scope, $http, eventoContext, $state, $rootScope){
        $rootScope.edit = false;
        $scope.createEvento = function(){
            $http.post(eventoContext, {
                nombre :$scope.eventoNombre,
                fechaFin :$scope.eventoFechaFin,
                fechaInicio :$scope.eventoFechaInicio              
            }).then(function (response) {
                $state.go('eventosList',{eventoId: response.data.id},{reload:true});
            });
        };
    }
    ]);
}
)(angular);


