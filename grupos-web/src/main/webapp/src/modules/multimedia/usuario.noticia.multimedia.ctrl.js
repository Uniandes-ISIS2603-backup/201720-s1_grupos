(function (ng) {
    
    var mod = ng.module("multimediaModule");

    mod.controller('usuarioNoticiaMultimediaCtrl', ['$scope', '$state', '$http', 'multimediaContext','noticiasContext','globalContext','noticiaUsuarioContext', function ($scope, $state, $http, multimediaContext,noticiaContext, globalContext,usuarioContext) {
            if($state.params.mensaje!==null && $state.params.mensaje!==undefined)
            {
                $scope.variableErrorMultimedia=$state.params.mensaje;
            }
            //Inicialización de variable para saber si es de blog o no.
            $scope.esMultimediaBlog=false;
            $scope.esMultimediaNoticia=true;
            //Inicialización del multimediaContexto
                fullContext=globalContext+"/"+usuarioContext+"/"+$state.params.usuarioId+"/"+noticiaContext+"/"+$state.params.noticiaId+"/"+multimediaContext;
            
            //Función de creación del link temporalmente
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
                                //Función de error
                                error=response.data;
                                $state.go('ERRORMULTIMEDIAUSUARIONOTICIA',{mensaje: error},{reload:true});
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
                                $state.go('ERRORMULTIMEDIAUSUARIONOTICIA',{mensaje: error},{reload:true});
                            });

            
        }
            /**
             * Guarda el registro.<br>
             * @param {type} link Link de a multimedia
             */
            this.saveRecord = function (link) {
                //Multimedia actual
                currentMultimedia = $scope.currentMultimedia;
                // si el link es null, es un registro nuevo, entonces lo crea
                if (link === null || link===undefined) {
                    // multimedia actual
                    currentMultimedia.link=this.randomString();
                    // ejecuta POST en el recurso REST
                    multimediaList=[currentMultimedia];
                    //Promesa de post
                    return $http.post(fullContext, multimediaList)
                            .then(function () {
                                // $http.post es una promesa
                                // cuando termine bien, cambie de estado
                                $state.go('usuarioNoticiaMultimediaList',{},{reload:true});
                            },function(response){
                                //Estado de error
                                error=response.data;
                                $state.go('ERRORMULTIMEDIAUSUARIONOTICIA',{mensaje: error},{reload:true});
                            });
                    // si el link no es null, es un registro existente entonces lo actualiza
                } else {

                    // ejecuta PUT en el recurso REST
                    return $http.put(fullContext+"/" + currentMultimedia.link, currentMultimedia)
                            .then(function () {
                                // $http.put es una promesa
                                // cuando termine bien, cambie de estado
                                $state.go('usuarioNoticiaMultimediaList',{},{reload:true});
                            },function(response){
                                //Estado de error
                                error=response.data;
                                $state.go('ERRORMULTIMEDIAUSUARIONOTICIA',{mensaje: error},{reload:true});
                            });
                }
                ;
            };
            /**
             * Borra elr egistro con el link dado.<br>
             * @param {type} link
             */
            this.deleteRecord= function(link)
            {
                if(link!==null)
                {
                    return $http.delete(fullContext+"/"+link).then (function()
                    {
                         $state.go('usuarioNoticiaMultimediaList',{},{reload:true});
                    },function(response){
                                //Estado de error
                                error=response.data;
                                $state.go('ERRORMULTIMEDIAUSUARIONOTICIA',{mensaje: error},{reload:true});
                            })
                }
            };

        }]);
})(angular);

