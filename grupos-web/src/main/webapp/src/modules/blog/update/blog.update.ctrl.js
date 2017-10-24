(function (ng) {
    var mod = ng.module("blogModule");
   
    mod.controller('blogUpdateCtrl', ['$scope', '$http', 'blogContext', '$state', 'grupoContext', '$rootScope',
        function ($scope, $http, blogContext, $state, grupoContext) {
            $scope.createBlog = function() {
                $http.post(grupoContext+'/'+$state.params.grupoId+'/'+blogContext, {
                    titulo: $scope.tituloBlog,
                    contenido: $scope.contenidoBlog
                }).then(function (response) {
                    $state.go('blogList', {blogId:response.data.id}, {reload:true});
                });

            };
            
        }
    ]);
}
)(angular);

