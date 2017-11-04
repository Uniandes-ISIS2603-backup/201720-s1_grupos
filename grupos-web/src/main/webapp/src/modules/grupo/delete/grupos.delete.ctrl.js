/**
 * Controlador que sirve para borrar un grupo
 */
(function (ng) {
    var mod = ng.module("grupoModule");
    mod.constant("gruposContext", "Stark/grupos");    
    mod.controller('grupoDeleteCtrl', ['$scope', '$http', 'gruposContext', '$state',
        function ($scope, $http, gruposContext, $state) {
            //Se guarda el id de grupo y se indica que se va a borrar
            var idgrupo = $state.params.grupoId;
            $scope.idGrupo=idgrupo;
            $scope.deleteGrupo = function () {
                //Se envía el servicio rest DELETE
                $http.delete(gruposContext + '/' + idgrupo, {}).then(function (response) {
                    $state.go('listaGrupos',{}, {reload: true});
                }, function (error) {
                    
                });
            };
        }
    ]);
}
        )(angular);