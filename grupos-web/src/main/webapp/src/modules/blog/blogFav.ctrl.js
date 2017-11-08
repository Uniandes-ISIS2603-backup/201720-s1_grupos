(function (ng) {
    var mod = ng.module("blogModule");
   
    mod.controller('blogFavCtrl', ['$scope', '$http', 'blogContext', '$state', 'usuarioContext',
        function ($scope, $http, blogContext, $state, usuarioContext) {
            //indica que la lista de blogs se accede desde un usuario
            $scope.blogsDeUsuario = true;
            
            /**
             * Se encarga de dirigirse al estado detail del blog que llega por parámetro
             */
            $scope.verBlogDetail = function(blog){
                $http.get(usuarioContext+'/'+$state.params.usuarioId+'/'+blogContext + '/' + blog.id).then(function (response) {
                    $state.go('blogDetail', {grupoId: response.data.grupo.id, blogId: blog.id}, {reload: true});
                }, function (error) {
                    $state.go('blogUsuarioError', {mensaje: error.data}, {reload: true});
                });
            };
            
            //se consigue la lista de blogs favoritos
            $http.get(usuarioContext+'/'+$state.params.usuarioId+'/'+blogContext).then(function (response) {
                $scope.listBlog = response.data;
            }, function (error) {
                $state.go('blogUsuarioError', {mensaje: error.data}, {reload: true});
            });
        }
    ]);
}
)(angular);
