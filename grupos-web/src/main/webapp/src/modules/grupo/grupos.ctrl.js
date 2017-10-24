(function (ng) {
    var mod = ng.module("grupoModule");
    mod.constant("grupoContext", "Stark/grupos");
   
    mod.controller('grupoCtrl', ['$scope', '$http', 'grupoContext', '$state',
        function ($scope, $http, grupoContext, $state) {
             $scope.opcionesGrupo=false;
             console.log($scope.opcionesGrupo);
            $http.get(grupoContext).then(function (response) {
                $scope.grupoRecords = response.data;
            });
            
            if ($state.params.grupoId !== undefined) {
                $http.get(grupoContext + '/' + $state.params.grupoId).then(function (response) {
                    $scope.grupoActual = response.data;
                    $scope.categoriaRecords=response.data.categorias;
                });
            }
        }
    ]);
}
)(angular);