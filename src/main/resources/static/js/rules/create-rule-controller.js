(function() {
    "use strict";

    angular.module("app.rules").controller("CreateRuleController",
        ["$scope", "$uibModalInstance", "ruleText", CreateRuleController]);

    function CreateRuleController($scope, $uibModalInstance, ruleText) {
        $scope.pattern = ruleText || "";

        $scope.createRule = function() {
            $uibModalInstance.close({
                pattern: $scope.pattern.trim(),
                tag: $scope.tag.trim(),
                exclude: $scope.exclude,
                isSavings: $scope.isSavings
            });
        };

        $scope.cancel = function() {
            $uibModalInstance.dismiss('cancel');
        };

    }

}).call(this);