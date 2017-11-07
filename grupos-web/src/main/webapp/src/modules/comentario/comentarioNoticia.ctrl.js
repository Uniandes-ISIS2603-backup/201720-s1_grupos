(function (ng) {
    var mod = ng.module("comentarioModule");
   
    mod.controller('comentarioNoticiaCtrl', ['$scope', '$http', 'comentarioContext', 'noticiaContext', '$state',
        function ($scope, $http, comentarioContext, noticiaContext, $state) {
            $scope.comentariosBlog = false;
            $scope.comentariosNoticia = true;
            $scope.comentariosUsuarioNoticia = false;
            $scope.comentariosGrupoNoticia = false;
            
            $scope.goComentarioList = function () {
                $state.go('comentarioNoticiaList', {}, {reload: true});
            };
            
            $scope.goPublicacionDetail = function () {
                $state.go('noticiaNoEditableDetail', {}, {reload: true});
            };
            
            $http.get(noticiaContext + '/' + $state.params.noticiaId + '/' + comentarioContext).then(function (response) {
                $scope.comentarioRecords = response.data;
            }, function (error) {
                
            });
            
        } ]);

}
)(angular);
