(function (ng) {
    var mod = ng.module("blogModule");
   
    mod.controller('blogCtrl', ['$scope', '$http', 'blogContext', 'grupoContext',
        function ($scope, $http, blogContext, grupoContext) {
            $http.get(grupoContext+'/10/'+blogContext).then(function (response) {
                $scope.listBlog = response.data;
            });
            
        }
    ]);
}
)(angular);
