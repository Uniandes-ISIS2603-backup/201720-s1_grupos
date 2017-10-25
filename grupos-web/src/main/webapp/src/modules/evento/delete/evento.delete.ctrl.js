(function (ng) {
    var mod = ng.module("eventoModule");
    mod.constant("eventosContext", "Stark/eventos");
    mod.controller('eventoDeleteCtrl', ['$scope', '$http', 'eventosContext', '$state',
        function ($scope, $http, eventosContext, $state) {
            var idEvento = $state.params.eventoId;
            $scope.deleteEvento = function () {
                $http.delete(eventosContext + '/' + idEvento, {}).then(function (response) {
                    $state.go('eventosList', {eventoId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(angular);