(function (ng) {
    var mod = ng.module("lugarModule");
    //constante para los lugares generales.
    mod.constant("lugaresContext", "Stark/lugares");
    //constantes para los lugares vinculados con los eventos.
    mod.constant("lugarEventoContext","Stark/eventos");
    mod.constant("lugarContext", "lugar");
    //declaracion del controlador
    mod.controller('lugarCtrl', ['$scope', '$http', 'lugaresContext','lugarEventoContext','lugarContext','$state',
        function ($scope, $http, lugaresContext,lugarEventoContext,lugarContext,$state) {

            // http para obtener todos los lugares de la aplicacion.
            $http.get(lugaresContext).then(function (response) {
                $scope.lugaresRecords = response.data;
            });
            //http para obtener un lugar de la aplicacion.
            if ($state.params.lugarId !== undefined) {
                $http.get(lugaresContext + '/' + $state.params.lugarId).then(function (response) {
                    $scope.currentLugar = response.data;
                });
            }
            //http para obtener el lugar del evento definido por parametro.
            if($state.params.eventoId !== undefined) {
                $http.get(lugarEventoContext + '/' + $state.params.eventoId+"/"+lugarContext).then(function (response) {
                    $scope.currentLugar = response.data;
                }); 
            }
            
        }
    ]);
}
)(angular);
