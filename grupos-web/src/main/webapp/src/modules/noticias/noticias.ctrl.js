(function (ng) {

    var mod = ng.module("noticiasModule");

    mod.controller("noticiasCtrl", ['$scope', '$state', '$http','noticiasContext','globalContext', function ($scope, $state, $http,context,globalContext) {
             $scope.noticiaEditable=false;$scope.esNoticiaUsuario=false; $scope.deGrupo=false;

             var error="";
             if($state.params.mensajeError!==null && $state.params.mensajeError!==undefined)
             {
                console.log("ERROR "+$state.params.mensajeError);
                 $scope.variableErrorNoticia=$state.params.mensajeError;
             }
            fullContext=globalContext+"/"+context;
            
            header="¿Qué pasa con tus intereses hoy?";            
            
            // inicialmente el listado de noticias está vacio
            $scope.records = {};
            // carga las noticias
            $http.get(fullContext).then(function (response) {
                $scope.records = response.data;
            },function(response){
                                error=response.data;
                                $state.go('ERRORNOTICIA',{mensajeError: error},{reload:true});
                            });
            // el controlador recibió un id ??
            // revisa los parámetros (ver el :id en la definición de la ruta)
            if ($state.params.noticiaId !== null && $state.params.noticiaId !== undefined) {

                // toma el id del parámetro
                id = $state.params.noticiaId;
                // obtiene el dato del recurso REST
                $http.get(fullContext + "/" + id)
                        .then(function (response) {
                            // $http.get es una promesa
                            // cuando llegue el dato, actualice currentRecord
                            $scope.currentRecord = response.data;
                        },function(response){
                                error=response.data;
                                $state.go('ERRORNOTICIA',{mensajeError: error},{reload:true});
                            });

                // el controlador no recibió un cityId
            } else {
                // el registro actual debe estar vacio
                $scope.currentRecord = {
                    id: undefined /*Tipo Long. El valor se asigna en el backend*/,
                    name: '' /*Tipo String*/,
                };

            }
            this.getHeader= function()
            {
                return header;
            };


        }]);
})(window.angular);

