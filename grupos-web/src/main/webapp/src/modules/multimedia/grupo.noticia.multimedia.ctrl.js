(function (ng) {
    //Variable del módulo de multimedia
    var mod = ng.module("multimediaModule");
    /**
     * Controlador con $scope, $state, $http, multimediaContext (Ruta de multimedia), noticiasContext (Ruta de noticias), globalContext(Ruta raíz), noticiaGrupoContext (Ruta grupo)
     */
    mod.controller('grupoNoticiaMultimediaCtrl', ['$scope', '$state', '$http', 'multimediaContext','noticiasContext','globalContext','noticiaGrupoContext', function ($scope, $state, $http, multimediaContext,noticiaContext, globalContext,grupoContext) {
           
            //Inicialización de rutas de la multimedia
            $scope.archivos=[];
            $http.get("./data/archivos.json").then(function(response)
            {
                $scope.archivos=response.data;
                var i=0;
                for(i=0;i<$scope.archivos.length;i++)
                {
                    $scope.archivos[i].ruta="data/"+$scope.archivos[i].ruta;
                }
            });
            
            $http.get(globalContext + "/" + noticiaContext+"/"+$state.params.noticiaId)
                        .then(function (response) {
                            // $http.get es una promesa
                            // cuando llegue el dato, actualice currentRecord
                            $scope.esAutor= (response.data.autor.id==sessionStorage.getItem("id"));
                        }, function(error)
                        {
                            $state.go('ERRORMULTIMEDIAGRUPONOTICIA',{mensaje: "Usted no es el autor de la noticia"},{reload:true});
                        });
            //Verifica si es miembro del grupo

            $http.get(globalContext+"/"+grupoContext+"/"+$state.params.grupoId+"/miembros/"+sessionStorage.getItem("id")).then(function(response){
                $scope.esMiembro=true;
            },function(response){
                $scope.esMiembro=false;
            });
            //Verifica si es admin del grupo
            $http.get(globalContext+"/"+grupoContext+"/"+$state.params.grupoId+"/administradores/"+sessionStorage.getItem("id")).then(function(response){
                $scope.esAdmin=true;
            },function(response){
                $scope.esAdmin=false;
            });
            //Inicialización del mensaje de error
            if($state.params.mensaje!==null && $state.params.mensaje!==undefined)
            {
                $scope.variableErrorMultimedia=$state.params.mensaje;
            }
            //Inicialización de variable para saber si es de blog o no.
            $scope.esMultimediaBlog=false;
            $scope.esMultimediaNoticia=true;
            //Inicialización del multimediaContexto
            fullContext=globalContext+"/"+grupoContext+"/"+$state.params.grupoId+"/"+noticiaContext+"/"+$state.params.noticiaId+"/"+multimediaContext;
            //Inicialización de elementos multimedia a agregar a la noticia.
            $scope.multimedia=[];
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
                                error=response.data;
                                $state.go('ERRORMULTIMEDIAGRUPONOTICIA',{mensaje: error},{reload:true});
                            });

            // el controlador recibió un id ??
            // revisa los parámetros (ver el :id en la definición de la ruta)
            $scope.currentMultimedia={};
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
                             //Estado de error
                                error=response.data;
                                $state.go('ERRORMULTIMEDIAGRUPONOTICIA',{mensaje: error},{reload:true});
                            });

                // el controlador no recibió un cityId
            }
            /**
             * Guarda un registro.<br>
             * @param {type} link Link del registro.<br>
             */
            this.saveRecord = function (link) {
                if(($scope.esMiembro || $scope.esAdmin) && this.esAutor())
                {
                    //Multimedia actual
                    currentMultimedia = $scope.currentMultimedia;
                    currentMultimedia.ruta=$scope.ruta;
                    // si el link es null, es un registro nuevo, entonces lo crea
                    if (link === null || link===undefined) {
                        currentMultimedia.link=this.randomString();
                        // ejecuta POST en el recurso REST
                        multimediaList=[currentMultimedia];
                        return $http.post(fullContext, multimediaList)
                                .then(function () {
                                    // $http.post es una promesa
                                    // cuando termine bien, cambie de estado
                                    $state.go('grupoNoticiaMultimediaList',{},{reload:true});
                                },function(response){
                                     //Estado de error
                                    error=response.data;
                                    $state.go('ERRORMULTIMEDIAGRUPONOTICIA',{mensaje: error},{reload:true});
                                });
                        // si el link no es null, es un registro existente entonces lo actualiza
                    } else {

                        // ejecuta PUT en el recurso REST
                        return $http.put(fullContext+"/" + currentMultimedia.link, currentMultimedia)
                                .then(function () {
                                    // $http.put es una promesa
                                    // cuando termine bien, cambie de estado
                                    $state.go('grupoNoticiaMultimediaList',{},{reload:true});
                                },function(response){
                                    error=response.data;
                                     //Estado de error
                                    $state.go('ERRORMULTIMEDIAGRUPONOTICIA',{mensaje: error},{reload:true});
                                });
                    }
                    ;
                }
                else
                {
                     $state.go('ERRORMULTIMEDIAGRUPONOTICIA',{mensaje: "No tiene los permisos para acceder a este recurso"},{reload:true});

                }
                
            };
            /**
             * Borra el registro.<br>
             * @param {type} link Link para borrar.<br>
             */
            this.deleteRecord= function(link)
            {
                
                if(link!==null)
                {
                    if(($scope.esMiembro || $scope.esAdmin) && this.esAutor())
                    {
                        return $http.delete(fullContext+"/"+link).then (function()
                        {
                            //Estado para ver la multimedia
                             $state.go('grupoNoticiaMultimediaList',{},{reload:true});
                        },function(response){
                             //Estado de error
                                    error=response.data;
                                    $state.go('ERRORMULTIMEDIAGRUPONOTICIA',{mensaje: error},{reload:true});
                                });
                    }
                    else
                    {
                        $state.go('ERRORMULTIMEDIAGRUPONOTICIA',{mensaje: error},{reload:true});
                    }
                    
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
            //Función para asignar la ruta cuando se le da click en la multimedia
            this.asignarRuta=function(ruta)
            {
                $scope.selectedOption=ruta;
                $scope.ruta=ruta;
            };
            //Función para verificar la multimedia actual
            this.verificarMultimedia=function(ruta)
            {
                if($scope.ruta===null || $scope.ruta===undefined)
                {
                    return false;
                }
                
                return true;
            };

        }]);
})(angular);

