(function (ng) {
    var mod = ng.module("comentarioModule");
   
    mod.controller('comentarioGrupoNoticiaCtrl', ['$scope', '$http', 'comentarioContext', 'noticiaContext', '$state',
        function ($scope, $http, comentarioContext, noticiaContext, $state) {
            $scope.comentariosBlog = false;
            $scope.comentariosNoticia = false;
            $scope.comentariosUsuarioNoticia = false;
            $scope.comentariosGrupoNoticia = true;
            
            $scope.goComentarioList = function () {
                $state.go('comentarioGrupoNoticiaList', {}, {reload: true});
            };
            
            $scope.goPublicacionDetail = function () {
                $state.go('grupoNoticiaDetail', {}, {reload: true});
            };
            
            $http.get(noticiaContext + '/' + $state.params.noticiaId + '/' + comentarioContext).then(function (response) {
                $scope.comentarioRecords = response.data;
            }, function (error) {
                
            });
            
        } ]);

}
)(angular);
