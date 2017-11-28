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
    mod.config(['$stateProvider', function ($stateProvider) {
            //Camino base de los archivos
            var basePath = 'src/modules/calificacions/';
            /**
             * Definición de estados:
             * -calificacions: Estado abstracto de calificaciones.<br>
             * -ERRORCALIFICACION: Estado de errores.<br>
             * -calificacionsList: Listado de las calificaciones.<br>
             * -calificacionCreate: Estado para crear una calificación (Un formulario).
             * -calificacionEdit: Estado paraa editar una calificación.<br>
             * -calificacionDelete: Estado para confirmar si se desea borrar la calificación o no.<br>
             * -calificacionDetail: Información detallada de la calificación.<br>
             */
            $stateProvider.state('calificacions', {
                url: '/calificacions',
                abstract:true,
                parent:'blogDetail',
                views: {
                    'childrenView': {
                        controller: 'calificacionsCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'calificacions.html'
                    }
                },
                data: {
                    requireLogin: false,
                    roles: []
                }
            }).state('ERRORCALIFICACION', {
                url: '/error',
                parent:'calificacions',
                params:{
                    mensaje:null
                },
                views: {
                    'childrenView': {
                        templateUrl: basePath + 'calificacions.error.html'
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
                url: '/create',
                parent:'calificacionsList',
                views: {
                    'detailView': {
                        controller: 'calificacionsCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'calificacions.create.html'
                    }
                }

            }).state('calificacionEdit', {
                url: '/edit/:calificacionId',
                parent:'calificacionsList',

                param: {
                    calificacionId: null
                },
                views: {
                    'detailView': {
                        controller: 'calificacionsCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'calificacions.create.html'
                    }
                }
            }).state('calificacionDelete', {
                url: '/delete/:calificacionId',
                parent:'calificacionsList',
                param: {
                    calificacionId: null
                },
                views: {
                    'detailView': {
                        controller: 'calificacionsCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'calificacion.delete.html'
                    }
                }
            }).state('calificacionDetail',{
                url:'/detail/:calificacionId',
               parent:'calificacionsList',

                param:{
                    calificacionId:null
                },
                views:{
                    'detailView': {
                    controller: 'calificacionsCtrl',
                    controllerAs: 'ctrl',
                    templateUrl: basePath + 'calificacions.detail.html'
                        }
                    }
            });
        }]);

})(window.angular);

