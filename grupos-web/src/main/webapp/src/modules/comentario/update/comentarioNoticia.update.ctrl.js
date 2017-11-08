(function (ng) {
    var mod = ng.module("comentarioModule");
   
    mod.controller('comentarioNoticiaUpdateCtrl', ['$scope', '$http', 'comentarioContext', 'noticiaContext', '$state',
        function ($scope, $http, comentarioContext, noticiaContext, $state) {
            //se obtiene el autor del comentario a modificar
            $http.get(noticiaContext + '/' + $state.params.noticiaId + '/' + comentarioContext + '/' + 
                    $state.params.comentarioId).then(function (response) {
                $scope.autor = response.data.autor;
            }, function (error) {
                $scope.goError();
            });
            
            /**
             * Actualiza el comentario con un nuevo contenido sin cambiar el autor
             */
            $scope.updateComentario = function() {
                $http.put(noticiaContext + '/' + $state.params.noticiaId + '/' + comentarioContext + '/' + 
                        $state.params.comentarioId, {
                            autor: $scope.autor,
                            comentario: $scope.comentario
                }).then(function (response) {
                    $scope.goComentarioList();
                }, function (error) {
                    $scope.goError();
                });
            };
            
        } ]);
}
)(angular);
