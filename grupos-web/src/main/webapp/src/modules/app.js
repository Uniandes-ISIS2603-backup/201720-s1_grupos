(function (ng) {
    var app = angular.module('mainApp', [
        // External dependencies
        'ui.router',
        'ui.bootstrap',
        // Internal modules dependencies       
        'grupoModule',
        'blogModule',
        'calificacionModule',
        'categoriaModule',
        'comentarioModule',
        'empresaModule',
        'multimediaModule',
        'noticiaModule',
        'patrocinioModule',
        'tarjetaModule',
        'usuarioModule',
        'eventoModule',
        'lugarModule'

    ]);
    // Resuelve problemas de las promesas
    app.config(['$qProvider', function ($qProvider) {
            $qProvider.errorOnUnhandledRejections(false);
        }]);
})(window.angular);

