(function(ng){
    var mod = ng.module("eventoModule");
    mod.constant("eventoContext","Stark/eventos");
    mod.constant("relacionContext","Stark/grupos");
    mod.controller('eventoCreateCtrl',['$scope','$http','eventoContext','eventosContext','relacionContext','$state','$rootScope',
       function($scope, $http, eventoContext,eventosContext,relacionContext, $state, $rootScope){
        $rootScope.edit = false;
        $scope.createEvento = function(){
            $http.post(eventoContext, {
                nombre :$scope.eventoNombre,
                fechaFin :$scope.eventoFechaFin,
                fechaInicio :$scope.eventoFechaInicio              
            }).then(function (response) {
                console.log(response);
            fullContext = relacionContext + "/10/" + eventosContext + "/" + response.data.id;
            $http.post(fullContext).then(function(response){
                $state.go('eventosList',{eventoId: response.data.id},{reload:true});
            });
        });
        };
    }
    ]);
}
)(angular);


