(
        function (ng) {
            var mod = ng.module("eventoModule");
            mod.constant("eventoContext", "Stark/eventos");
            mod.controller('eventoUpdateCtrl', ['$scope', '$http', 'eventoContext', '$state', '$rootScope', '$filter',
                function ($scope, $http, eventoContext, $state) {

                    var idEvento = $state.params.eventoId;

                    $http.get(eventoContext + '/' + idEvento).then(function (response) {
                        var evento = response.data;
                        $scope.eventoNombre = evento.nombre;
                        $scope.eventoFechaInicio = new Date(evento.fechaInicio);
                        $scope.eventoFechaFin = new Date(evento.fechaFin);
                    });
                    $scope.actualizarEvento = function()
                    {
                   $http.put(eventoContext + "/" + idEvento,{                 
                nombre :$scope.eventoNombre,
                fechaFin :$scope.eventoFechaFin,
                fechaInicio :$scope.eventoFechaInicio              
                   }).then(function (response){
                       $state.go('eventosList', {eventoId: response.data.id}, {reload: true});
                   });
                    };
                }
            ]);
        }
)(angular);