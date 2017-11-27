(function (ng) {
    var mod = ng.module("eventoModule");
    mod.constant("eventoContext", "Stark/eventos");
    mod.controller('eventoCalendarDetailCtrl', ['$scope', '$http', 'eventoContext','$state',
        function ($scope, $http, eventoContext,$state) {
            
            var inscrito = false;
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
            
            /**
             * Inscribe un usuario a un evento
             * @returns {undefined}
             */
            $scope.inscripcionUsuario = function() {
                $http.post(eventoContext+'/'+$scope.currentEvento.id + '/usuarios/'+ $scope.idUsuarioActual).then(function(resonse){
                    $scope.inscrito = true;
                },function(error)
                {
                });
            };
            
            /**
             * Retira la inscripción de un usuario a un evento
             * @returns {undefined}
             */
            $scope.retirarUsuario = function() {
                $http.delete(eventoContext+'/'+$scope.currentEvento.id + '/usuarios/'+ $scope.idUsuarioActual).then(function(resonse){
                    $scope.inscrito = false;
                },function(error)
                {
                });
            };
        }
    ]);

})(angular);
