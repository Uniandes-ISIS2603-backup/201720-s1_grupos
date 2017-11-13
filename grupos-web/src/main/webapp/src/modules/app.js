(function (ng) {
    'use strict';
    var app=angular.module('mainApp', [// External dependencies
        'ui.router',
        'ui.bootstrap',
        // Internal modules dependencies   
        'uploadModule',
        'grupoModule',
        'blogModule',
        'calificacionsModule',
        'categoriaModule',
        'comentarioModule',
        'empresaModule',
        'multimediaModule',
        'noticiasModule',
        'patrocinioModule',
        'tarjetaModule',
        'usuarioModule',
        'eventoModule',
        'lugarModule',
        'loginModule']);
    // Resuelve problemas de las promesas
    app.config(['$qProvider', function ($qProvider) {
            $qProvider.errorOnUnhandledRejections(false);
        }]);
    app.run(['$rootScope', '$transitions', function ($rootScope, $transitions) {

            $transitions.onSuccess({to:'**'}, function (trans) {
                var $state = trans.router.stateService;
                var requireLogin = $state.current.data.requireLogin
                var roles = $state.current.data.roles
                $rootScope.isAuthenticated = function () {
                    if (sessionStorage.getItem("email") !==null && sessionStorage.getItem("email") !==undefined ) {
                        var x = sessionStorage.getItem("nickname");
                        if(x=== null || x===undefined)
                        {
                            $rootScope.currentUser = sessionStorage.getItem("nombreCompleto"); 

                        }
                        else
                        {
                            $rootScope.currentUser = sessionStorage.getItem("nickname"); 

                        }
                        $rootScope.id=sessionStorage.getItem("id");
                        return true;
                    } else {
                        return false;
                    }
                };
                
                $rootScope.hasPermissions = function () {
                    if (($rootScope.isAuthenticated) && (roles.indexOf(sessionStorage.getItem("rol")) > -1)) {
                        return true;
                    } else {
                        return false;
                    }
                };


                if (requireLogin && (sessionStorage.getItem("correo") === null)) {
                    event.preventDefault();
                    $state.go('login', $state.params);
                }

            });

        }]);
})(window.angular);

