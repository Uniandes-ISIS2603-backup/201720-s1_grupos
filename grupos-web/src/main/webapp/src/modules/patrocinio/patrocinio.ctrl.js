(function (ng) {
    var mod = ng.module("patrocinioModule");
    mod.constant("patrocinioContext", "Stark/patrocinios");
   
    mod.controller('patrocinioCtrl', ['$scope', '$http', 'patrocinioContext', '$state',
        function ($scope, $http, patrocinioContext, $state) {
            $http.get(patrocinioContext).then(function (response) {
                $scope.patrociniosRecords = response.data;
            });
            if ($state.params.patrocinioId !== undefined) {
                $http.get(patrocinioContext + '/' + $state.params.patrocinioId).then(function (response) {
                    $scope.currentPatrocinio = response.data;
                });
            }
        } 
    ]);

}
)(angular);