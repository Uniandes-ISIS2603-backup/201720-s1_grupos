(function (ng) {
    var mod = ng.module("comentarioModule");
   
    mod.controller('comentarioGrupoNoticiaCtrl', ['$scope', '$http', 'comentarioContext', 'noticiaContext', '$state',
        function ($scope, $http, comentarioContext, noticiaContext, $state) {
            //variables para mostrar botones en el template
            $scope.comentariosBlog = false;
            $scope.comentariosNoticia = false;
            $scope.comentariosUsuarioNoticia = false;
            $scope.comentariosGrupoNoticia = true;
            
            /**
             * Va al estado donde se encuentra la lista de comentario
             */
            $scope.goComentarioList = function () {
                $state.go('comentarioGrupoNoticiaList', {}, {reload: true});
            };
            
            /**
             * Va al estado donde se encuentra el detail de la publicación
             */
            $scope.goPublicacionDetail = function () {
                $state.go('grupoNoticiaDetail', {}, {reload: true});
            };
            
            /**
             * Va a la´página de error
             */
            $scope.goError = function() {
                $state.go('comentarioGrupoNoticiaError', {mensaje: error.data}, {reload: true});
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
