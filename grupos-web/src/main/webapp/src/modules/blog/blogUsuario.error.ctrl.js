(function (ng) {
    var mod = ng.module("blogModule");
   
    mod.controller('blogUsuarioErrorCtrl', ['$scope', '$state', function ($scope, $state) {
            $scope.mensajeDeError = $state.params.mensaje;
            $scope.blogsDeUsuario = true;
        }
    ]);
}
)(angular);
