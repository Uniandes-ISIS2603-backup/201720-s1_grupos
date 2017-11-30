(function (ng) {
    var mod = ng.module("comentarioModule");
   
    mod.controller('comentarioDeleteCtrl', ['$scope', '$http', 'comentarioContext', 'blogContext', 'grupoContext', '$state',
        function ($scope, $http, comentarioContext, blogContext, grupoContext, $state) {
            /**
             * Elimina el comentario actual.
             */
            $scope.deleteComentario = function() {
                $http.delete(grupoContext + '/' + $state.params.grupoId + '/' + blogContext + '/' + 
                    $state.params.blogId + '/' + comentarioContext + '/' + $state.params.comentarioId).then(function () {
                    $scope.goComentarioList();
                }, function () {
                    $scope.goError();
                });
            };
            
        } ]);
}
)(angular);
