/**
Contorlador principal de un grupo y algunos de sus subrecursos
 */
(function (ng) {
    var mod = ng.module("grupoModule");
    //Constantes para la ruta de grupo y de la categoría
    mod.constant("grupoContext", "Stark/grupos");
    mod.constant("categoriaContext", "Stark/categorias");
    mod.constant("usuarioContext", "Stark/usuarios");
    //Declaración del controlador
    mod.controller('grupoCtrl', ['$scope', '$http', 'grupoContext', 'categoriaContext','usuarioContext', '$state', '$filter', '$rootScope',
        function ($scope, $http, grupoContext,categoriaContext,usuarioContext,$state, $filter, $rootScope) {
            $rootScope.edit = false;
            $scope.soyMiembro = false;
            //Indica el usuario logeado actual
            $scope.idUsuarioActual=sessionStorage.getItem("id");
            //Booleano que indica que actualmente se está en el controlador de grupo
            $scope.deGrupo=true;     
            /**
             * Inscribe al usuario actual como miembro del grupo
             */
            $scope.inscripcionGrupo = function () {
                $http.post(grupoContext +'/'+$scope.grupoActual.id +'/miembros/' +$scope.idUsuarioActual).then(function (response)
                {
                    var idGrupo=$scope.grupoActual.id;
                    //Se recarga en caso que funcione
                    $state.go('grupoDetail',{},{reload:true});
                    
                },function(error)
                {
                });
            };
            /**
             * Inscribe al usuario actual como miembro del grupo
             */
            $scope.dejarGrupo = function () {
                $http.delete(grupoContext +'/'+$scope.grupoActual.id +'/miembros/' +$scope.idUsuarioActual).then(function (response)               
                {
                    var idGrupo=$scope.grupoActual.id;
                    //Se recarga en caso que funcione
                    $state.go('grupoDetail',{idGrupo},{reload:true});
                },function(error)
                {
                });
            };
            /**
             * Devuelve los administradores del grupo actual
             * @param {type} idGrupo, id del grupo del que se van a obtener todos sus administradores
             */
            $scope.adminsDeMiGrupo= function(idGrupo)
            {
                $http.get(grupoContext +'/'+ idGrupo+ '/administradores' ).then(function (response) {                    
                    $scope.adminRecords = response.data;
                    $state.go('adminsDeGrupo',{},{});
                },function(error)
                {
                    
                });
            };
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
                    
                });
            };
            /**
             * Busca los usuarios que un grupo no tiene como administradoresy va al estado que los muestra
             */
            $scope.asociarAdmins= function(){             
                $scope.adminsQueNoTengo($scope.adminRecords);
                $state.go('asociarAdmins',{},{reload:false});
            };
            /*
             * Esta función recibe como param los usuarios que tiene el grupo como administradores para hacer un filtro visual con todos los usuarios que existen.
             * @param {type} adminsDeGrupo, administradores que el grupo posee
             */
            $scope.adminsQueNoTengo = function (adminsDeGrupo) {
                $http.get(usuarioContext).then(function (response) {
                    $scope.todosLosAdmins = response.data;                  
                    $scope.adminsDeGrupo=adminsDeGrupo;
                    
                    //Se hace el filtro visual dependiendo de todas las que existen
                    var filteredAdmins= $scope.todosLosAdmins.filter(function (todosLosAdmins) {
                        return $scope.adminsDeGrupo.filter(function (adminsDeGrupo) {
                            return adminsDeGrupo.id === todosLosAdmins.id;
                        }).length === 0;
                    });
                    //Las categorías filtradas se mostrarán
                    $scope.adminRecords = filteredAdmins;
                },function(error)
                {
                });
            };
            /**
             * Asocia el usuario dada con el grupo actual como administrador
             * @param {type} idAdmin, id de la categoría a asociar
             */
            $scope.asociarAdmin= function(idAdmin){
                $http.post(grupoContext +'/'+$scope.grupoActual.id +'/administradores/' +idAdmin).then(function (response)
                {
                    //Quita el administrador recién asociado para que no se muestre
                    $scope.adminRecords= $scope.adminRecords.filter(function( obj ) {
                        return obj.id !== idAdmin;
                    });
                    //Vuelve a buscar los que no se tienen para mostrarlas
                    adminsQueNoTengo($scope.adminRecords);
                    
                },function(error)
                {
                });
                
            };
            /**
             * Desasocia un administrador, ya no hace parte del grupo
             * @param {type} idAdmin, id del administrador a desasociar
             */
            $scope.desasociarAdmin= function(idAdmin){
                $http.delete(grupoContext +'/'+$scope.grupoActual.id +'/administradores/' +idAdmin).then(function (response)
                {
                    //Se recarga en caso que funcione
                    $state.go('adminsDeGrupo',{},{reload:true});
                },function(error)
                {
                });
            };
            /**
             * Busca las categorías que un grupo no tiene y va al estado que los muestra
             */
            $scope.asociarCategorias= function(){             
                $scope.categoriasQueNoTengo($scope.categoriaRecords);
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
                            return categoriasDeGrupo.id === todasLasCategorias.id;
                        }).length === 0;
                    });
                    //Las categorías filtradas se mostrarán
                    $scope.categoriaRecords = filteredCategorias;
                    
                },function(error)
                {
                });
            };
            /**
             * Asocia la categoría dada con el grupo actual
             * @param {type} idCategoria, id de la categoría a asociar
             */
            $scope.asociarCategoria= function(idCategoria){
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
                });
                
            };
            /**
             * Desasocia una categoría, ya no hace parte del grupo
             * @param {type} idCategoria, id de la categoría a desasociar
             */
            $scope.desasociarCategoria= function(idCategoria){
                $http.delete(grupoContext +'/'+$scope.grupoActual.id +'/categorias/' +idCategoria).then(function (response)
                {
                    //Se recarga en caso que funcione
                    $state.go('categoriasDeGrupo',{},{reload:true});
                },function(error)
                {
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
                    $scope.adminRecords=response.data.administradores;
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
            var soyMiembro= false;
            if ($state.params.grupoId !== undefined) {
                $http.get(grupoContext + '/' + $state.params.grupoId).then(function (response) {
                    $scope.grupoActual = response.data;
                    $scope.categoriaRecords=response.data.categorias;
                    $scope.miembroRecords=response.data.miembros;
                    $scope.adminRecords=response.data.administradores;
                    $scope.usuariosRecords=response.data.administradores;
                    $scope.eventosRecords=response.data.eventosGrupo;
                    $scope.records=response.data.noticias;
                    
                    for(var i = 0; i < $scope.miembroRecords.length; i++) {
                        if ($scope.miembroRecords[i].id == $scope.idUsuarioActual) {
                            soyMiembro = true;
                            break;
                        }
                    }
                    $scope.soyMiembro=soyMiembro;
                });
            }
        }
    ]);
}
)(angular);