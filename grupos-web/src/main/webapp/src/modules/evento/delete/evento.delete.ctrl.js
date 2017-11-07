(function (ng) {
    var mod = ng.module("eventoModule");
    mod.constant("eventoContext", "Stark/eventos");
    mod.controller('eventoDeleteCtrl', ['$scope', '$http', 'eventoContext', '$state',
        function ($scope, $http, eventoContext, $state) {
            var idEvento = $state.params.eventoId;
            $scope.deleteEvento = function () {
                $http.delete(eventoContext + '/' + idEvento, {}).then(function (response) {
                    $state.go('eventosList', {eventoId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(angular);