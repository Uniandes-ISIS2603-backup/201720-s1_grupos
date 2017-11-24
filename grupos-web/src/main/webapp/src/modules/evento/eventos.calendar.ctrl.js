(function (ng) {
    var mod = ng.module("eventoModule");
    mod.controller('eventosCalendarCtrl', ['$scope', '$http', 'eventoContext','$state',
        function ($scope, $http, eventoContext,$state, moment, alert, calendarConfig) {
            var vm = this;
            
            //These variables MUST be set as a minimum for the calendar to work
            vm.calendarView = 'month';
            vm.viewDate = new Date();
            var actions = [{
                label: '<i class=\'glyphicon glyphicon-pencil\'></i>',
                onClick: function(args) {
                  alert.show('Edited', args.calendarEvent);
                }
              }, {
                label: '<i class=\'glyphicon glyphicon-remove\'></i>',
                onClick: function(args) {
                  alert.show('Deleted', args.calendarEvent);
                }
              }];
            vm.events = $scope.records;
            
            for(var i = 0; i<vm.events.length; i++) {
                var event = vm.events[i];
                event.title = event.nombre;
                event.startsAt = new Date(event.fechaInicio);
                event.endsAt = new Date(event.fechaFin);
                
                event.actions = actions;
            }
            vm.cellIsOpen = true;

            vm.addEvent = function() {
              vm.events.push({
                title: 'New event',
                startsAt: moment().startOf('day').toDate(),
                endsAt: moment().endOf('day').toDate(),
                resizable: true
              });
            };



                }
            ]);
}
)(angular);

