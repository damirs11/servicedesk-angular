(function() {
    'use strict';

    angular
        .module('ui.test', [
            'ui.dialogs'
        ])
        .config(function ($stateProvider) {
            $stateProvider
                .state('test', {
                    url: '/test',
                    templateUrl: 'js/app/widget/test/test.html',
                    controller: 'testController'
                })
        })
        //https://github.com/m-e-conroy/angular-dialog-service
        .controller('testController', ['$scope', 'uiDialogs',
            function($scope, uiDialogs) {
                $scope.output = 'Область для отображения результатов';
                $scope.errorClick = function() {
                    uiDialogs.error('Ошибка', 'Невозможно выполнить операцию');
                };
                $scope.notifyClick = function() {
                    uiDialogs.notify('Уведомление', 'Документ отправлен');
                };
                $scope.confirmClick = function() {
                    var opts = {
                        labelYes:"Подтверить",
                        labelNo:"Отменить"
                    };
                    var dlg = uiDialogs.confirm('Вопрос', 'Вы уверены, что хотите отправить документ на корректировку?', opts);
                    dlg.result.then(function(result){
                        $scope.output = result + ' - result'
                    },function(reason){
                        $scope.output = reason + ' - dismiss'
                    });
                };
                $scope.inputMessageClick = function() {
                    var dlg = uiDialogs.inputMessage();
                    dlg.result.then(function(result){
                        $scope.output = result + ' - result'
                    },function(reason){
                        $scope.output = reason + ' - dismiss'
                    })
                }
            }])
} ());