(function (ng) {
    var mod = ng.module("multimediaModule");
    mod.constant("multimediaContext", "Stark/multimedias");
   
    mod.controller('multimediaCtrl', ['$scope', '$http', 'multimediaContext', '$state',
        function ($scope, $http, multimediaContext, $state) {} ]);

}
)(angular);