(function (ng) {
    var mod = ng.module("eventoModule");
    mod.constant("eventosContext", "Stark/eventos");
    mod.controller('eventoCtrl', ['$scope', '$http', 'eventosContext','$state',
        function ($scope, $http, eventoContext,$state) {
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
