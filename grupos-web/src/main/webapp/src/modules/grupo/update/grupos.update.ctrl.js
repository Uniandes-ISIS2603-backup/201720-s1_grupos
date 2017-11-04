/**
 *Controlador que sirve para actualizar el grupo
 */
(function (ng) {
    var mod = ng.module("grupoModule");
    mod.constant("gruposContext", "Stark/grupos");    
    mod.controller('grupoUpdateCtrl', ['$scope', '$http', 'gruposContext', '$state', '$rootScope', '$filter',
        function ($scope, $http, gruposContext, $state, $rootScope, $filter) {
            //Se indica que se va a editar para mostrar los respectivos botones, se guarda el id como variable
            $rootScope.edit = true;            
            var idgrupo = $state.params.grupoId;
            $scope.crearGrupo=false;
            $scope.actualizarGrupo=true;
            //Consulto el grupo a editar.
            $http.get(gruposContext + '/' + idgrupo).then(function (response) {
                //Guardo las variables del grupo para mostrarlas en los cuadros de editar
                var grupoActual = response.data;
                $scope.grupoName = grupoActual.nombre;
                $scope.grupoDescription = grupoActual.descripcion;
                $scope.idGrupo=grupoActual.id;
            });
            /**
             * Función para volver a un grupo detail en caso que se oprima cancelar
             * @param {type} idGrupo, id del grupo al que se quiere regresar
             */
            $scope.volver = function(idGrupo)
            {
                $state.go('grupoDetail', {grupoId: $scope.idGrupo}, {reload: true});
            };
            /**
             * Función para actualizar el grupo
             */
            $scope.creategrupo = function () {
                //Se envía el servicio PUT
                $http.put(gruposContext + "/" + idgrupo, {
                    nombre: $scope.grupoName,
                    descripcion: $scope.grupoDescription
                }).then(function (response) {                    
                    //Se creó bien, se devuelve al grupo detail
                    $state.go('grupoDetail', {grupoId: response.data.id}, {reload: true});
                }, function (error) {
                    //En caso de error se muestra el modal correspondiente
                    $scope.errorGruposMensaje=error.data;
                    $scope.errorGruposTitulo='Error creando grupo';
                    $("#modalModificarGrupos").modal('show');
                });
            };
            
            
        }
    ]);
}
        )(angular);