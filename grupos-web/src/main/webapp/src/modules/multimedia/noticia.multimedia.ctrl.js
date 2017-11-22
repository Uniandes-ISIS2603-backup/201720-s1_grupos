(function (ng) {
    //Módulo
    var mod = ng.module("multimediaModule");
    /**
     * Controlador con $scope, $state, $http, multimediaContext (Ruta de multimedia), noticiasContext (Ruta de noticias), globalContext(Ruta raíz)
     */
    mod.controller('noticiaMultimediaCtrl', ['$scope', '$state', '$http', 'multimediaContext','noticiasContext','globalContext', function ($scope, $state, $http, multimediaContext,noticiasContext, globalContext) {
            $scope.archivos=[];
            $http.get("./data/archivos.json").then(function(response)
            {
                $scope.archivos=response.data;
                var i=0;
                for(i=0;i<$scope.archivos.length;i++)
                {
                    $scope.archivos[i].ruta="data/"+$scope.archivos[i].ruta;
                    console.log($scope.archivos[i].ruta);
                }
            },function(response)
            {
                console.log("HOLA");
            });
            if($state.params.mensaje!==null && $state.params.mensaje!==undefined)
            {
                $scope.variableErrorMultimedia=$state.params.mensaje;
            }
            //Inicialización d variable para saber si es de blog o no.
            $scope.esMultimediaBlog=false;
            $scope.esMultimediaNoticia=true;
            //Contexto global
            fullContext=globalContext+"/"+noticiasContext+"/"+$state.params.noticiaId+"/"+multimediaContext;

            // inicialmente el listado de multimdia está vacio
            $scope.multimediaRecords = {};
            // carga la multimedia
            $http.get(fullContext).then(function (response) {
                $scope.multimediaRecords = response.data;
            },function(response){
                                //Función de error
                                error=response.data;
                                $state.go('ERRORMULTIMEDIANOTICIA',{mensaje: error},{reload:true});
                            });
            
            // el controlador recibió un link ??
            // revisa los parámetros (ver el :link en la definición de la ruta)
            if ($state.params.multimediaLink !== null && $state.params.multimediaLink !== undefined) {

                // toma el link del parámetro
                link = $state.params.multimediaLink;
                // obtiene el dato del recurso REST
                $http.get(fullContext+"/"+ link)
                        .then(function (response) {
                            // $http.get es una promesa
                            // cuando llegue el dato, actualice currentMultimedia
                            $scope.currentMultimedia = response.data;
                        },function(response){
                            //Estado de error
                                error=response.data;
                                $state.go('ERRORMULTIMEDIANOTICIA',{mensaje: error},{reload:true});
                            });

            } 
            
            

        }]);
})(angular);
