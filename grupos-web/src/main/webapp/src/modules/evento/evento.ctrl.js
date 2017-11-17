(function (ng) {
    var mod = ng.module("eventoModule");
    mod.constant("eventoContext", "Stark/eventos");
    mod.controller('eventoCtrl', ['$scope', '$http', 'eventoContext','$state',
        function ($scope, $http, eventoContext,$state) {
            $scope.deGrupo=false;
            $scope.inscrito= false;
            //Indica el usuario logeado actual
            $scope.idUsuarioActual=sessionStorage.getItem("id");
            $http.get(eventoContext).then(function (response) {
                $scope.eventosRecords = response.data;
            });
            $scope.inscripcionUsuario = function() {
                $http.post(eventoContext+'/'+$scope.currentEvento.id + '/usuarios/'+ $scope.idUsuarioActual).then(function(resonse){
                    var idEvento = $scope.currentEvento.id;
                    $state.go('eventoDetail',{idEvento},{reload:true});
                },function(error)
                {
                });
            };
            $scope.retirarUsuario = function() {
                $http.delete(eventoContext+'/'+$scope.currentEvento.id + '/usuarios/'+ $scope.idUsuarioActual).then(function(resonse){
                    var idEvento = $scope.currentEvento.id;
                    $state.go('eventoDetail',{idEvento},{reload:true});
                },function(error)
                {
                });
            };
            
            /**
             * indica si se pueden editar los eventos
             * @returns {Boolean} true si se pueden editar los eventos, false de lo contrario.
             */
            $scope.puedoEditarEventos = function() {
                return sessionStorage.getItem("rol") === 'Administrador';
            };
            var inscrito = false;
            if ($state.params.eventoId !== undefined) {
                $http.get(eventoContext + '/' + $state.params.eventoId).then(function (response) {
                    $scope.currentEvento = response.data;
                    $scope.usuariosRecords = response.data.usuarios;
                            for (var i = 0; i < $scope.usuariosRecords.length;i++ )
                            {
                                if(parseInt($scope.usuariosRecords[i].id)===parseInt($scope.idUsuarioActual))
                                {
                                    inscrito = true;
                                    break;
                                }
                            }
                            $scope.inscrito = inscrito;
                });
            }
        }
    ]);
}
)(angular);
