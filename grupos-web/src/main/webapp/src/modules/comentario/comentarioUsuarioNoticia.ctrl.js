(function (ng) {
    var mod = ng.module("comentarioModule");
   
    mod.controller('comentarioUsuarioNoticiaCtrl', ['$scope', '$http', 'comentarioContext', 'noticiaContext', '$state',
        function ($scope, $http, comentarioContext, noticiaContext, $state) {
            $scope.comentariosBlog = false;
            $scope.comentariosNoticia = false;
            $scope.comentariosUsuarioNoticia = true;
            $scope.comentariosGrupoNoticia = false;
            
            $scope.goComentarioList = function () {
                $state.go('comentarioUsuarioNoticiaList', {}, {reload: true});
            };
            
            $scope.goPublicacionDetail = function () {
                $state.go('usuarioNoticiaDetail', {}, {reload: true});
            };
            
            $http.get(noticiaContext + '/' + $state.params.noticiaId + '/' + comentarioContext).then(function (response) {
                $scope.comentarioRecords = response.data;
            }, function (error) {
                
            });
            
        } ]);

}
)(angular);
