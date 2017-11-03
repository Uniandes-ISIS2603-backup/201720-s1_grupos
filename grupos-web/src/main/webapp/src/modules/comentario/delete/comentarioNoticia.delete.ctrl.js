(function (ng) {
    var mod = ng.module("comentarioModule");
   
    mod.controller('comentarioNoticiaDeleteCtrl', ['$scope', '$http', 'comentarioContext', 'noticiaContext', '$state',
        function ($scope, $http, comentarioContext, noticiaContext, $state) {
            $scope.deleteComentario = function() {
                $http.delete(noticiaContext + '/' + $state.params.noticiaId + '/' + comentarioContext + '/' + 
                        $state.params.comentarioId).then(function (response) {
                    $scope.goComentarioList();
                });
            };
            
        } ]);
}
)(angular);

