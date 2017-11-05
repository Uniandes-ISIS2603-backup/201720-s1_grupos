(function (ng) {

    var mod = ng.module("multimediaModule");

    mod.controller('multimediaBlogCtrl', ['$scope', '$state', '$http', 'multimediaContext','blogContext', 'grupoContext', function ($scope, $state, $http, multimediaContext,blogContext, grupoContext) {
            
            if($state.params.mensaje!==null && $state.params.mensaje!==undefined)
            {
                $scope.variableErrorMultimedia=$state.params.mensaje;
            }
            //Inicialización de variable para saber si es de blog o no.
            $scope.esMultimediaBlog=true;
            $scope.esMultimediaNoticia=false;

            fullContext=grupoContext+"/"+$state.params.grupoId+"/"+blogContext+"/"+$state.params.blogId+"/"+multimediaContext;
            
            /**
             * Genera un link con un String aleatorio.<br>
             * @returns String aleatorio como link
             */
            this.randomString= function()
            {
                 var text="";
              var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
                   for (var i = 0; i < 5; i++)
                 text += possible.charAt(Math.floor(Math.random() * possible.length));
                return text;
            };
            // inicialmente el listado de multimdia está vacio
            $scope.multimediaRecords = {};
            // carga la multimedia
            $http.get(fullContext).then(function (response) {
                $scope.multimediaRecords = response.data;
            },function(response){
                                error=response.data;
                                $state.go('ERRORMULTIMEDIABLOG',{mensaje: error},{reload:true});
                            });

            // el controlador recibió un id ??
            // revisa los parámetros (ver el :id en la definición de la ruta)
            if ($state.params.multimediaLink !== null && $state.params.multimediaLink !== undefined) {

                // toma el id del parámetro
                link = $state.params.multimediaLink;
                // obtiene el dato del recurso REST
                $http.get(fullContext+"/"+ link)
                        .then(function (response) {
                            // $http.get es una promesa
                            // cuando llegue el dato, actualice currentMultimedia
                            $scope.currentMultimedia = response.data;
                        },function(response){
                                error=response.data;
                                $state.go('ERRORMULTIMEDIABLOG',{mensaje: error},{reload:true});
                            });

                // el controlador no recibió un cityId
            } else {
               
               
                $scope.alerts = [];
            }
            this.saveRecord = function (link) {
                currentMultimedia = $scope.currentMultimedia;

                // si el id es null, es un registro nuevo, entonces lo crea
                if (link === null || link===undefined) {
                    currentMultimedia.link=this.randomString();
                    // ejecuta POST en el recurso REST
                    multimediaList=[currentMultimedia];
                    return $http.post(fullContext, multimediaList)
                            .then(function () {
                                // $http.post es una promesa
                                // cuando termine bien, cambie de estado
                                $state.go('blogMultimediaList',{},{reload:true});
                            },function(response){
                                error=response.data;
                                $state.go('ERRORMULTIMEDIABLOG',{mensaje: error},{reload:true});
                            });
                    currentMultimedia.link=null;
                    // si el id no es null, es un registro existente entonces lo actualiza
                } else {

                    // ejecuta PUT en el recurso REST
                    return $http.put(fullContext+"/" + currentMultimedia.link, currentMultimedia)
                            .then(function () {
                                // $http.put es una promesa
                                // cuando termine bien, cambie de estado
                                $state.go('blogMultimediaList',{},{reload:true});
                            },function(response){
                                error=response.data;
                                $state.go('ERRORMULTIMEDIABLOG',{mensaje: error},{reload:true});
                            });
                }
                ;
            };
            this.deleteRecord= function(link)
            {
                if(link!==null)
                {
                    return $http.delete(fullContext+"/"+link).then (function()
                    {
                         $state.go('blogMultimediaList',{},{reload:true});
                    },function(response){
                                error=response.data;
                                $state.go('ERRORMULTIMEDIABLOG',{mensaje: error},{reload:true});
                            });
                }
            };

        }]);
})(angular);
