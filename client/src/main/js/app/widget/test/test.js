(function() {
    'use strict'

    angular
        .module('ui.test', [
            'ui.bootstrap',
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
        .controller('testController', ['$scope', '$log', 'uiDialogs', 'dialogResult',
            function($scope, $log, uiDialogs, dialogResult) {
                $scope.output = 'Область для отображения результатов';
                $scope.errorClick = function() {
                    aplanaDialogs.error('Ошибка', 'Невозможно выполнить операцию', {})
                }
                $scope.notifyClick = function() {
                    aplanaDialogs.notify('Уведомление', 'Документ отправлен')
                }
                $scope.confirmClick = function() {
                    var opts = {
                        labelYes:"Подтверить",
                        labelNo:"Отменить"
                    };
                    var dlg = aplanaDialogs.confirm('Вопрос', 'Вы уверены, что хотите отправить документ на корректировку?', opts)
                    dlg.result.then(function(result){
                        $scope.output = result
                    },function(reason){
                        $scope.output = reason
                    });
                }
                $scope.inputMessageClick = function() {
                    var dlg = aplanaDialogs.inputMessage()
                    dlg.result.then(function(result){
                        $scope.output = result
                    },function(reason){
                        $scope.output = reason
                    })
                }
            }])
} ())