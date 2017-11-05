(function (ng) {

    var mod = ng.module("noticiasModule");

    mod.controller("noticiasCtrl", ['$scope', '$state', '$http','noticiasContext','globalContext', function ($scope, $state, $http,context,globalContext) {
             $scope.noticiaEditable=false;$scope.esNoticiaUsuario=false; $scope.deGrupo=false;

             var error="";
             if($state.params.mensaje!==null && $state.params.mensaje!==undefined)
             {
                 $scope.variableErrorNoticia=$state.params.mensaje;
             }
            fullContext=globalContext+"/"+context;
            
            header="¿Qué pasa con tus intereses hoy?";            
            
            //Inicialización de elementos multimedia a agregar a la noticia.
            $scope.multimedia=[];
            $scope.itemsToAdd=[{nombre:' ',descripcion:' ',link:' '}];
            this.add=function(itemToAdd){
                console.log(itemToAdd);
                itemToAdd.link=this.randomString();
                var index=$scope.itemsToAdd.indexOf(itemToAdd);
                $scope.itemsToAdd.splice(index,1);
                $scope.multimedia.push(angular.copy(itemToAdd));
            };
            this.addNew=function(){
                console.log("NUEVO ITEM");
                $scope.itemsToAdd.push({nombre:' ',descripcion:' ',link:' '});
            };
            this.addAll=function()
            {
                while($scope.itemsToAdd.length!==0)
                {
                    console.log($scope.itemsToAdd[0]);
                    this.add($scope.itemsToAdd[0]);
                }
            };
            this.remove=function(itemToAdd)
            {
                var index=$scope.itemsToAdd.indexOf(itemToAdd);
                $scope.itemsToAdd.splice(index,1);
            };
            this.randomString= function()
            {
                var text="";
              var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
                   for (var i = 0; i < 10; i++)
                 text += possible.charAt(Math.floor(Math.random() * possible.length));
                 console.log("TEXTO "+text);
                return text;  
            };
            // inicialmente el listado de noticias está vacio
            $scope.records = {};
            // carga las noticias
            $http.get(fullContext).then(function (response) {
                $scope.records = response.data;
            },function(response){
                                error=response.data;
                                $state.go('ERRORNOTICIA',{mensaje: error},{reload:true});
                            });
            // el controlador recibió un id ??
            // revisa los parámetros (ver el :id en la definición de la ruta)
            if ($state.params.noticiaId !== null && $state.params.noticiaId !== undefined) {

                // toma el id del parámetro
                id = $state.params.noticiaId;
                // obtiene el dato del recurso REST
                $http.get(fullContext + "/" + id)
                        .then(function (response) {
                            // $http.get es una promesa
                            // cuando llegue el dato, actualice currentRecord
                            $scope.currentRecord = response.data;
                        },function(response){
                                error=response.data;
                                $state.go('ERRORNOTICIA',{mensaje: error},{reload:true});
                            });

                // el controlador no recibió un cityId
            } else {
                // el registro actual debe estar vacio
                $scope.currentRecord = {
                    id: undefined /*Tipo Long. El valor se asigna en el backend*/,
                    name: '' /*Tipo String*/,
                };

            }
            console.log($scope.currentRecord);
            this.getHeader= function()
            {
                return header;
            };
            // Código continua con las funciones de despliegue de errores


        }]);
})(window.angular);

