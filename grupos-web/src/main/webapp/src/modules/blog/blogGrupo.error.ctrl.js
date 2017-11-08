(function (ng) {
    var mod = ng.module("blogModule");
   
    mod.controller('blogGrupoErrorCtrl', ['$scope', '$state', function ($scope, $state) {
            //variables necesarias para mostrar el error
            $scope.mensajeDeError = $state.params.mensaje;
            $scope.blogsDeUsuario = false;
        }
    ]);
}
)(angular);
