(function (ng) {
    //modulo de patrocinio
    var mod = ng.module("patrocinioModule");
    //contexto de patrocinio
    mod.constant("patrociniosContext", "patrocinios");
    //contexto de usuario
    mod.constant("eventosContext", "Stark/eventos");
    mod.constant("usuariosContext","Stark/usuarios");
    mod.controller('patrocinioEventoNewCtrl', ['$scope', '$http', 'eventosContext', '$state', 'patrociniosContext', 'usuariosContext', '$rootScope',
        function ($scope, $http, eventosContext, $state, patrociniosContext, usuariosContext, $rootScope) {
            $rootScope.edit = false;
            //variables que permiten determinar si se esta modificando o creando
            $scope.patrocinioEventoCreacion=true;
            $scope.patrocinoEventoActualizar=false;

            $scope.createPatrocinio = function(){
            $http.post(usuariosContext + sessionStorage.getItem("id") + patrociniosContext, {
                pago :$scope.patrocinioPago,              
            }).then(function (response) {
                fullContext = "Stark/"+eventosContext + "/" +$state.params.eventoId + "/" + patrociniosContext + "/" + response.data.id;
                $http.post(fullContext).then(function(response){
                    $state.go('patrocinioEventoListDetail',{patrocinioId: response.data.id},{reload:true});
            });
        });
        };
    }
    ]);
}
)(angular);