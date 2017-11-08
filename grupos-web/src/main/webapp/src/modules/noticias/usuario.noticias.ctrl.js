(function (ng) {
    //Módulo
    var mod = ng.module("noticiasModule");
    /**
     * Controlador con $scope, $state, $http, noticiasContext (Ruta de noticia), noticiaUsuarioContext (Ruta de usuario), grupoContext(Ruta de grupo)
     */
    mod.controller("usuarioNoticiasCtrl", ['$scope', '$state', '$http','noticiasContext','noticiaUsuarioContext','globalContext', function ($scope, $state, $http,context, usuarioContext,globalContext) {
            //inicialización del mensaje de error
            var error="";
            if($state.params.mensaje!==null && $state.params.mensaje!==undefined)
            {
                $scope.variableErrorNoticia=$state.params.mensaje;
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
                    this.addAll();
                    currentRecord.multimedia=$scope.multimedia;
                    currentRecord.autor={
    apellido: "Guzmán",
    email: "hola@uniandes.edu.co",
    id: 1,
    nombre: "Sergio",
    password: "hola"};
                    // ejecuta POST en el recurso REST
                    return $http.post(fullContext, currentRecord)
                            .then(function () {
                                // $http.post es una promesa
                                // cuando termine bien, cambie de estado
                                $state.go('usuarioNoticiasList');
                            },function(response){
                                //Estado de error
                                error=response.data;
                                $state.go('ERRORUSUARIONOTICIA',{mensaje: error},{reload:true});
                            });

                    // si el id no es null, es un registro existente entonces lo actualiza
                } else {

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

        }]);
})(window.angular);

