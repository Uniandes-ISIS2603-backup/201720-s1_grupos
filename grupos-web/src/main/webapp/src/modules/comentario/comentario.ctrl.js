(function (ng) {
    var mod = ng.module("comentarioModule");
    mod.constant("comentarioContext", "Stark/comentarios");
   
    mod.controller('comentarioCtrl', ['$scope', '$http', 'comentarioContext', '$state',
        function ($scope, $http, comentarioContext, $state) {} ]);

}
)(angular);

