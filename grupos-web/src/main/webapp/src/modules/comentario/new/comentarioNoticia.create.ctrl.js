(function (ng) {
    var mod = ng.module("comentarioModule");
   
    mod.controller('comentarioNoticiaCreateCtrl', ['$scope', '$http', 'comentarioContext', 'noticiaContext', '$state',
        function ($scope, $http, comentarioContext, noticiaContext, $state) {
            
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
