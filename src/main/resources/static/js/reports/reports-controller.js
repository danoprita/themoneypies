(function() {
    "use strict";

    angular.module("app.reports").controller("ReportsController", [
        "$scope", "$http", "$log", "$timeout", "localStorageService",
        ReportsController])

    function ReportsController($scope, $http, $log, $timeout, localStorageService) {
        initDatePickers();

        $timeout(function() {
            $scope.refreshReports();
        }, 1000);

        $scope.refreshReports = function() {
            var dates = getDates();
            localStorageService.set("themoneypies.reports.dates", dates);

            savingsReport(dates);
            expensesPerTagReport(dates);
            savingsPerMonthReport(dates);
            tagsPerMonthReport(dates);
        };

        $scope.getAbsoluteValue = function(value) {
            if (value) {
                return Math.abs(value);
            } else {
                return ""
            }
        };

        function tagsPerMonthReport(dates) {
            $http.get("/report-tags-per-month", {
                params: dates
            }).then(function(response) {
                console.debug(response.data);
                var data = new google.visualization.DataTable();
                data.addColumn('string', 'Month');
                angular.forEach(response.data.tagsSet, function(tag) {
                    data.addColumn("number", tag);
                });

                var dataRows = [];

                angular.forEach(response.data.tagsPerMonth, function(tagsMap, month) {
                    var row = [month];
                    angular.forEach(response.data.tagsSet, function(tag) {
                        row.push(Math.abs(tagsMap[tag]));
                    });
                    dataRows.push(row);
                });
                data.addRows(dataRows);

                var options = {
                    title: "Tags per Month",
                    legend: {position: 'top', maxLines: 3},
                    bar: {groupWidth: '75%'},
                    isStacked: true
                };

                var chart = new google.visualization.ColumnChart(document.getElementById('tags-per-month'));
                chart.draw(data, options);
            });
        }

        function savingsPerMonthReport(dates) {
            $http.get("/report-savings-per-month", {
                params: dates
            }).then(function(response) {
                var rows = [[
                    "Month", 'Income', "Expenses", "Savings", "Deposits"
                ]];
                angular.forEach(response.data, function(value, key) {
                    var income = Math.abs(value.income);
                    var expenses = Math.abs(value.expenses);
                    var savings = Math.abs(value.savings);
                    var deposits = Math.abs(value.deposits);
                    rows.push([
                        key, income, expenses, savings, deposits
                    ])
                });
                var data = google.visualization.arrayToDataTable(rows);

                var options = {
                    title: 'Savings Per Month',
                    vAxis: {title: 'Sum'},
                    hAxis: {title: 'Month'},
                    seriesType: 'bars'
                    //series: {
                    //    3: {type: 'line'},
                    //    4: {type: 'line'},
                    //    5: {type: 'line'}
                    //}
                };

                var chart = new google.visualization.ComboChart(document.getElementById('savings-per-month'));
                chart.draw(data, options);
            });
        }

        function savingsReport(dates) {
            $http.get("/report-savings", {
                params: dates
            }).then(function(response) {
                $scope.generalReportData = response.data;
            });
        }

        function expensesPerTagReport(dates) {
            $http.get("/report-expenses-per-tag", {
                params: dates
            }).then(function(response) {
                $log.debug(response);
                var rows = [];
                angular.forEach(response.data, function(value, key) {
                    rows.push([key, Math.abs(value)]);
                });

                // Create the data table.
                var data = new google.visualization.DataTable();
                data.addColumn('string', 'Tag');
                data.addColumn('number', 'Amount');
                data.addRows(rows);

                // Set chart options
                var options = {
                    'title': 'Expenses per tag'
                    //'width': 400,
                    //'height': 300
                };

                console.debug("draw pie chart")
                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.PieChart(document.getElementById('expenses-per-tag'));
                chart.draw(data, options);
            });
        }

        function getDates() {
            return {
                startDate: moment($scope.startDate).format('YYYY-MM-DD'),
                endDate: moment($scope.endDate).format('YYYY-MM-DD')
            }
        }

        function initDatePickers() {
            $scope.dateOptions = {
                startingDay: 1
            };

            $scope.startDateConfig = {
                opened: false
            };

            $scope.endDateConfig = {
                opened: false
            };

            var savedDates = localStorageService.get("themoneypies.reports.dates");
            if (savedDates) {
                $scope.startDate = moment(savedDates.startDate, 'YYYY-MM-DD').toDate();
                $scope.endDate = moment(savedDates.endDate, 'YYYY-MM-DD').toDate();
            } else {
                $scope.startDate = new Date();
                $scope.endDate = new Date();
            }

            function today() {
                return new Date();
            }

            $scope.maxDate = new Date(2020, 5, 22);

            $scope.openStartDate = function() {
                $scope.startDateConfig.opened = true;
            };

            $scope.openEndDate = function() {
                $scope.endDateConfig.opened = true;
            };
        }
    }
}).call(this);