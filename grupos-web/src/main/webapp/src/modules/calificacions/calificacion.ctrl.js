
(function (ng) {
    //Módulo de la app
    var mod = ng.module("calificacionsModule");
    /**
     * Definición del controlador. Se incluye el nombre del controlador, el $scope,$state,$stateParams,$http y las constantes para acceder a calificaciones definiidos en archivos de módulo anteriores.
     */
    mod.controller('calificacionsCtrl', ['$scope', '$state', '$stateParams', '$http', 'calificacionsContext','gruposContext','blogContext', function ($scope, $state, $stateParams, $http, context, grupoContext, blogContext) {
            var errorCalificacion="Error";
            var currentRecord={};
            //Contexto completo del controlador
            var fullContext=grupoContext+"/"+$state.params.grupoId+"/"+blogContext+"/"+$state.params.blogId+"/"+context;
            //Variables para la eliminación de la calificación
            $scope.calificacionSeleccionada=false;
            $scope.calificacionEliminada=false;
            //Verifica si es miembro del grupo
            $http.get(grupoContext+"/"+$state.params.grupoId+"/miembros/"+sessionStorage.getItem("id")).then(function(){
                $scope.esMiembro=true;
            },function(){
                $scope.esMiembro=false;
            });
            //Verifica si es admin del grupo
            $http.get(grupoContext+"/"+$state.params.grupoId+"/administradores/"+sessionStorage.getItem("id")).then(function(){
                $scope.esAdmin=true;
            },function(){
                $scope.esAdmin=false;
            });
            //Calificaor por default 1
            var currentAutor={};
            //Calificador por default el 1 (Se define con el login)
            $http.get("Stark/usuarios/"+sessionStorage.getItem("id")).then(function(response){
                            currentAutor.apellido= response.data.apellido,
                            currentAutor.email= response.data.email,
                            currentAutor.id= response.data.id,
                            currentAutor.nombre= response.data.nombre,
                            currentAutor.password= response.data.password}, function(){
                                $state.go('ERROR',{mensaje: "El usuario "+sessionStorage.getItem("id")+"  no está loggeado"},{reload:true});
                            });
            /**
             * Inicialización dle mensaje de estado de error
             */
            if($state.params.mensaje!==null && $state.params.mensaje!==undefined)
            {
                $scope.variableErrorCalificacion=$state.params.mensaje;
            }
            // inicialmente el listado de calificaciones está vacio
            $scope.records = {};
            // carga las calificaciones
             $http.get(fullContext).then(function (response) {
                $scope.records = response.data;
            },function(response){
                 //Estado de error
                errorCalificacion=response.data;
                $state.go('ERROR',{mensaje: errorCalificacion});
            });

            // el controlador recibió un id ??
            // revisa los parámetros (ver el :id en la definición de la ruta)
            if ($stateParams.calificacionId !== null && $stateParams.calificacionId !== undefined) {

                // toma el id del parámetro
                var id = $stateParams.calificacionId;
                // obtiene el dato del recurso REST
                $http.get(fullContext + "/" + id)
                        .then(function (response) {
                            // $http.get es una promesa
                            // cuando llegue el dato, actualice currentRecord
                            $scope.currentRecord = response.data;
                            currentRecord=$scope.currentRecord;
                        },function(response)
                        {
                             //Estado de error
                            errorCalificacion=response.data;
                            $state.go('ERRORCALIFICACION',{mensaje: errorCalificacion});

                        })
                        ;

                // el controlador no recibió un id
            } else {
                // el registro actual debe estar vacio
                $scope.currentRecord = {
                    id: undefined /*Tipo Long. El valor se asigna en el backend*/,
                    name: '' /*Tipo String*/
                };
            }
            /**
             * Guarda el registro en la base de datos.<br>
             * @param {type} id Id a guardar.<br>
             * @returns {unresolved} Función resultado de la promesa
             */
            this.saveRecord=function(id) {
            if($scope.esMiembro || $scope.esAdmin)
            {
                currentRecord=$scope.currentRecord;
                //Calificador por default (Se define con el login)
                currentRecord.calificador=currentAutor;
                // si el id es null, es un registro nuevo, entonces lo crea
                if (id === null || id===undefined) {
                    
                    // ejecuta POST en el recurso REST
                    return $http.post(fullContext, currentRecord)
                            .then(function () {
                                // $http.post es una promesa
                                // cuando termine bien, cambie de estado
                                $state.go('calificacionsList',{},{reload:true});
                            },function(response){
                                 //Estado de error
                                errorCalificacion=response.data;
                                $state.go('ERRORCALIFICACION',{mensaje: errorCalificacion});
                            });
                    

                    // si el id no es null, es un registro existente entonces lo actualiza
                } else {
                    if(currentRecord.calificador.id!==currentAutor.id)
                    {
                        var errorCalificacion="No puedes calificar si no eres el autor";
                        $state.go('ERRORCALIFICACION',{mensaje: errorCalificacion});
                    }
                    else
                    {
                        // ejecuta PUT en el recurso REST
                        return $http.put(fullContext + "/" + currentRecord.id, currentRecord)
                            .then(function () {
                                // $http.put es una promesa
                                // cuando termine bien, cambie de estado
                                $state.go('calificacionsList',{},{reload:true});
                            },function(response){
                                 //Estado de error
                                errorCalificacion=response.data;
                                $state.go('ERRORCALIFICACION',{mensaje: errorCalificacion});
                            });
                    }
                    
                };
            }
            else
            {
                        errorCalificacion="No puede calificar si no hace parte del grupo";
                        $state.go('ERRORCALIFICACION',{mensaje: errorCalificacion});

            }
        }
            /**
             * Borra el registro.<br>
             * @param {type} id Id del registro.<br>
             * @returns {unresolved} Respuesta de la promesa
             */
            this.deleteRecord=function (id)
            {
                
                    //Si el id existe ejecute la instrucción
                if(id!==null)
                {
                    if(currentRecord.calificador.id!==currentAutor.id)
                    {
                        errorCalificacion="No puedes calificar si no eres el autor";
                        $state.go('ERRORCALIFICACION',{mensaje: errorCalificacion});
                    }
                    else
                    {
                        //Borre el registro
                        return $http.delete(fullContext+"/"+id).then (function()
                        {
                            //Cambie al listado de calificaciones
                            $state.go('calificacionsList',{},{reload:true});
                        },function(response){
                                    //Estado de error
                                    errorCalificacion=response.data;
                                    $state.go('ERROR',{mensaje: errorCalificacion});
                        }  ) ;
                    }
                    
                    }
                
                
            };
            /**
             * Obtiene el valor de la barra de calificación de la interfaz.<br>
             * @returns {Element.value} Valor de la calificación en interfaz
             */
            this.getRangeValue=function()
            {
                return document.getElementById("calificacion").value;
            };
            /**
             * Obtiene el error
             * @return Error
             */
            this.getError=function()
            {
                return errorCalificacion;
            };
            /**
             * Obtener si el autor concuerda con el usuario loggeado.<br>
             * @type type
             */
            this.esAutor=function()
            {
                if(currentRecord.calificador!==undefined)
                {
                    return currentRecord.calificador.id===currentAutor.id;
                }
                else
                {
                    return false;
                }
            };
            


        }]);
})(window.angular);

