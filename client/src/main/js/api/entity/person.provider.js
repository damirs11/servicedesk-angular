import {Parse} from "./decorator/parse.decorator";
import {Serialize} from "./decorator/serialize.decorator";
import {Nullable} from "./decorator/parse-utils";
import {Mixin} from "./mixin/mixin.decorator";


PersonProvider.$inject = ["EditableEntity", "SD", "AttachmentsHolder"];
function PersonProvider(EditableEntity, SD, AttachmentsHolder) {
    /**
     * Персона
     * @class
     * @name SD.Person
     * @mixes ENTITY_MIXIN.AttachmentsHolder
     */
    @Mixin(AttachmentsHolder)
    class Person extends EditableEntity {
        /**
         * Пол персоны: false - женский, true - мужской
         * @property
         * @name SD.Person#sex
         * @type {boolean}
         */
        @Serialize(Boolean) @Parse( Nullable(Boolean) ) sex;
        /**
         * Почта
         * @property
         * @name SD.Person#email
         * @type {string}
         */
        @Serialize(String) @Parse( Nullable(String) ) email;
        /**
         * Должность
         * @property
         * @name SD.Person#job
         * @type {string}
         */
        @Serialize(String) @Parse( Nullable(String) ) job;
        /**
         * Имя
         * @property
         * @name SD.Person#firstName
         * @type {string}
         */
        @Serialize(String) @Parse( Nullable(String) ) firstName;
        /**
         * Фамилия
         * @property
         * @name SD.Person#lastName
         * @type {string}
         */
        @Serialize(String) @Parse( Nullable(String) ) lastName;
        /**
         * Отчество
         * @property
         * @name SD.Person#middleName
         * @type {string}
         */
        @Serialize(String) @Parse( Nullable(String) ) middleName;
        /**
         * Сокращенное имя персоны
         * Фамилия + инициалы
         * @property
         * @name SD.Person#shortName
         * @type {String}
         */
        @Parse( Nullable(String) ) shortName;
        /**
         * Организация персоны
         * @property
         * @name SD.Person#organization
         * @type {SD.Organization}
         */
        @Serialize(org => org.id) @Parse(data => SD.Organization.parse(data)) organization;

        /**
         * Полное имя персоны
         * Фамилия + имя + отчество
         * @property
         * @name SD.Person#shortName
         * @type {String}
         */
        get fullName(){
            return [ this.lastName, this.firstName, this.middleName ]
                .filter(_=>_)
                .join(' ')
            ;
        }

        toString(){
            return this.fullName
        }

        $avatarAttachment;
        $loadingAvatar = false;

        get avatarPath(){
            if (this.avatarAttachment) return `rest/service/file/download?id=${this.avatarAttachment.id}`;
            // Если вложения все нет - асинхронно грузим его, а возвращаем дефолтную аватарку.
            if (this.avatarAttachment === undefined && !this.$loadingAvatar) this.$loadAvatarAttachment();

            if (this.sex) {
                return "img/female-avatar.png";
            } else if (this.sex === false) {
                return "img/male-avatar.png";
            } else {
                return "img/default-avatar.png";
            }
        }

        async $loadAvatarAttachment(){
            this.$loadingAvatar = true;
            const attachments = await this.getAttachments();
            if (!attachments || !attachments.length) {
                this.avatarAttachment = null;
            } else {
                this.avatarAttachment = attachments[0];
            }
            this.$loadingAvatar = false;
        }
    }
    return Person;
}

export {PersonProvider};