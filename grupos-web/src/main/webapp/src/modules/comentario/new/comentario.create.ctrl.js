(function (ng) {
    var mod = ng.module("comentarioModule");
   
    mod.controller('comentarioCreateCtrl', ['$scope', '$http', 'comentarioContext', 'blogContext', 'grupoContext', '$state',
        function ($scope, $http, comentarioContext, blogContext, grupoContext, $state) {
            
            $scope.createComentario = function () {
                $http.post(grupoContext+'/'+$state.params.grupoId+'/'+blogContext + '/' +
                        $state.params.blogId + '/' + comentarioContext, {
                    autor: 'Sergio',
                    comentario: $scope.comentario
                }).then(function (response) {
                    $state.go('comentarioList', {}, {reload: true});
                });
            };
        } ]);
}
)(angular);
