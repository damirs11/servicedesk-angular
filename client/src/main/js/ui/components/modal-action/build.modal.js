import {jQuery as $} from "../../../common/web-libraries.const";

const MODAL_OPEN_CLASS = 'modal-open';

const linkPromiseCache = Object.create(null);
export const modalAndStateStack = [];

export const modalArea = $(`<div id="ModalActionProviderArea" role="dialog" style="display: block;">`);
const maskTemplate = $("<div id='ModalActionProviderMask' class='modalActionProviderMask'>");
const containerTemplate = $(`<div class="modalContainer">`)
    .click(function (e) {
        if (this !== e.target) return;
        const modalAndState = modalAndStateStack[modalAndStateStack.length - 1];
        modalAndState && modalAndState.state.onCancel && modalAndState.state.onCancel(e);
    });
let mask;

const getScrollWidth = (computedScroll => () => {
    if (computedScroll != null) return computedScroll;
    const div = document.createElement('div');
    div.style.cssText = 'overflow-y: scroll; width: 50px; height: 50px; visibility: hidden';
    document.body.appendChild(div);
    computedScroll = div.offsetWidth - div.clientWidth;
    document.body.removeChild(div);
    return computedScroll;
})();

buildModal.$inject = ['$modalConfig', '$modalName', '$scope', '$q', '$modalData', '$callback', '$compile', '$controller', '$injector', '$http', '$templateCache', '$rootScope', '$animate', '$timeout'];
function buildModal($modalConfig, $modalName, $scope, $q, $modalData, $callback, $compile, $controller, $injector, $http, $templateCache, $rootScope, $animate, $timeout) {
    if ($scope == null) $scope = $rootScope;
    const modalScope = $scope.$new($modalConfig.isolateScope || false);

    let modalResolve, modalReject;
    const modalPromise = $q((resolve, reject) => {
        modalResolve = resolve;
        modalReject = reject;
    });

    function doResolve(value) {
        return modalResolve(value);
    }

    function doReject(value) {
        return modalReject(value);
    }

    modalScope.$resolve = doResolve;
    modalScope.$reject = doReject;
    modalScope.$modalData = $modalData;
    /**
     * Обработчик состояния модального окна. Может разрешить его или закрыть
     * @type {{resolve: doResolve, reject: doReject, onCancel: $modalState.onCancel}}
     */
    const $modalState = {
        resolve: doResolve,
        reject: doReject,
        onCancel: function () {
            return modalResolve(null)
        }
    };

    modalScope.$on("$destroy", doReject);

    let linkPromise = linkPromiseCache[$modalName];
    // в linkPromiseCache кладем промис, который загрузит template и скомпилирует
    if (!linkPromise) {
        linkPromise = linkPromiseCache[$modalName] = loadTemplate()
            .then(template => $compile(template))
    }

    // получаем template:
    // если оп прописан в config.template - берем как строку
    // если в config.templateUrl - загружаем по ссылке, используя $templateCache
    function loadTemplate() {
        if ('template' in $modalConfig) return $q.when($modalConfig.template);
        else return $http({method: "GET", url: $modalConfig.templateUrl, cache: $templateCache})
            .then(response => response.data)
        ;
    }

    const event = $rootScope.$broadcast('ModalAction:open', $modalName, $modalState, $modalData);
    if (event.defaultPrevented) return $q.reject(event);

    let modalAndState;
    // возвращаем промис, который разрешится после linkPromise вместе с modalPromise
    return linkPromise
        .then(link => {
            let $element = null;
            const locals = {
                $scope: modalScope,
                $element: $element,
                $modalState: $modalState,
                $modalData: $modalData
            };
            if ($modalConfig.controller) $injector.annotate($modalConfig.controller).forEach(dep => {
                if ($injector.has(dep)) return;
                if (locals[dep]) return;
                locals[dep] = $modalData && $modalData[dep];
            });
            const controller = $modalConfig.controller ? $controller($modalConfig.controller, locals) : null;
            if ($modalConfig.controllerAs) modalScope[$modalConfig.controllerAs] = controller;
            if (typeof $callback === "function") $callback($modalState, $modalData, $scope);
            link(modalScope, el => $element = el);
            const pushModalResult = pushModalState($element, $modalState, $animate, $q, $timeout);
            modalAndState = pushModalResult.modalAndState;
            return pushModalResult.animation;
        })
        .then(() => {
            const event = $rootScope.$broadcast('ModalAction:show', $modalName, $modalState, $modalData, modalScope);
            if (event.defaultPrevented) return $q.reject(event);
            return modalPromise;
        })
        .then((value) => {
            $rootScope.$broadcast('ModalAction:resolve',$modalName, $modalState, $modalData, modalScope, value);
            return value;
        }, (error) => {
            $rootScope.$broadcast('ModalAction:reject',$modalName, $modalState, $modalData, modalScope, error);
            return $q.reject(error);
        })
        .finally(() => {
            modalScope.$destroy();
            removeModalState(modalAndState, $animate, $q)
                .then(() => {
                    $rootScope.$broadcast('ModalAction:destroy',$modalName, $modalState, $modalData, modalScope);
                })
            ;
        });
}

