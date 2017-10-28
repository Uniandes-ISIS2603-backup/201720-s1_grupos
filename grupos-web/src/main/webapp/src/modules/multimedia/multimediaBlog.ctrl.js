(function (ng) {

    var mod = ng.module("multimediaModule");

    mod.controller('multimediaBlogCtrl', ['$scope', '$state', '$http', 'multimediaContext','blogContext', 'grupoContext', function ($scope, $state, $http, multimediaContext,blogContext, grupoContext) {
            
            //Inicialización d variable para saber si es de blog o no.
            $scope.esMultimediaBlog=true;
            $scope.esMultimediaNoticia=false;

            fullContext=grupoContext+"/"+$state.params.grupoId+"/"+blogContext+"/"+$state.params.blogId+"/"+multimediaContext;
            
            this.randomString= function()
            {
                 var text="";
              var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
                   for (var i = 0; i < 10; i++)
                 text += possible.charAt(Math.floor(Math.random() * possible.length));
                 console.log("TEXTO "+text);
                return text;
            }
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
            this.saveRecord = function (link) {
                currentMultimedia = $scope.currentMultimedia;

                // si el id es null, es un registro nuevo, entonces lo crea
                if (link === null || link===undefined) {
                    currentMultimedia.link=this.randomString();
                    // ejecuta POST en el recurso REST
                    multimediaList=[currentMultimedia];
                    return $http.post(fullContext, multimediaList)
                            .then(function () {
                                // $http.post es una promesa
                                // cuando termine bien, cambie de estado
                                $state.go('blogMultimediaList');
                            });
                    currentMultimedia.link=null;
                    // si el id no es null, es un registro existente entonces lo actualiza
                } else {

                    // ejecuta PUT en el recurso REST
                    return $http.put(fullContext+"/" + currentMultimedia.link, currentMultimedia)
                            .then(function () {
                                // $http.put es una promesa
                                // cuando termine bien, cambie de estado
                                $state.go('blogMultimediaList');
                            });
                }
                ;
            }
            this.deleteRecord= function(link)
            {
                if(link!==null)
                {
                    return $http.delete(fullContext+"/"+link).then (function()
                    {
                        $state.reload();
                    })
                }
            }
            this.prueba = function(){
                console.log("HOLA Q HACE");
            }

            
// Código continua con las funciones de despliegue de errores


        }]);
})(angular);
