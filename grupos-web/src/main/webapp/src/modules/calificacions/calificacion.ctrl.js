(function (ng) {
    var mod = ng.module("calificacionModule");
    mod.constant("calificacionContext", "Stark/calificaciones");
   
    mod.controller('calificacionCtrl', ['$scope', '$http', 'calificacionContext', '$state',
        function ($scope, $http, calificacionContext, $state) {} ]);

}
)(angular);

