/**
 * Contorlador principal de una categoría
 */
(function (ng) {
    var mod = ng.module("categoriaModule");
    mod.constant("categoriaContext", "Stark/categorias");
    mod.controller('categoriaCtrl', ['$scope', '$http', 'categoriaContext', '$state',
        function ($scope, $http, categoriaContext, $state) {
            //Se indica que no se está actualmente en un grupo, para mostrar los botones correspondientes            
            $scope.opcionesGrupo=false;
            $scope.deGrupo=false;
            /**
             * Buscar una categoría por tipo exacto
             * @param tipo, tipo de la categoría exacto a buscar
             */
            $scope.buscarPorTipo = function (nombre) {
                $http.get(categoriaContext + '/tipo?tipo='+ nombre).then(function (response) {
                    $scope.categoriaActual = response.data;
                    $scope.grupoRecords = response.data.grupos;
                    var categoriaId= $scope.categoriaActual.id;
                    $state.go('categoriaDetail',{categoriaId},{reload:true});
                });
            };
            
            /**
             * indica si se pueden editar las categorías.
             * @returns {Boolean} true si se pueden editar las categorías, false de lo contrario.
             */
            $scope.puedoEditarCategorias = function() {
                return sessionStorage.getItem("rol") === 'Administrador';
            };
            
            /**
             * Busca todas las categorías
             */
            $http.get(categoriaContext).then(function (response) {
                $scope.categoriaRecords = response.data;
            });
            //En caso que haya una categoría seleccionada se busca y se guarda su información
            if ($state.params.categoriaId !== undefined) {
                $scope.opcionesGrupo=true;
                $http.get(categoriaContext + '/' + $state.params.categoriaId).then(function (response) {
                    $scope.categoriaActual = response.data;
                    $scope.grupoRecords = response.data.grupos;
                });
            }
        }
    ]);
}
        )(angular);