(function() {
    "use strict";

    angular.module("app.rules").controller("RulesController", ["$scope", "$http", "$log", RulesController]);

    function RulesController($scope, $http, $log) {
        $scope.rules = [];

        loadRules();

        function loadRules() {
            $http.get("/rules").then(function(response) {
                $scope.rules = response.data;
            })

        }

        $scope.getPatterns = function(rule) {
            return rule.patterns.join(", ");
        };

        $scope.deleteRule = function(rule) {
            $http.delete("/rules/" + rule.id).then(function() {
                $log.debug("Deleted successfully.");
                loadRules();
            })
        }
    }
}).call(this);