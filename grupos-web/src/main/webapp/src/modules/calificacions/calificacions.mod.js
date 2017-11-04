(function (ng) {
    //Módulo de la aplicación
    var mod = ng.module("calificacionsModule", []);
    //Constante de contexto de grupo
    mod.constant("gruposContext", "Stark/grupos");
    //CONSTANTE DE BLOG
        mod.constant("blogContext", "blogs");
    //Constante de las calificaciones
    mod.constant("calificacionsContext", "calificaciones");
    /**
     * Configuración del módulo. Recibe el $stateProvider y el $urlRouterProvider para definir estados y un estado predefinido respectivamente.
     */
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            //Camino base de los archivos
            var basePath = 'src/modules/calificacions/';
            //Estado predefinido
            $urlRouterProvider.otherwise("/calificacionsList");
            /**
             * Definición de estados:
             * -calificacionsList: Listado de las calificaciones.<br>
             * -calificacionCreate: Estado para crear una calificación (Un formulario).
             * -calificacionEdit: Estado paraa editar una calificación.<br>
             * -calificacionDelete: Estado para confirmar si se desea borrar la calificación o no.<br>
             * -calificacionDetail: Información detallada de la calificación.<br>
             */
            $stateProvider.state('404', {
                url: '/calificacion404',
                parent:'blogDetail',
                views: {
                    'childrenView': {
                        templateUrl: basePath + 'calificacions.404.html'
                    }
                }
            }).state('calificacionsList', {
                url: '/calificacions',
                parent:'blogDetail',
                views: {
                    'childrenView': {
                        controller: 'calificacionsCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'calificacions.list.html'
                    }
                }
            }).state('calificacionCreate', {
                url: '/calificacions/create',
                parent:'blogDetail',
                views: {
                    'childrenView': {
                        controller: 'calificacionsCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'calificacions.create.html'
                    }
                }

            }).state('calificacionEdit', {
                url: '/calificacions/:calificacionId',
                parent:'blogDetail',

                param: {
                    calificacionId: null
                },
                views: {
                    'childrenView': {
                        controller: 'calificacionsCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'calificacions.create.html'
                    }
                }
            }).state('calificacionDelete', {
                url: '/calificacions/delete/:calificacionId',
                parent:'blogDetail',
                param: {
                    calificacionId: null
                },
                views: {
                    'childrenView': {
                        controller: 'calificacionsCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'calificacion.delete.html'
                    }
                }
            }).state('calificacionDetail',{
                url:'/calificacions/:calificacionId/detail',
               parent:'blogDetail',

                param:{
                    calificacionId:null
                },
                views:{
                        'childrenView': {
                        controller: 'calificacionsCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'calificacions.detail.html'
                            }
                        }
            });
        }]);

})(window.angular);

