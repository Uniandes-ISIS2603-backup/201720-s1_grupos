(function (ng) {
    var mod = ng.module("grupoModule");
    mod.constant("grupoContext", "Stark/grupos");
    
    mod.controller('grupoCtrl', ['$scope', '$http', 'grupoContext', '$state',
        function ($scope, $http, grupoContext, $state) {
            $scope.opcionesGrupo=false;
            $scope.listaMiembros = function () {
                $state.go('usuariosList');
                $scope.usuariosRecords=miembroRecords;
            };
            $scope.listaAdmins = function () {
                $state.go('usuariosList');
                $scope.usuariosRecords=adminRecords;
            };
            $http.get(grupoContext).then(function (response) {
                $scope.grupoRecords = response.data;
            });
            
            if ($state.params.grupoId !== undefined) {
                $http.get(grupoContext + '/' + $state.params.grupoId).then(function (response) {
                    console.log(response.data);
                    $scope.grupoActual = response.data;
                    $scope.categoriaRecords=response.data.categorias;
                    $scope.miembroRecords=response.data.miembros;
                    $scope.adminRecords=response.data.administradores;
                    $scope.eventosRecords=response.data.eventos;
                });
            }
        }
    ]);
}
        )(angular);