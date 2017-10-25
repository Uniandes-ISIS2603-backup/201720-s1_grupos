(function (ng) {
    var mod = ng.module("eventoModule");
    mod.constant("eventosContext", "Stark/eventos");
    mod.controller('eventoCtrl', ['$scope', '$http', 'eventosContext','$state',
        function ($scope, $http, eventosContext,$state) {
            $http.get(eventosContext).then(function (response) {
                $scope.eventosRecords = response.data;
            });
            if ($state.params.eventoId !== undefined) {
                $http.get(eventosContext + '/' + $state.params.eventoId).then(function (response) {
                    $scope.currentEvento = response.data;
                });
            }
        }
    ]);
}
)(angular);
