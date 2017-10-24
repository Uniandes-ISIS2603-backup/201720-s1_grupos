(function (ng) {
    var mod = ng.module("blogModule");
   
    mod.controller('blogDeleteCtrl', ['$scope', '$http', 'blogContext', '$state', 'grupoContext', 
        function ($scope, $http, blogContext, $state, grupoContext) {
            
            $scope.deleteBlog = function() {
                $http.delete(grupoContext+'/'+$state.params.grupoId+'/'+blogContext + '/' + $state.params.blogId).then(function (response) {
                    $state.go('blogList', {blogId:response.data.id}, {reload:true});
                });

            };
            
        }
    ]);
}
)(angular);
