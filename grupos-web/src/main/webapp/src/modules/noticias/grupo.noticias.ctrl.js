(function (ng) {
    //Módulo
    var mod = ng.module("noticiasModule");
    /**
     * Controlador con $scope, $state, $http, noticiasContext (Ruta de noticia), noticiaGrupoContext (Ruta de grupo), grupoContext(Ruta de grupo)
     */
    mod.controller("grupoNoticiasCtrl", ['$scope', '$state', '$http','noticiasContext','noticiaGrupoContext','globalContext', function ($scope, $state, $http,context, grupoContext,globalContext) {
            var error=""; 
            //Inicialización de booleanos importantes
            $scope.esNoticiaUsuario=false;
            $scope.deGrupo=true; 
            $scope.noticiaEditable=true;
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
            
            
            //Autor
            var currentAutor={};
                    //Autor por default (Se define con el login)
                    $http.get("Stark/usuarios/"+sessionStorage.getItem("id")).then(function(response){
                                    currentAutor.apellido= response.data.apellido,
                                    currentAutor.email= response.data.email,
                                    currentAutor.id= response.data.id,
                                    currentAutor.nombre= response.data.nombre,
                                    currentAutor.password= response.data.password}, function(response){
                                        error="El usuario "+sessionStorage.getItem("id")+ " no está loggeado";
                                        $state.go('ERRORGRUPONOTICIA',{mensaje: error},{reload:true});
                                    });
            //Inicialización de mensaje de error
            if($state.params.mensaje!==null && $state.params.mensaje!==undefined)
            {
                $scope.variableErrorNoticia=$state.params.mensaje;
                error=$scope.variableErrorNoticia;
            }
           //Header
              header="Noticias de grupo";
              //Contexto global
            fullContext=globalContext+"/"+grupoContext+"/"+$state.params.grupoId+"/"+context;                       
            // carga las noticias
            $http.get(fullContext).then(function (response) {
                $scope.records = response.data;
            },function(response){
                                //Estado de error
                                error=response.data;
                                $state.go('ERRORGRUPONOTICIA',{mensaje: error},{reload:true});
                            });
            
            //Inicialización de elementos multimedia a agregar a la noticia.
            $scope.multimedia=[];
            //Items para agregar
            $scope.itemsToAdd=[{nombre:' ',descripcion:' ',link:' '}];
            /**
             * Agrega un elemento a la lista por añadir en el post.<br>
             * @param {type} itemToAdd Item por añadir.<br>
             */
            this.add=function(itemToAdd){
                itemToAdd.link=this.randomString();
                var index=$scope.itemsToAdd.indexOf(itemToAdd);
                $scope.itemsToAdd.splice(index,1);
                $scope.multimedia.push(angular.copy(itemToAdd));
            };
            /**
             * Agrega un nuevo item pendiente.<br>
             */
            this.addNew=function(){
                $scope.itemsToAdd.push({nombre:' ',descripcion:' ',link:' '});
            };
            /**
             * Agrega todos los elementos de la lista de POST.
             */
            this.addAll=function()
            {
                while($scope.itemsToAdd.length!==0)
                {
                    this.add($scope.itemsToAdd[0]);
                }
            };
            /**
             * Remueve un item de los pendientes.<br>
             * @param {type} itemToAdd
             */
            this.remove=function(itemToAdd)
            {
                var index=$scope.itemsToAdd.indexOf(itemToAdd);
                $scope.itemsToAdd.splice(index,1);
            };
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
                            if(response.data.id!==currentAutor.id)
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
                                $state.go('ERRORGRUPONOTICIA',{mensaje: error},{reload:true});
                            });

            } else {
                // el registro actual debe estar vacio
                $scope.currentRecord = {
                    id: undefined /*Tipo Long. El valor se asigna en el backend*/,
                    name: '' /*Tipo String*/
                };

            }
            /**
             * Guarda el registro con id dado por parámetro.<br>
             * @param {type} id
             */
            this.saveRecord = function (id) {
                if(!$scope.esMiembro || !$scope.esAdmin)
                {
                    error="El usuario no pertenece al grupo asociado";
                    $state.go('ERRORGRUPONOTICIA',{mensaje: error},{reload:true});
                }
                currentRecord = $scope.currentRecord;
                // si el id es null, es un registro nuevo, entonces lo crea
                if (id === null || id===undefined) {
                    this.addAll();
                    currentRecord.multimedia=$scope.multimedia;
                    
                    currentRecord.autor=currentAutor;
                    // ejecuta POST en el recurso REST
                    return $http.post(fullContext, currentRecord)
                            .then(function () {
                                // $http.post es una promesa
                                // cuando termine bien, cambie de estado
                                $state.go('grupoNoticiasList');
                            },function(response){
                                //Estado de error
                                error=response.data;
                                $state.go('ERRORGRUPONOTICIA',{mensaje: error},{reload:true});
                            });

                    // si el id no es null, es un registro existente entonces lo actualiza
                } else {
                    
                    if(!$scope.esAutor)
                    {
                        $state.go('ERRORGRUPONOTICIA',{mensaje: "No es el autor de la noticia"},{reload:true});
                    }
                    else
                    {
                        // ejecuta PUT en el recurso REST
                            return $http.put(fullContext + "/" + $scope.currentRecord.id, currentRecord)
                            .then(function () {
                                // $http.put es una promesa
                                // cuando termine bien, cambie de estado
                                $state.go('grupoNoticiasList');
                            },function(response){
                                //Estado de error
                                error=response.data;
                                $state.go('ERRORGRUPONOTICIA',{mensaje: error},{reload:true});
                            });
                    }
                        
                    
                }
                ;
            };
            /**
             * Borra el registro con id dado.<br>
             * @param {type} id 
             */
            this.deleteRecord= function(id)
            {
                //No es miembro
                if(!$scope.esMiembro|| !$scope.esAdmin)
                {
                    error="El usuario no pertenece al grupo asociado";
                    $state.go('ERRORGRUPONOTICIA',{mensaje:error},{reload:true});
                }
                if(id!==null)
                {
                    //Id del autor diferente al de la noticia
                    if(!$scope.esAutor)
                    {
                        error="No es el autor de la noticia"
                                    $state.go('ERRORGRUPONOTICIA',{mensaje:error},{reload:true});
                    }
                    else
                    {
                       return $http.delete(fullContext+"/"+id).then (function()
                        {
                              $state.go('grupoNoticiasList');
                        },function(response){
                                    //Estado de error
                                    error=response.data;
                                    $state.go('ERRORGRUPONOTICIA',{mensaje: error},{reload:true});
                                }); 
                    }
                        
                }
                 
            };
            /**
             * Obtiene el error
             * @return Error
             */
            this.getError=function()
            {
                return error;
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

