/**
 * Controlador que sirve para crear un grupo
 */
(function (ng) {
    var mod = ng.module("grupoModule");
    mod.constant("gruposContext", "Stark/grupos");
    mod.controller('grupoNewCtrl', ['$scope', '$http', 'gruposContext', '$state', '$rootScope',
        function ($scope, $http, gruposContext, $state, $rootScope) {
            //Se indica que se va a crear el grupo para mostrar los respectivos botones, se guarda el id como variable
            $rootScope.edit = false;
            $scope.crearGrupo=true;
            $scope.actualizarGrupo=false;
            //En caso que se oprima cancelar, se vuelve a la lista de grupos
            $scope.volver = function()
            {
                $state.go('listaGrupos',{}, {reload: true});
            }
            /**
             * Función para crear el grupo
             */
            $scope.creategrupo = function () {
                //Se envía el servicio POST con la info del grupo nuevo
                $http.post(gruposContext, {
                    nombre: $scope.grupoName,
                    descripcion: $scope.grupoDescription
                }).then(function (response) {
                    //grupo creado exitosamente
                    $state.go('listaGrupos',{}, {reload: true});
                }, function (error) {
                    //En caso de error se muestra el modal correspondiente
                    $scope.errorGruposMensaje=error.data;
                    $scope.errorGruposTitulo='Error creando grupo';
                    $("#modalCrearGrupos").modal('show');
                });
            };
            
        }
    ]);
}
        )(angular);