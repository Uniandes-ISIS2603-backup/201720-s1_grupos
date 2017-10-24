(function(ng){
    var mod = ng.module("eventoModule");
    mod.constant("eventosContext","Stark/eventos");
    mod.controller('eventoCreateCtrl',['$scope','$http','eventosContext','$state','$rootScope',
       function($scope, $http, eventosContext, $state, $rootScope){
        $rootScope.edit = false;
        $scope.createEvento = function(){
            $http.post(eventosContext, {
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


