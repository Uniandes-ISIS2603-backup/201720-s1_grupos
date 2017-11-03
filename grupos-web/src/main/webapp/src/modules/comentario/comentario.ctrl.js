(function (ng) {
    var mod = ng.module("comentarioModule");
   
    mod.controller('comentarioCtrl', ['$scope', '$http', 'comentarioContext', 'blogContext', 'grupoContext', '$state',
        function ($scope, $http, comentarioContext, blogContext, grupoContext, $state) {
            $scope.comentariosBlog = true;
            $scope.comentariosNoticia = false;
            $scope.comentariosUsuarioNoticia = false;
            $scope.comentariosGrupoNoticia = false;
            
            
            $scope.goComentarioList = function () {
                $state.go('comentarioList', {}, {reload: true});
            };
            
            $scope.goPublicacionDetail = function () {
                $state.go('blogDetail', {}, {reload: true});
            };
            
            $http.get(grupoContext + '/' + $state.params.grupoId + '/' + blogContext + '/' + 
                    $state.params.blogId + '/' + comentarioContext).then(function (response) {
                $scope.comentarioRecords = response.data;
            });
        } ]);
}
)(angular);

