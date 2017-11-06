const ICON_CLASS_MAP = {
    "archive": "fa-file-archive-o",
    "word": "fa-file-o-word",
    "unknown": "fa-file-o",
    "image": "fa-file-image-o",
    "pdf": "fa-file-pdf-o",
    "video": "fa-file-video-o",
    "powerpoint": "fa-file-powerpoint-o",
    "audio": "fa-file-audio-o",
    "excel": "fa-file-excel-o",
    "text": "fa-file-text-o"
};

class SDAttachmentItemController {

    get iconClass(){
        const classes = {fa:true};
        const ico = ICON_CLASS_MAP[this.attachment.fileType];
        classes[ico] = true;
        return classes
    }

    get fileSize(){
        const size = this.attachment.size;
        if (!size) return "? b";
        if (size < 1024) return "1 kb";
        if (size < 1048576) return `${Math.ceil(size/1024)} kB`; // size < 2^20
        if (size < 1073741824) return `${Math.ceil(size/1048576)} MB`; // size < 2^30
        return `${Math.ceil(size/1073741824)} GB`; // size >= 2^30
    }
}

export {SDAttachmentItemController as controller}