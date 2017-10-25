(function (ng) {
    var mod = ng.module("lugarModule");
    mod.constant("lugaresContext", "Stark/lugares");
    mod.controller('lugarCtrl', ['$scope', '$http', 'lugaresContext','$state',
        function ($scope, $http, lugaresContext,$state) {
            $http.get(lugaresContext).then(function (response) {
                $scope.lugaresRecords = response.data;
            });
            if ($state.params.lugarId !== undefined) {
                $http.get(lugaresContext + '/' + $state.params.lugarId).then(function (response) {
                    $scope.currentLugar = response.data;
                });
            }
        }
    ]);
}
)(angular);
