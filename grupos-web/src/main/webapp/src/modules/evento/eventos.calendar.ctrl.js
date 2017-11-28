(function (ng) {
    var mod = ng.module("eventoModule");
    mod.controller('eventosCalendarCtrl', ['$scope', '$http', 'usuariosContext','$state',
        function ($scope, $http, usuariosContext,$state, moment, alert, calendarConfig) {
            
            //Verifica si es usuario de calendario o no
            if ($state.params.usuarioId !== undefined) {
                $http.get(usuariosContext + '/' + $scope.idUsuarioActual).then(function (response) {
                    $scope.usuarioActual = response.data;
                    $scope.records = $scope.usuarioActual.eventos;
                    $scope.eventoUsuario = true;
                });
            }
            //These variables MUST be set as a minimum for the calendar to work
            $scope.calendarView = 'month';
            $scope.viewDate = new Date();
            var actions = [];
            $scope.events = $scope.records;
            for(var i = 0; i<$scope.events.length; i++) {
                var event = $scope.events[i];
                event.title = event.nombre;
                event.startsAt = new Date(event.fechaInicio);
                event.endsAt = new Date(event.fechaFin);
                
                event.actions = actions;
            }
            $scope.cellIsOpen = true;


            $scope.addEventCalendar = function() {
              
            };
            if($state.params.grupoId !== undefined){
                $scope.eventClicked = function(event) {
                    $state.go('eventoCalendarDetail', {eventoId: event.id}, {});
                };
            }
            else if($state.params.usuarioId !== undefined){
                $scope.eventClicked = function(event) {
                    $state.go('eventoCalendarDetailUsuario', {eventoId: event.id}, {});
                };
            }
            else {
            $scope.eventClicked = function(event) {
                $state.go('eventoCalendarDetailAdmin', {eventoId: event.id}, {});
            };
        }


        }
    ]);
}
        )(angular);

