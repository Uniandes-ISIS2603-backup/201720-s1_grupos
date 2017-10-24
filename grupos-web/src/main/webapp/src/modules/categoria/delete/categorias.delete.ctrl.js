(function (ng) {
    var mod = ng.module("grupoModule");
    mod.constant("gruposContext", "Stark/grupos");
    
    mod.controller('grupoDeleteCtrl', ['$scope', '$http', 'gruposContext', '$state',
        function ($scope, $http, gruposContext, $state) {
            var idgrupo = $state.params.grupoId;
            
            $scope.crearGrupo=true;
            $scope.actualizarGrupo=false;
            $scope.deleteGrupo = function () {
                console.log("voy a borrar");
                $http.delete(gruposContext + '/' + idgrupo, {}).then(function (response) {
                    $state.go('listaGrupos',{}, {reload: true});
                }, function (error) {
                    $scope.errorGrupos=error.status;
                    console.log(errorGrupos);
                    $("#modalErrorGrupos").modal();
                });
            };
        }
    ]);
}
        )(angular);