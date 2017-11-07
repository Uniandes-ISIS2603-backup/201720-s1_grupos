(function (ng) {
    var mod = ng.module("eventoModule");
    mod.constant("eventoContext", "Stark/eventos");
    mod.constant("relacionContext","Stark/grupos");
    mod.controller('eventoDeleteCtrl', ['$scope', '$http', 'eventoContext','eventosContext','relacionContext', '$state',
        function ($scope, $http, eventoContext,eventosContext,relacionContext, $state) {
            var idEvento = $state.params.eventoId;
            $scope.deleteEvento = function () {
            fullContext = relacionContext + "/10/" + eventosContext + "/" + idEvento;
            $http.delete(fullContext).then(function(response)
                {   
                    $http.delete(eventoContext +"/" +idEvento).then(function(response){
                    $state.go('eventosList', {eventoId: response.data.id}, {reload: true});
                    });
                });
            };
        }
    ]);
}
)(angular);