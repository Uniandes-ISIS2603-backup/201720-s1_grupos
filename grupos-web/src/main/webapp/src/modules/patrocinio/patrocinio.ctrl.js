(function (ng) {
    //variable de modulo de patrocinio
    var mod = ng.module("patrocinioModule");
    mod.constant("usuariosContext","Stark/usuarios");
    mod.constant("usuarioPatrocinioContext", "patrocinios");
    //constante que define el contexto del patrocinio
    mod.constant("patrocinioContext", "Stark/patrocinios");
    mod.controller('patrocinioCtrl', ['$scope', '$http', 'usuariosContext', 'usuarioPatrocinioContext','patrocinioContext', '$state',
        function ($scope, $http, usuariosContext, usuarioPatrocinioContext, patrocinioContext, $state) {
            //get de todos los patrocinios de la aplicación
            $http.get(patrocinioContext).then(function (response) {
                $scope.patrociniosRecords = response.data;
            });
            //get de un patrocinio
            if ($state.params.patrocinioId !== undefined) {
                $http.get(usuariosContext + '/' + sessionStorage.getItem("id") + '/' + usuarioPatrocinioContext + '/' + $state.params.patrocinioId).then(function (response) {
                    $scope.currentPatrocinio = response.data;
                    $scope.patrocinio = response.data;
                });
            }
        } 
    ]);

}
)(angular);