(function () {
    "use strict";

    angular.module("app.entries").controller("TransactionsController",
        ["$scope", "$http", "$uibModal", "$log", TransactionsController]);

    function TransactionsController($scope, $http, $uibModal, $log) {
        $scope.transactions = [];

        loadTransactions();

        function loadTransactions() {
            $http.get('/transactions').then(function (response) {
                $scope.transactions = response.data;
            });
        }

        $scope.getDate = function (date) {
            var date = new Date(date);
            return moment(date).format('DD MMMM YYYY');
        };

        $scope.createRule = function () {
            openCreateRule();

            function openCreateRule() {
                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: 'html/rules/create-rule-modal.html?' + new Date(),
                    controller: "CreateRuleController",
                    size: "modal-sm",
                    resolve: {
                        ruleText: function () {
                            return getSelectedText();
                        }
                    }
                });

                modalInstance.result.then(function (ruleData) {
                    $log.debug("saving rule ", ruleData)

                    $http.post("/rules", {
                        pattern: ruleData.pattern,
                        tag: ruleData.tag,
                        exclude: ruleData.exclude,
                        savings: ruleData.isSavings
                    }).then(function(response) {
                        $log.debug("success", response);
                        loadTransactions();
                    })


                }, function () {
                    $log.info('Modal dismissed at: ' + new Date());
                });
            }


            function getSelectedText() {
                if (window.getSelection) {  // all browsers, except IE before version 9
                    var range = window.getSelection();
                    return range.toString();
                }
                else {
                    if (document.selection.createRange) { // Internet Explorer
                        var range = document.selection.createRange();
                        return range.text;
                    }
                }
            }
        };


    }
}).call(this);