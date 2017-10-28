(function (ng) {
    var mod = ng.module("grupoModule");
    mod.constant("gruposContext", "Stark/grupos");
    
    mod.controller('grupoUpdateCtrl', ['$scope', '$http', 'gruposContext', '$state', '$rootScope', '$filter',
        function ($scope, $http, gruposContext, $state, $rootScope, $filter) {
            $rootScope.edit = true;
            
            var idgrupo = $state.params.grupoId;
            $scope.crearGrupo=false;
            $scope.actualizarGrupo=true;
            //Consulto el autor a editar.
            $http.get(gruposContext + '/' + idgrupo).then(function (response) {
                var grupoActual = response.data;
                $scope.grupoName = grupoActual.nombre;
                $scope.grupoDescription = grupoActual.descripcion;
            });
            
            $scope.creategrupo = function () {
                /*Se llama a la función newBooks() para buscar cada uno de los ids de los books
                         en el array que tiene todos los books y así saber como queda la lista final de los books asociados al autor.
                 */
                $http.put(gruposContext + "/" + idgrupo, {
                    nombre: $scope.grupoName,
                    descripcion: $scope.grupoDescription
                }).then(function (response) {
                    
                    //grupo created successfully
                    $state.go('grupoDetail', {grupoId: response.data.id}, {reload: true});
                }, function (error,status) {
                    $scope.errorGruposMensaje=error.data;
                    $scope.errorGruposTitulo='Error creando grupo';
                    $("#modalModificarGrupos").modal('show');
                });
            };
            
            
        }
    ]);
}
        )(angular);