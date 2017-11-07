(function (ng) {
    var mod = ng.module("comentarioModule");
   
    mod.controller('comentarioNoticiaErrorCtrl', ['$scope', '$state', function ($scope, $state) {
            $scope.mensajeDeError = $state.params.mensaje;
            $scope.botonDeRegreso = 'Volver a la noticia';
        }
    ]);
}
)(angular);
