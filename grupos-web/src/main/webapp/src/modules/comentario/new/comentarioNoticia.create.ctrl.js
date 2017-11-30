(function (ng) {
    var mod = ng.module("comentarioModule");
   
    mod.controller('comentarioNoticiaCreateCtrl', ['$scope', '$http', 'comentarioContext', 'noticiaContext', '$state',
        function ($scope, $http, comentarioContext, noticiaContext, $state) {
            
            /**
             * Crea un nuevo comentario
             */
            $scope.createComentario = function () {
                $http.post(noticiaContext+'/'+$state.params.noticiaId+'/'+ comentarioContext, {
                    autor: sessionStorage.getItem("nickname"),
                    comentario: $scope.comentario
                }).then(function () {
                    $scope.goComentarioList();
                }, function () {
                    $scope.goError();
                });
            };
        } ]);
}
)(angular);
