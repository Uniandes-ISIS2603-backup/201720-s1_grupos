(function (ng) {
    //Módulo actual
    var mod = ng.module("empresaModule");
    //Contextos utilizados dentro del controlador
    mod.constant("empresaUsuarioContext", "empresa");
    mod.constant("usuariosContext", "Stark/usuarios");
    mod.controller('empresaNewCtrl', ['$scope', '$http', 'usuariosContext', '$state', 'empresaUsuarioContext', '$rootScope',
        function ($scope, $http, usuariosContext, $state, empresaUsuarioContext, $rootScope) {
            $rootScope.edit = false;
            
            //Búsqueda de las imágenes en la carpeta data
            $http.get("./data/archivos.json").then(function(response)
            {
                $scope.rutaImagenes = response.data;
            });
            //Función utilizada para crear una empresa
            $scope.createEmpresa = function () {
                $http.post(usuariosContext + '/' + $state.params.usuarioId + '/' + empresaUsuarioContext, {
                    nit: $scope.empresaNit,
                    nombre: $scope.empresaNombre,
                    logo: $scope.empresaLogo
                }).then(function () {
                    //Empresa creada exitosamente
                    $state.go('empresaDetail', {reload: true});
                });
            };
        }
    ]);
}
)(angular);


