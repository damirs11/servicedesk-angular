import { Directive, ElementRef, HostListener, Input } from '@angular/core';

@Directive({
    selector: '[appAutoHeight]'
})
export class AutoHeightDirective {

    constructor(private el: ElementRef) { }

    @Input() hMax = 124;
    @Input() hMin = 38;

    @HostListener('input') onInput() {

        this.el.nativeElement.style.height = 0;
        this.el.nativeElement.style.overflow = 'hidden';

        let height = this.el.nativeElement.scrollHeight + 2;
        let overscroll = false;

        if (height > this.hMax) {
            overscroll = true;
            height = +this.hMax;
        }

        if (height < this.hMin) {
            height = +this.hMin;
        }

        this.el.nativeElement.style.height = height + 'px';

        if (overscroll) {
            this.el.nativeElement.style.overflow = '';
        }

        console.log("recalculated, ", height, this.hMax, this.hMin);
    }

    // function delayedRecalculate(newValue) {
    //     newValue && setTimeout(recalculateHeight, 4);
    // }


    // element.on('input', recalculateHeight);
    // // scope.$evalAsync(recalculateHeight);
    // scope.$watch(autoHeight, delayedRecalculate);

    // scope.$on('$destroy', () => {
    //     element.off('input', recalculateHeight);
    // });
}
