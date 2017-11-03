(function (ng) {
    var mod = ng.module("grupoModule");
    mod.constant("gruposContext", "Stark/grupos");
    mod.controller('grupoNewCtrl', ['$scope', '$http', 'gruposContext', '$state', '$rootScope',
        function ($scope, $http, gruposContext, $state, $rootScope) {
            $rootScope.edit = false;
            $scope.crearGrupo=true;
            $scope.actualizarGrupo=false;
            $scope.volver = function()
            {
                $state.go('listaGrupos',{}, {reload: true});
            }
            $scope.creategrupo = function () {
                $http.post(gruposContext, {
                    nombre: $scope.grupoName,
                    descripcion: $scope.grupoDescription
                }).then(function (response) {
                    //grupo created successfully
                    console.log("la data:" + response.data.id);
                    $state.go('listaGrupos',{}, {reload: true});
                }, function (error) {
                    console.log("llega Status: "+ error.status);
                    console.log(error.data)
                    $scope.errorGruposMensaje=error.data;
                    $scope.errorGruposTitulo='Error creando grupo';
                    $("#modalCrearGrupos").modal('show');
                });
            };
            
        }
    ]);
}
        )(angular);