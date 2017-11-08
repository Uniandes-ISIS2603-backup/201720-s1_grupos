(function (ng) {
    var mod = ng.module("comentarioModule");
   
    mod.controller('comentarioNoticiaCtrl', ['$scope', '$http', 'comentarioContext', 'noticiaContext', '$state',
        function ($scope, $http, comentarioContext, noticiaContext, $state) {
            //variables para mostrar botones en el template
            $scope.comentariosBlog = false;
            $scope.comentariosNoticia = true;
            $scope.comentariosUsuarioNoticia = false;
            $scope.comentariosGrupoNoticia = false;
            
            /**
             * Va al estado donde se encuentra la lista de comentario
             */
            $scope.goComentarioList = function () {
                $state.go('comentarioNoticiaList', {}, {reload: true});
            };
            
            /**
             * Va al estado donde se encuentra el detail de la publicación
             */
            $scope.goPublicacionDetail = function () {
                $state.go('noticiaNoEditableDetail', {}, {reload: true});
            };
            
            /**
             * Va a la´página de error
             */
            $scope.goError = function() {
                $state.go('comentarioNoticiaError', {mensaje: error.data}, {reload: true});
            };
            
            //se obtiene la lista de comentarios.
            $http.get(noticiaContext + '/' + $state.params.noticiaId + '/' + comentarioContext).then(function (response) {
                $scope.comentarioRecords = response.data;
            }, function (error) {
                $scope.goError();
            });
            
        } ]);

}
)(angular);
