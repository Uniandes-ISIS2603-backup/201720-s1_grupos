(function (ng) {
    //Módulo
    var mod = ng.module("noticiasModule");
    /**
     * Controlador con $scope, $state, $http, noticiasContext (Ruta de noticia), noticiaUsuarioContext (Ruta de usuario), grupoContext(Ruta de grupo)
     */
    mod.controller("usuarioNoticiasCtrl", ['$scope', '$state', '$http','noticiasContext','noticiaUsuarioContext','globalContext', function ($scope, $state, $http,context, usuarioContext,globalContext) {
            //inicialización del mensaje de error
            error="";
            if($state.params.mensaje!==null && $state.params.mensaje!==undefined)
            {
                $scope.variableErrorNoticia=$state.params.mensaje;
                error=$scope.variableErrorNoticia;
            }
            //Inicialización de booleanos importantes
            $scope.esNoticiaUsuario=true; 
            $scope.deGrupo=false; 
            //Validación de desde dónde viene la noticia,
            $scope.noticiaEditable=true;
            //Header
            header="Tus noticias";
            //Contexto
                fullContext=globalContext+"/"+usuarioContext+"/"+$state.params.usuarioId+"/"+context;
            
            // carga las noticias
            $http.get(fullContext).then(function (response) {
                $scope.records = response.data;
            },function(response){
                                //Estado de error
                                error=response.data;
                                $state.go('ERRORUSUARIONOTICIA',{mensaje: error},{reload:true});
                            });
            //Autor
            var currentAutor={};
                    //Autor por default (Se define con el login)
                    $http.get("Stark/usuarios/"+sessionStorage.getItem("id")).then(function(response){
                                    currentAutor.apellido= response.data.apellido,
                                    currentAutor.email= response.data.email,
                                    currentAutor.id= response.data.id,
                                    currentAutor.nombre= response.data.nombre,
                                    currentAutor.password= response.data.password}, function(response){
                                        $state.go('ERRORUSUARIONOTICIA',{mensaje: "El usuario "+sessionStorage.getItem("id")+ " no está loggeado"},{reload:true});
                                    });
            /**
             * Retorna un string aleatorio como link formado.<br>
             * @return link aleatorio
             */
            this.randomString= function()
            {
                var text="";
              var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
                   for (var i = 0; i < 5; i++)
                 text += possible.charAt(Math.floor(Math.random() * possible.length));
                return text;  
            };
            // inicialmente el listado de noticias está vacio
            $scope.records = {};
            // carga las noticias
            $http.get(fullContext).then(function (response) {
                $scope.records = response.data;
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
                            if(response.data.autor.id!==currentAutor.id)
                            {
                                $scope.esAutor=false;
                            }
                            else 
                            {
                                $scope.esAutor=true;
                            }
                        },function(response){
                                //Estado de error
                                error=response.data;
                                $state.go('ERRORUSUARIONOTICIA',{mensaje: error},{reload:true});
                            });

                // el controlador no recibió un cityId
            } else {
                // el registro actual debe estar vacio
                $scope.currentRecord = {
                    id: undefined /*Tipo Long. El valor se asigna en el backend*/,
                    name: '' /*Tipo String*/,
                };

            }
            /**
             * Guarda el registro con id dado.<br>
             * @param {type} id
             */
            this.saveRecord = function (id) {
                
                //Record actual
                currentRecord = $scope.currentRecord;

                // si el id es null, es un registro nuevo, entonces lo crea
                if (id === null || id===undefined) {
                   error="No se puede crear una noticia desde acá";
                   $state.go('ERRORUSUARIONOTICIA',{mensaje: error},{reload:true});
                } else {

                    //Id del autor diferente al de la noticia
                    if(!$scope.esAutor)
                    {
                        error="No es el autor de la noticia";
                        $state.go('ERRORGRUPONOTICIA',{mensaje: error},{reload:true});
                    }
                    // ejecuta PUT en el recurso REST
                    return $http.put(fullContext + "/" + currentRecord.id, currentRecord)
                            .then(function () {
                                // $http.put es una promesa
                                // cuando termine bien, cambie de estado
                                $state.go('usuarioNoticiasList');
                            },function(response){
                                //Estado de error
                                error=response.data;
                                $state.go('ERRORUSUARIONOTICIA',{mensaje: error},{reload:true});
                            });
                }
                ;
            };
            /**
             * Borra el registro con id dado.<br>
             * @param {type} id
             */
            this.deleteRecord= function(id)
            {
                if(id!==null)
                {
                    if(!$scope.esAutor)
                    {
                        error="No es el autor de la noticia";
                        $state.go('ERRORGRUPONOTICIA',{mensaje: error},{reload:true});
                    }
                    return $http.delete(fullContext+"/"+id).then (function()
                    {
                          $state.go('usuarioNoticiasList');
                    },function(response){
                                //Estado de error
                                error=response.data;
                                $state.go('ERRORUSUARIONOTICIA',{mensaje: error},{reload:true});
                            });
                }
            };
            /**
             * Retorna el header.<br>
             * @returns {header|String}
             */
            this.getHeader= function()
            {
                return header;
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
             * Retorna el error que se tiene por ahora.<br>
             * @type(String|error)
             */
            this.getError=function(){
                return $scope.variableErrorNoticia;
            };
            /**
             * Se devuelve al perfil del usuario
             */
            this.devolverAPerfil=function()
            {
                $state.go("usuarioDetail",{usuarioId:sessionStorage.getItem("id")},{reload:true});
            };

        }]);
})(window.angular);

