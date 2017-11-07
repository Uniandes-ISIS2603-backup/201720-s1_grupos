(function (ng) {
    //Módulo
    var mod = ng.module("eventoModule");
    /**
     * Controlador con $scope, $state, $http, eventosContext (Ruta de evento), eventoGrupoContext (Ruta de grupo), grupoContext(Ruta de grupo)
     */
    mod.controller("grupoEventosCtrl", ['$scope', '$state', '$http','eventosContext','eventoGrupoContext','globalContext', function ($scope, $state, $http,context, grupoContext,globalContext) {
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
            this.deleteRecord= function(id)
            {
                if(id!==null)
                {
                    return $http.delete(fullContext+"/"+id).then (function()
                    {
                          $state.go('grupoEventosList');
                    },function(response){
                                //Estado de error
                                error=response.data;
                                $state.go('ERRORGRUPOEvento',{mensaje: error},{reload:true});
                            });
                }
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

