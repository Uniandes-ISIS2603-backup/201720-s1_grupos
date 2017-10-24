(function (ng) {
    var mod = ng.module("tarjetaModule");
    mod.constant("tarjetaContext", "Stark/tarjetas");
   
    mod.controller('tarjetaCtrl', ['$scope', '$http', 'tarjetaContext', '$state',
        function ($scope, $http, tarjetaContext, $state) {} ]);

}
)(angular);