(function (ng) {
    var mod = ng.module("eventoModule");
    mod.constant("eventoContext", "Stark/eventos");
    mod.controller('eventoCtrl', ['$scope', '$http', 'eventoContext','$state',
        function ($scope, $http, eventoContext,$state) {
            $scope.deGrupo=false;
            $http.get(eventoContext).then(function (response) {
                $scope.eventosRecords = response.data;
            });
            if ($state.params.eventoId !== undefined) {
                $http.get(eventoContext + '/' + $state.params.eventoId).then(function (response) {
                    $scope.currentEvento = response.data;
                });
            }
        }
    ]);
}
)(angular);
