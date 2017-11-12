(function (ng) {
    var mod = ng.module("loginModule");
    mod.controller('logoutCtrl', ['$rootScope', '$state', function ($rootScope, $state) {
            if (sessionStorage.getItem("email")) {
                sessionStorage.clear();
            } else {
                console.log("No sé qué hacer");
            }
        }
    ]);
}
)(window.angular);

