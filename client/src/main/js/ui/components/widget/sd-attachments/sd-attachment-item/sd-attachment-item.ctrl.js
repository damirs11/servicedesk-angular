const ICON_CLASS_MAP = {
    "archieve": "fa file-archieve-o",
    "word": "fa file-o-word",
    "unknown": "fa file-o",
    "image": "fa file-image-o",
    "pdf": "fa file-pdf-o",
    "video": "fa file-video-o",
    "powerpoint": "fa file-powerpoint-o",
    "audio": "fa file-audio-o",
    "excel": "fa file-excel-o",
    "text": "fa file-text-o"
};

class SDAttachmentItemController {

    get iconClass(){
        return ICON_CLASS_MAP[this.attachment.fileType];
    }

    get fileSize(){
        const size = this.attachment.size;
        if (!size) return "? kb";
        if (size < 1024) return `${(size).toFixed(2)} kB`; // size < 2^10
        if (size < 1048576) return `${(size/1024).toFixed(2)} MB`; // size < 2^20
        return `${(size/1048576).toFixed(2)} GB`; // size >= 2^20
    }
}

export {SDAttachmentItemController as controller}