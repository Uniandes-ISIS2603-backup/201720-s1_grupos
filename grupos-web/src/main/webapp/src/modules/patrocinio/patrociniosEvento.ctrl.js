(function (ng) {
    //variable con el modulo actual
    var mod = ng.module("patrocinioModule");
    //se define la constante que une patrocinios con el usuario actual
    mod.constant("patrociniosEventoContext", "patrocinios");
    //se define la constante que determina la ruta de usuarios
    mod.constant("eventosContext", "Stark/eventos");
    mod.controller('eventoPatrocinioCtrl', ['$scope', '$http', 'eventosContext', '$state', 'patrociniosEventoContext', '$rootScope',
        function ($scope, $http, eventosContext, $state, patrociniosEventoContext, $rootScope) {
           //Se buscan todos los patrocinios del usuario actual
            $http.get('Stark/'+eventosContext + '/' + $state.params.eventoId + '/' + patrociniosEventoContext).then(function (response) {
                $scope.patrociniosRecords = response.data;
            });
        }
    ]);
}
)(angular); 