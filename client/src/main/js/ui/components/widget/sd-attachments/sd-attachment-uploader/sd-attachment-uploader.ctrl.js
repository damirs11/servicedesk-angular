class SDAttachmentUploaderController {


    select(files) {
        this.drop(files);
    }

    drop(files) {
        console.log(files);
    }
}

export {SDAttachmentUploaderController as controller}