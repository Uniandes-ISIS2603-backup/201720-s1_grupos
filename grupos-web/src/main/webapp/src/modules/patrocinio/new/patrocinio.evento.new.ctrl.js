(function (ng) {
    //modulo de patrocinio
    var mod = ng.module("patrocinioModule");
    //contexto de patrocinio
    mod.constant("patrociniosContext", "patrocinios");
    //contexto de usuario
    mod.constant("eventosContext", "Stark/eventos");
    mod.constant("usuariosContext","Stark/usuarios");
    mod.constant("tarjetasContext", "tarjetas");
    mod.controller('patrocinioEventoNewCtrl', ['$scope', '$http', 'eventosContext', '$state', 'patrociniosContext', 'usuariosContext', 'tarjetasContext', '$rootScope',
        function ($scope, $http, eventosContext, $state, patrociniosContext, usuariosContext, tarjetasContext, $rootScope) {
            $rootScope.edit = false;
            //variables que permiten determinar si se esta modificando o creando
            $scope.patrocinioEventoCreacion=true;
            $scope.patrocinoEventoActualizar=false;
            $scope.errorNoTarjeta = false;
            $scope.errorNoDinero = false;

            $scope.createPatrocinio = function(){
            var numTarjeta = $scope.numTarjeta;
            
            
            
            if (numTarjeta !== undefined) {
                $http.get(usuariosContext + '/' + sessionStorage.getItem("id") + '/' + tarjetasContext + '/' + numTarjeta).then(function (response) {
                    var tarjetaUsada = response.data;
                    var nuevoDinero = tarjetaUsada.dineroDisponible - $scope.patrocinioPago;
                    $http.put(usuariosContext + '/' + sessionStorage.getItem("id") + '/' + tarjetasContext + '/' + numTarjeta, {
                    numero: numTarjeta,
                    banco: tarjetaUsada.banco,
                    dineroDisponible: nuevoDinero,
                    maxCupo: tarjetaUsada.maxCupo
                    }).then(function (response) {
                        //Se realiza descuento a la plata de la tarjeta
                        console.log("Se edito la tarjeta crack");
                        $http.post(usuariosContext + '/' + sessionStorage.getItem("id") + '/' + patrociniosContext, {
                            pago :$scope.patrocinioPago,              
                        }).then(function (response) {
                            fullContext = "Stark/"+eventosContext + "/" +$state.params.eventoId + "/" + patrociniosContext + "/" + response.data.id;
                            $http.post(fullContext).then(function(response){
                                $state.go('patrocinioEventoListDetail',{patrocinioId: response.data.id},{reload:true});
                            });
                        });
                    },function(response){
                        //Si no hay dinero suficiente en la tarjeta.
                        $rootScope.alerts.pop();
                        $scope.errorNoDinero = true;
                    });     
                },function(response){
                //Si no se encuentra la tarjeta que se quiere usar.
                $rootScope.alerts.pop();
                $scope.errorNoTarjeta = true;
            });
            }
 
        };
    }
    ]);
}
)(angular);