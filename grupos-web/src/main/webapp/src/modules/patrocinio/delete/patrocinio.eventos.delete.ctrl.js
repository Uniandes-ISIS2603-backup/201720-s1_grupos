(function (ng) {
    //Módulo
    var mod = ng.module("patrocinioModule");
    mod.constant("eventosContext", "Stark/eventos");
    mod.constant("patrociniosContext","patrocinios");
    /**
     * Controlador con $scope, $state, $http, eventosContext (Ruta de evento), eventoGrupoContext (Ruta de grupo), grupoContext(Ruta de grupo)
     */
    mod.controller("patrocinioEventosDeleteCtrl", ['$scope', '$state', '$http','eventosContext','patrociniosContext','globalContext', 
        function ($scope, $state, $http,eventosContext,patrociniosContext,globalContext) {
             /**
             * Borra el registro con id dado.<br>
             * @param {type} id 
             */         
            $scope.deleteEvento = function () 
            {
                fullContext = "Stark/" + eventosContext + "/"+$state.params.eventoId +"/"+ patrociniosContext + "/" + $state.params.patrocinioId;
                $http.delete(fullContext).then(function(response)
                { 
                    $state.go('patrocinioEventoListDetail',{patrocinioId: response.data.id},{reload:true});
                });
            };
        }]);
})(window.angular);

