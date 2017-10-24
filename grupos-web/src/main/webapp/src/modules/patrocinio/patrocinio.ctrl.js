(function (ng) {
    var mod = ng.module("patrocinioModule");
    mod.constant("patrocinioContext", "Stark/patrocinios");
   
    mod.controller('patrocinioCtrl', ['$scope', '$http', 'patrocinioContext', '$state',
        function ($scope, $http, patrocinioContext, $state) {} ]);

}
)(angular);