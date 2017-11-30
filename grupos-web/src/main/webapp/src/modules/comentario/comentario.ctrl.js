(function (ng) {
    var mod = ng.module("comentarioModule");
   
    mod.controller('comentarioCtrl', ['$scope', '$http', 'comentarioContext', 'blogContext', 'grupoContext', '$state',
        function ($scope, $http, comentarioContext, blogContext, grupoContext, $state) {
            //variables para mostrar botones en el template
            $scope.comentariosBlog = true;
            $scope.comentariosNoticia = false;
            $scope.comentariosUsuarioNoticia = false;
            $scope.comentariosGrupoNoticia = false;
            
            /**
             * Va al estado donde se encuentra la lista de comentario
             */
            $scope.goComentarioList = function () {
                $state.go('comentarioList', {}, {reload: true});
            };
            
            /**
             * Va al estado donde se encuentra el detail de la publicación
             */
            $scope.goPublicacionDetail = function () {
                $state.go('blogDetail', {}, {reload: true});
            };
            
            /**
             * Va a la´página de error
             */
            $scope.goError = function() {
                $state.go('comentarioError', {mensaje: error.data}, {reload: true});
            };
            
            /**
             * verifica si el nick del usuario del comentario es el nick del usuario logueado.
             * @param {String} nick del usuario del comentario
             * @returns {Boolean} true si es el mismo, false si son distintos.
             */
            $scope.esMiComentario = function(nick) {
                return sessionStorage.getItem("nickname") === nick;
            };
            
            /**
             * indica si se puede comentar o no.
             * @returns {Boolean}
             */
            $scope.puedoComentar = function() {
                return $scope.puedoEditarContenidoGrupo();
            };
            
            //se obtiene la lista de comentarios.
            $http.get(grupoContext + '/' + $state.params.grupoId + '/' + blogContext + '/' + 
                    $state.params.blogId + '/' + comentarioContext).then(function (response) {
                $scope.comentarioRecords = response.data;
            }, function () {
                $scope.goError();
            });
        } ]);
}
)(angular);

