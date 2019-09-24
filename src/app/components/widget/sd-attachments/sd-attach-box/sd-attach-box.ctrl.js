class SDAttachBoxController {


    select(files) {
        this.onAttach({$files:files})
    }

    drop(files) {
        this.onAttach({$files:files})
    }
}

export {SDAttachBoxController as controller}