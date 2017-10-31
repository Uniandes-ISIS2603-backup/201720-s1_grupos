(function (ng) {
    var mod = ng.module("comentarioModule");
   
    mod.controller('comentarioNoticiaCtrl', ['$scope', '$http', 'comentarioContext', 'noticiaContext', '$state',
        function ($scope, $http, comentarioContext, noticiaContext, $state) {
            $scope.comentariosDeBlog = false;
            $scope.comentariosDeNoticia = true;
            
            $http.get(noticiaContext + '/' + $state.params.noticiaId + '/' + comentarioContext).then(function (response) {
                $scope.comentarioRecords = response.data;
            });
            
            $scope.createComentario = function () {
                $http.post(noticiaContext+'/'+$state.params.noticiaId+'/'+ comentarioContext, {
                    autor: 'Sergio',
                    comentario: $scope.comentario
                }).then(function (response) {
                    $state.reload();
                });
            };
        } ]);

}
)(angular);
