(function (ng) {
    var mod = ng.module("loginModule");
    mod.controller('logoutCtrl', ['$rootScope', function ($rootScope) {
            $rootScope.esLogin=false;
            if (sessionStorage.getItem("email")) {
                sessionStorage.clear();
            }
        }
    ]);
}
        )(window.angular);

