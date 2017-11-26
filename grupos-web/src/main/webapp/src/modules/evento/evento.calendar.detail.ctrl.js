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
        }
    ]);

})(angular);
