(function (ng) {
    var mod = ng.module("blogModule");
   
    mod.controller('blogCreateCtrl', ['$scope', '$http', 'blogContext', '$state', 'grupoContext', '$rootScope',
        function ($scope, $http, blogContext, $state, grupoContext, $rootScope) {
            var controlBlog=this;
            
            $rootScope.edit=false;
            $scope.crearBlog=true;
            $scope.actualizarBlog=false;
            //Inicialización de rutas de la multimedia
            $scope.archivos=[];
            //Inicialización de opciones vacías
            $scope.selectedOption=[];
            $http.get("./data/archivos.json").then(function(response)
            {
                $scope.archivos=response.data;
                var i=0;
                for(i=0;i<$scope.archivos.length;i++)
                {
                    $scope.archivos[i].ruta="data/"+$scope.archivos[i].ruta;
                }
            });
            
            //Inicialización de elementos multimedia a agregar a la noticia.
            $scope.multimedia=[];
            //Items para agregar
            $scope.itemsToAdd=[{nombre:null,descripcion:null,link:null,ruta:null}];
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
                $scope.itemsToAdd.push({nombre:null,descripcion:null,link:null,ruta:null});
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
             * Asigna la ruta al ítem a agregar.<br>
             * @param itemToAdd
             * @param ruta
             */
            this.asignarRuta=function(itemToAdd,ruta)
            {
                var index=$scope.itemsToAdd.indexOf(itemToAdd);
                $scope.itemsToAdd[index].ruta=ruta;
                $scope.selectedOption[index]=index+"-"+ruta;
            };
            /**
             * Verifica que todas la multimedia tiene un mensaje asignado.<br>
             * @return booleano para ver si todas las tienen o no.
             */
            this.verificarMultimedia=function()
            {
                var i;
                for(i=0;i<$scope.itemsToAdd.length;i++)
                {
                    if($scope.itemsToAdd[i].ruta===null || $scope.itemsToAdd[i].ruta===undefined)
                    {
                        return false;
                    }
                    if($scope.itemsToAdd[i].nombre===null || $scope.itemsToAdd[i].nombre===undefined)
                    {
                        return false;
                    }
                }
                return true;
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
            /**
             * Retorna el número actual asignado.<br>
             * @param itemToAdd
             * @param ruta
             */
            this.numeroActual=function(itemToAdd)
            {
                var index=$scope.itemsToAdd.indexOf(itemToAdd);
                return index;
            };
            
        }
    ]);
}
)(angular);
