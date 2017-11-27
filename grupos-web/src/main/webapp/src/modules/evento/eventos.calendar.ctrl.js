(function (ng) {
    var mod = ng.module("eventoModule");
    mod.controller('eventosCalendarCtrl', ['$scope', '$http', 'eventoContext','$state',
        function ($scope, $http, eventoContext,$state, moment, alert, calendarConfig) {
            
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
            
            $scope.eventClicked = function(event) {
                $state.go('eventoCalendarDetail', {eventoId: event.id}, {});
            };



                }
            ]);
}
)(angular);

