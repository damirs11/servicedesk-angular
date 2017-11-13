/**
 * Модуль, предоставляющий API бэкэнда
 */
import {SDFactory} from "./sd.factory";
import {ConnectorProvider} from "./connector/connector-provider";
import {SessionFactory} from "./session.factory";
import {FileUploaderFactory} from "./file-uploader.factory";

const servicedeskAPI = angular.module("servicedesk-api", ["ngFileUpload"])
    .provider("$connector", ConnectorProvider)
    .factory('SD', SDFactory)
    .factory('$fileUploader', FileUploaderFactory)
    .factory('Session', SessionFactory)
    .name
;

export {servicedeskAPI};