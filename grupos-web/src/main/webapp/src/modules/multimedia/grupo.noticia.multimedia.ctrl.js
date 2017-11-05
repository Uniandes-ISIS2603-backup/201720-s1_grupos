(function (ng) {
    
    var mod = ng.module("multimediaModule");

    mod.controller('grupoNoticiaMultimediaCtrl', ['$scope', '$state', '$http', 'multimediaContext','noticiasContext','globalContext','noticiaGrupoContext', function ($scope, $state, $http, multimediaContext,noticiaContext, globalContext,grupoContext) {
            if($state.params.mensaje!==null && $state.params.mensaje!==undefined)
            {
                $scope.variableErrorMultimedia=$state.params.mensaje;
            }
            //Inicialización de variable para saber si es de blog o no.
            $scope.esMultimediaBlog=false;
            $scope.esMultimediaNoticia=true;
            //Inicialización del multimediaContexto
            fullContext=globalContext+"/"+grupoContext+"/"+$state.params.grupoId+"/"+noticiaContext+"/"+$state.params.noticiaId+"/"+multimediaContext;
            //Inicialización de elementos multimedia a agregar a la noticia.
            $scope.multimedia=[];
            //Items a agregar
            $scope.itemsToAdd=[{nombre:' ',descripcion:' ',link:' '}];
            /**
             * Agrega un nuevo ítem a la lista de por agregar
             * @param {type} itemToAdd
             */
            this.add=function(itemToAdd){
                console.log(itemToAdd);
                itemToAdd.link=this.randomString();
                var index=$scope.itemsToAdd.indexOf(itemToAdd);
                $scope.itemsToAdd.splice(index,1);
                $scope.multimedia.push(angular.copy(itemToAdd));
            };
            /**
             * Agrega un nuevo ítem a la lista de multimedia
             * @param {type} itemToAdd
             */
            this.addNew=function(){
                console.log("NUEVO ITEM");
                $scope.itemsToAdd.push({nombre:' ',descripcion:' ',link:' '});
            };
            /**
             * Agrega todos los ítems que se deben agregar
             * @param {type} itemToAdd
             */
            this.addAll=function()
            {
                while($scope.itemsToAdd.length!==0)
                {
                    console.log($scope.itemsToAdd[0]);
                    this.add($scope.itemsToAdd[0]);
                }
            };
            /**
             * Quitan un item por agregar de la lista
             * @param {type} itemToAdd
             */
            this.remove=function(itemToAdd)
            {
                var index=$scope.itemsToAdd.indexOf(itemToAdd);
                $scope.itemsToAdd.splice(index,1);
            };
            //Función de creación del link temporalmente
            this.randomString= function()
            {
                 var text="";
              var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
                   for (var i = 0; i < 5; i++)
                 text += possible.charAt(Math.floor(Math.random() * possible.length));
                 console.log("TEXTO "+text);
                return text; 
            };
            // inicialmente el listado de multimdia está vacio
            $scope.multimediaRecords = {};
            // carga la multimedia
            $http.get(fullContext).then(function (response) {
                $scope.multimediaRecords = response.data;
            },function(response){
                                error=response.data;
                                $state.go('ERRORMULTIMEDIAGRUPONOTICIA',{mensaje: error},{reload:true});
                            });

            // el controlador recibió un id ??
            // revisa los parámetros (ver el :id en la definición de la ruta)
            if ($state.params.multimediaLink !== null && $state.params.multimediaLink !== undefined) {

                // toma el id del parámetro
                link = $state.params.multimediaLink;
                // obtiene el dato del recurso REST
                $http.get(fullContext+"/"+ link)
                        .then(function (response) {
                            // $http.get es una promesa
                            // cuando llegue el dato, actualice currentMultimedia
                            $scope.currentMultimedia = response.data;
                        },function(response){
                                error=response.data;
                                $state.go('ERRORMULTIMEDIAGRUPONOTICIA',{mensaje: error},{reload:true});
                            });

                // el controlador no recibió un cityId
            } else {
               
               
                $scope.alerts = [];
            }
            this.saveRecord = function (link) {
                currentMultimedia = $scope.currentMultimedia;

                // si el id es null, es un registro nuevo, entonces lo crea
                if (link === null || link===undefined) {
                    currentMultimedia.link=this.randomString();
                    // ejecuta POST en el recurso REST
                    multimediaList=[currentMultimedia];
                    return $http.post(fullContext, multimediaList)
                            .then(function () {
                                // $http.post es una promesa
                                // cuando termine bien, cambie de estado
                                $state.go('grupoNoticiaMultimediaList',{},{reload:true});
                            },function(response){
                                error=response.data;
                                $state.go('ERRORMULTIMEDIAGRUPONOTICIA',{mensaje: error},{reload:true});
                            });
                    // si el id no es null, es un registro existente entonces lo actualiza
                } else {

                    // ejecuta PUT en el recurso REST
                    return $http.put(fullContext+"/" + currentMultimedia.link, currentMultimedia)
                            .then(function () {
                                // $http.put es una promesa
                                // cuando termine bien, cambie de estado
                                $state.go('grupoNoticiaMultimediaList',{},{reload:true});
                            },function(response){
                                error=response.data;
                                $state.go('ERRORMULTIMEDIAGRUPONOTICIA',{mensaje: error},{reload:true});
                            });
                }
                ;
            };
            this.deleteRecord= function(link)
            {
                if(link!==null)
                {
                    return $http.delete(fullContext+"/"+link).then (function()
                    {
                         $state.go('grupoNoticiaMultimediaList',{},{reload:true});
                    },function(response){
                                error=response.data;
                                $state.go('ERRORMULTIMEDIAGRUPONOTICIA',{mensaje: error},{reload:true});
                            });
                }
            };
           

        }]);
})(angular);

