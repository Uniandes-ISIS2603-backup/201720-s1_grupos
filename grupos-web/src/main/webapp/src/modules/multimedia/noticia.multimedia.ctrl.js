(function (ng) {

    var mod = ng.module("multimediaModule");

    mod.controller('noticiaMultimediaCtrl', ['$scope', '$state', '$http', 'multimediaContext','noticiasContext','globalContext', function ($scope, $state, $http, multimediaContext,noticiasContext, globalContext) {
            
            //Inicialización d variable para saber si es de blog o no.
            $scope.esMultimediaBlog=false;
            $scope.esMultimediaNoticia=true;

            fullContext=globalContext+"/"+noticiasContext+"/"+$state.params.noticiaId+"/"+multimediaContext;

            // inicialmente el listado de multimdia está vacio
            $scope.multimediaRecords = {};
            // carga la multimedia
            $http.get(fullContext).then(function (response) {
                $scope.multimediaRecords = response.data;
            });
            
            // el controlador recibió un id ??
            // revisa los parámetros (ver el :id en la definición de la ruta)
            if ($state.params.multimediaLink !== null && $state.params.multimediaLink !== undefined) {

                // toma el id del parámetro
                link = $state.params.multimediaLink;
                // obtiene el dato del recurso REST
                $http.get(fullContext+"/"+ link)
                        .then(function (response) {
                            // $http.get es una promesa
                            // cuando llegue el dato, actualice currentMultimedia
                            $scope.currentMultimedia = response.data;
                        });

                // el controlador no recibió un cityId
            } else {
               
               
                $scope.alerts = [];
            }
// Código continua con las funciones de despliegue de errores


        }]);
})(angular);
