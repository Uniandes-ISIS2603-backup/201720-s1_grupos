(function (ng) {
    var mod = ng.module("comentarioModule");
   
    mod.controller('comentarioNoticiaCreateCtrl', ['$scope', '$http', 'comentarioContext', 'noticiaContext', '$state',
        function ($scope, $http, comentarioContext, noticiaContext, $state) {
            
            /**
             * Crea un nuevo comentario
             */
            $scope.createComentario = function () {
                $http.post(noticiaContext+'/'+$state.params.noticiaId+'/'+ comentarioContext, {
                    autor: sessionStorage.getItem("nombreCompleto"),
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
