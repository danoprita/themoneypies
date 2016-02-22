(function() {
    "use strict";

    angular.module("app.import").controller("ImportController", [
        "$scope", "FileUploader",
        ImportController
    ]);

    function ImportController($scope, FileUploader) {
        $scope.transactionsUploader = new FileUploader({
            url: "/import/transactions"
        });

        $scope.rulesUploader = new FileUploader({
            url: "/import/rules"
        });
    }
}).call(this);