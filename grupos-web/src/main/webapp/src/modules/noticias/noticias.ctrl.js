(function (ng) {
    var mod = ng.module("noticiaModule");
    mod.constant("noticiaContext", "Stark/noticias");
   
    mod.controller('noticiaCtrl', ['$scope', '$http', 'noticiaContext', '$state',
        function ($scope, $http, noticiaContext, $state) {} ]);

}
)(angular);