(function (ng) {
    var mod = ng.module("grupoModule");
    mod.constant("grupoContext", "Stark/grupos");
    mod.constant("categoriaContext", "Stark/categorias");
    
    mod.controller('grupoCtrl', ['$scope', '$http', 'grupoContext', 'categoriaContext', '$state', '$filter',
        function ($scope, $http, grupoContext,categoriaContext, $state, $filter) {
            $scope.deGrupo=true;
            $scope.categoriasDeMiGrupo= function(idGrupo)
            {
                $http.get(grupoContext +'/'+ idGrupo+ '/categorias' ).then(function (response) {                    
                    $scope.categoriaRecords = response.data;
                    $state.go('categoriasDeGrupo',{},{});
                },function(error)
                {
                    
                })
            };
            $scope.asociarCategorias= function(){             
                $scope.categoriasQueNoTengo($scope.categoriaRecords);
                console.log( $scope.categoriaRecords);
                $state.go('asociarCategorias',{},{reload:false});
            };
            /*
             * Esta función recibe como param los books que tiene el autor para hacer un filtro visual con todos los books que existen.
             * @param {type} books
             * @returns {undefined}
             */
            $scope.categoriasQueNoTengo = function (categoriasDeGrupo) {
                $http.get(categoriaContext).then(function (response) {
                    $scope.todasLasCategorias = response.data;
                    $scope.categoriasDeGrupo=categoriasDeGrupo;
                    var filteredCategorias = $scope.todasLasCategorias.filter(function (todasLasCategorias) {
                        return $scope.categoriasDeGrupo.filter(function (categoriasDeGrupo) {
                            return categoriasDeGrupo.id == todasLasCategorias.id;
                        }).length == 0
                    });
                    
                    $scope.categoriaRecords = filteredCategorias;
                    
                },function(error)
                {
                    console.log(error.data);
                });
            };
            $scope.asociarCategoria= function(idCategoria){
                console.log("id"+ idCategoria);
                $http.post(grupoContext +'/'+$scope.grupoActual.id +'/categorias/' +idCategoria).then(function (response)
                {
                    $scope.categoriaRecords= $scope.categoriaRecords.filter(function( obj ) {
                        return obj.id !== idCategoria;
                    });
                    categoriasQueNoTengo($scope.categoriaRecords);
                    
                },function(error)
                {
                    console.log(error.data);
                });
                
            };
            $scope.desasociarCategoria= function(idCategoria){
                console.log("id "+idCategoria);
                $http.delete(grupoContext +'/'+$scope.grupoActual.id +'/categorias/' +idCategoria).then(function (response)
                {
                    $state.go('categoriasDeGrupo',{},{reload:true});
                },function(error)
                {
                    console.log(error.data);
                });
            };
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