(function () {
    "use strict";

    angular.module("app", [
        "ngRoute",
        "ui.bootstrap",
        "LocalStorageModule",
        "app.entries",
        "app.rules",
        "app.reports",
        "app.import"
    ]);

    angular.module("app").config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/', {
            redirectTo: "/transactions"
        }).when('/transactions', {
            templateUrl: 'html/transactions/transactions-list.html',
            controller: 'TransactionsController'
        }).when('/rules', {
            templateUrl: 'html/rules/rules-list.html',
            controller: 'RulesController'
        }).when('/reports', {
            templateUrl: 'html/reports/dashboard.html',
            controller: 'ReportsController'
        }).when('/import', {
            templateUrl: 'html/import/import.html',
            controller: 'ImportController'
        }).otherwise({
            redirectTo: '/'
        });
    }]);

    initGoogleCharts();

    function initGoogleCharts() {
        // Load the Visualization API and the corechart package.
        google.charts.load('current', {'packages': ['corechart']});

         //Set a callback to run when the Google Visualization API is loaded.
        //google.charts.setOnLoadCallback(drawChartCallback);
    }
}).call(this);