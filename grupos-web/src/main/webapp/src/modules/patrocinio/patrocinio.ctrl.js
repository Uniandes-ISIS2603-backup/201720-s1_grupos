(function (ng) {
    //variable de modulo de patrocinio
    var mod = ng.module("patrocinioModule");
    //constante que define el contexto del patrocinio
    mod.constant("patrocinioContext", "Stark/patrocinios");
    mod.controller('patrocinioCtrl', ['$scope', '$http', 'patrocinioContext', '$state',
        function ($scope, $http, patrocinioContext, $state) {
            //get de todos los patrocinios de la aplicación
            $http.get(patrocinioContext).then(function (response) {
                $scope.patrociniosRecords = response.data;
            });
            //get de un patrocinio
            if ($state.params.patrocinioId !== undefined) {
                $http.get(patrocinioContext + '/' + $state.params.patrocinioId).then(function (response) {
                    $scope.currentPatrocinio = response.data;
                });
            }
        } 
    ]);

}
)(angular);