(function (ng) {
    var mod = ng.module("eventoModule");
    mod.constant("eventoContext", "Stark/eventos");
    mod.constant("usuariosContext", "Stark/usuarios");
    mod.controller('eventoCtrl', ['$scope', '$http', 'eventoContext','usuariosContext','$state',
        function ($scope, $http, eventoContext,usuariosContext,$state) {
            $scope.deGrupo=false;
            $scope.inscrito= false;
            $scope.eventoUsuario = false;
            //Indica el usuario logeado actual
            $scope.idUsuarioActual=sessionStorage.getItem("id");
            if($state.params.usuarioId === undefined){
            $http.get(eventoContext).then(function (response) {
                $scope.records = response.data;
            });
        }
            $scope.inscripcionUsuario = function() {
                $http.post(eventoContext+'/'+$scope.currentEvento.id + '/usuarios/'+ $scope.idUsuarioActual).then(function(){
                    var idEvento = $scope.currentEvento.id;
                    $state.go('eventoDetail',{idEvento},{reload:true});
                },function()
                {
                });
            };
            $scope.retirarUsuario = function() {
                $http.delete(eventoContext+'/'+$scope.currentEvento.id + '/usuarios/'+ $scope.idUsuarioActual).then(function(){
                    var idEvento = $scope.currentEvento.id;
                    $state.go('eventoDetail',{idEvento},{reload:true});
                },function()
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
            if ($state.params.usuarioId !== undefined) {
                $http.get(usuariosContext + '/' + $scope.idUsuarioActual).then(function (response) {
                    $scope.usuarioActual = response.data;
                    $scope.records = $scope.usuarioActual.eventos;
                    $scope.eventoUsuario = true;
                });
            }
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
