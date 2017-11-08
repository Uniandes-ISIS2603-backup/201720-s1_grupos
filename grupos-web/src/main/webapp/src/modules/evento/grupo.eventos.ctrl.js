(function (ng) {
    //Módulo
    var mod = ng.module("eventoModule");
    mod.constant("eventoContext", "Stark/eventos");
    mod.constant("relacionContext","Stark/grupos");
    /**
     * Controlador con $scope, $state, $http, eventosContext (Ruta de evento), eventoGrupoContext (Ruta de grupo), grupoContext(Ruta de grupo)
     */
    mod.controller("grupoEventosCtrl", ['$scope', '$state', '$http','eventosContext','eventoContext','relacionContext','eventoGrupoContext','globalContext', function ($scope, $state, $http,context,eventoContext,relacionContext, grupoContext,globalContext) {
             //Inicialización de booleanos importantes
            $scope.esEventoUsuario=false;
            $scope.deGrupo=true; 
            //Inicialización de mensaje de error
            var error="";
            if($state.params.mensaje!==null && $state.params.mensaje!==undefined)
            {
                $scope.variableErrorEvento=$state.params.mensaje;
            }
           //Header
              header="Eventos de grupo";
              //Contexto global
            fullContext=globalContext+"/"+grupoContext+"/"+$state.params.grupoId+"/"+context;                       
            // carga las eventos
            $http.get(fullContext).then(function (response) {
                $scope.records = response.data;
            },function(response){
                                //Estado de error
                                error=response.data;
                                $state.go('ERRORGRUPOEvento',{mensaje: error},{reload:true});
                            });

            // el controlador recibió un id ??
            // revisa los parámetros (ver el :id en la definición de la ruta)
            if ($state.params.eventoId !== null && $state.params.eventoId !== undefined) {

                // toma el id del parámetro
                id = $state.params.eventoId;
                // obtiene el dato del recurso REST
                $http.get(fullContext + "/" + id)
                        .then(function (response) {
                            // $http.get es una promesa
                            // cuando llegue el dato, actualice currentRecord
                            $scope.currentRecord = response.data;
                        },function(response){
                                //Estado de error
                                error=response.data;
                                $state.go('ERRORGRUPOEvento',{mensaje: error},{reload:true});
                            });

            } else {
                // el registro actual debe estar vacio
                $scope.currentRecord = {
                    id: undefined /*Tipo Long. El valor se asigna en el backend*/,
                    name: '' /*Tipo String*/
                };

            }
             /**
             * Borra el registro con id dado.<br>
             * @param {type} id 
             */         
            var idEvento = $state.params.eventoId;
            $scope.deleteEvento = function () {
                console.log($state)
            fullContext = relacionContext + "/"+$state.params.grupoId +"/"+ context + "/" + idEvento;
            $http.delete(fullContext).then(function(response)
                { 
                    $http.delete(eventoContext +"/" +idEvento).then(function(response){
                    $state.go('eventosList', {eventoId: response.data.id}, {reload: true});
                    });
                });
            };
            
            /**
             * Agrega un evento al grupo dado por parametro
             * 
             */
            $scope.createEvento = function(){
            $http.post(eventoContext, {
                nombre :$scope.eventoNombre,
                fechaFin :$scope.eventoFechaFin,
                fechaInicio :$scope.eventoFechaInicio              
            }).then(function (response) {
                console.log($state);
            fullContext = relacionContext + "/"+ $state.params.grupoId +"/" + context + "/" + response.data.id;
            $http.post(fullContext).then(function(response){
                $state.go('eventosList',{eventoId: response.data.id},{reload:true});
            });
        });
        };
            
            /**
             * Retorna el header actual.<br>
             * @returns {header|String}
             */
            this.getHeader= function()
            {
                return header;
            };

        }]);
})(window.angular);
