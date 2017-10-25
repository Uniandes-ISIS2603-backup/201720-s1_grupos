(
        function (ng) {
            var mod = ng.module("eventoModule");
            mod.constant("eventoContext", "Stark/eventos");
            mod.controller('eventoUpdateCtrl', ['$scope', '$http', 'eventosContext', '$state', '$rootScope', '$filter',
                function ($scope, $http, eventosContext, $state, $rootScope, $filter) {
                    $rootScope.edit = true;

                    var idEvento = $state.params.eventoId;

                    $http.get(eventosContext + '/' + idEvento).then(function (response) {
                        var evento = response.data;
                        $scope.eventoNombre = evento.nombre;
                        $scope.eventoFechaInicio = new Date(evento.fechaInicio);
                        $scope.eventoFechaFin = new Date(evento.fechaFin);
                    });
                }
            ]);
        }
)(angular);