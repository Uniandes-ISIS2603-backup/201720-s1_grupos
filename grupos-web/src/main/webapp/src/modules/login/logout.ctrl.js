(function (ng) {
    var mod = ng.module("loginModule");
    mod.controller('logoutCtrl', ['$rootScope', '$state', function ($rootScope, $state) {
            $rootScope.esLogin=false;
            if (sessionStorage.getItem("email")) {
                sessionStorage.clear();
            } else {
                console.log("Hubo un error en el logout");
            }
        }
    ]);
}
        )(window.angular);

