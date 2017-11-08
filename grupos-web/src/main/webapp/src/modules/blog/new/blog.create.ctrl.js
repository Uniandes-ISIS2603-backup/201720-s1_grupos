(function (ng) {
    var mod = ng.module("blogModule");
   
    mod.controller('blogCreateCtrl', ['$scope', '$http', 'blogContext', '$state', 'grupoContext', '$rootScope',
        function ($scope, $http, blogContext, $state, grupoContext, $rootScope) {
            var controlBlog=this;
            
            $rootScope.edit=false;
            $scope.crearBlog=true;
            $scope.actualizarBlog=false;
            //Inicialización de elementos multimedia a agregar a la noticia.
            $scope.multimedia=[];
            //Items para agregar
            $scope.itemsToAdd=[{nombre:' ',descripcion:' ',link:' '}];
            /**
             * Agrega un elemento a la lista por añadir en el post.<br>
             * @param {type} itemToAdd Item por añadir.<br>
             */
            this.add=function(itemToAdd){
                itemToAdd.link=this.randomString();
                var index=$scope.itemsToAdd.indexOf(itemToAdd);
                $scope.itemsToAdd.splice(index,1);
                $scope.multimedia.push(angular.copy(itemToAdd));
            };
            /**
             * Agrega un nuevo item pendiente.<br>
             */
            this.addNew=function(){
                $scope.itemsToAdd.push({nombre:' ',descripcion:' ',link:' '});
            };
            /**
             * Agrega todos los elementos de la lista de POST.
             */
            this.addAll=function()
            {
                while($scope.itemsToAdd.length!==0)
                {
                    this.add($scope.itemsToAdd[0]);
                }
            };
            /**
             * Remueve un item de los pendientes.<br>
             * @param {type} itemToAdd
             */
            this.remove=function(itemToAdd)
            {
                var index=$scope.itemsToAdd.indexOf(itemToAdd);
                $scope.itemsToAdd.splice(index,1);
            };
            /**
             * Retorna un string aleatorio como link formado.<br>
             * @return link aleatorio
             */
            this.randomString= function()
            {
                var text="";
              var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
                   for (var i = 0; i < 5; i++)
                 text += possible.charAt(Math.floor(Math.random() * possible.length));
                 console.log("TEXTO "+text);
                return text;  
            };
            
            /**
             * Se crea el blog con un post.
             */
            $scope.createBlog = function() {
                controlBlog.addAll();
                $http.post(grupoContext+'/'+$state.params.grupoId+'/'+blogContext, {
                    titulo: $scope.tituloBlog,
                    contenido: $scope.contenidoBlog,
                    multimedia: $scope.multimedia
                }).then(function (response) {
                    $state.go('blogList', {blogId:response.data.id}, {reload:true});
                }, function (error) {
                    $state.go('blogGrupoError', {mensaje: error.data}, {reload: true});
                });

            };
            
        }
    ]);
}
)(angular);
