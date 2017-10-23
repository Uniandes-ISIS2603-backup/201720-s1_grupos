(function (ng) {
    var mod = ng.module("blogModule");
    mod.constant("blogContext", "Stark/blogs");
   
    mod.controller('blogCtrl', ['$scope', '$http', 'blogContext', '$state',
        function ($scope, $http, blogContext, $state) {} ]);

}
)(angular);