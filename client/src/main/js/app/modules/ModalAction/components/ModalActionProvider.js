import {modalArea, default as buildModal} from "./buildModal";

/**
 * Модуль ModalAction
 * Позволяет создавать всплывающие модальные окна и определить их функционал.
 *
 * Применение:
 *
 * 1) Настроить в конфиге модальное действие:
 *
 *    ModalActionProvider.modal(name:string,config:Object):ModalActionProvider
 *      name:string имя модального действия (необязательно, если передан параметр name)
 *      config:Object конфигурация модального действия
 *      config.name:string имя модального действия
 *      config.template:string html шаблон (необязательно)
 *      config.templateUrl:string ссылка html шаблон (если template не указан)
 *      config.controller:string|Function контроллер модального окна
 *        примеры:
 *          controller: "MyController"
 *          controller: "MyController as ctrl"
 *          controller: function(){...}
 *          controller: ["$inject",function($inject){...}]
 *      config.controllerAs:String ссылка в скоупе на контроллер (необязательно)
 *      config.isolateScope:boolean нужно ли изолировать скоуп (false)
 *      return:ModalActionProvider возвращает this
 *
 *    ModalActionProvider.modal(config:Object):ModalActionProvider
 *      аналогично предыдущему, но обязательно указать config.name
 *
 * 2) Использовать для вызова модального окна:
 *    ModalAction.${name}($scope, invoke, resolver):Promise
 *      name: имя модального действия
 *      $scope:Scope текущий скоуп. Для модального окна создастся дочерний
 *      invoke:Object* данные, передаваемые в контроллер как $modalData
 *          все поля объекта будут разрешены и переданы в контроллер по их именам (если требуются).
 *      resolver:?Promise промис, который может разрешить модальное действие раньше, извне контроллера
 *      return:Promise промис, который разрешится с закрытием модального окна
 *    Пример:
 *      ModalAction.promptNumber($scope, {numbers:[1,2,3,4,5]} )
 *          .then(function(value){
 *              alert("Вы выбрали "+value);
 *          });
 *    Ах, да, забыл сказать... их можно вызывать рекурсивно!
 *    То есть контроллер модального действия может запросить еще одно действие.
 *
 * 3) Параметры контроллера модального окна:
 *    $scope:Scope - скоуп модального окна
 *    $scope.$resolve:Function(value) функция для разрешения модального действия
 *      value:* - возаращаемое значение при разрешении
 *    $scope.$reject:Function(value) функция для разрешения модального действия с ошибкой
 *      value:* - возаращаемое значение при разрешении с ошибкой
 *    $element:jQuery|jqLite - элемент модального окна
 *    $modalData:* данные, передаваемые в контроллер
 *    $modalState:Object Объект для управления состоянием модального окна
 *    $modalState.resolve:Function(value) функция для разрешения модального действия
 *      value:* - возаращаемое значение при разрешении
 *    $modalState.reject:Function(value) функция для разрешения модального действия с ошибкой
 *      value:* - возаращаемое значение при разрешении с ошибкой
 *    $modalState.onCancel:Function(event) функция при отмене (клик вне модального окна)
 *      event:Event - событие клика на маску вне элемента модального окна
 *      по умолчанию вызывается $modalState.resolve(null)
 *      чтобы изменить это поведение, нужно переопределить значение $modalState.onCancel
 *    также в контроллер передаются все перечисляемые поля из объекта $modalData
 *
 * 4) Настройка анимации:
 *    С помощью модуля $animate можно настроить анимацию всплывающих окон.
 *    Используются классы: ng-enter, ng-enter-active, ng-leave, ng-leave-active
 *    Анимация появления окна:
 *      в .ng-enter указывается исходное состояние и  transition (время анимации будет включено в Promise).
 *      в .ng-enter-active указывается конечное состояние.
 *    Анимация закрытия окна:
 *      в .ng-leave указывается исходное состояние и transition (время анимации будет включено в Promise).
 *      в .ng-leave-active указывается конечное состояние.
 *
 * @author: Андрей Вахтеров [dpohvar@gmail.com]
 * @date 23.10.2015
 */

ModalActionProvider.$inject = [];
function ModalActionProvider(){

    var factory = {};

    /**
     * @name ModalActionProvider
     * @type {Object}
     */
    var provider = {

        /**
         * Добавляет конфиг модального действия
         * @name ModalActionProvider.modal
         * @param {?String|Object} $modalName имя модального действия
         * @param {?Object} [$modalConfig] конфигурация
         * @returns {Object} this
         */
        modal($modalName, $modalConfig){
            if (typeof $modalName == "object") {
                $modalConfig = $modalName;
                $modalName = $modalConfig.name;
            }

            // добавить метод ModalAction.{имя модального действия}
            Object.defineProperty(factory, $modalName, {
                value: invokeModal,
                enumerable: true
            });

            // сам метод ModalAction.{name}
            // $scope - в каком скоупе создать окошко
            // $modalData - что передать внутрь контроллера того окна
            // $callback - калбака при открытии окна
            function invokeModal($scope, $modalData, $callback){
                if ($scope && $scope.$$destroyed) throw new Error("Scope is destroyed!");
                var $injector = provider.$injector;
                return $injector.invoke(buildModal, null, {$modalData, $scope, $callback, $modalConfig, $modalName})
            }
            return this;
        },

        $get: ModalAction
    };

    ModalAction.$inject = ['$injector'];
    function ModalAction($injector){
        modalArea.prependTo('body');
        provider.$injector = $injector;
        return factory;
    }

    return provider;
}


export default ModalActionProvider;