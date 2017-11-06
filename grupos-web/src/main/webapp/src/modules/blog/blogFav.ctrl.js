(function (ng) {
    var mod = ng.module("blogModule");
   
    mod.controller('blogFavCtrl', ['$scope', '$http', 'blogContext', '$state', 'usuarioContext',
        function ($scope, $http, blogContext, $state, usuarioContext) {
            $scope.blogsDeUsuario = true;
            
            $scope.verBlogDetail = function(blog){
                $http.get(usuarioContext+'/'+$state.params.usuarioId+'/'+blogContext + '/' + blog.id).then(function (response) {
                    $state.go('blogDetail', {grupoId: response.data.grupo.id, blogId: blog.id}, {reload: true});
                });
            };
            
            $http.get(usuarioContext+'/'+$state.params.usuarioId+'/'+blogContext).then(function (response) {
                $scope.listBlog = response.data;
            });
            
            //if ($state.params.blogId !== undefined) {
            //    $http.get(grupoContext + '/' + $state.params.grupoId+'/'+blogContext+'/'+$state.params.blogId).then(function (response) {
            //        $scope.blogActual = response.data;
            //    });
            //}
        }
    ]);
}
)(angular);
