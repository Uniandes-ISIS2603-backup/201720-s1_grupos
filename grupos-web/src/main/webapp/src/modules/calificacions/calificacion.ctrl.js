(function (ng) {

    var mod = ng.module("calificacionsModule");

    mod.controller('calificacionsCtrl', ['$scope', '$state', '$stateParams', '$http', 'calificacionsContext','gruposContext','blogContext', function ($scope, $state, $stateParams, $http, context, grupoContext, blogContext) {

            fullContext=grupoContext+"/"+$state.params.grupoId+"/"+blogContext+"/"+$state.params.blogId+"/"+context;
            
            $scope.calificacionSeleccionada=false;
            $scope.calificacionEliminada=false;
            // inicialmente el listado de ciudades está vacio
            $scope.records = {};
            // carga las ciudades
            $http.get(fullContext).then(function (response) {
                $scope.records = response.data;
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
                        });

                // el controlador no recibió un cityId
            } else {
                // el registro actual debe estar vacio
                $scope.currentRecord = {
                    id: undefined /*Tipo Long. El valor se asigna en el backend*/,
                    name: '' /*Tipo String*/,
                };

                $scope.alerts = [];
            }
            this.saveRecord=function(id) {
                currentRecord = $scope.currentRecord;
                currentRecord.calificador={apellido: "Rd",
        email: "xd@uniandes.edu.co",
        id: 11,
        nombre: "DE",
        password: "Hola"}
                // si el id es null, es un registro nuevo, entonces lo crea
                if (id == null) {
                    // ejecuta POST en el recurso REST
                    return $http.post(fullContext, currentRecord)
                            .then(function () {
                                // $http.post es una promesa
                                // cuando termine bien, cambie de estado
                                $state.go('calificacionsList');
                            });

                    // si el id no es null, es un registro existente entonces lo actualiza
                } else {

                    // ejecuta PUT en el recurso REST
                    return $http.put(fullContext + "/" + currentRecord.id, currentRecord)
                            .then(function () {
                                // $http.put es una promesa
                                // cuando termine bien, cambie de estado
                                $state.go('calificacionsList');
                            });
                }
                ;
            }
            this.deleteRecord=function (id)
            {
                if(id!=null)
                {
                    return $http.delete(fullContext+"/"+id).then (function()
                    {
                        $state.go('calificacionsList');
                    })
                }
            }
            this.getRangeValue=function()
            {
                return document.getElementById("calificacion").value;
            };

// Código continua con las funciones de despliegue de errores


        }]);
})(window.angular);

