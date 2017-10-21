(function (ng) {

    var mod = ng.module("multimediaModule");

    mod.controller("multimediaCtrl", ['$scope', '$state', '$stateParams', '$http', 'multimediaContext', function ($scope, $state, $stateParams, $http, context) {

            // inicialmente el listado de multimdia está vacio
            $scope.records = {};
            // carga la multimedia
            $http.get(context).then(function (response) {
                $scope.records = response.data;
            });

            // el controlador recibió un id ??
            // revisa los parámetros (ver el :id en la definición de la ruta)
            if ($stateParams.link !== null && $stateParams.link !== undefined) {

                // toma el id del parámetro
                link = $stateParams.link;
                // obtiene el dato del recurso REST
                $http.get(context + "/" + link)
                        .then(function (response) {
                            // $http.get es una promesa
                            // cuando llegue el dato, actualice currentRecord
                            $scope.currentRecord = response.data;
                        });

                // el controlador no recibió un cityId
            } else {
               
               
                $scope.alerts = [];
            }
            this.saveRecord = function (link) {
                currentRecord = $scope.currentRecord;

                // si el id es null, es un registro nuevo, entonces lo crea
                if (link == null || link==undefined) {
                    // ejecuta POST en el recurso REST
                    return $http.post(context, currentRecord)
                            .then(function () {
                                // $http.post es una promesa
                                // cuando termine bien, cambie de estado
                                $state.go('multimediaList');
                            });

                    // si el id no es null, es un registro existente entonces lo actualiza
                } else {

                    // ejecuta PUT en el recurso REST
                    return $http.put(context + "/" + currentRecord.link, currentRecord)
                            .then(function () {
                                // $http.put es una promesa
                                // cuando termine bien, cambie de estado
                                $state.go('multimediaList');
                            });
                }
                ;
            }
            this.deleteRecord= function(link)
            {
                if(link!=null)
                {
                    return $http.delete(context+"/"+link).then (function()
                    {
                        $state.reload();
                    })
                }
            };

// Código continua con las funciones de despliegue de errores


        }]);
})(window.angular);

