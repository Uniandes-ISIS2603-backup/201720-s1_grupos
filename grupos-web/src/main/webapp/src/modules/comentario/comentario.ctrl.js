(function (ng) {
    var mod = ng.module("comentarioModule");
   
    mod.controller('comentarioCtrl', ['$scope', '$http', 'comentarioContext', 'blogContext', 'grupoContext', '$state',
        function ($scope, $http, comentarioContext, blogContext, grupoContext, $state) {
            $scope.comentariosDeBlog = true;
            $scope.comentariosDeNoticia = false;
            $http.get(grupoContext + '/' + $state.params.grupoId + '/' + blogContext + '/' + 
                    $state.params.blogId + '/' + comentarioContext).then(function (response) {
                $scope.comentarioRecords = response.data;
            });
            
            $scope.crearComentario = function () {
                $http.post(grupoContext+'/'+$state.params.grupoId+'/'+blogContext + '/' +
                        $state.params.blogId + '/' + comentarioContext, {
                    autor: 'Sergio',
                    comentario: $scope.comentario
                }).then(function (response) {
                    $state.reload();
                });
            };
        } ]);
}
)(angular);