function pushModalState(element, state, $animate, $q, $timeout) {
    const container = containerTemplate
        .clone(true);
    const modalAndState = {element: element, state: state, container: container};
    modalAndStateStack.push(modalAndState);
    let animation;
    if (modalAndStateStack.length > 1) {
        mask.appendTo(modalArea);
        container.appendTo(modalArea);
        animation = $animate.enter(element, container);
    } else {
        const currentMask = mask = maskTemplate.clone(true);
        fixBodyScroll();

        modalArea.append(container);
        const prev = container.prev();
        animation = $q.all([
            $animate.enter(currentMask, modalArea, prev.length ? prev : void 0),
            // $timeout(0).then(()=>$animate.enter(container, modalArea, currentMask)),
            $timeout(0).then(()=>$animate.enter(element, container))
        ]);
    }
    return {
        animation: animation,
        modalAndState: modalAndState
    }
}

function removeModalState(modalAndState, $animate, $q) {
    const index = modalAndStateStack.indexOf(modalAndState);
    if (index < 0) return;
    modalAndStateStack.splice(index, 1);
    let promise;
    if (modalAndStateStack.length == 0) {
        promise = $q.all([
            $animate.leave(mask),
            $animate.leave(modalAndState.element)
        ]).then(() => {
            modalAndState.container.remove();
            fixBodyScroll();
        });
    } else {
        mask.detach().insertBefore(modalAndState.container.prev());
        promise = $animate.leave(modalAndState.element).then(()=> {
            modalAndState.container.remove();
            if (modalAndStateStack.length) {
                mask.detach().insertBefore(modalAndStateStack[modalAndStateStack.length - 1].container)
            } else {
                fixBodyScroll();
                return $animate.leave(mask);
            }
        });
    }
    return promise;
}

function fixMaskPosition(modalAndState) {
    mask.detach().insertBefore(modalAndState.container.prev())
}

function fixBodyScroll(){
    const $body = $('body');
    if (modalAndStateStack.length) {
        let $overflowElement;
        if (document.documentElement.clientWidth < document.documentElement.scrollWidth
            || document.documentElement.clientHeight < document.documentElement.scrollHeight) {
            $overflowElement = $("html");
        }
        if (document.body.clientWidth < document.body.scrollWidth
            || document.body.clientHeight < document.body.scrollHeight) {
            $overflowElement = $body;
        }
        $body.addClass(MODAL_OPEN_CLASS);
        $overflowElement && $overflowElement.css({marginRight: getScrollWidth()});
    } else {
        $body.removeClass(MODAL_OPEN_CLASS).css({marginRight: ""});
    }
}

export default buildModal;