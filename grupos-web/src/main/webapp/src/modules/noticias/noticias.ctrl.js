(function (ng) {

    var mod = ng.module("noticiasModule");

    mod.controller("noticiasCtrl", ['$scope', '$state', '$http', 'noticiasContext','usuarioContext','grupoContext', function ($scope, $state, $http, context, usuarioContext, grupoContext) {
            fullContext=context;
            header="¿Qué pasa con tus intereses hoy?";
            if($state.params.idUsuario!==null && $state.params.idUsuario!==undefined)
            {
                header="Tus noticias";
                fullContext=usuarioContext+"/"+$state.params.idUsuario+"/"+context;
            }
            else if($state.params.idGrupo!==null && $state.params.idGrupo!==undefined)
            {
                header="Noticias de grupo";
                fullContext=grupoContext+"/"+$state.params.idGrupo+"/"+context;
            }
            
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
            console.log($scope.currentRecord);

            this.saveRecord = function (id) {
                currentRecord = $scope.currentRecord;

                // si el id es null, es un registro nuevo, entonces lo crea
                if (id == null) {

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
                                $state.go('noticiasList');
                            });

                    // si el id no es null, es un registro existente entonces lo actualiza
                } else {

                    // ejecuta PUT en el recurso REST
                    return $http.put(fullContext + "/" + currentRecord.id, currentRecord)
                            .then(function () {
                                // $http.put es una promesa
                                // cuando termine bien, cambie de estado
                                $state.go('noticiasList');
                            });
                }
                ;
            }
            this.deleteRecord= function(id)
            {
                if(id!=null)
                {
                    return $http.delete(fullContext+"/"+id).then (function()
                    {
                        $state.reload();
                    })
                }
            }
            this.getHeader= function()
            {
                return header;
            };
            

// Código continua con las funciones de despliegue de errores


        }]);
})(window.angular);

