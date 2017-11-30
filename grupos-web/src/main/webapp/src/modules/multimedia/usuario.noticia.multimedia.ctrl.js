(function (ng) {
    
    var mod = ng.module("multimediaModule");

    mod.controller('usuarioNoticiaMultimediaCtrl', ['$scope', '$state', '$http', 'multimediaContext','noticiasContext','globalContext','noticiaUsuarioContext', function ($scope, $state, $http, multimediaContext,noticiaContext, globalContext,usuarioContext) {
            var error="";
           var currentMultimedia={};
           var multimediaList=[];
            //Inicialización de rutas de la multimedia
            $scope.archivos=[];
            $http.get("./data/archivos.json").then(function(response)
            {
                $scope.archivos=response.data;
                for(var i=0;i<$scope.archivos.length;i++)
                {
                    $scope.archivos[i].ruta="data/"+$scope.archivos[i].ruta;
                }
            });
            
            $http.get(globalContext + "/" + noticiaContext+"/"+$state.params.noticiaId)
                        .then(function (response) {
                            // $http.get es una promesa
                            // cuando llegue el dato, actualice currentRecord
                            $scope.esAutor= (response.data.autor.id===parseInt(sessionStorage.getItem("id")));
                        }, function()
                        {
                            $state.go('ERRORMULTIMEDIAUSUARIONOTICIA',{mensaje: "Usted no es el autor de la noticia"},{reload:true});
                        });
            if($state.params.mensaje!==null && $state.params.mensaje!==undefined)
            {
                $scope.variableErrorMultimedia=$state.params.mensaje;
            }
            //Inicialización de variable para saber si es de blog o no.
            $scope.esMultimediaBlog=false;
            $scope.esMultimediaNoticia=true;
            //Inicialización del multimediaContexto
            var fullContext=globalContext+"/"+usuarioContext+"/"+$state.params.usuarioId+"/"+noticiaContext+"/"+$state.params.noticiaId+"/"+multimediaContext;
            
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
            $scope.currentMultimedia={};
            if ($state.params.multimediaLink !== null && $state.params.multimediaLink !== undefined) {

                // toma el link del parámetro
                var link = $state.params.multimediaLink;
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
                if(!this.verificarMultimedia())
                {
                    return;
                }
                if(this.esAutor())
                {
                        //Multimedia actual
                    currentMultimedia = $scope.currentMultimedia;
                    currentMultimedia.ruta=$scope.ruta;
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
                }
                else
                {
                    $state.go('ERRORMULTIMEDIAUSUARIONOTICIA',{mensaje: "Usted no es el autor de la noticia"},{reload:true});
                }
                
            };
            /**
             * Borra elr egistro con el link dado.<br>
             * @param {type} link
             */
            this.deleteRecord= function(link)
            {
                if(this.esAutor())
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
                }
                else
                {
                   $state.go('ERRORMULTIMEDIAUSUARIONOTICIA',{mensaje: "Usted no es el autor de la noticia"},{reload:true});
                }
                
            };
             /**
             * Retorna si es autor o no
             * @returns {Boolean|esAutor}
             */
            this.esAutor=function()
            {
                return $scope.esAutor;
            };
            /**
             * Se devuelve al perfil del usuario
             */
            this.devolverAPerfil=function()
            {
                $state.go("usuarioDetail",{usuarioId:sessionStorage.getItem("id")},{reload:true});
            };
            //Función para asignar la ruta cuando se le da click en la multimedia
            this.asignarRuta=function(ruta)
            {
                document.getElementById("rutamultimedia").value = ruta;
                $scope.selectedOption=ruta;
                $scope.ruta=ruta;
            };
            //Función para verificar la multimedia actual
            this.verificarMultimedia=function()
            {
                if($scope.ruta===null || $scope.ruta===undefined)
                {
                    return false;
                }
                return true;
            };
            
            $scope.style={"background-color" : "coral"};

        }]);
})(angular);

