/**
Contorlador principal de un grupo y algunos de sus subrecursos
 */
(function (ng) {
    var mod = ng.module("grupoModule");
    //Constantes para la ruta de grupo y de la categoría
    mod.constant("grupoContext", "Stark/grupos");
    mod.constant("categoriaContext", "Stark/categorias");
    //Declaración del controlador
    mod.controller('grupoCtrl', ['$scope', '$http', 'grupoContext', 'categoriaContext', '$state', '$filter',
        function ($scope, $http, grupoContext,categoriaContext, $state, $filter) {
            //Booleano que indica que actualmente se está en el controlador de grupo
            $scope.deGrupo=true;            
            /**
             * Devuelve las categorías del grupo actual
             * @param {type} idGrupo, id del grupo del que se van a obtener todas sus categorías
             */
            $scope.categoriasDeMiGrupo= function(idGrupo)
            {
                $http.get(grupoContext +'/'+ idGrupo+ '/categorias' ).then(function (response) {                    
                    $scope.categoriaRecords = response.data;
                    $state.go('categoriasDeGrupo',{},{});
                },function(error)
                {
                    
                })
            };
            /**
             * Busca las categorías que un grupo no tiene y va al estado que los muestra
             */
            $scope.asociarCategorias= function(){             
                $scope.categoriasQueNoTengo($scope.categoriaRecords);
                console.log( $scope.categoriaRecords);
                $state.go('asociarCategorias',{},{reload:false});
            };
            /*
             * Esta función recibe como param las categorías que tiene el grupo para hacer un filtro visual con todas las categorías que existen.
             * @param {type} categoriasDeGrupo, categorias que el grupo posee
             */
            $scope.categoriasQueNoTengo = function (categoriasDeGrupo) {
                $http.get(categoriaContext).then(function (response) {
                    $scope.todasLasCategorias = response.data;
                    $scope.categoriasDeGrupo=categoriasDeGrupo;
                    //Se hace el filtro visual dependiendo de todas las que existen
                    var filteredCategorias = $scope.todasLasCategorias.filter(function (todasLasCategorias) {
                        return $scope.categoriasDeGrupo.filter(function (categoriasDeGrupo) {
                            return categoriasDeGrupo.id == todasLasCategorias.id;
                        }).length == 0
                    });
                    //Las categorías filtradas se mostrarán
                    $scope.categoriaRecords = filteredCategorias;
                    
                },function(error)
                {
                    console.log(error.data);
                });
            };
            /**
             * Asocia la categoría dada con el grupo actual
             * @param {type} idCategoria, id de la categoría a asociar
             */
            $scope.asociarCategoria= function(idCategoria){
                console.log("id"+ idCategoria);
                $http.post(grupoContext +'/'+$scope.grupoActual.id +'/categorias/' +idCategoria).then(function (response)
                {
                    //Quita la categoría recién asociada para que no se muestre
                    $scope.categoriaRecords= $scope.categoriaRecords.filter(function( obj ) {
                        return obj.id !== idCategoria;
                    });
                    //Vuelve a buscar las que no se tienen para mostrarlas
                    categoriasQueNoTengo($scope.categoriaRecords);
                    
                },function(error)
                {
                    console.log(error.data);
                });
                
            };
            /**
             * Desasocia una categoría, ya no hace parte del grupo
             * @param {type} idCategoria, id de la categoría a desasociar
             */
            $scope.desasociarCategoria= function(idCategoria){
                console.log("id "+idCategoria);
                $http.delete(grupoContext +'/'+$scope.grupoActual.id +'/categorias/' +idCategoria).then(function (response)
                {
                    //Se recarga en caso que funcione
                    $state.go('categoriasDeGrupo',{},{reload:true});
                },function(error)
                {
                    console.log(error.data);
                });
            };
            /**
             * Buscar un grupo por nombre exacto
             * @param {type} nombre, nombre del grupo exacto a buscar
             */
            $scope.buscarPorNombre = function (nombre) {
                $http.get(grupoContext + '/nombre?nombre='+ nombre).then(function (response) {
                    $scope.grupoActual = response.data;
                    $scope.categoriaRecords=response.data.categorias;
                    $scope.miembroRecords=response.data.miembros;
                    $scope.usuariosRecords=response.data.administradores;
                    $scope.eventosRecords=response.data.eventosGrupo;
                    $scope.records=response.data.noticias;
                    var grupoId= $scope.grupoActual.id;
                    $state.go('grupoDetail',{grupoId},{reload:true});
                });
            };
            $http.get(grupoContext).then(function (response) {
                $scope.grupoRecords = response.data;
            });
            //En caso de estar en un grupoDetail, se busca y se guardan sus atributos
            if ($state.params.grupoId !== undefined) {
                $http.get(grupoContext + '/' + $state.params.grupoId).then(function (response) {
                    $scope.grupoActual = response.data;
                    $scope.categoriaRecords=response.data.categorias;
                    $scope.miembroRecords=response.data.miembros;
                    $scope.usuariosRecords=response.data.administradores;
                    $scope.eventosRecords=response.data.eventosGrupo;
                    $scope.records=response.data.noticias;
                });
            }
        }
    ]);
}
        )(angular);