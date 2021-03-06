(function (ng) {
    var mod = ng.module("blogModule");
   
    mod.controller('blogDeleteCtrl', ['$scope', '$http', 'blogContext', '$state', 'grupoContext', 
        function ($scope, $http, blogContext, $state, grupoContext) {
            /**
             * Elimina el blog del grupo
             */
            $scope.deleteBlog = function() {
                $http.delete(grupoContext+'/'+$state.params.grupoId+'/'+blogContext + '/' + $state.params.blogId).then(function (response) {
                    $state.go('blogList', {blogId:response.data.id}, {reload:true});
                }, function (error) {
                    $state.go('blogGrupoError', {mensaje: error.data}, {reload: true});
                });

            };
            
        }
    ]);
}
)(angular);
