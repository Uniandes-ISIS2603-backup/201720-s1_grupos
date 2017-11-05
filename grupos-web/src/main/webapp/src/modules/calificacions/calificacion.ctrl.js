
(function (ng) {
    //Módulo de la app
    var mod = ng.module("calificacionsModule");
    /**
     * Definición del controlador. Se incluye el nombre del controlador, el $scope,$state,$stateParams,$http y las constantes para acceder a calificaciones definiidos en archivos de módulo anteriores.
     */
    mod.controller('calificacionsCtrl', ['$scope', '$state', '$stateParams', '$http', 'calificacionsContext','gruposContext','blogContext', function ($scope, $state, $stateParams, $http, context, grupoContext, blogContext) {
            //Contexto completo del controlador
            fullContext=grupoContext+"/"+$state.params.grupoId+"/"+blogContext+"/"+$state.params.blogId+"/"+context;
            //Variables para la eliminación de la calificación
            $scope.calificacionSeleccionada=false;
            $scope.calificacionEliminada=false;
            /**
             * Inicialización dle mensaje de estado de error
             */
            if($state.params.mensaje!==null && $state.params.mensaje!==undefined)
            {
                $scope.variableErrorCalificacion=$state.params.mensaje;
            }
            // inicialmente el listado de calificaciones está vacio
            $scope.records = {};
            var errorCalificacion="Error";
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
                id = $stateParams.calificacionId;
                // obtiene el dato del recurso REST
                $http.get(fullContext + "/" + id)
                        .then(function (response) {
                            // $http.get es una promesa
                            // cuando llegue el dato, actualice currentRecord
                            $scope.currentRecord = response.data;
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
                currentRecord = $scope.currentRecord;
                //Calificador por default (Se define con el login)
                currentRecord.calificador={apellido: "Rd",
        email: "xd@uniandes.edu.co",
        id: 11,
        nombre: "DE",
        password: "Hola"};
                // si el id es null, es un registro nuevo, entonces lo crea
                if (id === null || id===undefined) {
                    // ejecuta POST en el recurso REST
                    return $http.post(fullContext, currentRecord)
                            .then(function () {
                                // $http.post es una promesa
                                // cuando termine bien, cambie de estado
                                $state.go('calificacionsList');
                            },function(response){
                                 //Estado de error
                                errorCalificacion=response.data;
                                $state.go('ERRORCALIFICACION',{mensaje: errorCalificacion});
                            });

                    // si el id no es null, es un registro existente entonces lo actualiza
                } else {

                    // ejecuta PUT en el recurso REST
                    return $http.put(fullContext + "/" + currentRecord.id, currentRecord)
                            .then(function () {
                                // $http.put es una promesa
                                // cuando termine bien, cambie de estado
                                $state.go('calificacionsList');
                            },function(response){
                                 //Estado de error
                                errorCalificacion=response.data;
                                $state.go('ERRORCALIFICACION',{mensaje: errorCalificacion});
                            });
            };
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
                    //Borre el registro
                    return $http.delete(fullContext+"/"+id).then (function()
                    {
                        //Cambie al listado de calificaciones
                        $state.go('calificacionsList');
                    },function(response){
                                //Estado de error
                                errorCalificacion=response.data;
                                $state.go('ERROR',{mensaje: errorCalificacion});
                    }  ) ;
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
// Código continua con las funciones de despliegue de errores


        }]);
})(window.angular);

