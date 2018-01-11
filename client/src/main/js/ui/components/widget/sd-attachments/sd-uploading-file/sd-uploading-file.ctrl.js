class SDUploadingFileController {

    abort(){
        this.onAbort()
    }

    get error() {
        return this.status == "error"
    }
    get success() {
        return this.status == "success"
    }
    get pending() {
        return this.status == "pending"
    }

    get progressBarText () {
        if (this.pending) return (this.percentage < 15 ? "" : this.percentage+"%");
        if (this.success) return "Подождите... ";
        if (this.error) return "Возникла ошибка"
    }

    get progressBarValue () {
        if (this.pending) return this.percentage;
        return 100;
    }

    get progressBarType () {
        if (this.pending) return "primary";
        if (this.success) return "success";
        if (this.error) return "danger";
    }
}

export {SDUploadingFileController as controller}