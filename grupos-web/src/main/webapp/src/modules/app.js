(function (ng) {
    var app = angular.module('mainApp', [
        // External dependencies
        'ui.router',
        'ui.bootstrap',
        // Internal modules dependencies       
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
        'usuarioModule'

    ]);
    // Resuelve problemas de las promesas
    app.config(['$qProvider', function ($qProvider) {
            $qProvider.errorOnUnhandledRejections(false);
        }]);
})(window.angular);

