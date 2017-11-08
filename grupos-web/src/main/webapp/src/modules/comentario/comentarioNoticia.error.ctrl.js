(function (ng) {
    var mod = ng.module("comentarioModule");
   
    mod.controller('comentarioNoticiaErrorCtrl', ['$scope', '$state', function ($scope, $state) {
            //variables para mostrar el mensaje de error
            $scope.mensajeDeError = $state.params.mensaje;
            $scope.botonDeRegreso = 'Volver a la noticia';
        }
    ]);
}
)(angular);
