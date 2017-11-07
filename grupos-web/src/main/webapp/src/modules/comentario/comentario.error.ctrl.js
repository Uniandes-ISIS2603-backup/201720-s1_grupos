(function (ng) {
    var mod = ng.module("comentarioModule");
   
    mod.controller('comentarioErrorCtrl', ['$scope', '$state', function ($scope, $state) {
            $scope.mensajeDeError = $state.params.mensaje;
            $scope.botonDeRegreso = 'Volver al blog';
        }
    ]);
}
)(angular);
