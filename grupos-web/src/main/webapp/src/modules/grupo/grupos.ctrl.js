(function (ng) {
    var mod = ng.module("grupoModule");
    mod.constant("grupoContext", "Stark/grupos");
    
    mod.controller('grupoCtrl', ['$scope', '$http', 'grupoContext', '$state',
        function ($scope, $http, grupoContext, $state) {
            $scope.deGrupo=true;
            $scope.buscarPorNombre = function (nombre) {
                $http.get(grupoContext + '/nombre?nombre='+ nombre).then(function (response) {
                    $scope.grupoActual = response.data;
                    $scope.categoriaRecords=response.data.categorias;
                    $scope.miembroRecords=response.data.miembros;
                    $scope.usuariosRecords=response.data.administradores;
                    $scope.eventosRecords=response.data.eventos;
                    $scope.records=response.data.noticias;
                    var grupoId= $scope.grupoActual.id;
                    $state.go('grupoDetail',{grupoId},{reload:true});
                });
            };
            $http.get(grupoContext).then(function (response) {
                $scope.grupoRecords = response.data;
            });
            
            if ($state.params.grupoId !== undefined) {
                $http.get(grupoContext + '/' + $state.params.grupoId).then(function (response) {

                    $scope.grupoActual = response.data;
                    $scope.categoriaRecords=response.data.categorias;
                    $scope.miembroRecords=response.data.miembros;
                    $scope.usuariosRecords=response.data.administradores;
                    $scope.eventosRecords=response.data.eventos;
                    $scope.records=response.data.noticias;
                });
            }
        }
    ]);
}
        )(angular);