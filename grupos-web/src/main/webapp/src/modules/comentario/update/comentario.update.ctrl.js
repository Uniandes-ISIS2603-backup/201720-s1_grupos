(function (ng) {
    var mod = ng.module("comentarioModule");
   
    mod.controller('comentarioUpdateCtrl', ['$scope', '$http', 'comentarioContext', 'blogContext', 'grupoContext', '$state',
        function ($scope, $http, comentarioContext, blogContext, grupoContext, $state) {
            $http.get(grupoContext + '/' + $state.params.grupoId + '/' + blogContext + '/' + $state.params.blogId + 
                    '/' + comentarioContext + '/' + $state.params.comentarioId).then(function (response) {
                $scope.autor = response.data.autor;
            });
            
            $scope.updateComentario = function() {
                $http.put(grupoContext + '/' + $state.params.grupoId + '/' + blogContext + '/' + 
                    $state.params.blogId + '/' + comentarioContext + '/' + $state.params.comentarioId, {
                        autor: $scope.autor,
                        comentario: $scope.comentario
                }).then(function (response) {
                    $scope.goComentarioList();
                });
            };
            
        } ]);
}
)(angular);
