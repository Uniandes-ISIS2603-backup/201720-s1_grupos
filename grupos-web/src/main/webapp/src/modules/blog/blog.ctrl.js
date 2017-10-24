(function (ng) {
    var mod = ng.module("blogModule");
   
    mod.controller('blogCtrl', ['$scope', '$http', 'blogContext', '$state', 'grupoContext',
        function ($scope, $http, blogContext, $state, grupoContext) {
            $http.get(grupoContext+'/'+$state.params.grupoId+'/'+blogContext).then(function (response) {
                $scope.listBlog = response.data;
            });
            
            if ($state.params.blogId !== null) {
                $http.get(grupoContext + '/' + $state.params.grupoId+'/'+blogContext+'/'+$state.params.blogId).then(function (response) {
                    $scope.blogActual = response.data;
                });
            }
        }
    ]);
}
)(angular);
