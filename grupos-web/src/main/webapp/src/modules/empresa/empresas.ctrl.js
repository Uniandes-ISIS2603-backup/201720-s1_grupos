(function (ng) {
    var mod = ng.module("empresaModule");
    mod.constant("empresaContext", "Stark/empresas");
   
    mod.controller('empresaCtrl', ['$scope', '$http', 'empresaContext', '$state',
        function ($scope, $http, empresaContext, $state) {} ]);

}
)(angular);