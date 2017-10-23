(function (ng) {
    var mod = ng.module("usuarioModule");
    mod.constant("usuarioContext", "Stark/usuarios");
   
    mod.controller('usuarioCtrl', ['$scope', '$http', 'usuarioContext', '$state',
        function ($scope, $http, usuarioContext, $state) {} ]);

}
)(angular);